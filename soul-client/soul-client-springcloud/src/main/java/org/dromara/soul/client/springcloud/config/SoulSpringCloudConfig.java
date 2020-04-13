/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * Contributor license agreements.See the NOTICE file distributed with
 * This work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * he License.You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.dromara.soul.client.springcloud.config;

import lombok.Data;
import lombok.experimental.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.Valid;

/**
 * The type Soul http config.
 *  获取配置文件，就是一個start，在类加载的时候会启动他，获取配置
 * @author xiaoyu
 */
@Data
@ConfigurationProperties(prefix = "soul.springcloud")
public class SoulSpringCloudConfig {


//    protected static final String PREFIX= "soul.springcloud";
    private String adminUrl;

    private String contextPath;

    private String appName;
}
