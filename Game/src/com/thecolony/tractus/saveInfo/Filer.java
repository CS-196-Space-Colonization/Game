package com.thecolony.tractus.saveInfo;

import com.thecolony.tractus.worldgen.SpatialEntities.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
/**
 * Created by wes on 12/8/14.
 */
public class Filer {
    private Document doc;
    private Element root;
    private File file;

    public Filer(String name){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            file=new File("/src/junk/"+name+".xml");
            if (file.exists()) {
                doc=builder.parse(file);
                root=doc.getDocumentElement();
            }
            else{
                doc=builder.newDocument();
                root=doc.createElement("tractus");
                doc.appendChild(root);
                write();
            }
        }
        catch (Exception e){e.printStackTrace();}
    }
    public void addPlanet(Planet p){
        Element planet=addObject("planet","name",p.getName());
        String type="?";
        switch (p.getType()){
            case LAVA_PLANET: type="L";
                break;
            case SUBEARTH_PLANET: type="S";
                break;
            case TERRESTRIAL_PLANET: type="T";
                break;
            case MININEPTUNE_PLANET: type="M";
                break;
            case GASGIANT_PLANET: type="G";
                break;
        }
        String cont="";
        for (Territory continent:p.getSubTerr()) cont+=continent.getName()+",";
        String loc=p.getLocation().getX()+",0,"+p.getLocation().getZ();
        addInfo(planet,"type",type);
        addInfo(planet,"loc",loc);
        addInfo(planet, "color", "" + p.getColor().asIntRGBA());
        addInfo(planet,"res",p.getResName().substring(7));
        addInfo(planet,"star",p.getSuperTerr().getName());
        addInfo(planet,"conts",cont.substring(0,cont.length()-1));
        write();
    }
    public void addStar(Star s){
        Element star=addObject("star","name",s.getName());
        String type="?";
        switch (s.getType()){
            case DWARF_STAR: type="D";
                break;
            case MAINSEQUENCE_STAR: type="M";
                break;
            case GIANT_STAR: type="G";
                break;
            case SUPERGIANT_STAR: type="S";
                break;
        }
        String planets="";
        for (Territory planet:s.getSubTerr()) planets+=planet.getName()+",";
        String loc=s.getLocation().getX()+",0,"+s.getLocation().getZ();
        addInfo(star,"type",type);
        addInfo(star,"loc",loc);
        addInfo(star, "color", "" + s.getColor().asIntRGBA());
        addInfo(star, "res", s.getResName().substring(7));
        addInfo(star,"star",s.getSuperTerr().getName());
        addInfo(star, "conts", planets.substring(0, planets.length() - 1));
        write();
    }
    public Planet loadPlanet(String name){

        return null;
    }
    public Star loadStar(String name){

        return null;
    }
    private Element addObject(String name, String attr, String val){
        Element node=doc.createElement(name);
        node.setAttribute(attr,val);
        root.appendChild(node);
        return node;
    }
    private void addInfo(Node node,String element,String text){
        Element el=doc.createElement(element);
        el.appendChild(doc.createTextNode(text));
        node.appendChild(el);
    }
    private void write(){
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        }
        catch (Exception e){e.printStackTrace();}
    }
}

/*import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by wes on 12/8/14.
 *//*
public class Filer {
    private String filePath;
    private Properties properties;

    public Filer(String path) {
        filePath = System.getProperty("user.dir") + "/src/com/thecolony/tractus/saveInfo/" + path + ".xml";
        properties = new Properties();
        try{
            if (Files.notExists(Paths.get(filePath))) Files.createFile(Paths.get(filePath));
        }
        catch(Exception e){}
    }

    public void save(String key, String value) {
        try {
            OutputStream write = new FileOutputStream(filePath);
            properties.setProperty(key, value);
            properties.storeToXML(write, filePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save the game state.");
        }
    }

    public String load(String value) {
        try {
            InputStream read = new FileInputStream(System.getProperty("user.dir") + "/src/junk/" + filePath + ".xml");
            properties.loadFromXML(read);
            return properties.getProperty(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}*/
