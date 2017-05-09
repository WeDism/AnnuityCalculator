package com.calculator.annunity.app.data;

import org.jetbrains.annotations.NotNull;

/**
 * Created by WeDin on 27.04.2017.
 */
public class Credit {
    //region class fields
    private double amountOfCredit;
    private double currentAmountOfCredit;
    private int creditPeriod;
    private int currentCreditPeriod;
    private int interestRate;
    //endregion

    private void initialize(double amountOfCredit, int creditPeriod, int interestRate) {
        this.amountOfCredit = amountOfCredit;
        this.creditPeriod = creditPeriod;
        this.interestRate = interestRate;

    }

    /**
     * Конструктор для начальной инициализации кредита
     * @param amountOfCredit сумма кредита
     * @param creditPeriod кредитный период
     * @param interestRate годовая процентная ставка
     */
    public Credit(double amountOfCredit, int creditPeriod, int interestRate) {
        initialize(amountOfCredit, creditPeriod, interestRate);
        this.currentAmountOfCredit = amountOfCredit;
        this.currentCreditPeriod = creditPeriod;
    }

    /**
     * Конструктор для внутренней работы <b>{@link com.calculator.annunity.app.logic.CreditCalculator}</b>
     * @param amountOfCredit сумма кредита
     * @param currentAmountOfCredit текущая сумма кредита
     * @param creditPeriod кредитный период
     * @param currentCreditPeriod текущий кредитный период
     * @param interestRate годовая процентная ставка
     */
    public Credit(double amountOfCredit, double currentAmountOfCredit, int creditPeriod, int currentCreditPeriod, int interestRate) {
        initialize(amountOfCredit, creditPeriod, interestRate);
        this.currentAmountOfCredit = currentAmountOfCredit;
        this.currentCreditPeriod = currentCreditPeriod;
    }

    @NotNull
    public static Credit newInstance(@NotNull Credit credit) {
        return new Credit(
                credit.amountOfCredit,
                credit.currentAmountOfCredit,
                credit.creditPeriod,
                credit.currentCreditPeriod,
                credit.interestRate);
    }

    public double getAmountOfCredit() {
        return amountOfCredit;
    }

    public double getCurrentAmountOfCredit() {
        return currentAmountOfCredit;
    }

    public void setCurrentAmountOfCredit(double currentAmountOfCredit) {
        this.currentAmountOfCredit = currentAmountOfCredit;
    }

    public int getCreditPeriod() {
        return creditPeriod;
    }

    public int getCurrentCreditPeriod() {
        return currentCreditPeriod;
    }

    public void setCurrentCreditPeriod(int currentCreditPeriod) {
        this.currentCreditPeriod = currentCreditPeriod;
    }

    public void decrementCurrentCreditPeriod() {
        currentCreditPeriod--;
    }

    public int getInterestRate() {
        return interestRate;
    }

}
