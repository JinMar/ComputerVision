package cz.tul.bussiness.workers;

import cz.tul.bussiness.workers.exceptions.SelectionLayerException;
import cz.tul.entities.PartAttributeValue;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Bc. Marek Jindrák on 17.10.2016.
 */
public class NoiseReducer extends Worker {
    private static final Logger logger = LoggerFactory.getLogger(NoiseReducer.class);
    private List<Mat> newChannels;

    @Override
    public void work() throws SelectionLayerException {

        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        BGR.put(0, 0, sourceData);
        Core.split(BGR, channels);
        newChannels = new ArrayList<>();
        for (PartAttributeValue att : getAttributes()) {
            if (att.getValue().equals(NoiseReducerEnum.SIMPLEAVERAGING.getReducerName())) {
                simpleAveraging();
            }
            if (att.getValue().equals(NoiseReducerEnum.MEDIAN.getReducerName())) {
                median();
            }


        }

    }

    private void simpleAveraging() {

        double[] average = new double[1];
        for (Mat channel : channels) {
            Mat simpleAvrFinal = Mat.zeros(channel.rows(), channel.cols(), channel.type());

            for (int i = 1; i < channel.rows() - 2; i++) {
                for (int j = 1; j < channel.cols() - 2; j++) {
                    average[0] = 0;
                    average[0] += channel.get(i - 1, j - 1)[0] / 9;
                    average[0] += channel.get(i - 1, j)[0] / 9;
                    average[0] += channel.get(i - 1, j + 1)[0] / 9;
                    average[0] += channel.get(i, j - 1)[0] / 9;
                    average[0] += channel.get(i, j)[0] / 9;
                    average[0] += channel.get(i, j + 1)[0] / 9;
                    average[0] += channel.get(i + 1, j - 1)[0] / 9;
                    average[0] += channel.get(i + 1, j)[0] / 9;
                    average[0] += channel.get(i + 1, j + 1)[0] / 9;
                    simpleAvrFinal.put(i, j, average);
                }
            }
            newChannels.add(simpleAvrFinal);
        }
        save();

    }

    public void median() {


        List<Double> listOfValues = new ArrayList<Double>();
        Mat SimpleAvrFinal;
        for (Mat channel : channels) {

            SimpleAvrFinal = Mat.zeros(channel.rows(), channel.cols(), channel.type());

            for (int i = 1; i < channel.rows() - 2; i++) {
                for (int j = 1; j < channel.cols() - 2; j++) {
                    listOfValues.add(channel.get(i - 1, j - 1)[0]);
                    listOfValues.add(channel.get(i - 1, j)[0]);
                    listOfValues.add(channel.get(i - 1, j + 1)[0]);
                    listOfValues.add(channel.get(i, j - 1)[0]);
                    listOfValues.add(channel.get(i, j)[0]);
                    listOfValues.add(channel.get(i, j + 1)[0]);
                    listOfValues.add(channel.get(i + 1, j - 1)[0]);
                    listOfValues.add(channel.get(i + 1, j)[0]);
                    listOfValues.add(channel.get(i + 1, j + 1)[0]);

                    Collections.sort(listOfValues);
                    SimpleAvrFinal.put(i, j, listOfValues.get(4));
                    listOfValues.clear();
                }
            }
            newChannels.add(SimpleAvrFinal);
        }
        save();
    }

    private void save() {
        Mat BGR = new Mat(newChannels.get(0).rows(), newChannels.get(0).cols(), CvType.CV_8UC3);
        List<Mat> RGB = new ArrayList<>();
        Core.split(BGR, newChannels);
        RGB.add(newChannels.get(2));
        RGB.add(newChannels.get(1));
        RGB.add(newChannels.get(0));
        Core.merge(RGB, BGR);
        sourceData = new byte[channels.get(0).rows() * channels.get(0).cols() * (int) BGR.elemSize()];
        BGR.get(0, 0, sourceData);
        imgData = new BufferedImage(channels.get(0).cols(), channels.get(0).rows(), BufferedImage.TYPE_3BYTE_BGR);
        imgData.getRaster().setDataElements(0, 0, channels.get(0).cols(), channels.get(0).rows(), sourceData);
        saveImg();

    }
}
