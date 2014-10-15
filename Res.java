import java.io.File;
import java.nio.file.Path;
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
    private final String dir="resources/";
    private Path path1;
    private File file;
    private Scanner reader;

    /**
     * Initialize with the file
     * @param resFile file name for this resource
     */
    public Res(String resFile){
        path=dir+resFile;
        file=new File(path);
        try{
            reader=new Scanner(file);
        }
        catch (Exception e){
            System.out.print(e);}
    }

    /**
     * Empty constructor will create new file for this
     */
    public Res(){
        path=dir+"resFile"; //TODO: Think of names for new files.
        file=new File(path);
        int i=1;
        while(file.exists()){
            path=dir+"resFile"+i;
            file=new File(path);
        }
        try {
            file.createNewFile();
            reader = new Scanner(file);
            return;
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public double getResource(String name){
        //Insert database query
        //Or for now, just do a search in the textfile
        String line;
        while(reader.hasNextLine()){
            line=reader.nextLine();
            if(line.indexOf(name+"=")==0) {
                reader.close();
                return Double.parseDouble(line.substring(name.length()+1));
            }
        }
        return 0.0;
    }
    public boolean setResource(String resource, double value){
        //Insert database instruction. Or for now, write to file If successful, then return true, if not, return false
        String line;
        if(value<0) return false;
        else {
            while (reader.hasNextLine()){
                line=reader.nextLine();
                if(line.indexOf(resource+"=")==0){

                }
            }

            return true;
        }
    }
}
