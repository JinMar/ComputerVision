package cz.tul.bussiness.jobs.EdgeDetectors;

import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.opencv.core.Core.BORDER_DEFAULT;
import static org.opencv.core.Core.convertScaleAbs;

/**
 * Created by Bc. Marek Jindrák on 13.02.2017.
 */
public class Canny extends AEdgeDetector {
    private static final Logger logger = LoggerFactory.getLogger(Canny.class);


    @Override
    protected void findEdges() {
        Imgproc.GaussianBlur(channels.get(0), channels.get(0), new Size(3, 3), 0, 0, BORDER_DEFAULT);
        Imgproc.Canny(channels.get(0), getEdges(), getLowThreshold(), getLowThreshold() * getRatio());
        convertScaleAbs(getEdges(), getEdges());

    }
}
