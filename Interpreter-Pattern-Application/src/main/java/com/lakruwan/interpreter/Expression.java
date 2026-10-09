package com.lakruwan.interpreter;

/**
 * Abstract Expression interface for the Interpreter Design Pattern.
 * Defines the contract for interpreting elements in the language grammar.
 */
public interface Expression {
    /**
     * Interprets the expression and returns the resulting evaluated string.
     *
     * @return the interpreted evaluation result
     */
    String interpret();
}
