package cz.tul.bussiness.workers;

/**
 * Created by Bc. Marek Jindrák on 17.10.2016.
 */
public enum NoiseReducerEnum {


    SIMPLEAVERAGING("simpleaveraging"),
    MEDIAN("median");


    private String reducerName;

    NoiseReducerEnum(String key) {
        this.reducerName = key;
    }

    public String getReducerName() {
        return reducerName;
    }

}
