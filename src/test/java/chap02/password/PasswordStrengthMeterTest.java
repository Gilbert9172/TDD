package chap02.password;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordStrengthMeterTest {

    PasswordStrengthMeter meter = new PasswordStrengthMeter();

    @Test
    @DisplayName("강함 : 모든 조건을 충족")
    void meetsAllCriteriaThenStrong() throws Exception {
        assertStrength("ab12!@AB", PasswordStrength.STRONG);
    }

    @Test
    @DisplayName("보통 : 길이만 8글자 미만, 나머지 충족")
    public void meetsOthersCriteriaExceptForLengthThenNormal () throws Exception {
        assertStrength("ab12!@A", PasswordStrength.NORMAL);
    }

    @Test
    @DisplayName("보통 :숫자는 포함하지 않음, 나머지 충족")
    public void test() throws Exception {
        assertStrength("ab!@ABqwer", PasswordStrength.NORMAL);
    }

    private void assertStrength(String password, PasswordStrength expStr) {
        PasswordStrength result = meter.meter(password);
        assertEquals(expStr, result);
    }

}
