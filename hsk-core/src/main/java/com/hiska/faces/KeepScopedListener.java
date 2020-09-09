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

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * @author Willyams Yujra
 */
public class KeepScopedListener implements PhaseListener {
   @Override
   public PhaseId getPhaseId() {
      return PhaseId.RESTORE_VIEW;
   }

   @Override
   public void beforePhase(PhaseEvent event) {
      // System.out.println("DirectoryScopeListener-beforePhase: " +
      // event.getPhaseId());
   }

   @Override
   public void afterPhase(PhaseEvent event) {
      FacesContext context = event.getFacesContext();
      Map<String, Object> dirMap = (Map) context.getExternalContext().getSessionMap().remove("DIR_MAP");
      if (dirMap != null) {
         Map<String, Object> viewMap = context.getViewRoot().getViewMap();
         String viewId = context.getViewRoot().getViewId();
         dirMap.forEach((var k, var v) -> {
            KeepScoped scope = v.getClass().getAnnotation(KeepScoped.class);
            if (scope != null && viewId.startsWith(scope.dir())) {
               LOGGER.log(Level.FINE, "RESTORE: {0} - {1}: {2}", new Object[]{scope, k, v});
               viewMap.put(k, v);
            }
         });
      }
   }

   private static final Logger LOGGER = Logger.getLogger(KeepScopedListener.class.getName());
}
