package cz.tul.bussiness.workers;

import cz.tul.bussiness.workers.exceptions.SelectionLayerException;
import cz.tul.entities.PartAttributeValue;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.opencv.core.Core.BORDER_DEFAULT;
import static org.opencv.core.Core.convertScaleAbs;

/**
 * Created by Bc. Marek Jindrák on 17.10.2016.
 */
public class EdgeDetector extends Worker {
    private static final Logger logger = LoggerFactory.getLogger(EdgeDetector.class);
    private Mat channelFinal;

    @Override
    public void work() throws SelectionLayerException {
        sourceData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
        BGR = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        BGR.put(0, 0, sourceData);
        Mat gray = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        Imgproc.cvtColor(BGR, gray, Imgproc.COLOR_RGB2GRAY);
        Core.split(gray, channels);
        channelFinal = Mat.zeros(channels.get(0).rows(), channels.get(0).cols(), channels.get(0).type());

        for (PartAttributeValue att : getAttributes()) {
            if (att.getValue().equals(EdgeDetectorEnum.LAPLACIAN.getDetectorlName())) {
                Laplacian();
            }
            if (att.getValue().equals(EdgeDetectorEnum.SOBEL.getDetectorlName())) {
                Sobel();
            }
        }

    }

    private void Sobel() {

        double[][] sobelMatrix0 = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};
        double[][] sobelMatrix1 = {{0, 1, 2}, {-1, 0, 1}, {-2, -1, -0}};
        double[][] sobelMatrix2 = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        double[][] sobelMatrix3 = {{-2, 1, 0}, {-1, 0, 1}, {0, 1, 2}};
        double[][] sobelMatrix4 = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        double[][] sobelMatrix5 = {{0, -1, -2}, {1, 0, -1}, {2, 1, 0}};
        double[][] sobelMatrix6 = {{1, 0, -1}, {1, 0, -1}, {1, 0, -1}};
        double[][] sobelMatrix7 = {{2, 1, 0}, {1, 0, -1}, {0, -1, -2}};
        ArrayList<Double> sobelVector = new ArrayList<>();
        double[][] sourceSubMatrix = new double[3][3];
        double maxTemp = 0, temp;
        double[][] dataSobel = new double[channels.get(0).rows()][channels.get(0).cols()];

        double[] test = new double[1];
        for (int i = 1; i < channels.get(0).rows() - 2; i++) {
            for (int j = 1; j < channels.get(0).cols() - 2; j++) {
                sourceSubMatrix[0][0] = channels.get(0).get(i - 1, j - 1)[0];
                sourceSubMatrix[0][1] = channels.get(0).get(i - 1, j)[0];
                sourceSubMatrix[0][2] = channels.get(0).get(i - 1, j + 1)[0];
                sourceSubMatrix[1][0] = channels.get(0).get(i, j - 1)[0];
                sourceSubMatrix[1][1] = 0;
                sourceSubMatrix[1][2] = channels.get(0).get(i, j + 1)[0];
                sourceSubMatrix[2][0] = channels.get(0).get(i + 1, j - 1)[0];
                sourceSubMatrix[2][1] = channels.get(0).get(i + 1, j)[0];
                sourceSubMatrix[2][2] = channels.get(0).get(i + 1, j + 1)[0];
                sobelVector.add(sobelConv(sobelMatrix0, sourceSubMatrix));

                sobelVector.add(sobelConv(sobelMatrix1, sourceSubMatrix));
                sobelVector.add(sobelConv(sobelMatrix2, sourceSubMatrix));
                sobelVector.add(sobelConv(sobelMatrix3, sourceSubMatrix));
                sobelVector.add(sobelConv(sobelMatrix4, sourceSubMatrix));
                sobelVector.add(sobelConv(sobelMatrix5, sourceSubMatrix));
                sobelVector.add(sobelConv(sobelMatrix6, sourceSubMatrix));
                sobelVector.add(sobelConv(sobelMatrix7, sourceSubMatrix));
                temp = Collections.max(sobelVector);
                sobelVector.clear();
                dataSobel[i][j] = temp;
                if (maxTemp < temp) {
                    maxTemp = temp;
                }
            }

        }


        for (int i = 1; i < channels.get(0).rows() - 2; i++) {
            for (int j = 1; j < channels.get(0).cols() - 2; j++) {

                test[0] = (int) (dataSobel[i][j] * 255 / maxTemp);

                channelFinal.put(i, j, test);
            }

        }
        save();

    }

    public void Laplacian() {


        channelFinal = Mat.zeros(channels.get(0).rows(), channels.get(0).cols(), channels.get(0).type());
        Imgproc.GaussianBlur(channels.get(0), channels.get(0), new Size(3, 3), 0, 0, BORDER_DEFAULT);
        Imgproc.Laplacian(channels.get(0), channelFinal, channels.get(0).depth(), 1, 1, BORDER_DEFAULT);
        convertScaleAbs(channelFinal, channelFinal);

        save();
    }

    private void save() {
        Mat BGR = new Mat(channels.get(0).rows(), channels.get(0).cols(), CvType.CV_8UC3);
        List<Mat> RGB = new ArrayList<>();
        RGB.add(channelFinal);
        RGB.add(channelFinal);
        RGB.add(channelFinal);
        Core.merge(RGB, BGR);
        sourceData = new byte[channels.get(0).rows() * channels.get(0).cols() * (int) BGR.elemSize()];
        BGR.get(0, 0, sourceData);
        imgData = new BufferedImage(channels.get(0).cols(), channels.get(0).rows(), BufferedImage.TYPE_3BYTE_BGR);
        imgData.getRaster().setDataElements(0, 0, channels.get(0).cols(), channels.get(0).rows(), sourceData);
        saveImg();

    }

    private double sobelConv(double[][] Sobelkernel, double[][] sourceKernel) {
        double result = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                result += Sobelkernel[i][j] * sourceKernel[j][j];
            }
        }

        return result;
    }
}
