import org.cyberneko.html.parsers.DOMParser
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource

import java.io.*;

File file = new File(basedir, "target/faraTestResults")
if (!file.exists()) {
  throw new FileNotFoundException("Can not find generated documentation file in target/FitCommands/firstIntegrationTestFitCommandDocs.html");
}

String[] testResultItems = file.list();

boolean testResultCreated = false;
File testResult = null;
for (String testResultItem : testResultItems) {
  if(testResultItem.startsWith("Test")) {
    testResultCreated = true;
    testResult = new File(basedir, testResultItem);
  }
}
if(!testResultCreated) {
  throw new Exception("No Test Result found");
}



DOMParser parser = new DOMParser();
parser.setFeature("http://xml.org/sax/features/namespaces", false);
parser.parse(new InputSource(new FileInputStream(file)));

Document doc = parser.getDocument()

NodeList rows = doc.getElementsByTagName("tr");
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