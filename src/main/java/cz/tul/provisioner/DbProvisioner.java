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
        Set<AllowStep> grayScale = new HashSet<>();
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
        sobel.setName("Sobell");
        sobel.setMethod(fDerivation);
        operations.add(sobel);

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

        operationRegister.register(sobel.getOperationId(), EdgeDetector.class, EdgeDetectorEnum.SOBEL.getDetectorlName());
        operationRegister.register(laplace.getOperationId(), EdgeDetector.class, EdgeDetectorEnum.LAPLACIAN.getDetectorlName());

        operationRegister.register(average.getOperationId(), NoiseReducer.class, NoiseReducerEnum.SIMPLEAVERAGING.getReducerName());
        operationRegister.register(median.getOperationId(), NoiseReducer.class, NoiseReducerEnum.MEDIAN.getReducerName());
        operationRegister.register(rotateMask.getOperationId(), NoiseReducer.class, NoiseReducerEnum.ROTATINGMASK.getReducerName());

        operationRegister.register(THRESH_BINARY.getOperationId(), Segmentation.class, SegmentorEnum.THRESH_BINARY.getSegmentorName());
        operationRegister.register(THRESH_BINARY_INV.getOperationId(), Segmentation.class, SegmentorEnum.THRESH_BINARY_INV.getSegmentorName());
        operationRegister.register(THRESH_TOZERO.getOperationId(), Segmentation.class, SegmentorEnum.THRESH_TOZERO.getSegmentorName());
        operationRegister.register(THRESH_TRUNC.getOperationId(), Segmentation.class, SegmentorEnum.THRESH_TRUNC.getSegmentorName());
        operationRegister.register(colorSeg.getOperationId(), Segmentation.class, SegmentorEnum.COLORING.getSegmentorName());

        operationRegister.register(erode.getOperationId(), Morphology.class, MorphologyEnum.ERODE.getMorphologyName());
        operationRegister.register(dilate.getOperationId(), Morphology.class, MorphologyEnum.DILATE.getMorphologyName());
        operationRegister.register(open.getOperationId(), Morphology.class, MorphologyEnum.OPEN.getMorphologyName());
        operationRegister.register(close.getOperationId(), Morphology.class, MorphologyEnum.CLOSE.getMorphologyName());
        operationRegister.register(topHat.getOperationId(), Morphology.class, MorphologyEnum.TOPHAT.getMorphologyName());


        Attribute step = new Attribute("Krok");
        Attribute shape = new Attribute("Tvar");
        Attribute size = new Attribute("Velikost");
        Attribute inputImg = new Attribute("Vstupní obraz");
        Attribute channel = new Attribute("Vrstva");
        Attribute segmentor = new Attribute("Segmentor");
        Attribute type = new Attribute("Typ");
        Attribute threshold = new Attribute("Práh");

        attributes.add(type);
        attributes.add(channel);
        attributes.add(step);
        attributes.add(shape);
        attributes.add(size);
        attributes.add(inputImg);
        attributes.add(threshold);
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
        erodeM_B.setDefaultValues("rectangle");
        erodeM_B.setOperation(erode);
        erodeM_B.setAttribute(shape);
        erodeM_B.setAttributeType(AttributeType.SELECT);
        erodeM_B.setOptions(shapes);
        operationAttributes.add(erodeM_B);

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
        operationDAO.save(operationAttributes);


        AllowStep allowStep_org = new AllowStep();
        allowStep_org.setOperation(originalOperation);
        allowStep_org.setAllowoperationId(null);
        grayScale.add(allowStep_org);

        AllowStep allowStep_R = new AllowStep();
        allowStep_R.setOperation(redOperation);
        allowStep_R.setAllowoperationId(originalOperation.getOperationId());
        grayScale.add(allowStep_R);

        AllowStep allowStep_G = new AllowStep();
        allowStep_G.setOperation(greenOperation);
        allowStep_G.setAllowoperationId(originalOperation.getOperationId());
        grayScale.add(allowStep_G);

        AllowStep allowStep_B = new AllowStep();
        allowStep_B.setOperation(blueOperation);
        allowStep_B.setAllowoperationId(originalOperation.getOperationId());
        grayScale.add(allowStep_B);

        AllowStep allowStep_GRAY = new AllowStep();
        allowStep_GRAY.setOperation(grayOperation);
        allowStep_GRAY.setAllowoperationId(originalOperation.getOperationId());
        grayScale.add(allowStep_GRAY);

        AllowStep allowStep_Y = new AllowStep();
        allowStep_Y.setOperation(yOperation);
        allowStep_Y.setAllowoperationId(originalOperation.getOperationId());
        grayScale.add(allowStep_Y);

        AllowStep allowStep_CB = new AllowStep();
        allowStep_CB.setOperation(cbOperation);
        allowStep_CB.setAllowoperationId(originalOperation.getOperationId());
        grayScale.add(allowStep_CB);

        AllowStep allowStep_CR = new AllowStep();
        allowStep_CR.setOperation(crOperation);
        allowStep_CR.setAllowoperationId(originalOperation.getOperationId());
        grayScale.add(allowStep_CR);

        AllowStep allowStep_H = new AllowStep();
        allowStep_H.setOperation(hOperation);
        allowStep_H.setAllowoperationId(originalOperation.getOperationId());
        grayScale.add(allowStep_H);

        AllowStep allowStep_sobel_a = new AllowStep();
        allowStep_sobel_a.setOperation(sobel);
        allowStep_sobel_a.setAllowoperationId(grayOperation.getOperationId());
        grayScale.add(allowStep_sobel_a);

        AllowStep allowStep_sobel_b = new AllowStep();
        allowStep_sobel_b.setOperation(sobel);
        allowStep_sobel_b.setAllowoperationId(redOperation.getOperationId());
        grayScale.add(allowStep_sobel_b);

        AllowStep allowStep_sobel_c = new AllowStep();
        allowStep_sobel_c.setOperation(sobel);
        allowStep_sobel_c.setAllowoperationId(greenOperation.getOperationId());
        grayScale.add(allowStep_sobel_c);

        AllowStep allowStep_sobel_d = new AllowStep();
        allowStep_sobel_d.setOperation(sobel);
        allowStep_sobel_d.setAllowoperationId(blueOperation.getOperationId());
        grayScale.add(allowStep_sobel_d);

        AllowStep allowStep_sobel_e = new AllowStep();
        allowStep_sobel_e.setOperation(sobel);
        allowStep_sobel_e.setAllowoperationId(yOperation.getOperationId());
        grayScale.add(allowStep_sobel_e);

        AllowStep allowStep_sobel_f = new AllowStep();
        allowStep_sobel_f.setOperation(sobel);
        allowStep_sobel_f.setAllowoperationId(cbOperation.getOperationId());
        grayScale.add(allowStep_sobel_f);

        AllowStep allowStep_sobel_g = new AllowStep();
        allowStep_sobel_g.setOperation(sobel);
        allowStep_sobel_g.setAllowoperationId(crOperation.getOperationId());
        grayScale.add(allowStep_sobel_g);
        ////////////////////////////////////
        AllowStep allowStep_laplace_a = new AllowStep();
        allowStep_laplace_a.setOperation(laplace);
        allowStep_laplace_a.setAllowoperationId(grayOperation.getOperationId());
        grayScale.add(allowStep_laplace_a);

        AllowStep allowStep_laplace_b = new AllowStep();
        allowStep_laplace_b.setOperation(laplace);
        allowStep_laplace_b.setAllowoperationId(redOperation.getOperationId());
        grayScale.add(allowStep_laplace_b);

        AllowStep allowStep_laplace_c = new AllowStep();
        allowStep_laplace_c.setOperation(laplace);
        allowStep_laplace_c.setAllowoperationId(greenOperation.getOperationId());
        grayScale.add(allowStep_laplace_c);

        AllowStep allowStep_laplace_d = new AllowStep();
        allowStep_laplace_d.setOperation(laplace);
        allowStep_laplace_d.setAllowoperationId(blueOperation.getOperationId());
        grayScale.add(allowStep_laplace_d);

        AllowStep allowStep_laplace_e = new AllowStep();
        allowStep_laplace_e.setOperation(laplace);
        allowStep_laplace_e.setAllowoperationId(yOperation.getOperationId());
        grayScale.add(allowStep_laplace_e);

        AllowStep allowStep_laplace_f = new AllowStep();
        allowStep_laplace_f.setOperation(laplace);
        allowStep_laplace_f.setAllowoperationId(cbOperation.getOperationId());
        grayScale.add(allowStep_laplace_f);

        AllowStep allowStep_laplace_g = new AllowStep();
        allowStep_laplace_g.setOperation(laplace);
        allowStep_laplace_g.setAllowoperationId(crOperation.getOperationId());
        grayScale.add(allowStep_laplace_g);


        ////////////////////////////////////

        AllowStep allowStep_threshold_b = new AllowStep();
        allowStep_threshold_b.setOperation(THRESH_BINARY);
        allowStep_threshold_b.setAllowoperationId(redOperation.getOperationId());
        grayScale.add(allowStep_threshold_b);

        AllowStep allowStep_threshold_c = new AllowStep();
        allowStep_threshold_c.setOperation(THRESH_BINARY);
        allowStep_threshold_c.setAllowoperationId(greenOperation.getOperationId());
        grayScale.add(allowStep_threshold_c);

        AllowStep allowStep_threshold_d = new AllowStep();
        allowStep_threshold_d.setOperation(THRESH_BINARY);
        allowStep_threshold_d.setAllowoperationId(blueOperation.getOperationId());
        grayScale.add(allowStep_threshold_d);

        AllowStep allowStep_threshold_e = new AllowStep();
        allowStep_threshold_e.setOperation(THRESH_BINARY);
        allowStep_threshold_e.setAllowoperationId(yOperation.getOperationId());
        grayScale.add(allowStep_threshold_e);

        AllowStep allowStep_threshold_f = new AllowStep();
        allowStep_threshold_f.setOperation(THRESH_BINARY);
        allowStep_threshold_f.setAllowoperationId(cbOperation.getOperationId());
        grayScale.add(allowStep_threshold_f);
        AllowStep allowStep_threshold_a = new AllowStep();

        allowStep_threshold_a.setOperation(THRESH_BINARY);
        allowStep_threshold_a.setAllowoperationId(crOperation.getOperationId());
        grayScale.add(allowStep_threshold_a);

        AllowStep allowStep_threshold_g = new AllowStep();
        allowStep_threshold_g.setOperation(THRESH_BINARY);
        allowStep_threshold_g.setAllowoperationId(grayOperation.getOperationId());
        grayScale.add(allowStep_threshold_g);

        //
        AllowStep allowStep_threshold_aa = new AllowStep();
        allowStep_threshold_aa.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_aa.setAllowoperationId(crOperation.getOperationId());
        grayScale.add(allowStep_threshold_aa);

        AllowStep allowStep_threshold_bb = new AllowStep();
        allowStep_threshold_bb.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_bb.setAllowoperationId(redOperation.getOperationId());
        grayScale.add(allowStep_threshold_bb);

        AllowStep allowStep_threshold_cc = new AllowStep();
        allowStep_threshold_cc.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_cc.setAllowoperationId(greenOperation.getOperationId());
        grayScale.add(allowStep_threshold_cc);

        AllowStep allowStep_threshold_dd = new AllowStep();
        allowStep_threshold_dd.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_dd.setAllowoperationId(blueOperation.getOperationId());
        grayScale.add(allowStep_threshold_dd);

        AllowStep allowStep_threshold_ee = new AllowStep();
        allowStep_threshold_ee.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_ee.setAllowoperationId(yOperation.getOperationId());
        grayScale.add(allowStep_threshold_ee);

        AllowStep allowStep_threshold_ff = new AllowStep();
        allowStep_threshold_ff.setOperation(THRESH_BINARY_INV);
        allowStep_threshold_ff.setAllowoperationId(cbOperation.getOperationId());
        grayScale.add(allowStep_threshold_ff);

        AllowStep allowStep_threshold_gg = new AllowStep();
        allowStep_threshold_gg.setOperation(THRESH_TOZERO);
        allowStep_threshold_gg.setAllowoperationId(grayOperation.getOperationId());
        grayScale.add(allowStep_threshold_gg);
        //
        AllowStep allowStep_threshold_aaa = new AllowStep();
        allowStep_threshold_aaa.setOperation(THRESH_TOZERO);
        allowStep_threshold_aaa.setAllowoperationId(grayOperation.getOperationId());
        grayScale.add(allowStep_threshold_aaa);

        AllowStep allowStep_threshold_bbb = new AllowStep();
        allowStep_threshold_bbb.setOperation(THRESH_TOZERO);
        allowStep_threshold_bbb.setAllowoperationId(redOperation.getOperationId());
        grayScale.add(allowStep_threshold_bbb);

        AllowStep allowStep_threshold_ccc = new AllowStep();
        allowStep_threshold_ccc.setOperation(THRESH_TOZERO);
        allowStep_threshold_ccc.setAllowoperationId(greenOperation.getOperationId());
        grayScale.add(allowStep_threshold_ccc);

        AllowStep allowStep_threshold_ddd = new AllowStep();
        allowStep_threshold_ddd.setOperation(THRESH_TOZERO);
        allowStep_threshold_ddd.setAllowoperationId(blueOperation.getOperationId());
        grayScale.add(allowStep_threshold_ddd);

        AllowStep allowStep_threshold_eee = new AllowStep();
        allowStep_threshold_eee.setOperation(THRESH_TOZERO);
        allowStep_threshold_eee.setAllowoperationId(yOperation.getOperationId());
        grayScale.add(allowStep_threshold_eee);

        AllowStep allowStep_threshold_fff = new AllowStep();
        allowStep_threshold_fff.setOperation(THRESH_TOZERO);
        allowStep_threshold_fff.setAllowoperationId(cbOperation.getOperationId());
        grayScale.add(allowStep_threshold_fff);

        AllowStep allowStep_threshold_ggg = new AllowStep();
        allowStep_threshold_ggg.setOperation(THRESH_TOZERO);
        allowStep_threshold_ggg.setAllowoperationId(crOperation.getOperationId());
        grayScale.add(allowStep_threshold_ggg);
////////////
        AllowStep allowStep_threshold_aaaa = new AllowStep();
        allowStep_threshold_aaaa.setOperation(THRESH_TRUNC);
        allowStep_threshold_aaaa.setAllowoperationId(grayOperation.getOperationId());
        grayScale.add(allowStep_threshold_aaaa);

        AllowStep allowStep_threshold_bbbb = new AllowStep();
        allowStep_threshold_bbbb.setOperation(THRESH_TRUNC);
        allowStep_threshold_bbbb.setAllowoperationId(redOperation.getOperationId());
        grayScale.add(allowStep_threshold_bbbb);

        AllowStep allowStep_threshold_cccc = new AllowStep();
        allowStep_threshold_cccc.setOperation(THRESH_TRUNC);
        allowStep_threshold_cccc.setAllowoperationId(greenOperation.getOperationId());
        grayScale.add(allowStep_threshold_cccc);

        AllowStep allowStep_threshold_dddd = new AllowStep();
        allowStep_threshold_dddd.setOperation(THRESH_TRUNC);
        allowStep_threshold_dddd.setAllowoperationId(blueOperation.getOperationId());
        grayScale.add(allowStep_threshold_dddd);

        AllowStep allowStep_threshold_eeee = new AllowStep();
        allowStep_threshold_eeee.setOperation(THRESH_TRUNC);
        allowStep_threshold_eeee.setAllowoperationId(yOperation.getOperationId());
        grayScale.add(allowStep_threshold_eeee);

        AllowStep allowStep_threshold_ffff = new AllowStep();
        allowStep_threshold_ffff.setOperation(THRESH_TRUNC);
        allowStep_threshold_ffff.setAllowoperationId(cbOperation.getOperationId());
        grayScale.add(allowStep_threshold_ffff);

        AllowStep allowStep_threshold_gggg = new AllowStep();
        allowStep_threshold_gggg.setOperation(THRESH_TRUNC);
        allowStep_threshold_gggg.setAllowoperationId(crOperation.getOperationId());
        grayScale.add(allowStep_threshold_gggg);
        //////////////////////////////////
        AllowStep allowStep_coloring_a = new AllowStep();
        allowStep_coloring_a.setOperation(colorSeg);
        allowStep_coloring_a.setAllowoperationId(THRESH_BINARY.getOperationId());
        grayScale.add(allowStep_coloring_a);

        AllowStep allowStep_coloring_b = new AllowStep();
        allowStep_coloring_b.setOperation(colorSeg);
        allowStep_coloring_b.setAllowoperationId(THRESH_BINARY_INV.getOperationId());
        grayScale.add(allowStep_coloring_b);

        AllowStep allowStep_coloring_c = new AllowStep();
        allowStep_coloring_c.setOperation(colorSeg);
        allowStep_coloring_c.setAllowoperationId(THRESH_TOZERO.getOperationId());
        grayScale.add(allowStep_coloring_c);

        AllowStep allowStep_coloring_d = new AllowStep();
        allowStep_coloring_d.setOperation(colorSeg);
        allowStep_coloring_d.setAllowoperationId(THRESH_TRUNC.getOperationId());
        grayScale.add(allowStep_coloring_d);

        allowStepDAO.save(grayScale);
    }

/*
    private void registerMethodAtributes(Set<MethodAttributes> input) {
        for (MethodAttributes ma : input) {
            try {
                DataHolder dh = new DataHolder();
                dh.setName(ma.getAttribute().getName());
                dh.setOptions(ma.getOptions());
                dh.setType(ma.getAttributeType());
                methodAttributeRegister.register(ma.getMethodAttributesId(), dh);
            } catch (IllegalInputException e) {
                e.printStackTrace();
            }
        }
    }*/

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
