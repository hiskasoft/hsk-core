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
package com.hiska.faces.application;

import com.hiska.result.Message;
import com.hiska.faces.MessageUtil;
import com.hiska.result.MessageBuilder;
import javax.faces.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.el.EvaluationException;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;

/**
 * @author Willyams Yujra
 */
public class ActionResultListener implements ActionListener {
   private static final Logger LOGGER = Logger.getLogger(ActionResultListener.class.getName());
   private ActionListener delegate;

   public ActionResultListener(ActionListener delegate) {
      this.delegate = delegate;
   }

   @Override
   @SuppressWarnings("deprecation")
   public void processAction(ActionEvent event) throws AbortProcessingException {
      try {
         delegate.processAction(event);
      } catch (FacesException e) {
         Exception root = (Exception) e.getCause();
         if (root instanceof EvaluationException) {
            root = (Exception) root.getCause();
            LOGGER.log(Level.SEVERE, "FACES EXCEPTION", root);
         }
         Message message = MessageBuilder.createMessageFatal(root);
         MessageUtil.processMessage(message);
      }
   }
}
