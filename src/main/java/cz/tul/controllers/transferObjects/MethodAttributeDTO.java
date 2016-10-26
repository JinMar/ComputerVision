package cz.tul.controllers.transferObjects;

/**
 * Created by Bc. Marek Jindrák on 12.10.2016.
 */
public class MethodAttributeDTO {
    private String value;
    private String methodAttributeId;


    public MethodAttributeDTO() {
    }

    //SETTERS

    public void setValue(String value) {
        this.value = value;
    }

    public void setMethodAttributeId(String methodAttributeId) {
        this.methodAttributeId = methodAttributeId;
    }


    //GETTERS

    public String getValue() {
        return value;
    }

    public String getMethodAttributeId() {
        return methodAttributeId;
    }


    @Override
    public String toString() {
        return "MethodAttributeDTO{" +
                "value='" + value + '\'' +
                ", methodAttributeId='" + methodAttributeId + '\'' +
                '}';
    }
}
