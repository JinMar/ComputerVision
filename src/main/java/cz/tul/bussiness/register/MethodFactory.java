package cz.tul.bussiness.register;

import cz.tul.bussiness.register.exceptions.IllegalInputException;
import cz.tul.bussiness.workers.IMethodWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 13.10.2016.
 */
public class MethodFactory {
    private static final Logger logger = LoggerFactory.getLogger(MethodFactory.class);


    public static IMethodWorker getMethod(String operationId) throws IllegalInputException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        OperationRegister operationRegister = OperationRegister.getInstance();
        String className = operationRegister.getRelation(operationId).getName();
        String classifier = operationRegister.getClassifier(operationId);

        IMethodWorker result = (IMethodWorker) Class.forName(className).newInstance();
        result.setClassifier(classifier);
        return result;
    }
}
