package cz.tul.bussiness.workers;

import cz.tul.bussiness.workers.enums.ChannelsEnum;
import cz.tul.bussiness.workers.exceptions.SelectionLayerException;
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
 * Created by Bc. Marek Jindrák on 17.10.2016.
 */
public class YCBCRChannel extends Worker {
    private static final Logger logger = LoggerFactory.getLogger(YCBCRChannel.class);

    @Override
    public void work() throws SelectionLayerException {
        int layer = -1;
        for (PartAttributeValue att : getAttributes()) {
            if (att.getValue().equals(ChannelsEnum.Y.getChannelName())) {
                layer = 0;
            }
            if (att.getValue().equals(ChannelsEnum.CB.getChannelName())) {
                layer = 2;
            }
            if (att.getValue().equals(ChannelsEnum.CR.getChannelName())) {
                layer = 1;
            }
        }
        if (layer < 0) {
            throw new SelectionLayerException("Cannot select layer id: " + layer);
        }

        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        BGR.put(0, 0, sourceData);
        Mat YCBCR_mat = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        Imgproc.cvtColor(BGR, YCBCR_mat, Imgproc.COLOR_BGR2YCrCb);
        List<Mat> layers = new ArrayList<>();
        Core.split(YCBCR_mat, channels);
        layers.add(channels.get(layer));
        layers.add(channels.get(layer));
        layers.add(channels.get(layer));
        Core.merge(layers, BGR);
        sourceData = new byte[YCBCR_mat.rows() * YCBCR_mat.cols() * (int) (YCBCR_mat.elemSize())];
        BGR.get(0, 0, sourceData);
        imgData = new BufferedImage(YCBCR_mat.cols(), YCBCR_mat.rows(), BufferedImage.TYPE_3BYTE_BGR);
        imgData.getRaster().setDataElements(0, 0, YCBCR_mat.cols(), YCBCR_mat.rows(), sourceData);

        saveImg();
    }
}
