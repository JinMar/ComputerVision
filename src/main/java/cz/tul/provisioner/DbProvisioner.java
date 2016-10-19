package cz.tul.provisioner;

import cz.tul.bussiness.register.MethodRegister;
import cz.tul.bussiness.workers.*;
import cz.tul.bussiness.workers.enums.ChannelsEnum;
import cz.tul.bussiness.workers.enums.EdgeDetectorEnum;
import cz.tul.bussiness.workers.enums.NoiseReducerEnum;
import cz.tul.bussiness.workers.enums.SegmentorEnum;
import cz.tul.entities.Attribute;
import cz.tul.entities.AttributeType;
import cz.tul.entities.Method;
import cz.tul.entities.MethodAttributes;
import cz.tul.repositories.*;
import org.opencv.imgproc.Imgproc;
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

    @Override
    public void afterPropertiesSet() throws Exception {
        String test = servletContext.getRealPath("/");
        methodRegister.registerContextPath(test);

        Set<Method> methods = new HashSet<>();
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
        methodAttributesDAO.save(methodAttributes);



/*

        erodeAttributesAtr1.setAttribute(size);
        erodeAttributesAtr1.setOptions(shapes);
        erodeAttributesAtr1.setAttributeType(AttributeType.NUMBER);
        dilateAttributesAtr1.setAttribute(size);
        dilateAttributesAtr1.setOptions(shapes);
        dilateAttributesAtr1.setAttributeType(AttributeType.NUMBER);
        openAttributesAtr1.setAttribute(size);
        openAttributesAtr1.setOptions(shapes);
        openAttributesAtr1.setAttributeType(AttributeType.NUMBER);
        closeAttributesAtr1.setAttribute(size);
        closeAttributesAtr1.setOptions(shapes);
        closeAttributesAtr1.setAttributeType(AttributeType.NUMBER);


        erodeAttributesAtr2.setAttribute(shape);
        erodeAttributesAtr2.setOptions(shapes);
        erodeAttributesAtr2.setAttributeType(AttributeType.SELECT);
        dilateAttributesAtr2.setAttribute(shape);
        dilateAttributesAtr2.setOptions(shapes);
        dilateAttributesAtr2.setAttributeType(AttributeType.SELECT);
        openAttributesAtr2.setAttribute(shape);
        openAttributesAtr2.setOptions(shapes);
        openAttributesAtr2.setAttributeType(AttributeType.SELECT);
        closeAttributesAtr2.setAttribute(shape);
        closeAttributesAtr2.setOptions(shapes);
        closeAttributesAtr2.setAttributeType(AttributeType.SELECT);
*/

        //ulozeni atributu metod do databaze

/*
        methodAttributes.add(erodeAttributesAtr1);
        methodAttributes.add(dilateAttributesAtr1);
        methodAttributes.add(openAttributesAtr1);
        methodAttributes.add(closeAttributesAtr1);
        methodAttributes.add(erodeAttributesAtr2);
        methodAttributes.add(dilateAttributesAtr2);
        methodAttributes.add(openAttributesAtr2);
        methodAttributes.add(closeAttributesAtr2);*/



        //Testing parts
       /* Part part = new Part();
        part.setState(StateEnum.ACTIVE);
        part.setChain(testChain);
        part.setPosition(0);
        Part part1 = new Part();
        part1.setState(StateEnum.ACTIVE);
        part1.setChain(testChain);
        part1.setPosition(1);
        partDAO.save(part);
        partDAO.save(part1);*/
/*
        PartAttributeValue partAttributeValue = new PartAttributeValue();
        partAttributeValue.setPart(part);
        partAttributeValue.setMethodAttributes(erodeAttributesAtr1);
        partAttributeValue.setValue("Parada");
        partAttributeValue.setMethodAttributes(erodeAttributesAtr1);
        partAttributeValueDAO.save(partAttributeValue);*/


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

    public MethodAttributesDAO getMethodAttributesDAO() {
        return methodAttributesDAO;
    }

    public void setMethodAttributesDAO(MethodAttributesDAO methodAttributesDAO) {
        this.methodAttributesDAO = methodAttributesDAO;
    }
}
