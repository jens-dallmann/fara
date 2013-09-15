package docGenerator.frontend;

import docGenerator.frontend.components.pathNamePair.PathNamePairController;
import docGenerator.model.DocPathNamePair;
import docGenerator.services.DocGeneratorService;

import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainWindowPanelController {

  private MainWindowPanelUI ui;
  private List<PathNamePairController> pathNamePairControllers;
  private DocGeneratorService genService;

  public MainWindowPanelController(int pathNamePairsCount, DocGeneratorService genService) {
    ui = new MainWindowPanelUI();
    this.genService = genService;
    pathNamePairControllers = new ArrayList<PathNamePairController>();
    for (int i = 0; i < pathNamePairsCount; i++) {
      PathNamePairController onePair = new PathNamePairController();
      pathNamePairControllers.add(onePair);
      ui.addJPanel(onePair.getPanel());
    }

    addListener();
  }

  private void addListener() {
    ui.getGenerateButton().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        generateDocs();
      }
    });
  }

  private void generateDocs() {
    List<DocPathNamePair> pairs = collectPairs();
    genService.generateDocs(pairs);
  }

  private List<DocPathNamePair> collectPairs() {
    List<DocPathNamePair> pairs = new ArrayList<DocPathNamePair>();
    for (PathNamePairController controller : pathNamePairControllers) {
      DocPathNamePair pair = controller.getPair();
      if (pair != null) {
        pairs.add(pair);
      }
    }
    return pairs;
  }

  public JPanel getPanel() {
    return ui.getPanel();
  }
}
