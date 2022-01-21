package com.leaderli.liutil.dom;

import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMDocumentFactory;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.List;

@SuppressWarnings("unchecked")
public class LiDomUtil {


    @SuppressWarnings("java:S106")
    private static final PrintStream LOGGER = System.out;

    /**
     * 在读取文件时，去掉dtd的验证，可以缩短运行时间
     */
    public static SAXReader getSAXReader() {
        @SuppressWarnings("all")
        SAXReader saxReader = new SAXReader(DOMDocumentFactory.getInstance(), false);
        return saxReader;
    }

    public static DOMDocument getDOMDocumentByPath(String path) throws DocumentException {
        return (DOMDocument) getSAXReader().read(LiDomUtil.class.getResourceAsStream(path));

    }

    public static DOMElement getDOMRootByPath(String path) throws DocumentException {
        return (DOMElement) getDOMDocumentByPath(path).getRootElement();

    }

    public static DOMDocument getDOMDocumentByString(String xml) throws DocumentException {
        return (DOMDocument) getSAXReader().read(new StringReader(xml));

    }

    public static DOMDocument getDOMDocumentByInputStream(InputStream inputStream) throws DocumentException {
        return (DOMDocument) getSAXReader().read(inputStream);

    }

    public static DOMElement getDOMRootByInputStream(InputStream inputStream) throws DocumentException {
        return (DOMElement) getSAXReader().read(inputStream).getRootElement();

    }

    public static DOMElement getDOMRootByString(String xml) throws DocumentException {
        return (DOMElement) getDOMDocumentByString(xml).getRootElement();

    }

    public static List<DOMElement> getChildren(Node element) {

        return element.selectNodes("child::*");
    }

    public static List<DOMElement> getChildren(Node element, String xpath) {

        return element.selectNodes(xpath);
    }

    public static DOMElement selectSingleNode(Node element, String xpath) {
        return (DOMElement) element.selectSingleNode(xpath);
    }

    /**
     * 打印格式化文本
     */
    public static void prettyPrint(Node node) {
        try {
            //document
            StringWriter writer = new StringWriter();
            XMLWriter xmlWriter = new XMLWriter(writer, OutputFormat.createPrettyPrint());
            xmlWriter.write(node);
            xmlWriter.close();
            LOGGER.println(writer);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String pretty(Node node) {
        StringWriter writer = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(writer, OutputFormat.createPrettyPrint());
        try {
            xmlWriter.write(node);
            xmlWriter.close();
            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 打印格式化文本
     */
    public static void createCompactFormat(Node node) {
        try {
            //document
            StringWriter writer = new StringWriter();
            XMLWriter xmlWriter = new XMLWriter(writer, OutputFormat.createCompactFormat());
            xmlWriter.write(node);
            xmlWriter.close();
            LOGGER.println(writer);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
