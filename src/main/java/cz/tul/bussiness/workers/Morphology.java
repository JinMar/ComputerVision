package cz.tul.bussiness.workers;

import cz.tul.bussiness.workers.enums.MorphologyEnum;
import cz.tul.entities.PartAttributeValue;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
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
public class Morphology extends AMethodWorker {
    private static final Logger logger = LoggerFactory.getLogger(Morphology.class);
    private Mat result;
    @Override
    public void work() {
        int size = 0;
        String shape = "";
        Mat element;
        int morfologyType = -1;

        if (classifier.equals(MorphologyEnum.ERODE.getMorphologyName())) {
            morfologyType = Imgproc.MORPH_ERODE;
        }
        if (classifier.equals(MorphologyEnum.DILATE.getMorphologyName())) {
            morfologyType = Imgproc.MORPH_DILATE;
        }
        if (classifier.equals(MorphologyEnum.OPEN.getMorphologyName())) {
            morfologyType = Imgproc.MORPH_OPEN;
        }
        if (classifier.equals(MorphologyEnum.CLOSE.getMorphologyName())) {
            morfologyType = Imgproc.MORPH_CLOSE;

        }
        if (classifier.equals(MorphologyEnum.TOPHAT.getMorphologyName())) {
            morfologyType = Imgproc.MORPH_TOPHAT;

        }

        for (PartAttributeValue att : getAttributes()) {
            if (att.getOperationAttributes().getAttribute().getName().equals("Velikost")) {
                size = Integer.parseInt(att.getValue());
            }
            if (att.getOperationAttributes().getAttribute().getName().equals("Tvar")) {
                shape = att.getValue();
            }


        }
        element = getElement(size, shape);
        if (morfologyType != 1) {
            processMorfology(morfologyType, element);
        } else {
            throw new IllegalArgumentException("wrong morfologyType: " + morfologyType);
        }

    }

    private void processMorfology(int morphType, Mat element) {
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        result = new Mat(BGR.rows(), BGR.cols(), CvType.CV_8UC1);
        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR.put(0, 0, sourceData);
        Core.split(BGR, channels);
        Imgproc.morphologyEx(channels.get(0), result, morphType, element);
        save();
    }

    private void save() {
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
        saveImg();
    }

    private Mat getElement(int size, String type) {
        Mat result = null;
        if (type.equals("ellipse")) {
            result = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(size, size));
        }
        if (type.equals("rectangle")) {
            result = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(size, size));
        }
        return result;
    }
}
