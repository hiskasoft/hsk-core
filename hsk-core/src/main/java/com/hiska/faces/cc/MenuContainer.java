/**
 *   _   _ _     _         ____         __ _
 *  | | | (_)___| | ____ _/ ___|  ___  / _| |_
 *  | |_| | / __| |/ / _` \___ \ / _ \| |_| __|
 *  |  _  | \__ \   < (_| |___) | (_) |  _| |_
 *  |_| |_|_|___/_|\_\__,_|____/ \___/|_|  \__|
 *
 *  Copyright © ${project.inceptionYear} HiskaSoft
 *  http://www.hiskasoft.com/licenses/LICENSE-2.0
 */
package com.hiska.faces.cc;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

@FacesComponent("menuContainer")
public class MenuContainer extends UINamingContainer {
   private Object value;

   @Override
   public void setValueExpression(String name, ValueExpression expression) {
      if ("value".equals(name)) {
         Object valueLocal = expression.getValue(getFacesContext().getELContext());
         setValue(valueLocal);
      } else {
         super.setValueExpression(name, expression);
      }
   }

   public Object getValue() {
      return value;
   }

   public void setValue(Object value) {
      this.value = value;
   }
}