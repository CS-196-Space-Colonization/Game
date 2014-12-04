package com.thecolony.tractus.networking;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
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
    
    public ColorRGBA getColor(String color)
     {
         String[] s = color.substring(6).split(",");
         float r = Float.parseFloat(s[0]);
         float g = Float.parseFloat(s[1]);
         float b = Float.parseFloat(s[2]);
         float a = Float.parseFloat(s[3].substring(0, s[3].length() - 2));
         return new ColorRGBA(r, g, b, a);
     }
    
     public Vector3f getPosition(String position)
     {
         String[] s = position.split(",");
         float x = Float.parseFloat(s[0].substring(1));
         float y = Float.parseFloat(s[1]);
         float z = Float.parseFloat(s[2].substring(0, s[2].length() - 2));
         return new Vector3f(x, y, z);
     }

}



