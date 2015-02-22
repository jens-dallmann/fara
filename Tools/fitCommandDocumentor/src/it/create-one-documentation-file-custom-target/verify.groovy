import org.cyberneko.html.parsers.DOMParser
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource

import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathExpression
import javax.xml.xpath.XPathFactory
import java.io.*;

File file = new File(basedir, "target/custom/FitCommands/firstIntegrationTestFitCommandDocs.html")
if (!file.exists()) {
  throw new FileNotFoundException("Can not find generated documentation file in target/custom/FitCommands/firstIntegrationTestFitCommandDocs.html");
}

XPathFactory xpFactory = XPathFactory.newInstance();
XPath xpath = xpFactory.newXPath();
String expression = "/html/body/table";
XPathExpression xpathExpression = xpath.compile(expression);

DOMParser parser = new DOMParser();
parser.setFeature("http://xml.org/sax/features/namespaces", false);
parser.parse(new InputSource(new FileInputStream(file)));

Document doc = parser.getDocument()

def result = xpathExpression.evaluate(doc, XPathConstants.NODE);

NodeList rows = doc.getElementsByTagName("tr");
Node nodes = (NodeList) result
verifyRow(rows.item(0), "ClassName.CommandName", "Parameter 1", "Parameter 2");
verifyRow(rows.item(1), "AnyCommand.anyCommand","firstParameterDescription","secondParameterDescription")

void verifyRow(Node row, String... values) {
  NodeList columns = row.getChildNodes();
  for(int i = 0; i < columns.getLength(); i++) {
    Node column = columns.item(i)

    def value = column.getTextContent()
    if(!value.trim().equals(values[i].trim())) {
      throw new Exception(values[i]+":"+column.getTextContent());
    }
  }
}