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
package net.kuujo.vertigo.messaging;

import net.kuujo.vertigo.serializer.Serializable;

import org.vertx.java.core.json.JsonObject;

/**
 * An eventbus message.
 *
 * @author Jordan Halterman
 */
public interface JsonMessage extends Serializable {

  /**
   * Returns the message ID.
   *
   * @return
   *   The message ID.
   */
  String id();

  /**
   * Returns the message source address.
   *
   * @return
   *   The source address.
   */
  String source();

  /**
   * Returns the parent ID.
   *
   * @return
   *   The parent message ID.
   */
  String parent();

  /**
   * Returns the ancestor ID.
   *
   * @return
   *   The ancestor message ID.
   */
  String ancestor();

  /**
   * Returns the auditor address.
   *
   * @return
   *   The message's auditor address.
   */
  String auditor();

  /**
   * Returns the message body.
   *
   * @return
   *   The message body.
   */
  JsonObject body();

  /**
   * Returns the message tag.
   *
   * @return
   *   A message tag.
   */
  String tag();

  /**
   * Creates a new child of the message with the same message data.
   *
   * @return
   *   A new child message.
   */
  JsonMessage createChild();

  /**
   * Creates a new child of the message.
   *
   * @param body
   *   The child body.
   * @return
   *   A new child message.
   */
  JsonMessage createChild(JsonObject body);

  /**
   * Creates a new child of the message.
   *
   * @param body
   *   The child body.
   * @param tag
   *   A tag to apply to the child. If no tag is specified then the
   *   parent tag will be inherited.
   * @return
   *   A new child message.
   */
  JsonMessage createChild(JsonObject body, String tag);

  /**
   * Creates a copy of the message.
   *
   * @return
   *   A copy of the message.
   */
  JsonMessage copy();

}
