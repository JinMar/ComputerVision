package cz.tul.bussiness.jobs.functions;

import cz.tul.bussiness.jobs.AJob;
import cz.tul.bussiness.jobs.exceptions.MinimalArgumentsException;
import cz.tul.bussiness.jobs.exceptions.NoTemplateFound;
import cz.tul.entities.PartAttributeValue;
import cz.tul.utilities.Utility;
import org.opencv.core.*;
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
 * Created by Bc. Marek Jindrák on 20.02.2017.
 */
public class TemplateMatching extends AJob {
    private static final Logger logger = LoggerFactory.getLogger(TemplateMatching.class);
    private int templateType;
    private Mat mask;
    private int layer = 6;


    @Override
    protected void init() throws MinimalArgumentsException, NoTemplateFound {

        for (PartAttributeValue att : getAttributes()) {

            if (att.getOperationAttributes().getAttribute().getName().equals("Maska")) {
                String base64Image = att.getValue();
                if (base64Image.length() > 0) {
                    byte[] data = Utility.getImageByte(base64Image.split(",")[1]);
                    try {
                        mask = getMask(data);
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                } else {
                    throw new NoTemplateFound("Data for mask was not found");
                }
                continue;
            }
            if (att.getOperationAttributes().getAttribute().getName().equals("Vrstva")) {
                switch (att.getValue()) {
                    case "Red":
                        layer = 0;
                        break;
                    case "Green":
                        layer = 1;
                        break;
                    case "Blue":
                        layer = 2;
                        break;
                    case "Cr":
                        layer = 3;
                        break;
                    case "Cb":
                        layer = 4;
                        break;
                    case "Y":
                        layer = 5;
                        break;
                    case "Gray":
                    default:
                        layer = 6;
                        break;
                }
                continue;
            }

            if (att.getOperationAttributes().getAttribute().getName().equals("Metoda")) {
                switch (att.getValue()) {
                    case "TM_CCOEFF":
                        templateType = Imgproc.TM_CCOEFF;
                        break;
                    case "TM_CCOEFF_NORMED":
                        templateType = Imgproc.TM_CCOEFF_NORMED;
                        break;
                    case "TM_CCORR":
                        templateType = Imgproc.TM_CCORR;
                        break;
                    case "TM_CCORR_NORMED":
                        templateType = Imgproc.TM_CCORR_NORMED;
                        break;
                    case "TM_SQDIFF":
                        templateType = Imgproc.TM_SQDIFF;
                        break;
                    case "TM_SQDIFF_NORMED":
                        templateType = Imgproc.TM_SQDIFF_NORMED;
                        break;
                }

            }

        }

    }

    @Override
    protected BufferedImage procces() {
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        result = new Mat(BGR.rows(), BGR.cols(), CvType.CV_8UC3);
        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR.put(0, 0, sourceData);
        Core.split(BGR, channels);

        Imgproc.matchTemplate(channels.get(0), getOptimalMat(), result, templateType);
        Core.MinMaxLocResult mmlr = Core.minMaxLoc(result);
        Point bottomR = new Point();
        Point topL = getTopLeftPoint(mmlr);
        bottomR.x = topL.x + mask.cols();
        bottomR.y = topL.y + mask.rows();
        Imgproc.rectangle(BGR, topL, bottomR, new Scalar(0, 255, 0), 1);
        finalData = new byte[BGR.rows() * BGR.cols() * channels.size()];
        BGR.get(0, 0, finalData);
        BufferedImage resultImgData = new BufferedImage(BGR.cols(), BGR.rows(), BufferedImage.TYPE_3BYTE_BGR);
        resultImgData.getRaster().setDataElements(0, 0, BGR.cols(), BGR.rows(), finalData);
        return resultImgData;
    }

    private Mat getOptimalMat() {
        List<Mat> maskChannels = new ArrayList<>();
        Mat localResult = new Mat(mask.rows(), mask.cols(), CvType.CV_8UC3);


        switch (layer) {
            case 0:
            case 1:
            case 2:
                Imgproc.cvtColor(mask, localResult, Imgproc.COLOR_BGR2RGB);
                break;
            case 3:
            case 4:
            case 5:
                Imgproc.cvtColor(mask, localResult, Imgproc.COLOR_RGB2YCrCb);
                break;
            case 6:
                Imgproc.cvtColor(mask, localResult, Imgproc.COLOR_RGB2GRAY);
                break;
        }

        Core.split(localResult, maskChannels);
        int test = layer % 3;
        return maskChannels.get(test);
    }

    private Point getTopLeftPoint(Core.MinMaxLocResult locPoint) {
        Point result;
        if (Imgproc.TM_SQDIFF == templateType || Imgproc.TM_SQDIFF_NORMED == templateType) {
            result = locPoint.minLoc;
        } else {
            result = locPoint.maxLoc;
        }
        return result;
    }

    private Mat getMask(byte[] input) throws IOException {
        BufferedImage data = ImageIO.read(new ByteArrayInputStream(input));
        Mat localMask = new Mat(data.getHeight(), data.getWidth(), CvType.CV_8UC3);
        localMask.put(0, 0, ((DataBufferByte) data.getRaster().getDataBuffer()).getData());

        return localMask;
    }
}
