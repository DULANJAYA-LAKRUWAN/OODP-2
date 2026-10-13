package com.lakruwan.mediator;

/**
 * Colleague class representing a Chat User.
 * Communicates with other users exclusively through the Mediator.
 */
public class User {
    private final String name;
    private final Mediator mediator;

    public User(String name, Mediator mediator) {
        this.name = name;
        this.mediator = mediator;
    }

    public String getName() {
        return name;
    }

    public Mediator getMediator() {
        return mediator;
    }

    /**
     * Sends a message through the central mediator.
     */
    public void send(String message) {
        mediator.sendMessage(message, this);
    }

    /**
     * Receives a message routed by the central mediator.
     */
    public void receive(String message, String senderName) {
        System.out.println(senderName + " to " + name + " : " + message);
    }

    @Override
    public String toString() {
        return name;
    }
}
