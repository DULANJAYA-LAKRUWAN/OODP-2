package com.lakruwan.interpreter;

/**
 * NonTerminalExpression represents a non-terminal node in the syntax tree.
 * Combines two Expression instances (expression1 and expression2) to evaluate
 * composite expressions according to the language rules.
 */
public class NonTerminalExpression implements Expression {

    /**
     * First operand / expression as specified in UML diagram (-expression1 : Expression).
     */
    private Expression expression1;

    /**
     * Second operand / expression as specified in UML diagram (-expression2 : Expression).
     */
    private Expression expression2;

    /**
     * Delimiter used to join results of the two expressions.
     * " " for Number-to-Text (e.g. "Nine" + " " + "Three")
     * "" for Text-to-Number (e.g. "9" + "" + "1")
     */
    private String delimiter;

    public NonTerminalExpression(Expression expression1, Expression expression2) {
        this(expression1, expression2, " ");
    }

    public NonTerminalExpression(Expression expression1, Expression expression2, String delimiter) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.delimiter = delimiter != null ? delimiter : "";
    }

    public Expression getExpression1() {
        return expression1;
    }

    public Expression getExpression2() {
        return expression2;
    }

    @Override
    public String interpret() {
        String res1 = expression1 != null ? expression1.interpret() : "";
        String res2 = expression2 != null ? expression2.interpret() : "";

        if (res1.isEmpty()) {
            return res2;
        }
        if (res2.isEmpty()) {
            return res1;
        }
        return res1 + delimiter + res2;
    }

    public int interpretAsInt() {
        String res = interpret().replaceAll("\\s+", "");
        try {
            return Integer.parseInt(res);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
