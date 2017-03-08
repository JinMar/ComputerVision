package cz.tul.bussiness.jobs.functions;

import cz.tul.bussiness.jobs.exceptions.NoTemplateFound;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 07.03.2017.
 */
public class GrabCutMask extends GrabCutA {
    private static final Logger logger = LoggerFactory.getLogger(GrabCutMask.class);


    @Override
    protected Mat initMat(Mat mask, Mat image) throws NoTemplateFound {
        if (!customMask) {
            throw new NoTemplateFound("Mask was not found");
        }

        Mat bgModel = new Mat();
        Mat fgModel = new Mat();
        Rect rect = new Rect(0, 0, 300, 300);
        Mat source = new Mat(1, 1, CvType.CV_8U, new Scalar(0));

        int iterCount = 1;
        Mat tmp = convertToOpencvValues(mask);
        Imgproc.grabCut(image, tmp, rect, bgModel, fgModel, iterCount, Imgproc.GC_INIT_WITH_MASK);


        Mat foreground = new Mat(image.size(), CvType.CV_8UC1, new Scalar(0, 0, 0));
        image.copyTo(foreground, mask);

        return mask;
    }
}
