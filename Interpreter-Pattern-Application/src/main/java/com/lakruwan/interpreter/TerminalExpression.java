package com.lakruwan.interpreter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * TerminalExpression represents a leaf node in the syntax tree.
 * Implements the interpret method to evaluate terminal symbols:
 * 1. Number to Text: Evaluates a single digit character (e.g. '9' -> "Nine").
 * 2. Text to Number: Evaluates a single number word (e.g. "Nine" -> "9").
 */
public class TerminalExpression implements Expression {

    /**
     * Integer value of the terminal symbol as specified in the UML diagram (-value : int).
     */
    private int value;

    /**
     * String representation of the token (e.g. "9", "Nine").
     */
    private String textValue;

    /**
     * Flag indicating conversion mode: true for Number -> Text, false for Text -> Number.
     */
    private boolean isNumberToText;

    private static final Map<String, String> DIGIT_TO_WORD_MAP;
    private static final Map<String, String> WORD_TO_DIGIT_MAP;

    static {
        Map<String, String> d2w = new HashMap<>();
        d2w.put("0", "Zero");
        d2w.put("1", "One");
        d2w.put("2", "Two");
        d2w.put("3", "Three");
        d2w.put("4", "Four");
        d2w.put("5", "Five");
        d2w.put("6", "Six");
        d2w.put("7", "Seven");
        d2w.put("8", "Eight");
        d2w.put("9", "Nine");
        DIGIT_TO_WORD_MAP = Collections.unmodifiableMap(d2w);

        Map<String, String> w2d = new HashMap<>();
        for (Map.Entry<String, String> entry : d2w.entrySet()) {
            w2d.put(entry.getValue().toLowerCase(), entry.getKey());
        }
        WORD_TO_DIGIT_MAP = Collections.unmodifiableMap(w2d);
    }

    /**
     * Constructor using integer value (Number to Text).
     *
     * @param value integer digit (0-9)
     */
    public TerminalExpression(int value) {
        this.value = value;
        this.textValue = String.valueOf(value);
        this.isNumberToText = true;
    }

    /**
     * Constructor using string representation and conversion direction.
     *
     * @param textValue      the token string
     * @param isNumberToText true if converting digit to word, false if word to digit
     */
    public TerminalExpression(String textValue, boolean isNumberToText) {
        this.textValue = textValue != null ? textValue.trim() : "";
        this.isNumberToText = isNumberToText;
        if (isNumberToText) {
            try {
                this.value = Integer.parseInt(this.textValue);
            } catch (NumberFormatException e) {
                this.value = -1;
            }
        } else {
            String digit = WORD_TO_DIGIT_MAP.get(this.textValue.toLowerCase());
            if (digit != null) {
                this.value = Integer.parseInt(digit);
            } else {
                this.value = -1;
            }
        }
    }

    public int getValue() {
        return value;
    }

    public String getTextValue() {
        return textValue;
    }

    public boolean isNumberToText() {
        return isNumberToText;
    }

    @Override
    public String interpret() {
        if (isNumberToText) {
            String word = DIGIT_TO_WORD_MAP.get(textValue);
            if (word != null) {
                return word;
            }
            throw new IllegalArgumentException("Unknown digit: " + textValue);
        } else {
            String digit = WORD_TO_DIGIT_MAP.get(textValue.toLowerCase());
            if (digit != null) {
                return digit;
            }
            throw new IllegalArgumentException("Unknown number word: '" + textValue + "'");
        }
    }

    public int interpretAsInt() {
        return value;
    }
}
