package com.lakruwan.servletFilter.chain;

import com.lakruwan.servletFilter.model.Request;

/**
 * Filter 1: Validates that the requested URL targets a valid .jsp resource.
 */
public class JspValidationFilter extends Handler {
    private FilterCallback callback;

    public JspValidationFilter() {
    }

    public JspValidationFilter(FilterCallback callback) {
        this.callback = callback;
    }

    public void setCallback(FilterCallback callback) {
        this.callback = callback;
    }

    @Override
    public void handle(Request request) {
        String url = request.getUrl();
        System.out.println("\n[Filter 1: JSP Validation] Checking URL: " + url);

        // Check if URL ends with or points to a .jsp file
        if (url != null && (url.toLowerCase().endsWith(".jsp") || url.toLowerCase().contains(".jsp?"))) {
            String msg = "URL targets a valid .jsp resource: " + url;
            System.out.println("  ✓ SUCCESS: " + msg);
            if (callback != null) {
                callback.onFilterSuccess("Filter 1 (.jsp Validation)", msg);
            }

            // Forward to next handler in the chain
            if (getHandler() != null) {
                getHandler().handle(request);
            }
        } else {
            String errorMsg = "Invalid resource requested. URL must target a .jsp page (Given: '" + url + "').";
            System.out.println("  ✗ BLOCKED: " + errorMsg);
            if (callback != null) {
                callback.onFilterFailure("Filter 1 (.jsp Validation)", errorMsg);
            }
        }
    }
}
