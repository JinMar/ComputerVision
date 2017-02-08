package cz.tul.bussiness.jobs;

import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.entities.PartAttributeValue;

import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * Created by Bc. Marek Jindrák on 28.12.2016.
 */
public interface IJob {
    BufferedImage start() throws MinimalArgumentsException;

    void setPartAttributeValue(Set<PartAttributeValue> attributeValues);

    void setImgData(BufferedImage imgData);
}
