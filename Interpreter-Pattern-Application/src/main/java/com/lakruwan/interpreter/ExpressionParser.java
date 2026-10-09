package com.lakruwan.interpreter;

/**
 * ExpressionParser builds an Abstract Syntax Tree (AST) of Expressions
 * from input strings for evaluation by the Interpreter Pattern.
 */
public class ExpressionParser {

    /**
     * Parses a string of digits (e.g. "9317") into an Expression tree.
     * Calling interpret() evaluates to words: "Nine Three One Seven".
     *
     * @param numberInput the digit sequence (e.g., "9317")
     * @return the composite Expression root node
     */
    public static Expression parseNumberToText(String numberInput) {
        if (numberInput == null || numberInput.trim().isEmpty()) {
            throw new IllegalArgumentException("Please enter a number to convert.");
        }

        String cleaned = numberInput.trim();
        Expression root = null;

        for (int i = 0; i < cleaned.length(); i++) {
            char c = cleaned.charAt(i);
            if (Character.isWhitespace(c)) {
                continue;
            }
            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException("Invalid character '" + c + "'. Only digits (0-9) are allowed.");
            }

            Expression terminal = new TerminalExpression(String.valueOf(c), true);
            if (root == null) {
                root = terminal;
            } else {
                root = new NonTerminalExpression(root, terminal, " ");
            }
        }

        if (root == null) {
            throw new IllegalArgumentException("No digits found in input.");
        }

        return root;
    }

    /**
     * Parses a string of number words (e.g. "Nine One Four") into an Expression tree.
     * Calling interpret() evaluates to digits: "914".
     *
     * @param textInput the space-separated words (e.g., "Nine One Four")
     * @return the composite Expression root node
     */
    public static Expression parseTextToNumber(String textInput) {
        if (textInput == null || textInput.trim().isEmpty()) {
            throw new IllegalArgumentException("Please enter text words to convert.");
        }

        String[] tokens = textInput.trim().split("\\s+");
        Expression root = null;

        for (String token : tokens) {
            if (token.isEmpty()) {
                continue;
            }

            Expression terminal = new TerminalExpression(token, false);
            // Pre-validate that terminal resolves to a valid digit
            try {
                terminal.interpret();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unrecognized word: '" + token + "'. Use words like Zero, One, Two, ... Nine.");
            }

            if (root == null) {
                root = terminal;
            } else {
                root = new NonTerminalExpression(root, terminal, "");
            }
        }

        if (root == null) {
            throw new IllegalArgumentException("No valid number words found in input.");
        }

        return root;
    }
}
