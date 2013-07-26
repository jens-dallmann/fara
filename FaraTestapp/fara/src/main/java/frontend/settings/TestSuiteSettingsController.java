package frontend.settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.xml.bind.PropertyException;

import core.exception.frontend.ApplicationExceptionAreaFiller;
import core.exception.frontend.ExceptionLevel;
import core.exception.frontend.ExceptionWindowController;
import core.service.PropertyService;

public class TestSuiteSettingsController{
	private TestSuiteSettingsUI ui;
	private PropertyService propertyService;
	private List<TestSuiteSettingsListener> listeners;
	private JFrame frame;
	public TestSuiteSettingsController(final JFrame owner, TestSuiteSettingsListener listener) {
		this.frame = owner;
		listeners = new ArrayList<TestSuiteSettingsListener>();
		listeners.add(listener);
		try {
			propertyService = new PropertyService();
		} catch (PropertyException e) {
			new ExceptionWindowController(owner, e, ExceptionLevel.ERROR, new ApplicationExceptionAreaFiller());
		}
		ui = new TestSuiteSettingsUI(owner);
		addListeners(owner);
		ui.setVisible();
	}

	private void addListeners(final JFrame owner) {
		ui.getChooseFolderButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FolderFileFilter());
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int showOpenDialog = chooser.showOpenDialog(owner);
				if(showOpenDialog == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					ui.setTestSuiteFolderText(file.getPath());
				}
			}
		});
		ui.getApproveButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String path = ui.getRootFolderPath();
				saveInPropertyFile(path);
				informNewRootFolder(path);
				ui.setDialogInvisible();
			}

		});
		ui.getCancelButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ui.setDialogInvisible();
			}
		});
	}

	private void saveInPropertyFile(String rootFolderPath) {
		try {
			propertyService.saveInProperty("rootFolderPath", rootFolderPath);
		} catch (PropertyException e) {
			new ExceptionWindowController(frame, e, ExceptionLevel.ERROR, new ApplicationExceptionAreaFiller());
		}
	}
	
	public void registerListener(TestSuiteSettingsListener listener) {
		listeners.add(listener);
	}
	
	public void informNewRootFolder(String path) {
		for(TestSuiteSettingsListener listener: listeners) { 
			listener.newRootFolder(path);
		}
	}
}
