
package com.spacecolonization.networking;

import com.jme3.math.ColorRGBA;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


@Serializable(id = 2)
public class CubeMessage extends AbstractMessage {

    ColorRGBA color;

    public CubeMessage() {} // empty default constructor
    public CubeMessage(ColorRGBA color) {
        this.color = color;
    }
    public ColorRGBA getColor(){
        return color; 
    }
}