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
package net.kuujo.vertigo.filter;

import net.kuujo.vertigo.messaging.JsonMessage;

import org.vertx.java.core.json.JsonObject;

/**
 * A source filter.
 *
 * @author Jordan Halterman
 */
public class SourceFilter implements Filter {

  private JsonObject definition = new JsonObject();

  public SourceFilter() {
  }

  public SourceFilter(String source) {
    definition.putString("source", source);
  }

  @Override
  public JsonObject getState() {
    return definition;
  }

  @Override
  public void setState(JsonObject state) {
    definition = state;
  }

  @Override
  public boolean isValid(JsonMessage message) {
    String source = message.source();
    return source != null && source.equals(definition.getString("source"));
  }

}
