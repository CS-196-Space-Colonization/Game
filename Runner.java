/**
 * Created by chthonic7 on 10/15/14.
 */
public class Runner {
    public static void main(String args[]) {
        Sector a = new Sector(1.0, 1.0, null, "a", "no-one");
        Cluster a1 = new Cluster(1.0, 2.0, a, null, "a1", "no-one");
        StarSystem a1a = new StarSystem(1.0, 2.0, a1, null, "a1a", "no-one");
        Star a1a1 = new Star(1.0, 2.0, Star.StarType.RED_GIANT, a1a, null, "a1a1", "no-one");
        Planet a1a1a = new Planet(1.0, 2.0, Planet.PlanetType.GASGIANT, a1a1, null, "a1a1a", "no-one");
        Continent a1a1a1 = new Continent(a1a1a, new Res("resFile"), 5, 2, "a1a1a1", "no-one");
        Territory[] terrs = new Territory[]{a, a1, a1a, a1a1, a1a1a, a1a1a1};
        for (Territory terr:terrs){
            System.out.println(terr);
        }
        System.out.println("iron="+a1a1a1.getResource("iron"));
    }
}
