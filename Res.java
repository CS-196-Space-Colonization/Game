import java.io.File;
import java.util.Scanner;

/**
 * This accesses resource information from permanent storage and returns values to owners.
 * Currently, the class is written for resource info stored in a text file
 * Each line will formatted as follows: resourceName=resourceValue
 *
 * Created by chthonic7 on 10/11/14.
 */
public class Res {
    //Initial assumption is a text file that has all the resource information
    private String path;
    private File file;
    private Scanner reader;

    /**
     * Initialize with the file
     * @param resFile
     */
    public Res(String resFile){
        path=resFile;
        file=new File(path);
        try{reader=new Scanner(file);}
        catch (Exception e){
            System.out.print(e);}
    }

    /**
     * Empty constructor will create new file for this
     */
    public Res(){
        path=""; //TODO: Think of names for new files.
        file=new File(path);
        try{
            file.createNewFile();
            reader=new Scanner(file);
        }
        catch(Exception e){System.out.println(e);}
    }
    public double getResource(String name){
       //Insert database query
        return 0.0;
    }
    public boolean setResource(String resouce, double value){
        //Insert database instruction. If successful, then return true, if not, return false
        if(value<0) return false;
        else return true;
    }
}
