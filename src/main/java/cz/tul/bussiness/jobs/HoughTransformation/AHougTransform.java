package cz.tul.bussiness.jobs.HoughTransformation;

import cz.tul.bussiness.jobs.AJob;
import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.entities.PartAttributeValue;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Created by Bc. Marek Jindrák on 07.02.2017.
 */
public abstract class AHougTransform extends AJob {
    private static final Logger logger = LoggerFactory.getLogger(AHougTransform.class);
    private double rho;
    private Mat edges;
    private double minDist;
    private int method;

    @Override
    public BufferedImage start() throws MinimalArgumentsException {
        init();
        return process();
    }

    private BufferedImage process() {
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        result = new Mat(BGR.rows(), BGR.cols(), CvType.CV_8UC3);
        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR.put(0, 0, sourceData);
        Core.split(BGR, channels);
        draw();
        finalData = new byte[(int) (result.total()) * result.channels()];
        BGR.get(0, 0, finalData);
        BufferedImage resultImgData = new BufferedImage(result.cols(), result.rows(), BufferedImage.TYPE_3BYTE_BGR);
        resultImgData.getRaster().setDataElements(0, 0, result.cols(), result.rows(), finalData);
        return resultImgData;
    }

    protected void init() throws MinimalArgumentsException {

        for (PartAttributeValue att : getAttributes()) {
            if (att.getOperationAttributes().getAttribute().getName().equals("Min vzdálenost")) {
                minDist = Integer.parseInt(att.getValue());

            }
            if (att.getOperationAttributes().getAttribute().getName().equals("DP")) {
                rho = Double.parseDouble(att.getValue());

            }
            if (att.getOperationAttributes().getAttribute().getName().equals("Metoda")) {

                switch (att.getValue()) {
                    case "HOUGH_STANDARD":
                        method = Imgproc.HOUGH_STANDARD;
                        break;
                    case "HOUGH_PROBABILISTIC":
                        method = Imgproc.HOUGH_PROBABILISTIC;
                        break;
                    case "HOUGH_GRADIENT":
                        method = Imgproc.HOUGH_GRADIENT;
                        break;
                    case "HOUGH_MULTI_SCALE":
                        method = Imgproc.HOUGH_MULTI_SCALE;
                        break;
                    default:
                        method = Imgproc.HOUGH_STANDARD;

                }

            }
        }
    }


    protected abstract void draw();

    public double getRho() {
        return rho;
    }

    public double getMinDist() {
        return minDist;
    }

    public int getMethod() {
        return method;
    }

    public Mat getEdges() {
        if (edges == null) {
            edges = new Mat();
        }
        return edges;
    }
}
