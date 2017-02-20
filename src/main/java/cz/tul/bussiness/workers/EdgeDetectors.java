package cz.tul.bussiness.workers;

import cz.tul.bussiness.jobs.EdgeDetectors.Canny;
import cz.tul.bussiness.jobs.EdgeDetectors.Laplacian;
import cz.tul.bussiness.jobs.EdgeDetectors.Sobel;
import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.bussiness.workers.enums.EdgeDetectorEnum;
import cz.tul.bussiness.workers.exceptions.SelectionLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 13.02.2017.
 */
public class EdgeDetectors extends AMethodWorker {
    private static final Logger logger = LoggerFactory.getLogger(EdgeDetectors.class);

    @Override
    public void work() throws SelectionLayerException, MinimalArgumentsException {
        if (classifier.equals(EdgeDetectorEnum.SOBEL.getDetectorlName())) {
            job = new Sobel();
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            setImgData(job.start());
        }
        if (classifier.equals(EdgeDetectorEnum.LAPLACIAN.getDetectorlName())) {
            job = new Laplacian();
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            setImgData(job.start());
        }
        if (classifier.equals(EdgeDetectorEnum.CANNY.getDetectorlName())) {
            job = new Canny();
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            setImgData(job.start());
        }


        saveImg();

    }
}
