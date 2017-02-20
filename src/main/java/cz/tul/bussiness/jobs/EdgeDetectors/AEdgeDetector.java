package cz.tul.bussiness.jobs.EdgeDetectors;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bc. Marek Jindrák on 13.02.2017.
 */
public abstract class AEdgeDetector extends AJob {
    private static final Logger logger = LoggerFactory.getLogger(AEdgeDetector.class);
    private Mat edges;
    private int lowThreshold;
    private int ratio;

    @Override
    public BufferedImage start() throws MinimalArgumentsException {
        init();
        return procces();

    }

    @Override
    protected void init() throws MinimalArgumentsException {
        for (PartAttributeValue att : getAttributes()) {
            if (att.getOperationAttributes().getAttribute().getName().equals("Práh")) {
                lowThreshold = Integer.parseInt(att.getValue());

            }
            if (att.getOperationAttributes().getAttribute().getName().equals("Poměr")) {
                ratio = Integer.parseInt(att.getValue());

            }
        }
    }

    private BufferedImage procces() {
        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        BGR.put(0, 0, sourceData);
        Mat gray = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        Imgproc.cvtColor(BGR, gray, Imgproc.COLOR_RGB2GRAY);
        Core.split(gray, channels);
        findEdges();
        Mat BGR = new Mat(channels.get(0).rows(), channels.get(0).cols(), CvType.CV_8UC3);
        List<Mat> RGB = new ArrayList<>();
        RGB.add(getEdges());
        RGB.add(getEdges());
        RGB.add(getEdges());
        Core.merge(RGB, BGR);
        sourceData = new byte[channels.get(0).rows() * channels.get(0).cols() * (int) BGR.elemSize()];
        BGR.get(0, 0, sourceData);
        imgData = new BufferedImage(channels.get(0).cols(), channels.get(0).rows(), BufferedImage.TYPE_3BYTE_BGR);
        imgData.getRaster().setDataElements(0, 0, channels.get(0).cols(), channels.get(0).rows(), sourceData);
        return imgData;
    }

    protected abstract void findEdges();

    public Mat getEdges() {
        if (edges == null) {
            edges = new Mat();
        }
        return edges;
    }

    public void setEdges(Mat edges) {
        this.edges = edges;
    }

    public int getLowThreshold() {
        return lowThreshold;
    }

    public void setLowThreshold(int lowThreshold) {
        this.lowThreshold = lowThreshold;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }
}
