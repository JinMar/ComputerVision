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
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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

        if (classifier.equals(SegmentorEnum.THRESH_BINARY.getSegmentorName())) {
            methodType = 1;
        }
        if (classifier.equals(SegmentorEnum.THRESH_BINARY_INV.getSegmentorName())) {
            methodType = 2;
        }
        if (classifier.equals(SegmentorEnum.THRESH_TOZERO.getSegmentorName())) {
            methodType = 3;
        }
        if (classifier.equals(SegmentorEnum.THRESH_TRUNC.getSegmentorName())) {
            methodType = 4;
        }

        if (classifier.equals(SegmentorEnum.COLORING.getSegmentorName())) {
            methodType = 5;
        }
        for (PartAttributeValue att : getAttributes()) {
            if (att.getOperationAttributes().getAttribute().getName().equals("Práh")) {
                System.out.println(att.getValue());
                threshold = Integer.parseInt(att.getValue());
            }

        }
        switch (methodType) {
            case 1:
                logger.info("threshold: " + threshold + " type: " + classifier);
                thresholding(Imgproc.THRESH_BINARY);
                break;
            case 2:
                logger.info("threshold: " + threshold + " type: " + classifier);
                thresholding(Imgproc.THRESH_BINARY_INV);
                break;
            case 3:
                logger.info("threshold: " + threshold + " type: " + classifier);
                thresholding(Imgproc.THRESH_TOZERO);
                break;
            case 4:
                logger.info("threshold: " + threshold + " type: " + classifier);
                thresholding(Imgproc.THRESH_TRUNC);
                break;
            case 5:
                logger.info("ColoringMethod:");
                coloringMethod();
                break;
            default:
                //todo vyhodit exception
        }

    }


    private void thresholding(int type) {
        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        channels.add(Mat.zeros(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC1));
        channels.add(Mat.zeros(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC1));
        channels.add(Mat.zeros(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC1));

        result = new Mat(BGR.rows(), BGR.cols(), CvType.CV_8UC1);
        BGR.put(0, 0, sourceData);
        Core.split(BGR, channels);
        Imgproc.threshold(channels.get(0), result, threshold, 255, type);
        save();
    }

    private void coloringMethod() {
        int temp, temp1, temp2, temp3, temp4;
        double[] values = new double[1];
        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        BGR.put(0, 0, sourceData);
        Core.split(BGR, channels);

        Mat colored = Mat.zeros(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC1);
        Mat result = new Mat(BGR.rows(), BGR.cols(), CvType.CV_8UC1);
        Imgproc.threshold(channels.get(0), result, 80, 255, typ);


        int color = 1;
        prepareData(result);
        HashMap<Integer, ArrayList<Integer>> map = new HashMap();
        ArrayList<Integer> colors = new ArrayList<>();
        boolean isInList, isInList2, isInlist3;
        for (int i = 1; i < result.rows(); i++) {
            for (int j = 1; j < result.cols() - 1; j++) {

                temp = (int) result.get(i, j)[0];

                if (temp > 0) {

                    temp1 = (int) result.get(i, j - 1)[0];
                    temp2 = (int) result.get(i - 1, j + 1)[0];
                    temp3 = (int) result.get(i - 1, j)[0];
                    temp4 = (int) result.get(i - 1, j - 1)[0];

                    if (temp1 == temp2 && temp2 == temp3 && temp3 == temp4 && temp4 == 0) {
                        color++;

                        map.put(color, new ArrayList<>(color));
                        values[0] = color;
                        colored.put(i, j, values);
                        continue;
                    }
                    if (temp4 > 0) {
                        values[0] = colored.get(i - 1, j - 1)[0];
                        colored.put(i, j, values);
                        continue;
                    }
                    if (temp3 > 0) {
                        values[0] = colored.get(i - 1, j)[0];
                        colored.put(i, j, values);
                        continue;
                    }
                    if (temp2 > 0) {
                        values[0] = (int) colored.get(i - 1, j + 1)[0];
                        colored.put(i, j, values);
                        continue;
                    }
                    if (temp1 > 0) {
                        values[0] = colored.get(i, j - 1)[0];
                        colored.put(i, j, values);

                    }
                }
            }
        }
        System.out.println("Počrt barev " + color);

        for (int i = 1; i < colored.rows(); i++) {
            for (int j = 1; j < colored.cols() - 1; j++) {

                temp = (int) colored.get(i, j)[0];

                if (temp > 0) {

                    temp1 = (int) colored.get(i, j - 1)[0];
                    temp2 = (int) colored.get(i - 1, j + 1)[0];
                    temp3 = (int) colored.get(i - 1, j)[0];
                    temp4 = (int) colored.get(i - 1, j - 1)[0];

                    if (map.get(temp).isEmpty()) {
                        map.get(temp).add(temp);

                    } else {
                        isInlist3 = false;
                        for (int k = 0; k < map.get(temp).size(); k++) {
                            if (map.get(temp).get(k) == temp) {
                                isInlist3 = true;
                                break;
                            }
                        }
                        if (!isInlist3) {

                            map.get(temp).add(temp);

                        }
                    }

                    if (temp4 > 0) {
                        isInList = false;
                        isInList2 = false;
                        for (int k = 0; k < map.get(temp).size(); k++) {
                            if (map.get(temp).get(k) == temp4) {
                                isInList = true;
                                break;
                            }
                        }
                        if (!isInList) {
                            if (temp != temp4) {
                                map.get(temp).add(temp4);
                            }
                        }
                        for (int k = 0; k < map.get(temp4).size(); k++) {
                            if (map.get(temp4).get(k) == temp) {
                                isInList2 = true;
                                break;
                            }
                        }
                        if (!isInList2) {
                            map.get(temp4).add(temp);
                        }
                    }
                    if (temp3 > 0) {
                        isInList = false;
                        isInList2 = false;
                        for (int k = 0; k < map.get(temp).size(); k++) {
                            if (map.get(temp).get(k) == temp3) {
                                isInList = true;
                                break;
                            }
                        }
                        if (!isInList) {
                            if (temp != temp3) {
                                map.get(temp).add(temp3);

                            }
                        }
                        for (int k = 0; k < map.get(temp3).size(); k++) {
                            if (map.get(temp3).get(k) == temp) {
                                isInList2 = true;
                                break;
                            }
                        }
                        if (!isInList2) {

                            map.get(temp3).add(temp);

                        }

                    }

                    if (temp2 > 0) {
                        isInList2 = false;
                        isInList = false;

                        for (int k = 0; k < map.get(temp).size(); k++) {
                            if (map.get(temp).get(k) == temp2) {
                                isInList = true;
                                break;
                            }
                        }
                        if (!isInList) {
                            if (temp != temp2) {
                                map.get(temp).add(temp2);

                            }
                        }
                        for (int k = 0; k < map.get(temp2).size(); k++) {
                            if (map.get(temp2).get(k) == temp) {
                                isInList2 = true;
                                break;
                            }
                        }
                        if (!isInList2) {

                            map.get(temp2).add(temp);

                        }

                    }
                    if (temp1 > 0) {
                        isInList2 = false;
                        isInList = false;

                        for (int k = 0; k < map.get(temp).size(); k++) {
                            if (map.get(temp).get(k) == temp1) {
                                isInList = true;
                                break;
                            }
                        }
                        if (!isInList) {
                            if (temp != temp1) {
                                map.get(temp).add(temp1);

                            }
                        }
                        for (int k = 0; k < map.get(temp1).size(); k++) {
                            if (map.get(temp1).get(k) == temp) {
                                isInList2 = true;
                                break;
                            }
                        }
                        if (!isInList2) {

                            map.get(temp1).add(temp);

                        }

                    }
                }
            }
        }

        map = coloring(map, colors);
        reColoring(map, colored);
        saveColoredImg();

    }


    private Mat prepareData(Mat threshold) {
        double[] values = new double[1];
        values[0] = 0;
        for (int i = 0; i < threshold.cols(); i++) {
            for (int j = 0; j < threshold.rows(); j++) {
                if (i == 0) {
                    threshold.put(i, j, values);
                }
                if (j == 0) {
                    threshold.put(i, j, values);
                }
                if (j == threshold.rows() - 1) {
                    threshold.put(i, j, values);
                }
            }
        }
        return threshold;
    }

    private HashMap<Integer, ArrayList<Integer>> coloring(HashMap<Integer, ArrayList<Integer>> list, ArrayList<Integer> colors) {
        boolean isInList;
        int color = 0;
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> ColorList = new HashMap();
        if (list.isEmpty()) {
            System.out.println("List is empty maybe throw someting else");
        } else {
            System.out.println("Počet " + list.size());
            for (int i = 2; i < list.size(); i++) {
                isInList = false;
                for (int k = 0; k < colors.size(); k++) {
                    if (colors.get(k) == i) {
                        isInList = true;
                        break;
                    }
                }
                if (!isInList) {
                    result = neighbor(i, list, colors, new ArrayList<>());
                    color++;
                    colors = result.get(1);
                    ColorList.put(color, result.get(0));
                }
            }
        }

        return ColorList;
    }

    private ArrayList<ArrayList<Integer>> neighbor(int color, HashMap<Integer, ArrayList<Integer>> list, ArrayList<Integer> colors, ArrayList<Integer> neighbors) {
        boolean isInList;
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (int i = 0; i < list.get(color).size(); i++) {
            isInList = false;

            for (int k = 0; k < colors.size(); k++) {
                int tmp = list.get(color).get(i);
                if (colors.get(k) == tmp) {
                    isInList = true;
                    break;
                }
            }
            if (!isInList) {
                neighbors.add(list.get(color).get(i));
                colors.add(list.get(color).get(i));
                neighbor(list.get(color).get(i), list, colors, neighbors);
            }
        }
        result.add(neighbors);
        result.add(colors);
        return result;
    }

    private void reColoring(HashMap<Integer, ArrayList<Integer>> map, Mat colored) {
        int temp;
        int color = 1;
        boolean end;
        double[] values = new double[1];
        ArrayList<ArrayList<Integer>> ListOfFinalColors = generateColors(map.size());
        for (int i = 1; i < colored.rows(); i++) {
            for (int j = 1; j < colored.cols() - 1; j++) {
                temp = (int) colored.get(i, j)[0];

                if (temp > 0) {
                    end = false;

                    for (int h = 0; h < map.size(); h++) {
                        for (int k = 0; k < map.get(h + 1).size(); k++) {
                            if (temp == map.get(h + 1).get(k)) {

                                for (int key : map.keySet()) {
                                    if (map.get(key).equals(map.get(h + 1))) {

                                        color = key;
                                    }
                                }

                                values[0] = ListOfFinalColors.get(color).get(0);
                                channels.get(0).put(i, j, values);
                                values[0] = ListOfFinalColors.get(color).get(1);
                                channels.get(1).put(i, j, values);
                                values[0] = ListOfFinalColors.get(color).get(2);
                                channels.get(2).put(i, j, values);
                                end = true;
                                break;
                            }
                            if (end) {
                                break;
                            }
                        }
                    }
                }
            }
        }

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

    private void saveColoredImg() {

        List<Mat> RGB = new ArrayList<>();
        RGB.add(channels.get(0));
        RGB.add(channels.get(1));
        RGB.add(channels.get(2));
        Core.merge(RGB, BGR);
        sourceData = new byte[channels.get(0).rows() * channels.get(0).cols() * (int) BGR.elemSize()];
        BGR.get(0, 0, sourceData);
        imgData = new BufferedImage(channels.get(0).cols(), channels.get(0).rows(), BufferedImage.TYPE_3BYTE_BGR);
        imgData.getRaster().setDataElements(0, 0, channels.get(0).cols(), channels.get(0).rows(), sourceData);
        saveImg();
    }

}
