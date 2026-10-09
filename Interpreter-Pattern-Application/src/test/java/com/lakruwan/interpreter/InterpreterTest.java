package com.lakruwan.interpreter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest {

    @Test
    @DisplayName("Should correctly interpret number '9317' to 'Nine Three One Seven'")
    void testNumberToTextPracticalExample() {
        Expression expression = ExpressionParser.parseNumberToText("9317");
        assertNotNull(expression);
        assertEquals("Nine Three One Seven", expression.interpret());
    }

    @Test
    @DisplayName("Should correctly interpret text 'Nine One Four' to '914'")
    void testTextToNumberPracticalExample() {
        Expression expression = ExpressionParser.parseTextToNumber("Nine One Four");
        assertNotNull(expression);
        assertEquals("914", expression.interpret());
    }

    @Test
    @DisplayName("Should interpret single digit correctly")
    void testSingleDigit() {
        Expression expr = ExpressionParser.parseNumberToText("5");
        assertEquals("Five", expr.interpret());

        Expression exprText = ExpressionParser.parseTextToNumber("Five");
        assertEquals("5", exprText.interpret());
    }

    @Test
    @DisplayName("Should handle case insensitive number words")
    void testCaseInsensitivity() {
        Expression expr = ExpressionParser.parseTextToNumber("nine THREE zero one");
        assertEquals("9301", expr.interpret());
    }

    @Test
    @DisplayName("Should verify TerminalExpression fields and methods")
    void testTerminalExpression() {
        TerminalExpression term1 = new TerminalExpression(7);
        assertEquals(7, term1.getValue());
        assertEquals("Seven", term1.interpret());

        TerminalExpression term2 = new TerminalExpression("Eight", false);
        assertEquals(8, term2.getValue());
        assertEquals("8", term2.interpret());
    }

    @Test
    @DisplayName("Should verify NonTerminalExpression combining expressions")
    void testNonTerminalExpression() {
        Expression left = new TerminalExpression(1);
        Expression right = new TerminalExpression(2);
        NonTerminalExpression composite = new NonTerminalExpression(left, right, " ");
        assertEquals("One Two", composite.interpret());
        assertEquals(left, composite.getExpression1());
        assertEquals(right, composite.getExpression2());
    }

    @Test
    @DisplayName("Should throw exception on invalid number input")
    void testInvalidNumberInput() {
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parseNumberToText("93A7"));
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parseNumberToText(""));
    }

    @Test
    @DisplayName("Should throw exception on invalid text words")
    void testInvalidTextInput() {
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parseTextToNumber("Nine Hello Four"));
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parseTextToNumber(""));
    }
}
