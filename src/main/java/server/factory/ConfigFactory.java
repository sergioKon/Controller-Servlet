package server.factory;

import com.zaxxer.hikari.HikariConfig;
import server.base.config.YamlReader;

public class ConfigFactory {

        public static  HikariConfig createHikariConfig(YamlReader yamlReader){
            HikariConfig hikariConfig=new HikariConfig();
            hikariConfig.setUsername(yamlReader.getUsername());
            hikariConfig.setPassword(yamlReader.getPassword());
            hikariConfig.setJdbcUrl(yamlReader.getUrl());
            hikariConfig.setDriverClassName(yamlReader.getDriverClassName());
            hikariConfig.setMaximumPoolSize(yamlReader.getPoolSize());
            return  hikariConfig;
        }
}

