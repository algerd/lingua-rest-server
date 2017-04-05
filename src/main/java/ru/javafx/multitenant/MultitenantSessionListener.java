
package ru.javafx.multitenant;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MultitenantSessionListener implements HttpSessionListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());
       
    @Autowired
    private MultitenantDataSourceMap dataSourceMap;
    
    @Value("${server.session.timeout}")
    private int serverSessionTimeout;
        
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        session.setMaxInactiveInterval(serverSessionTimeout);
        //logger.info("Session created: {}", session.getId());       
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        String sessionId = event.getSession().getId();       
        dataSourceMap.removeBySessionId(sessionId);        
        //logger.info("Session destroyed: {}", sessionId);
    }

}
