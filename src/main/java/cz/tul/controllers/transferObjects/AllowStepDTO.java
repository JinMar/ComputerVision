package cz.tul.controllers.transferObjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by Bc. Marek Jindrák on 13.03.2017.
 */
public class AllowStepDTO implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(AllowStepDTO.class);
    private String functionName;
    private String methodName;
    private String operationName;

    public AllowStepDTO(String functionName, String methodName, String operationName) {
        this.functionName = functionName;
        this.methodName = methodName;
        this.operationName = operationName;
        logger.info("Allow step was created with: functionName %s, methodName: %s, operationName: %s ", functionName, methodName, operationName);
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }
}
