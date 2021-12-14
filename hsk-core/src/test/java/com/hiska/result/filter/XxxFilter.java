/**
 *   _   _ _     _         ____         __ _
 *  | | | (_)___| | ____ _/ ___|  ___  / _| |_
 *  | |_| | / __| |/ / _` \___ \ / _ \| |_| __|
 *  |  _  | \__ \   < (_| |___) | (_) |  _| |_
 *  |_| |_|_|___/_|\_\__,_|____/ \___/|_|  \__|
 *
 *  Copyright © 2020 HiskaSoft
 *  http://www.hiskasoft.com/licenses/LICENSE-2.0
 */
package com.hiska.result.filter;

import com.hiska.result.FilterEntry;
import com.hiska.result.ext.FilterElement;
import com.hiska.result.Param;
import lombok.Data;

/**
 * @author Willyams Yujra
 */
@Data
public class XxxFilter {
   @FilterElement
   private FilterEntry<Long> id;
   @FilterElement(name = {"col1", "col2", "col3"})
   private FilterEntry<String> col;
   @FilterElement
   private FilterEntry<String> colName;
   @FilterElement
   private FilterEntry<Param> code;
   @FilterElement(name = {"other.ref", "ref"})
   private FilterEntry<Long> ref;
}
