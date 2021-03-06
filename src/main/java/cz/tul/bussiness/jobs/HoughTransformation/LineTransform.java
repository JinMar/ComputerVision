package cz.tul.bussiness.jobs.HoughTransformation;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Bc. Marek Jindrák on 07.02.2017.
 */
public class LineTransform extends AHougTransform {
    private static final Logger logger = LoggerFactory.getLogger(LineTransform.class);


    @Override
    protected void draw() {
        Mat lines = new Mat();
        Imgproc.HoughLinesP(channels.get(0), lines, 1, Math.PI / 180, getThrashold(), getMinLineLenght(), getMinLineGap());

        for (int i = 0; i < lines.rows(); i++) {
            double[] val = lines.get(i, 0);
            Imgproc.line(BGR, new Point(val[0], val[1]), new Point(val[2], val[3]), new Scalar(0, 0, 255), 2);
        }
        logger.info("Successful drawing lines!");
    }
}
