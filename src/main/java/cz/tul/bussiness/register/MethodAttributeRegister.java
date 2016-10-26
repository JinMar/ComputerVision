package cz.tul.bussiness.register;

import cz.tul.bussiness.register.exceptions.IllegalInputException;
import cz.tul.bussiness.workflow.exceptions.NoDataFound;
import cz.tul.provisioner.holder.DataHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bc. Marek Jindrák on 26.10.2016.
 */
public class MethodAttributeRegister {
    private static final Logger logger = LoggerFactory.getLogger(MethodAttributeRegister.class);
    private static MethodAttributeRegister INSTANCE;
    private static Map<String, DataHolder> relation;

    private MethodAttributeRegister() {

    }

    public static MethodAttributeRegister getInstance() {
        if (INSTANCE == null) {
            relation = new HashMap<>();
            INSTANCE = new MethodAttributeRegister();
        }
        return INSTANCE;
    }

    /**
     * data je mozne cist rovnou z db, v ramci optimalizace zbytecnych dotazu tuto variantu nepouzivam
     *
     * @param key  - identifikator atributu metody
     * @param data - atributy
     */
    public void register(String key, DataHolder data) throws IllegalInputException {
        if (key == null || data == null) {
            throw new IllegalInputException("Input parameters can not null");
        }
        relation.put(key, data);
    }

    public DataHolder getData(String key) throws NoDataFound {
        DataHolder result = relation.get(key);
        if (result == null) {
            throw new NoDataFound("Can not find data for key:" + key);
        }
        return result;
    }
}
