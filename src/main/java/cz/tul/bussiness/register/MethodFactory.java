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


    public static IMethodWorker getMethod(String methodId) throws IllegalInputException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        MethodRegister methodRegister = MethodRegister.getInstance();
        String className = methodRegister.getRelation(methodId).getName();


        return (IMethodWorker) Class.forName(className).newInstance();
    }
}
