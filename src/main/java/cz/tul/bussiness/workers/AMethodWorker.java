package cz.tul.bussiness.workers;

import cz.tul.bussiness.register.OperationRegister;
import cz.tul.entities.PartAttributeValue;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    protected BufferedImage imgData;
    protected String classifier;
    protected byte[] sourceData;
    protected Mat BGR;
    protected List<Mat> channels = new ArrayList<>();


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
            if (!channels.isEmpty()) {
                createhist();
                transformImage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createhist() {
        BufferedImage histData;
        File outputFile = null;
        Mat mergedChannels = new Mat(imgData.getHeight(), imgData.getWidth(), CvType.CV_8UC3);
        List<Mat> localChannles = new ArrayList<>();
        mergedChannels.put(0, 0, sourceData);
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


        BufferedImage img = new BufferedImage(800, 600, BufferedImage.TYPE_3BYTE_BGR);
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
        mergedChannels.put(0, 0, sourceData);
        Core.split(mergedChannels, localChannles);

        Mat complexImage = new Mat();
        Mat image = localChannles.get(0);
        List<Mat> planes = new ArrayList<>();
        Mat padded = optimizeImageDim(image);
        padded.convertTo(padded, CvType.CV_32F);
        // prepare the image planes to obtain the complex image
        planes.add(padded);
        planes.add(Mat.zeros(padded.size(), CvType.CV_32F));
        // prepare a complex image for performing the dft
        Core.merge(planes, complexImage);

        // dft
        Core.dft(complexImage, complexImage);

        // optimize the image resulting from the dft operation
        createOptimizedMagnitude(complexImage);


    }

    private Mat optimizeImageDim(Mat image) {
        // init
        Mat padded = new Mat();
        // get the optimal rows size for dft
        int addPixelRows = Core.getOptimalDFTSize(image.rows());
        // get the optimal cols size for dft
        int addPixelCols = Core.getOptimalDFTSize(image.cols());
        // apply the optimal cols and rows size to the image
        Core.copyMakeBorder(image, padded, 0, addPixelRows - image.rows(), 0, addPixelCols - image.cols(),
                Core.BORDER_CONSTANT, Scalar.all(0));

        return padded;
    }

    private Mat createOptimizedMagnitude(Mat complexImage) {
        // init
        List<Mat> newPlanes = new ArrayList<>();
        Mat mag = new Mat();
        // split the comples image in two planes
        Core.split(complexImage, newPlanes);
        // compute the magnitude
        Core.magnitude(newPlanes.get(0), newPlanes.get(1), mag);

        // move to a logarithmic scale
        Core.add(Mat.ones(mag.size(), CvType.CV_32F), mag, mag);
        Core.log(mag, mag);
        // optionally reorder the 4 quadrants of the magnitude image
        this.shiftDFT(mag);
        // normalize the magnitude image for the visualization
        // and OpenCV need images with value between 0 and 255
        // convert back to CV_8UC1
        mag.convertTo(mag, CvType.CV_8UC1);
        Core.normalize(mag, mag, 0, 255, Core.NORM_MINMAX, CvType.CV_8UC1);

        // you can also write on disk the resulting image...
        Imgproc.applyColorMap(mag, mag, Imgproc.COLORMAP_JET);
        Imgcodecs.imwrite(getRealPath() + "\\img\\magnitude-" + getImgName() + ".jpg", mag);

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
