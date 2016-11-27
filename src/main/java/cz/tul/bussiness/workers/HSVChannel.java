package cz.tul.bussiness.workers;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Created by Bc. Marek Jindrák on 17.10.2016.
 */
public class HSVChannel extends AMethodWorker {
    private static final Logger logger = LoggerFactory.getLogger(HSVChannel.class);

    @Override
    public void work() {
        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        BGR.put(0, 0, sourceData);
        Mat HSV_mat = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        Imgproc.cvtColor(BGR, HSV_mat, Imgproc.COLOR_BGR2HSV_FULL);
        Core.split(HSV_mat, channels);
        sourceData = new byte[BGR.rows() * BGR.cols() * (int) (BGR.elemSize())];
        Imgproc.cvtColor(HSV_mat, HSV_mat, Imgproc.COLOR_HSV2RGB_FULL);
        HSV_mat.get(0, 0, sourceData);
        imgData = new BufferedImage(HSV_mat.cols(), HSV_mat.rows(), BufferedImage.TYPE_3BYTE_BGR);
        imgData.getRaster().setDataElements(0, 0, HSV_mat.cols(), HSV_mat.rows(), sourceData);
        saveImg();
    }
}
