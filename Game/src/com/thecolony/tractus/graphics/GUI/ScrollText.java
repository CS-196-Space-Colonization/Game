package com.thecolony.tractus.graphics.GUI;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import java.util.ArrayList;

/**
 * A class used to represent a scrollable text array.
 * @author Joe Pagliuco
 */
public class ScrollText
{
    /**
     * Will always be a few lines higher than the actual lines per screen.
     */
    private final int linesPerScreen;
    
    private BitmapText bitmapText;
    private ArrayList<String> text;
    
    private int topIndex;
    
    public ScrollText(int screenHeight, float fontSize, float xPosition, float yPosition, BitmapFont font, Node node)
    {        
        bitmapText = new BitmapText(font);
        bitmapText.setSize(fontSize);
        bitmapText.setColor(ColorRGBA.White);
        bitmapText.setText("");
        bitmapText.setLocalTranslation(xPosition, yPosition, 0.0f);
        
        text = new ArrayList<String>();
        
        topIndex = 0;
        
        linesPerScreen = (int)((float)(screenHeight - yPosition) / bitmapText.getHeight());
        
        node.attachChild(bitmapText);
    }
    
    public void addText(String... textContent)
    {
        for (int i = 0; i < textContent.length; i++)
            text.add(textContent[i]);
        
        addTextToBitmap();
    }
    
    public void clearText()
    {
        bitmapText.setText("");
        text = new ArrayList<String>();
        topIndex = 0;
    }
    
    /**
     * Removes a single line of text.
     * @param index The index of the text to remove.
     */
    public void removeText(int index)
    {
        removeText(index, index+1);
    }
    /**
     * Removes a block of text.
     * @param beginIndex The beginning index of the text (inclusive).
     * @param endIndex The end index of the text (exclusive).
     */
    public void removeText(int beginIndex, int endIndex)
    {
        ArrayList<String> t = new ArrayList<String>();
        
        clearText();
        
        for (int i = 0; i < text.size(); i++)
        {
            if (i == beginIndex)
                i = endIndex;
            t.add(text.get(i));
        }
        text = t;
        addTextToBitmap();
    }
    
    public String[] getText()
    {
        return (String[]) text.toArray();
    }
    
    public void scroll(boolean up)
    {
        topIndex += up ? -1 : 1;
        if (topIndex < 0)
            topIndex = 0;
        else if (topIndex > text.size() - 1)
            topIndex = text.size() - 1;
        
        addTextToBitmap();
    }
    
    private void addTextToBitmap()
    {
        String t = "";
        int cap = Math.min(linesPerScreen, text.size() - topIndex);
        for (int i = 0; i < cap; i++)
            t += text.get(i + topIndex) + "\n";
        
        bitmapText.setText(t);
    }
    
    public boolean isEmpty()
    {
        return text.isEmpty();
    }
}