/*
 * Copyright 2014 the original author or authors.
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
package net.kuujo.vertigo.input.impl;

import net.kuujo.vertigo.context.InputConnectionContext;
import net.kuujo.vertigo.input.InputConnection;
import net.kuujo.vertigo.message.JsonMessage;
import net.kuujo.vertigo.util.serializer.DeserializationException;
import net.kuujo.vertigo.util.serializer.Serializer;
import net.kuujo.vertigo.util.serializer.SerializerFactory;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;

/**
 * Default input connection implementation.
 *
 * @author Jordan Halterman
 */
public class DefaultInputConnection implements InputConnection {
  private static final Serializer serializer = SerializerFactory.getSerializer(JsonMessage.class);
  private final EventBus eventBus;
  private final InputConnectionContext context;
  private Handler<JsonMessage> messageHandler;

  private final Handler<Message<String>> internalMessageHandler = new Handler<Message<String>>() {
    @Override
    public void handle(Message<String> message) {
      if (messageHandler != null) {
        try {
          messageHandler.handle(serializer.deserializeString(message.body(), JsonMessage.class));
        } catch (DeserializationException e) {
        }
      }
    }
  };

  public DefaultInputConnection(Vertx vertx, InputConnectionContext context) {
    this.eventBus = vertx.eventBus();
    this.context = context;
  }

  @Override
  public InputConnectionContext context() {
    return context;
  }

  @Override
  public String port() {
    return context.port();
  }

  @Override
  public InputConnection messageHandler(Handler<JsonMessage> handler) {
    messageHandler = handler;
    return this;
  }

  @Override
  public InputConnection open() {
    return open(null);
  }

  @Override
  public InputConnection open(Handler<AsyncResult<Void>> doneHandler) {
    eventBus.registerHandler(context.port(), internalMessageHandler, doneHandler);
    return this;
  }

  @Override
  public void close() {
    close(null);
  }

  @Override
  public void close(Handler<AsyncResult<Void>> doneHandler) {
    eventBus.unregisterHandler(context.port(), internalMessageHandler, doneHandler);
  }

}
