package cz.tul.bussiness.jobs.Segmentation;

import cz.tul.bussiness.jobs.AJob;
import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.bussiness.jobs.exceptions.NoTemplateFound;
import cz.tul.entities.PartAttributeValue;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bc. Marek Jindrák on 27.02.2017.
 */
public class Watershed extends AJob {
    private static final Logger logger = LoggerFactory.getLogger(Watershed.class);
    private int size = 0;
    private int amorphType = 0;
    private int bmorphType = 0;
    private int iterateOne = 0;
    private int iterateTwo = 0;
    private int valueOne = 128;
    private int valueTwo = 128;
    private int threshTypeOne = 0;
    private int threshTypeTwo = 0;

    @Override
    protected void init() throws MinimalArgumentsException, NoTemplateFound {
        for (PartAttributeValue att : getAttributes()) {
            if (att.getOperationAttributes().getAttribute().getName().equals("Velikost")) {
                size = Integer.parseInt(att.getValue());
                continue;
            }
            if (att.getOperationAttributes().getAttribute().getName().equals("A Iterace")) {
                iterateOne = Integer.parseInt(att.getValue());
                continue;
            }
            if (att.getOperationAttributes().getAttribute().getName().equals("B Iterace")) {
                iterateTwo = Integer.parseInt(att.getValue());
                continue;
            }

            if (att.getOperationAttributes().getAttribute().getName().equals("A práh")) {
                valueOne = Integer.parseInt(att.getValue());
                continue;
            }
            if (att.getOperationAttributes().getAttribute().getName().equals("B práh")) {
                valueTwo = Integer.parseInt(att.getValue());
                continue;
            }
            if (att.getOperationAttributes().getAttribute().getName().equals("A. Typ moph. t. ")) {

                switch (att.getValue()) {
                    case "EROZE":
                        amorphType = Imgproc.MORPH_ERODE;
                        break;
                    case "DLATACE":
                        amorphType = Imgproc.MORPH_DILATE;
                        break;
                    case "OPEN":
                        amorphType = Imgproc.MORPH_OPEN;
                        break;
                    case "CLOSE":
                        amorphType = Imgproc.MORPH_CLOSE;
                        break;
                }

                continue;
            }
            if (att.getOperationAttributes().getAttribute().getName().equals("B. Typ moph. t. ")) {

                switch (att.getValue()) {
                    case "EROZE":
                        bmorphType = Imgproc.MORPH_ERODE;
                        break;
                    case "DILATACE":
                        bmorphType = Imgproc.MORPH_DILATE;
                        break;
                    case "OPEN":
                        bmorphType = Imgproc.MORPH_OPEN;
                        break;
                    case "CLOSE":
                        bmorphType = Imgproc.MORPH_CLOSE;
                        break;
                }

                continue;
            }
            if (att.getOperationAttributes().getAttribute().getName().equals("A Thresh type")) {

                switch (att.getValue()) {
                    case "THRESH_BINARY":
                        threshTypeOne = Imgproc.THRESH_BINARY;
                        break;
                    case "THRESH_BINARY_INV":
                        threshTypeOne = Imgproc.THRESH_BINARY_INV;
                        break;

                }

                continue;
            }
            if (att.getOperationAttributes().getAttribute().getName().equals("B Thresh type")) {

                switch (att.getValue()) {
                    case "THRESH_BINARY":
                        threshTypeTwo = Imgproc.THRESH_BINARY;
                        break;
                    case "THRESH_BINARY_INV":
                        threshTypeTwo = Imgproc.THRESH_BINARY_INV;
                        break;

                }

                continue;
            }


        }
    }

    @Override
    protected BufferedImage procces() {
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        result = new Mat(BGR.rows(), BGR.cols(), CvType.CV_32SC1);
        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR.put(0, 0, sourceData);

        Mat orignalImg = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        byte[] localSourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        orignalImg.put(0, 0, localSourceData);

        Core.split(BGR, channels);
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(size, size));

        Mat threeChannel = new Mat();
        Imgproc.cvtColor(BGR, threeChannel, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(threeChannel, threeChannel, valueOne, 255, Imgproc.THRESH_BINARY_INV);

        Mat threeChannel2 = new Mat();
        Imgproc.cvtColor(BGR, threeChannel2, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(threeChannel2, threeChannel2, valueOne, 255, Imgproc.THRESH_BINARY_INV);

        Mat fg = new Mat(BGR.size(), CvType.CV_8U);
        Imgproc.distanceTransform(threeChannel, threeChannel, Imgproc.DIST_L1, 3, CvType.CV_8UC1);
        Imgproc.threshold(threeChannel, fg, valueTwo, 255, Imgproc.THRESH_BINARY);


        Mat bg = new Mat(BGR.size(), CvType.CV_8U);
        Imgproc.dilate(threeChannel2, bg, element, new Point(-1, -1), iterateTwo);
        Imgproc.threshold(bg, bg, valueTwo, 128, Imgproc.THRESH_BINARY_INV);

        Mat markers = new Mat(BGR.size(), CvType.CV_8U, new Scalar(255));
        Mat finalMat = new Mat();
        Core.add(fg, bg, finalMat);
        finalMat.copyTo(markers);

        finalMat.convertTo(finalMat, CvType.CV_32S);
        Imgproc.watershed(BGR, finalMat);
        finalMat.convertTo(finalMat, CvType.CV_8U);

        Imgproc.threshold(finalMat, finalMat, 128, 254, Imgproc.THRESH_BINARY_INV);

        Imgproc.connectedComponents(markers, markers);
        markers.convertTo(markers, CvType.CV_32S);
        Imgproc.watershed(BGR, markers);
        markers.convertTo(markers, CvType.CV_8U);


        markers = getOriginaMAt(markers, orignalImg);
        List<Mat> RGB_ = new ArrayList<>();
        RGB_.add(markers);
        RGB_.add(markers);
        RGB_.add(markers);
        Core.merge(RGB_, BGR);

        sourceData = new byte[channels.get(0).rows() * channels.get(0).cols() * (int) BGR.elemSize()];
        markers.get(0, 0, sourceData);
        imgData = new BufferedImage(channels.get(0).cols(), channels.get(0).rows(), BufferedImage.TYPE_3BYTE_BGR);
        imgData.getRaster().setDataElements(0, 0, channels.get(0).cols(), channels.get(0).rows(), sourceData);
        return imgData;
    }

    private Mat getOriginaMAt(Mat marker, Mat orig) {
        Mat tempResult = new Mat(orig.rows(), orig.cols(), CvType.CV_8UC3);
        double[] rgb = new double[3];
        double[] tmp;
        for (int i = 0; i < marker.rows(); i++) {
            for (int j = 0; j < marker.cols(); j++) {
                if (marker.get(i, j)[0] != 0) {

                    tmp = orig.get(i, j);
                    rgb[0] = tmp[2];
                    rgb[1] = tmp[1];
                    rgb[2] = tmp[0];
                    tempResult.put(i, j, rgb);
                } else {
                    rgb[0] = 0;
                    rgb[1] = 255;
                    rgb[2] = 0;
                    tempResult.put(i, j, rgb);
                }
            }
        }

        return tempResult;

    }

}
