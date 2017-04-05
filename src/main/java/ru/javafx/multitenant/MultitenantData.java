
package ru.javafx.multitenant;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Component
@Scope(value = SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MultitenantData {

    private boolean multitenantFlag = false;
    private Long multitenantId = null;

    public boolean isMultitenantFlag() {
        return multitenantFlag;
    }

    public void setMultitenantFlag(boolean multitenantFlag) {
        this.multitenantFlag = multitenantFlag;
    }

    public Long getMultitenantId() {
        return multitenantId;
    }

    public void setMultitenantId(Long multitenantId) {
        this.multitenantId = multitenantId;
    }  
    
}
