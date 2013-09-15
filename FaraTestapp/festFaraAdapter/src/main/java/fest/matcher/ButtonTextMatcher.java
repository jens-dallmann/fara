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
package fest.matcher;

import org.fest.swing.core.ComponentMatcher;

import javax.swing.JButton;
import java.awt.Component;

public class ButtonTextMatcher implements ComponentMatcher {

  private String _text;

  public ButtonTextMatcher(String text) {
    _text = text;
  }

  @Override
  public boolean matches(Component c) {
    if (c instanceof JButton) {
      JButton button = (JButton) c;
      if (button.getText().equals(_text)) {
        return true;
      }
    }
    return false;
  }
}
