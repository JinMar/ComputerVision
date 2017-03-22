package cz.tul.bussiness.jobs.Segmentation;

import cz.tul.bussiness.jobs.AJob;
import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.bussiness.jobs.exceptions.NoTemplateFound;
import cz.tul.entities.PartAttributeValue;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bc. Marek Jindrák on 15.03.2017.
 */
public class Thresholding extends AJob {
    private static final Logger logger = LoggerFactory.getLogger(Thresholding.class);
    private int thresholdType;
    private int threshold;

    public Thresholding(int thresholdType) {
        this.thresholdType = thresholdType;
    }

    @Override
    protected void init() throws MinimalArgumentsException, NoTemplateFound {
        for (PartAttributeValue att : getAttributes()) {
            if (att.getOperationAttributes().getAttribute().getName().equals("Práh")) {
                threshold = Integer.parseInt(att.getValue());
            }


        }
    }

    @Override
    protected BufferedImage procces() throws NoTemplateFound {
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        result = new Mat(BGR.rows(), BGR.cols(), CvType.CV_8UC1);
        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR.put(0, 0, sourceData);
        Core.split(BGR, channels);
        Imgproc.threshold(channels.get(0), result, threshold, 255, thresholdType);

        Mat BGR = new Mat(channels.get(0).rows(), channels.get(0).cols(), CvType.CV_8UC3);
        List<Mat> RGB_ = new ArrayList<>();
        RGB_.add(result);
        RGB_.add(result);
        RGB_.add(result);
        Core.merge(RGB_, BGR);
        sourceData = new byte[channels.get(0).rows() * channels.get(0).cols() * (int) BGR.elemSize()];
        BGR.get(0, 0, sourceData);
        imgData = new BufferedImage(channels.get(0).cols(), channels.get(0).rows(), BufferedImage.TYPE_3BYTE_BGR);
        imgData.getRaster().setDataElements(0, 0, channels.get(0).cols(), channels.get(0).rows(), sourceData);
        return imgData;
    }
}
