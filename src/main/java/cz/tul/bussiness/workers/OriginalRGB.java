package cz.tul.bussiness.workers;

import cz.tul.entities.PartAttributeValue;
import cz.tul.utilities.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Bc. Marek Jindrák on 16.10.2016.
 */
public class OriginalRGB extends AMethodWorker {
    private static final Logger logger = LoggerFactory.getLogger(OriginalRGB.class);


    @Override
    public void work() {

        String input = "";
        for (PartAttributeValue att : getAttributes()) {
            input = att.getValue();
        }
        String base64Image = input.split(",")[1];
        byte[] imageBytes = Utility.getImageByte(base64Image);

        ImageIO.setUseCache(false);
        try {
            setImgData(ImageIO.read(new ByteArrayInputStream(imageBytes)));
            BufferedImage convertedImg = new BufferedImage(imgData.getWidth(), imgData.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            convertedImg.getGraphics().drawImage(imgData, 0, 0, null);
            setImgData(convertedImg);

        } catch (IOException e) {
            e.printStackTrace();
        }
        saveImg();

    }


}
