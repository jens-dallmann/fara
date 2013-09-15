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
 * Provides methods for testing a list component.
 *
 * @author jens.dallmann
 */
public interface ListUIAdapter {

  /**
   * Select a list item in the given list.
   * The list item which should be selected will be allocated by name.
   *
   * @param listName the name of the list in which a item should be selected
   * @param itemName the name of the item which should be selected in the given list.
   * @return CommandResult
   */
  CommandResult selectListItem(String listName, String itemName);

  CommandResult checkListItemExist(String listName, String itemDescriptor);

  CommandResult checkNoListItemSelected(String listName);

  CommandResult checkListItemCount(String listName, String operator,
                                   String count);
}
