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
package com.hiska.result.ext;

import java.lang.annotation.*;

/**
 * Annotation for define a attribute as Filter Element
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FilterElement {
   /**
    * Attribute names for the where clause
    *
    * @return
    */
   public String[] name() default "#default";

   /**
    * Parameter name in the query
    *
    * @return
    */
   public String param() default "#default";

   /**
    * Parameter name in the where clause
    *
    * @return
    */
   public boolean convertToParam() default false;
}
