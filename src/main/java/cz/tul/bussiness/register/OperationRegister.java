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
public class OperationRegister implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(OperationRegister.class);
    private static OperationRegister INSTANCE;
    private static Map<String, Class<? extends IMethodWorker>> relation;
    private static Map<String, String> classifier;
    private static String realContextPath;
    private static String original;

    private OperationRegister() {
    }


    public static OperationRegister getInstance() {
        if (INSTANCE == null) {
            relation = new HashMap<>();
            classifier = new HashMap<>();
            INSTANCE = new OperationRegister();

        }
        return INSTANCE;


    }

    /**
     * @param key   - identifikator metody (PK entity Method)
     * @param input - modul, ktery bude vykonavat zpracovani dat
     */
    public void register(String key, Class<? extends IMethodWorker> input, String operationClassifier) throws IllegalInputException {
        if (key == null || input == null || operationClassifier == null) {
            throw new IllegalInputException("One of input parameters is null");
        } else if (key.length() == 0) {
            throw new IllegalInputException("Length of key is 0 !!! Its not supported");
        } else if (relation.containsKey(key)) {
            throw new IllegalInputException("The key %s was already used", key);
        } else {
            relation.put(key, input);
            classifier.put(key, operationClassifier);
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

    public String getClassifier(String key) throws IllegalInputException {
        if (!classifier.containsKey(key)) {
            throw new IllegalInputException("Register does not contains this key %s", key);
        }
        return classifier.get(key);
    }

    public void registerOriginal(String original) {
        OperationRegister.original = original;
    }

    public String getOriginal() throws IllegalInputException {
        if (original == null) {
            throw new IllegalInputException("Id of original image worker is not registred");
        }
        return original;

    }

}
