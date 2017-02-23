package cz.tul.bussiness.jobs.MorphologyTransformation;

import cz.tul.bussiness.jobs.AJob;
import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.entities.PartAttributeValue;
import cz.tul.utilities.Utility;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bc. Marek Jindrák on 08.02.2017.
 */
public class MorphologyTransform extends AJob {
    private static final Logger logger = LoggerFactory.getLogger(MorphologyTransform.class);
    private int size = 0;
    private String shape = "";
    private byte[] customlement = null;
    private boolean defaultShape = false;
    private int morphologyType = -1;

    public MorphologyTransform(int morphologyType) {
        this.morphologyType = morphologyType;
    }

    @Override
    public BufferedImage start() throws MinimalArgumentsException {
        init();
        return procces();
    }

    @Override
    protected void init() throws MinimalArgumentsException {
        for (PartAttributeValue att : getAttributes()) {
            if (att.getOperationAttributes().getAttribute().getName().equals("Velikost")) {
                size = Integer.parseInt(att.getValue());
                continue;
            }

            if (att.getOperationAttributes().getAttribute().getName().equals("Maska")) {
                String base64Image = att.getValue();
                if (base64Image.length() > 0) {
                    customlement = Utility.getImageByte(base64Image.split(",")[1]);
                    defaultShape = true;
                }
                continue;
            }
            if (att.getOperationAttributes().getAttribute().getName().equals("Tvar")) {

                shape = att.getValue();

                continue;
            }

        }
    }

    @Override
    protected BufferedImage procces() {
        Mat element = null;
        if (morphologyType != -1) {
            if (!defaultShape) {
                element = getElement(size, shape);
            } else {
                try {
                    BufferedImage data = ImageIO.read(new ByteArrayInputStream(customlement));
                    Mat newBGR = new Mat(data.getHeight(), data.getWidth(), CvType.CV_8UC3);
                    newBGR.put(0, 0, ((DataBufferByte) data.getRaster().getDataBuffer()).getData());
                    Mat gray = new Mat(data.getHeight(), data.getWidth(), CvType.CV_8UC3);
                    element = new Mat(data.getHeight(), data.getWidth(), CvType.CV_8UC1);
                    Imgproc.cvtColor(newBGR, gray, Imgproc.COLOR_RGB2GRAY);
                    Imgproc.threshold(gray, element, 127, 255, Imgproc.THRESH_BINARY);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return processMorphology(element);
        } else {
            throw new IllegalArgumentException("Wrong morphologyType: " + morphologyType);
        }
    }

    private BufferedImage processMorphology(Mat element) {
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        result = new Mat(BGR.rows(), BGR.cols(), CvType.CV_8UC1);
        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR.put(0, 0, sourceData);
        Core.split(BGR, channels);
        Imgproc.morphologyEx(channels.get(0), result, morphologyType, element);
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

    private Mat getElement(int size, String type) {
        Mat result = null;
        if (type.equals("ellipse")) {
            result = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(size, size));
        }
        if (type.equals("rectangle")) {
            result = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(size, size));
        }
        return result;
    }
}
