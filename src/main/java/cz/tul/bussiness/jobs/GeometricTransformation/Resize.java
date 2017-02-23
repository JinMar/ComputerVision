package cz.tul.bussiness.jobs.GeometricTransformation;

import cz.tul.bussiness.jobs.AJob;
import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.entities.PartAttributeValue;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Created by Bc. Marek Jindrák on 28.12.2016.
 */
public class Resize extends AJob {
    private static final Logger logger = LoggerFactory.getLogger(Resize.class);
    private double size = 0;


    @Override
    protected void init() throws MinimalArgumentsException {
        int tmp = 0;
        for (PartAttributeValue att : getAttributes()) {
            if (att.getOperationAttributes().getAttribute().getName().equals("Velikost")) {
                tmp = Integer.parseInt(att.getValue());
                size = tmp / 100.0;
            }


        }
    }

    @Override
    protected BufferedImage procces() {
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        result = new Mat((int) (BGR.rows() * size), (int) (BGR.cols() * size), CvType.CV_8UC3);
        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR.put(0, 0, sourceData);

        Imgproc.resize(BGR, result, result.size());

        Core.split(result, channels);
        channelsRGB.add(channels.get(2));
        channelsRGB.add(channels.get(1));
        channelsRGB.add(channels.get(0));
        Core.merge(channelsRGB, result);
        finalData = new byte[(int) (result.total()) * result.channels()];
        result.get(0, 0, finalData);
        BufferedImage resultImgData = new BufferedImage(result.cols(), result.rows(), BufferedImage.TYPE_3BYTE_BGR);
        resultImgData.getRaster().setDataElements(0, 0, result.cols(), result.rows(), finalData);
        return resultImgData;
    }
}
