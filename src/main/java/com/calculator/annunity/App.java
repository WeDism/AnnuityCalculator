package com.calculator.annunity;

import com.calculator.annunity.app.AnnuityCalculator;
import com.calculator.annunity.app.data.Credit;
import com.calculator.annunity.app.data.Month;
import com.calculator.annunity.app.implementation.LoanRepaymentSchedule;
import org.apache.commons.math3.util.Precision;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        System.out.print("Введите через пробел сумму кредита, кредитный период, процентную ставку: ");
        AnnuityCalculator annuityCalculator = new LoanRepaymentSchedule(new Credit(scanner.nextDouble(), scanner.nextInt(), scanner.nextInt()));
        Map<Integer, Month> monthsMap = annuityCalculator.getMonthsMap();

        String text =
                "Остаток осн. долга после совершения текущего платежа, руб: %s\n" +
                        "Общий ежемесячный платеж, руб: %s\n" +
                        "Платеж в счет погашения основного долга, руб: %s\n" +
                        "Проценты по кредиту, руб: %s";

        for (int i = 1; i <= annuityCalculator.size(); i++) {
            Month month = monthsMap.get(i);
            double paymentSum;

            do {
                System.out.println("\nМинимальный платеж: " + month.getTotalMonthlyPayment());
                System.out.print("Введите сумму платежа: ");
                paymentSum = scanner.nextDouble();
            }
            while (paymentSum < month.getTotalMonthlyPayment());

            if (paymentSum > month.getTotalMonthlyPayment()) {
                annuityCalculator.addAboveMonthlyPayment(i, Precision.round(paymentSum - month.getTotalMonthlyPayment(), 2, BigDecimal.ROUND_HALF_UP));
                month = monthsMap.get(i);
            }

            System.out.println("\n" + i + " месяц");
            System.out.println(String.format(text,
                    month.getBalanceOfPrincipal(),
                    month.getTotalMonthlyPayment(),
                    month.getRepaymentOfPrincipalDebt(),
                    month.getInterestOnTheLoan()));
        }

        System.out.println("\nВсего:");
        System.out.println(String.format(text +
                        "\nДосрочное погашение (сверх ежемесячного платежа), руб: %s",
                "0.0",
                annuityCalculator.repaymentTotalMonthlyPaymentForAllTime(),
                annuityCalculator.repaymentOfPrincipalDebtForAllTime(),
                annuityCalculator.repaymentInterestOnTheLoanForAllTime(),
                annuityCalculator.repaymentAboveMonthlyPaymentForAllTime()));
    }
}
