/*
 * Mark will be handling most of this stuff, so hold on until he has a class that I work with
 */
package Arturo;

import java.util.ArrayList;

public class Unit 
{
	//Stats
	public String name;
	public double currentHealth;
	public double maxHealth;
	public double attackDamage;
	public double attackSpeed;
	public double attackRadius;
	public double movementSpeed;
	public double reloadSpeed;
	public double splashDamage;
	public double splashRadius;
	public Location location;
	public double sightRadius;
	public ArrayList<Unit> visible;
	public ArrayList<Unit> canAttack;
	
	public Unit(String n, double h, double d, double a, double m, double r)
	{
		name = n;
		maxHealth = h;
		currentHealth = h;
		attackDamage = d;
		attackSpeed = a;
		movementSpeed = m;
		reloadSpeed = r;
	}
	
	public String getName()
	{
		return name;
	}
	
	public double getCurrentHealth()
	{
		return currentHealth;
	}
	
	public double getMaxHealth()
	{
		return maxHealth;
	}
	
	public double getAttackDamage()
	{
		return attackDamage;
	}
	
	public double getAttackSpeed()
	{
		return attackSpeed;
	}
	
	public double getAttackRadius()
	{
		return attackRadius;
	}
	
	public double getMovementSpeed()
	{
		return movementSpeed;
	}
	
	public double getReloadSpeed()
	{
		return reloadSpeed;
	}
	
	public double getSplashDamage()
	{
		return splashDamage;
	}
	
	public double getSplashRadius()
	{
		return splashRadius;
	}
	
	public void setName(String s)
	{
		name = s;
	}
	
	public void setCurrentHealth(double h)
	{
		currentHealth= h;
	}
	
	public void setMaxHealth(double h)
	{
		maxHealth = h;
	}
	
	public void setAttackDamage(double d)
	{
		attackDamage = d;
	}
	
	public void setAttackSpeed(double a)
	{
		attackSpeed = a;
	}
	
	public void setAttackRadius(double a)
	{
		attackRadius = a;
	}
	
	public void setMovementSpeed(double m)
	{
		movementSpeed = m;
	}
	
	public void setReloadSpeed(double r)
	{
		reloadSpeed = r;
	}
	
	public void setSplashDamage(double sd)
	{
		splashDamage = sd;
	}
	
	public void setSplashRadius(double sr)
	{
		splashRadius = sr;
	}
	
	public void attack(Unit other)
	{
		//attack stuff goes here
	}
	
	public double distanceTo(Unit other)
	{
		return 0.0;
	}
}


