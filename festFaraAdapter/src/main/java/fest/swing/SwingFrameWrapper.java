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

import javax.swing.JFrame;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.ComponentLookupScope;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.testing.FestSwingTestCaseTemplate;

public class SwingFrameWrapper extends FestSwingTestCaseTemplate {

	private FrameFixture _frameFixture;

	public SwingFrameWrapper() {
	}

	public void init(GuiQuery<JFrame> frameQuery) {
		if(robot() != null) {
			robot().cleanUp();
		}
		setUpRobot();
		robot().settings().componentLookupScope(
				ComponentLookupScope.DEFAULT);
		_frameFixture = new FrameFixture(robot(),
				startupInGuiThread(frameQuery));
		_frameFixture.robot.settings().delayBetweenEvents(600);
	}

	@RunsInEDT
	private JFrame startupInGuiThread(GuiQuery<JFrame> frameQuery) {
		return GuiActionRunner.execute(frameQuery);
	}

	public Robot getRobot() {
		return robot();
	}

	public FrameFixture getFrameFixture() {
		return _frameFixture;
	}
	
}
