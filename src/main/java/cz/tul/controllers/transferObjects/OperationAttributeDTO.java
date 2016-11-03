package cz.tul.controllers.transferObjects;

/**
 * Created by Bc. Marek Jindrák on 12.10.2016.
 */
public class OperationAttributeDTO {
    private String value;
    private String operationAttributeId;


    public OperationAttributeDTO() {
    }

    public void setOperationAttributeId(String operationAttributeId) {
        this.operationAttributeId = operationAttributeId;
    }
//SETTERS

    public void setValue(String value) {
        this.value = value;
    }


    //GETTERS

    public String getValue() {
        return value;
    }

    public String getOperationAttributeId() {
        return operationAttributeId;
    }

    @Override
    public String toString() {
        return "OperationAttributeDTO{" +
                "value='" + value + '\'' +
                ", operationAttributeId='" + operationAttributeId + '\'' +
                '}';
    }
}
