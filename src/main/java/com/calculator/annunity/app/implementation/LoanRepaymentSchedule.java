package com.calculator.annunity.app.implementation;

import com.calculator.annunity.app.AnnuityCalculator;
import com.calculator.annunity.app.data.Credit;
import com.calculator.annunity.app.data.Month;
import org.apache.commons.math3.util.Precision;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.ToDoubleFunction;

/**
 * Created by WeDin on 27.04.2017.
 */
public class LoanRepaymentSchedule implements AnnuityCalculator {

    //region class fields
    //region instances fields
    private Credit credit;
    private Map<Integer, Month> monthsMap = new TreeMap<Integer, Month>();
    private CreditCalculator creditCalculator;
    //endregion
    //endregion

    public LoanRepaymentSchedule(@NotNull Credit credit) {
        this.credit = credit.newInstance(credit);
        creditCalculator = new CreditCalculator();
        monthsMap = createLoanRepaymentSchedule();
    }

    private Map<Integer, Month> createLoanRepaymentSchedule() {
        for (int i = 1; i <= credit.getCreditPeriod(); i++) {
            monthsMap.put(i, creditCalculator.calculateMonth(i));
        }
        return monthsMap;
    }

    @Override
    public Map<Integer, Month> getMonthsMap() {
        return Collections.unmodifiableMap(monthsMap);
    }

    @Override
    public void addAboveMonthlyPayment(int monthNumber, double aboveMonthlyPayment) {

        Month currentMonth = monthsMap.get(monthNumber);
        currentMonth = Month.newInstance(currentMonth, aboveMonthlyPayment, Precision.round(currentMonth.getBalanceOfPrincipal() - aboveMonthlyPayment, 2, BigDecimal.ROUND_HALF_UP));
        monthsMap.replace(monthNumber, currentMonth);

        recalculateMonthlyPayment();
    }

    private void recalculateMonthlyPayment() {
        for (int j = 1; j <= monthsMap.size(); j++) {
            Month currentMonth = monthsMap.get(j);

            if (currentMonth.getAboveMonthlyPayment() != 0) {
                creditCalculator.rebuildCreditCalculator(currentMonth);
                for (int i = ++j; i <= monthsMap.size(); i++) {
                    currentMonth = monthsMap.get(i);
                    monthsMap.replace(i, creditCalculator.recalculateMonth(currentMonth));
                }
            }
        }
    }

    private double calculateSum(ToDoubleFunction<Month> function) {
        return Precision.round(monthsMap.values().stream().mapToDouble(function).sum(), 2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public double repaymentOfPrincipalDebtForAllTime() {
        return calculateSum(month -> month.getRepaymentOfPrincipalDebt());
    }

    @Override
    public double repaymentInterestOnTheLoanForAllTime() {
        return calculateSum(month -> month.getInterestOnTheLoan());
    }

    @Override
    public double repaymentTotalMonthlyPaymentForAllTime() {
        return calculateSum(month -> month.getTotalMonthlyPayment());
    }

    @Override
    public double repaymentAboveMonthlyPaymentForAllTime() {
        return calculateSum(month -> month.getAboveMonthlyPayment());
    }

    public Credit getCredit() {
        return Credit.newInstance(credit);
    }

    public int size() {
        return monthsMap.size();
    }

    //region inner class where implement logic
    private class CreditCalculator {

        //region class fields
        //region constant fields
        private final static int ONE_HUNDRED_PERCENTS = 100;
        private final static double COUNT_MONTHS = 12;
        //endregion
        //region variables fields
        private double interestRatePerMonth;
        private double paymentPerScheduler;
        //endregion
        //endregion

        public CreditCalculator() {
            initializeFieldsCreditCalculator();
        }

        private void calculateInterestRatePerMonth() {
            interestRatePerMonth = credit.getInterestRate() / COUNT_MONTHS / ONE_HUNDRED_PERCENTS;
        }

        private void initializeFieldsCreditCalculator() {
            calculateInterestRatePerMonth();

            paymentPerScheduler = Precision.round(credit.getCurrentAmountOfCredit() *
                    (interestRatePerMonth + interestRatePerMonth / (Math.pow(1 + interestRatePerMonth, credit.getCurrentCreditPeriod()) - 1)), 2, BigDecimal.ROUND_HALF_UP);
        }

        private double calcRepaymentOfPrincipalDebt() {
            return Precision.round(credit.getCurrentAmountOfCredit() * interestRatePerMonth, 2, BigDecimal.ROUND_HALF_UP);
        }

        private double calcInterestOnTheLoan() {
            return paymentPerScheduler - calcRepaymentOfPrincipalDebt();
        }

        private double calcCurrentAmountOfCredit() {
            credit.setCurrentAmountOfCredit
                    (Precision.round(credit.getCurrentAmountOfCredit(), 2, BigDecimal.ROUND_HALF_UP) > Precision.round(calcInterestOnTheLoan(), 2, BigDecimal.ROUND_HALF_UP)
                            ? credit.getCurrentAmountOfCredit() - calcInterestOnTheLoan()
                            : 0.0);
            return credit.getCurrentAmountOfCredit();
        }

        public Month calculateMonth(int number) {
            credit.decrementCurrentCreditPeriod();
            return new Month(number, calcRepaymentOfPrincipalDebt(),
                    calcInterestOnTheLoan(), paymentPerScheduler,
                    calcCurrentAmountOfCredit());
        }

        private void rebuildCredit(Month currentMonth) {
            credit.setCurrentCreditPeriod(credit.getCreditPeriod() - currentMonth.getMonthNumber());
            credit.setCurrentAmountOfCredit(currentMonth.getBalanceOfPrincipal());
        }

        public void rebuildCreditCalculator(Month currentMonth) {
            rebuildCredit(currentMonth);
            initializeFieldsCreditCalculator();
        }

        public Month recalculateMonth(Month currentMonth) {

            return Month.newInstance(currentMonth,
                    calcInterestOnTheLoan(),
                    calcRepaymentOfPrincipalDebt(),
                    paymentPerScheduler,
                    calcCurrentAmountOfCredit() - currentMonth.getAboveMonthlyPayment());

        }

        public Credit getCredit() {
            return credit;
        }
    }
    //endregion
}
