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
package fest.driver;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

import javax.swing.*;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Query to get the selection state of the abstract button.
 *
 * @author jens.dallmann
 */
public class AbstractButtonSelectedQuery {

  /**
   * Returns if the button is selected
   *
   * @param button the button which should be checked
   * @return true if the button is selected else false.
   */
  @RunsInEDT
  public static boolean isSelected(final AbstractButton button) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return button.isSelected();
      }
    });
  }
}
