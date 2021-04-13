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
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * @author yracnet
 */
@Provider
public class ProcessingExceptionMapper extends JaxrsExceptionMapper<ProcessingException> {
   @Override
   public Response processResponse(ProcessingException ex) {
      Result result = MessageBuilder.create("HTTP-400: Error al processar los parametros")
            .exception(ex)
            .asResult();
      return Response.status(Response.Status.BAD_REQUEST)
            .entity(result)
            .type(MediaType.APPLICATION_JSON)
            .build();
   }
}
