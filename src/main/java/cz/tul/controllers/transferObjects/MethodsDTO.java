package cz.tul.controllers.transferObjects;

import java.io.Serializable;

/**
 * Created by Marek on 02.10.2016.
 */
public class MethodsDTO implements Serializable {
    private String name;
    private String idMethod;


    public MethodsDTO() {
    }

    public void setIdMethod(String idMethod) {
        this.idMethod = idMethod;
    }

    public String getIdMethod() {
        return idMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
