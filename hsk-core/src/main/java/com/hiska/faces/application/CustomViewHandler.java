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
package com.hiska.faces.application;

import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class CustomViewHandler extends ViewHandlerWrapper {
   private final ViewHandler wrapped;

   public CustomViewHandler(ViewHandler wrapped) {
      this.wrapped = wrapped;
   }

   @Override
   public UIViewRoot restoreView(FacesContext facesContext, String viewId) {
      UIViewRoot root = wrapped.restoreView(facesContext, viewId);
      if (root == null) {
         root = wrapped.createView(facesContext, viewId);
      }
      return root;
   }

   @Override
   public ViewHandler getWrapped() {
      return wrapped;
   }
}
