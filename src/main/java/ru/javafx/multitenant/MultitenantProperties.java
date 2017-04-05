
package ru.javafx.multitenant;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "multitenant")
public class MultitenantProperties {
    @NotNull
    private String dbDriverClassName;
    private String dbUsername;
    private String dbPassword;
    private String dbUrl = "db_";
    @NotNull
    private String dbPrefix;
    private List<String> sqlResources = new ArrayList<>();
    private String basePath;   
    private final List<String> fullPaths = new ArrayList<>();
    private final List<String> paths = new ArrayList<>();

    public String getDbDriverClassName() {
        return dbDriverClassName;
    }

    public void setDbDriverClassName(String dbDriverClassName) {
        this.dbDriverClassName = dbDriverClassName;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
    
    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbPrefix() {
        return dbPrefix;
    }

    public void setDbPrefix(String dbPrefix) {
        this.dbPrefix = dbPrefix;
    }

    public List<String> getSqlResources() {
        return sqlResources;
    }

    public String getBasePath() {
        return basePath;
    }
    
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
       
    public List<String> getFullPaths() {
        return fullPaths;
    }

    public List<String> getPaths() {
        return paths;
    }   
     
}
