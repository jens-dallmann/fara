package core.exception.frontend;

import java.awt.Color;

import javax.swing.JTextArea;

public class ApplicationExceptionAreaFiller implements ExceptionAreaFiller{
	@Override
	public void fillArea(Throwable throwable, JTextArea area) {
		StringBuffer buffer = new StringBuffer();
		StackTraceElement[] stackTrace = throwable.getStackTrace();
		for(StackTraceElement oneElement: stackTrace) {
			buffer.append(oneElement.toString()+"\n");
		}
		area.setText(buffer.toString());
		area.setForeground(Color.RED);
	}
}
