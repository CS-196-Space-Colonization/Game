package com.thecolony.tractus.player.ai;

import com.thecolony.tractus.Map;
import com.thecolony.tractus.Unit;
import java.util.ArrayList;

/**
 * This is a default AI class that is going to be used primarily for testing
 * purposes. They have no real priority sorting for the different actions that
 * they can take, but will try to execute all commands in order.
 * Code name: Grez (pronounced: greys, anagram for zerg)
 *
 * @author Arturo Guerrero
 *
 */
public class DefaultAI extends NPC
{

    public DefaultAI(Map map, int playerNumber)
    {
        super(map, playerNumber, NPC.TYPE_NONE);
    }

    /**
     * This method is the one called by the main game loop on every AI. It wont
     * try to add things to the queue but instead just execute one thing every
     * game tick. Right now if there are other units around then it will send
     * all of its units to attack those units.
     *
     * @see NPC.act()
     */
    public void act()
    {
        //if there are other units vissible, then attack those untis.
        Unit closest = findClosest(otherUnits);
        if (hasUnits())
        {
	  allAttack(closest);
        } else
        {
	  //make untis so it can attack
        }
    }

    /*
     * If the unit this AI is trying to attack is not null, it will send all of
     * its units to that unit and attack it.
     */
    private void allAttack(Unit unit)
    {
        if (unit != null)
        {
	  for (int i = 0; i < ownUnits.size(); i++)
	  {
	      //attack that unit
	      //or at the very least, move to that unit
	  }
        }
    }
    /*
     * Finds the closest unit.
     * @todo This should be moved to the unit class to ba called on every unit.
     * @return the closest unit to some abitrary point
     */
    private Unit findClosest(ArrayList<Unit> units)
    {
        if (units.size() == 0)
        {
	  return null;
        }
        return null;
    }
}
