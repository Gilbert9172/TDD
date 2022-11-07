package chap02.bank;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ExpiryDateCalculatorTest {

    @Test
    @DisplayName("만원 납부하면 한달 뒤가 만료일이 됨.")
    void test1() throws Exception {

        assertExpiryDate(
                PayDate.builder()
                .billingDate(LocalDate.of(2019,3,1))
                .payAmount(10000).build(),
                LocalDate.of(2019, 4,1)
        );
    }

    @Test
    @DisplayName("납부일과 한달 뒤 일자가 같지 않음.")
    public void test2() throws Exception {

        assertExpiryDate(
                PayDate.builder()
                        .billingDate(LocalDate.of(2019,1,31))
                        .payAmount(10000).build(),
                LocalDate.of(2019, 2,28)
        );
    }

    @Test
    @DisplayName("첫 납부일 != 만료일의 일자인데 1만원 납부 → 첫 납부일 기준으로 다음 만료일 정함.")
    public void test3() throws Exception {

        PayDate payDate = PayDate.builder()
                .firstBillingDate(LocalDate.of(2019,1,31))
                .billingDate(LocalDate.of(2019,2,28))
                .payAmount(10000).build();

        PayDate payDate2 = PayDate.builder()
                .firstBillingDate(LocalDate.of(2019,1,30))
                .billingDate(LocalDate.of(2019,2,28))
                .payAmount(10000).build();

        PayDate payDate3 = PayDate.builder()
                .firstBillingDate(LocalDate.of(2019,5,31))
                .billingDate(LocalDate.of(2019,6,30))
                .payAmount(10000).build();

        assertExpiryDate(payDate, LocalDate.of(2019, 3,31));
        assertExpiryDate(payDate2, LocalDate.of(2019, 3,30));
        assertExpiryDate(payDate3, LocalDate.of(2019, 7,31));

    }

    @Test
    @DisplayName("2만원 이상 납부하면 비례해서 만료일 계산")
    public void test4() throws Exception {

        PayDate payDate = PayDate.builder()
                .billingDate(LocalDate.of(2019,3,1))
                .payAmount(20000).build();

        PayDate payDate2 = PayDate.builder()
                .billingDate(LocalDate.of(2019,3,1))
                .payAmount(50000).build();

        assertExpiryDate(payDate, LocalDate.of(2019, 5,1));
        assertExpiryDate(payDate2, LocalDate.of(2019, 8,1));
    }

    @Test
    @DisplayName("첫 납부일과 만료일 일자가 다를 때 2만원 이상 납부")
    public void test5() throws Exception {
        PayDate payDate = PayDate.builder()
                .firstBillingDate(LocalDate.of(2019,1,31))
                .billingDate(LocalDate.of(2019,2,28))
                .payAmount(20000).build();

        PayDate payDate2 = PayDate.builder()
                .firstBillingDate(LocalDate.of(2019,1,31))
                .billingDate(LocalDate.of(2019,2,28))
                .payAmount(40000).build();

        PayDate payDate3 = PayDate.builder()
                .firstBillingDate(LocalDate.of(2019,3,31))
                .billingDate(LocalDate.of(2019,4,30))
                .payAmount(30000).build();

        assertExpiryDate(payDate, LocalDate.of(2019, 4,30));
        assertExpiryDate(payDate2, LocalDate.of(2019, 6,30));
        assertExpiryDate(payDate3, LocalDate.of(2019, 7,31));
    }

    @Test
    @DisplayName("10만원을 납부하면 1년 제공")
    public void test6() throws Exception {
        PayDate payDate1 = PayDate.builder()
                .billingDate(LocalDate.of(2019,1,28))
                .payAmount(100000).build();

        assertExpiryDate(payDate1, LocalDate.of(2020, 1,28));
    }

    @Test
    @DisplayName("10만원 이상")
    public void test7() throws Exception {
        PayDate payDate1 = PayDate.builder()
                .billingDate(LocalDate.of(2019,1,28))
                .payAmount(200000).build();

        assertExpiryDate(payDate1, LocalDate.of(2021, 1,28));
    }

    private void assertExpiryDate(PayDate payDate, LocalDate expectedExpiryDate) {

        ExpiryDateCalculator calculator = new ExpiryDateCalculator();
        LocalDate realExpiryDate = calculator.calculateExpiryDate(payDate);
        assertEquals(expectedExpiryDate, realExpiryDate);
    }

}
