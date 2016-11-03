package cz.tul.controllers.transferObjects;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Marek on 02.10.2016.
 */
public class ChainDTO implements Serializable {
    private String operationId;
    private String methodId;
    private String functionId;
    private int position;
    private String URL;
    private List<OperationAttributeDTO> attributes;

    public ChainDTO() {
    }


    //SETTERS


    public void setPosition(int position) {
        this.position = position;
    }

    public void setAttributes(List<OperationAttributeDTO> attributes) {
        this.attributes = attributes;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }
    //GETTERS

    public String getOperationId() {
        return operationId;
    }

    public int getPosition() {
        return position;
    }

    public List<OperationAttributeDTO> getAttributes() {
        return attributes;
    }

    public String getURL() {
        return URL;
    }

    public String getMethodId() {
        return methodId;
    }

    public String getFunctionId() {
        return functionId;
    }

    @Override
    public String toString() {
        return "ChainDTO{" +
                ", position=" + position + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
