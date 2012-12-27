/*******************************************************************************
 * Copyright (c) 2012 Jens Dallmann.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jens Dallmann - initial API and implementation
 ******************************************************************************/
package fest.swing;

import java.awt.Component;

import javax.swing.JFrame;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.ComponentFinder;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.testing.FestSwingTestCaseTemplate;

import fest.matcher.ButtonTextMatcher;
import fest.matcher.ComponentNameMatcher;


public class SwingFrameWrapper extends FestSwingTestCaseTemplate{
	
	private FrameFixture _frameFixture;
	
	public SwingFrameWrapper() {
	}
	
	public void init(GuiQuery<JFrame> frameQuery) {
		setUpRobot();
		_frameFixture = new FrameFixture(robot(), startupInGuiThread(frameQuery));
	}

	@RunsInEDT
	private JFrame startupInGuiThread(GuiQuery<JFrame> frameQuery) {
		return GuiActionRunner.execute(frameQuery);
	}
	/**
	 * Finds a component by its name. To find a component by a name a component with this name must exist else a ComponentLookupException will be thrown.
	 * 
	 * @param name name of the component
	 * @return the component if one can be found
	 */
	public Component findComponentByName(String name) {
		ComponentFinder finder = _frameFixture.robot.finder();
		Component findByName = null;
		try {
			findByName = finder.find(new ComponentNameMatcher(name));
		}
		catch(ComponentLookupException e) {
		}
		return findByName;
	}
	
	/**
	 * If no button can be found by name on several reasons you can try to find buttons by text.
	 * This feature is only implemented for buttons not for any other component.
	 * For this an extra component matcher is implemented which asks for the text of the button.
	 * If no Component can be found a ComponentLookupException 
	 * 
	 * @param text which is displayed on the component.
	 * @return Component the component if one can be found
	 */
	public Component findButtonByText(String text) {
		ComponentFinder finder = _frameFixture.robot.finder();
		Component component = null;
		try {
			component = finder.find(new ButtonTextMatcher(text));
		}
		catch(ComponentLookupException e){
		}
		return component;
	}

	public Robot getRobot() {
		return robot();
	}
}
