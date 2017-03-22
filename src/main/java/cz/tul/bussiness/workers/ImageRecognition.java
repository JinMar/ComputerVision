package cz.tul.bussiness.workers;

import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.bussiness.jobs.exceptions.NoTemplateFound;
import cz.tul.bussiness.jobs.imagerecognition.GrabCutMask;
import cz.tul.bussiness.jobs.imagerecognition.GrabCutRect;
import cz.tul.bussiness.jobs.imagerecognition.RegionIdentification;
import cz.tul.bussiness.jobs.imagerecognition.TemplateMatching;
import cz.tul.bussiness.workers.enums.ExtractForegroundEnum;
import cz.tul.bussiness.workers.enums.SegmentorEnum;
import cz.tul.bussiness.workers.enums.TemplateEnum;
import cz.tul.bussiness.workers.exceptions.SelectionLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 15.03.2017.
 */
public class ImageRecognition extends AMethodWorker {
    private static final Logger logger = LoggerFactory.getLogger(ImageRecognition.class);

    @Override
    public void work() throws SelectionLayerException, MinimalArgumentsException, NoTemplateFound {
        if (classifier.equals(ExtractForegroundEnum.EXTRACT_FOREGROUND.getExtractForegroundName())) {
            job = new GrabCutRect();// zvolit spávnou metodu
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            setImgData(job.start());

        }
        if (classifier.equals(ExtractForegroundEnum.EXTRACT_FOREGROUND_MASK.getExtractForegroundName())) {
            job = new GrabCutMask();// zvolit spávnou metodu
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            setImgData(job.start());

        }
        if (classifier.equals(TemplateEnum.TM.getTemplateName())) {
            job = new TemplateMatching();
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            setImgData(job.start());
        }
        if (classifier.equals(SegmentorEnum.COLORING.getSegmentorName())) {
            job = new RegionIdentification();
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            setImgData(job.start());
        }

        saveImg();
    }
}
