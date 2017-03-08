package cz.tul.bussiness.workers;

import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.bussiness.jobs.exceptions.NoTemplateFound;
import cz.tul.bussiness.jobs.functions.GrabCutMask;
import cz.tul.bussiness.jobs.functions.GrabCutRect;
import cz.tul.bussiness.workers.enums.ExtractForegroundEnum;
import cz.tul.bussiness.workers.exceptions.SelectionLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 07.03.2017.
 */
public class ExtractForeground extends AMethodWorker {
    private static final Logger logger = LoggerFactory.getLogger(SegmentationW.class);

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

        saveImg();
    }

}
