package cz.tul.controllers.transferObjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Bc. Marek Jindrák on 13.03.2017.
 */
public class AllowStepsDTO implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(AllowStepsDTO.class);
    private AllowStepDTO basicStep;
    private List<AllowStepDTO> allowSteps;

    public AllowStepsDTO(AllowStepDTO basicStep, List<AllowStepDTO> allowSteps) {
        this.basicStep = basicStep;
        this.allowSteps = allowSteps;
    }

    public AllowStepDTO getBasicStep() {
        return basicStep;
    }

    public List<AllowStepDTO> getAllowSteps() {
        return allowSteps;
    }
}
