package cz.tul.controllers.transferObjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Bc. Marek Jindrák on 26.10.2016.
 */
public class PartData {
    private static final Logger logger = LoggerFactory.getLogger(PartData.class);
    private List<PartValue> partValueList;
    private String URL;
    private String methodId;
    private int position;


    public void setURL(String URL) {
        this.URL = URL;
    }


    public void setPartValueList(List<PartValue> partValueList) {
        this.partValueList = partValueList;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public String getURL() {
        return URL;
    }

    public List<PartValue> getPartValueList() {
        return partValueList;
    }

    public String getMethodId() {
        return methodId;
    }

    public int getPosition() {
        return position;
    }
}
