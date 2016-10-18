package cz.tul.bussiness.workers;

import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bc. Marek Jindrák on 16.10.2016.
 */
public abstract class Worker extends AMethodWorker {


    private static final Logger logger = LoggerFactory.getLogger(Worker.class);

    protected byte[] sourceData;
    protected Mat BGR;
    protected List<Mat> channels = new ArrayList<>();


    public byte[] getSourceData() {
        return sourceData;
    }

    public void setSourceData(byte[] sourceData) {
        this.sourceData = sourceData;
    }

}
