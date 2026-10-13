package com.lakruwan.mediator;

/**
 * Listener interface for observing chat events (used for GUI updates and logging).
 */
public interface ChatListener {
    void onMessageDispatched(String senderName, String message, String receiverName);
    void onUserAdded(User user);
}
