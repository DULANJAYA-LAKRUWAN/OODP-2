package com.lakruwan.servletFilter.chain;

import com.lakruwan.servletFilter.model.Request;

/**
 * Filter 2: Validates that the required parameter names ("username" and "password")
 * are present in the request query string.
 */
public class ParameterNameFilter extends Handler {
    private FilterCallback callback;

    public ParameterNameFilter() {
    }

    public ParameterNameFilter(FilterCallback callback) {
        this.callback = callback;
    }

    public void setCallback(FilterCallback callback) {
        this.callback = callback;
    }

    @Override
    public void handle(Request request) {
        System.out.println("[Filter 2: Parameter Names Check] Validating presence of 'username' and 'password' parameters...");

        boolean hasUsername = request.hasParameter("username");
        boolean hasPassword = request.hasParameter("password");

        if (hasUsername && hasPassword) {
            String msg = "Required parameter names ('username' and 'password') are present.";
            System.out.println("  ✓ SUCCESS: " + msg);
            if (callback != null) {
                callback.onFilterSuccess("Filter 2 (Parameter Names Check)", msg);
            }

            // Forward to next handler in the chain
            if (getHandler() != null) {
                getHandler().handle(request);
            }
        } else {
            StringBuilder missing = new StringBuilder();
            if (!hasUsername) missing.append("'username' ");
            if (!hasPassword) missing.append("'password' ");
            String errorMsg = "Missing required parameter(s): " + missing.toString().trim() + 
                              ". Available parameters: " + request.getParameterMap().keySet();
            System.out.println("  ✗ BLOCKED: " + errorMsg);
            if (callback != null) {
                callback.onFilterFailure("Filter 2 (Parameter Names Check)", errorMsg);
            }
        }
    }
}
