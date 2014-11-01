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

package net.kuujo.vertigo.io.impl;

import net.kuujo.vertigo.component.InstanceInfo;
import net.kuujo.vertigo.io.InputInfo;
import net.kuujo.vertigo.io.port.InputPortInfo;
import net.kuujo.vertigo.util.Args;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Input info implementation.
 *
 * @author <a href="http://github.com/kuujo">Jordan Halterman</a>
 */
public class InputInfoImpl extends BaseIOInfoImpl<InputInfo> implements InputInfo {
  private Map<String, InputPortInfo> ports = new HashMap<>();

  @Override
  public Collection<InputPortInfo> ports() {
    return ports.values();
  }

  @Override
  public InputPortInfo port(String name) {
    return ports.get(name);
  }

  /**
   * Input info builder.
   */
  public static class Builder implements InputInfo.Builder {
    private final InputInfoImpl input;

    public Builder() {
      input = new InputInfoImpl();
    }

    public Builder(InputInfoImpl input) {
      this.input = input;
    }

    @Override
    public Builder addPort(InputPortInfo port) {
      Args.checkNotNull(port, "port cannot be null");
      input.ports.put(port.name(), port);
      return this;
    }

    @Override
    public Builder removePort(InputPortInfo port) {
      Args.checkNotNull(port, "port cannot be null");
      input.ports.remove(port.name());
      return this;
    }

    @Override
    public Builder setPorts(InputPortInfo... ports) {
      input.ports.clear();
      for (InputPortInfo port : ports) {
        input.ports.put(port.name(), port);
      }
      return this;
    }

    @Override
    public Builder setPorts(Collection<InputPortInfo> ports) {
      Args.checkNotNull(ports, "ports cannot be null");
      input.ports.clear();
      for (InputPortInfo port : ports) {
        input.ports.put(port.name(), port);
      }
      return this;
    }

    @Override
    public Builder clearPorts() {
      input.ports.clear();
      return this;
    }

    @Override
    public Builder setInstance(InstanceInfo instance) {
      Args.checkNotNull(instance, "instance cannot be null");
      input.instance = instance;
      return this;
    }

    @Override
    public InputInfoImpl build() {
      return input;
    }
  }

}