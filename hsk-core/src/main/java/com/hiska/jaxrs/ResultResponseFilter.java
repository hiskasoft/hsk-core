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
import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

@Provider
public class ResultResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
        MultivaluedMap<String, Object> headers = response.getHeaders();
        // ---------------------------------------------------------------------
        String tokenValue = (String) request.getProperty("TOKEN-VALUE");
        if (tokenValue != null) {
            headers.add("SET-TOKEN", tokenValue);
        }
        // ---------------------------------------------------------------------
        Object o = response.getEntity();
        if (o instanceof Result) {
            headers.add("IS-RESULT", "true");
            Result r = (Result) o;
            if (r.isError()) {
                response.setStatus(401);
            }
        } else {
            headers.add("IS-RESULT", "false");
        }
    }
}
