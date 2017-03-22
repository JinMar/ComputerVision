package cz.tul.bussiness.jobs.imagerecognition;

import cz.tul.bussiness.jobs.AJob;
import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.bussiness.jobs.exceptions.NoTemplateFound;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.*;

/**
 * Created by Bc. Marek Jindrák on 15.03.2017.
 */
public class RegionIdentification extends AJob {
    private static final Logger logger = LoggerFactory.getLogger(RegionIdentification.class);

    @Override
    protected void init() throws MinimalArgumentsException, NoTemplateFound {
        logger.info("nothing to init");
    }

    @Override
    protected BufferedImage procces() throws NoTemplateFound {

        coloringMethod();
        List<Mat> RGB = new ArrayList<>();
        RGB.add(channels.get(0));
        RGB.add(channels.get(1));
        RGB.add(channels.get(2));
        Core.merge(RGB, BGR);
        sourceData = new byte[channels.get(0).rows() * channels.get(0).cols() * (int) BGR.elemSize()];
        BGR.get(0, 0, sourceData);
        imgData = new BufferedImage(channels.get(0).cols(), channels.get(0).rows(), BufferedImage.TYPE_3BYTE_BGR);
        imgData.getRaster().setDataElements(0, 0, channels.get(0).cols(), channels.get(0).rows(), sourceData);


        return imgData;
    }

    private void coloringMethod() {
        double[] values = new double[1];
        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        BGR.put(0, 0, sourceData);
        Core.split(BGR, channels);
        int x = channels.get(0).rows(), y = channels.get(0).cols();
        Map<Double, Set<Double>> neighbours = new HashMap<>();
        List<Double> tempList = new ArrayList<>();
        Set<Double> usedNeighbours = new HashSet<>();
        Set<Double> newNeighbours = new HashSet<>();
        List<Set<Double>> finalColors = new ArrayList<>();
        ArrayList<ArrayList<Integer>> RGBColors = new ArrayList<>();
        ArrayList<Integer> color;
        double current_color = 2;

        double[][] prepearedData = null;
        if (channels.get(0) != null) {
            prepearedData = prepareData(channels.get(0));
        }

        for (int i = 1; i < x; i++) {
            for (int j = 1; j < y - 1; j++) {

                if (prepearedData[i][j] == 1) {

                    if (prepearedData[i - 1][j - 1] == 0 && prepearedData[i - 1][j] == 0 && prepearedData[i - 1][j + 1] == 0 && prepearedData[i][j - 1] == 0) {
                        current_color++;
                        Set<Double> newSet = new HashSet<>();
                        newSet.add(current_color);
                        neighbours.put(current_color, newSet);
                        prepearedData[i][j] = current_color;

                    } else {
                        tempList.clear();
                        tempList.add(prepearedData[i - 1][j - 1]);
                        tempList.add(prepearedData[i - 1][j]);
                        tempList.add(prepearedData[i - 1][j + 1]);
                        tempList.add(prepearedData[i][j - 1]);

                        Collections.sort(tempList);
                        if (tempList.get(2) > 0 && tempList.get(3) > 0) {
                            double test = tempList.get(2);
                            Set<Double> tets = neighbours.get(tempList.get(2));
                            tets.add(tempList.get(3));
                            neighbours.get(tempList.get(3)).add(tempList.get(2));
                        }

                        prepearedData[i][j] = tempList.get(3);
                    }
                }
            }

        }
        for (int i = 3; i < neighbours.size(); i++) {
            if (!usedNeighbours.contains((double) i)) {
                newNeighbours = findNeighbours(i, neighbours, new HashSet<>());
                finalColors.add(newNeighbours);
                usedNeighbours.addAll(newNeighbours);
            }
        }

        RGBColors = generateColors(finalColors.size());

        for (int i = 1; i < x; i++) {
            for (int j = 1; j < y - 1; j++) {
                if (prepearedData[i][j] > 0) {

                    color = findFinalColor(prepearedData[i][j], finalColors, RGBColors);
                    channels.get(0).put(i, j, color.get(0));
                    channels.get(1).put(i, j, color.get(1));
                    channels.get(2).put(i, j, color.get(2));
                }

            }
        }

    }

    private ArrayList<Integer> findFinalColor(double value, List<Set<Double>> finalColors, ArrayList<ArrayList<Integer>> colors) {
        for (int i = 0; i < finalColors.size(); i++) {
            if (finalColors.get(i).contains(value)) {
                return colors.get(i);
            }
        }
        System.out.println("Barva pro : " + value + "nebyla nalezena");
        return colors.get(0);
    }

    private Set<Double> findNeighbours(double currentColor, Map<Double, Set<Double>> neig, Set<Double> neighboursFinal) {
        List<Double> currNeig = new ArrayList<>(neig.get(currentColor));

        for (int i = 0; i < currNeig.size(); i++) {
            if (!neighboursFinal.contains(currNeig.get(i))) {
                neighboursFinal.add(currNeig.get(i));
                neighboursFinal = findNeighbours(currNeig.get(i), neig, neighboursFinal);
            }
        }

        return neighboursFinal;
    }

    private ArrayList<ArrayList<Integer>> generateColors(int count) {
        ArrayList<ArrayList<Integer>> ListOfFinalColors = new ArrayList<>();
        ArrayList<Integer> RGB;
        Random R = new Random();
        Random G = new Random();
        Random B = new Random();
        for (int i = 0; i < count + 3; i++) {
            int red = R.nextInt(256) + 5;
            int green = G.nextInt(256) + 5;
            int blue = B.nextInt(256) + 5;
            RGB = new ArrayList<>();

            RGB.add(red);
            RGB.add(green);
            RGB.add(blue);
            ListOfFinalColors.add(RGB);

        }
        return ListOfFinalColors;
    }

    private double[][] prepareData(Mat mat) {
        double[][] result = new double[mat.rows()][mat.cols()];
        for (int i = 0; i < mat.rows(); i++) {
            for (int j = 0; j < mat.cols() - 1; j++) {
                if (mat.get(i, j)[0] == 255) {
                    result[i][j] = 1;
                } else {
                    result[i][j] = 0;
                }
                /*
                osetreni okrajů obrazu
                 */
                if (i == 0 || i == mat.rows() - 1 || j == 0 || j == mat.cols() - 1) {
                    result[i][j] = 0;
                }
            }
        }
        return result;
    }
}
