package cz.tul.bussiness.register;

import cz.tul.bussiness.register.exceptions.IllegalInputException;
import cz.tul.bussiness.register.exceptions.NoMethodFoundException;
import cz.tul.bussiness.workers.IMethodWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bc. Marek Jindrák on 13.10.2016.
 */
public class MethodRegister implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(MethodRegister.class);
    private static MethodRegister INSTANCE;
    private static Map<String, Class<? extends IMethodWorker>> relation;
    private static String realContextPath;

    private MethodRegister() {
    }


    public static MethodRegister getInstance() {
        if (INSTANCE == null) {
            relation = new HashMap<>();
            INSTANCE = new MethodRegister();

        }
        return INSTANCE;


    }

    /**
     * @param key   - identifikator metody (PK entity Method)
     * @param input - modul, ktery bude vykonavat zpracovani dat
     */
    public void register(String key, Class<? extends IMethodWorker> input) throws IllegalInputException {
        if (key == null || input == null) {
            throw new IllegalInputException("One of input parameters is null");
        } else if (key.length() == 0) {
            throw new IllegalInputException("Length of key is 0 !!! Its not supported");
        } else if (relation.containsKey(key)) {
            throw new IllegalInputException("The key %s was already used", key);
        } else {
            relation.put(key, input);
        }

    }

    /**
     * Metoda vrací seznam vztahu metody vuci modelu, ktery ji realne vykona
     *
     * @return Map<String, IMethodWorker>
     */
    public Map<String, Class<? extends IMethodWorker>> getRelations() throws NoMethodFoundException {
        if (relation.isEmpty()) {
            throw new NoMethodFoundException();
        }
        return relation;
    }

    public Class<? extends IMethodWorker> getRelation(String key) throws IllegalInputException {
        if (!relation.containsKey(key)) {
            throw new IllegalInputException("Register does not contains this key %s", key);
        }
        return relation.get(key);
    }

    private Object readResolve() {
        return INSTANCE;
    }

    public void registerContextPath(String path) {
        realContextPath = path;
    }

    public String getRealContextPath() {
        return realContextPath;

    }
}
