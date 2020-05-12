package birintsev.sumdu.edu.ua.dbpr8;

import birintsev.sumdu.edu.ua.dbpr8.controller.ConsoleCommandsController;
import birintsev.sumdu.edu.ua.dbpr8.controller.ConsoleControllerImpl;
import birintsev.sumdu.edu.ua.dbpr8.service.ScanService;
import birintsev.sumdu.edu.ua.dbpr8.service.imp.ScanServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    @Bean
    public DataSource dataSource(@Value(value = "${spring.datasource.url}") String url,
                                 @Value(value = "${spring.datasource.username}") String username,
                                 @Value(value = "${spring.datasource.password}") String password) {
        return new DriverManagerDataSource(url, username, password);
    }

    @Bean
    public ConsoleCommandsController consoleCommandsController(ScanService service) {
        return new ConsoleControllerImpl(service);
    }

    @Bean
    public ScanService scanService(DataSource dataSource) {
        return new ScanServiceImp(dataSource);
    }
}
