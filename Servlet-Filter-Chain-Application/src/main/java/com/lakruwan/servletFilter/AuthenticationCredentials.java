package com.lakruwan.servletFilter;

/**
 * Authentication values supplied by the runtime environment.
 */
public final class AuthenticationCredentials {
    private static final String USERNAME_VARIABLE = "SERVLET_FILTER_USERNAME";
    private static final String PASSWORD_VARIABLE = "SERVLET_FILTER_PASSWORD";

    private final String username;
    private final String password;

    private AuthenticationCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static AuthenticationCredentials fromEnvironment() {
        String username = System.getenv(USERNAME_VARIABLE);
        String password = System.getenv(PASSWORD_VARIABLE);
        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            throw new IllegalStateException(
                    "Set non-empty " + USERNAME_VARIABLE + " and " + PASSWORD_VARIABLE + " environment variables.");
        }
        return new AuthenticationCredentials(username, password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
