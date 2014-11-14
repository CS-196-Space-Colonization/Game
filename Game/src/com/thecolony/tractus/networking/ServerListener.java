package com.spacecolonization.networking;

import com.jme3.network.MessageListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;



public class ServerListener implements MessageListener<HostedConnection> {

    public void messageReceived(HostedConnection source, Message message) {
        if (message instanceof GreetingMessage) {
            GreetingMessage helloMessage = (GreetingMessage) message;
            System.out.println("Server received '"
                    + helloMessage.getGreeting()
                    + "' from client #" + source.getId());
            // prepare and send an answer
            helloMessage.setGreeting("Welcome client #" + source.getId() + "!");
            source.send(helloMessage);
        } else if (message instanceof InetAddressMessage) {
            InetAddressMessage addrMessage = (InetAddressMessage) message;
            System.out.println("The server received the IP address " 
                + addrMessage.getAddress() + "from client #" + source.getId());
        }
        else if (message instanceof TextMessage) {
            TextMessage textm = (TextMessage) message;
            System.out.println("Server received '"
                    + textm.getText()
                    + "' from client #" + source.getId());
            textm.setText("You, client #" + source.getId() + " said " + textm.getText());
            source.send(textm);
        }
    }
}