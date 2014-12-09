package com.thecolony.tractus.saveInfo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import javax.xml.transform.OutputKeys;
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

    public Filer(String name) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            file = new File("src/com/thecolony/tractus/saveInfo/" + name + ".xml");
            if (file.exists()) {
                doc = builder.parse(file);
                root = doc.getDocumentElement();
            } else {
                file.createNewFile();
                doc = builder.newDocument();
                root = doc.createElement("tractus");
                doc.appendChild(root);
                //write();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Element addObject(String name, String attr, String val){
        Element node=doc.createElement(name);
        node.setAttribute(attr,val);
        root.appendChild(node);
        return node;
    }
    public void addInfo(Element node,String element,String text){
        Element el=doc.createElement(element);
        el.appendChild(doc.createTextNode(text));
        node.appendChild(el);
    }
    public NodeList getObject(String name){
        return root.getElementsByTagName(name);
    }
    public void write(){
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        }
        catch (Exception e){e.printStackTrace();}
    }
}