package com.thecolony.tractus.player.ai.battle;

import com.thecolony.tractus.player.ai.battle.ships.Flotilla;

/**
 * A class used to simulate a flotilla battle.
 * @author Joe Pagliuco
 */
public class FlotillaBattler
{
    private Flotilla[] attackers;
    private Flotilla defend;
    
    /**
     * Creates a new simulation for a flotilla battle.
     * @param defend The <code>Flotilla</code> that is being attacked.
     * @param attackers A list <code>Flotilla</code> objects that are attacking.
     */
    public FlotillaBattler(Flotilla defend, Flotilla... attackers)
    {
        this.attackers = attackers;
        this.defend = defend;
    }
    
    /**
     * Adds an attacker flotilla to attacking side.
     * @param newAttacker The <code>Flotilla</code> to add.
     */
    public void addAttacker(Flotilla newAttacker)
    {
        Flotilla[] temp = new Flotilla[attackers.length + 1];
        System.arraycopy(attackers, 0, temp, 0, attackers.length);
        temp[temp.length - 1] = newAttacker;
        attackers = temp;
    }
    
    /**
     * Simulates the battle between the attacking and defender flotillas.
     * @param deltaTime The time between two consecutive frames.
     * @return 0 if the battle has not ended, -1 if the attackers won, +1 if the
     * defender won.
     */
    public int update(float deltaTime)
    {
        // return Flotilla.flotillaBattle(attackers, defend);
        return attackers[0].flotillaBattle(defend, deltaTime);
    }
}