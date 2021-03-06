/*
 * JBoss, Home of Professional Open Source
 * Copyright 2016, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.weld.vertx.web.async;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.weld.vertx.AsyncReference;
import org.jboss.weld.vertx.web.WebRoute;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

@WebRoute("/helloasync")
public class HelloAsyncHandler implements Handler<RoutingContext> {

    @Inject
    Instance<AsyncReference<BlockingService>> service;

    @Override
    public void handle(RoutingContext ctx) {
        // Obtain a new bean instance per each invocation
        service.get().whenComplete((r, t) -> {
            if (t != null) {
                ctx.response().setStatusCode(500).end();
            } else {
                ctx.response().setStatusCode(200).end(r.getMessage());
            }
        });
    }

}
