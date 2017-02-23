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


    private OperationRegister operationRegister = OperationRegister.getInstance();


    @Override
    public void afterPropertiesSet() throws Exception {
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

        Function colorSpaces = new Function();
        colorSpaces.setName("Color Spaces");
        functions.add(colorSpaces);
        Function edgeDetectors = new Function();
        edgeDetectors.setName("Edge Detectors");
        functions.add(edgeDetectors);
        Function denoiser = new Function();
        denoiser.setName("Denoiser");
        functions.add(denoiser);
        Function morphology = new Function();
        morphology.setName("Morphology operations");
        functions.add(morphology);
        Function segment = new Function();
        segment.setName("Segmentor");
        functions.add(segment);
        Function tmpMatching = new Function();
        tmpMatching.setName("Template Matching");
        functions.add(tmpMatching);


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

        Method fDerivation = new Method();
        fDerivation.setFunction(edgeDetectors);
        fDerivation.setName("First Derivation");
        methods.add(fDerivation);

        Method sDerivation = new Method();
        sDerivation.setFunction(edgeDetectors);
        sDerivation.setName("Second Derivation");
        methods.add(sDerivation);


        Method noise = new Method();
        noise.setFunction(denoiser);
        noise.setName("Noise reducer");
        methods.add(noise);

        Method coloring = new Method();
        coloring.setFunction(segment);
        coloring.setName("Coloring");
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

        Method tmplMatching = new Method();
        tmplMatching.setFunction(tmpMatching);
        tmplMatching.setName("Geom. Transformations");
        methods.add(tmplMatching);

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
        grayOperation.setMethod(RGB);
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
        laplace.setMethod(sDerivation);
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

        Operation colorSeg = new Operation();
        colorSeg.setName("Coloring");
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
        HTC.setMethod(gTransformation);
        operations.add(HTC);

        Operation HTL = new Operation();
        HTL.setName("Hough lines");
        HTL.setMethod(gTransformation);
        operations.add(HTL);

        Operation tempMatching = new Operation();
        tempMatching.setName("Template Matching");
        tempMatching.setMethod(tmplMatching);
        operations.add(tempMatching);


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

        operationRegister.register(THRESH_BINARY.getOperationId(), Segmentation.class, SegmentorEnum.THRESH_BINARY.getSegmentorName());
        operationRegister.register(THRESH_BINARY_INV.getOperationId(), Segmentation.class, SegmentorEnum.THRESH_BINARY_INV.getSegmentorName());
        operationRegister.register(THRESH_TOZERO.getOperationId(), Segmentation.class, SegmentorEnum.THRESH_TOZERO.getSegmentorName());
        operationRegister.register(THRESH_TRUNC.getOperationId(), Segmentation.class, SegmentorEnum.THRESH_TRUNC.getSegmentorName());
        operationRegister.register(colorSeg.getOperationId(), Segmentation.class, SegmentorEnum.COLORING.getSegmentorName());

        operationRegister.register(erode.getOperationId(), MorfologyTransformations.class, MorphologyEnum.ERODE.getMorphologyName());
        operationRegister.register(dilate.getOperationId(), MorfologyTransformations.class, MorphologyEnum.DILATE.getMorphologyName());
        operationRegister.register(open.getOperationId(), MorfologyTransformations.class, MorphologyEnum.OPEN.getMorphologyName());
        operationRegister.register(close.getOperationId(), MorfologyTransformations.class, MorphologyEnum.CLOSE.getMorphologyName());
        operationRegister.register(topHat.getOperationId(), MorfologyTransformations.class, MorphologyEnum.TOPHAT.getMorphologyName());

        operationRegister.register(resizing.getOperationId(), GeometricTransformations.class, GeometricTransformationEnum.RESIZE.getGgometricTransformationName());
        operationRegister.register(rotating.getOperationId(), GeometricTransformations.class, GeometricTransformationEnum.ROTATE.getGgometricTransformationName());
        operationRegister.register(HTC.getOperationId(), GeometricTransformations.class, HoughTransformationEnum.CIRCLE.getGgometricTransformationName());
        operationRegister.register(HTL.getOperationId(), GeometricTransformations.class, HoughTransformationEnum.LINE.getGgometricTransformationName());

        operationRegister.register(tempMatching.getOperationId(), TemplateSearch.class, TemplateEnum.TM.getTemplateName());


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


        operationDAO.save(operationAttributes);


        AllowStep allowStep_org = new AllowStep();
        allowStep_org.setOperation(originalOperation);
        allowStep_org.setAllowoperationId(null);
        allowSteps.add(allowStep_org);

        AllowStep allowStep_R = new AllowStep();
        allowStep_R.setOperation(redOperation);
        allowStep_R.setAllowoperationId(originalOperation.getOperationId());
        allowSteps.add(allowStep_R);

        AllowStep allowStep_G = new AllowStep();
        allowStep_G.setOperation(greenOperation);
        allowStep_G.setAllowoperationId(originalOperation.getOperationId());
        allowSteps.add(allowStep_G);

        AllowStep allowStep_B = new AllowStep();
        allowStep_B.setOperation(blueOperation);
        allowStep_B.setAllowoperationId(originalOperation.getOperationId());
        allowSteps.add(allowStep_B);

        AllowStep allowStep_GRAY = new AllowStep();
        allowStep_GRAY.setOperation(grayOperation);
        allowStep_GRAY.setAllowoperationId(originalOperation.getOperationId());
        allowSteps.add(allowStep_GRAY);

        AllowStep allowStep_Y = new AllowStep();
        allowStep_Y.setOperation(yOperation);
        allowStep_Y.setAllowoperationId(originalOperation.getOperationId());
        allowSteps.add(allowStep_Y);

        AllowStep allowStep_CB = new AllowStep();
        allowStep_CB.setOperation(cbOperation);
        allowStep_CB.setAllowoperationId(originalOperation.getOperationId());
        allowSteps.add(allowStep_CB);

        AllowStep allowStep_CR = new AllowStep();
        allowStep_CR.setOperation(crOperation);
        allowStep_CR.setAllowoperationId(originalOperation.getOperationId());
        allowSteps.add(allowStep_CR);

        AllowStep allowStep_H = new AllowStep();
        allowStep_H.setOperation(hOperation);
        allowStep_H.setAllowoperationId(originalOperation.getOperationId());
        allowSteps.add(allowStep_H);

        AllowStep allowStep_sobel_a = new AllowStep();
        allowStep_sobel_a.setOperation(sobel);
        allowStep_sobel_a.setAllowoperationId(grayOperation.getOperationId());
        allowSteps.add(allowStep_sobel_a);

        AllowStep allowStep_sobel_b = new AllowStep();
        allowStep_sobel_b.setOperation(sobel);
        allowStep_sobel_b.setAllowoperationId(redOperation.getOperationId());
        allowSteps.add(allowStep_sobel_b);

        AllowStep allowStep_sobel_c = new AllowStep();
        allowStep_sobel_c.setOperation(sobel);
        allowStep_sobel_c.setAllowoperationId(greenOperation.getOperationId());
        allowSteps.add(allowStep_sobel_c);

        AllowStep allowStep_sobel_d = new AllowStep();
        allowStep_sobel_d.setOperation(sobel);
        allowStep_sobel_d.setAllowoperationId(blueOperation.getOperationId());
        allowSteps.add(allowStep_sobel_d);

        AllowStep allowStep_sobel_e = new AllowStep();
        allowStep_sobel_e.setOperation(sobel);
        allowStep_sobel_e.setAllowoperationId(yOperation.getOperationId());
        allowSteps.add(allowStep_sobel_e);

        AllowStep allowStep_sobel_f = new AllowStep();
        allowStep_sobel_f.setOperation(sobel);
        allowStep_sobel_f.setAllowoperationId(cbOperation.getOperationId());
        allowSteps.add(allowStep_sobel_f);

        AllowStep allowStep_sobel_g = new AllowStep();
        allowStep_sobel_g.setOperation(sobel);
        allowStep_sobel_g.setAllowoperationId(crOperation.getOperationId());
        allowSteps.add(allowStep_sobel_g);
        ////////////////////////////////////
        AllowStep allowStep_laplace_a = new AllowStep();
        allowStep_laplace_a.setOperation(laplace);
        allowStep_laplace_a.setAllowoperationId(grayOperation.getOperationId());
        allowSteps.add(allowStep_laplace_a);

        AllowStep allowStep_laplace_b = new AllowStep();
        allowStep_laplace_b.setOperation(laplace);
        allowStep_laplace_b.setAllowoperationId(redOperation.getOperationId());
        allowSteps.add(allowStep_laplace_b);

        AllowStep allowStep_laplace_c = new AllowStep();
        allowStep_laplace_c.setOperation(laplace);
        allowStep_laplace_c.setAllowoperationId(greenOperation.getOperationId());
        allowSteps.add(allowStep_laplace_c);

        AllowStep allowStep_laplace_d = new AllowStep();
        allowStep_laplace_d.setOperation(laplace);
        allowStep_laplace_d.setAllowoperationId(blueOperation.getOperationId());
        allowSteps.add(allowStep_laplace_d);

        AllowStep allowStep_laplace_e = new AllowStep();
        allowStep_laplace_e.setOperation(laplace);
        allowStep_laplace_e.setAllowoperationId(yOperation.getOperationId());
        allowSteps.add(allowStep_laplace_e);

        AllowStep allowStep_laplace_f = new AllowStep();
        allowStep_laplace_f.setOperation(laplace);
        allowStep_laplace_f.setAllowoperationId(cbOperation.getOperationId());
        allowSteps.add(allowStep_laplace_f);

        AllowStep allowStep_laplace_g = new AllowStep();
        allowStep_laplace_g.setOperation(laplace);
        allowStep_laplace_g.setAllowoperationId(crOperation.getOperationId());
        allowSteps.add(allowStep_laplace_g);


        ////////////////////////////////////

        ////////////////////////////////////
        AllowStep allowStep_canny_a = new AllowStep();
        allowStep_canny_a.setOperation(canny);
        allowStep_canny_a.setAllowoperationId(grayOperation.getOperationId());
        allowSteps.add(allowStep_canny_a);

        AllowStep allowStep_canny_b = new AllowStep();
        allowStep_canny_b.setOperation(canny);
        allowStep_canny_b.setAllowoperationId(redOperation.getOperationId());
        allowSteps.add(allowStep_canny_b);

        AllowStep allowStep_canny_c = new AllowStep();
        allowStep_canny_c.setOperation(canny);
        allowStep_canny_c.setAllowoperationId(greenOperation.getOperationId());
        allowSteps.add(allowStep_canny_c);

        AllowStep allowStep_canny_d = new AllowStep();
        allowStep_canny_d.setOperation(canny);
        allowStep_canny_d.setAllowoperationId(blueOperation.getOperationId());
        allowSteps.add(allowStep_canny_d);

        AllowStep allowStep_canny_e = new AllowStep();
        allowStep_canny_e.setOperation(canny);
        allowStep_canny_e.setAllowoperationId(yOperation.getOperationId());
        allowSteps.add(allowStep_canny_e);

        AllowStep allowStep_canny_f = new AllowStep();
        allowStep_canny_f.setOperation(canny);
        allowStep_canny_f.setAllowoperationId(cbOperation.getOperationId());
        allowSteps.add(allowStep_canny_f);

        AllowStep allowStep_canny_g = new AllowStep();
        allowStep_canny_g.setOperation(canny);
        allowStep_canny_g.setAllowoperationId(crOperation.getOperationId());
        allowSteps.add(allowStep_canny_g);


        ////////////////////////////////////

        AllowStep allowStep_threshold_b = new AllowStep();
        allowStep_threshold_b.setOperation(THRESH_BINARY);
        allowStep_threshold_b.setAllowoperationId(redOperation.getOperationId());
        allowSteps.add(allowStep_threshold_b);

        AllowStep allowStep_threshold_c = new AllowStep();
        allowStep_threshold_c.setOperation(THRESH_BINARY);
        allowStep_threshold_c.setAllowoperationId(greenOperation.getOperationId());
        allowSteps.add(allowStep_threshold_c);

        AllowStep allowStep_threshold_d = new AllowStep();
        allowStep_threshold_d.setOperation(THRESH_BINARY);
        allowStep_threshold_d.setAllowoperationId(blueOperation.getOperationId());
        allowSteps.add(allowStep_threshold_d);

        AllowStep allowStep_threshold_e = new AllowStep();
        allowStep_threshold_e.setOperation(THRESH_BINARY);
        allowStep_threshold_e.setAllowoperationId(yOperation.getOperationId());
        allowSteps.add(allowStep_threshold_e);

        AllowStep allowStep_threshold_f = new AllowStep();
        allowStep_threshold_f.setOperation(THRESH_BINARY);
        allowStep_threshold_f.setAllowoperationId(cbOperation.getOperationId());
        allowSteps.add(allowStep_threshold_f);
        AllowStep allowStep_threshold_a = new AllowStep();

        allowStep_threshold_a.setOperation(THRESH_BINARY);
        allowStep_threshold_a.setAllowoperationId(crOperation.getOperationId());
        allowSteps.add(allowStep_threshold_a);

        AllowStep allowStep_threshold_g = new AllowStep();
        allowStep_threshold_g.setOperation(THRESH_BINARY);
        allowStep_threshold_g.setAllowoperationId(grayOperation.getOperationId());
        allowSteps.add(allowStep_threshold_g);

        //
        AllowStep allowStep_threshold_aa = new AllowStep();
        allowStep_threshold_aa.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_aa.setAllowoperationId(crOperation.getOperationId());
        allowSteps.add(allowStep_threshold_aa);

        AllowStep allowStep_threshold_bb = new AllowStep();
        allowStep_threshold_bb.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_bb.setAllowoperationId(redOperation.getOperationId());
        allowSteps.add(allowStep_threshold_bb);

        AllowStep allowStep_threshold_cc = new AllowStep();
        allowStep_threshold_cc.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_cc.setAllowoperationId(greenOperation.getOperationId());
        allowSteps.add(allowStep_threshold_cc);

        AllowStep allowStep_threshold_dd = new AllowStep();
        allowStep_threshold_dd.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_dd.setAllowoperationId(blueOperation.getOperationId());
        allowSteps.add(allowStep_threshold_dd);

        AllowStep allowStep_threshold_ee = new AllowStep();
        allowStep_threshold_ee.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_ee.setAllowoperationId(yOperation.getOperationId());
        allowSteps.add(allowStep_threshold_ee);

        AllowStep allowStep_threshold_ff = new AllowStep();
        allowStep_threshold_ff.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_ff.setAllowoperationId(cbOperation.getOperationId());
        allowSteps.add(allowStep_threshold_ff);

        AllowStep allowStep_threshold_gg = new AllowStep();
        allowStep_threshold_gg.setOperation(THRESH_TOZERO);
        allowStep_threshold_gg.setAllowoperationId(grayOperation.getOperationId());
        allowSteps.add(allowStep_threshold_gg);
        //
        AllowStep allowStep_threshold_aaa = new AllowStep();
        allowStep_threshold_aaa.setOperation(THRESH_TOZERO);
        allowStep_threshold_aaa.setAllowoperationId(grayOperation.getOperationId());
        allowSteps.add(allowStep_threshold_aaa);

        AllowStep allowStep_threshold_bbb = new AllowStep();
        allowStep_threshold_bbb.setOperation(THRESH_TOZERO);
        allowStep_threshold_bbb.setAllowoperationId(redOperation.getOperationId());
        allowSteps.add(allowStep_threshold_bbb);

        AllowStep allowStep_threshold_ccc = new AllowStep();
        allowStep_threshold_ccc.setOperation(THRESH_TOZERO);
        allowStep_threshold_ccc.setAllowoperationId(greenOperation.getOperationId());
        allowSteps.add(allowStep_threshold_ccc);

        AllowStep allowStep_threshold_ddd = new AllowStep();
        allowStep_threshold_ddd.setOperation(THRESH_TOZERO);
        allowStep_threshold_ddd.setAllowoperationId(blueOperation.getOperationId());
        allowSteps.add(allowStep_threshold_ddd);

        AllowStep allowStep_threshold_eee = new AllowStep();
        allowStep_threshold_eee.setOperation(THRESH_TOZERO);
        allowStep_threshold_eee.setAllowoperationId(yOperation.getOperationId());
        allowSteps.add(allowStep_threshold_eee);

        AllowStep allowStep_threshold_fff = new AllowStep();
        allowStep_threshold_fff.setOperation(THRESH_TOZERO);
        allowStep_threshold_fff.setAllowoperationId(cbOperation.getOperationId());
        allowSteps.add(allowStep_threshold_fff);

        AllowStep allowStep_threshold_ggg = new AllowStep();
        allowStep_threshold_ggg.setOperation(THRESH_TOZERO);
        allowStep_threshold_ggg.setAllowoperationId(crOperation.getOperationId());
        allowSteps.add(allowStep_threshold_ggg);
////////////
        AllowStep allowStep_threshold_aaaa = new AllowStep();
        allowStep_threshold_aaaa.setOperation(THRESH_TRUNC);
        allowStep_threshold_aaaa.setAllowoperationId(grayOperation.getOperationId());
        allowSteps.add(allowStep_threshold_aaaa);

        AllowStep allowStep_threshold_bbbb = new AllowStep();
        allowStep_threshold_bbbb.setOperation(THRESH_TRUNC);
        allowStep_threshold_bbbb.setAllowoperationId(redOperation.getOperationId());
        allowSteps.add(allowStep_threshold_bbbb);

        AllowStep allowStep_threshold_cccc = new AllowStep();
        allowStep_threshold_cccc.setOperation(THRESH_TRUNC);
        allowStep_threshold_cccc.setAllowoperationId(greenOperation.getOperationId());
        allowSteps.add(allowStep_threshold_cccc);

        AllowStep allowStep_threshold_dddd = new AllowStep();
        allowStep_threshold_dddd.setOperation(THRESH_TRUNC);
        allowStep_threshold_dddd.setAllowoperationId(blueOperation.getOperationId());
        allowSteps.add(allowStep_threshold_dddd);

        AllowStep allowStep_threshold_eeee = new AllowStep();
        allowStep_threshold_eeee.setOperation(THRESH_TRUNC);
        allowStep_threshold_eeee.setAllowoperationId(yOperation.getOperationId());
        allowSteps.add(allowStep_threshold_eeee);

        AllowStep allowStep_threshold_ffff = new AllowStep();
        allowStep_threshold_ffff.setOperation(THRESH_TRUNC);
        allowStep_threshold_ffff.setAllowoperationId(cbOperation.getOperationId());
        allowSteps.add(allowStep_threshold_ffff);

        AllowStep allowStep_threshold_gggg = new AllowStep();
        allowStep_threshold_gggg.setOperation(THRESH_TRUNC);
        allowStep_threshold_gggg.setAllowoperationId(crOperation.getOperationId());
        allowSteps.add(allowStep_threshold_gggg);
        //////////////////////////////////
        AllowStep allowStep_coloring_a = new AllowStep();
        allowStep_coloring_a.setOperation(colorSeg);
        allowStep_coloring_a.setAllowoperationId(THRESH_BINARY.getOperationId());
        allowSteps.add(allowStep_coloring_a);

        AllowStep allowStep_coloring_b = new AllowStep();
        allowStep_coloring_b.setOperation(colorSeg);
        allowStep_coloring_b.setAllowoperationId(THRESH_BINARY_INV.getOperationId());
        allowSteps.add(allowStep_coloring_b);

        AllowStep allowStep_coloring_c = new AllowStep();
        allowStep_coloring_c.setOperation(colorSeg);
        allowStep_coloring_c.setAllowoperationId(THRESH_TOZERO.getOperationId());
        allowSteps.add(allowStep_coloring_c);

        AllowStep allowStep_coloring_d = new AllowStep();
        allowStep_coloring_d.setOperation(colorSeg);
        allowStep_coloring_d.setAllowoperationId(THRESH_TRUNC.getOperationId());
        allowSteps.add(allowStep_coloring_d);

        AllowStep allowStep_coloring_e = new AllowStep();
        allowStep_coloring_e.setOperation(colorSeg);
        allowStep_coloring_e.setAllowoperationId(erode.getOperationId());
        allowSteps.add(allowStep_coloring_e);

        AllowStep allowStep_coloring_f = new AllowStep();
        allowStep_coloring_f.setOperation(colorSeg);
        allowStep_coloring_f.setAllowoperationId(dilate.getOperationId());
        allowSteps.add(allowStep_coloring_f);

        AllowStep allowStep_coloring_g = new AllowStep();
        allowStep_coloring_g.setOperation(colorSeg);
        allowStep_coloring_g.setAllowoperationId(open.getOperationId());
        allowSteps.add(allowStep_coloring_g);

        AllowStep allowStep_coloring_h = new AllowStep();
        allowStep_coloring_h.setOperation(colorSeg);
        allowStep_coloring_h.setAllowoperationId(close.getOperationId());
        allowSteps.add(allowStep_coloring_h);

        //DENOISER avg
        AllowStep noiseReduceAVG_a = new AllowStep();
        noiseReduceAVG_a.setOperation(average);
        noiseReduceAVG_a.setAllowoperationId(redOperation.getOperationId());
        allowSteps.add(noiseReduceAVG_a);

        AllowStep noiseReduceAVG_b = new AllowStep();
        noiseReduceAVG_b.setOperation(average);
        noiseReduceAVG_b.setAllowoperationId(greenOperation.getOperationId());
        allowSteps.add(noiseReduceAVG_b);

        AllowStep noiseReduceAVG_c = new AllowStep();
        noiseReduceAVG_c.setOperation(average);
        noiseReduceAVG_c.setAllowoperationId(blueOperation.getOperationId());
        allowSteps.add(noiseReduceAVG_c);

        AllowStep noiseReduceAVG_d = new AllowStep();
        noiseReduceAVG_d.setOperation(average);
        noiseReduceAVG_d.setAllowoperationId(yOperation.getOperationId());
        allowSteps.add(noiseReduceAVG_d);

        AllowStep noiseReduceAVG_e = new AllowStep();
        noiseReduceAVG_e.setOperation(average);
        noiseReduceAVG_e.setAllowoperationId(cbOperation.getOperationId());
        allowSteps.add(noiseReduceAVG_e);

        AllowStep noiseReduceAVG_f = new AllowStep();
        noiseReduceAVG_f.setOperation(average);
        noiseReduceAVG_f.setAllowoperationId(crOperation.getOperationId());
        allowSteps.add(noiseReduceAVG_f);

        AllowStep noiseReduceAVG_g = new AllowStep();
        noiseReduceAVG_g.setOperation(average);
        noiseReduceAVG_g.setAllowoperationId(originalOperation.getOperationId());
        allowSteps.add(noiseReduceAVG_g);

        AllowStep noiseReduceAVG_h = new AllowStep();
        noiseReduceAVG_h.setOperation(average);
        noiseReduceAVG_h.setAllowoperationId(grayOperation.getOperationId());
        allowSteps.add(noiseReduceAVG_h);

        //noise reducer median

        AllowStep noiseReduceMedian_a = new AllowStep();
        noiseReduceMedian_a.setOperation(median);
        noiseReduceMedian_a.setAllowoperationId(redOperation.getOperationId());
        allowSteps.add(noiseReduceMedian_a);

        AllowStep noiseReduceMedian_b = new AllowStep();
        noiseReduceMedian_b.setOperation(median);
        noiseReduceMedian_b.setAllowoperationId(greenOperation.getOperationId());
        allowSteps.add(noiseReduceMedian_b);

        AllowStep noiseReduceMedian_c = new AllowStep();
        noiseReduceMedian_c.setOperation(median);
        noiseReduceMedian_c.setAllowoperationId(blueOperation.getOperationId());
        allowSteps.add(noiseReduceMedian_c);

        AllowStep noiseReduceMedian_d = new AllowStep();
        noiseReduceMedian_d.setOperation(median);
        noiseReduceMedian_d.setAllowoperationId(yOperation.getOperationId());
        allowSteps.add(noiseReduceMedian_d);

        AllowStep noiseReduceMedian_e = new AllowStep();
        noiseReduceMedian_e.setOperation(median);
        noiseReduceMedian_e.setAllowoperationId(cbOperation.getOperationId());
        allowSteps.add(noiseReduceMedian_e);

        AllowStep noiseReduceMedian_f = new AllowStep();
        noiseReduceMedian_f.setOperation(median);
        noiseReduceMedian_f.setAllowoperationId(crOperation.getOperationId());
        allowSteps.add(noiseReduceMedian_f);

        AllowStep noiseReduceMedian_g = new AllowStep();
        noiseReduceMedian_g.setOperation(median);
        noiseReduceMedian_g.setAllowoperationId(originalOperation.getOperationId());
        allowSteps.add(noiseReduceMedian_g);

        AllowStep noiseReduceMedian_h = new AllowStep();
        noiseReduceMedian_h.setOperation(median);
        noiseReduceMedian_h.setAllowoperationId(grayOperation.getOperationId());
        allowSteps.add(noiseReduceMedian_h);

        //rotating mask reducer median

        AllowStep noiseReduceRotMask_a = new AllowStep();
        noiseReduceRotMask_a.setOperation(median);
        noiseReduceRotMask_a.setAllowoperationId(redOperation.getOperationId());
        allowSteps.add(noiseReduceRotMask_a);

        AllowStep noiseReduceRotMask_b = new AllowStep();
        noiseReduceRotMask_b.setOperation(median);
        noiseReduceRotMask_b.setAllowoperationId(greenOperation.getOperationId());
        allowSteps.add(noiseReduceRotMask_b);

        AllowStep noiseReduceRotMask_c = new AllowStep();
        noiseReduceRotMask_c.setOperation(median);
        noiseReduceRotMask_c.setAllowoperationId(blueOperation.getOperationId());
        allowSteps.add(noiseReduceRotMask_c);

        AllowStep noiseReduceRotMask_d = new AllowStep();
        noiseReduceRotMask_d.setOperation(median);
        noiseReduceRotMask_d.setAllowoperationId(yOperation.getOperationId());
        allowSteps.add(noiseReduceRotMask_d);

        AllowStep noiseReduceRotMask_e = new AllowStep();
        noiseReduceRotMask_e.setOperation(median);
        noiseReduceRotMask_e.setAllowoperationId(cbOperation.getOperationId());
        allowSteps.add(noiseReduceRotMask_e);

        AllowStep noiseReduceRotMask_f = new AllowStep();
        noiseReduceRotMask_f.setOperation(median);
        noiseReduceRotMask_f.setAllowoperationId(crOperation.getOperationId());
        allowSteps.add(noiseReduceRotMask_f);

        AllowStep noiseReduceRotMask_g = new AllowStep();
        noiseReduceRotMask_g.setOperation(median);
        noiseReduceRotMask_g.setAllowoperationId(originalOperation.getOperationId());
        allowSteps.add(noiseReduceRotMask_g);

        AllowStep noiseReduceRotMask_h = new AllowStep();
        noiseReduceRotMask_h.setOperation(median);
        noiseReduceRotMask_h.setAllowoperationId(grayOperation.getOperationId());
        allowSteps.add(noiseReduceRotMask_h);

        //tophat

        AllowStep morphologyTophat_a = new AllowStep();
        morphologyTophat_a.setOperation(topHat);
        morphologyTophat_a.setAllowoperationId(redOperation.getOperationId());
        allowSteps.add(morphologyTophat_a);

        AllowStep morphologyTophat_b = new AllowStep();
        morphologyTophat_b.setOperation(topHat);
        morphologyTophat_b.setAllowoperationId(greenOperation.getOperationId());
        allowSteps.add(noiseReduceRotMask_b);

        AllowStep morphologyTophat_c = new AllowStep();
        morphologyTophat_c.setOperation(topHat);
        morphologyTophat_c.setAllowoperationId(blueOperation.getOperationId());
        allowSteps.add(noiseReduceRotMask_c);

        AllowStep morphologyTophat_d = new AllowStep();
        morphologyTophat_d.setOperation(topHat);
        morphologyTophat_d.setAllowoperationId(yOperation.getOperationId());
        allowSteps.add(morphologyTophat_d);

        AllowStep morphologyTophat_e = new AllowStep();
        morphologyTophat_e.setOperation(topHat);
        morphologyTophat_e.setAllowoperationId(cbOperation.getOperationId());
        allowSteps.add(morphologyTophat_e);

        AllowStep morphologyTophat_f = new AllowStep();
        morphologyTophat_f.setOperation(topHat);
        morphologyTophat_f.setAllowoperationId(crOperation.getOperationId());
        allowSteps.add(morphologyTophat_f);

        AllowStep morphologyTophat_g = new AllowStep();
        morphologyTophat_g.setOperation(topHat);
        morphologyTophat_g.setAllowoperationId(originalOperation.getOperationId());
        allowSteps.add(morphologyTophat_g);

        AllowStep morphologyTophat_h = new AllowStep();
        morphologyTophat_h.setOperation(topHat);
        morphologyTophat_h.setAllowoperationId(grayOperation.getOperationId());
        allowSteps.add(morphologyTophat_h);

        //binary

        AllowStep morphologyErode_a = new AllowStep();
        morphologyErode_a.setOperation(erode);
        morphologyErode_a.setAllowoperationId(THRESH_BINARY.getOperationId());
        allowSteps.add(morphologyErode_a);

        AllowStep morphologyErode_b = new AllowStep();
        morphologyErode_b.setOperation(erode);
        morphologyErode_b.setAllowoperationId(THRESH_BINARY_INV.getOperationId());
        allowSteps.add(morphologyErode_a);

        AllowStep morphologyErode_c = new AllowStep();
        morphologyErode_c.setOperation(erode);
        morphologyErode_c.setAllowoperationId(THRESH_TOZERO.getOperationId());
        allowSteps.add(morphologyErode_c);

        AllowStep morphologyErode_d = new AllowStep();
        morphologyErode_d.setOperation(erode);
        morphologyErode_d.setAllowoperationId(THRESH_TRUNC.getOperationId());
        allowSteps.add(morphologyErode_d);

        AllowStep morphologyDilate_a = new AllowStep();
        morphologyDilate_a.setOperation(dilate);
        morphologyDilate_a.setAllowoperationId(THRESH_BINARY.getOperationId());
        allowSteps.add(morphologyDilate_a);

        AllowStep morphologyDilate_b = new AllowStep();
        morphologyDilate_b.setOperation(dilate);
        morphologyDilate_b.setAllowoperationId(THRESH_BINARY_INV.getOperationId());
        allowSteps.add(morphologyDilate_b);

        AllowStep morphologyDilate_c = new AllowStep();
        morphologyDilate_c.setOperation(dilate);
        morphologyDilate_c.setAllowoperationId(THRESH_TOZERO.getOperationId());
        allowSteps.add(morphologyDilate_c);

        AllowStep morphologyDilate_d = new AllowStep();
        morphologyDilate_d.setOperation(dilate);
        morphologyDilate_d.setAllowoperationId(THRESH_TRUNC.getOperationId());
        allowSteps.add(morphologyDilate_d);

        AllowStep morphologyOpen_a = new AllowStep();
        morphologyOpen_a.setOperation(open);
        morphologyOpen_a.setAllowoperationId(THRESH_BINARY.getOperationId());
        allowSteps.add(morphologyOpen_a);

        AllowStep morphologyOpen_b = new AllowStep();
        morphologyOpen_b.setOperation(open);
        morphologyOpen_b.setAllowoperationId(THRESH_BINARY_INV.getOperationId());
        allowSteps.add(morphologyOpen_b);

        AllowStep morphologyOpen_c = new AllowStep();
        morphologyOpen_c.setOperation(open);
        morphologyOpen_c.setAllowoperationId(THRESH_TOZERO.getOperationId());
        allowSteps.add(morphologyOpen_c);

        AllowStep morphologyOpen_d = new AllowStep();
        morphologyOpen_d.setOperation(open);
        morphologyOpen_d.setAllowoperationId(THRESH_TRUNC.getOperationId());
        allowSteps.add(morphologyOpen_d);

        AllowStep morphologyClose_a = new AllowStep();
        morphologyClose_a.setOperation(close);
        morphologyClose_a.setAllowoperationId(THRESH_BINARY.getOperationId());
        allowSteps.add(morphologyClose_a);

        AllowStep morphologyClose_b = new AllowStep();
        morphologyClose_b.setOperation(close);
        morphologyClose_b.setAllowoperationId(THRESH_BINARY_INV.getOperationId());
        allowSteps.add(morphologyClose_b);

        AllowStep morphologyClose_c = new AllowStep();
        morphologyClose_c.setOperation(close);
        morphologyClose_c.setAllowoperationId(THRESH_TOZERO.getOperationId());
        allowSteps.add(morphologyClose_c);

        AllowStep morphologyClose_d = new AllowStep();
        morphologyClose_d.setOperation(close);
        morphologyClose_d.setAllowoperationId(THRESH_TRUNC.getOperationId());
        allowSteps.add(morphologyClose_d);


        AllowStep allowResize = new AllowStep();
        allowResize.setOperation(resizing);
        allowResize.setAllowoperationId(originalOperation.getOperationId());
        allowSteps.add(allowResize);

        AllowStep allowRotate = new AllowStep();
        allowRotate.setOperation(rotating);
        allowRotate.setAllowoperationId(originalOperation.getOperationId());
        allowSteps.add(allowRotate);

        AllowStep allowHTC_A = new AllowStep();
        allowHTC_A.setOperation(HTC);
        allowHTC_A.setAllowoperationId(laplace.getOperationId());
        allowSteps.add(allowHTC_A);

        AllowStep allowHTC_B = new AllowStep();
        allowHTC_B.setOperation(HTC);
        allowHTC_B.setAllowoperationId(sobel.getOperationId());
        allowSteps.add(allowHTC_B);

        AllowStep allowHTC_C = new AllowStep();
        allowHTC_C.setOperation(HTC);
        allowHTC_C.setAllowoperationId(canny.getOperationId());
        allowSteps.add(allowHTC_C);

        AllowStep allowHTL_A = new AllowStep();
        allowHTL_A.setOperation(HTL);
        allowHTL_A.setAllowoperationId(laplace.getOperationId());
        allowSteps.add(allowHTL_A);

        AllowStep allowHTL_B = new AllowStep();
        allowHTL_B.setOperation(HTL);
        allowHTL_B.setAllowoperationId(sobel.getOperationId());
        allowSteps.add(allowHTL_B);

        AllowStep allowHTL_C = new AllowStep();
        allowHTL_C.setOperation(HTL);
        allowHTL_C.setAllowoperationId(canny.getOperationId());
        allowSteps.add(allowHTL_C);

        AllowStep allowStep_tmpl_A = new AllowStep();
        allowStep_tmpl_A.setOperation(tempMatching);
        allowStep_tmpl_A.setAllowoperationId(redOperation.getOperationId());
        allowSteps.add(allowStep_tmpl_A);

        AllowStep allowStep_tmpl_B = new AllowStep();
        allowStep_tmpl_B.setOperation(tempMatching);
        allowStep_tmpl_B.setAllowoperationId(greenOperation.getOperationId());
        allowSteps.add(allowStep_tmpl_B);

        AllowStep allowStep_tmpl_C = new AllowStep();
        allowStep_tmpl_C.setOperation(tempMatching);
        allowStep_tmpl_C.setAllowoperationId(blueOperation.getOperationId());
        allowSteps.add(allowStep_tmpl_C);

        AllowStep allowStep_tmpl_D = new AllowStep();
        allowStep_tmpl_D.setOperation(tempMatching);
        allowStep_tmpl_D.setAllowoperationId(grayOperation.getOperationId());
        allowSteps.add(allowStep_tmpl_D);

        AllowStep allowStep_tmpl_E = new AllowStep();
        allowStep_tmpl_E.setOperation(tempMatching);
        allowStep_tmpl_E.setAllowoperationId(yOperation.getOperationId());
        allowSteps.add(allowStep_tmpl_E);

        AllowStep allowStep_tmpl_F = new AllowStep();
        allowStep_tmpl_F.setOperation(tempMatching);
        allowStep_tmpl_F.setAllowoperationId(cbOperation.getOperationId());
        allowSteps.add(allowStep_tmpl_F);

        AllowStep allowStep_tmpl_G = new AllowStep();
        allowStep_tmpl_G.setOperation(tempMatching);
        allowStep_tmpl_G.setAllowoperationId(crOperation.getOperationId());
        allowSteps.add(allowStep_tmpl_G);

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
