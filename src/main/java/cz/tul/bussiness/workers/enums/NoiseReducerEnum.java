package cz.tul.bussiness.workers.enums;

/**
 * Created by Bc. Marek Jindrák on 17.10.2016.
 */
public enum NoiseReducerEnum {


    SIMPLEAVERAGING("simpleaveraging"),
    MEDIAN("median"),
    ROTATINGMASK("rotatingmask");


    private String reducerName;

    NoiseReducerEnum(String key) {
        this.reducerName = key;
    }

    public String getReducerName() {
        return reducerName;
    }

}
