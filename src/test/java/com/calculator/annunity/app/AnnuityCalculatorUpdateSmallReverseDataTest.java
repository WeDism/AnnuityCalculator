package com.calculator.annunity.app;

import com.calculator.annunity.app.data.Credit;
import com.calculator.annunity.app.implementation.LoanRepaymentSchedule;
import org.junit.Test;

import static com.calculator.annunity.helper.AnnuityCalculatorTestHelper.assertEquals;

/**
 * Created by WeDin on 28.04.2017.
 */

public class AnnuityCalculatorUpdateSmallReverseDataTest {

    //region class fields
    AnnuityCalculator loanRepaymentSchedule;
    //region instants fields
    //endregion
    //region constant fields
    private static final long ABOVE_MONTHLY_PAYMENT_100L = 100L;
    private static final long ABOVE_MONTHLY_PAYMENT_200L = 200L;
    private static final long AMOUNT_OF_CREDIT = 1_000L;
    //endregion
    //region calculable fields
    private static final long ABOVE_MONTHLY_PAYMENT_FOR_ALL_TIME
            = ABOVE_MONTHLY_PAYMENT_100L
            + ABOVE_MONTHLY_PAYMENT_200L;

    private static final long REPAYMENT_OF_PRINCIPAL_DEBT_FOR_ALL_TIME
            = AMOUNT_OF_CREDIT
            - ABOVE_MONTHLY_PAYMENT_100L
            - ABOVE_MONTHLY_PAYMENT_200L;
    //endregion
    //endregion

    public AnnuityCalculatorUpdateSmallReverseDataTest() {
        loanRepaymentSchedule = new LoanRepaymentSchedule(new Credit(AMOUNT_OF_CREDIT, 5, 12));
        loanRepaymentSchedule.addAboveMonthlyPayment(4, ABOVE_MONTHLY_PAYMENT_100L);
        loanRepaymentSchedule.addAboveMonthlyPayment(2, ABOVE_MONTHLY_PAYMENT_200L);
    }

    @Test
    public void getMonthsMap() throws Exception {
        assertEquals("Size map is: %s, but expected %s", loanRepaymentSchedule.getMonthsMap().size(), 5L);
    }

    @Test
    public void repaymentOfPrincipalDebtForAllTime() throws Exception {
        assertEquals("Repayment of principal debt for all time is: %s, but expected %s",
                loanRepaymentSchedule.repaymentOfPrincipalDebtForAllTime(), REPAYMENT_OF_PRINCIPAL_DEBT_FOR_ALL_TIME);
    }

    @Test
    public void repaymentInterestOnTheLoanForAllTime() throws Exception {
        assertEquals("Interest on the loan for all time is: %s, but expected %s",
                loanRepaymentSchedule.repaymentInterestOnTheLoanForAllTime(), 25L);
    }

    @Test
    public void repaymentTotalMonthlyPaymentForAllTime() throws Exception {
        assertEquals("Total monthly payment for all time is: %s, but expected %s",
                loanRepaymentSchedule.repaymentTotalMonthlyPaymentForAllTime(), 725L);
    }

    @Test
    public void repaymentAboveMonthlyPaymentForAllTime() throws Exception {
        assertEquals("Above monthly payment for all time is: %s, but expected %s",
                loanRepaymentSchedule.repaymentAboveMonthlyPaymentForAllTime(), ABOVE_MONTHLY_PAYMENT_FOR_ALL_TIME);
    }

}