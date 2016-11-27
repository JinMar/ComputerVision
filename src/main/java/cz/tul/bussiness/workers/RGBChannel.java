package cz.tul.bussiness.workers;

import cz.tul.bussiness.workers.enums.ChannelsEnum;
import cz.tul.bussiness.workers.exceptions.SelectionLayerException;
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
 * Created by Bc. Marek Jindrák on 16.10.2016.
 */
public class RGBChannel extends AMethodWorker {
    private static final Logger logger = LoggerFactory.getLogger(RGBChannel.class);

    @Override
    public void work() throws SelectionLayerException {
        int layer = -1;

        if (classifier.equals(ChannelsEnum.RED.getChannelName())) {
            layer = 2;
        }
        if (classifier.equals(ChannelsEnum.GREEN.getChannelName())) {
            layer = 1;
        }
        if (classifier.equals(ChannelsEnum.BLUE.getChannelName())) {
            layer = 0;
        }
        if (classifier.equals(ChannelsEnum.GRAY.getChannelName())) {
            layer = 3;


        }
        if (layer < 0) {
            throw new SelectionLayerException("Cannot select layer id: " + layer);
        }
        switch (layer) {
            case 0:
            case 1:
            case 2:

                sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
                BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
                List<Mat> RGB = new ArrayList<Mat>();
                BGR.put(0, 0, sourceData);
                Core.split(BGR, channels);
                RGB.add(channels.get(layer));
                RGB.add(channels.get(layer));
                RGB.add(channels.get(layer));
                Core.merge(RGB, BGR);
                sourceData = new byte[BGR.rows() * BGR.cols() * (int) (BGR.elemSize())];
                BGR.get(0, 0, sourceData);
                imgData = new BufferedImage(BGR.cols(), BGR.rows(), BufferedImage.TYPE_3BYTE_BGR);
                imgData.getRaster().setDataElements(0, 0, BGR.cols(), BGR.rows(), sourceData);
                break;
            case 3:
                sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
                BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
                BGR.put(0, 0, sourceData);
                Mat gray = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
                Imgproc.cvtColor(BGR, gray, Imgproc.COLOR_RGB2GRAY);
                Core.split(gray, channels);
                List<Mat> grayLayers = new ArrayList<>();
                grayLayers.add(channels.get(0));
                grayLayers.add(channels.get(0));
                grayLayers.add(channels.get(0));
                Core.merge(grayLayers, BGR);
                sourceData = new byte[gray.rows() * gray.cols() * (int) (BGR.elemSize())];
                BGR.get(0, 0, sourceData);
                imgData = new BufferedImage(gray.cols(), gray.rows(), BufferedImage.TYPE_3BYTE_BGR);
                imgData.getRaster().setDataElements(0, 0, gray.cols(), gray.rows(), sourceData);

        }
        saveImg();
    }
}
