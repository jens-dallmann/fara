package docGenerator.frontend.components.pathNamePair;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import org.apache.commons.lang3.StringUtils;

import docGenerator.model.DocPathNamePair;

public class PathNamePairController {
	
	private PathNamePairUI ui;
	
	public PathNamePairController() {
		ui = new PathNamePairUI();
		ui.getFilechooserButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser filechooser = new JFileChooser();
				filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				String absolutePath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		        absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
				filechooser.setSelectedFile(new File(absolutePath));
				filechooser.setFileFilter(new FolderFilechooserFilter());
				int showOpenDialog = filechooser.showOpenDialog(null);
				if(showOpenDialog == JFileChooser.APPROVE_OPTION) {
					File selectedFile = filechooser.getSelectedFile();
					ui.setPath(selectedFile.getAbsolutePath());
				}
			}
		});
	}
	
	public JPanel getPanel() {
		return ui.getPanel();
	}
	
	public DocPathNamePair getPair() {
		DocPathNamePair pair = null;
		if(isValidPair()) {
			pair = new DocPathNamePair(ui.getPath(), ui.getName());
		}
		return pair;
	}

	private boolean isValidPair() {
		return StringUtils.isNotEmpty(ui.getName()) && StringUtils.isNotEmpty(ui.getPath());
	}
}
