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
package com.hiska.faces.renderkit;

import com.sun.faces.renderkit.html_basic.MessageRenderer;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @author yracnet
 */
public class BootstrapMessageRenderer extends MessageRenderer {
   @Override
   public void encodeBegin(FacesContext context, UIComponent component)
         throws IOException {
      assertAttribute(component, "infoClass", " invalid-feedback text-success d-block");
      assertAttribute(component, "warnClass", " invalid-feedback text-warning d-block");
      assertAttribute(component, "errorClass", " invalid-feedback text-danger d-block");
      assertAttribute(component, "fatalClass", " invalid-feedback text-dark d-block");
      super.encodeBegin(context, component);
   }

   private void assertAttribute(UIComponent component, String name, String defaultValue) {
      String value = (String) component.getAttributes().get(name);
      if (value == null || value.isEmpty()) {
         component.getAttributes().put(name, defaultValue);
      }
   }
}
