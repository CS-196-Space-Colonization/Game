package gamemechanics;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Stores a map of all possible upgrades a player can implement and which of they've already implemented
 * Every player has an instance of the upgrade tree
 * 
 * @author Arkojit
 *
 */
public class UpgradeTree
{
	private static class Node {
		public Upgrade upgrade;
		public HashSet<Node> previousNodes;
		public HashSet<Node> nextNodes;
		
		public Node(Upgrade u) {
			upgrade = u;
			previousNodes = new HashSet<Node>();
			nextNodes = new HashSet<Node>();
		}
		
		public void addRequirement(Node n) {
			previousNodes.add(n);
		}
		
		public void addNextNode(Node n) {
			nextNodes.add(n);
		}
	}
	
	private Node startingNode;
	private HashSet<Node> nodes;
	
	/**
	 * Creates a new upgrade tree with none of the upgrades activated
	 */
	public UpgradeTree() {
		Node n0 = new Node(new UpgradeZero("0"));
		Node n10 = new Node(new UpgradeOneZero("1.0"));
		Node n11 = new Node(new UpgradeOneOne("1.1"));
		
		nodes.add(n0);
		nodes.add(n10);
		nodes.add(n11);
		
		n0.addNextNode(n10);
		n0.addNextNode(n11);
		n10.addRequirement(n0);
		n11.addRequirement(n0);
	}
	
	/**
	 * Returns a set of all the upgrades that the player can implement based on previously activated upgrades
	 * @return
	 */
	public Upgrade[] getUpgrades()
	{
		Upgrade[] upgrades = new Upgrade[nodes.size()];
		Iterator<Node> iter = nodes.iterator();
		for(int i = 0; i < upgrades.length; i++)
		{
			upgrades[i] = iter.next().upgrade;
		}
		return upgrades;
	}
}
