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

import com.hiska.result.MessageBuilder;
import com.hiska.result.Result;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * @author yracnet
 */
@Provider
public class ValidationExceptionMapper extends JaxrsExceptionMapper<ConstraintViolationException> {
   private String getClassName(Object o) {
      return o == null ? "<null>" : o.getClass().getSimpleName();
   }

   private String getPropertyName(Path path) {
      String[] split = path.toString().split("\\.");
      return split[split.length - 1];
   }

   @Override
   public Response processResponse(ConstraintViolationException exception) {
      MessageBuilder mb = MessageBuilder.create("APP-2001: Parametros invalidos")
            .title("Validacion")
            .warn();
      exception.getConstraintViolations()
            .stream()
            .forEach(it -> {
               String className = getClassName(it.getLeafBean());
               String name = getPropertyName(it.getPropertyPath());
               mb.cause(name + ": " + it.getMessage());
               mb.trace(className + "." + name + " = " + it.getInvalidValue());
            });
      Result result = mb.asResultBuilder()
            .error()
            .get();
      return Response.status(Response.Status.BAD_REQUEST)
            .entity(result)
            .type(MediaType.APPLICATION_JSON)
            .build();
   }
}
