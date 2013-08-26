/*
* Copyright 2013 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.blankstyle.vine.eventbus.root.actions;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;

import com.blankstyle.vine.context.RootContext;
import com.blankstyle.vine.eventbus.Action;
import com.blankstyle.vine.eventbus.AsynchronousAction;

/**
 * A root deploy action.
 *
 * @author Jordan Halterman
 */
public class Deploy extends Action<RootContext> implements AsynchronousAction<Void> {

  public static final String NAME = "deploy";

  @Override
  public void execute(Object[] args, Handler<AsyncResult<Object>> resultHandler) {
    
  }

}