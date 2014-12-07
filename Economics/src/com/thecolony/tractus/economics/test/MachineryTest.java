package com.thecolony.tractus.economics.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.thecolony.tractus.economics.*;
import com.thecolony.tractus.economics.need.*;
import com.thecolony.tractus.economics.products.*;


public class MachineryTest {

	private Machinery getDefaultMachinery() {
		Machinery machines = new Machinery();
		Need needs = new NeedBranch();
		Need actualNeed = new BasicNeed(new Quantity(new Iron(), 5.0));
		needs.add(actualNeed);
		machines.setMaintenanceNeeds(needs);
		return machines;
	}
	
	@Test
	public void testDecay() {
		Machinery machines = getDefaultMachinery();
		machines.performMaintenance(new Inventory());
		
		assertTrue("Machinery does not decay correctly", Double.compare(machines.getHealth(), .95)==0);
	}

	@Test
	public void testRepair() {
		Machinery machines = getDefaultMachinery();
		machines.performMaintenance(new Inventory());
		machines.performMaintenance(machines.getMaintenanceNeeds().getNeededProducts());
		assertTrue("Machinery should only repair if maintenance needs are exceeded", Double.compare(machines.getHealth(), .98)==0);
	}
	
	@Test
	public void testOverRepair() {
		Machinery machines = getDefaultMachinery();
		machines.performMaintenance(new Inventory());
		machines.performMaintenance(machines.getMaintenanceNeeds().getNeededProducts());
		machines.performMaintenance(machines.getMaintenanceNeeds().getNeededProducts());
		assertTrue("Machinery should only repair to 1.0", Double.compare(machines.getHealth(), 1.0)==0);
	}
	
	@Test
	public void testOverDecay() {
		Machinery machines = getDefaultMachinery();
		for (int i = 0; i < 21; i++)
			machines.performMaintenance(new Inventory());
		assertTrue("Machinery should only repair to 1.0", Double.compare(machines.getHealth(), 0.0)==0);
		
	}
}
