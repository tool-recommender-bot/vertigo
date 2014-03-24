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

import java.util.ArrayList;
import java.util.List;

import net.kuujo.vertigo.context.InputConnectionContext;
import net.kuujo.vertigo.context.InputStreamContext;
import net.kuujo.vertigo.hooks.InputHook;
import net.kuujo.vertigo.input.InputConnection;
import net.kuujo.vertigo.input.InputStream;
import net.kuujo.vertigo.message.JsonMessage;
import net.kuujo.vertigo.network.auditor.Acker;
import net.kuujo.vertigo.util.CountingCompletionHandler;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.impl.DefaultFutureResult;

/**
 * Default input stream implementation.
 *
 * @author Jordan Halterman
 */
public class DefaultInputStream implements InputStream {
  private final Vertx vertx;
  private final InputStreamContext context;
  private final Acker acker;
  private final List<InputConnection> connections = new ArrayList<>();
  private final List<InputHook> hooks = new ArrayList<>();
  private Handler<JsonMessage> messageHandler;

  private final Handler<JsonMessage> internalMessageHandler = new Handler<JsonMessage>() {
    @Override
    public void handle(JsonMessage message) {
      hookReceived(message.messageId().correlationId());
      if (messageHandler != null) {
        messageHandler.handle(message);
      }
    }
  };

  public DefaultInputStream(Vertx vertx, InputStreamContext context, Acker acker) {
    this.vertx = vertx;
    this.context = context;
    this.acker = acker;
  }

  @Override
  public String name() {
    return context.name();
  }

  @Override
  public InputStreamContext context() {
    return context;
  }

  @Override
  public InputStream addHook(InputHook hook) {
    hooks.add(hook);
    return this;
  }

  /**
   * Calls receive hooks.
   */
  private void hookReceived(final String messageId) {
    for (InputHook hook : hooks) {
      hook.handleReceive(messageId);
    }
  }

  /**
   * Calls ack hooks.
   */
  private void hookAck(final String messageId) {
    for (InputHook hook : hooks) {
      hook.handleAck(messageId);
    }
  }

  /**
   * Calls fail hooks.
   */
  private void hookFail(final String messageId) {
    for (InputHook hook : hooks) {
      hook.handleFail(messageId);
    }
  }

  @Override
  public InputStream messageHandler(Handler<JsonMessage> handler) {
    messageHandler = handler;
    return this;
  }

  @Override
  public InputStream ack(JsonMessage message) {
    acker.ack(message.messageId());
    hookAck(message.messageId().correlationId());
    return this;
  }

  @Override
  public InputStream fail(JsonMessage message) {
    acker.fail(message.messageId());
    hookFail(message.messageId().correlationId());
    return this;
  }

  @Override
  public InputStream open() {
    return open(null);
  }

  @Override
  public InputStream open(final Handler<AsyncResult<Void>> doneHandler) {
    if (connections.isEmpty()) {
      final CountingCompletionHandler<Void> startCounter = new CountingCompletionHandler<Void>(context.connections().size());
      startCounter.setHandler(new Handler<AsyncResult<Void>>() {
        @Override
        public void handle(AsyncResult<Void> result) {
          if (result.failed()) {
            new DefaultFutureResult<Void>(result.cause()).setHandler(doneHandler);
          } else {
            new DefaultFutureResult<Void>((Void) null).setHandler(doneHandler);
          }
        }
      });

      for (InputConnectionContext connection : context.connections()) {
        connections.add(new DefaultInputConnection(vertx, connection).open(new Handler<AsyncResult<Void>>() {
          @Override
          public void handle(AsyncResult<Void> result) {
            if (result.failed()) {
              startCounter.fail(result.cause());
            } else {
              startCounter.succeed();
            }
          }
        }).messageHandler(internalMessageHandler));
      }
    }
    return this;
  }

  @Override
  public void close() {
    close(null);
  }

  @Override
  public void close(final Handler<AsyncResult<Void>> doneHandler) {
    final CountingCompletionHandler<Void> stopCounter = new CountingCompletionHandler<Void>(connections.size());
    stopCounter.setHandler(new Handler<AsyncResult<Void>>() {
      @Override
      public void handle(AsyncResult<Void> result) {
        if (result.failed()) {
          new DefaultFutureResult<Void>(result.cause()).setHandler(doneHandler);
        } else {
          connections.clear();
          new DefaultFutureResult<Void>((Void) null).setHandler(doneHandler);
        }
      }
    });

    for (InputConnection connection : connections) {
      connection.close(new Handler<AsyncResult<Void>>() {
        @Override
        public void handle(AsyncResult<Void> result) {
          if (result.failed()) {
            stopCounter.fail(result.cause());
          } else {
            stopCounter.succeed();
          }
        }
      });
    }
  }

}
