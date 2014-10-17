package space_colonization;

public class Fleet {
	private flotilla[] fleet;
	public Fleet ()
	{
		fleet = flotilla_creator.createFleet(10);
	}
	public Fleet(int size)
	{
		fleet = flotilla_creator.createFleet(size);
	}

}
