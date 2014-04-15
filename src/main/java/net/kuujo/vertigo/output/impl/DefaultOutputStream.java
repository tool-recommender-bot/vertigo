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
package net.kuujo.vertigo.output.impl;

import java.util.ArrayList;
import java.util.List;

import net.kuujo.vertigo.context.OutputConnectionContext;
import net.kuujo.vertigo.context.OutputStreamContext;
import net.kuujo.vertigo.output.OutputConnection;
import net.kuujo.vertigo.output.OutputGroup;
import net.kuujo.vertigo.output.OutputStream;
import net.kuujo.vertigo.output.selector.Selector;
import net.kuujo.vertigo.util.CountingCompletionHandler;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

/**
 * Default output stream implementation.
 *
 * @author Jordan Halterman
 */
public class DefaultOutputStream implements OutputStream {
  private final OutputStreamContext context;
  private final List<OutputConnection> connections = new ArrayList<>();
  private int maxQueueSize;
  private Selector selector;

  public DefaultOutputStream(Vertx vertx, OutputStreamContext context) {
    this.context = context;
    for (OutputConnectionContext connection : context.connections()) {
      connections.add(new DefaultOutputConnection(vertx, connection));
    }
    this.selector = context.grouping().createSelector();
  }

  @Override
  public OutputStreamContext context() {
    return context;
  }

  @Override
  public OutputStream open() {
    return open(null);
  }

  @Override
  public OutputStream open(Handler<AsyncResult<Void>> doneHandler) {
    final CountingCompletionHandler<Void> counter = new CountingCompletionHandler<Void>(connections.size()).setHandler(doneHandler);
    for (OutputConnection connection : connections) {
      connection.open(counter);
    }
    return this;
  }

  @Override
  public OutputStream setSendQueueMaxSize(int maxSize) {
    this.maxQueueSize = maxSize;
    for (OutputConnection connection : connections) {
      connection.setSendQueueMaxSize(Math.round(maxQueueSize / connections.size()));
    }
    return this;
  }

  @Override
  public int getSendQueueMaxSize() {
    return maxQueueSize;
  }

  @Override
  public boolean sendQueueFull() {
    for (OutputConnection connection : connections) {
      if (connection.sendQueueFull()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public OutputStream drainHandler(Handler<Void> handler) {
    for (OutputConnection connection : connections) {
      connection.drainHandler(handler);
    }
    return this;
  }

  @Override
  public OutputStream group(final String name, final Handler<OutputGroup> handler) {
    final List<OutputGroup> groups = new ArrayList<>();
    List<OutputConnection> connections = selector.select(name, name, this.connections);
    final int connectionsSize = connections.size();
    for (OutputConnection connection : connections) {
      connection.group(name, new Handler<OutputGroup>() {
        @Override
        public void handle(OutputGroup group) {
          groups.add(group);
          if (groups.size() == connectionsSize) {
            handler.handle(new BaseOutputGroup(name, groups));
          }
        }
      });
    }
    return this;
  }

  @Override
  public OutputStream send(Object message) {
    for (OutputConnection connection : selector.select(message, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Object message, String key) {
    for (OutputConnection connection : selector.select(message, key, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(String message) {
    for (OutputConnection connection : selector.select(message, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(String message, String key) {
    for (OutputConnection connection : selector.select(message, key, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Boolean message) {
    for (OutputConnection connection : selector.select(message, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Boolean message, String key) {
    for (OutputConnection connection : selector.select(message, key, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Character message) {
    for (OutputConnection connection : selector.select(message, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Character message, String key) {
    for (OutputConnection connection : selector.select(message, key, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Short message) {
    for (OutputConnection connection : selector.select(message, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Short message, String key) {
    for (OutputConnection connection : selector.select(message, key, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Integer message) {
    for (OutputConnection connection : selector.select(message, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Integer message, String key) {
    for (OutputConnection connection : selector.select(message, key, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Long message) {
    for (OutputConnection connection : selector.select(message, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Long message, String key) {
    for (OutputConnection connection : selector.select(message, key, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Double message) {
    for (OutputConnection connection : selector.select(message, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Double message, String key) {
    for (OutputConnection connection : selector.select(message, key, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Float message) {
    for (OutputConnection connection : selector.select(message, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Float message, String key) {
    for (OutputConnection connection : selector.select(message, key, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Buffer message) {
    for (OutputConnection connection : selector.select(message, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Buffer message, String key) {
    for (OutputConnection connection : selector.select(message, key, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(JsonObject message) {
    for (OutputConnection connection : selector.select(message, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(JsonObject message, String key) {
    for (OutputConnection connection : selector.select(message, key, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(JsonArray message) {
    for (OutputConnection connection : selector.select(message, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(JsonArray message, String key) {
    for (OutputConnection connection : selector.select(message, key, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Byte message) {
    for (OutputConnection connection : selector.select(message, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(Byte message, String key) {
    for (OutputConnection connection : selector.select(message, key, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(byte[] message) {
    for (OutputConnection connection : selector.select(message, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public OutputStream send(byte[] message, String key) {
    for (OutputConnection connection : selector.select(message, key, connections)) {
      connection.send(message);
    }
    return this;
  }

  @Override
  public void close() {
    close(null);
  }

  @Override
  public void close(Handler<AsyncResult<Void>> doneHandler) {
    final CountingCompletionHandler<Void> counter = new CountingCompletionHandler<Void>(connections.size()).setHandler(doneHandler);
    for (OutputConnection connection : connections) {
      connection.close(counter);
    }
  }

}
