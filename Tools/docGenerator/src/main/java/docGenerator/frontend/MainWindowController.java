package docGenerator.frontend;

import docGenerator.services.DocGeneratorService;


public class MainWindowController {
	
	private MainWindowUI ui;
	private MainWindowPanelController panelController;
	
	public MainWindowController(DocGeneratorService service) {
		ui = new MainWindowUI();
		panelController = new MainWindowPanelController(5, service);
		ui.addPanel(panelController.getPanel());
	}
}
