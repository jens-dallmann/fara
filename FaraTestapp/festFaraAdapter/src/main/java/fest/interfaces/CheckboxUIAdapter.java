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
 * A Adapter for a checkbox which provides methods for using a checkbox on ui tests.
 *
 * @author jens.dallmann
 */
public interface CheckboxUIAdapter {

  /**
   * Checks a checkbox in the ui. The state of the checkbox will be true after using this method.
   *
   * @param checkboxName name of the component
   */
  CommandResult selectCheckbox(String checkboxName);
}
