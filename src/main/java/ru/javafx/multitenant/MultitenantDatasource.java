package ru.javafx.multitenant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import ru.javafx.entity.User;

public class MultitenantDatasource extends AbstractDataSource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private MultitenantData multitenantData;      
    private Map<Object, DataSource> resolvedDataSources;    
    private DataSource defaultTargetDataSource;
    private boolean initialized;

    public void setMultitenantData(MultitenantData multitenantData) {
        this.multitenantData = multitenantData;
    }  
    
    protected Object determineCurrentLookupKey() {  
        
        Authentication authentication;
        if (initialized && (authentication = SecurityContextHolder.getContext().getAuthentication()) != null && authentication.isAuthenticated()) {
            logger.info("Multitenant flag: {}", multitenantData.isMultitenantFlag());            

            return !multitenantData.isMultitenantFlag() ? 
                    multitenantData.getMultitenantId() : 
                    ((User) authentication.getPrincipal()).getId();
        }        
        initialized = true;
        return null;
    }

    protected DataSource determineTargetDataSource() {
        Assert.notNull(resolvedDataSources, "DataSource router not initialized");
        Assert.notNull(defaultTargetDataSource, "Default DataSource not initialized");
        
        Object lookupKey = determineCurrentLookupKey();
        logger.info("Multitenant LookupKey: {}", lookupKey == null ? "null" : lookupKey);
        DataSource dataSource = (lookupKey == null) ? defaultTargetDataSource : resolvedDataSources.get(lookupKey);      
        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
        }
        return dataSource;
    }
    
    public void setResolvedDataSources(Map<Object, DataSource> resolvedDataSources) {
        this.resolvedDataSources = resolvedDataSources;
    }
    
    public void setDefaultTargetDataSource(DataSource defaultTargetDataSource) {
		this.defaultTargetDataSource = defaultTargetDataSource;
	}

    @Override
    public Connection getConnection() throws SQLException {
        return determineTargetDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineTargetDataSource().getConnection(username, password);
    }
  
    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        return determineTargetDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface.isInstance(this) || determineTargetDataSource().isWrapperFor(iface));
    }
  
}
