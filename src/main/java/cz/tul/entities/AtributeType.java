package cz.tul.entities;

/**
 * Created by Marek on 27.09.2016.
 */
public enum AtributeType {
    SIMPLE("SIMPLE"),
    MULTIPLEVALUES("MULTIPLEVALUES");

    private String atributeType;

    AtributeType(String atributeType) {
        this.atributeType = atributeType;
    }

    public String getAtributeType() {
        return atributeType;
    }
}
