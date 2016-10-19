package cz.tul.bussiness.workers;

import cz.tul.bussiness.workers.enums.SegmentorEnum;
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
 * Created by Bc. Marek Jindrák on 19.10.2016.
 */
public class Segmentation extends Worker {
    private static final Logger logger = LoggerFactory.getLogger(Segmentation.class);
    private int threshold;
    private int typ;
    Mat result;

    @Override
    public void work() {
        int methodType = -1;
        for (PartAttributeValue att : getAttributes()) {
            if (att.getValue().equals(SegmentorEnum.TRESHHOLDING.getSegmentorName())) {
                methodType = 1;
            }
            if (att.getMethodAttributes().getAttribute().getName().equals("Práh")) {
                threshold = Integer.parseInt(att.getValue());
            }
            if (att.getMethodAttributes().getAttribute().getName().equals("Typ")) {
                typ = Integer.parseInt(att.getValue());
            }
        }
        switch (methodType) {
            case 1:
                logger.info("threshold: " + threshold + " type: " + typ);
                thresholding();
                break;
        }

    }


    private void thresholding() {
        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        result = new Mat(BGR.rows(), BGR.cols(), CvType.CV_8UC1);
        BGR.put(0, 0, sourceData);
        Core.split(BGR, channels);
        Imgproc.threshold(channels.get(0), result, threshold, 255, typ);
        save();
    }

    private void save() {
        Mat BGR = new Mat(channels.get(0).rows(), channels.get(0).cols(), CvType.CV_8UC3);
        List<Mat> RGB = new ArrayList<>();
        RGB.add(result);
        RGB.add(result);
        RGB.add(result);
        Core.merge(RGB, BGR);
        sourceData = new byte[channels.get(0).rows() * channels.get(0).cols() * (int) BGR.elemSize()];
        BGR.get(0, 0, sourceData);
        imgData = new BufferedImage(channels.get(0).cols(), channels.get(0).rows(), BufferedImage.TYPE_3BYTE_BGR);
        imgData.getRaster().setDataElements(0, 0, channels.get(0).cols(), channels.get(0).rows(), sourceData);
        saveImg();

    }

}
