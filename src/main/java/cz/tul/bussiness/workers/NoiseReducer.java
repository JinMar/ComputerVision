package cz.tul.bussiness.workers;

import cz.tul.bussiness.workers.enums.NoiseReducerEnum;
import cz.tul.bussiness.workers.exceptions.SelectionLayerException;
import cz.tul.bussiness.workers.helper.Dispersion;
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
public class NoiseReducer extends AMethodWorker {
    private static final Logger logger = LoggerFactory.getLogger(NoiseReducer.class);
    private List<Mat> newChannels;

    @Override
    public void work() throws SelectionLayerException {

        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        BGR.put(0, 0, sourceData);
        Core.split(BGR, channels);
        newChannels = new ArrayList<>();


        if (classifier.equals(NoiseReducerEnum.SIMPLEAVERAGING.getReducerName())) {
            simpleAveraging();
        }
        if (classifier.equals(NoiseReducerEnum.MEDIAN.getReducerName())) {
            median();
        }
        if (classifier.equals(NoiseReducerEnum.ROTATINGMASK.getReducerName())) {
            rotatingMask();
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

    public void rotatingMask() {

        double values[][] = new double[3][3];
        List<Dispersion> listOfDispersion = new ArrayList<>();
        Mat SimpleAvrFinal;
        double min, minrozpt;

        double[] average = new double[1];
        for (Mat channel : channels) {

            SimpleAvrFinal = Mat.zeros(channel.rows(), channel.cols(), channel.type());

            for (int i = 2; i < channel.rows() - 3; i++) {
                for (int j = 2; j < channel.cols() - 3; j++) {

                    values[0][0] = channel.get(i - 2, j - 2)[0];
                    values[0][1] = channel.get(i - 2, j - 1)[0];
                    values[0][2] = channel.get(i - 2, j)[0];
                    values[1][0] = channel.get(i - 1, j - 2)[0];
                    values[1][1] = channel.get(i - 1, j - 1)[0];
                    values[1][2] = channel.get(i - 1, j)[0];
                    values[2][0] = channel.get(i, j - 2)[0];
                    values[2][1] = channel.get(i, j - 1)[0];
                    values[2][2] = channel.get(i, j)[0];
                    listOfDispersion.add(new Dispersion(values));

                    values[0][0] = channel.get(i - 2, j - 1)[0];
                    values[0][1] = channel.get(i - 2, j)[0];
                    values[0][2] = channel.get(i - 2, j + 1)[0];
                    values[1][0] = channel.get(i - 1, j - 1)[0];
                    values[1][1] = channel.get(i - 1, j)[0];
                    values[1][2] = channel.get(i - 1, j + 1)[0];
                    values[2][0] = channel.get(i, j - 1)[0];
                    values[2][1] = channel.get(i, j)[0];
                    values[2][2] = channel.get(i, j + 1)[0];
                    listOfDispersion.add(new Dispersion(values));

                    values[0][0] = channel.get(i - 2, j)[0];
                    values[0][1] = channel.get(i - 2, j + 1)[0];
                    values[0][2] = channel.get(i - 2, j + 2)[0];
                    values[1][0] = channel.get(i - 1, j)[0];
                    values[1][1] = channel.get(i - 1, j + 1)[0];
                    values[1][2] = channel.get(i - 1, j + 2)[0];
                    values[2][0] = channel.get(i, j)[0];
                    values[2][1] = channel.get(i, j + 1)[0];
                    values[2][2] = channel.get(i, j + 2)[0];
                    listOfDispersion.add(new Dispersion(values));
                    ////////////////////
                    values[0][0] = channel.get(i + 2, j - 2)[0];
                    values[0][1] = channel.get(i + 2, j - 1)[0];
                    values[0][2] = channel.get(i + 2, j)[0];
                    values[1][0] = channel.get(i + 1, j - 2)[0];
                    values[1][1] = channel.get(i + 1, j - 1)[0];
                    values[1][2] = channel.get(i + 1, j)[0];
                    values[2][0] = channel.get(i, j - 2)[0];
                    values[2][1] = channel.get(i, j - 1)[0];
                    values[2][2] = channel.get(i, j)[0];
                    listOfDispersion.add(new Dispersion(values));

                    values[0][0] = channel.get(i + 2, j - 1)[0];
                    values[0][1] = channel.get(i + 2, j)[0];
                    values[0][2] = channel.get(i + 2, j + 1)[0];
                    values[1][0] = channel.get(i + 1, j - 1)[0];
                    values[1][1] = channel.get(i + 1, j)[0];
                    values[1][2] = channel.get(i + 1, j + 1)[0];
                    values[2][0] = channel.get(i, j - 1)[0];
                    values[2][1] = channel.get(i, j)[0];
                    values[2][2] = channel.get(i, j + 1)[0];
                    listOfDispersion.add(new Dispersion(values));

                    values[0][0] = channel.get(i + 2, j)[0];
                    values[0][1] = channel.get(i + 2, j + 1)[0];
                    values[0][2] = channel.get(i + 2, j + 2)[0];
                    values[1][0] = channel.get(i + 1, j)[0];
                    values[1][1] = channel.get(i + 1, j + 1)[0];
                    values[1][2] = channel.get(i + 1, j + 2)[0];
                    values[2][0] = channel.get(i, j)[0];
                    values[2][1] = channel.get(i, j + 1)[0];
                    values[2][2] = channel.get(i, j + 2)[0];
                    listOfDispersion.add(new Dispersion(values));
                    //////
                    values[0][0] = channel.get(i - 1, j - 2)[0];
                    values[0][1] = channel.get(i - 1, j - 1)[0];
                    values[0][2] = channel.get(i - 1, j)[0];
                    values[1][0] = channel.get(i, j - 2)[0];
                    values[1][1] = channel.get(i, j - 1)[0];
                    values[1][2] = channel.get(i, j)[0];
                    values[2][0] = channel.get(i + 1, j - 2)[0];
                    values[2][1] = channel.get(i + 1, j - 1)[0];
                    values[2][2] = channel.get(i + 1, j)[0];
                    listOfDispersion.add(new Dispersion(values));

                    values[0][0] = channel.get(i - 1, j + 2)[0];
                    values[0][1] = channel.get(i - 1, j + 1)[0];
                    values[0][2] = channel.get(i - 1, j)[0];
                    values[1][0] = channel.get(i, j + 2)[0];
                    values[1][1] = channel.get(i, j + 1)[0];
                    values[1][2] = channel.get(i, j + 1)[0];
                    values[2][0] = channel.get(i + 1, j + 2)[0];
                    values[2][1] = channel.get(i + 1, j + 1)[0];
                    values[2][2] = channel.get(i + 1, j)[0];
                    listOfDispersion.add(new Dispersion(values));
                    min = Double.MAX_VALUE;
                    minrozpt = 0;

                    for (Dispersion listOfDispersion1 : listOfDispersion) {

                        if (listOfDispersion1.getRozptyl() < min) {
                            min = listOfDispersion1.getRozptyl();
                            minrozpt = listOfDispersion1.getPrumer();
                        }
                    }
                    average[0] = minrozpt;
                    SimpleAvrFinal.put(i, j, average);
                    listOfDispersion.clear();
                }
            }
            newChannels.add(SimpleAvrFinal);
        }
        save();
    }


    private void save() {
        Mat BGR = new Mat(channels.get(0).rows(), channels.get(0).cols(), CvType.CV_8UC3);
        List<Mat> RGB = new ArrayList<>();
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
