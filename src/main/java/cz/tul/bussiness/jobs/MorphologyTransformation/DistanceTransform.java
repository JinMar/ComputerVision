package cz.tul.bussiness.jobs.MorphologyTransformation;

import cz.tul.bussiness.jobs.AJob;
import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.bussiness.jobs.exceptions.NoTemplateFound;
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
 * Created by Bc. Marek Jindrák on 27.02.2017.
 */
public class DistanceTransform extends AJob {
    private static final Logger logger = LoggerFactory.getLogger(DistanceTransform.class);
    private int maskSize;
    private int maskType;

    @Override
    protected void init() throws MinimalArgumentsException, NoTemplateFound {
        for (PartAttributeValue att : getAttributes()) {
            if (att.getOperationAttributes().getAttribute().getName().equals("Maska")) {
                switch (att.getValue()) {
                    case "DIST_MASK_3":
                        maskSize = Imgproc.DIST_MASK_3;
                        break;
                    case "DIST_MASK_5":
                        maskSize = Imgproc.DIST_MASK_5;
                        break;
                    default:
                        maskSize = Imgproc.DIST_MASK_3;
                        logger.warn("Default mask size was choosen: 3×3");
                }
            }
            if (att.getOperationAttributes().getAttribute().getName().equals("Metrika")) {
                switch (att.getValue()) {
                    case "DIST_L1":
                        maskType = Imgproc.DIST_L1;
                        break;
                    case "DIST_L2":
                        maskType = Imgproc.DIST_L2;
                        break;
                    case "DIST_C":
                        maskType = Imgproc.DIST_C;
                        break;

                    default:
                        logger.warn("Default distance metrics was choosen: the simple euclidean distance");

                }
            }


        }
    }

    @Override
    protected BufferedImage procces() {
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        result = new Mat(BGR.rows(), BGR.cols(), CvType.CV_8UC1);

        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR.put(0, 0, sourceData);
        Core.split(BGR, channels);
        Imgproc.distanceTransform(channels.get(0), result, maskType, maskSize, CvType.CV_8UC1);

        Mat BGR = new Mat(channels.get(0).rows(), channels.get(0).cols(), CvType.CV_8UC3);
        List<Mat> RGB_ = new ArrayList<>();

        RGB_.add(result);
        RGB_.add(result);
        RGB_.add(result);
        Core.merge(RGB_, BGR);
        sourceData = new byte[channels.get(0).rows() * channels.get(0).cols() * (int) BGR.elemSize()];
        BGR.get(0, 0, sourceData);
        imgData = new BufferedImage(channels.get(0).cols(), channels.get(0).rows(), BufferedImage.TYPE_3BYTE_BGR);
        imgData.getRaster().setDataElements(0, 0, channels.get(0).cols(), channels.get(0).rows(), sourceData);
        return imgData;
    }
}
