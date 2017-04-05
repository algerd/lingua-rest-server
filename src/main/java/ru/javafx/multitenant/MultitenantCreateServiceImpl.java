
package ru.javafx.multitenant;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Service;

@Service
@PropertySources({
	@PropertySource(value="classpath:multitenant.properties", ignoreResourceNotFound=true),
	@PropertySource(value="classpath:config/multitenant.properties", ignoreResourceNotFound=true)
})
public class MultitenantCreateServiceImpl implements MultitenantCreateService {
       
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private MultitenantProperties multitenantProperties;
    
    @Autowired
    private DataSource dataSource; 
    
    @Autowired
    private MultitenantData multitenantData; 
    
    @Override
    public void createTenant(Long id) {
        createDb(id);
        multitenantData.setMultitenantId(id);
        createTables();
    }   
        
    private void createTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(); 
        populator.setContinueOnError(true); 
        multitenantProperties.getSqlResources().forEach(resource -> populator.addScript(new ClassPathResource(resource)));
        populator.execute(dataSource);
    }
    
    private void createDb(Long id) {
        String query = "CREATE DATABASE IF NOT EXISTS " + multitenantProperties.getDbPrefix() + id + " CHARACTER SET utf8 COLLATE utf8_general_ci";
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try {
            dataSource.getConnection().createStatement().executeUpdate(query);
            
        } catch (SQLException ex) {
            logger.error("Error executing of query: {}", query);
        }    
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
    }

}
