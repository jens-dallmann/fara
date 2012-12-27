/*
 * StuReSy - Student Response System
 * Copyright (C) 2012  StuReSy-Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fest.interfaces;

import fitArchitectureAdapter.container.CommandResult;

/**
 * Provides methods for tabbed pane tests.
 * 
 * @author jens.dallmann
 */
public interface TabbedPaneUIAdapter {

	/**
	 * changes the current tab to the tab with the passed title of the tabbed
	 * pane with the passed name
	 * 
	 * @param componentName name of the tabbed pane.
	 * @param tabTitle title of the tab which should be shown
	 * @return CommandResult
	 */
	CommandResult changeTab(String componentName, String tabTitle);
}
