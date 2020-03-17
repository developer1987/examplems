package ru.diasoft.micro.common;

import javax.annotation.PreDestroy;

import org.apache.kafka.common.KafkaException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;

@Configuration
public class EmbeddedKafka {

    private Logger logger = LogManager.getLogger();
    private EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, false);
    private static boolean started = false;

    @Value("${spring.kafka.bootstrap-servers:}")
    private String[] servers;
    
    @Bean
    public EmbeddedKafka EmbeddedKafkaBean() {
        if (!started) {            
            try {
                int port = -1;
                if (servers != null && servers.length > 0) {
                    logger.info("servers[0] = " + servers[0]);
                    String[] split = servers[0].split(":");
                    if (split.length == 2) {
                        port = Integer.parseInt(split[1]);
                    }
                }
                if (port != -1) {
                    logger.info("### START EmbeddedKafkaBean ###");
                    embeddedKafka.kafkaPorts(port);
                    embeddedKafka.before();
                    started = true;
                }                
            } catch (Exception e) {
                throw new KafkaException(e);
            }            
        }
        return this;
    }

    @PreDestroy
    public void onExit() {
        if (started) {
            logger.info("### STOP EmbeddedKafkaBean ###");
            try {
                embeddedKafka.getEmbeddedKafka().destroy();
            } catch (Exception e) {
                throw new KafkaException(e);
            }
            started = false;
        }
    }

}
