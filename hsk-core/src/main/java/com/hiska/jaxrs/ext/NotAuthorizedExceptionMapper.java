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

import com.hiska.result.ext.MessageBuilder;
import com.hiska.result.Result;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * @author yracnet
 */
@Provider
public class NotAuthorizedExceptionMapper extends JaxrsExceptionMapper<WebApplicationException> {
   @Override
   public Response processResponse(WebApplicationException ex) {
      Response.Status status = Response.Status.UNAUTHORIZED;
      Result result = MessageBuilder.create("HTTP-401: No autorizado")
            .exception(ex)
            .asResult();
      return Response.status(status)
            .entity(result)
            .type(MediaType.APPLICATION_JSON)
            .build();
   }
}
