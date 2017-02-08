package cz.tul.bussiness.jobs;

import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.entities.PartAttributeValue;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Created by Bc. Marek Jindrák on 28.12.2016.
 */
public class Rotate extends AJob {
    private static final Logger logger = LoggerFactory.getLogger(Resize.class);
    private double size = 1;
    private int angle = 0;
    private int interpolace = 0;

    @Override
    public BufferedImage start() throws MinimalArgumentsException {
        init();
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        result = new Mat((int) (BGR.rows() * size), (int) (BGR.cols() * size), CvType.CV_8UC3);
        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR.put(0, 0, sourceData);

        double radians = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));

        int newWidth = (int) (imgData.getWidth() * cos + imgData.getHeight() * sin);
        int newHeight = (int) (imgData.getWidth() * sin + imgData.getHeight() * cos);

        int[] newWidthHeight = {newWidth, newHeight};


        int pivotX = newWidthHeight[0] / 2;
        int pivotY = newWidthHeight[1] / 2;
        Point center = new org.opencv.core.Point(pivotX, pivotY);
        Size targetSize = new Size(newWidthHeight[0], newWidthHeight[1]);
        Mat targetMat = new Mat(targetSize, result.type());


        int offsetX = (newWidthHeight[0] - result.width()) / 2;
        int offsetY = (newWidthHeight[1] - result.height()) / 2;
        Mat submat = targetMat.submat(offsetY, offsetY + imgData.getHeight(), offsetX, offsetX + imgData.getWidth());
        BGR.copyTo(submat);

        Mat rotImage = Imgproc.getRotationMatrix2D(center, angle, 1.0);
        Mat resultMat = new Mat();
        Imgproc.warpAffine(targetMat, resultMat, rotImage, targetSize, interpolace);

        Core.split(resultMat, channels);
        channelsRGB.add(channels.get(2));
        channelsRGB.add(channels.get(1));
        channelsRGB.add(channels.get(0));
        Core.merge(channelsRGB, result);
        finalData = new byte[(int) (result.total()) * result.channels()];
        result.get(0, 0, finalData);
        BufferedImage resultImgData = new BufferedImage(result.cols(), result.rows(), BufferedImage.TYPE_3BYTE_BGR);
        resultImgData.getRaster().setDataElements(0, 0, result.cols(), result.rows(), finalData);
        return resultImgData;
    }


    @Override
    protected void init() throws MinimalArgumentsException {
        int tmp = 0;
        String inter = "";
        for (PartAttributeValue att : getAttributes()) {
            if (att.getOperationAttributes().getAttribute().getName().equals("Velikost")) {
                tmp = Integer.parseInt(att.getValue());
                size = tmp / 100.0;
            }
            if (att.getOperationAttributes().getAttribute().getName().equals("Úhel")) {
                angle = Integer.parseInt(att.getValue());

            }
            if (att.getOperationAttributes().getAttribute().getName().equals("Interpolace")) {
                inter = att.getValue();
                switch (inter) {
                    case "INTER_AREA":
                        interpolace = Imgproc.INTER_AREA;
                        break;
                    case "INTER_BITS":
                        interpolace = Imgproc.INTER_BITS;
                        break;
                    case "INTER_BITS2":
                        interpolace = Imgproc.INTER_BITS2;
                        break;
                    case "INTER_CUBIC":
                        interpolace = Imgproc.INTER_CUBIC;
                        break;
                    case "INTER_LANCZOS4":
                        interpolace = Imgproc.INTER_LANCZOS4;
                        break;
                    case "INTER_LINEAR":
                        interpolace = Imgproc.INTER_LINEAR;
                        break;
                    case "INTER_MAX":
                        interpolace = Imgproc.INTER_MAX;
                        break;
                    case "INTER_NEAREST":
                        interpolace = Imgproc.INTER_NEAREST;
                        break;
                    default:
                        interpolace = Imgproc.INTER_NEAREST;

                }

            }


        }
    }
}
