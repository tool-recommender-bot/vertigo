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
package net.kuujo.vitis.messaging;

import java.util.Random;

/**
 * A random dispatcher implementation.
 *
 * @author Jordan Halterman
 */
public class RandomDispatcher extends AbstractDispatcher {

  private Connection[] connections;

  private int connectionSize;

  private Random rand = new Random();

  @Override
  public void init(ConnectionPool<?> connections) {
    this.connections = connections.toArray(new Connection[]{});
    connectionSize = connections.size();
  }

  @Override
  protected Connection getConnection(JsonMessage message) {
    return connections[rand.nextInt(connectionSize)];
  }

}