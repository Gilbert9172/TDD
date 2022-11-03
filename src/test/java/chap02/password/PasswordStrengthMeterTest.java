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

    @Test
    @DisplayName("불가 : 값이 없는 경우")
    void nullInputThenInvalid() throws Exception {
        assertStrength(null, PasswordStrength.INVALID);
    }

    @Test
    @DisplayName("보통 : 대문자를 포함하지 않음, 나머지 충족")
    void meetsOtherCriteriaExceptForUppercaseThenNormal() throws Exception {
        assertStrength("ab12!@df", PasswordStrength.NORMAL);
    }

    @Test
    @DisplayName("약함 : 길이가 8글자 이상인 조건만 충족")
    void meetsOnlyLengthCriteriaThenWeak() throws Exception {
        assertStrength("abdefghi", PasswordStrength.WEAK);
    }

    @Test
    @DisplayName("약함 : 숫자 포함 조건만 충족")
    void meetsOnlyNumCriteriaThenWeak() throws Exception {
        assertStrength("ABZEF", PasswordStrength.WEAK);
    }

    @Test
    @DisplayName("약함 : 대문자 포함 조건만 충족")
    void meetsOnlyUpperCriteriaThenWeak() throws Exception {
        assertStrength("ABZEF", PasswordStrength.WEAK);
    }

    @Test
    @DisplayName("약함 : 아무 조건도 충족하지 않음")
    void meetsNoCriteriaThenWeak() throws Exception {
        assertStrength("abc", PasswordStrength.WEAK);
    }

    private void assertStrength(String password, PasswordStrength expStr) throws Exception{
        PasswordStrength result = meter.meter(password);
        assertEquals(expStr, result);
    }

}
