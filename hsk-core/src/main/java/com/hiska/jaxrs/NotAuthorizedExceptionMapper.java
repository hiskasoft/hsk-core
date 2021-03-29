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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiska.jaxrs;

import com.hiska.result.MessageBuilder;
import com.hiska.result.Result;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author yracnet
 */
@Provider
public class NotAuthorizedExceptionMapper implements ExceptionMapper<WebApplicationException> {
   @Override
   public Response toResponse(WebApplicationException ex) {
      System.out.println("--->" + ex.getClass());
      ex.printStackTrace();
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
