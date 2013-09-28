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
package net.kuujo.vitis.test.integration;

/*
import net.kuujo.vitis.Cluster;
import net.kuujo.vitis.Groupings;
import net.kuujo.vitis.LocalCluster;
import net.kuujo.vitis.Networks;
import net.kuujo.vitis.context.NetworkContext;
import net.kuujo.vitis.definition.NetworkDefinition;
import net.kuujo.vitis.java.WorkerVerticle;
import net.kuujo.vitis.messaging.JsonMessage;
import net.kuujo.vitis.node.feeder.PullFeeder;

import org.junit.Test;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonObject;

import static org.vertx.testtools.VertxAssert.assertTrue;
import static org.vertx.testtools.VertxAssert.testComplete;
*/

import org.vertx.testtools.TestVerticle;

/**
 * A vine deploy test.
 *
 * @author Jordan Halterman
 */
public class NetworkTest extends TestVerticle {

  /*
  public static class TestFeeder extends PullFeederVerticle {
    private boolean fed = false;
    @Override
    public void handle(PullFeeder feeder) {
      if (!fed) {
        feeder.feed(new JsonObject().putString("body", "Hello world"), new Handler<AsyncResult<Void>>() {
          @Override
          public void handle(AsyncResult<Void> result) {
            if (result.failed()) {
              assertTrue(result.cause().getMessage(), result.succeeded());
            }
            else {
              assertTrue(result.succeeded());
            }
            testComplete();
          }
        });
        fed = true;
      }
    }
  }

  public static class TestNodeOne extends WorkerVerticle {
    @Override
    public void handle(JsonMessage message) {
      JsonObject body = message.body();
      String hello = body.getString("body");
      emit(new JsonObject().putString("body", hello + "!"), message);
      ack(message);
    }
  }

  public static class TestNodeTwo extends WorkerVerticle {
    @Override
    public void handle(JsonMessage message) {
      JsonObject body = message.body();
      String hello = body.getString("body");
      if (hello.equals("Hello world!")) {
        ack(message);
      }
      else {
        fail(message);
      }
    }
  }

  private NetworkDefinition createSimpleTestDefinition() {
    NetworkDefinition network = Networks.createDefinition("test");
    network.from("feeder", TestFeeder.class.getName())
      .to("nodeone", TestNodeOne.class.getName()).groupBy(Groupings.random())
      .to("nodetwo", TestNodeTwo.class.getName()).groupBy(Groupings.round());
    return network;
  }

  @Test
  public void testLocalSimpleNetwork() {
    Cluster cluster = new LocalCluster(vertx, container);
    NetworkDefinition network = createSimpleTestDefinition();
    cluster.deploy(network, new Handler<AsyncResult<NetworkContext>>() {
      @Override
      public void handle(AsyncResult<NetworkContext> result) {
        if (result.failed()) {
          assertTrue(result.cause().getMessage(), result.succeeded());
        }
        else {
          assertTrue(result.succeeded());
        }
      }
    });
  }
  */

}