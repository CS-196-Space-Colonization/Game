/**
 * Created by chthonic7 on 10/8/14.
 */
public class Continent {
    protected double size; //Placeholder for bigness of the continent
    protected Res res=new Res();
    protected int population;
    private final Planet planet;   //We might want the continent to know what planet owns it, but I'm not sure why...
    protected String owner="", name="";
    //Tech=techtree

    public Continent(Planet planet, Res res, int population, double size, String owner, String name){
        this.planet=planet;
        this.population=population;
        this.size=size;
        this.res=res;
        this.owner=owner;
        this.name=name;
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

    public void setPopulation(int population) { this.population = population; }
    //population dynamics will be just implement logic using get/set pop/res

    public double getResource(String resourceName){
        return res.getResource(resourceName);
    }
    public boolean setResource(String resourceName, double value){
        return res.setResource(resourceName,value);
    }
    public String toString(){
        return planet.toString()+" Continent="+name;
    }
}
