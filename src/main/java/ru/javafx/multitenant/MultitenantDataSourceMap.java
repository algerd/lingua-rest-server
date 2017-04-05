package ru.javafx.multitenant;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
@PropertySources({
	@PropertySource(value="classpath:multitenant.properties", ignoreResourceNotFound=true),
	@PropertySource(value="classpath:config/multitenant.properties", ignoreResourceNotFound=true)
})
public class MultitenantDataSourceMap extends HashMap<Object, DataSource> {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private final Map<String, Object> sessionIdMap = new HashMap<>();
    
    @Autowired
    private HttpSession httpSession;
          
    @Override
    public DataSource get(Object key) {
        DataSource value = super.get(key);
        //logger.info("DataSourceMap key: {}", key);
        if (value == null) {                              
            value = createDataSource(key);
            put(key, value);
            sessionIdMap.put(httpSession.getId(), key);         
            //logger.info("New dataSource created: {}", value);                      
        }
        return value;
    }
    
    @Autowired
    private MultitenantProperties multitenantProperties;
    
    private DataSource createDataSource(Object key) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(multitenantProperties.getDbDriverClassName());
        dataSource.setUrl(multitenantProperties.getDbUrl() + multitenantProperties.getDbPrefix() + key);
        dataSource.setUsername(multitenantProperties.getDbUsername());
        dataSource.setPassword(multitenantProperties.getDbPassword());                       
        return dataSource;
    }
    
    public void removeBySessionId(String sessionId) {
        if (sessionIdMap.containsKey(sessionId)) {
            Object keyDataSource = sessionIdMap.get(sessionId);
            sessionIdMap.remove(sessionId);
            if (containsKey(keyDataSource)) {
                remove(keyDataSource);
            }
        }
    }
    
}
