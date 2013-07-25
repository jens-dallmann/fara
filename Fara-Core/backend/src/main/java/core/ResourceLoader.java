package core;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class ResourceLoader {

	public static final String ERROR_ICON = "error_button.png";
	public static final String WARNING_ICON = "warning-icon.png";
	public static final String INFO_ICON = "info-icon.png";
	public static final String FILE_BROKEN_ICON = "file-broken-icon.png";
	public static final String SETTINGS_PROPERTIES = "settings.properties";

	public static ClassLoader cl = ResourceLoader.class.getClassLoader();

	public static ImageIcon getImageIcon(String imageName) {
		try {
			URL imageURL = cl.getResource(imageName);
			return new ImageIcon(imageURL);
		} catch (Exception e) {
			URL brokenImageURL = cl.getResource(FILE_BROKEN_ICON);
			return new ImageIcon(brokenImageURL);
		}
	}

	public static ImageIcon getSmoothImageIconResized(String imageName,
			int width, int height) {
		Image image = getImageIcon(imageName).getImage();
		Image scaledImage = image.getScaledInstance(width, height,
				Image.SCALE_SMOOTH);
		return new ImageIcon(scaledImage);
	}
	
	public static String retrieveRessourcePath(String ressourceFile) {
		return cl.getResource(ressourceFile).getPath();
	}
}