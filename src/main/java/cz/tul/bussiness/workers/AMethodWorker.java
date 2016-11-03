package cz.tul.bussiness.workers;

import cz.tul.bussiness.register.OperationRegister;
import cz.tul.entities.PartAttributeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Bc. Marek Jindrák on 17.10.2016.
 */
public abstract class AMethodWorker implements IMethodWorker {
    private static final Logger logger = LoggerFactory.getLogger(AMethodWorker.class);
    protected Set<PartAttributeValue> attributes;
    protected String imgName;
    protected BufferedImage imgData;
    protected String classifier;

    @Override
    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    @Override
    public String getRealPath() {
        return OperationRegister.getInstance().getRealContextPath();
    }

    @Override
    public BufferedImage getImgData() {
        return imgData;
    }

    @Override
    public void setAttributes(Set<PartAttributeValue> attributes) {
        this.attributes = attributes;
    }

    @Override
    public void setImgName(String name) {
        this.imgName = name;
    }

    public String getImgName() {
        return imgName;
    }


    @Override
    public void setImgData(BufferedImage imgData) {
        this.imgData = imgData;
    }

    public Set<PartAttributeValue> getAttributes() {
        return attributes;
    }

    @Override
    public void saveImg() {
        File outputFile = null;
        outputFile = new File(getRealPath() + "\\img\\" + getImgName() + ".jpg");
        logger.info("Output" + getRealPath() + "\\img\\" + getImgName() + ".jpg");
        try {
            ImageIO.write(imgData, "jpg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
