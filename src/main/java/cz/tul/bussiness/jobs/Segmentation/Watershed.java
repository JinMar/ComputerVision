package cz.tul.bussiness.jobs.Segmentation;

import cz.tul.bussiness.jobs.AJob;
import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.bussiness.jobs.exceptions.NoTemplateFound;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
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

    @Override
    protected void init() throws MinimalArgumentsException, NoTemplateFound {

    }

    @Override
    protected BufferedImage procces() {
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        result = new Mat(BGR.rows(), BGR.cols(), CvType.CV_32SC1);

        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR.put(0, 0, sourceData);
        Core.split(BGR, channels);
        Mat markers = getMarkers(BGR);


        //  Imgproc.watershed(BGR, markers);


        Mat BGR = new Mat(channels.get(0).rows(), channels.get(0).cols(), CvType.CV_8UC3);


        List<Mat> RGB_ = new ArrayList<>();
        RGB_.add(markers);
        RGB_.add(markers);
        RGB_.add(markers);
        Core.merge(RGB_, BGR);
        sourceData = new byte[channels.get(0).rows() * channels.get(0).cols() * (int) BGR.elemSize()];
        BGR.get(0, 0, sourceData);
        imgData = new BufferedImage(channels.get(0).cols(), channels.get(0).rows(), BufferedImage.TYPE_3BYTE_BGR);
        imgData.getRaster().setDataElements(0, 0, channels.get(0).cols(), channels.get(0).rows(), sourceData);
        return imgData;
    }


    private Mat getMarkers(Mat mat) {

        Mat BGRLoc = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);


        byte[] sourceDatalo = ((DataBufferByte) originalImgData.getRaster().getDataBuffer()).getData();
        BGRLoc.put(0, 0, sourceDatalo);


        Mat threeChannel = new Mat();

        Imgproc.cvtColor(BGRLoc, threeChannel, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(threeChannel, threeChannel, 100, 255, Imgproc.THRESH_BINARY);

        Mat fg = new Mat(BGRLoc.size(), CvType.CV_8U);
        Imgproc.erode(threeChannel, fg, new Mat());

        Mat bg = new Mat(BGRLoc.size(), CvType.CV_8U);
        Imgproc.dilate(threeChannel, bg, new Mat());
        Imgproc.threshold(bg, bg, 1, 128, Imgproc.THRESH_BINARY_INV);

        Mat markers = new Mat(BGRLoc.size(), CvType.CV_8U, new Scalar(0));
        Core.add(fg, bg, markers);


        markers.convertTo(markers, CvType.CV_32S);


        Imgproc.watershed(BGRLoc, markers);
        markers.convertTo(markers, CvType.CV_8U);

        return markers;
    }
}
