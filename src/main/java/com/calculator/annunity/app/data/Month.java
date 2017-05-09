package com.calculator.annunity.app.data;

import org.jetbrains.annotations.NotNull;

/**
 * Created by WeDin on 27.04.2017.
 */
public class Month {
    //region class fields
    private int monthNumber;
    private double repaymentOfPrincipalDebt;
    private double interestOnTheLoan;
    //The balance of the main debt after the end of the current payment
    private double balanceOfPrincipal;
    private double totalMonthlyPayment;
    private double aboveMonthlyPayment;
    //endregion

    private void initialize(int monthNumber, double interestOnTheLoan, double repaymentOfPrincipalDebt, double totalMonthlyPayment, double balanceOfPrincipal) {
        this.monthNumber = monthNumber;
        this.repaymentOfPrincipalDebt = repaymentOfPrincipalDebt;
        this.interestOnTheLoan = interestOnTheLoan;
        this.totalMonthlyPayment = totalMonthlyPayment;
        this.balanceOfPrincipal = balanceOfPrincipal;

    }

    public Month(int monthNumber, double interestOnTheLoan, double repaymentOfPrincipalDebt, double totalMonthlyPayment, double balanceOfPrincipal) {
        initialize(monthNumber, interestOnTheLoan, repaymentOfPrincipalDebt, totalMonthlyPayment, balanceOfPrincipal);
        aboveMonthlyPayment = 0.0;
    }

    public Month(int monthNumber, double interestOnTheLoan, double repaymentOfPrincipalDebt, double totalMonthlyPayment, double balanceOfPrincipal, double aboveMonthlyPayment) {
        initialize(monthNumber, interestOnTheLoan, repaymentOfPrincipalDebt, totalMonthlyPayment, balanceOfPrincipal);
        this.aboveMonthlyPayment = aboveMonthlyPayment;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public double getRepaymentOfPrincipalDebt() {
        return repaymentOfPrincipalDebt;
    }

    public double getInterestOnTheLoan() {
        return interestOnTheLoan;
    }

    public double getBalanceOfPrincipal() {
        return balanceOfPrincipal;
    }

    public double getTotalMonthlyPayment() {
        return totalMonthlyPayment;
    }

    public double getAboveMonthlyPayment() {
        return aboveMonthlyPayment;
    }

    @NotNull
    public static Month newInstance(@NotNull Month month, double aboveMonthlyPayment, double balanceOfPrincipal) {
        return new Month(
                month.getMonthNumber(),
                month.getInterestOnTheLoan(),
                month.getRepaymentOfPrincipalDebt(),
                month.getTotalMonthlyPayment(),
                balanceOfPrincipal,
                aboveMonthlyPayment
        );
    }

    @NotNull
    public static Month newInstance(@NotNull Month month, double interestOnTheLoan, double repaymentOfPrincipalDebt, double totalMonthlyPayment, double balanceOfPrincipal) {
        return new Month(
                month.getMonthNumber(),
                repaymentOfPrincipalDebt,
                interestOnTheLoan,
                totalMonthlyPayment,
                balanceOfPrincipal,
                month.getAboveMonthlyPayment());
    }
}
