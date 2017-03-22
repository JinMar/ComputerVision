package cz.tul.bussiness.workers;

import cz.tul.bussiness.jobs.Segmentation.Thresholding;
import cz.tul.bussiness.jobs.Segmentation.Watershed;
import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.bussiness.jobs.exceptions.NoTemplateFound;
import cz.tul.bussiness.workers.enums.SegmentorEnum;
import cz.tul.bussiness.workers.exceptions.SelectionLayerException;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 27.02.2017.
 */
public class SegmentationW extends AMethodWorker {
    private static final Logger logger = LoggerFactory.getLogger(SegmentationW.class);

    @Override
    public void work() throws SelectionLayerException, MinimalArgumentsException, NoTemplateFound {

        if (classifier.equals(SegmentorEnum.WATERSHED.getSegmentorName())) {
            job = new Watershed();// zvolit spávnou metodu
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            job.setOriginalImgData(originalImageData);
            setImgData(job.start());

        }
        if (classifier.equals(SegmentorEnum.THRESH_BINARY.getSegmentorName())) {
            job = new Thresholding(Imgproc.THRESH_BINARY);// zvolit spávnou metodu
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            job.setOriginalImgData(originalImageData);
            setImgData(job.start());
        }
        if (classifier.equals(SegmentorEnum.THRESH_BINARY_INV.getSegmentorName())) {
            job = new Thresholding(Imgproc.THRESH_BINARY_INV);// zvolit spávnou metodu
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            job.setOriginalImgData(originalImageData);
            setImgData(job.start());
        }
        if (classifier.equals(SegmentorEnum.THRESH_TOZERO.getSegmentorName())) {
            job = new Thresholding(Imgproc.THRESH_TOZERO);// zvolit spávnou metodu
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            job.setOriginalImgData(originalImageData);
            setImgData(job.start());
        }
        if (classifier.equals(SegmentorEnum.THRESH_TRUNC.getSegmentorName())) {
            job = new Thresholding(Imgproc.THRESH_TRUNC);// zvolit spávnou metodu
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            job.setOriginalImgData(originalImageData);
            setImgData(job.start());
        }
        saveImg();
    }
}
