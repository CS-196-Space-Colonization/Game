package com.thecolony.tractus.military.battle;

import com.thecolony.tractus.military.ships.Flotilla;

/**
 * A class used to simulate a flotilla battle.
 * @author Joe Pagliuco
 */
public class FlotillaBattler
{
    private Flotilla attacker;
    private Flotilla defender;
    
    /**
     * Creates a new simulation for a flotilla battle.
     * @param attacker The <code>Flotilla</code> that is attacking.
     * @param defender A <code>Flotilla</code> that is being attacked.
     */
    public FlotillaBattler(Flotilla attacker, Flotilla defender)
    {
        this.attacker = attacker;
        this.defender = defender;
    }

    public Flotilla getAttacker() {
        return attacker;
    }

    public void setAttacker(Flotilla attacker) {
        this.attacker = attacker;
    }

    public Flotilla getDefender() {
        return defender;
    }

    public void setDefender(Flotilla defender) {
        this.defender = defender;
    }

    /**
     * Simulates the battle between the attacker and defender flotillas.
     * @param deltaTime The time between two consecutive frames.
     * @return 0 if the battle has not ended, -1 if the attacker won, +1 if the
     * defender won.
     */
    public int update(float deltaTime)
    {
        int battle = attacker.flotillaBattle(defender, deltaTime);
        if (battle == 1)
            attacker.getDrawableObject3d().changeNodeState(false);
        else if (battle == -1)
            defender.getDrawableObject3d().changeNodeState(false);
        return battle;
    }
}