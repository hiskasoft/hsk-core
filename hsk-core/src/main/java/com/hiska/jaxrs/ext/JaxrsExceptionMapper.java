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
package com.hiska.jaxrs.ext;

import java.security.Principal;
import java.util.*;
import java.util.logging.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.ExceptionMapper;

public abstract class JaxrsExceptionMapper<E extends Throwable> implements ExceptionMapper<E> {
   private static final Logger LOG = Logger.getLogger(JaxrsExceptionMapper.class.getName());
   @Context
   private UriInfo uriInfo;
   @Context
   private Request request;
   @Context
   private SecurityContext security;

   @Override
   public Response toResponse(E ex) {
      LOG.log(Level.SEVERE, "JAX-RS Exception - " + this.getClass().getSimpleName(), ex);
      return processResponse(ex);
   }

   public List<String> traceInfo() {
      Principal principal = security.getUserPrincipal();
      List<String> trace = new ArrayList<>();
      trace.add(formatName("URL", uriInfo.getAbsolutePath()));
      trace.add(formatName("PATH", uriInfo.getPath()));
      trace.add(formatName("METHOD", request.getMethod()));
      trace.add(formatName("USER", principal == null ? "NONE" : principal.getName()));
      trace.add(formatName("REALM", security.getAuthenticationScheme()));
      return trace;
   }

   public String formatName(String name, Object value) {
      if (name != null && name.length() < 10) {
         name = (name + "          ").substring(0, 10);
      }
      return name + " : " + value;
   }

   public abstract Response processResponse(E ex);
}
