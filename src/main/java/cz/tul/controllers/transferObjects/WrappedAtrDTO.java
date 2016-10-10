package cz.tul.controllers.transferObjects;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Marek on 02.10.2016.
 */
public class WrappedAtrDTO implements Serializable {
    private List<AttributesDTO> attributesDTOs;
    private String pageAttributeId;


    public WrappedAtrDTO() {
    }

    public WrappedAtrDTO(List<AttributesDTO> attributesDTOs, String pageAttributeId) {
        this.attributesDTOs = attributesDTOs;
        this.pageAttributeId = pageAttributeId;
    }


    //SETTERS
    public void setPageAttributeId(String pageAttributeId) {

        this.pageAttributeId = pageAttributeId;
    }

    public void setAttributesDTOs(List<AttributesDTO> attributesDTOs) {
        this.attributesDTOs = attributesDTOs;
    }


    //GETTERS


    public String getPageAttributeId() {
        return pageAttributeId;
    }

    public List<AttributesDTO> getAttributesDTOs() {
        return attributesDTOs;
    }
}
