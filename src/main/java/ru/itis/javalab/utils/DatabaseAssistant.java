package ru.itis.javalab.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.itis.javalab.models.Equipment;
import ru.itis.javalab.models.Well;
import ru.itis.javalab.services.UtilService;
import ru.itis.javalab.services.UtilServiceImpl;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DatabaseAssistant {

    public static void exportDataFromDBToXML() {
        UtilService utilService = UtilServiceImpl.getInstance();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = Objects.requireNonNull(documentBuilder).newDocument();

        Map<Well, List<Equipment>> treemap = new TreeMap<>(Comparator.comparingInt(Well::getId));
        Map<Well, List<Equipment>> map = utilService.findAllDataToXML();
        treemap.putAll(map);

        Element root = doc.createElement("dbinfo");
        doc.appendChild(root);

        treemap.forEach((well, equipmentList) -> {
            Element wellTag = doc.createElement("well");
            wellTag.setAttribute("name", well.getName());
            wellTag.setAttribute("id", String.valueOf(well.getId()));
            equipmentList.forEach(equipment -> {
                Element equipmentTag = doc.createElement("equipment");
                equipmentTag.setAttribute("name", equipment.getName());
                equipmentTag.setAttribute("id", String.valueOf(equipment.getId()));
                wellTag.appendChild(equipmentTag);
            });
            root.appendChild(wellTag);
        });

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(transformer).setOutputProperty(OutputKeys.INDENT, "yes");

        StreamResult streamResult = null;
        try {
            streamResult = new StreamResult(new FileWriter("test.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DOMSource source = new DOMSource(doc);
        try {
            transformer.transform(source, streamResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
