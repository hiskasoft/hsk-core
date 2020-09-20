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
package com.hiska.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.hiska.result.Param;

public class ParamConverter implements Converter<Param> {
   @Override
   public Param getAsObject(FacesContext facesContext, UIComponent component, String value) {
      return Param.create(value, "JSF_" + value);
   }

   @Override
   public String getAsString(FacesContext facesContext, UIComponent component, Param param) {
      return param == null ? null : param.getValue();
   }
}
