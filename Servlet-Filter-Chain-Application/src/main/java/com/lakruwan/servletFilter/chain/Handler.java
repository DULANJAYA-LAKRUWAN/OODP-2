package com.lakruwan.servletFilter.chain;

import com.lakruwan.servletFilter.model.Request;

/**
 * Abstract Handler for the Chain of Responsibility Pattern.
 * Corresponds to the base Handler class in the UML specification.
 */
public abstract class Handler {
    // Private field matching '-handler : Handler' in the UML diagram
    private Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Handler getHandler() {
        return this.handler;
    }

    /**
     * Handles the incoming Request.
     * Subclasses implement validation and decide whether to pass to getHandler().
     *
     * @param request the incoming HTTP request
     */
    public abstract void handle(Request request);
}
