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
package com.hiska.faces;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

public class ViewCacheListener implements PhaseListener {
   @Override
   public PhaseId getPhaseId() {
      return PhaseId.RENDER_RESPONSE;
   }

   @Override
   public void afterPhase(PhaseEvent event) {
   }

   @Override
   public void beforePhase(PhaseEvent event) {
      FacesContext facesContext = event.getFacesContext();
      HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
      response.setHeader("Pragma", "no-cache");
      response.setHeader("Cache-Control", "no-cache");
      response.setHeader("Cache-Control", "no-store");
      response.setHeader("Cache-Control", "must-revalidate");
      response.setHeader("Expires", "Mon, 8 Aug 2006 10:00:00 GMT");
   }
}
