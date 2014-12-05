/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thecolony.tractus.networking;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Arturo
 */
@Serializable
public class TestClass implements java.io.Serializable
{
    public String hold;
    public TestClass()
    {
        hold = "new string";
    }
}
