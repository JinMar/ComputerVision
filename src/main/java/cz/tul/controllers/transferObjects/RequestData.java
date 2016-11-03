package cz.tul.controllers.transferObjects;

import java.io.Serializable;

/**
 * Created by Bc. Marek Jindrák on 04.10.2016.
 */
public class RequestData implements Serializable {

    private String pageAttributeId;
    private String objectId;
    private String objectId2;

    public RequestData() {
    }

    public String getPageAttributeId() {
        return pageAttributeId;
    }

    public void setPageAttributeId(String pageAttributeId) {
        this.pageAttributeId = pageAttributeId;

    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId2() {
        return objectId2;
    }

    public void setObjectId2(String objectId2) {
        this.objectId2 = objectId2;
    }
}

