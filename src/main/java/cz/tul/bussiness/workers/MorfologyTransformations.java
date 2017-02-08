package cz.tul.bussiness.workers;

import cz.tul.bussiness.jobs.MorphologyTransformation.MorphologyTransform;
import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.bussiness.workers.enums.MorphologyEnum;
import cz.tul.bussiness.workers.exceptions.SelectionLayerException;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 08.02.2017.
 */
public class MorfologyTransformations extends AMethodWorker {
    private static final Logger logger = LoggerFactory.getLogger(MorfologyTransformations.class);

    @Override
    public void work() throws SelectionLayerException, MinimalArgumentsException {
        if (classifier.equals(MorphologyEnum.ERODE.getMorphologyName())) {
            job = new MorphologyTransform(Imgproc.MORPH_ERODE);// zvolit spávnou metodu
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            setImgData(job.start());
        }

        if (classifier.equals(MorphologyEnum.DILATE.getMorphologyName())) {
            job = new MorphologyTransform(Imgproc.MORPH_DILATE);// zvolit spávnou metodu
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            setImgData(job.start());
        }
        if (classifier.equals(MorphologyEnum.OPEN.getMorphologyName())) {
            job = new MorphologyTransform(Imgproc.MORPH_OPEN);// zvolit spávnou metodu
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            setImgData(job.start());
        }
        if (classifier.equals(MorphologyEnum.CLOSE.getMorphologyName())) {
            job = new MorphologyTransform(Imgproc.MORPH_CLOSE);// zvolit spávnou metodu
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            setImgData(job.start());

        }
        if (classifier.equals(MorphologyEnum.TOPHAT.getMorphologyName())) {
            job = new MorphologyTransform(Imgproc.MORPH_TOPHAT);// zvolit spávnou metodu
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            setImgData(job.start());

        }
        saveImg();
    }
}