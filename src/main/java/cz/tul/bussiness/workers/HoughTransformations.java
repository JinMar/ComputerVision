package cz.tul.bussiness.workers;

import cz.tul.bussiness.jobs.HoughTransformation.CircleTransform;
import cz.tul.bussiness.jobs.HoughTransformation.LineTransform;
import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.bussiness.jobs.exceptions.NoTemplateFound;
import cz.tul.bussiness.workers.enums.HoughTransformationEnum;
import cz.tul.bussiness.workers.exceptions.SelectionLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 15.03.2017.
 */
public class HoughTransformations extends AMethodWorker {
    private static final Logger logger = LoggerFactory.getLogger(HoughTransformations.class);

    @Override
    public void work() throws SelectionLayerException, MinimalArgumentsException, NoTemplateFound {
        if (classifier.equals(HoughTransformationEnum.CIRCLE.getGgometricTransformationName())) {
            job = new CircleTransform();
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            setImgData(job.start());
        }
        if (classifier.equals(HoughTransformationEnum.LINE.getGgometricTransformationName())) {
            job = new LineTransform();
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            setImgData(job.start());
        }


        saveImg();
    }
}
