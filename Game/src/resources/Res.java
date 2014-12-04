package resources;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

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
    private final String dir="Game/src/com/thecolony/tractus/resources/";
    //private final String dir="resources/";
    private Path path;
    private BufferedReader reader;
    private BufferedWriter writer;
    /**
     * Initialize with the file
     * @param resFile file name for this resource
     */
    public Res(String resFile){
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
        //TODO: Think of names for new files. Or just be unoriginal
        pathName=dir+"resFile";
        path=Paths.get(pathName);
        int i=1;
        try {
            while (Files.exists(path)) {
                pathName = dir + "resFile" + i;
                i++;
                path = Paths.get(pathName);
            }
            path.toFile().createNewFile();
        }
        catch (Exception e){ e.printStackTrace();}
    }

    /**
     * Get the value of a particular resource
     * @param name Name of resource
     * @return Value of resource. If DNE, return 0
     */
    public double getResource(String name){
        //Insert database query
        //Or for now, just do a search in the textfile
        String line="";
        try{
            reader=Files.newBufferedReader(path, StandardCharsets.UTF_8);
            line = reader.readLine();
            while(line!=null){
                if (line.indexOf(name + "=") == 0) {
                    reader.close();
                    return Double.parseDouble(line.substring(name.length() + 1));
                }
                line = reader.readLine();
            }
        }
        catch (Exception e){}
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
        Path dir= Paths.get(this.dir),tmp = null;
        try {
            tmp = Files.createTempFile(dir, null, null);
            writer = Files.newBufferedWriter(tmp, StandardCharsets.UTF_8);
            reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            if (value < 0) return false;
            line = reader.readLine();
            while (line != null) {
                if (line.indexOf(resource + "=") == 0) {
                    writer.write(newLine);
                    written = true;
                } else writer.write(line);
                writer.newLine();
                line = reader.readLine();
            }
        }
        catch (Exception e) {  e.printStackTrace();}
        if(!written) {
            try {
                writer.write(newLine);
                writer.close();
                reader.close();
                Files.copy(tmp,path,StandardCopyOption.REPLACE_EXISTING);
                Files.delete(tmp);
                return true;
            }
            catch (Exception e){e.printStackTrace();}
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
    public String[] listResource(){
        ArrayList<String> strs=new ArrayList<String>();
        String line="";
        try{
            reader=Files.newBufferedReader(path,StandardCharsets.UTF_8);
            line=reader.readLine();
            while(line!=null){
                if(line.length()>0)strs.add(line.substring(0,line.indexOf("=")));
                line=reader.readLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        String[]str=new String[strs.size()];
        for(int i=0;i<str.length;i++){
            str[i]=strs.get(i);
        }
        return str;
    }
}
