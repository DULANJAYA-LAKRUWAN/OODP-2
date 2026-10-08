package com.lakruwan.servletFilter.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the incoming HTTP Request object in the servlet filter mechanism.
 * Encapsulates the URL and query parameters.
 */
public class Request {
    private final String url;
    private final String parameters;
    private final Map<String, String> parameterMap;

    public Request(String url, String parameters) {
        this.url = (url != null) ? url.trim() : "";
        this.parameters = (parameters != null) ? parameters.trim() : "";
        this.parameterMap = parseParameters(this.parameters);
    }

    private Map<String, String> parseParameters(String rawParams) {
        if (rawParams == null || rawParams.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, String> map = new HashMap<>();
        String[] pairs = rawParams.split("&");
        for (String pair : pairs) {
            if (pair.isEmpty()) continue;
            int idx = pair.indexOf('=');
            if (idx > 0) {
                String key = pair.substring(0, idx).trim();
                String value = (idx < pair.length() - 1) ? pair.substring(idx + 1).trim() : "";
                map.put(key, value);
            } else {
                map.put(pair.trim(), "");
            }
        }
        return Collections.unmodifiableMap(map);
    }

    public String getUrl() {
        return url;
    }

    public String getParameters() {
        return parameters;
    }

    public boolean hasParameter(String name) {
        return parameterMap.containsKey(name);
    }

    public String getParameter(String name) {
        return parameterMap.get(name);
    }

    public Map<String, String> getParameterMap() {
        return parameterMap;
    }

    @Override
    public String toString() {
        return "Request{url='" + url + "', parameters='" + parameters + "'}";
    }
}
