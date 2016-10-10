package cz.tul.controllers.transferObjects;

import java.io.Serializable;

/**
 * Created by Marek on 02.10.2016.
 */
public class DTO implements Serializable {
    private String methodId;

    private int position;


    public DTO() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    @Override
    public String toString() {
        return "DTO{" +
                "methodId='" + methodId + '\'' +
                ", position=" + position +
                '}';
    }
}
