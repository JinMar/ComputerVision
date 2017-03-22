package cz.tul.bussiness.jobs.imagerecognition;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 07.03.2017.
 */
public class GrabCutRect extends GrabCutA {
    private static final Logger logger = LoggerFactory.getLogger(GrabCutRect.class);


    @Override
    protected Mat initMat(Mat mask, Mat image) {
        prepare();
        Mat bgModel = new Mat();
        Mat fgModel = new Mat();
        Rect rect = new Rect(getStartX(), getStartY(), getW(), getH());
        Mat source = new Mat(1, 1, CvType.CV_8UC3, new Scalar(3));
        Imgproc.grabCut(BGR, mask, rect, bgModel, fgModel, 1, Imgproc.GC_INIT_WITH_RECT);
        Core.compare(mask, source, mask, Core.CMP_EQ);
        return mask;

    }

}
