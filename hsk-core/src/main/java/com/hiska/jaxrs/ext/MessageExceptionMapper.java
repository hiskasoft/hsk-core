/**
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

import com.hiska.result.Result;
import com.hiska.result.MessageException;
import com.hiska.result.ext.MessageBuilder;
import javax.validation.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;

/**
 * @author yracnet
 */
@Provider
public class MessageExceptionMapper extends JaxrsExceptionMapper<MessageException> {
   private String getClassName(Object o) {
      return o == null ? "<null>" : o.getClass().getSimpleName();
   }

   private String getPropertyName(Path path) {
      String[] split = path.toString().split("\\.");
      return split[split.length - 1];
   }

   @Override
   public Response processResponse(MessageException exception) {
      Result result = exception.getBuilder()
            .cause("Not Handler")
            .asResultBuilder()
            .error()
            .get();
      return Response.status(Response.Status.BAD_REQUEST)
            .entity(result)
            .type(MediaType.APPLICATION_JSON)
            .build();
   }
}
