package com.lakruwan.mediator;

/**
 * Console demonstration corresponding directly to Page 9 of the course curriculum.
 */
public class ChatApp {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     Chat Room - Mediator Pattern Demo            ");
        System.out.println("==================================================");

        ChatRoom chatRoom = new ChatRoom();

        User sahan = new User("Sahan", chatRoom);
        User dinuka = new User("Dinuka", chatRoom);

        chatRoom.addUser(sahan);
        chatRoom.addUser(dinuka);

        sahan.send("Hello");
        dinuka.send("Good morning");

        System.out.println("==================================================");
    }
}
