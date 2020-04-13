package org.dromara.soul.admin.config;

import org.I0Itec.zkclient.ZkClient;
import org.dromara.soul.admin.listener.DataChangedListener;
import org.dromara.soul.admin.listener.http.HttpLongPollingDataChangedListener;
import org.dromara.soul.admin.listener.websocket.WebsocketCollector;
import org.dromara.soul.admin.listener.websocket.WebsocketDataChangedListener;
import org.dromara.soul.admin.listener.zookeeper.ZookeeperDataChangedListener;
import org.dromara.soul.configuration.zookeeper.ZookeeperConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * The type Data sync configuration.
 * 类型数据同步配置，目前存在websocket,httplog,zk，根据配置不同获取不同的推送模式
 * @author xiaoyu
 * @author huangxiaofeng
 */
@Configuration
public class DataSyncConfiguration {

    /**
     * http long polling(default strategy).
     * ConditionalOnMissingBean 导入接口
     * Import 实现入值
     */
    @Configuration
    @ConditionalOnMissingBean(DataChangedListener.class)
    @ConditionalOnProperty(name = "soul.sync.strategy", havingValue = "http")
    @Import(HttpLongPollingDataChangedListener.class)
    static class HttpLongPollingListener {
    }

    /**
     * The type Zookeeper listener.
     */
    @Configuration
    @ConditionalOnMissingBean(DataChangedListener.class)
    @ConditionalOnProperty(name = "soul.sync.strategy", havingValue = "zookeeper")
    @Import(ZookeeperConfiguration.class)
    static class ZookeeperListener {

        /**
         * Config event listener data changed listener.
         *
         * @param zkClient the zk client
         * @return the data changed listener
         */
        @Bean
        public DataChangedListener dataChangedListener(final ZkClient zkClient) {
            return new ZookeeperDataChangedListener(zkClient);
        }
    }


    /**
     * The WebsocketListener.
     */
    @Configuration
    @ConditionalOnMissingBean(DataChangedListener.class)
    @ConditionalOnProperty(name = "soul.sync.strategy", havingValue = "websocket", matchIfMissing = true)
    static class WebsocketListener {

        /**
         * Config event listener data changed listener.
         * 配置事件监听器数据更改监听器，使用了后置初始化
         * @return the data changed listener
         */
        @Bean
        public DataChangedListener dataChangedListener() {
            return new WebsocketDataChangedListener();
        }

        /**
         * Websocket collector websocket collector.
         * Websocket收集器，开启站点，
         * @return the websocket collector
         */
        @Bean
        public WebsocketCollector websocketCollector() {
            return new WebsocketCollector();
        }

        /**
         * Server endpoint exporter server endpoint exporter.
         * 服务器端点出口商服务器端点出口商
         * @return the server endpoint exporter
         */
        @Bean
        public ServerEndpointExporter serverEndpointExporter() {
            return new ServerEndpointExporter();
        }
    }

}
