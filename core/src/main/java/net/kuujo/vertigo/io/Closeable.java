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
package net.kuujo.vertigo.io;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;

/**
 * Closeable type.
 *
 * @author <a href="http://github.com/kuujo">Jordan Halterman</a>
 *
 * @param <T> The closeable type.
 */
public interface Closeable<T extends Closeable<T>> extends AutoCloseable {

  /**
   * Closes the stream.
   */
  void close();

  /**
   * Closes the stream.
   *
   * @param doneHandler An asynchronous handler to be called once complete.
   */
  void close(Handler<AsyncResult<Void>> doneHandler);

}
