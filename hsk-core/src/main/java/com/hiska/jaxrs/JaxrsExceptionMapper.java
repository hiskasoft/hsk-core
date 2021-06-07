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
package com.hiska.jaxrs;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public abstract class JaxrsExceptionMapper<E extends Throwable> implements ExceptionMapper<E> {
   private static final Logger LOG = Logger.getLogger(JaxrsExceptionMapper.class.getName());

   @Override
   public Response toResponse(E ex) {
      LOG.log(Level.SEVERE, "JAX-RS Exception - " + this.getClass().getSimpleName(), ex);
      return processResponse(ex);
   }

   public abstract Response processResponse(E ex);
}
