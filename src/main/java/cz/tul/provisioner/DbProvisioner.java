package cz.tul.provisioner;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import cz.tul.bussiness.register.MethodAttributeRegister;
import cz.tul.bussiness.register.MethodRegister;
import cz.tul.bussiness.register.exceptions.IllegalInputException;
import cz.tul.bussiness.workers.*;
import cz.tul.bussiness.workers.enums.ChannelsEnum;
import cz.tul.bussiness.workers.enums.EdgeDetectorEnum;
import cz.tul.bussiness.workers.enums.NoiseReducerEnum;
import cz.tul.bussiness.workers.enums.SegmentorEnum;
import cz.tul.entities.*;
import cz.tul.provisioner.holder.DataHolder;
import cz.tul.repositories.*;
import org.opencv.imgproc.Imgproc;
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
    ChainDAO chainDAO;
    @Autowired
    MethodDAO methodDAO;
    @Autowired
    PartDAO partDAO;
    @Autowired
    MethodAttributesDAO methodAttributesDAO;
    @Autowired
    PartAttributeValueDAO partAttributeValueDAO;
    @Autowired
    ServletContext servletContext;


    private MethodRegister methodRegister = MethodRegister.getInstance();
    private MethodAttributeRegister methodAttributeRegister = MethodAttributeRegister.getInstance();

    @Override
    public void afterPropertiesSet() throws Exception {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);

        String test = servletContext.getRealPath("/");
        methodRegister.registerContextPath(test);

        Set<Method> methods = new HashSet<>();
        Set<AllowStep> grayScale = new HashSet<>();
        Set<MethodAttributes> methodAttributes = new HashSet<>();
        Set<Attribute> attributes = new HashSet<>();

        // RGB funkce
        Method RGB = new Method();
        Method original = new Method();
        // YCBCR funkce
        Method YCBCR = new Method();
        // HSV
        Method HSV = new Method();
        //EDGE DETECTORS
        Method detectors = new Method();
        //NOISE
        Method noise = new Method();
        //SEGMENTATION
        Method segmentation = new Method();

        //MORPOHOLOGY
      /*  Method erode = new Method();
        Method dilate = new Method();
        Method open = new Method();
        Method close = new Method();*/


        // inicializace základních metoda ulozeni

        RGB.setName("RGB");
        YCBCR.setName("YCBCR");
        HSV.setName("HSV");
        original.setName("original");
        detectors.setName("Edge Detectors");
        noise.setName("Noise reducer");
        segmentation.setName("Segmentation");


        //ulozeni metod do databaze
        methods.add(RGB);
        methods.add(YCBCR);
        methods.add(HSV);
        methods.add(detectors);
        methods.add(noise);
        methods.add(segmentation);
        methods.add(original);

        methodDAO.save(methods);
        //test

        methodRegister.register(original.getMethodId(), OriginalRGB.class);
        methodRegister.register(RGB.getMethodId(), RGBChannel.class);
        methodRegister.register(YCBCR.getMethodId(), YCBCRChannel.class);
        methodRegister.register(HSV.getMethodId(), HSVChannel.class);
        methodRegister.register(detectors.getMethodId(), EdgeDetector.class);
        methodRegister.register(noise.getMethodId(), NoiseReducer.class);
        methodRegister.register(segmentation.getMethodId(), Segmentation.class);


        //RGB
        MethodAttributes rgbMethodAttributes = new MethodAttributes();
        //YCBCR
        MethodAttributes ycbcrMethodAttributes = new MethodAttributes();
        //HSV
        MethodAttributes hsvMethodAttributes = new MethodAttributes();
        //BASIC
        MethodAttributes originalMethodAttributes = new MethodAttributes();
        //EDGEDETECTORS
        MethodAttributes detectorsMethodAttributes = new MethodAttributes();
        //NOSISEDETOCTOR
        MethodAttributes noiseMethodAttributes = new MethodAttributes();
        //SEGMENTATION
        MethodAttributes thresholdMethodAttributes = new MethodAttributes();
        MethodAttributes typeMethodAttributes = new MethodAttributes();
        MethodAttributes segmentorMethodAttributes = new MethodAttributes();

/*
        //MORPHOLOGY
        MethodAttributes erodeAttributesAtr1 = new MethodAttributes();
        MethodAttributes dilateAttributesAtr1 = new MethodAttributes();
        MethodAttributes openAttributesAtr1 = new MethodAttributes();
        MethodAttributes closeAttributesAtr1 = new MethodAttributes();
        MethodAttributes erodeAttributesAtr2 = new MethodAttributes();
        MethodAttributes dilateAttributesAtr2 = new MethodAttributes();
        MethodAttributes openAttributesAtr2 = new MethodAttributes();
        MethodAttributes closeAttributesAtr2 = new MethodAttributes();*/

        //inicializace
        rgbMethodAttributes.setMethod(RGB);
        ycbcrMethodAttributes.setMethod(YCBCR);
        hsvMethodAttributes.setMethod(HSV);
        originalMethodAttributes.setMethod(original);
        detectorsMethodAttributes.setMethod(detectors);
        noiseMethodAttributes.setMethod(noise);
        //
        thresholdMethodAttributes.setMethod(segmentation);
        typeMethodAttributes.setMethod(segmentation);
        segmentorMethodAttributes.setMethod(segmentation);


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


        originalMethodAttributes.setAttribute(inputImg);
        originalMethodAttributes.setAttributeType(AttributeType.TEXT);

        Map<String, String> shapes = new HashMap<>();
        shapes.put("oval", "Oval");
        shapes.put("circle", "Kruh");
        shapes.put("square", "Čtverec");
        shapes.put("rectangle", "Obdelník");

        Map<String, String> RGBChannels = new HashMap<>();
        RGBChannels.put(ChannelsEnum.RED.getChannelName(), ChannelsEnum.RED.getChannelName());
        RGBChannels.put(ChannelsEnum.GREEN.getChannelName(), ChannelsEnum.GREEN.getChannelName());
        RGBChannels.put(ChannelsEnum.BLUE.getChannelName(), ChannelsEnum.BLUE.getChannelName());
        RGBChannels.put(ChannelsEnum.GRAY.getChannelName(), ChannelsEnum.GRAY.getChannelName());
        rgbMethodAttributes.setAttribute(channel);
        rgbMethodAttributes.setAttributeType(AttributeType.SELECT);
        rgbMethodAttributes.setOptions(RGBChannels);
        rgbMethodAttributes.setMinValue(0);
        rgbMethodAttributes.setMaxValue(255);


        Map<String, String> YCBCRChannels = new HashMap<>();
        YCBCRChannels.put(ChannelsEnum.Y.getChannelName(), ChannelsEnum.Y.getChannelName());
        YCBCRChannels.put(ChannelsEnum.CB.getChannelName(), ChannelsEnum.CB.getChannelName());
        YCBCRChannels.put(ChannelsEnum.CR.getChannelName(), ChannelsEnum.CR.getChannelName());
        ycbcrMethodAttributes.setAttribute(channel);
        ycbcrMethodAttributes.setAttributeType(AttributeType.SELECT);
        ycbcrMethodAttributes.setOptions(YCBCRChannels);

        Map<String, String> HSVChannels = new HashMap<>();
        HSVChannels.put(ChannelsEnum.H.getChannelName(), ChannelsEnum.H.getChannelName());
        hsvMethodAttributes.setAttribute(channel);
        hsvMethodAttributes.setAttributeType(AttributeType.SELECT);
        hsvMethodAttributes.setOptions(HSVChannels);

        Map<String, String> detectorTypes = new HashMap<>();
        detectorTypes.put(EdgeDetectorEnum.LAPLACIAN.getDetectorlName(), EdgeDetectorEnum.LAPLACIAN.getDetectorlName());
        detectorTypes.put(EdgeDetectorEnum.SOBEL.getDetectorlName(), EdgeDetectorEnum.SOBEL.getDetectorlName());
        detectorsMethodAttributes.setAttribute(type);
        detectorsMethodAttributes.setAttributeType(AttributeType.SELECT);
        detectorsMethodAttributes.setOptions(detectorTypes);

        Map<String, String> noiseReducerTypes = new HashMap<>();
        noiseReducerTypes.put(NoiseReducerEnum.MEDIAN.getReducerName(), NoiseReducerEnum.MEDIAN.getReducerName());
        noiseReducerTypes.put(NoiseReducerEnum.SIMPLEAVERAGING.getReducerName(), NoiseReducerEnum.SIMPLEAVERAGING.getReducerName());
        noiseReducerTypes.put(NoiseReducerEnum.ROTATINGMASK.getReducerName(), NoiseReducerEnum.ROTATINGMASK.getReducerName());
        noiseMethodAttributes.setAttribute(type);
        noiseMethodAttributes.setAttributeType(AttributeType.SELECT);
        noiseMethodAttributes.setOptions(noiseReducerTypes);

        thresholdMethodAttributes.setAttribute(threshold);
        thresholdMethodAttributes.setAttributeType(AttributeType.NUMBER);


        Map<String, String> tresholdTypes = new HashMap<>();
        tresholdTypes.put("" + Imgproc.THRESH_BINARY, "THRESH_BINARY");
        tresholdTypes.put("" + Imgproc.THRESH_BINARY_INV, "THRESH_BINARY_INV");
        tresholdTypes.put("" + Imgproc.THRESH_TRUNC, "THRESH_TRUNC");
        tresholdTypes.put("" + Imgproc.THRESH_TOZERO, "THRESH_TOZERO");
        typeMethodAttributes.setAttribute(type);
        typeMethodAttributes.setAttributeType(AttributeType.SELECT);
        typeMethodAttributes.setOptions(tresholdTypes);


        Map<String, String> segmentorTypes = new HashMap<>();
        segmentorTypes.put(SegmentorEnum.TRESHHOLDING.getSegmentorName(), SegmentorEnum.TRESHHOLDING.getSegmentorName());
        segmentorTypes.put(SegmentorEnum.COLORING.getSegmentorName(), SegmentorEnum.COLORING.getSegmentorName());
        segmentorMethodAttributes.setAttribute(segmentor);
        segmentorMethodAttributes.setAttributeType(AttributeType.SELECT);
        segmentorMethodAttributes.setOptions(segmentorTypes);


        methodAttributes.add(rgbMethodAttributes);
        methodAttributes.add(ycbcrMethodAttributes);
        methodAttributes.add(hsvMethodAttributes);
        methodAttributes.add(originalMethodAttributes);
        methodAttributes.add(detectorsMethodAttributes);
        methodAttributes.add(noiseMethodAttributes);
        methodAttributes.add(thresholdMethodAttributes);
        methodAttributes.add(typeMethodAttributes);
        methodAttributes.add(segmentorMethodAttributes);
        registerMethodAtributes(methodAttributes);
        methodAttributesDAO.save(methodAttributes);

        AllowStep allowStep_org = new AllowStep(ChannelsEnum.ORIGINAL.getChannelName());
        allowStep_org.setMethod(original.getMethodId());
        allowStep_org.setAllowStep(null);
        grayScale.add(allowStep_org);

        AllowStep allowStep_R = new AllowStep(ChannelsEnum.RED.getChannelName());
        allowStep_R.setMethod(RGB.getMethodId());
        allowStep_R.setAllowStep(original.getMethodId());
        grayScale.add(allowStep_R);

        AllowStep allowStep_G = new AllowStep(ChannelsEnum.GREEN.getChannelName());
        allowStep_G.setMethod(RGB.getMethodId());
        allowStep_G.setAllowStep(original.getMethodId());
        grayScale.add(allowStep_G);

        AllowStep allowStep_B = new AllowStep(ChannelsEnum.BLUE.getChannelName());
        allowStep_B.setMethod(RGB.getMethodId());
        allowStep_B.setAllowStep(original.getMethodId());
        grayScale.add(allowStep_B);

        AllowStep allowStep_GRAY = new AllowStep(ChannelsEnum.GRAY.getChannelName());
        allowStep_GRAY.setMethod(RGB.getMethodId());
        allowStep_GRAY.setAllowStep(original.getMethodId());
        grayScale.add(allowStep_GRAY);

        AllowStep allowStep_Y = new AllowStep(ChannelsEnum.Y.getChannelName());
        allowStep_Y.setMethod(YCBCR.getMethodId());
        allowStep_Y.setAllowStep(original.getMethodId());
        grayScale.add(allowStep_Y);

        AllowStep allowStep_CB = new AllowStep(ChannelsEnum.CB.getChannelName());
        allowStep_CB.setMethod(YCBCR.getMethodId());
        allowStep_CB.setAllowStep(original.getMethodId());
        grayScale.add(allowStep_CB);

        AllowStep allowStep_CR = new AllowStep(ChannelsEnum.CR.getChannelName());
        allowStep_CR.setMethod(YCBCR.getMethodId());
        allowStep_CR.setAllowStep(original.getMethodId());
        grayScale.add(allowStep_CR);

        AllowStep allowStep_H = new AllowStep(ChannelsEnum.H.getChannelName());
        allowStep_H.setMethod(HSV.getMethodId());
        allowStep_H.setAllowStep(original.getMethodId());
        grayScale.add(allowStep_H);

        ///////////////////////////////////
        AllowStep allowStep_sobel_a = new AllowStep(EdgeDetectorEnum.SOBEL.getDetectorlName());
        allowStep_sobel_a.setMethod(detectors.getMethodId());
        allowStep_sobel_a.setAllowStep(RGB.getMethodId());
        grayScale.add(allowStep_sobel_a);
        AllowStep allowStep_sobel_b = new AllowStep(EdgeDetectorEnum.SOBEL.getDetectorlName());
        allowStep_sobel_b.setMethod(detectors.getMethodId());
        allowStep_sobel_b.setAllowStep(YCBCR.getMethodId());
        grayScale.add(allowStep_sobel_b);
        AllowStep allowStep_sobel_c = new AllowStep(EdgeDetectorEnum.SOBEL.getDetectorlName());
        allowStep_sobel_c.setMethod(detectors.getMethodId());
        allowStep_sobel_c.setAllowStep(HSV.getMethodId());
        grayScale.add(allowStep_sobel_c);
        ////////////////////////////////////
        AllowStep allowStep_laplace_a = new AllowStep(EdgeDetectorEnum.LAPLACIAN.getDetectorlName());
        allowStep_laplace_a.setMethod(detectors.getMethodId());
        allowStep_laplace_a.setAllowStep(RGB.getMethodId());
        grayScale.add(allowStep_laplace_a);
        AllowStep allowStep_laplace_b = new AllowStep(EdgeDetectorEnum.LAPLACIAN.getDetectorlName());
        allowStep_laplace_b.setMethod(detectors.getMethodId());
        allowStep_laplace_b.setAllowStep(YCBCR.getMethodId());
        grayScale.add(allowStep_laplace_b);
        AllowStep allowStep_laplace_c = new AllowStep(EdgeDetectorEnum.LAPLACIAN.getDetectorlName());
        allowStep_laplace_c.setMethod(detectors.getMethodId());
        allowStep_laplace_c.setAllowStep(HSV.getMethodId());
        grayScale.add(allowStep_laplace_c);
        ////////////////////////////////////
        AllowStep allowStep_threshold_a = new AllowStep(SegmentorEnum.TRESHHOLDING.getSegmentorName());
        allowStep_threshold_a.setMethod(segmentation.getMethodId());
        allowStep_threshold_a.setAllowStep(RGB.getMethodId());
        grayScale.add(allowStep_threshold_a);
        AllowStep allowStep_threshold_b = new AllowStep(SegmentorEnum.TRESHHOLDING.getSegmentorName());
        allowStep_threshold_b.setMethod(segmentation.getMethodId());
        allowStep_threshold_b.setAllowStep(YCBCR.getMethodId());
        grayScale.add(allowStep_threshold_b);
        AllowStep allowStep_threshold_c = new AllowStep(SegmentorEnum.TRESHHOLDING.getSegmentorName());
        allowStep_threshold_c.setMethod(segmentation.getMethodId());
        allowStep_threshold_c.setAllowStep(HSV.getMethodId());
        grayScale.add(allowStep_threshold_c);
        //////////////////////////////////
        AllowStep allowStep_coloring = new AllowStep(SegmentorEnum.COLORING.getSegmentorName());
        allowStep_coloring.setMethod(segmentation.getMethodId());
        allowStep_coloring.setAllowStep(segmentation.getMethodId());
        allowStep_coloring.setSelfRelation(true);
        allowStep_coloring.setSelfParam(SegmentorEnum.TRESHHOLDING.getSegmentorName());
        grayScale.add(allowStep_coloring);

        allowStepDAO.save(grayScale);
    }


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

    public MethodAttributesDAO getMethodAttributesDAO() {
        return methodAttributesDAO;
    }

    public void setMethodAttributesDAO(MethodAttributesDAO methodAttributesDAO) {
        this.methodAttributesDAO = methodAttributesDAO;
    }
}
