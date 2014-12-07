package com.thecolony.tractus.player.ai;

import com.thecolony.tractus.Unit;
import com.thecolony.tractus.player.ai.battle.BattleObject;
import com.thecolony.tractus.player.ai.battle.ships.Ship;
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

    public DefaultAI(int playerNumber)
    {
        super(playerNumber, NPC.TYPE_NONE);
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
        Ship closest = findClosest(otherUnits);
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
    private void allAttack(Ship ship)
    {
        if (ship != null)
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
    private Ship findClosest(ArrayList<BattleObject> ships)
    {
        if (ships.size() == 0)
        {
	  return null;
        }
        return null;
    }
}
