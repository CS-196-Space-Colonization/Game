/**
 * Created by chthonic7 on 10/15/14.
 */
public abstract class Territory {
    protected double locationX,locationY; //Or any sort of spatial positioning...
    //protected Res res=new Res();
    //Current method of getting resources doesn't necessitate a Res object, only at continent level is it necessary
    protected String name, owner;
    protected Territory superTerr;
    protected Territory[] subTerr;

    protected Territory(double locX, double locY, Territory superTerr, Territory[] terr, String name, String owner){
        this.subTerr=terr;
        this.superTerr=superTerr;
        this.locationX=locX;
        this.locationY=locY;
        this.name=name;
        this.owner=owner;
    }
    public final double getLocationX() {
        return locationX;
    }

    public final void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public final double getLocationY() {
        return locationY;
    }

    public final void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getOwner() {
        return owner;
    }

    public final void setOwner(String owner) {
        this.owner = owner;
    }

    public final Territory getSuperTerr() {
        return superTerr;
    }

    public final void setSuperTerr(Territory superTerr) {
        this.superTerr = superTerr;
    }

    public final Territory[] getSubTerr() {
        return subTerr;
    }

    public final void setSubTerr(Territory[] subTerr) {
        this.subTerr = subTerr;
    }

    public double getResource(String resourceName){
        double sum=0;
        for (Territory terr:subTerr) sum+=terr.getResource(resourceName);
        /*this requires that we have a continent class that calls to the resource class
            That would make a resource object at every level redundant.
            Or we could have the files constantly updating up and down the chain, which would be hell.
            I mean "recursively" call getResource takes up hella stack, but screw it.
         */
        return sum;
    }
    public String toString(){
        return "("+locationX+","+locationY+") is owned by "+owner+"\n";
    }
}
