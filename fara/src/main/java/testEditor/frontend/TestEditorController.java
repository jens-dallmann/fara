package testEditor.frontend;

import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.bind.PropertyException;

import testEditor.frontend.editorTable.FitHtmlRepresentationController;
import testEditor.frontend.menubar.MenubarController;
import testEditor.frontend.settings.TestSuiteSettingsListener;
import core.exception.frontend.ApplicationExceptionAreaFiller;
import core.exception.frontend.ExceptionHandler;
import core.exception.frontend.ExceptionLevel;
import core.exception.frontend.ExceptionWindowController;
import core.htmlFileFolderNavigator.HTMLFileFolderNavigatorController;
import core.htmlFileFolderNavigator.utils.WrappedFile;
import core.processableTable.ProcessService;
import core.service.PropertyService;
import fit.Parse;

public class TestEditorController {
	private FitHtmlRepresentationController fitTable;
	private HTMLFileFolderNavigatorController navigation;
	private PropertyService propertyService;
	private TestEditorUI ui;

	public TestEditorController(ProcessService service, Parse table) {
		this(service, table, "Fara Test Editor");
	}

	public TestEditorController(ProcessService service, Parse table,
			String title) {
		try {
			propertyService = new PropertyService();
		} catch (PropertyException e1) {
			new ExceptionWindowController(null, e1, ExceptionLevel.ERROR, new ApplicationExceptionAreaFiller());
		}
		fitTable = new FitHtmlRepresentationController();
		ui = new TestEditorUI(title);
		MenubarController menubarController = new MenubarController(ui.getFrame());
		ui.setMenuBar(menubarController.getMenubar());
		menubarController.setTestSuiteSettingsListener(new TestSuiteSettingsListener() {
			@Override
			public void newRootFolder(String path) {
				if(path != null && !path.equals("")) {
					navigation.init(path);
				}
			}
		});

		navigation = new HTMLFileFolderNavigatorController(ui.getFrame());
		String rootFolder = readRootFolder();
		if(!rootFolder.equals("")) {
			navigation.init(rootFolder);
		}
		JComponent scrollableTree = navigation.getScrollableTree();
		defineUncaugtExceptionHandling();
		fitTable.init(service, table);
		ui.addPanel(fitTable.getToolbarUI());
		ui.addTreePanel(scrollableTree);
		navigation.addSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode lastPathComponent = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				WrappedFile wrappedFile = (WrappedFile) lastPathComponent.getUserObject();
				File file = wrappedFile.getFile();
				fitTable.load(file);
			}
		});
		ui.addTablePanel(fitTable.getTableUI());
	}

	private String readRootFolder() {
		String rootFolder = propertyService.getProperty(PropertyService.ROOT_FOLDER_PATH);
		if(rootFolder == null) {
			rootFolder = "";
		}
		return rootFolder;
	}

	private void defineUncaugtExceptionHandling() {
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
		System.setProperty("sun.awt.exception.handler",
				ExceptionHandler.class.getName());
		ExceptionHandler.setParent(ui.getFrame());
	}

	public JFrame getFrame() {
		return ui.getFrame();
	}

	public void setTestFile(File file) {
		fitTable.setTestFile(file);
	}
}
