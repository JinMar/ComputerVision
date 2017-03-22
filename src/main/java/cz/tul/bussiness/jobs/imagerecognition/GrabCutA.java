package cz.tul.bussiness.jobs.imagerecognition;

import cz.tul.bussiness.jobs.AJob;
import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.bussiness.jobs.exceptions.NoTemplateFound;
import cz.tul.entities.PartAttributeValue;
import cz.tul.utilities.Utility;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Bc. Marek Jindrák on 08.03.2017.
 */
public abstract class GrabCutA extends AJob {
    private static final Logger logger = LoggerFactory.getLogger(GrabCutA.class);


    private byte[] customElement;
    private int type;
    private int startX;
    private int startY;
    private int w;
    private int h;
    protected boolean customMask = false;

    protected abstract Mat initMat(Mat mask, Mat image) throws NoTemplateFound;

    @Override
    protected void init() throws MinimalArgumentsException, NoTemplateFound {
        for (PartAttributeValue att : getAttributes()) {

            if (att.getOperationAttributes().getAttribute().getName().equals("Maska")) {
                String base64Image = att.getValue();
                if (base64Image.length() > 0) {
                    customElement = Utility.getImageByte(base64Image.split(",")[1]);


                }
                continue;
            }
            if (att.getOperationAttributes().getAttribute().getName().equals("Počatek X")) {
                startX = Integer.parseInt(att.getValue());
                if (startX < 0) {
                    startX = 0;
                }
            }
            if (att.getOperationAttributes().getAttribute().getName().equals("Počatek Y")) {
                startY = Integer.parseInt(att.getValue());
                if (startY < 0) {
                    startY = 0;
                }
            }
            if (att.getOperationAttributes().getAttribute().getName().equals("Výška")) {
                h = Integer.parseInt(att.getValue());
                if (h < 0) {
                    h = 0;
                }
            }
            if (att.getOperationAttributes().getAttribute().getName().equals("Šířka")) {
                w = Integer.parseInt(att.getValue());
                if (w < 0) {
                    w = 0;
                }
            }

        }
    }

    protected void prepare() {
        if (startY > BGR.cols()) {
            startY = 0;
        }
        if (startX > BGR.rows()) {
            startX = 0;
        }
        if (startY + h > BGR.cols()) {
            h = BGR.cols() - startY - 10;
        }
        if (startX + w > BGR.rows()) {
            w = BGR.rows() - startX - 10;
        }
    }

    @Override
    protected BufferedImage procces() throws NoTemplateFound {
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        result = new Mat(BGR.rows(), BGR.cols(), CvType.CV_8UC3);
        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR.put(0, 0, sourceData);
        Core.split(BGR, channels);
        Mat mask = null;

        if (customElement != null) {
            BufferedImage data = null;
            try {
                data = ImageIO.read(new ByteArrayInputStream(customElement));

                Mat newBGR = new Mat(data.getHeight(), data.getWidth(), CvType.CV_8UC3);
                newBGR.put(0, 0, ((DataBufferByte) data.getRaster().getDataBuffer()).getData());
                Mat gray = new Mat(data.getHeight(), data.getWidth(), CvType.CV_8UC3);
                mask = new Mat(data.getHeight(), data.getWidth(), CvType.CV_8UC1);
                Imgproc.cvtColor(newBGR, gray, Imgproc.COLOR_RGB2GRAY);
                Imgproc.threshold(gray, mask, 127, 255, Imgproc.THRESH_BINARY);
                customMask = true;
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            mask = new Mat();
        }


        result = getOriginaMAt(initMat(mask, BGR));
        finalData = new byte[result.cols() * result.rows() * (int) result.elemSize()];
        result.get(0, 0, finalData);
        imgData = new BufferedImage(result.cols(), result.rows(), BufferedImage.TYPE_3BYTE_BGR);
        imgData.getRaster().setDataElements(0, 0, result.cols(), result.rows(), finalData);


        return imgData;
    }

    private Mat getOriginaMAt(Mat mask) {
        Mat tempResult = new Mat(BGR.rows(), BGR.cols(), CvType.CV_8UC3);
        double[] rgb;
        double[] tmp;
        for (int i = 1; i < mask.rows(); i++) {
            for (int j = 1; j < mask.cols(); j++) {
                if (mask.get(i, j)[0] == 255) {
                    rgb = new double[3];
                    tmp = BGR.get(i, j);
                    rgb[0] = tmp[2];
                    rgb[1] = tmp[1];
                    rgb[2] = tmp[0];
                    tempResult.put(i, j, rgb);
                }
            }
        }

        return tempResult;

    }


    protected Mat convertToHumanValues(Mat mask) {
        byte[] buffer = new byte[3];
        Mat ret = new Mat(mask.rows(), mask.cols(), mask.type());
        for (int x = 0; x < mask.rows(); x++) {
            for (int y = 0; y < mask.cols(); y++) {
                mask.get(x, y, buffer);
                int value = buffer[0];
                if (value == Imgproc.GC_BGD) {
                    buffer[0] = 0;
                } else if (value == Imgproc.GC_PR_BGD) {
                    buffer[0] = 85;
                } else if (value == Imgproc.GC_PR_FGD) {
                    buffer[0] = (byte) 170;
                } else {
                    buffer[0] = (byte) 255;

                }
                ret.put(x, y, buffer);
            }
        }
        return ret;
    }

    protected Mat convertToOpencvValues(Mat mask) {
        Mat ret = new Mat(mask.rows(), mask.cols(), mask.type());
        byte[] buffer = new byte[3];
        for (int x = 0; x < mask.rows(); x++) {
            for (int y = 0; y < mask.cols(); y++) {
                mask.get(x, y, buffer);
                int value = buffer[0];
                if (value >= 0 && value < 64) {
                    buffer[0] = Imgproc.GC_BGD;
                } else if (value >= 64 && value < 128) {
                    buffer[0] = Imgproc.GC_PR_BGD;
                } else if (value >= 128 && value < 192) {
                    buffer[0] = Imgproc.GC_PR_FGD;
                } else {
                    buffer[0] = Imgproc.GC_FGD;

                }
                ret.put(x, y, buffer);
            }
        }
        return ret;
    }

    protected int getStartX() {
        return startX;
    }

    protected int getStartY() {
        return startY;
    }

    protected int getW() {
        return w;
    }

    protected int getH() {
        return h;
    }
}


