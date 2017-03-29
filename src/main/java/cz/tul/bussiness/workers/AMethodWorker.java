package cz.tul.bussiness.workers;

import cz.tul.bussiness.jobs.IJob;
import cz.tul.bussiness.register.OperationRegister;
import cz.tul.entities.PartAttributeValue;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Bc. Marek Jindrák on 17.10.2016.
 */
public abstract class AMethodWorker implements IMethodWorker {
    private static final Logger logger = LoggerFactory.getLogger(AMethodWorker.class);
    protected Set<PartAttributeValue> attributes;
    protected String imgName;
    protected IJob job;
    protected BufferedImage imgData;
    protected String classifier;
    protected byte[] sourceData;
    private byte[] localImgData;
    protected Mat BGR;
    protected List<Mat> channels = new ArrayList<>();
    private IJob work;
    protected BufferedImage originalImageData;

    public IJob getWork() {
        return work;
    }

    public void setWork(IJob work) {
        this.work = work;
    }

    public byte[] getSourceData() {
        return sourceData;
    }

    public void setSourceData(byte[] sourceData) {
        this.sourceData = sourceData;
    }

    @Override
    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    @Override
    public String getRealPath() {
        return OperationRegister.getInstance().getRealContextPath();
    }

    @Override
    public BufferedImage getImgData() {
        return imgData;
    }

    @Override
    public void setAttributes(Set<PartAttributeValue> attributes) {
        this.attributes = attributes;
    }

    @Override
    public void setImgName(String name) {
        this.imgName = name;
    }

    public String getImgName() {
        return imgName;
    }


    @Override
    public void setImgData(BufferedImage imgData) {
        this.imgData = imgData;
    }

    public Set<PartAttributeValue> getAttributes() {
        return attributes;
    }

    @Override
    public void saveImg() {
        File outputFile = null;
        outputFile = new File(getRealPath() + "\\img\\" + getImgName() + ".jpg");
        logger.info("Output" + getRealPath() + "\\img\\" + getImgName() + ".jpg");
        try {
            ImageIO.write(imgData, "jpg", outputFile);

            if (!channels.isEmpty() || job != null) {
                localImgData = ((DataBufferByte) imgData.getRaster().getDataBuffer()).getData();
                createHist();
                transformImage();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void type(BufferedImage type) {
        logger.info("cvType: " + CvType.typeToString(type.getType()));
    }

    @Override
    public void setOriginalImageData(BufferedImage originalImageData) {
        this.originalImageData = originalImageData;
    }

    private void createHist() {

        File outputFile;
        type(imgData);
        Mat mergedChannels = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        List<Mat> localChannles = new ArrayList<>();
        mergedChannels.put(0, 0, localImgData);
        Core.split(mergedChannels, localChannles);

        int rows = 600;
        int cols = 800;


        outputFile = new File(getRealPath() + "\\img\\histogram-" + getImgName() + ".jpg");


        Mat histogram = new Mat();
        MatOfFloat ranges = new MatOfFloat(0, 256);
        MatOfInt histSize = new MatOfInt(256);
        Imgproc.calcHist(
                localChannles,
                new MatOfInt(0),
                new Mat(),
                histogram,
                histSize,
                ranges);

        Core.MinMaxLocResult max = Core.minMaxLoc(histogram);


        BufferedImage img = new BufferedImage(800, 550, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D img2D = img.createGraphics();
        Font font = new Font("TimesRoman", Font.BOLD, 20);
        img2D.setFont(font);
        img2D.setPaint(Color.WHITE);
        img2D.fillRect(0, 0, cols, rows);
        img2D.setPaint(Color.BLACK);
        img2D.drawRect(15, 15, 3 * 256 + 1, 421);
        img2D.setPaint(Color.BLACK);
        img2D.drawRect(15, 449, 3 * 256 + 1, 51);

        double tmp;
        for (int i = 0; i < 256; i++) {
            tmp = ((140 * histogram.get(i, 0)[0] / max.maxVal)) * 3;
            img2D.setPaint(new Color(240, 125, 0));
            img2D.fillRect(16 + (3) * i, (int) (436 - tmp), 3, (int) tmp);
            img2D.setPaint(new Color(i, i, i));
            img2D.fillRect(16 + (3) * i, 450, 3, 50);

        }
        img2D.setPaint(Color.BLACK);
        img2D.drawString("0", 15, 520);
        img2D.drawString("255", 750, 520);

        logger.info("Output" + getRealPath() + "\\img\\histogram-" + getImgName() + ".jpg");
        try {
            ImageIO.write(img, "jpg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void transformImage() {
        Mat mergedChannels = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        List<Mat> localChannles = new ArrayList<>();
        mergedChannels.put(0, 0, localImgData);
        Core.split(mergedChannels, localChannles);
        Mat complexImage = new Mat();
        Mat image = localChannles.get(0);
        List<Mat> planes = new ArrayList<>();
        Mat padded = optimizeImageDim(image);
        padded.convertTo(padded, CvType.CV_32F);
        planes.add(padded);
        planes.add(Mat.zeros(padded.size(), CvType.CV_32F));
        Core.merge(planes, complexImage);
        Core.dft(complexImage, complexImage);
        createOptimizedMagnitude(complexImage);


    }

    private Mat optimizeImageDim(Mat image) {
        Mat padded = new Mat();
        int addPixelRows = Core.getOptimalDFTSize(image.rows());
        int addPixelCols = Core.getOptimalDFTSize(image.cols());
        Core.copyMakeBorder(image, padded, 0, addPixelRows - image.rows(), 0, addPixelCols - image.cols(),
                Core.BORDER_CONSTANT, Scalar.all(0));

        return padded;
    }

    private Mat generateColorBar() {
        List<Mat> colorBar = new ArrayList<>();
        Mat layer = new Mat(256, 1, CvType.CV_8UC1);
        Mat result = new Mat();
        for (int i = 0; i < 256; i++) {
            layer.put(i, 0, 255 - i);
        }
        colorBar.add(layer);
        colorBar.add(layer);
        colorBar.add(layer);
        Core.merge(colorBar, result);
        Imgproc.applyColorMap(result, result, Imgproc.COLORMAP_JET);
        return result;
    }

    private Mat createOptimizedMagnitude(Mat complexImage) {
        double[] white = new double[]{255, 255, 255};
        double[] black = new double[]{0, 0, 0};
        List<Mat> newPlanes = new ArrayList<>();
        Mat mag = new Mat();
        Mat result;
        int addWidth = 70;
        int minRow = 280;
        int ofset = 0;
        int color = 0;
        Core.split(complexImage, newPlanes);
        Core.magnitude(newPlanes.get(0), newPlanes.get(1), mag);
        Core.add(Mat.ones(mag.size(), CvType.CV_32F), mag, mag);
        Core.log(mag, mag);
        Core.MinMaxLocResult rep = Core.minMaxLoc(mag);
        System.out.printf("%f %f ", rep.maxVal, rep.minVal);
        this.shiftDFT(mag);
        mag.convertTo(mag, CvType.CV_8UC1);
        Core.normalize(mag, mag, 0, 255, Core.NORM_MINMAX, CvType.CV_8UC1);
        Imgproc.applyColorMap(mag, mag, Imgproc.COLORMAP_JET);

        List<Mat> splited = new ArrayList<>();

        Core.split(mag, splited);
        ofset = 0;
        if (mag.rows() < minRow) {
            ofset = minRow - mag.rows();
        }
        result = new Mat(mag.rows() + (ofset * 2), mag.cols() + addWidth, CvType.CV_8UC3);


        Mat colorBar = generateColorBar();

        for (int i = 0; i < result.rows(); i++) {
            for (int j = 0; j < result.cols(); j++) {
                if (j < mag.cols()) {
                    if (i > ofset && i < mag.rows() + ofset) {
                        result.put(i, j, mag.get(i - ofset, j));
                    } else {
                        result.put(i, j, white);
                    }

                } else {
                    if (i > (result.rows() / 2) - 128 && mag.cols() + 20 < j && color < 255 && mag.cols() + 40 > j) {

                        result.put(i, j, colorBar.get(color, 0));


                    } else if (i > (result.rows() / 2) - 128 && i < (result.rows() / 2) + 128) {

                        if (mag.cols() + 20 == j || mag.cols() + 40 == j) {
                            result.put(i, j, black);
                        } else {

                            result.put(i, j, white);
                        }
                    } else if (i == (result.rows() / 2) - 128 || i == (result.rows() / 2) + 128) {

                        if (mag.cols() + 20 <= j && mag.cols() + 40 >= j) {
                            result.put(i, j, black);
                        } else {

                            result.put(i, j, white);
                        }
                    } else {

                        result.put(i, j, white);
                    }
                }

            }
            if (i > (result.rows() / 2) - 128 && color < 256) {
                color++;
            }


        }


        int max = (int) rep.maxVal;

        if (max + 0.45 < rep.maxVal) {

            max++;
        }
        if (max >= 10) {
            Imgproc.putText(result, "" + max, new Point(mag.cols() + 10, (result.rows() / 2) - 132), 4, 1, new Scalar(0, 0, 0), 1);
        } else {
            Imgproc.putText(result, "" + max, new Point(mag.cols() + 10, (result.rows() / 2) - 132), 4, 1, new Scalar(0, 0, 0), 1);
        }
        Imgproc.putText(result, "0", new Point(mag.cols() + 20, (result.rows() / 2) + 152), 4, 1, new Scalar(0, 0, 0), 1);
        Imgcodecs.imwrite(getRealPath() + "\\img\\magnitude-" + getImgName() + ".jpg", result);

        return mag;
    }


    private void shiftDFT(Mat image) {
        image = image.submat(new Rect(0, 0, image.cols() & -2, image.rows() & -2));
        int cx = image.cols() / 2;
        int cy = image.rows() / 2;

        Mat q0 = new Mat(image, new Rect(0, 0, cx, cy));
        Mat q1 = new Mat(image, new Rect(cx, 0, cx, cy));
        Mat q2 = new Mat(image, new Rect(0, cy, cx, cy));
        Mat q3 = new Mat(image, new Rect(cx, cy, cx, cy));

        Mat tmp = new Mat();
        q0.copyTo(tmp);
        q3.copyTo(q0);
        tmp.copyTo(q3);

        q1.copyTo(tmp);
        q2.copyTo(q1);
        tmp.copyTo(q2);
    }


}
