# Interpreter Pattern Application - Practical 07

This project implements the **Interpreter Design Pattern** in Java with a Swing Graphical User Interface, based on the requirements of **Practical 07**.

---

## 📌 Problem Specification

The **Interpreter Pattern** is a behavioral design pattern that defines a grammatical representation for a language and provides an interpreter to deal with this grammar.

### Requirements:
1. Interpret **Number to Text**:
   - **Input:** `9317`
   - **Output:** `Nine Three One Seven`
2. Interpret **Text to Number**:
   - **Input:** `Nine One Four`
   - **Output:** `914`
3. Implement the UML Class Diagram:
   - `<<interface>> Expression` with `+interpret()`
   - `TerminalExpression` with `-value: int` and `+interpret()`
   - `NonTerminalExpression` with `-expression1: Expression`, `-expression2: Expression`, and `+interpret()`
4. Provide a desktop GUI with Java Swing matching the practical UI screenshot.

---

## 🏛️ Class Diagram & Architecture

```
                 +---------------------------+
                 |       <<interface>>       |
                 |        Expression         |
                 +---------------------------+
                 | +interpret() : String     |
                 +---------------------------+
                               ^
                               |
               +---------------+---------------+
               |                               |
 +-----------------------------+ +-----------------------------+
 |    NonTerminalExpression    | |     TerminalExpression      |
 +-----------------------------+ +-----------------------------+
 | -expression1 : Expression   | | -value : int                |
 | -expression2 : Expression   | | -textValue : String         |
 | -delimiter : String         | | -isNumberToText : boolean   |
 +-----------------------------+ +-----------------------------+
 | +interpret() : String       | | +interpret() : String       |
 | +interpretAsInt() : int     | | +getValue() : int           |
 +-----------------------------+ +-----------------------------+
```

### Components:
- **`Expression`**: The common interface defining the `interpret()` method.
- **`TerminalExpression`**: Evaluates individual tokens:
  - For digits (e.g., `'9'`), returns `"Nine"`.
  - For number words (e.g., `"Nine"`), returns `"9"`.
- **`NonTerminalExpression`**: Combines two expressions (`expression1` and `expression2`) with a delimiter:
  - For Number-to-Text: Joins words with a space `" "` (`"Nine Three"`).
  - For Text-to-Number: Concatenates digits `""` (`"91"`).
- **`ExpressionParser`**: Parses raw string inputs into an Abstract Syntax Tree (AST) of Expressions.
- **`InterpreterUI`**: Java Swing GUI with turquoise / cyan theme and emerald buttons matching the practical screenshot.
- **`InterpreterApp`**: Main entry point displaying console demonstrations and launching the GUI.

---

## 🚀 How to Run

### Run using Maven:
```bash
mvn clean compile exec:java -Dexec.mainClass="com.lakruwan.interpreter.InterpreterApp"
```

### Run tests:
```bash
mvn test
```

### Run using Java directly:
```bash
mvn clean package
java -jar target/Interpreter-Pattern-Application-1.0.jar
```
