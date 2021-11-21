package server.base.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import server.factory.ConfigFactory;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@Log4j2
public class DataSourceMySqlConfig {

    @Autowired
    MySqlYamlReader yamlReader;

    public DataSource getDataSource() {
        HikariDataSource dataSource= new HikariDataSource(ConfigFactory.createHikariConfig(yamlReader));
        log.debug(" connection for driver  {} succeed",dataSource.getDriverClassName());
        return dataSource;
    }
}
