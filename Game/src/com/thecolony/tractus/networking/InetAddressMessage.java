/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacecolonization.networking;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import java.net.InetAddress;


@Serializable(id=1)
public class InetAddressMessage extends AbstractMessage {
     private InetAddress address;
     public InetAddressMessage() {}                  // empty default constructor
     public InetAddressMessage(InetAddress a) {      // custom constructor
       address = a;
     }                  
     public void setAddress(InetAddress a){address = a;}
     public InetAddress getAddress(){return address;}
}