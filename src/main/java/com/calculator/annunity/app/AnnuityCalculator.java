package com.calculator.annunity.app;

import com.calculator.annunity.app.data.Month;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by WeDin on 28.04.2017.
 */
public interface AnnuityCalculator{
    Map<Integer, Month> getMonthsMap();

    double repaymentOfPrincipalDebtForAllTime();

    double repaymentInterestOnTheLoanForAllTime();

    double repaymentTotalMonthlyPaymentForAllTime();

    double repaymentAboveMonthlyPaymentForAllTime();

    void addAboveMonthlyPayment(int monthNumber, double aboveMonthlyPayment);

    int size();
}
