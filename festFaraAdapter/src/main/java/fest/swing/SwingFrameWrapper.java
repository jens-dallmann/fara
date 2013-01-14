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
import java.awt.Container;
import java.awt.Window;

import javax.swing.JFrame;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.ComponentFinder;
import org.fest.swing.core.ComponentLookupScope;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.testing.FestSwingTestCaseTemplate;

import fest.matcher.ButtonTextMatcher;

public class SwingFrameWrapper extends FestSwingTestCaseTemplate {

	private FrameFixture _frameFixture;

	public SwingFrameWrapper() {
	}

	public void init(GuiQuery<JFrame> frameQuery) {
		setUpRobot();
		robot().settings().componentLookupScope(
				ComponentLookupScope.SHOWING_ONLY);
		_frameFixture = new FrameFixture(robot(),
				startupInGuiThread(frameQuery));

	}

	@RunsInEDT
	private JFrame startupInGuiThread(GuiQuery<JFrame> frameQuery) {
		return GuiActionRunner.execute(frameQuery);
	}

	/**
	 * Finds a component by its name. To find a component by a name a component
	 * with this name must exist else null will be returned
	 * 
	 * @param name
	 *            name of the component
	 * @return the component if one can be found
	 */
	public Component findComponentByName(String name) {
		Window[] windows = JFrame.getWindows();
		Component component =  searchThroughWindows(windows, name);
		return component;
	}

	private Component searchThroughWindows(Window[] windows, String name) {
		for (Window window : windows) {
			if (window.isVisible()) {
				Component[] components = window.getComponents();
				return searchThroughComponent(components, name);
			}
		}
		return null;
	}

	private Component searchThroughComponent(Component[] components, String name) {
		Component foundComponent = null;
		for (int i = 0; i < components.length; i++) {
			Component oneComponent = components[i];
			if (foundComponent == null) {
				if (isSearchedComponent(name, oneComponent)) {
					foundComponent = oneComponent;
				} else if (canGoDeeper(oneComponent)) {
					foundComponent = goDeeperInRecursion(name, foundComponent,
							oneComponent);
				}
			}
		}
		return foundComponent;
	}

	private boolean canGoDeeper(Component oneComponent) {
		return oneComponent.isVisible();
	}

	private Component goDeeperInRecursion(String name,
			Component foundComponent, Component oneComponent) {
		if(isContainer(oneComponent)) {
			Container container = (Container) oneComponent;
			foundComponent = searchThroughComponent(container.getComponents(), name);
		}
		return foundComponent;
	}

	private boolean isContainer(Component oneComponent) {
		return oneComponent instanceof Container;
	}

	private boolean isSearchedComponent(String name, Component oneComponent) {
		return canGoDeeper(oneComponent) && oneComponent.getName() != null
				&& oneComponent.getName().equals(name);
	}

	/**
	 * If no button can be found by name on several reasons you can try to find
	 * buttons by text. This feature is only implemented for buttons not for any
	 * other component. For this an extra component matcher is implemented which
	 * asks for the text of the button. If no Component can be found a
	 * ComponentLookupException
	 * 
	 * @param text
	 *            which is displayed on the component.
	 * @return Component the component if one can be found
	 */
	public Component findButtonByText(String text) {
		ComponentFinder finder = _frameFixture.robot.finder();
		Component component = null;
		try {
			component = finder.find(new ButtonTextMatcher(text));
		} catch (ComponentLookupException e) {
		}
		return component;
	}

	public Robot getRobot() {
		return robot();
	}

	public FrameFixture getFrameFixture() {
		return _frameFixture;
	}
}
