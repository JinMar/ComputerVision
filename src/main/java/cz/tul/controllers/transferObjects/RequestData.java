package cz.tul.controllers.transferObjects;

import java.io.Serializable;

/**
 * Created by Bc. Marek Jindrák on 04.10.2016.
 */
public class RequestData implements Serializable {

    private String pageAttributeId;
    private String idMethod;

    public RequestData() {
    }

    public String getPageAttributeId() {
        return pageAttributeId;
    }

    public void setPageAttributeId(String pageAttributeId) {
        this.pageAttributeId = pageAttributeId;
    }

    public String getIdMethod() {
        return idMethod;
    }

    public void setIdMethod(String idMethod) {
        this.idMethod = idMethod;
    }
}
