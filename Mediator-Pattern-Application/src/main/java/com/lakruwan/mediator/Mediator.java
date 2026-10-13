package com.lakruwan.mediator;

/**
 * Mediator Interface defining contract for communication among User colleagues.
 * Defined as per OODP Coursework specifications.
 */
public interface Mediator {
    void sendMessage(String message, User sender);
    void addUser(User user);
}
