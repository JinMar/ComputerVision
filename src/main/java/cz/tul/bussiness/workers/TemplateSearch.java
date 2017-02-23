package cz.tul.bussiness.workers;

import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.bussiness.jobs.exceptions.NoTemplateFound;
import cz.tul.bussiness.jobs.templateMatching.TemplateMatching;
import cz.tul.bussiness.workers.enums.TemplateEnum;
import cz.tul.bussiness.workers.exceptions.SelectionLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 20.02.2017.
 */
public class TemplateSearch extends AMethodWorker {
    private static final Logger logger = LoggerFactory.getLogger(TemplateSearch.class);

    @Override
    public void work() throws SelectionLayerException, MinimalArgumentsException, NoTemplateFound {
        if (classifier.equals(TemplateEnum.TM.getTemplateName())) {
            job = new TemplateMatching();
            job.setPartAttributeValue(getAttributes());
            job.setImgData(imgData);
            setImgData(job.start());
        }
        saveImg();
    }
}
