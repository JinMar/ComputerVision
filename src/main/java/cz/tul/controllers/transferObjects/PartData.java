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
    List<ListDataDTO> operations;
    List<ListDataDTO> methods;
    private String URL;
    private String hURL;
    private String mURL;
    private String methodId;
    private String functionId;
    private String operationId;
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

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getOperationId() {
        return operationId;
    }

    public List<ListDataDTO> getOperations() {
        return operations;
    }

    public void setOperations(List<ListDataDTO> operations) {
        this.operations = operations;
    }

    public List<ListDataDTO> getMethods() {
        return methods;
    }

    public void setMethods(List<ListDataDTO> methods) {
        this.methods = methods;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
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

    public String gethURL() {
        return hURL;
    }

    public void sethURL(String hURL) {
        this.hURL = hURL;
    }

    public String getmURL() {
        return mURL;
    }

    public void setmURL(String mURL) {
        this.mURL = mURL;
    }
}
