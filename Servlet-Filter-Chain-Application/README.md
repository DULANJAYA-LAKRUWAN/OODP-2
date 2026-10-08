# Servlet Filter Simulation — Chain of Responsibility Pattern 🚀

**Practical 05 — Object-Oriented Design Patterns 2 (OODP 2)**  
*Java Institute for Advanced Technology (JIAT)*

---

## 📌 Overview

This project implements a simulated **Servlet Filter Mechanism** using the **Chain of Responsibility Design Pattern**.  
In web servers (like Apache Tomcat, Jetty, and Java EE / Jakarta EE Servlets), incoming HTTP requests pass through a sequence of pre-processing filters before reaching the destination servlet or JSP page.

Each filter has the opportunity to:
1. Intercept and inspect the request.
2. Validate specific criteria.
3. Pass the request along to the next filter in the chain if validation succeeds.
4. Terminate processing immediately and reject the request if validation fails.

---

## 📐 UML Class Diagram

```
                +------------------------------------+
                |              Request               |
                +------------------------------------+
                | -url : String                      |
                | -parameters : String               |
                | -parameterMap : Map<String,String> |
                +------------------------------------+
                | +getUrl() : String                 |
                | +getParameters() : String          |
                | +hasParameter(name : String) : bool|
                | +getParameter(name : String):String|
                +------------------------------------+
                                  ^
                                  | uses
                                  |
                +------------------------------------+
                |              Handler               |
                +------------------------------------+
                | -handler : Handler                 |
                +------------------------------------+
                | +setHandler(handler : Handler)     |
                | +getHandler() : Handler            |
                | +handle(request : Request)*        |
                +------------------------------------+
                   ▲              ▲              ▲
                   |              |              |
      +------------+       +------+       +------+------------+
      |                    |                                  |
+--------------------+ +--------------------------+ +-------------------------+
|JspValidationFilter | |   ParameterNameFilter    | |  ParameterValueFilter   |
|     (Filter 1)     | |        (Filter 2)        | |       (Filter 3)        |
+--------------------+ +--------------------------+ +-------------------------+
| +handle(request)   | | +handle(request)         | | +handle(request)        |
+--------------------+ +--------------------------+ +-------------------------+
```

---

## 🔄 Filter Processing Pipeline

```
Incoming Request
 (url, parameters)
        │
        ▼
┌──────────────────┐
│     Filter 1     │  Checks if URL ends with ".jsp"
│  .jsp Validation │  ❌ Fail: Reject request with 400/404 error
└─────────┬────────┘
          │ (Pass)
          ▼
┌──────────────────┐
│     Filter 2     │  Verifies both "username" and "password"
│ Parameter Names  │  parameter names exist in query string
└─────────┬────────┘  ❌ Fail: Reject missing required parameters
          │ (Pass)
          ▼
┌──────────────────┐
│     Filter 3     │  Validates parameter values:
│ Parameter Values │  username == "abc" && password == "123"
└─────────┬────────┘  ❌ Fail: Reject with 401 Unauthorized
          │ (Pass)
          ▼
┌──────────────────┐
│  Target Servlet  │  Access Granted!
│  or JSP Page     │  Request processed successfully.
└──────────────────┘
```

---

## 📁 Project Structure

```
Servlet-Filter-Chain-Application/
├── pom.xml                                   # Maven configuration (Java 8-21 compatible)
├── README.md                                 # Comprehensive documentation & Viva Q&A
└── src/
    ├── main/java/com/lakruwan/servletFilter/
    │   ├── model/
    │   │   └── Request.java                  # HTTP Request representation
    │   ├── chain/
    │   │   ├── Handler.java                  # Abstract Handler (matches UML specification)
    │   │   ├── FilterCallback.java           # Observer callback for live UI updates
    │   │   ├── JspValidationFilter.java      # Filter 1: .jsp resource validator
    │   │   ├── ParameterNameFilter.java      # Filter 2: Parameter presence validator
    │   │   └── ParameterValueFilter.java     # Filter 3: Authentication credential validator
    │   ├── ui/
    │   │   └── ServletFilterUI.java          # Interactive Swing Graphical User Interface
    │   └── ServletFilterApp.java             # Console test suite with 4 scenarios
    └── test/java/com/lakruwan/servletFilter/
        └── FilterChainTest.java              # JUnit 5 automated test suite
```

---

## 🚀 How to Run

### 1. Run Automated Console Tests
```bash
mvn compile
java -cp target/classes com.lakruwan.servletFilter.ServletFilterApp
```

### 2. Run Interactive Swing GUI
```bash
java -cp target/classes com.lakruwan.servletFilter.ui.ServletFilterUI
```

### 3. Run JUnit Test Suite
```bash
mvn test
```

---

## 🎓 Viva Voce (Oral Defense) Preparation

### Q1: What is the Intent of the Chain of Responsibility Pattern?
> **Answer**: It decouples the sender of a request from its receivers by giving more than one object the chance to handle the request. We link the receiving objects in a chain and pass the request along the chain until an object handles it or rejects it.

### Q2: Why is Chain of Responsibility ideal for Servlet Filters?
> **Answer**: 
> 1. **Open-Closed Principle (OCP)**: New filters (e.g., LoggingFilter, RateLimitingFilter, CSRFFilter) can be added or existing ones removed without altering the client code or other filters.
> 2. **Single Responsibility Principle (SRP)**: Each filter focuses on a single distinct concern (Filter 1 handles URL validation, Filter 2 verifies query parameters, Filter 3 checks authentication).
> 3. **Dynamic Reordering**: The sequence of execution can be easily configured or adjusted at runtime.

### Q3: What happens if a filter in the chain fails?
> **Answer**: The failing filter halts further propagation by **not** invoking `getHandler().handle(request)`. This creates a short-circuit rejection mechanism essential for web security and input validation.

---

© 2026 Lakruwan | JIAT OODP 2
