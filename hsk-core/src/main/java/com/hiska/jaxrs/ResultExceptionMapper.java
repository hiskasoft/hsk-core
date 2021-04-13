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

import com.hiska.result.Result;
import com.hiska.result.ResultException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * @author yracnet
 */
@Provider
public class ResultExceptionMapper extends JaxrsExceptionMapper<ResultException> {
   @Override
   public Response processResponse(ResultException ex) {
      Result result = ex.getResult();
      return Response.status(Response.Status.BAD_REQUEST)
            .entity(result)
            .type(MediaType.APPLICATION_JSON)
            .build();
   }
}
