package com.lakruwan.servletFilter.chain;

/**
 * Callback interface to notify observers (such as Swing UI or test runners)
 * of the progress and outcome of each filter in the chain.
 */
public interface FilterCallback {
    void onFilterSuccess(String filterName, String message);
    void onFilterFailure(String filterName, String errorMessage);
    void onRequestComplete(String targetUrl, String message);
}
