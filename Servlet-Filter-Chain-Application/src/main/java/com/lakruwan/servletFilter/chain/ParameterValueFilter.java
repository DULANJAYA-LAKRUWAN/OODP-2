package com.lakruwan.servletFilter.chain;

import com.lakruwan.servletFilter.model.Request;

/**
 * Filter 3: Validates the parameter values:
 * username must equal "abc" and password must equal "123".
 */
public class ParameterValueFilter extends Handler {
    private final String expectedUsername;
    private final String expectedPassword;
    private FilterCallback callback;

    public ParameterValueFilter() {
        this("abc", "123");
    }

    public ParameterValueFilter(String expectedUsername, String expectedPassword) {
        this.expectedUsername = expectedUsername;
        this.expectedPassword = expectedPassword;
    }

    public ParameterValueFilter(String expectedUsername, String expectedPassword, FilterCallback callback) {
        this.expectedUsername = expectedUsername;
        this.expectedPassword = expectedPassword;
        this.callback = callback;
    }

    public void setCallback(FilterCallback callback) {
        this.callback = callback;
    }

    @Override
    public void handle(Request request) {
        System.out.println("[Filter 3: Parameter Values Check] Authenticating parameter values...");

        String actualUsername = request.getParameter("username");
        String actualPassword = request.getParameter("password");

        boolean userMatch = expectedUsername.equals(actualUsername);
        boolean passMatch = expectedPassword.equals(actualPassword);

        if (userMatch && passMatch) {
            String msg = "Credentials verified successfully (username='" + actualUsername + "', password='***').";
            System.out.println("  ✓ SUCCESS: " + msg);
            if (callback != null) {
                callback.onFilterSuccess("Filter 3 (Parameter Values Check)", msg);
                callback.onRequestComplete(request.getUrl(), "Target servlet/JSP dispatched! Request processing complete for: " + request.getUrl());
            }

            // If there are further downstream filters or target handlers
            if (getHandler() != null) {
                getHandler().handle(request);
            } else {
                System.out.println(">>> [TARGET DISPATCHED]: Access granted! Forwarding to: " + request.getUrl() + " <<<");
            }
        } else {
            String errorMsg = "Authentication failed! Invalid username or password.";
            if (!userMatch) {
                errorMsg += " (Username '" + actualUsername + "' does not match expected '" + expectedUsername + "')";
            } else {
                errorMsg += " (Incorrect password)";
            }
            System.out.println("  ✗ BLOCKED: " + errorMsg);
            if (callback != null) {
                callback.onFilterFailure("Filter 3 (Parameter Values Check)", errorMsg);
            }
        }
    }
}
