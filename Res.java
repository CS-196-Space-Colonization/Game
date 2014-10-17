import com.sun.org.apache.xerces.internal.impl.io.UTF8Reader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    private String pathName;
    private final String dir="resources/";
    private Path path;
    private BufferedReader reader;
    private BufferedWriter writer;
    /**
     * Initialize with the file
     * @param resFile file name for this resource
     */
    public Res(String resFile){
        /*path=dir+resFile;
        file=new File(path);
        try{
            reader=new Scanner(file);
        }
        catch (Exception e){
            System.out.print(e);}
        This was erroneous Scanner attempt
        */
        pathName=dir+resFile;
        path= Paths.get(pathName);
        if (!Files.exists(path)){
            System.out.println("File does not exist");
            path=null;
        }
    }

    /**
     * Empty constructor will create new file for this
     */
    public Res(){
        /*path=dir+"resFile"; //TODO: Think of names for new files. Or just be unoriginal
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
        }*/
        pathName=dir+"resFile";
        path=Paths.get(pathName);
        int i=1;
        while(Files.exists(path)){
            pathName=dir+"resFile"+i;
            i++;
            path=Paths.get(pathName);
        }
    }
    public double getResource(String name){
        //Insert database query
        //Or for now, just do a search in the textfile
        String line="";
        try{reader=Files.newBufferedReader(path, StandardCharsets.UTF_8);}
        catch (Exception e){}
        while(line!=null){
            try {
                line = reader.readLine();
                if (line.indexOf(name + "=") == 0) {
                    reader.close();
                    return Double.parseDouble(line.substring(name.length() + 1));
                }
            }
            catch (Exception e){}
        }
        return 0.0;
    }

    /**
     *
     * @param resource The resource to be set
     * @param value The value of the resource
     * @return True if successful, false if not
     */
    public boolean setResource(String resource, double value){
        //Insert database instruction. Or for now, write to file If successful, then return true, if not, return false
        String line="", newLine=resource+"="+value;
        boolean written=false;
        Path dir= Paths.get("resources/"),tmp = null;
        try {
            tmp=Files.createTempFile(dir,null,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            writer=Files.newBufferedWriter(tmp,StandardCharsets.UTF_8);
            reader=Files.newBufferedReader(path, StandardCharsets.UTF_8);
        }
        catch (Exception e) {}
        if(value<0) return false;
        while(line!=null){
            try {
                line = reader.readLine();
                if (line.indexOf(resource + "=") == 0) {
                    writer.write(newLine);
                    written=true;
                }
                else writer.write(line);
                writer.newLine();
            }
            catch (Exception e){}
        }
        if(!written) {
            try {
                writer.write(newLine);
                writer.close();
                reader.close();
                Files.copy(tmp,path);
                Files.delete(tmp);
                return true;
            }
            catch (Exception e){}
            return false;
        }
        else{
            try {
                writer.close();
                reader.close();
                Files.copy(tmp, path, StandardCopyOption.REPLACE_EXISTING);
                Files.delete(tmp);
                return true;
            }
            catch (Exception e){
                e.printStackTrace();
                return false;}
        }
    }
}
