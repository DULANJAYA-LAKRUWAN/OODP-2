package com.lakruwan.mediator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Concrete Mediator representing a centralized Chat Room.
 * Directs communication between colleagues without them having references to each other.
 */
public class ChatRoom implements Mediator {
    private final List<User> users = new ArrayList<>();
    private final List<ChatListener> listeners = new ArrayList<>();

    public void addListener(ChatListener listener) {
        listeners.add(listener);
    }

    @Override
    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            for (ChatListener listener : listeners) {
                listener.onUserAdded(user);
            }
        }
    }

    @Override
    public void sendMessage(String message, User sender) {
        for (User user : users) {
            // Mediator prevents sending the message back to the sender
            if (user != sender) {
                user.receive(message, sender.getName());
                for (ChatListener listener : listeners) {
                    listener.onMessageDispatched(sender.getName(), message, user.getName());
                }
            }
        }
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }
}
