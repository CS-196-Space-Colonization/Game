/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spacecolonization.networking;

import com.jme3.network.serializing.Serializer;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;


public class InetAddressSerializer extends Serializer {

  @Override
  public <T> T readObject(ByteBuffer data, Class<T> c) throws IOException {
    byte[] address = new byte[4];
    data.get(address);
    return (T) InetAddress.getByAddress(address);
  }

  @Override
  public void writeObject(ByteBuffer buffer, Object object) throws IOException {
    InetAddress address = (InetAddress) object;
    buffer.put(address.getAddress());
  }
}
