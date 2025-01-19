package com.tsc.sfgjms;

import org.apache.activemq.artemis.api.core.management.ActiveMQServerControl;
import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.ActiveMQServers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SfgJmsApplication {

    public static void main(String[] args) throws Exception {
        ActiveMQServer server = ActiveMQServers.newActiveMQServer(
                new ConfigurationImpl()
                        .setPersistenceEnabled(true)
                        .setJournalDirectory("target/data/journal")
                        .setSecurityEnabled(false)
                        .addAcceptorConfiguration("invm", "vm://0")
        );

        server.start();
        SpringApplication.run(SfgJmsApplication.class, args);
    }

}
