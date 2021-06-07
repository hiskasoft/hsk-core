/**
 *   _   _ _     _         ____         __ _
 *  | | | (_)___| | ____ _/ ___|  ___  / _| |_
 *  | |_| | / __| |/ / _` \___ \ / _ \| |_| __|
 *  |  _  | \__ \   < (_| |___) | (_) |  _| |_
 *  |_| |_|_|___/_|\_\__,_|____/ \___/|_|  \__|
 *
 *  Copyright © 2020 HiskaSoft
 *  http://www.hiskasoft.com/licenses/LICENSE-2.0
 */
package com.hiska.faces;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GenerateTagLib {
   public static void main(String[] args) throws Exception {
      generate();
   }

   public static void generate() throws Exception {
      File resources = new File("src/main/resources/META-INF/resources");
      File target = new File("target/generated-xml/");
      List<Entry> entries = createDocuments(resources, target);
      for (Entry entry : entries) {
         entry.document = null;
         File fileOutput = new File("src/main/resources/META-INF/" + entry.name + "-cc.taglib.xml");
         createTagLib(entry.file, fileOutput, "http://xmlns.jcp.org/jsf/composite/hsk");
         fileOutput = new File("src/main/resources/META-INF/" + entry.name + ".taglib.xml");
         createTagLib(entry.file, fileOutput, "http://hiskasoft.com/jsf/hsk");
      }
   }

   public static void createTagLib(File fileInput, File fileOutput, String url) throws Exception {
      TransformerFactory factory = TransformerFactory.newInstance();
      File fileXslt = new File("src/test/resources/taglib.xslt");
      Source xslt = new StreamSource(fileXslt);
      Source xml = new StreamSource(fileInput);
      Transformer transformer = factory.newTransformer(xslt);
      transformer.setParameter("URL", url);
      StreamResult result = new StreamResult(fileOutput);
      transformer.transform(xml, result);
   }

   public static List<Entry> createDocuments(File resources, File output) throws Exception {
      List<Entry> documents = new ArrayList<>();
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      File libs[] = resources.listFiles(f -> f.getName().length() < 5);
      for (File lib : libs) {
         Entry entry = new Entry();
         entry.name = lib.getName();
         System.out.println("READ LIB: " + lib.getName());
         File[] xhtmls = lib.listFiles(f -> f.getName().endsWith(".xhtml"));
         Arrays.sort(xhtmls);
         String url = "http://java.sun.com/jsf/composite";
         if (xhtmls.length > 0) {
            entry.document = dBuilder.newDocument();
            Element rootElement = entry.document.createElementNS(url, "cc:components");
            entry.document.appendChild(rootElement);
            for (File xhtml : xhtmls) {
               System.out.println("APPEND: " + xhtml.getName());
               // --------
               Element fileElement = entry.document.createElementNS(url, "cc:component");
               fileElement.setAttribute("resourceId", lib.getName() + "/" + xhtml.getName());
               fileElement.setAttribute("fileName", xhtml.getName());
               fileElement.setAttribute("name", xhtml.getName().replace(".xhtml", ""));
               // --------
               Document cc = dBuilder.parse(xhtml);
               Node item = getNodeInterface(cc.getDocumentElement());
               if (item == null) {
                  continue;
               }
               Node newNode = entry.document.importNode(item, true);
               // --------
               fileElement.appendChild(newNode);
               rootElement.appendChild(fileElement);
            }
            documents.add(entry);
            entry.file = new File(output, lib.getName() + ".xml");
            writeDocument(entry.document, entry.file);
         }
      }
      return documents;
   }

   public static Node getNodeInterface(Element el) throws Exception {
      NodeList list = el.getChildNodes();
      for (int i = 0; i < list.getLength(); i++) {
         String name = list.item(i).getNodeName();
         if (name.equals("interface") || name.endsWith(":interface")) {
            return list.item(i);
         }
      }
      return null;
   }

   public static void writeDocument(Document doc, File file) throws Exception {
      file.getParentFile().mkdirs();
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(file);
      transformer.transform(source, result);
//      StreamResult consoleResult = new StreamResult(System.out);
//      transformer.transform(source, consoleResult);
   }
}
