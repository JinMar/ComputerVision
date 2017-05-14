package cz.tul.provisioner;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import cz.tul.bussiness.register.OperationRegister;
import cz.tul.bussiness.workers.*;
import cz.tul.bussiness.workers.enums.*;
import cz.tul.entities.*;
import cz.tul.repositories.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Marek on 29.09.2016.
 */
@Component
public class DbProvisioner implements InitializingBean {

    @Autowired
    AttributeDAO attributeDAO;
    @Autowired
    AllowStepDAO allowStepDAO;
    @Autowired
    FunctionDAO functionDAO;
    @Autowired
    ChainDAO chainDAO;
    @Autowired
    MethodDAO methodDAO;
    @Autowired
    PartDAO partDAO;
    @Autowired
    OperationDAO operationDAO;
    @Autowired
    PartAttributeValueDAO partAttributeValueDAO;
    @Autowired
    ServletContext servletContext;


    private OperationRegister operationRegister;


    @Override
    public void afterPropertiesSet() throws Exception {

        operationRegister = OperationRegister.getInstance();
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);

        String test = servletContext.getRealPath("/");
        operationRegister.registerContextPath(test);

        Set<Method> methods = new HashSet<>();
        Set<AllowStep> allowSteps = new HashSet<>();
        Set<Operation> operations = new HashSet<>();
        Set<Attribute> attributes = new HashSet<>();
        Set<Function> functions = new HashSet<>();
        Set<OperationAttributes> operationAttributes = new HashSet<>();

        Function origo = new Function();
        origo.setName("Original");
        functions.add(origo);
        Function colorSpaces = new Function();
        colorSpaces.setName("Color Spaces");
        functions.add(colorSpaces);
        Function edgeDetectors = new Function();
        edgeDetectors.setName("Edge Detectors");
        functions.add(edgeDetectors);
        Function denoiser = new Function();
        denoiser.setName("Smoothing Images");
        functions.add(denoiser);
        Function morphology = new Function();
        morphology.setName("Morphology operations");
        functions.add(morphology);
        Function segment = new Function();
        segment.setName("Segmentation");
        functions.add(segment);
        Function othterFCE = new Function();
        othterFCE.setName("Image recognition");
        functions.add(othterFCE);
        Function houghTransform = new Function();
        houghTransform.setName("Hough transform");
        functions.add(houghTransform);


        Function geometricTransformation = new Function();
        geometricTransformation.setName("Geom. Transformations");
        functions.add(geometricTransformation);

        functionDAO.save(functions);

        // RGB funkce
        Method RGB = new Method();
        RGB.setFunction(colorSpaces);
        RGB.setName("RGB");
        methods.add(RGB);

        Method original = new Method();
        original.setFunction(origo);
        original.setName("original");
        methods.add(original);

        Method YCBCR = new Method();
        YCBCR.setName("YCBCR");
        YCBCR.setFunction(colorSpaces);
        methods.add(YCBCR);

        Method HSV = new Method();
        HSV.setName("HSV");
        HSV.setFunction(colorSpaces);
        methods.add(HSV);

        Method grayScaleCS = new Method();
        grayScaleCS.setName("Grayscale");
        grayScaleCS.setFunction(colorSpaces);
        methods.add(grayScaleCS);

        Method fDerivation = new Method();
        fDerivation.setFunction(edgeDetectors);
        fDerivation.setName("Edge Detectors");
        methods.add(fDerivation);


        Method noise = new Method();
        noise.setFunction(denoiser);
        noise.setName("Smoothing Images");
        methods.add(noise);

        Method coloring = new Method();
        coloring.setFunction(othterFCE);
        coloring.setName("Region Identification");
        methods.add(coloring);

        Method thresholding = new Method();
        thresholding.setFunction(segment);
        thresholding.setName("Thresholding");
        methods.add(thresholding);


        Method bin = new Method();
        bin.setFunction(morphology);
        bin.setName("Binary");
        methods.add(bin);

        Method grayscale = new Method();
        grayscale.setFunction(morphology);
        grayscale.setName("Grayscale");
        methods.add(grayscale);

        Method gTransformation = new Method();
        gTransformation.setFunction(geometricTransformation);
        gTransformation.setName("Geom. Transformations");
        methods.add(gTransformation);

        Method hTransformation = new Method();
        hTransformation.setFunction(houghTransform);
        hTransformation.setName("Hough transform");
        methods.add(hTransformation);

        Method tmplMatching = new Method();
        tmplMatching.setFunction(othterFCE);
        tmplMatching.setName("Template Matching");
        methods.add(tmplMatching);

        Method grabCut = new Method();
        grabCut.setFunction(othterFCE);
        grabCut.setName("GrabCut");
        methods.add(grabCut);

        methodDAO.save(methods);

        Operation originalOperation = new Operation();
        originalOperation.setName("Original");
        originalOperation.setMethod(original);
        operations.add(originalOperation);

        Operation redOperation = new Operation();
        redOperation.setName("Red");
        redOperation.setMethod(RGB);
        operations.add(redOperation);

        Operation greenOperation = new Operation();
        greenOperation.setName("Green");
        greenOperation.setMethod(RGB);
        operations.add(greenOperation);

        Operation blueOperation = new Operation();
        blueOperation.setName("Blue");
        blueOperation.setMethod(RGB);
        operations.add(blueOperation);

        Operation grayOperation = new Operation();
        grayOperation.setName("Gray");
        grayOperation.setMethod(grayScaleCS);
        operations.add(grayOperation);

        Operation yOperation = new Operation();
        yOperation.setName("Y");
        yOperation.setMethod(YCBCR);
        operations.add(yOperation);

        Operation cbOperation = new Operation();
        cbOperation.setName("CB");
        cbOperation.setMethod(YCBCR);
        operations.add(cbOperation);

        Operation crOperation = new Operation();
        crOperation.setName("CR");
        crOperation.setMethod(YCBCR);
        operations.add(crOperation);

        Operation hOperation = new Operation();
        hOperation.setName("H");
        hOperation.setMethod(HSV);
        operations.add(hOperation);

        Operation THRESH_BINARY = new Operation();
        THRESH_BINARY.setName("THRESH_BINARY");
        THRESH_BINARY.setMethod(thresholding);
        operations.add(THRESH_BINARY);

        Operation THRESH_BINARY_INV = new Operation();
        THRESH_BINARY_INV.setName("THRESH_BINARY_INV");
        THRESH_BINARY_INV.setMethod(thresholding);
        operations.add(THRESH_BINARY_INV);

        Operation THRESH_TRUNC = new Operation();
        THRESH_TRUNC.setName("THRESH_TRUNC");
        THRESH_TRUNC.setMethod(thresholding);
        operations.add(THRESH_TRUNC);

        Operation THRESH_TOZERO = new Operation();
        THRESH_TOZERO.setName("THRESH_TOZERO");
        THRESH_TOZERO.setMethod(thresholding);
        operations.add(THRESH_TOZERO);

        Operation average = new Operation();
        average.setName("Average");
        average.setMethod(noise);
        operations.add(average);

        Operation median = new Operation();
        median.setName("Median");
        median.setMethod(noise);
        operations.add(median);

        Operation rotateMask = new Operation();
        rotateMask.setName("Rotating Mask");
        rotateMask.setMethod(noise);
        operations.add(rotateMask);

        Operation sobel = new Operation();
        sobel.setName("Sobel");
        sobel.setMethod(fDerivation);
        operations.add(sobel);

        Operation canny = new Operation();
        canny.setName("Canny");
        canny.setMethod(fDerivation);
        operations.add(canny);

        Operation laplace = new Operation();
        laplace.setName("Laplacian");
        laplace.setMethod(fDerivation);
        operations.add(laplace);

        Operation erode = new Operation();
        erode.setName("Erode");
        erode.setMethod(bin);
        operations.add(erode);

        Operation dilate = new Operation();
        dilate.setName("Dilate");
        dilate.setMethod(bin);
        operations.add(dilate);

        Operation open = new Operation();
        open.setName("Open");
        open.setMethod(bin);
        operations.add(open);

        Operation close = new Operation();
        close.setName("Close");
        close.setMethod(bin);
        operations.add(close);

        Operation topHat = new Operation();
        topHat.setName("Tophat");
        topHat.setMethod(grayscale);
        operations.add(topHat);

        Operation open_a = new Operation();
        open_a.setName("Open");
        open_a.setMethod(grayscale);
        operations.add(open_a);

        Operation close_a = new Operation();
        close_a.setName("Close");
        close_a.setMethod(grayscale);
        operations.add(close_a);

        Operation erode_a = new Operation();
        erode_a.setName("Erode");
        erode_a.setMethod(grayscale);
        operations.add(erode_a);

        Operation dilate_a = new Operation();
        dilate_a.setName("Dilate");
        dilate_a.setMethod(grayscale);
        operations.add(dilate_a);


        Operation colorSeg = new Operation();
        colorSeg.setName("Region Identification");
        colorSeg.setMethod(coloring);
        operations.add(colorSeg);

        Operation resizing = new Operation();
        resizing.setName("Resizing");
        resizing.setMethod(gTransformation);
        operations.add(resizing);

        Operation rotating = new Operation();
        rotating.setName("Rotating");
        rotating.setMethod(gTransformation);
        operations.add(rotating);

        Operation HTC = new Operation();
        HTC.setName("Hough circles");
        HTC.setMethod(hTransformation);
        operations.add(HTC);

        Operation HTL = new Operation();
        HTL.setName("Hough lines");
        HTL.setMethod(hTransformation);
        operations.add(HTL);

        Operation tempMatching = new Operation();
        tempMatching.setName("Template Matching");
        tempMatching.setMethod(tmplMatching);
        operations.add(tempMatching);

        Operation gCut = new Operation();
        gCut.setName("Rect");
        gCut.setMethod(grabCut);
        operations.add(gCut);

        Operation gCutM = new Operation();
        gCutM.setName("Mask");
        gCutM.setMethod(grabCut);
        operations.add(gCutM);

        Operation distTransfrom = new Operation();
        distTransfrom.setName("Distance Transform");
        distTransfrom.setMethod(bin);
        operations.add(distTransfrom);

        Operation watershed = new Operation();
        watershed.setName("Watershed");
        watershed.setMethod(bin);
        operations.add(watershed);

        operationDAO.save(operations);


        operationRegister.register(originalOperation.getOperationId(), OriginalRGB.class, ChannelsEnum.ORIGINAL.getChannelName());
        operationRegister.registerOriginal(originalOperation.getOperationId());

        operationRegister.register(redOperation.getOperationId(), RGBChannel.class, ChannelsEnum.RED.getChannelName());
        operationRegister.register(greenOperation.getOperationId(), RGBChannel.class, ChannelsEnum.GREEN.getChannelName());
        operationRegister.register(blueOperation.getOperationId(), RGBChannel.class, ChannelsEnum.BLUE.getChannelName());
        operationRegister.register(grayOperation.getOperationId(), RGBChannel.class, ChannelsEnum.GRAY.getChannelName());

        operationRegister.register(yOperation.getOperationId(), YCBCRChannel.class, ChannelsEnum.Y.getChannelName());
        operationRegister.register(cbOperation.getOperationId(), YCBCRChannel.class, ChannelsEnum.CB.getChannelName());
        operationRegister.register(crOperation.getOperationId(), YCBCRChannel.class, ChannelsEnum.CR.getChannelName());

        operationRegister.register(hOperation.getOperationId(), HSVChannel.class, ChannelsEnum.H.getChannelName());

        operationRegister.register(sobel.getOperationId(), EdgeDetectors.class, EdgeDetectorEnum.SOBEL.getDetectorlName());
        operationRegister.register(laplace.getOperationId(), EdgeDetectors.class, EdgeDetectorEnum.LAPLACIAN.getDetectorlName());
        operationRegister.register(canny.getOperationId(), EdgeDetectors.class, EdgeDetectorEnum.CANNY.getDetectorlName());

        operationRegister.register(average.getOperationId(), NoiseReducer.class, NoiseReducerEnum.SIMPLEAVERAGING.getReducerName());
        operationRegister.register(median.getOperationId(), NoiseReducer.class, NoiseReducerEnum.MEDIAN.getReducerName());
        operationRegister.register(rotateMask.getOperationId(), NoiseReducer.class, NoiseReducerEnum.ROTATINGMASK.getReducerName());

        operationRegister.register(THRESH_BINARY.getOperationId(), SegmentationW.class, SegmentorEnum.THRESH_BINARY.getSegmentorName());
        operationRegister.register(THRESH_BINARY_INV.getOperationId(), SegmentationW.class, SegmentorEnum.THRESH_BINARY_INV.getSegmentorName());
        operationRegister.register(THRESH_TOZERO.getOperationId(), SegmentationW.class, SegmentorEnum.THRESH_TOZERO.getSegmentorName());
        operationRegister.register(THRESH_TRUNC.getOperationId(), SegmentationW.class, SegmentorEnum.THRESH_TRUNC.getSegmentorName());

        operationRegister.register(watershed.getOperationId(), SegmentationW.class, SegmentorEnum.WATERSHED.getSegmentorName());

        operationRegister.register(erode.getOperationId(), MorfologyTransformations.class, MorphologyEnum.ERODE.getMorphologyName());
        operationRegister.register(dilate.getOperationId(), MorfologyTransformations.class, MorphologyEnum.DILATE.getMorphologyName());
        operationRegister.register(open.getOperationId(), MorfologyTransformations.class, MorphologyEnum.OPEN.getMorphologyName());
        operationRegister.register(close.getOperationId(), MorfologyTransformations.class, MorphologyEnum.CLOSE.getMorphologyName());
        operationRegister.register(erode_a.getOperationId(), MorfologyTransformations.class, MorphologyEnum.ERODEGS.getMorphologyName());
        operationRegister.register(dilate_a.getOperationId(), MorfologyTransformations.class, MorphologyEnum.DILATEGS.getMorphologyName());
        operationRegister.register(open_a.getOperationId(), MorfologyTransformations.class, MorphologyEnum.OPENGS.getMorphologyName());
        operationRegister.register(close_a.getOperationId(), MorfologyTransformations.class, MorphologyEnum.CLOSEGS.getMorphologyName());
        operationRegister.register(topHat.getOperationId(), MorfologyTransformations.class, MorphologyEnum.TOPHAT.getMorphologyName());
        operationRegister.register(distTransfrom.getOperationId(), MorfologyTransformations.class, MorphologyEnum.DISTANCETRANSFORM.getMorphologyName());

        operationRegister.register(resizing.getOperationId(), GeometricTransformations.class, GeometricTransformationEnum.RESIZE.getGgometricTransformationName());
        operationRegister.register(rotating.getOperationId(), GeometricTransformations.class, GeometricTransformationEnum.ROTATE.getGgometricTransformationName());

        operationRegister.register(HTC.getOperationId(), HoughTransformations.class, HoughTransformationEnum.CIRCLE.getGgometricTransformationName());
        operationRegister.register(HTL.getOperationId(), HoughTransformations.class, HoughTransformationEnum.LINE.getGgometricTransformationName());

        operationRegister.register(colorSeg.getOperationId(), ImageRecognition.class, SegmentorEnum.COLORING.getSegmentorName());
        operationRegister.register(tempMatching.getOperationId(), ImageRecognition.class, TemplateEnum.TM.getTemplateName());
        operationRegister.register(gCut.getOperationId(), ImageRecognition.class, ExtractForegroundEnum.EXTRACT_FOREGROUND.getExtractForegroundName());
        operationRegister.register(gCutM.getOperationId(), ImageRecognition.class, ExtractForegroundEnum.EXTRACT_FOREGROUND_MASK.getExtractForegroundName());

        Attribute step = new Attribute("Krok");
        Attribute shape = new Attribute("Tvar");
        Attribute size = new Attribute("Velikost");
        Attribute inputImg = new Attribute("Vstupní obraz");
        Attribute channel = new Attribute("Vrstva");
        Attribute segmentor = new Attribute("Segmentor");
        Attribute type = new Attribute("Typ");
        Attribute threshold = new Attribute("Práh");
        Attribute angle = new Attribute("Úhel");
        Attribute interpolation = new Attribute("Interpolace");
        Attribute mask = new Attribute("Maska");
        Attribute use = new Attribute("Použít tvar");
        Attribute method = new Attribute("Metoda");
        Attribute dp = new Attribute("DP");
        Attribute minDist = new Attribute("Min vzdálenost");
        Attribute ratio = new Attribute("Poměr");
        Attribute minLen = new Attribute("Min. délka");
        Attribute maxLineGap = new Attribute("Min. Line Gap");
        Attribute metrics = new Attribute("Metrika");
        Attribute startX = new Attribute("Počatek X");
        Attribute startY = new Attribute("Počatek Y");
        Attribute w = new Attribute("Výška");
        Attribute h = new Attribute("Šířka");
        Attribute iteration = new Attribute("Iteration");
        Attribute scale = new Attribute("Scale");

        Attribute aIteration = new Attribute("A Iterace");
        Attribute bIteration = new Attribute("B Iterace");
        Attribute aThresh = new Attribute("A práh");
        Attribute bThresh = new Attribute("B práh");
        Attribute aMoType = new Attribute("A. Typ moph. t. ");
        Attribute bMoType = new Attribute("B. Typ moph. t. ");
        Attribute aThresType = new Attribute("A Thresh type");
        Attribute bThresType = new Attribute("B Thresh type");


        attributes.add(type);
        attributes.add(channel);
        attributes.add(step);
        attributes.add(shape);
        attributes.add(size);
        attributes.add(inputImg);
        attributes.add(threshold);
        attributes.add(angle);
        attributes.add(use);
        attributes.add(mask);
        attributes.add(method);
        attributes.add(dp);
        attributes.add(minDist);
        attributes.add(ratio);
        attributes.add(minLen);
        attributes.add(metrics);
        attributes.add(aIteration);
        attributes.add(bIteration);
        attributes.add(aThresh);
        attributes.add(bThresh);
        attributes.add(aMoType);
        attributes.add(bMoType);
        attributes.add(aThresType);
        attributes.add(bThresType);
        attributes.add(iteration);
        attributeDAO.save(attributes);

        /*
        OperationAttributes red = new OperationAttributes();
        OperationAttributes green = new OperationAttributes();
        OperationAttributes bleu = new OperationAttributes();
        OperationAttributes gray = new OperationAttributes();
        OperationAttributes y = new OperationAttributes();
        OperationAttributes cb = new OperationAttributes();
        OperationAttributes cr = new OperationAttributes();
        OperationAttributes h = new OperationAttributes();
        OperationAttributes color = new OperationAttributes();
        OperationAttributes avr = new OperationAttributes();
        OperationAttributes med = new OperationAttributes();
        OperationAttributes rotMask = new OperationAttributes();
        OperationAttributes sobelED = new OperationAttributes();
        OperationAttributes laplacianED = new OperationAttributes();
        */

        OperationAttributes trashA = new OperationAttributes();
        trashA.setMinValue(0);
        trashA.setMaxValue(255);
        trashA.setOperation(THRESH_BINARY);
        trashA.setAttribute(threshold);
        trashA.setAttributeType(AttributeType.NUMBER);
        trashA.setDefaultValues("125");
        operationAttributes.add(trashA);

        OperationAttributes trashB = new OperationAttributes();
        trashB.setMinValue(0);
        trashB.setMaxValue(255);
        trashB.setOperation(THRESH_BINARY_INV);
        trashB.setAttribute(threshold);
        trashB.setAttributeType(AttributeType.NUMBER);
        trashB.setDefaultValues("125");
        operationAttributes.add(trashB);

        OperationAttributes trashC = new OperationAttributes();
        trashC.setMinValue(0);
        trashC.setMaxValue(255);
        trashC.setOperation(THRESH_TOZERO);
        trashC.setAttribute(threshold);
        trashC.setAttributeType(AttributeType.NUMBER);
        trashC.setDefaultValues("125");
        operationAttributes.add(trashC);

        OperationAttributes trashD = new OperationAttributes();
        trashD.setMinValue(0);
        trashD.setMaxValue(255);
        trashD.setOperation(THRESH_TRUNC);
        trashD.setAttribute(threshold);
        trashD.setAttributeType(AttributeType.NUMBER);
        trashD.setDefaultValues("125");
        operationAttributes.add(trashD);
////////////////
        OperationAttributes laplace_A = new OperationAttributes();
        laplace_A.setMinValue(1);
        laplace_A.setMaxValue(15);
        laplace_A.setDefaultValues("1");
        laplace_A.setOperation(laplace);
        laplace_A.setAttribute(scale);
        laplace_A.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(laplace_A);

        Map<String, String> kSize = new HashMap<>();
        kSize.put("1", "1");
        kSize.put("3", "3");
        kSize.put("5", "5");
        kSize.put("7", "7");


        OperationAttributes laplace_B = new OperationAttributes();
        laplace_B.setDefaultValues("1");
        laplace_B.setOperation(laplace);
        laplace_B.setAttribute(size);
        laplace_B.setOptions(kSize);
        laplace_B.setAttributeType(AttributeType.SELECT);
        operationAttributes.add(laplace_B);

        OperationAttributes sobel_A = new OperationAttributes();
        sobel_A.setMinValue(1);
        sobel_A.setMaxValue(15);
        sobel_A.setDefaultValues("1");
        sobel_A.setOperation(sobel);
        sobel_A.setAttribute(scale);
        sobel_A.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(sobel_A);

        OperationAttributes sobel_B = new OperationAttributes();
        sobel_B.setDefaultValues("1");
        sobel_B.setOptions(kSize);
        sobel_B.setOperation(sobel);
        sobel_B.setAttribute(size);
        sobel_B.setAttributeType(AttributeType.SELECT);
        operationAttributes.add(sobel_B);


        Map<String, String> useOperationValues = new HashMap<>();
        useOperationValues.put("default", "Výchozí");
        useOperationValues.put("custom", "Vlastní");

        OperationAttributes cannyA = new OperationAttributes();
        cannyA.setMinValue(0);
        cannyA.setMaxValue(256);
        cannyA.setDefaultValues("127");
        cannyA.setOperation(canny);
        cannyA.setAttribute(ratio);
        cannyA.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(cannyA);

        OperationAttributes cannyB = new OperationAttributes();
        cannyB.setMinValue(0);
        cannyB.setMaxValue(100);
        cannyB.setDefaultValues("1");
        cannyB.setOperation(canny);
        cannyB.setAttribute(threshold);
        cannyB.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(cannyB);

        OperationAttributes erodeM_A = new OperationAttributes();
        erodeM_A.setMinValue(3);
        erodeM_A.setMaxValue(5);
        erodeM_A.setDefaultValues("3");
        erodeM_A.setOperation(erode);
        erodeM_A.setAttribute(size);
        erodeM_A.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(erodeM_A);

        OperationAttributes erodeM_B = new OperationAttributes();
        Map<String, String> shapes = new HashMap<>();
        shapes.put("ellipse", "Elipsa");
        shapes.put("rectangle", "Obdelník");
        shapes.put("custom", "Vlastní");
        erodeM_B.setDefaultValues("rectangle");
        erodeM_B.setOperation(erode);
        erodeM_B.setAttribute(shape);
        erodeM_B.setAttributeType(AttributeType.SELECT);
        erodeM_B.setOptions(shapes);
        operationAttributes.add(erodeM_B);

        OperationAttributes erodeM_C = new OperationAttributes();
        erodeM_C.setOperation(erode);
        erodeM_C.setAttribute(mask);
        erodeM_C.setAttributeType(AttributeType.IMAGE);
        operationAttributes.add(erodeM_C);

        OperationAttributes erodeM_D = new OperationAttributes();
        erodeM_D.setOperation(erode);
        erodeM_D.setAttribute(iteration);
        erodeM_D.setMinValue(1);
        erodeM_D.setMaxValue(15);
        erodeM_D.setDefaultValues("1");
        erodeM_D.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(erodeM_D);


////
        OperationAttributes dilateM_A = new OperationAttributes();
        dilateM_A.setMinValue(3);
        dilateM_A.setMaxValue(5);
        dilateM_A.setDefaultValues("3");
        dilateM_A.setOperation(dilate);
        dilateM_A.setAttribute(size);
        dilateM_A.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(dilateM_A);

        OperationAttributes dilateM_B = new OperationAttributes();
        dilateM_B.setDefaultValues("rectangle");
        dilateM_B.setOperation(dilate);
        dilateM_B.setAttribute(shape);
        dilateM_B.setAttributeType(AttributeType.SELECT);
        dilateM_B.setOptions(shapes);
        operationAttributes.add(dilateM_B);

        OperationAttributes dilateM_C = new OperationAttributes();
        dilateM_C.setOperation(dilate);
        dilateM_C.setAttribute(mask);
        dilateM_C.setAttributeType(AttributeType.IMAGE);
        operationAttributes.add(dilateM_C);

        OperationAttributes dilateM_D = new OperationAttributes();
        dilateM_D.setOperation(dilate);
        dilateM_D.setAttribute(iteration);
        dilateM_D.setMinValue(1);
        dilateM_D.setMaxValue(15);
        dilateM_D.setDefaultValues("1");
        dilateM_D.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(dilateM_D);


////
        OperationAttributes openM_A = new OperationAttributes();
        openM_A.setMinValue(3);
        openM_A.setMaxValue(5);
        openM_A.setDefaultValues("3");
        openM_A.setOperation(open);
        openM_A.setAttribute(size);
        openM_A.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(openM_A);

        OperationAttributes openM_B = new OperationAttributes();
        openM_B.setDefaultValues("rectangle");
        openM_B.setOperation(open);
        openM_B.setAttribute(shape);
        openM_B.setAttributeType(AttributeType.SELECT);
        openM_B.setOptions(shapes);
        operationAttributes.add(openM_B);

        OperationAttributes openM_C = new OperationAttributes();
        openM_C.setOperation(open);
        openM_C.setAttribute(mask);
        openM_C.setAttributeType(AttributeType.IMAGE);
        operationAttributes.add(openM_C);

        OperationAttributes openM_D = new OperationAttributes();
        openM_D.setOperation(open);
        openM_D.setAttribute(iteration);
        openM_D.setMinValue(1);
        openM_D.setMaxValue(15);
        openM_D.setDefaultValues("1");
        openM_D.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(openM_D);


////
        OperationAttributes closeM_A = new OperationAttributes();
        closeM_A.setMinValue(3);
        closeM_A.setMaxValue(5);
        closeM_A.setDefaultValues("3");
        closeM_A.setOperation(close);
        closeM_A.setAttribute(size);
        closeM_A.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(closeM_A);

        OperationAttributes closeM_B = new OperationAttributes();
        closeM_B.setDefaultValues("rectangle");
        closeM_B.setOperation(close);
        closeM_B.setAttribute(shape);
        closeM_B.setAttributeType(AttributeType.SELECT);
        closeM_B.setOptions(shapes);
        operationAttributes.add(closeM_B);

        OperationAttributes closeM_C = new OperationAttributes();
        closeM_C.setOperation(close);
        closeM_C.setAttribute(mask);
        closeM_C.setAttributeType(AttributeType.IMAGE);
        operationAttributes.add(closeM_C);

        OperationAttributes closeM_D = new OperationAttributes();
        closeM_D.setOperation(close);
        closeM_D.setAttribute(iteration);
        closeM_D.setMinValue(1);
        closeM_D.setMaxValue(15);
        closeM_D.setDefaultValues("1");
        closeM_D.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(closeM_D);
////

        OperationAttributes erodeM_AA = new OperationAttributes();
        erodeM_AA.setMinValue(3);
        erodeM_AA.setMaxValue(5);
        erodeM_AA.setDefaultValues("3");
        erodeM_AA.setOperation(erode_a);
        erodeM_AA.setAttribute(size);
        erodeM_AA.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(erodeM_AA);

        OperationAttributes erodeM_BB = new OperationAttributes();
        erodeM_BB.setDefaultValues("rectangle");
        erodeM_BB.setOperation(erode_a);
        erodeM_BB.setAttribute(shape);
        erodeM_BB.setAttributeType(AttributeType.SELECT);
        erodeM_BB.setOptions(shapes);
        operationAttributes.add(erodeM_BB);

        OperationAttributes erodeM_CC = new OperationAttributes();
        erodeM_CC.setOperation(erode_a);
        erodeM_CC.setAttribute(mask);
        erodeM_CC.setAttributeType(AttributeType.IMAGE);
        operationAttributes.add(erodeM_CC);

        OperationAttributes erodeM_DD = new OperationAttributes();
        erodeM_DD.setOperation(erode_a);
        erodeM_DD.setAttribute(iteration);
        erodeM_DD.setMinValue(1);
        erodeM_DD.setMaxValue(15);
        erodeM_DD.setDefaultValues("1");
        erodeM_DD.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(erodeM_DD);


////
        OperationAttributes dilateM_AA = new OperationAttributes();
        dilateM_AA.setMinValue(3);
        dilateM_AA.setMaxValue(5);
        dilateM_AA.setDefaultValues("3");
        dilateM_AA.setOperation(dilate_a);
        dilateM_AA.setAttribute(size);
        dilateM_AA.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(dilateM_AA);

        OperationAttributes dilateM_BB = new OperationAttributes();
        dilateM_BB.setDefaultValues("rectangle");
        dilateM_BB.setOperation(dilate_a);
        dilateM_BB.setAttribute(shape);
        dilateM_BB.setAttributeType(AttributeType.SELECT);
        dilateM_BB.setOptions(shapes);
        operationAttributes.add(dilateM_BB);

        OperationAttributes dilateM_CC = new OperationAttributes();
        dilateM_CC.setOperation(dilate_a);
        dilateM_CC.setAttribute(mask);
        dilateM_CC.setAttributeType(AttributeType.IMAGE);
        operationAttributes.add(dilateM_CC);

        OperationAttributes dilateM_DD = new OperationAttributes();
        dilateM_DD.setOperation(dilate_a);
        dilateM_DD.setAttribute(iteration);
        dilateM_DD.setMinValue(1);
        dilateM_DD.setMaxValue(15);
        dilateM_DD.setDefaultValues("1");
        dilateM_DD.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(dilateM_DD);


////
        OperationAttributes openM_AA = new OperationAttributes();
        openM_AA.setMinValue(3);
        openM_AA.setMaxValue(5);
        openM_AA.setDefaultValues("3");
        openM_AA.setOperation(open_a);
        openM_AA.setAttribute(size);
        openM_AA.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(openM_AA);

        OperationAttributes openM_BB = new OperationAttributes();
        openM_BB.setDefaultValues("rectangle");
        openM_BB.setOperation(open_a);
        openM_BB.setAttribute(shape);
        openM_BB.setAttributeType(AttributeType.SELECT);
        openM_BB.setOptions(shapes);
        operationAttributes.add(openM_BB);

        OperationAttributes openM_CC = new OperationAttributes();
        openM_CC.setOperation(open_a);
        openM_CC.setAttribute(mask);
        openM_CC.setAttributeType(AttributeType.IMAGE);
        operationAttributes.add(openM_CC);

        OperationAttributes openM_DD = new OperationAttributes();
        openM_DD.setOperation(open_a);
        openM_DD.setAttribute(iteration);
        openM_DD.setMinValue(1);
        openM_DD.setMaxValue(15);
        openM_DD.setDefaultValues("1");
        openM_DD.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(openM_DD);


////
        OperationAttributes closeM_AA = new OperationAttributes();
        closeM_AA.setMinValue(3);
        closeM_AA.setMaxValue(5);
        closeM_AA.setDefaultValues("3");
        closeM_AA.setOperation(close_a);
        closeM_AA.setAttribute(size);
        closeM_AA.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(closeM_AA);

        OperationAttributes closeM_BB = new OperationAttributes();
        closeM_BB.setDefaultValues("rectangle");
        closeM_BB.setOperation(close_a);
        closeM_BB.setAttribute(shape);
        closeM_BB.setAttributeType(AttributeType.SELECT);
        closeM_BB.setOptions(shapes);
        operationAttributes.add(closeM_BB);

        OperationAttributes closeM_CC = new OperationAttributes();
        closeM_CC.setOperation(close_a);
        closeM_CC.setAttribute(mask);
        closeM_CC.setAttributeType(AttributeType.IMAGE);
        operationAttributes.add(closeM_CC);

        OperationAttributes closeM_DD = new OperationAttributes();
        closeM_DD.setOperation(close_a);
        closeM_DD.setAttribute(iteration);
        closeM_DD.setMinValue(1);
        closeM_DD.setMaxValue(15);
        closeM_DD.setDefaultValues("1");
        closeM_DD.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(closeM_DD);


////
        OperationAttributes topHatM_A = new OperationAttributes();
        topHatM_A.setMinValue(3);
        topHatM_A.setMaxValue(5);
        topHatM_A.setDefaultValues("3");
        topHatM_A.setOperation(topHat);
        topHatM_A.setAttribute(size);
        topHatM_A.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(topHatM_A);

        OperationAttributes topHatM_B = new OperationAttributes();
        topHatM_B.setDefaultValues("rectangle");
        topHatM_B.setOperation(topHat);
        topHatM_B.setAttribute(shape);
        topHatM_B.setAttributeType(AttributeType.SELECT);
        topHatM_B.setOptions(shapes);
        operationAttributes.add(topHatM_B);

        OperationAttributes topHatM_C = new OperationAttributes();
        topHatM_C.setOperation(topHat);
        topHatM_C.setAttribute(mask);
        topHatM_C.setAttributeType(AttributeType.IMAGE);
        operationAttributes.add(topHatM_C);

        OperationAttributes topHatM_D = new OperationAttributes();
        topHatM_D.setOperation(topHat);
        topHatM_D.setAttribute(iteration);
        topHatM_D.setMinValue(1);
        topHatM_D.setMaxValue(15);
        topHatM_D.setDefaultValues("1");
        topHatM_D.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(topHatM_D);


/////
        OperationAttributes resizeOp = new OperationAttributes();
        resizeOp.setDefaultValues("100");
        resizeOp.setMinValue(1);
        resizeOp.setMaxValue(1000);
        resizeOp.setOperation(resizing);
        resizeOp.setAttribute(size);
        resizeOp.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(resizeOp);

        /*OperationAttributes rotateOp = new OperationAttributes();
        rotateOp.setDefaultValues("100");
        rotateOp.setMinValue(1);
        rotateOp.setMaxValue(1000);
        rotateOp.setOperation(rotating);
        rotateOp.setAttribute(size);
        rotateOp.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(rotateOp);*/

        OperationAttributes rotateOp_A = new OperationAttributes();
        rotateOp_A.setDefaultValues("0");
        rotateOp_A.setMinValue(-360);
        rotateOp_A.setMaxValue(360);
        rotateOp_A.setOperation(rotating);
        rotateOp_A.setAttribute(angle);
        rotateOp_A.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(rotateOp_A);


        OperationAttributes HTC_A = new OperationAttributes();
        HTC_A.setDefaultValues("15");
        HTC_A.setMinValue(0);
        HTC_A.setMaxValue(1000);
        HTC_A.setOperation(HTC);
        HTC_A.setAttribute(minDist);
        HTC_A.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(HTC_A);

        OperationAttributes HTC_B = new OperationAttributes();
        Map<String, String> HTCmethods = new HashMap<>();
        //HTCmethods.put("HOUGH_STANDARD", "STANDARD ");
        // HTCmethods.put("HOUGH_PROBABILISTIC", "PROBABILISTIC ");
        //HTCmethods.put("HOUGH_MULTI_SCALE", "MULTI SCALE ");
        HTCmethods.put("HOUGH_GRADIENT", "GRADIENT ");

        HTC_B.setDefaultValues("HOUGH_GRADIENT");

        HTC_B.setOperation(HTC);
        HTC_B.setAttribute(method);
        HTC_B.setAttributeType(AttributeType.SELECT);
        HTC_B.setOptions(HTCmethods);
        operationAttributes.add(HTC_B);

        OperationAttributes HTC_C = new OperationAttributes();
        HTC_C.setDefaultValues("0.1");
        HTC_C.setMinValue(0);
        HTC_C.setMaxValue(100);
        HTC_C.setOperation(HTC);
        HTC_C.setAttribute(dp);
        HTC_C.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(HTC_C);

        OperationAttributes HTL_A = new OperationAttributes();
        HTL_A.setDefaultValues("50");
        HTL_A.setMinValue(0);
        HTL_A.setMaxValue(256);
        HTL_A.setOperation(HTL);
        HTL_A.setAttribute(threshold);
        HTL_A.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(HTL_A);

        OperationAttributes HTL_B = new OperationAttributes();
        Map<String, String> HTLmethods = new HashMap<>();
        //HTCmethods.put("HOUGH_STANDARD", "STANDARD ");
        // HTCmethods.put("HOUGH_PROBABILISTIC", "PROBABILISTIC ");
        //HTCmethods.put("HOUGH_MULTI_SCALE", "MULTI SCALE ");
        HTLmethods.put("HOUGH_GRADIENT", "GRADIENT ");

        HTL_B.setDefaultValues("HOUGH_GRADIENT");

        HTL_B.setOperation(HTL);
        HTL_B.setAttribute(method);
        HTL_B.setAttributeType(AttributeType.SELECT);
        HTL_B.setOptions(HTLmethods);
        operationAttributes.add(HTL_B);

        OperationAttributes HTL_C = new OperationAttributes();
        HTL_C.setDefaultValues("40");
        HTL_C.setMinValue(0);
        HTL_C.setMaxValue(1000);
        HTL_C.setOperation(HTL);
        HTL_C.setAttribute(minLen);
        HTL_C.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(HTL_C);

        OperationAttributes HTL_D = new OperationAttributes();
        HTL_D.setDefaultValues("10");
        HTL_D.setMinValue(0);
        HTL_D.setMaxValue(1000);
        HTL_D.setOperation(HTL);
        HTL_D.setAttribute(maxLineGap);
        HTL_D.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(HTL_D);

        OperationAttributes rotateOp_B = new OperationAttributes();
        Map<String, String> interpolations = new HashMap<>();
        interpolations.put("INTER_AREA", "INTER_AREA");
        interpolations.put("INTER_BITS", "INTER_BITS");
        interpolations.put("INTER_BITS2", "INTER_BITS2");
        interpolations.put("INTER_CUBIC", "INTER_CUBIC");
        interpolations.put("INTER_LANCZOS4", "INTER_LANCZOS4");
        interpolations.put("INTER_LINEAR", "INTER_LINEAR");
        interpolations.put("INTER_MAX", "INTER_MAX");
        interpolations.put("INTER_NEAREST", "INTER_NEAREST");
        rotateOp_B.setDefaultValues("INTER_NEAREST");
        rotateOp_B.setMinValue(-360);
        rotateOp_B.setMaxValue(360);
        rotateOp_B.setOperation(rotating);
        rotateOp_B.setAttribute(interpolation);
        rotateOp_B.setAttributeType(AttributeType.SELECT);
        rotateOp_B.setOptions(interpolations);
        operationAttributes.add(rotateOp_B);

        OperationAttributes tmplM_A = new OperationAttributes();
        Map<String, String> tmplMethods = new HashMap<>();
        tmplMethods.put(TemplateEnum.TM_CCOEFF.getTemplateName(), TemplateEnum.TM_CCOEFF.getTemplateName());
        tmplMethods.put(TemplateEnum.TM_CCOEFF_NORMED.getTemplateName(), TemplateEnum.TM_CCORR_NORMED.getTemplateName());
        tmplMethods.put(TemplateEnum.TM_CCORR.getTemplateName(), TemplateEnum.TM_CCORR.getTemplateName());
        tmplMethods.put(TemplateEnum.TM_CCORR_NORMED.getTemplateName(), TemplateEnum.TM_CCORR_NORMED.getTemplateName());
        tmplMethods.put(TemplateEnum.TM_SQDIFF.getTemplateName(), TemplateEnum.TM_SQDIFF.getTemplateName());
        tmplMethods.put(TemplateEnum.TM_SQDIFF_NORMED.getTemplateName(), TemplateEnum.TM_SQDIFF_NORMED.getTemplateName());
        tmplM_A.setDefaultValues(TemplateEnum.TM_CCOEFF.getTemplateName());
        tmplM_A.setOperation(tempMatching);
        tmplM_A.setAttribute(method);
        tmplM_A.setAttributeType(AttributeType.SELECT);
        tmplM_A.setOptions(tmplMethods);
        operationAttributes.add(tmplM_A);

        OperationAttributes tmplM_B = new OperationAttributes();
        tmplM_B.setOperation(tempMatching);
        tmplM_B.setAttribute(mask);
        tmplM_B.setAttributeType(AttributeType.IMAGE);
        operationAttributes.add(tmplM_B);

        OperationAttributes tmplM_C = new OperationAttributes();
        Map<String, String> tmplMethods_A = new HashMap<>();
        tmplMethods_A.put("Red", "Red");
        tmplMethods_A.put("Green", "Green");
        tmplMethods_A.put("Blue", "Blue");
        tmplMethods_A.put("Gray", "Gray");
        tmplMethods_A.put("Y", "Y");
        tmplMethods_A.put("Cb", "Cb");
        tmplMethods_A.put("Cr", "Cr");
        tmplM_C.setDefaultValues("Gray");
        tmplM_C.setOperation(tempMatching);
        tmplM_C.setAttribute(channel);
        tmplM_C.setAttributeType(AttributeType.SELECT);
        tmplM_C.setOptions(tmplMethods_A);
        operationAttributes.add(tmplM_C);

        OperationAttributes dstTrans_A = new OperationAttributes();
        Map<String, String> distTransMethods_A = new HashMap<>();
        distTransMethods_A.put("DIST_L1", "DIST_L1");
        distTransMethods_A.put("DIST_L2", "DIST_L2");
        distTransMethods_A.put("DIST_C", "DIST_C");
        dstTrans_A.setDefaultValues("DIST_L1");
        dstTrans_A.setOperation(distTransfrom);
        dstTrans_A.setAttribute(metrics);
        dstTrans_A.setAttributeType(AttributeType.SELECT);
        dstTrans_A.setOptions(distTransMethods_A);
        operationAttributes.add(dstTrans_A);


        OperationAttributes dstTrans_B = new OperationAttributes();
        Map<String, String> distTransMethods_B = new HashMap<>();
        distTransMethods_B.put("DIST_MASK_3", "3×3");
        distTransMethods_B.put("DIST_MASK_5", "5×5");
        dstTrans_B.setDefaultValues("DIST_MASK_3");
        dstTrans_B.setOperation(distTransfrom);
        dstTrans_B.setAttribute(mask);
        dstTrans_B.setAttributeType(AttributeType.SELECT);
        dstTrans_B.setOptions(distTransMethods_B);
        operationAttributes.add(dstTrans_B);


        OperationAttributes grapCat_A = new OperationAttributes();
        grapCat_A.setOperation(gCut);
        grapCat_A.setDefaultValues("0");
        grapCat_A.setMinValue(0);
        grapCat_A.setAttribute(startX);
        grapCat_A.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(grapCat_A);

        OperationAttributes grapCat_B = new OperationAttributes();
        grapCat_B.setOperation(gCut);
        grapCat_B.setDefaultValues("0");
        grapCat_B.setMinValue(0);
        grapCat_B.setAttribute(startY);
        grapCat_B.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(grapCat_B);

        OperationAttributes grapCat_C = new OperationAttributes();
        grapCat_C.setOperation(gCut);
        grapCat_C.setDefaultValues("0");
        grapCat_C.setMinValue(0);
        grapCat_C.setAttribute(h);
        grapCat_C.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(grapCat_C);

        OperationAttributes grapCat_D = new OperationAttributes();
        grapCat_D.setOperation(gCut);
        grapCat_D.setDefaultValues("0");
        grapCat_D.setMinValue(0);
        grapCat_D.setAttribute(w);
        grapCat_D.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(grapCat_D);

        OperationAttributes grapCatM_A = new OperationAttributes();
        grapCatM_A.setOperation(gCutM);
        grapCatM_A.setAttribute(mask);
        grapCatM_A.setAttributeType(AttributeType.IMAGE);
        operationAttributes.add(grapCatM_A);

        /*
        * Attribute aIteration = new Attribute("A Iterace");
        Attribute bIteration = new Attribute("B Iterace");
        Attribute aThresh = new Attribute("A práh");
        Attribute bThresh = new Attribute("B práh");
        Attribute aMoType = new Attribute("A. Typ moph. t. ");
        Attribute bMoType = new Attribute("B. Typ moph. t. ");
        Attribute aThresType = new Attribute("A Thresh type");
        Attribute bThresType = new Attribute("B Thresh type");
        * */

        OperationAttributes waterShed_A = new OperationAttributes();
        waterShed_A.setOperation(watershed);
        waterShed_A.setAttribute(aThresh);
        waterShed_A.setMinValue(0);
        waterShed_A.setMaxValue(255);
        waterShed_A.setDefaultValues("128");
        waterShed_A.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(waterShed_A);

        OperationAttributes waterShed_B = new OperationAttributes();
        waterShed_B.setOperation(watershed);
        waterShed_B.setAttribute(bThresh);
        waterShed_B.setMinValue(0);
        waterShed_B.setMaxValue(255);
        waterShed_B.setDefaultValues("20");
        waterShed_B.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(waterShed_B);


        OperationAttributes waterShed_D = new OperationAttributes();
        waterShed_D.setOperation(watershed);
        waterShed_D.setAttribute(bIteration);
        waterShed_D.setMinValue(0);
        waterShed_D.setMaxValue(15);
        waterShed_D.setDefaultValues("3");
        waterShed_D.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(waterShed_D);

        OperationAttributes waterShed_E = new OperationAttributes();
        waterShed_E.setOperation(watershed);
        waterShed_E.setMinValue(0);
        waterShed_E.setMaxValue(10);
        waterShed_E.setDefaultValues("3");
        waterShed_E.setAttribute(size);
        waterShed_E.setAttributeType(AttributeType.NUMBER);
        operationAttributes.add(waterShed_E);


        operationDAO.save(operationAttributes);


        AllowStep allowStep_org = new AllowStep();
        allowStep_org.setOperation(originalOperation);
        allowStep_org.setAllowoperationId(null);
        allowSteps.add(allowStep_org);

        AllowStep allowStep_R = new AllowStep();
        allowStep_R.setOperation(redOperation);
        allowStep_R.setAllowoperationId(originalOperation);
        allowSteps.add(allowStep_R);

        AllowStep allowStep_G = new AllowStep();
        allowStep_G.setOperation(greenOperation);
        allowStep_G.setAllowoperationId(originalOperation);
        allowSteps.add(allowStep_G);

        AllowStep allowStep_B = new AllowStep();
        allowStep_B.setOperation(blueOperation);
        allowStep_B.setAllowoperationId(originalOperation);
        allowSteps.add(allowStep_B);

        AllowStep allowStep_GRAY = new AllowStep();
        allowStep_GRAY.setOperation(grayOperation);
        allowStep_GRAY.setAllowoperationId(originalOperation);
        allowSteps.add(allowStep_GRAY);

        AllowStep allowStep_Y = new AllowStep();
        allowStep_Y.setOperation(yOperation);
        allowStep_Y.setAllowoperationId(originalOperation);
        allowSteps.add(allowStep_Y);

        AllowStep allowStep_CB = new AllowStep();
        allowStep_CB.setOperation(cbOperation);
        allowStep_CB.setAllowoperationId(originalOperation);
        allowSteps.add(allowStep_CB);

        AllowStep allowStep_CR = new AllowStep();
        allowStep_CR.setOperation(crOperation);
        allowStep_CR.setAllowoperationId(originalOperation);
        allowSteps.add(allowStep_CR);

        AllowStep allowStep_H = new AllowStep();
        allowStep_H.setOperation(hOperation);
        allowStep_H.setAllowoperationId(originalOperation);
        allowSteps.add(allowStep_H);

        AllowStep allowStep_sobel_a = new AllowStep();
        allowStep_sobel_a.setOperation(sobel);
        allowStep_sobel_a.setAllowoperationId(grayOperation);
        allowSteps.add(allowStep_sobel_a);

        AllowStep allowStep_sobel_b = new AllowStep();
        allowStep_sobel_b.setOperation(sobel);
        allowStep_sobel_b.setAllowoperationId(redOperation);
        allowSteps.add(allowStep_sobel_b);

        AllowStep allowStep_sobel_c = new AllowStep();
        allowStep_sobel_c.setOperation(sobel);
        allowStep_sobel_c.setAllowoperationId(greenOperation);
        allowSteps.add(allowStep_sobel_c);

        AllowStep allowStep_sobel_d = new AllowStep();
        allowStep_sobel_d.setOperation(sobel);
        allowStep_sobel_d.setAllowoperationId(blueOperation);
        allowSteps.add(allowStep_sobel_d);

        AllowStep allowStep_sobel_e = new AllowStep();
        allowStep_sobel_e.setOperation(sobel);
        allowStep_sobel_e.setAllowoperationId(yOperation);
        allowSteps.add(allowStep_sobel_e);

        AllowStep allowStep_sobel_f = new AllowStep();
        allowStep_sobel_f.setOperation(sobel);
        allowStep_sobel_f.setAllowoperationId(cbOperation);
        allowSteps.add(allowStep_sobel_f);

        AllowStep allowStep_sobel_g = new AllowStep();
        allowStep_sobel_g.setOperation(sobel);
        allowStep_sobel_g.setAllowoperationId(crOperation);
        allowSteps.add(allowStep_sobel_g);
        ////////////////////////////////////
        AllowStep allowStep_laplace_a = new AllowStep();
        allowStep_laplace_a.setOperation(laplace);
        allowStep_laplace_a.setAllowoperationId(grayOperation);
        allowSteps.add(allowStep_laplace_a);

        AllowStep allowStep_laplace_b = new AllowStep();
        allowStep_laplace_b.setOperation(laplace);
        allowStep_laplace_b.setAllowoperationId(redOperation);
        allowSteps.add(allowStep_laplace_b);

        AllowStep allowStep_laplace_c = new AllowStep();
        allowStep_laplace_c.setOperation(laplace);
        allowStep_laplace_c.setAllowoperationId(greenOperation);
        allowSteps.add(allowStep_laplace_c);

        AllowStep allowStep_laplace_d = new AllowStep();
        allowStep_laplace_d.setOperation(laplace);
        allowStep_laplace_d.setAllowoperationId(blueOperation);
        allowSteps.add(allowStep_laplace_d);

        AllowStep allowStep_laplace_e = new AllowStep();
        allowStep_laplace_e.setOperation(laplace);
        allowStep_laplace_e.setAllowoperationId(yOperation);
        allowSteps.add(allowStep_laplace_e);

        AllowStep allowStep_laplace_f = new AllowStep();
        allowStep_laplace_f.setOperation(laplace);
        allowStep_laplace_f.setAllowoperationId(cbOperation);
        allowSteps.add(allowStep_laplace_f);

        AllowStep allowStep_laplace_g = new AllowStep();
        allowStep_laplace_g.setOperation(laplace);
        allowStep_laplace_g.setAllowoperationId(crOperation);
        allowSteps.add(allowStep_laplace_g);


        ////////////////////////////////////

        ////////////////////////////////////
        AllowStep allowStep_canny_a = new AllowStep();
        allowStep_canny_a.setOperation(canny);
        allowStep_canny_a.setAllowoperationId(grayOperation);
        allowSteps.add(allowStep_canny_a);

        AllowStep allowStep_canny_b = new AllowStep();
        allowStep_canny_b.setOperation(canny);
        allowStep_canny_b.setAllowoperationId(redOperation);
        allowSteps.add(allowStep_canny_b);

        AllowStep allowStep_canny_c = new AllowStep();
        allowStep_canny_c.setOperation(canny);
        allowStep_canny_c.setAllowoperationId(greenOperation);
        allowSteps.add(allowStep_canny_c);

        AllowStep allowStep_canny_d = new AllowStep();
        allowStep_canny_d.setOperation(canny);
        allowStep_canny_d.setAllowoperationId(blueOperation);
        allowSteps.add(allowStep_canny_d);

        AllowStep allowStep_canny_e = new AllowStep();
        allowStep_canny_e.setOperation(canny);
        allowStep_canny_e.setAllowoperationId(yOperation);
        allowSteps.add(allowStep_canny_e);

        AllowStep allowStep_canny_f = new AllowStep();
        allowStep_canny_f.setOperation(canny);
        allowStep_canny_f.setAllowoperationId(cbOperation);
        allowSteps.add(allowStep_canny_f);

        AllowStep allowStep_canny_g = new AllowStep();
        allowStep_canny_g.setOperation(canny);
        allowStep_canny_g.setAllowoperationId(crOperation);
        allowSteps.add(allowStep_canny_g);


        ////////////////////////////////////

        AllowStep allowStep_threshold_b = new AllowStep();
        allowStep_threshold_b.setOperation(THRESH_BINARY);
        allowStep_threshold_b.setAllowoperationId(redOperation);
        allowSteps.add(allowStep_threshold_b);

        AllowStep allowStep_threshold_c = new AllowStep();
        allowStep_threshold_c.setOperation(THRESH_BINARY);
        allowStep_threshold_c.setAllowoperationId(greenOperation);
        allowSteps.add(allowStep_threshold_c);

        AllowStep allowStep_threshold_d = new AllowStep();
        allowStep_threshold_d.setOperation(THRESH_BINARY);
        allowStep_threshold_d.setAllowoperationId(blueOperation);
        allowSteps.add(allowStep_threshold_d);

        AllowStep allowStep_threshold_e = new AllowStep();
        allowStep_threshold_e.setOperation(THRESH_BINARY);
        allowStep_threshold_e.setAllowoperationId(yOperation);
        allowSteps.add(allowStep_threshold_e);

        AllowStep allowStep_threshold_f = new AllowStep();
        allowStep_threshold_f.setOperation(THRESH_BINARY);
        allowStep_threshold_f.setAllowoperationId(cbOperation);
        allowSteps.add(allowStep_threshold_f);
        AllowStep allowStep_threshold_a = new AllowStep();

        allowStep_threshold_a.setOperation(THRESH_BINARY);
        allowStep_threshold_a.setAllowoperationId(crOperation);
        allowSteps.add(allowStep_threshold_a);

        AllowStep allowStep_threshold_g = new AllowStep();
        allowStep_threshold_g.setOperation(THRESH_BINARY);
        allowStep_threshold_g.setAllowoperationId(grayOperation);
        allowSteps.add(allowStep_threshold_g);

        AllowStep allowStep_threshold_h = new AllowStep();
        allowStep_threshold_h.setOperation(THRESH_BINARY);
        allowStep_threshold_h.setAllowoperationId(distTransfrom);
        allowSteps.add(allowStep_threshold_h);


        //
        AllowStep allowStep_threshold_aa = new AllowStep();
        allowStep_threshold_aa.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_aa.setAllowoperationId(crOperation);
        allowSteps.add(allowStep_threshold_aa);

        AllowStep allowStep_threshold_bb = new AllowStep();
        allowStep_threshold_bb.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_bb.setAllowoperationId(redOperation);
        allowSteps.add(allowStep_threshold_bb);

        AllowStep allowStep_threshold_cc = new AllowStep();
        allowStep_threshold_cc.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_cc.setAllowoperationId(greenOperation);
        allowSteps.add(allowStep_threshold_cc);

        AllowStep allowStep_threshold_dd = new AllowStep();
        allowStep_threshold_dd.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_dd.setAllowoperationId(blueOperation);
        allowSteps.add(allowStep_threshold_dd);

        AllowStep allowStep_threshold_ee = new AllowStep();
        allowStep_threshold_ee.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_ee.setAllowoperationId(yOperation);
        allowSteps.add(allowStep_threshold_ee);

        AllowStep allowStep_threshold_ff = new AllowStep();
        allowStep_threshold_ff.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_ff.setAllowoperationId(cbOperation);
        allowSteps.add(allowStep_threshold_ff);

        AllowStep allowStep_threshold_gg = new AllowStep();
        allowStep_threshold_gg.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_gg.setAllowoperationId(grayOperation);
        allowSteps.add(allowStep_threshold_gg);

        AllowStep allowStep_threshold_hh = new AllowStep();
        allowStep_threshold_hh.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_hh.setAllowoperationId(distTransfrom);
        allowSteps.add(allowStep_threshold_hh);
        //
        AllowStep allowStep_threshold_aaa = new AllowStep();
        allowStep_threshold_aaa.setOperation(THRESH_TOZERO);
        allowStep_threshold_aaa.setAllowoperationId(grayOperation);
        allowSteps.add(allowStep_threshold_aaa);

        AllowStep allowStep_threshold_bbb = new AllowStep();
        allowStep_threshold_bbb.setOperation(THRESH_TOZERO);
        allowStep_threshold_bbb.setAllowoperationId(redOperation);
        allowSteps.add(allowStep_threshold_bbb);

        AllowStep allowStep_threshold_ccc = new AllowStep();
        allowStep_threshold_ccc.setOperation(THRESH_TOZERO);
        allowStep_threshold_ccc.setAllowoperationId(greenOperation);
        allowSteps.add(allowStep_threshold_ccc);

        AllowStep allowStep_threshold_ddd = new AllowStep();
        allowStep_threshold_ddd.setOperation(THRESH_TOZERO);
        allowStep_threshold_ddd.setAllowoperationId(blueOperation);
        allowSteps.add(allowStep_threshold_ddd);

        AllowStep allowStep_threshold_eee = new AllowStep();
        allowStep_threshold_eee.setOperation(THRESH_TOZERO);
        allowStep_threshold_eee.setAllowoperationId(yOperation);
        allowSteps.add(allowStep_threshold_eee);

        AllowStep allowStep_threshold_fff = new AllowStep();
        allowStep_threshold_fff.setOperation(THRESH_TOZERO);
        allowStep_threshold_fff.setAllowoperationId(cbOperation);
        allowSteps.add(allowStep_threshold_fff);

        AllowStep allowStep_threshold_ggg = new AllowStep();
        allowStep_threshold_ggg.setOperation(THRESH_TOZERO);
        allowStep_threshold_ggg.setAllowoperationId(crOperation);
        allowSteps.add(allowStep_threshold_ggg);

        AllowStep allowStep_threshold_hhh = new AllowStep();
        allowStep_threshold_hhh.setOperation(THRESH_TOZERO);
        allowStep_threshold_hhh.setAllowoperationId(distTransfrom);
        allowSteps.add(allowStep_threshold_hhh);
////////////
        AllowStep allowStep_threshold_aaaa = new AllowStep();
        allowStep_threshold_aaaa.setOperation(THRESH_TRUNC);
        allowStep_threshold_aaaa.setAllowoperationId(grayOperation);
        allowSteps.add(allowStep_threshold_aaaa);

        AllowStep allowStep_threshold_bbbb = new AllowStep();
        allowStep_threshold_bbbb.setOperation(THRESH_TRUNC);
        allowStep_threshold_bbbb.setAllowoperationId(redOperation);
        allowSteps.add(allowStep_threshold_bbbb);

        AllowStep allowStep_threshold_cccc = new AllowStep();
        allowStep_threshold_cccc.setOperation(THRESH_TRUNC);
        allowStep_threshold_cccc.setAllowoperationId(greenOperation);
        allowSteps.add(allowStep_threshold_cccc);

        AllowStep allowStep_threshold_dddd = new AllowStep();
        allowStep_threshold_dddd.setOperation(THRESH_TRUNC);
        allowStep_threshold_dddd.setAllowoperationId(blueOperation);
        allowSteps.add(allowStep_threshold_dddd);

        AllowStep allowStep_threshold_eeee = new AllowStep();
        allowStep_threshold_eeee.setOperation(THRESH_TRUNC);
        allowStep_threshold_eeee.setAllowoperationId(yOperation);
        allowSteps.add(allowStep_threshold_eeee);

        AllowStep allowStep_threshold_ffff = new AllowStep();
        allowStep_threshold_ffff.setOperation(THRESH_TRUNC);
        allowStep_threshold_ffff.setAllowoperationId(cbOperation);
        allowSteps.add(allowStep_threshold_ffff);

        AllowStep allowStep_threshold_gggg = new AllowStep();
        allowStep_threshold_gggg.setOperation(THRESH_TRUNC);
        allowStep_threshold_gggg.setAllowoperationId(crOperation);
        allowSteps.add(allowStep_threshold_gggg);

        AllowStep allowStep_threshold_hhhh = new AllowStep();
        allowStep_threshold_hhhh.setOperation(THRESH_TRUNC);
        allowStep_threshold_hhhh.setAllowoperationId(distTransfrom);
        allowSteps.add(allowStep_threshold_hhhh);
        //////////////////////////////////
        AllowStep allowStep_coloring_a = new AllowStep();
        allowStep_coloring_a.setOperation(colorSeg);
        allowStep_coloring_a.setAllowoperationId(THRESH_BINARY);
        allowSteps.add(allowStep_coloring_a);

        AllowStep allowStep_coloring_b = new AllowStep();
        allowStep_coloring_b.setOperation(colorSeg);
        allowStep_coloring_b.setAllowoperationId(THRESH_BINARY_INV);
        allowSteps.add(allowStep_coloring_b);

        AllowStep allowStep_coloring_c = new AllowStep();
        allowStep_coloring_c.setOperation(colorSeg);
        allowStep_coloring_c.setAllowoperationId(THRESH_TOZERO);
        allowSteps.add(allowStep_coloring_c);

        AllowStep allowStep_coloring_d = new AllowStep();
        allowStep_coloring_d.setOperation(colorSeg);
        allowStep_coloring_d.setAllowoperationId(THRESH_TRUNC);
        allowSteps.add(allowStep_coloring_d);

        AllowStep allowStep_coloring_e = new AllowStep();
        allowStep_coloring_e.setOperation(colorSeg);
        allowStep_coloring_e.setAllowoperationId(erode);
        allowSteps.add(allowStep_coloring_e);

        AllowStep allowStep_coloring_f = new AllowStep();
        allowStep_coloring_f.setOperation(colorSeg);
        allowStep_coloring_f.setAllowoperationId(dilate);
        allowSteps.add(allowStep_coloring_f);

        AllowStep allowStep_coloring_g = new AllowStep();
        allowStep_coloring_g.setOperation(colorSeg);
        allowStep_coloring_g.setAllowoperationId(open);
        allowSteps.add(allowStep_coloring_g);

        AllowStep allowStep_coloring_h = new AllowStep();
        allowStep_coloring_h.setOperation(colorSeg);
        allowStep_coloring_h.setAllowoperationId(close);
        allowSteps.add(allowStep_coloring_h);

        //DENOISER avg
        AllowStep noiseReduceAVG_a = new AllowStep();
        noiseReduceAVG_a.setOperation(average);
        noiseReduceAVG_a.setAllowoperationId(redOperation);
        allowSteps.add(noiseReduceAVG_a);

        AllowStep noiseReduceAVG_b = new AllowStep();
        noiseReduceAVG_b.setOperation(average);
        noiseReduceAVG_b.setAllowoperationId(greenOperation);
        allowSteps.add(noiseReduceAVG_b);

        AllowStep noiseReduceAVG_c = new AllowStep();
        noiseReduceAVG_c.setOperation(average);
        noiseReduceAVG_c.setAllowoperationId(blueOperation);
        allowSteps.add(noiseReduceAVG_c);

        AllowStep noiseReduceAVG_d = new AllowStep();
        noiseReduceAVG_d.setOperation(average);
        noiseReduceAVG_d.setAllowoperationId(yOperation);
        allowSteps.add(noiseReduceAVG_d);

        AllowStep noiseReduceAVG_e = new AllowStep();
        noiseReduceAVG_e.setOperation(average);
        noiseReduceAVG_e.setAllowoperationId(cbOperation);
        allowSteps.add(noiseReduceAVG_e);

        AllowStep noiseReduceAVG_f = new AllowStep();
        noiseReduceAVG_f.setOperation(average);
        noiseReduceAVG_f.setAllowoperationId(crOperation);
        allowSteps.add(noiseReduceAVG_f);

        AllowStep noiseReduceAVG_g = new AllowStep();
        noiseReduceAVG_g.setOperation(average);
        noiseReduceAVG_g.setAllowoperationId(originalOperation);
        allowSteps.add(noiseReduceAVG_g);

        AllowStep noiseReduceAVG_h = new AllowStep();
        noiseReduceAVG_h.setOperation(average);
        noiseReduceAVG_h.setAllowoperationId(grayOperation);
        allowSteps.add(noiseReduceAVG_h);

        //noise reducer median

        AllowStep noiseReduceMedian_a = new AllowStep();
        noiseReduceMedian_a.setOperation(median);
        noiseReduceMedian_a.setAllowoperationId(redOperation);
        allowSteps.add(noiseReduceMedian_a);

        AllowStep noiseReduceMedian_b = new AllowStep();
        noiseReduceMedian_b.setOperation(median);
        noiseReduceMedian_b.setAllowoperationId(greenOperation);
        allowSteps.add(noiseReduceMedian_b);

        AllowStep noiseReduceMedian_c = new AllowStep();
        noiseReduceMedian_c.setOperation(median);
        noiseReduceMedian_c.setAllowoperationId(blueOperation);
        allowSteps.add(noiseReduceMedian_c);

        AllowStep noiseReduceMedian_d = new AllowStep();
        noiseReduceMedian_d.setOperation(median);
        noiseReduceMedian_d.setAllowoperationId(yOperation);
        allowSteps.add(noiseReduceMedian_d);

        AllowStep noiseReduceMedian_e = new AllowStep();
        noiseReduceMedian_e.setOperation(median);
        noiseReduceMedian_e.setAllowoperationId(cbOperation);
        allowSteps.add(noiseReduceMedian_e);

        AllowStep noiseReduceMedian_f = new AllowStep();
        noiseReduceMedian_f.setOperation(median);
        noiseReduceMedian_f.setAllowoperationId(crOperation);
        allowSteps.add(noiseReduceMedian_f);

        AllowStep noiseReduceMedian_g = new AllowStep();
        noiseReduceMedian_g.setOperation(median);
        noiseReduceMedian_g.setAllowoperationId(originalOperation);
        allowSteps.add(noiseReduceMedian_g);

        AllowStep noiseReduceMedian_h = new AllowStep();
        noiseReduceMedian_h.setOperation(median);
        noiseReduceMedian_h.setAllowoperationId(grayOperation);
        allowSteps.add(noiseReduceMedian_h);

        //rotating mask reducer median

        AllowStep noiseReduceRotMask_a = new AllowStep();
        noiseReduceRotMask_a.setOperation(median);
        noiseReduceRotMask_a.setAllowoperationId(redOperation);
        allowSteps.add(noiseReduceRotMask_a);

        AllowStep noiseReduceRotMask_b = new AllowStep();
        noiseReduceRotMask_b.setOperation(median);
        noiseReduceRotMask_b.setAllowoperationId(greenOperation);
        allowSteps.add(noiseReduceRotMask_b);

        AllowStep noiseReduceRotMask_c = new AllowStep();
        noiseReduceRotMask_c.setOperation(median);
        noiseReduceRotMask_c.setAllowoperationId(blueOperation);
        allowSteps.add(noiseReduceRotMask_c);

        AllowStep noiseReduceRotMask_d = new AllowStep();
        noiseReduceRotMask_d.setOperation(median);
        noiseReduceRotMask_d.setAllowoperationId(yOperation);
        allowSteps.add(noiseReduceRotMask_d);

        AllowStep noiseReduceRotMask_e = new AllowStep();
        noiseReduceRotMask_e.setOperation(median);
        noiseReduceRotMask_e.setAllowoperationId(cbOperation);
        allowSteps.add(noiseReduceRotMask_e);

        AllowStep noiseReduceRotMask_f = new AllowStep();
        noiseReduceRotMask_f.setOperation(median);
        noiseReduceRotMask_f.setAllowoperationId(crOperation);
        allowSteps.add(noiseReduceRotMask_f);

        AllowStep noiseReduceRotMask_g = new AllowStep();
        noiseReduceRotMask_g.setOperation(median);
        noiseReduceRotMask_g.setAllowoperationId(originalOperation);
        allowSteps.add(noiseReduceRotMask_g);

        AllowStep noiseReduceRotMask_h = new AllowStep();
        noiseReduceRotMask_h.setOperation(median);
        noiseReduceRotMask_h.setAllowoperationId(grayOperation);
        allowSteps.add(noiseReduceRotMask_h);

        //tophat

        AllowStep morphologyTophat_a = new AllowStep();
        morphologyTophat_a.setOperation(topHat);
        morphologyTophat_a.setAllowoperationId(redOperation);
        allowSteps.add(morphologyTophat_a);

        AllowStep morphologyTophat_b = new AllowStep();
        morphologyTophat_b.setOperation(topHat);
        morphologyTophat_b.setAllowoperationId(greenOperation);
        allowSteps.add(noiseReduceRotMask_b);

        AllowStep morphologyTophat_c = new AllowStep();
        morphologyTophat_c.setOperation(topHat);
        morphologyTophat_c.setAllowoperationId(blueOperation);
        allowSteps.add(noiseReduceRotMask_c);

        AllowStep morphologyTophat_d = new AllowStep();
        morphologyTophat_d.setOperation(topHat);
        morphologyTophat_d.setAllowoperationId(yOperation);
        allowSteps.add(morphologyTophat_d);

        AllowStep morphologyTophat_e = new AllowStep();
        morphologyTophat_e.setOperation(topHat);
        morphologyTophat_e.setAllowoperationId(cbOperation);
        allowSteps.add(morphologyTophat_e);

        AllowStep morphologyTophat_f = new AllowStep();
        morphologyTophat_f.setOperation(topHat);
        morphologyTophat_f.setAllowoperationId(crOperation);
        allowSteps.add(morphologyTophat_f);

        AllowStep morphologyTophat_g = new AllowStep();
        morphologyTophat_g.setOperation(topHat);
        morphologyTophat_g.setAllowoperationId(originalOperation);
        allowSteps.add(morphologyTophat_g);

        AllowStep morphologyTophat_h = new AllowStep();
        morphologyTophat_h.setOperation(topHat);
        morphologyTophat_h.setAllowoperationId(grayOperation);
        allowSteps.add(morphologyTophat_h);

        //GrayScale - erode

        AllowStep morphologyErodeGS_A = new AllowStep();
        morphologyErodeGS_A.setOperation(erode_a);
        morphologyErodeGS_A.setAllowoperationId(redOperation);
        allowSteps.add(morphologyErodeGS_A);

        AllowStep morphologyErodeGS_B = new AllowStep();
        morphologyErodeGS_B.setOperation(erode_a);
        morphologyErodeGS_B.setAllowoperationId(greenOperation);
        allowSteps.add(morphologyErodeGS_B);

        AllowStep morphologyErodeGS_C = new AllowStep();
        morphologyErodeGS_C.setOperation(erode_a);
        morphologyErodeGS_C.setAllowoperationId(blueOperation);
        allowSteps.add(morphologyErodeGS_C);

        AllowStep morphologyErodeGS_D = new AllowStep();
        morphologyErodeGS_D.setOperation(erode_a);
        morphologyErodeGS_D.setAllowoperationId(yOperation);
        allowSteps.add(morphologyErodeGS_D);

        AllowStep morphologyErodeGS_E = new AllowStep();
        morphologyErodeGS_E.setOperation(erode_a);
        morphologyErodeGS_E.setAllowoperationId(cbOperation);
        allowSteps.add(morphologyTophat_e);

        AllowStep morphologyErodeGS_F = new AllowStep();
        morphologyErodeGS_F.setOperation(erode_a);
        morphologyErodeGS_F.setAllowoperationId(crOperation);
        allowSteps.add(morphologyErodeGS_F);

        AllowStep morphologyErodeGS_G = new AllowStep();
        morphologyErodeGS_G.setOperation(erode_a);
        morphologyErodeGS_G.setAllowoperationId(originalOperation);
        allowSteps.add(morphologyErodeGS_G);

        AllowStep morphologyErodeGS_H = new AllowStep();
        morphologyErodeGS_H.setOperation(erode_a);
        morphologyErodeGS_H.setAllowoperationId(grayOperation);
        allowSteps.add(morphologyErodeGS_H);

        //GrayScale - dilate

        AllowStep morphologyDilateGS_A = new AllowStep();
        morphologyDilateGS_A.setOperation(dilate_a);
        morphologyDilateGS_A.setAllowoperationId(redOperation);
        allowSteps.add(morphologyDilateGS_A);

        AllowStep morphologyDilateGS_B = new AllowStep();
        morphologyDilateGS_B.setOperation(dilate_a);
        morphologyDilateGS_B.setAllowoperationId(greenOperation);
        allowSteps.add(morphologyDilateGS_B);

        AllowStep morphologyDilateGS_C = new AllowStep();
        morphologyDilateGS_C.setOperation(dilate_a);
        morphologyDilateGS_C.setAllowoperationId(blueOperation);
        allowSteps.add(morphologyDilateGS_C);

        AllowStep morphologyDilateGS_D = new AllowStep();
        morphologyDilateGS_D.setOperation(dilate_a);
        morphologyDilateGS_D.setAllowoperationId(yOperation);
        allowSteps.add(morphologyDilateGS_D);

        AllowStep morphologyDilateGS_E = new AllowStep();
        morphologyDilateGS_E.setOperation(dilate_a);
        morphologyDilateGS_E.setAllowoperationId(cbOperation);
        allowSteps.add(morphologyTophat_e);

        AllowStep morphologyDilateGS_F = new AllowStep();
        morphologyDilateGS_F.setOperation(dilate_a);
        morphologyDilateGS_F.setAllowoperationId(crOperation);
        allowSteps.add(morphologyDilateGS_F);

        AllowStep morphologyDilateGS_G = new AllowStep();
        morphologyDilateGS_G.setOperation(dilate_a);
        morphologyDilateGS_G.setAllowoperationId(originalOperation);
        allowSteps.add(morphologyDilateGS_G);

        AllowStep morphologyDilateGS_H = new AllowStep();
        morphologyDilateGS_H.setOperation(dilate_a);
        morphologyDilateGS_H.setAllowoperationId(grayOperation);
        allowSteps.add(morphologyDilateGS_H);

        //Grayscale - open

        AllowStep morphologyOpenGS_A = new AllowStep();
        morphologyOpenGS_A.setOperation(open_a);
        morphologyOpenGS_A.setAllowoperationId(redOperation);
        allowSteps.add(morphologyOpenGS_A);

        AllowStep morphologyOpenGS_B = new AllowStep();
        morphologyOpenGS_B.setOperation(open_a);
        morphologyOpenGS_B.setAllowoperationId(greenOperation);
        allowSteps.add(morphologyOpenGS_B);

        AllowStep morphologyOpenGS_C = new AllowStep();
        morphologyOpenGS_C.setOperation(open_a);
        morphologyOpenGS_C.setAllowoperationId(blueOperation);
        allowSteps.add(morphologyOpenGS_C);

        AllowStep morphologyOpenGS_D = new AllowStep();
        morphologyOpenGS_D.setOperation(open_a);
        morphologyOpenGS_D.setAllowoperationId(yOperation);
        allowSteps.add(morphologyOpenGS_D);

        AllowStep morphologyOpenGS_E = new AllowStep();
        morphologyOpenGS_E.setOperation(open_a);
        morphologyOpenGS_E.setAllowoperationId(cbOperation);
        allowSteps.add(morphologyTophat_e);

        AllowStep morphologyOpenGS_F = new AllowStep();
        morphologyOpenGS_F.setOperation(open_a);
        morphologyOpenGS_F.setAllowoperationId(crOperation);
        allowSteps.add(morphologyOpenGS_F);

        AllowStep morphologyOpenGS_G = new AllowStep();
        morphologyOpenGS_G.setOperation(open_a);
        morphologyOpenGS_G.setAllowoperationId(originalOperation);
        allowSteps.add(morphologyOpenGS_G);

        AllowStep morphologyOpenGS_H = new AllowStep();
        morphologyOpenGS_H.setOperation(open_a);
        morphologyOpenGS_H.setAllowoperationId(grayOperation);
        allowSteps.add(morphologyOpenGS_H);

        //GrayScale - close


        AllowStep morphologyCloseGS_A = new AllowStep();
        morphologyCloseGS_A.setOperation(close_a);
        morphologyCloseGS_A.setAllowoperationId(redOperation);
        allowSteps.add(morphologyCloseGS_A);

        AllowStep morphologyCloseGS_B = new AllowStep();
        morphologyCloseGS_B.setOperation(close_a);
        morphologyCloseGS_B.setAllowoperationId(greenOperation);
        allowSteps.add(morphologyCloseGS_B);

        AllowStep morphologyCloseGS_C = new AllowStep();
        morphologyCloseGS_C.setOperation(close_a);
        morphologyCloseGS_C.setAllowoperationId(blueOperation);
        allowSteps.add(morphologyCloseGS_C);

        AllowStep morphologyCloseGS_D = new AllowStep();
        morphologyCloseGS_D.setOperation(close_a);
        morphologyCloseGS_D.setAllowoperationId(yOperation);
        allowSteps.add(morphologyCloseGS_D);

        AllowStep morphologyCloseGS_E = new AllowStep();
        morphologyCloseGS_E.setOperation(close_a);
        morphologyCloseGS_E.setAllowoperationId(cbOperation);
        allowSteps.add(morphologyTophat_e);

        AllowStep morphologyCloseGS_F = new AllowStep();
        morphologyCloseGS_F.setOperation(close_a);
        morphologyCloseGS_F.setAllowoperationId(crOperation);
        allowSteps.add(morphologyCloseGS_F);

        AllowStep morphologyCloseGS_G = new AllowStep();
        morphologyCloseGS_G.setOperation(close_a);
        morphologyCloseGS_G.setAllowoperationId(originalOperation);
        allowSteps.add(morphologyCloseGS_G);

        AllowStep morphologyCloseGS_H = new AllowStep();
        morphologyCloseGS_H.setOperation(close_a);
        morphologyCloseGS_H.setAllowoperationId(grayOperation);
        allowSteps.add(morphologyCloseGS_H);

        //grayscale

        AllowStep allowStepErodeGs_B = new AllowStep();
        allowStepErodeGs_B.setOperation(THRESH_BINARY);
        allowStepErodeGs_B.setAllowoperationId(erode_a);
        allowSteps.add(allowStepErodeGs_B);

        AllowStep allowStepErodeGs_C = new AllowStep();
        allowStepErodeGs_C.setOperation(THRESH_BINARY_INV);
        allowStepErodeGs_C.setAllowoperationId(erode_a);
        allowSteps.add(allowStepErodeGs_C);

        AllowStep allowStepErodeGs_D = new AllowStep();
        allowStepErodeGs_D.setOperation(THRESH_TOZERO);
        allowStepErodeGs_D.setAllowoperationId(erode_a);
        allowSteps.add(allowStepErodeGs_D);

        AllowStep allowStepErodeGs_E = new AllowStep();
        allowStepErodeGs_E.setOperation(THRESH_TRUNC);
        allowStepErodeGs_E.setAllowoperationId(erode_a);
        allowSteps.add(allowStepErodeGs_E);

        AllowStep allowStepDilateGs_B = new AllowStep();
        allowStepDilateGs_B.setOperation(THRESH_BINARY);
        allowStepDilateGs_B.setAllowoperationId(dilate_a);
        allowSteps.add(allowStepDilateGs_B);

        AllowStep allowStepDilateGs_C = new AllowStep();
        allowStepDilateGs_C.setOperation(THRESH_BINARY_INV);
        allowStepDilateGs_C.setAllowoperationId(dilate_a);
        allowSteps.add(allowStepDilateGs_C);

        AllowStep allowStepDilateGs_D = new AllowStep();
        allowStepDilateGs_D.setOperation(THRESH_TOZERO);
        allowStepDilateGs_D.setAllowoperationId(dilate_a);
        allowSteps.add(allowStepDilateGs_D);

        AllowStep allowStepDilateGs_E = new AllowStep();
        allowStepDilateGs_E.setOperation(THRESH_TRUNC);
        allowStepDilateGs_E.setAllowoperationId(dilate_a);
        allowSteps.add(allowStepDilateGs_E);

        AllowStep allowStepOpenGs_B = new AllowStep();
        allowStepOpenGs_B.setOperation(THRESH_BINARY);
        allowStepOpenGs_B.setAllowoperationId(open_a);
        allowSteps.add(allowStepOpenGs_B);

        AllowStep allowStepOpenGs_C = new AllowStep();
        allowStepOpenGs_C.setOperation(THRESH_BINARY_INV);
        allowStepOpenGs_C.setAllowoperationId(open_a);
        allowSteps.add(allowStepOpenGs_C);

        AllowStep allowStepOpenGs_D = new AllowStep();
        allowStepOpenGs_D.setOperation(THRESH_TOZERO);
        allowStepOpenGs_D.setAllowoperationId(open_a);
        allowSteps.add(allowStepOpenGs_D);

        AllowStep allowStepOpenGs_E = new AllowStep();
        allowStepOpenGs_E.setOperation(THRESH_TRUNC);
        allowStepOpenGs_E.setAllowoperationId(open_a);
        allowSteps.add(allowStepOpenGs_E);

        AllowStep allowStepCloseGs_B = new AllowStep();
        allowStepCloseGs_B.setOperation(THRESH_BINARY);
        allowStepCloseGs_B.setAllowoperationId(close_a);
        allowSteps.add(allowStepCloseGs_B);

        AllowStep allowStepCloseGs_C = new AllowStep();
        allowStepCloseGs_C.setOperation(THRESH_BINARY_INV);
        allowStepCloseGs_C.setAllowoperationId(close_a);
        allowSteps.add(allowStepCloseGs_C);

        AllowStep allowStepCloseGs_D = new AllowStep();
        allowStepCloseGs_D.setOperation(THRESH_TOZERO);
        allowStepCloseGs_D.setAllowoperationId(close_a);
        allowSteps.add(allowStepCloseGs_D);

        AllowStep allowStepCloseGs_E = new AllowStep();
        allowStepCloseGs_E.setOperation(THRESH_TRUNC);
        allowStepCloseGs_E.setAllowoperationId(close_a);
        allowSteps.add(allowStepCloseGs_E);

        //binary

        AllowStep morphologyErode_a = new AllowStep();
        morphologyErode_a.setOperation(erode);
        morphologyErode_a.setAllowoperationId(THRESH_BINARY);
        allowSteps.add(morphologyErode_a);

        AllowStep morphologyErode_b = new AllowStep();
        morphologyErode_b.setOperation(erode);
        morphologyErode_b.setAllowoperationId(THRESH_BINARY_INV);
        allowSteps.add(morphologyErode_b);

        AllowStep morphologyErode_c = new AllowStep();
        morphologyErode_c.setOperation(erode);
        morphologyErode_c.setAllowoperationId(THRESH_TOZERO);
        allowSteps.add(morphologyErode_c);

        AllowStep morphologyErode_d = new AllowStep();
        morphologyErode_d.setOperation(erode);
        morphologyErode_d.setAllowoperationId(THRESH_TRUNC);
        allowSteps.add(morphologyErode_d);

        AllowStep morphologyDilate_a = new AllowStep();
        morphologyDilate_a.setOperation(dilate);
        morphologyDilate_a.setAllowoperationId(THRESH_BINARY);
        allowSteps.add(morphologyDilate_a);

        AllowStep morphologyDilate_b = new AllowStep();
        morphologyDilate_b.setOperation(dilate);
        morphologyDilate_b.setAllowoperationId(THRESH_BINARY_INV);
        allowSteps.add(morphologyDilate_b);

        AllowStep morphologyDilate_c = new AllowStep();
        morphologyDilate_c.setOperation(dilate);
        morphologyDilate_c.setAllowoperationId(THRESH_TOZERO);
        allowSteps.add(morphologyDilate_c);

        AllowStep morphologyDilate_d = new AllowStep();
        morphologyDilate_d.setOperation(dilate);
        morphologyDilate_d.setAllowoperationId(THRESH_TRUNC);
        allowSteps.add(morphologyDilate_d);

        AllowStep morphologyOpen_a = new AllowStep();
        morphologyOpen_a.setOperation(open);
        morphologyOpen_a.setAllowoperationId(THRESH_BINARY);
        allowSteps.add(morphologyOpen_a);

        AllowStep morphologyOpen_b = new AllowStep();
        morphologyOpen_b.setOperation(open);
        morphologyOpen_b.setAllowoperationId(THRESH_BINARY_INV);
        allowSteps.add(morphologyOpen_b);

        AllowStep morphologyOpen_c = new AllowStep();
        morphologyOpen_c.setOperation(open);
        morphologyOpen_c.setAllowoperationId(THRESH_TOZERO);
        allowSteps.add(morphologyOpen_c);

        AllowStep morphologyOpen_d = new AllowStep();
        morphologyOpen_d.setOperation(open);
        morphologyOpen_d.setAllowoperationId(THRESH_TRUNC);
        allowSteps.add(morphologyOpen_d);

        AllowStep morphologyClose_a = new AllowStep();
        morphologyClose_a.setOperation(close);
        morphologyClose_a.setAllowoperationId(THRESH_BINARY);
        allowSteps.add(morphologyClose_a);

        AllowStep morphologyClose_b = new AllowStep();
        morphologyClose_b.setOperation(close);
        morphologyClose_b.setAllowoperationId(THRESH_BINARY_INV);
        allowSteps.add(morphologyClose_b);

        AllowStep morphologyClose_c = new AllowStep();
        morphologyClose_c.setOperation(close);
        morphologyClose_c.setAllowoperationId(THRESH_TOZERO);
        allowSteps.add(morphologyClose_c);

        AllowStep morphologyClose_d = new AllowStep();
        morphologyClose_d.setOperation(close);
        morphologyClose_d.setAllowoperationId(THRESH_TRUNC);
        allowSteps.add(morphologyClose_d);


        AllowStep allowResize = new AllowStep();
        allowResize.setOperation(resizing);
        allowResize.setAllowoperationId(originalOperation);
        allowSteps.add(allowResize);

        AllowStep allowRotate = new AllowStep();
        allowRotate.setOperation(rotating);
        allowRotate.setAllowoperationId(originalOperation);
        allowSteps.add(allowRotate);
/////
        AllowStep allowResizeA = new AllowStep();
        allowResizeA.setOperation(resizing);
        allowResizeA.setAllowoperationId(redOperation);
        allowSteps.add(allowResizeA);

        AllowStep allowRotateA = new AllowStep();
        allowRotateA.setOperation(rotating);
        allowRotateA.setAllowoperationId(redOperation);
        allowSteps.add(allowRotateA);

        AllowStep allowResizeB = new AllowStep();
        allowResizeB.setOperation(resizing);
        allowResizeB.setAllowoperationId(greenOperation);
        allowSteps.add(allowResizeB);

        AllowStep allowRotateB = new AllowStep();
        allowRotateB.setOperation(rotating);
        allowRotateB.setAllowoperationId(greenOperation);
        allowSteps.add(allowRotateB);

        AllowStep allowResizeC = new AllowStep();
        allowResizeC.setOperation(resizing);
        allowResizeC.setAllowoperationId(blueOperation);
        allowSteps.add(allowResizeC);

        AllowStep allowRotateC = new AllowStep();
        allowRotateC.setOperation(rotating);
        allowRotateC.setAllowoperationId(blueOperation);
        allowSteps.add(allowRotateC);

        AllowStep allowResizeD = new AllowStep();
        allowResizeD.setOperation(resizing);
        allowResizeD.setAllowoperationId(grayOperation);
        allowSteps.add(allowResizeD);

        AllowStep allowRotateD = new AllowStep();
        allowRotateD.setOperation(rotating);
        allowRotateD.setAllowoperationId(grayOperation);
        allowSteps.add(allowRotateD);

        AllowStep allowResizeE = new AllowStep();
        allowResizeE.setOperation(resizing);
        allowResizeE.setAllowoperationId(yOperation);
        allowSteps.add(allowResizeE);

        AllowStep allowRotateE = new AllowStep();
        allowRotateE.setOperation(rotating);
        allowRotateE.setAllowoperationId(yOperation);
        allowSteps.add(allowRotateE);

        AllowStep allowResizeF = new AllowStep();
        allowResizeF.setOperation(resizing);
        allowResizeF.setAllowoperationId(cbOperation);
        allowSteps.add(allowResizeF);

        AllowStep allowRotateF = new AllowStep();
        allowRotateF.setOperation(rotating);
        allowRotateF.setAllowoperationId(cbOperation);
        allowSteps.add(allowRotateF);

        AllowStep allowResizeG = new AllowStep();
        allowResizeG.setOperation(resizing);
        allowResizeG.setAllowoperationId(crOperation);
        allowSteps.add(allowResizeG);

        AllowStep allowRotateG = new AllowStep();
        allowRotateG.setOperation(rotating);
        allowRotateG.setAllowoperationId(crOperation);
        allowSteps.add(allowRotateG);


        AllowStep allowHTC_A = new AllowStep();
        allowHTC_A.setOperation(HTC);
        allowHTC_A.setAllowoperationId(laplace);
        allowSteps.add(allowHTC_A);

        AllowStep allowHTC_B = new AllowStep();
        allowHTC_B.setOperation(HTC);
        allowHTC_B.setAllowoperationId(sobel);
        allowSteps.add(allowHTC_B);

        AllowStep allowHTC_C = new AllowStep();
        allowHTC_C.setOperation(HTC);
        allowHTC_C.setAllowoperationId(canny);
        allowSteps.add(allowHTC_C);

        AllowStep allowHTL_A = new AllowStep();
        allowHTL_A.setOperation(HTL);
        allowHTL_A.setAllowoperationId(laplace);
        allowSteps.add(allowHTL_A);

        AllowStep allowHTL_B = new AllowStep();
        allowHTL_B.setOperation(HTL);
        allowHTL_B.setAllowoperationId(sobel);
        allowSteps.add(allowHTL_B);

        AllowStep allowHTL_C = new AllowStep();
        allowHTL_C.setOperation(HTL);
        allowHTL_C.setAllowoperationId(canny);
        allowSteps.add(allowHTL_C);

        AllowStep allowStep_tmpl_A = new AllowStep();
        allowStep_tmpl_A.setOperation(tempMatching);
        allowStep_tmpl_A.setAllowoperationId(redOperation);
        allowSteps.add(allowStep_tmpl_A);

        AllowStep allowStep_tmpl_B = new AllowStep();
        allowStep_tmpl_B.setOperation(tempMatching);
        allowStep_tmpl_B.setAllowoperationId(greenOperation);
        allowSteps.add(allowStep_tmpl_B);

        AllowStep allowStep_tmpl_C = new AllowStep();
        allowStep_tmpl_C.setOperation(tempMatching);
        allowStep_tmpl_C.setAllowoperationId(blueOperation);
        allowSteps.add(allowStep_tmpl_C);

        AllowStep allowStep_tmpl_D = new AllowStep();
        allowStep_tmpl_D.setOperation(tempMatching);
        allowStep_tmpl_D.setAllowoperationId(grayOperation);
        allowSteps.add(allowStep_tmpl_D);

        AllowStep allowStep_tmpl_E = new AllowStep();
        allowStep_tmpl_E.setOperation(tempMatching);
        allowStep_tmpl_E.setAllowoperationId(yOperation);
        allowSteps.add(allowStep_tmpl_E);

        AllowStep allowStep_tmpl_F = new AllowStep();
        allowStep_tmpl_F.setOperation(tempMatching);
        allowStep_tmpl_F.setAllowoperationId(cbOperation);
        allowSteps.add(allowStep_tmpl_F);

        AllowStep allowStep_tmpl_G = new AllowStep();
        allowStep_tmpl_G.setOperation(tempMatching);
        allowStep_tmpl_G.setAllowoperationId(crOperation);
        allowSteps.add(allowStep_tmpl_G);
///
        AllowStep allowDistTrans_A = new AllowStep();
        allowDistTrans_A.setOperation(distTransfrom);
        allowDistTrans_A.setAllowoperationId(open);
        allowSteps.add(allowDistTrans_A);

        AllowStep allowDistTrans_B = new AllowStep();
        allowDistTrans_B.setOperation(distTransfrom);
        allowDistTrans_B.setAllowoperationId(close);
        allowSteps.add(allowDistTrans_B);

        AllowStep allowDistTrans_C = new AllowStep();
        allowDistTrans_C.setOperation(distTransfrom);
        allowDistTrans_C.setAllowoperationId(erode);
        allowSteps.add(allowDistTrans_C);

        AllowStep allowDistTrans_D = new AllowStep();
        allowDistTrans_D.setOperation(distTransfrom);
        allowDistTrans_D.setAllowoperationId(dilate);
        allowSteps.add(allowDistTrans_D);

        AllowStep allowDistTrans_E = new AllowStep();
        allowDistTrans_E.setOperation(distTransfrom);
        allowDistTrans_E.setAllowoperationId(THRESH_BINARY);
        allowSteps.add(allowDistTrans_E);

        AllowStep allowDistTrans_F = new AllowStep();
        allowDistTrans_F.setOperation(distTransfrom);
        allowDistTrans_F.setAllowoperationId(THRESH_BINARY_INV);
        allowSteps.add(allowDistTrans_F);

        AllowStep allowDistTrans_G = new AllowStep();
        allowDistTrans_G.setOperation(distTransfrom);
        allowDistTrans_G.setAllowoperationId(THRESH_TOZERO);
        allowSteps.add(allowDistTrans_G);

        AllowStep allowDistTrans_H = new AllowStep();
        allowDistTrans_H.setOperation(distTransfrom);
        allowDistTrans_H.setAllowoperationId(THRESH_TRUNC);
        allowSteps.add(allowDistTrans_H);

        AllowStep allowDistTrans_I = new AllowStep();
        allowDistTrans_I.setOperation(distTransfrom);
        allowDistTrans_I.setAllowoperationId(topHat);
        allowSteps.add(allowDistTrans_I);

        //

        AllowStep allowDWatershed_A = new AllowStep();
        allowDWatershed_A.setOperation(watershed);
        allowDWatershed_A.setAllowoperationId(THRESH_BINARY);
        allowSteps.add(allowDWatershed_A);

        AllowStep allowDWatershed_B = new AllowStep();
        allowDWatershed_B.setOperation(watershed);
        allowDWatershed_B.setAllowoperationId(THRESH_BINARY_INV);
        allowSteps.add(allowDWatershed_B);

        AllowStep allowDWatershed_C = new AllowStep();
        allowDWatershed_C.setOperation(watershed);
        allowDWatershed_C.setAllowoperationId(THRESH_TOZERO);
        allowSteps.add(allowDWatershed_C);

        AllowStep allowDWatershed_D = new AllowStep();
        allowDWatershed_D.setOperation(watershed);
        allowDWatershed_D.setAllowoperationId(THRESH_TRUNC);
        allowSteps.add(allowDWatershed_D);

        AllowStep allowDWatershed_E = new AllowStep();
        allowDWatershed_E.setOperation(watershed);
        allowDWatershed_E.setAllowoperationId(colorSeg);
        allowSteps.add(allowDWatershed_E);

        //


        AllowStep allowDWatershed_F = new AllowStep();
        allowDWatershed_F.setOperation(watershed);
        allowDWatershed_F.setAllowoperationId(blueOperation);
        allowSteps.add(allowDWatershed_F);

        AllowStep allowDWatershed_G = new AllowStep();
        allowDWatershed_G.setOperation(watershed);
        allowDWatershed_G.setAllowoperationId(greenOperation);
        allowSteps.add(allowDWatershed_G);

        AllowStep allowDWatershed_H = new AllowStep();
        allowDWatershed_H.setOperation(watershed);
        allowDWatershed_H.setAllowoperationId(redOperation);
        allowSteps.add(allowDWatershed_H);

        AllowStep allowDWatershed_I = new AllowStep();
        allowDWatershed_I.setOperation(watershed);
        allowDWatershed_I.setAllowoperationId(crOperation);
        allowSteps.add(allowDWatershed_I);

        AllowStep allowDWatershed_J = new AllowStep();
        allowDWatershed_J.setOperation(watershed);
        allowDWatershed_J.setAllowoperationId(cbOperation);
        allowSteps.add(allowDWatershed_J);

        AllowStep allowDWatershed_K = new AllowStep();
        allowDWatershed_K.setOperation(watershed);
        allowDWatershed_K.setAllowoperationId(yOperation);
        allowSteps.add(allowDWatershed_K);

        AllowStep allowDWatershed_L = new AllowStep();
        allowDWatershed_L.setOperation(watershed);
        allowDWatershed_L.setAllowoperationId(grayOperation);
        allowSteps.add(allowDWatershed_L);

        AllowStep allowDWatershed_M = new AllowStep();
        allowDWatershed_M.setOperation(watershed);
        allowDWatershed_M.setAllowoperationId(originalOperation);
        allowSteps.add(allowDWatershed_M);

        AllowStep allowDWatershed_N = new AllowStep();
        allowDWatershed_N.setOperation(watershed);
        allowDWatershed_N.setAllowoperationId(originalOperation);
        allowSteps.add(allowDWatershed_N);

        AllowStep allowDWatershed_O = new AllowStep();
        allowDWatershed_O.setOperation(watershed);
        allowDWatershed_O.setAllowoperationId(distTransfrom);
        allowSteps.add(allowDWatershed_O);

        AllowStep allowDWatershed_TEST = new AllowStep();
        allowDWatershed_TEST.setOperation(watershed);
        allowDWatershed_TEST.setAllowoperationId(originalOperation);
        allowSteps.add(allowDWatershed_TEST);

        AllowStep allowDWatershed_TEST2 = new AllowStep();
        allowDWatershed_TEST2.setOperation(colorSeg);
        allowDWatershed_TEST2.setAllowoperationId(watershed);
        allowSteps.add(allowDWatershed_TEST2);

        AllowStep allowDWatershed_TEST3 = new AllowStep();
        allowDWatershed_TEST3.setOperation(THRESH_BINARY);
        allowDWatershed_TEST3.setAllowoperationId(watershed);
        allowSteps.add(allowDWatershed_TEST3);

        AllowStep allowDWatershed_TEST4 = new AllowStep();
        allowDWatershed_TEST4.setOperation(THRESH_BINARY_INV);
        allowDWatershed_TEST4.setAllowoperationId(watershed);
        allowSteps.add(allowDWatershed_TEST4);

        AllowStep grabCut_A = new AllowStep();
        grabCut_A.setOperation(gCut);
        grabCut_A.setAllowoperationId(originalOperation);
        allowSteps.add(grabCut_A);

        AllowStep grabCut_B = new AllowStep();
        grabCut_B.setOperation(gCutM);
        grabCut_B.setAllowoperationId(originalOperation);
        allowSteps.add(grabCut_B);

        allowStepDAO.save(allowSteps);
    }


    public AttributeDAO getAttributeDAO() {
        return attributeDAO;
    }

    public void setAttributeDAO(AttributeDAO attributeDAO) {
        this.attributeDAO = attributeDAO;
    }

    public ChainDAO getChainDAO() {
        return chainDAO;
    }

    public void setChainDAO(ChainDAO chainDAO) {
        this.chainDAO = chainDAO;
    }

    public MethodDAO getMethodDAO() {
        return methodDAO;
    }

    public void setMethodDAO(MethodDAO methodDAO) {
        this.methodDAO = methodDAO;
    }

    public PartDAO getPartDAO() {
        return partDAO;
    }

    public void setPartDAO(PartDAO partDAO) {
        this.partDAO = partDAO;
    }

    public AllowStepDAO getAllowStepDAO() {
        return allowStepDAO;
    }

    public void setAllowStepDAO(AllowStepDAO allowStepDAO) {
        this.allowStepDAO = allowStepDAO;
    }


}
