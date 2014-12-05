package com.thecolony.tractus.networking;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;


public class ClientListener implements MessageListener<Client> {

    private ClientMain app;
    
    
    public ClientListener(ClientMain app)
    {
        this.app = app;
        System.out.println("created listner");
    }
    
    public void messageReceived(Client source, Message message) {
        System.out.println("got a message");
//        if (message instanceof GreetingMessage) {
//            GreetingMessage helloMessage = (GreetingMessage) message;
//            System.out.println("Client #" + source.getId()
//                    + " received the message: '"
//                    + helloMessage.getGreeting() + "'");
//            
//            if(helloMessage.getMap()!=null){
//                for(int i = 0; i<helloMessage.getMap().length; i++){   
//                }
//            }
//        }  
        
        if(message instanceof UpdateClientMessage)
        {
	  UpdateClientMessage msg = (UpdateClientMessage) message;
	  System.out.println("Msg: " + msg.getGreeting());
        }
    }
}