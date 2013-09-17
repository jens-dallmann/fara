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
 * Provides methods for the base class components.
 *
 * @author jens.dallmann
 */
public interface ComponentUIAdapter {

  /**
   * Return if the component with the name is enabled.
   *
   * @param componentName name of the component.
   * @return CommandResult
   */
  CommandResult checkEnabled(String componentName);

  /**
   * Return if the component with the name is disabled.
   * Should be the negotiation of isEnabled.
   *
   * @param componentName name of the component
   * @return CommandResult
   */
  CommandResult checkDisabled(String componentName);

  /**
   * Return if the component with the name is visible.
   *
   * @param componentName name of the component.
   * @return CommandResult
   */
  CommandResult checkVisible(String componentName);
}
