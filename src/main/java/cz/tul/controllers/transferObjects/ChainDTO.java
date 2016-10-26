package cz.tul.controllers.transferObjects;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Marek on 02.10.2016.
 */
public class ChainDTO implements Serializable {
    private String methodId;
    private int position;
    private String URL;
    private List<MethodAttributeDTO> attributes;

    public ChainDTO() {
    }


    //SETTERS
    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setAttributes(List<MethodAttributeDTO> attributes) {
        this.attributes = attributes;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    //GETTERS
    public String getMethodId() {
        return methodId;
    }

    public int getPosition() {
        return position;
    }

    public List<MethodAttributeDTO> getAttributes() {
        return attributes;
    }

    public String getURL() {
        return URL;
    }

    @Override
    public String toString() {
        return "ChainDTO{" +
                "methodId='" + methodId + '\'' +
                ", position=" + position + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
