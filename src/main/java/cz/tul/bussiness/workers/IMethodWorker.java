package cz.tul.bussiness.workers;

import cz.tul.bussiness.workers.exceptions.SelectionLayerException;
import cz.tul.entities.PartAttributeValue;

import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * Created by Bc. Marek Jindrák on 13.10.2016.
 */
public interface IMethodWorker {
    String getRealPath();

    void work() throws SelectionLayerException;

    void setAttributes(Set<PartAttributeValue> attributes);

    void setImgName(String name);

    BufferedImage getImgData();

    void setImgData(BufferedImage imgData);

    void saveImg();

    void setClassifier(String classifier);
}
