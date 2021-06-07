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
 * @author Willyams Yujra
 */
public class SpanMessageRenderer extends MessageRenderer {
   @Override
   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      boolean messageToSpan = RenderkitHelp.isMessageToSpan(context);
      if (!messageToSpan) {
         super.encodeEnd(context, component);
      } else {
         RenderkitHelp.assertAttribute(component, "infoClass", " invalid-feedback text-success d-block");
         RenderkitHelp.assertAttribute(component, "warnClass", " invalid-feedback text-warning d-block");
         RenderkitHelp.assertAttribute(component, "errorClass", " invalid-feedback text-danger d-block");
         RenderkitHelp.assertAttribute(component, "fatalClass", " invalid-feedback text-dark d-block");
         super.encodeEnd(context, component);
      }
   }
}
