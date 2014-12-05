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
public class TestSub extends TestClass
{
    public String hold;
    public TestSub()
    {
        hold = "test sub";
    }
}
