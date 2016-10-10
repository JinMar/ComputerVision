package cz.tul.provisioner;

import cz.tul.entities.*;
import cz.tul.repositories.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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


    @Override

    public void afterPropertiesSet() throws Exception {

        Set<Method> methods = new HashSet<>();
        Set<MethodAttributes> methodAttributes = new HashSet<>();
        Set<Attribute> attributes = new HashSet<>();

        // RGB funkce
        Method redChannel = new Method();
        Method greenChannel = new Method();
        Method blueChannel = new Method();
        Method original = new Method();
        Method gray = new Method();

        // YCBCR funkce
        Method yChannel = new Method();
        Method crChannel = new Method();
        Method cbChannel = new Method();

        // HSV
        Method hChannel = new Method();

        //MORPOHOLOGY
        Method erode = new Method();
        Method dilate = new Method();
        Method open = new Method();
        Method close = new Method();


        // inicializace základních metoda ulozeni

        redChannel.setName("redChannel");
        greenChannel.setName("greenChannel");
        blueChannel.setName("blueChannel");
        yChannel.setName("yChannel");
        crChannel.setName("crChannel");
        cbChannel.setName("cbChannel");
        hChannel.setName("hChannel");
        original.setName("original");
        gray.setName("gray");
        erode.setName("Eroze");
        dilate.setName("Dilatace");
        open.setName("Otevření");
        close.setName("Uzavření");
        //ulozeni metod do databaze
        methods.add(redChannel);
        methods.add(greenChannel);
        methods.add(blueChannel);
        methods.add(yChannel);
        methods.add(crChannel);
        methods.add(cbChannel);
        methods.add(hChannel);
        methods.add(original);
        methods.add(dilate);
        methods.add(erode);
        methods.add(close);
        methods.add(gray);
        methods.add(open);
        methodDAO.save(methods);


        //Testing chain
        Chain testChain = new Chain();
        testChain.setCreateDate(new Date());

        chainDAO.save(testChain);
        //RGB
        MethodAttributes redMethodAttributes = new MethodAttributes();
        MethodAttributes greenMethodAttributes = new MethodAttributes();
        MethodAttributes blueMethodAttributes = new MethodAttributes();
        //YCBCR
        MethodAttributes yMethodAttributes = new MethodAttributes();
        MethodAttributes cbMethodAttributes = new MethodAttributes();
        MethodAttributes crMethodAttributes = new MethodAttributes();
        //BASIC
        MethodAttributes grayMethodAttributes = new MethodAttributes();
        MethodAttributes originalMethodAttributes = new MethodAttributes();
        //HSV
        MethodAttributes hMethodAttributes = new MethodAttributes();

        //MORPHOLOGY
        MethodAttributes erodeAttributesAtr1 = new MethodAttributes();
        MethodAttributes dilateAttributesAtr1 = new MethodAttributes();
        MethodAttributes openAttributesAtr1 = new MethodAttributes();
        MethodAttributes closeAttributesAtr1 = new MethodAttributes();
        MethodAttributes erodeAttributesAtr2 = new MethodAttributes();
        MethodAttributes dilateAttributesAtr2 = new MethodAttributes();
        MethodAttributes openAttributesAtr2 = new MethodAttributes();
        MethodAttributes closeAttributesAtr2 = new MethodAttributes();

        //inicializace
        redMethodAttributes.setMethod(redChannel);
        greenMethodAttributes.setMethod(greenChannel);
        blueMethodAttributes.setMethod(blueChannel);
        yMethodAttributes.setMethod(yChannel);
        cbMethodAttributes.setMethod(cbChannel);
        crMethodAttributes.setMethod(cbChannel);
        grayMethodAttributes.setMethod(gray);
        originalMethodAttributes.setMethod(original);
        hMethodAttributes.setMethod(hChannel);

        erodeAttributesAtr1.setMethod(erode);
        dilateAttributesAtr1.setMethod(dilate);
        openAttributesAtr1.setMethod(open);
        closeAttributesAtr1.setMethod(close);

        erodeAttributesAtr2.setMethod(erode);
        dilateAttributesAtr2.setMethod(dilate);
        openAttributesAtr2.setMethod(open);
        closeAttributesAtr2.setMethod(close);


        Attribute attribute = new Attribute("Krok");
        Attribute shape = new Attribute("Tvar");
        Attribute size = new Attribute("Velikost");

        attributes.add(attribute);
        attributes.add(shape);
        attributes.add(size);

        attributeDAO.save(attributes);

        redMethodAttributes.setAttribute(attribute);
        redMethodAttributes.setAttributeType(AttributeType.NUMBER);

        Map<String, String> shapes = new HashMap<>();
        shapes.put("oval", "Oval");
        shapes.put("circle", "Kruh");
        shapes.put("square", "Čtverec");
        shapes.put("rectangle", "Obdelník");

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


        //ulozeni atributu metod do databaze
        methodAttributes.add(redMethodAttributes);
        methodAttributes.add(greenMethodAttributes);
        methodAttributes.add(blueMethodAttributes);
        methodAttributes.add(yMethodAttributes);
        methodAttributes.add(cbMethodAttributes);
        methodAttributes.add(crMethodAttributes);
        methodAttributes.add(grayMethodAttributes);
        methodAttributes.add(originalMethodAttributes);
        methodAttributes.add(hMethodAttributes);
        methodAttributes.add(erodeAttributesAtr1);
        methodAttributes.add(dilateAttributesAtr1);
        methodAttributes.add(openAttributesAtr1);
        methodAttributes.add(closeAttributesAtr1);
        methodAttributes.add(erodeAttributesAtr2);
        methodAttributes.add(dilateAttributesAtr2);
        methodAttributes.add(openAttributesAtr2);
        methodAttributes.add(closeAttributesAtr2);

        methodAttributesDAO.save(methodAttributes);

        //Testing parts
        Part part = new Part();
        part.setMethodAttributes(redMethodAttributes);
        part.setCurrentValue("hoooooovno");
        part.setChain(testChain);
        part.setPosition(0);
        Part part1 = new Part();
        part1.setMethodAttributes(redMethodAttributes);
        part1.setChain(testChain);
        part1.setPosition(1);
        partDAO.save(part);
        partDAO.save(part1);


        ///// testing

/*
        Set<Attribute> atributes = new HashSet<>();

        attribute.setAtributeId(UUID.randomUUID().toString());
        attribute.setMax(5);
        atributes.add(attribute);

        Chain chain = new Chain();
        Method method = new Method();
        Part part = new Part();


        chain.setChainId(UUID.randomUUID().toString());
        chain.setCreateDate("ted");

        method.setMethodId(UUID.randomUUID().toString());
        method.setName("Blue");

        method.setAtributes(atributes);


        part.setPartId(UUID.randomUUID().toString());
        part.setPosition(1);
        part.setChain(chain);
        part.setMethod(method);


        atributeDao.save(attribute);
        chainDao.save(chain);
        methodDao.save(method);
        partDao.save(part);
        //partDao.save(part2);
*/
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
