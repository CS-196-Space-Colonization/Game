package com.spacecolonization.networking;



import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;


public class ClientListener implements MessageListener<Client> {

    public void messageReceived(Client source, Message message) {
        
        if (message instanceof GreetingMessage) {
            GreetingMessage helloMessage = (GreetingMessage) message;
            System.out.println("Client #" + source.getId()
                    + " received the message: '"
                    + helloMessage.getGreeting() + "'");
        } else if (message instanceof InetAddressMessage) {
            InetAddressMessage addrMessage = (InetAddressMessage) message;
            // unused
        }
        else if (message instanceof TextMessage) {
            TextMessage textm = (TextMessage) message;
            System.out.println("Client #" + source.getId()
                    + " received the message: '"
                    + textm.getText() + "'");
        }
    }

}

