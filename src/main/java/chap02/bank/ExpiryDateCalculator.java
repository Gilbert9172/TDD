package chap02.bank;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {

    final int payPerYear = 100000;
    final int payPerMonth = 10000;


    public ExpiryDateCalculator() {
    }

    public LocalDate calculateExpiryDate(PayDate payDate) {

        int addedMonths = calculateAddedMonths(payDate.getPayAmount());

        if (payDate.getFirstBillingDate() != null) {
            return expiryDateUsingFirstBillingDate(payDate, addedMonths);
        } else {
            return payDate.getBillingDate().plusMonths(addedMonths);
        }
    }

    private LocalDate expiryDateUsingFirstBillingDate(PayDate payDate, int addedMonths) {
        LocalDate candidateExp = payDate.getBillingDate().plusMonths(addedMonths);
        int dayOfFirstBilling = payDate.getFirstBillingDate().getDayOfMonth();

        if (dayOfFirstBilling != candidateExp.getDayOfMonth()) {
            int dayLenOfCandiMon = YearMonth.from(candidateExp).lengthOfMonth();
            int dayLenOfFirstBilling = payDate.getFirstBillingDate().getDayOfMonth();

            if (dayLenOfCandiMon < dayLenOfFirstBilling) {
                return candidateExp.withDayOfMonth(dayLenOfCandiMon);
            }
            return candidateExp.withDayOfMonth(payDate.getFirstBillingDate().getDayOfMonth());
        } else {
            return candidateExp;
        }
    }

    private int calculateAddedMonths(int payAmount) {
        int addedMonths;

        if (payAmount >= payPerYear) {
            addedMonths = (payAmount / payPerYear) * 12 + ((payAmount % payPerYear) / payPerMonth);
        } else {
            addedMonths = payAmount / payPerMonth;
        }
        return addedMonths;
    }

}
