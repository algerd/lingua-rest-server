package ru.javafx.multitenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class MultitenantConfiguration {
      
    @Bean
    public MultitenantDataSourceMap multitenantDataSourceMap() {
        return new MultitenantDataSourceMap();
    }
  
    @Autowired
    private MultitenantData multitenantData;
    
    @Bean
    public DataSource dataSource() {
        MultitenantDatasource dataSource = new MultitenantDatasource();
        dataSource.setDefaultTargetDataSource(defaultDataSource());
        dataSource.setResolvedDataSources(multitenantDataSourceMap());
        dataSource.setMultitenantData(multitenantData);       
        return dataSource;
    }

    @Autowired
    private DataSourceProperties properties;
    
    private DataSource defaultDataSource() {
        DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader())
                .driverClassName(properties.getDriverClassName())
                .url(properties.getUrl())
                .username(properties.getUsername())
                .password(properties.getPassword());

        if(properties.getType() != null) {
            dataSourceBuilder.type(properties.getType());
        }
        return dataSourceBuilder.build();
    }
}
