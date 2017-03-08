package cz.tul.bussiness.jobs;

import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.bussiness.jobs.exceptions.NoTemplateFound;
import cz.tul.entities.PartAttributeValue;
import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Bc. Marek Jindrák on 28.12.2016.
 */
public abstract class AJob implements IJob {
    private static final Logger logger = LoggerFactory.getLogger(AJob.class);
    private Set<PartAttributeValue> attributes = new HashSet<>();
    protected int minimalParams = 0;
    protected BufferedImage imgData;
    protected Mat BGR;
    protected Mat RGB;
    protected Mat result;
    protected byte[] sourceData;
    protected List<Mat> channels = new ArrayList<>();
    protected List<Mat> channelsRGB = new ArrayList<>();
    protected byte[] finalData;
    protected BufferedImage originalImgData;

    @Override
    public void setImgData(BufferedImage imgData) {
        this.imgData = imgData;

    }

    @Override
    public void setOriginalImgData(BufferedImage originalImgData) {
        this.originalImgData = originalImgData;
    }

    @Override
    public void setPartAttributeValue(Set<PartAttributeValue> atrtibuteValues) {
        this.attributes = atrtibuteValues;
    }

    public Set<PartAttributeValue> getAttributes() throws MinimalArgumentsException {
        if (minimalParams > attributes.size()) {
            throw new MinimalArgumentsException("Insufficient number of input parameters. Expected {}, have {}", minimalParams, attributes.size());
        }
        return attributes;
    }

    @Override
    public BufferedImage start() throws MinimalArgumentsException, NoTemplateFound {
        init();
        return procces();
    }

    protected abstract void init() throws MinimalArgumentsException, NoTemplateFound;

    protected abstract BufferedImage procces() throws NoTemplateFound;
}
