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
package net.kuujo.vertigo.output.selector;

import java.util.List;

import net.kuujo.vertigo.output.OutputConnection;

/**
 * A *round* selector.
 *
 * The *round* selector dispatches messages to workers in a round-robin
 * fashion.
 *
 * @author Jordan Halterman
 */
public class RoundSelector implements Selector {
  private int current;

  public RoundSelector() {
  }

  @Override
  public List<OutputConnection> select(Object message, List<OutputConnection> connections) {
    int index = current++ % connections.size();
    return connections.subList(index, index+1);
  }

  @Override
  public List<OutputConnection> select(Object message, String key, List<OutputConnection> connections) {
    return select(message, connections);
  }

}
