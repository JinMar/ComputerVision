package cz.tul.bussiness.workflow;

import cz.tul.bussiness.register.MethodFactory;
import cz.tul.bussiness.register.exceptions.IllegalInputException;
import cz.tul.bussiness.workers.IMethodWorker;
import cz.tul.bussiness.workers.exceptions.SelectionLayerException;
import cz.tul.bussiness.workflow.exceptions.NoDataFound;
import cz.tul.entities.Part;
import cz.tul.entities.PartAttributeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * Created by Bc. Marek Jindrák on 16.10.2016.
 */
public class WorkflowStep {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowStep.class);
    private BufferedImage sourceImage = null;
    private Part part;

    public WorkflowStep(BufferedImage sourceImage, Part part, boolean firstStep) throws NoDataFound, SelectionLayerException {
        this.sourceImage = sourceImage;
        this.part = part;
        makeStep(firstStep);
    }

    private void makeStep(boolean firstStep) throws NoDataFound, SelectionLayerException {
        IMethodWorker methodWorker = null;
        if (sourceImage == null && !firstStep) {
            throw new NoDataFound("Couldn't load image data for current step");
        }
        Set<PartAttributeValue> attributesPart = part.getPartAttributeValues();


        try {

            methodWorker = MethodFactory.getMethod(part.getMethod().getMethodId());
            methodWorker.setAttributes(attributesPart);
            methodWorker.setImgName(part.getPartId());
            methodWorker.setImgData(sourceImage);
            methodWorker.work();
        } catch (IllegalInputException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        if (methodWorker != null) {
            sourceImage = methodWorker.getImgData();
        }

    }


    public BufferedImage getData() {
        return sourceImage;
    }
}