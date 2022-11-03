package chap02.password;


public class PasswordStrengthMeter {

    public PasswordStrengthMeter() {
    }

    public PasswordStrength meter(String s) {

        if (s == null || s.equals("")) return PasswordStrength.INVALID;
        int metCounts = getMetCriteriaCounts(s);
        if (metCounts <= 1) return PasswordStrength.WEAK;
        if (metCounts == 2) return PasswordStrength.NORMAL;
        return PasswordStrength.STRONG;
    }

    private boolean meetContainingNumberCriteria(String s) {
        for (char ch : s.toCharArray()) {
            if (ch >= '0' && ch <= '9') {
                return true;
            }
        }
        return false;
    }

    private boolean meetsContainingUppercaseCriteria(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private int getMetCriteriaCounts(String s) {
        int metCounts = 0;

        // 길이가 8자리 이상인지.
        if (s.length() >= 8) metCounts++;

        // 숫자를 포함하고 있는지
        if (meetContainingNumberCriteria(s)) metCounts++;

        // 대문자를 포함하고 있는지
        if (meetsContainingUppercaseCriteria(s)) metCounts++;

        return metCounts;
    }
}
