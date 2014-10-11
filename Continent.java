/**
 * Created by chthonic7 on 10/8/14.
 */
public enum Continent {
    BASIC, ADVANCED, ADVANCING, SPACE;

    protected double size;
    //LET'S IGNORE RESOURCE STUFF, IT DOESN'T WORK
    protected Resource[] res=new Resource[]; //heck, how long is this supposed to be?
    protected int population;
    protected final Planet planet;   //We might want the continent to know what planet owns it, but I'm not sure why...
    protected String owner="";

    public Continent(Planet owner, Resource[] res, int population, double size){
        this.planet=owner;
        this.population=population;
        this.size=size;
        this.res=new Resource[res.length];
        for (int i=0;i<res.length;i++) this.res[i]=res[i];
        this.techLevel=Tech.BASIC;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
    //population dynamics will be just implement logic using get/set pop/res
    //TODO: Once I find out what exactly are the resources, getters and setters will be made for those as well.
}
