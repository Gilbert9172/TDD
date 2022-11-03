package chap02.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCalculator {

    @Test
    @DisplayName("더하기")
    void plus() throws Exception {
        int result = Calculator.plus(1, 2);
        assertEquals(3, result);
    }

}
