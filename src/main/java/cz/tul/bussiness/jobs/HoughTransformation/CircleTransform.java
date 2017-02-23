package cz.tul.bussiness.jobs.HoughTransformation;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 01.02.2017.
 */
public class CircleTransform extends AHougTransform {
    private static final Logger logger = LoggerFactory.getLogger(CircleTransform.class);


    @Override
    protected void draw() {
        double x = 0.0;
        double y = 0.0;
        int r = 0;

        Mat circles = new Mat();
        Imgproc.HoughCircles(channels.get(0), circles, getMethod(), getRho(), getMinDist());
        for (int i = 0; i < circles.cols(); i++) {
            double[] data = circles.get(0, i);
            for (int j = 0; j < data.length; j++) {
                x = data[0];
                y = data[1];
                r = (int) Math.round(data[2]);
            }
            Imgproc.circle(BGR, new Point(x, y), r, new Scalar(0, 255, 0), 1);
        }
        logger.info("Successful drawing circles!");
    }
}



