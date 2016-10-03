package cz.tul.provisioner;

import cz.tul.entities.*;
import cz.tul.repositories.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
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


    @Override

    public void afterPropertiesSet() throws Exception {

        Set<Method> methods = new HashSet<>();
        Set<MethodAttributes> methodAttributes = new HashSet<>();
        Atribute atribute = new Atribute();

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
        //ulozeni metod do databaze
        methods.add(redChannel);
        methods.add(greenChannel);
        methods.add(blueChannel);
        methods.add(yChannel);
        methods.add(crChannel);
        methods.add(cbChannel);
        methods.add(hChannel);
        methods.add(original);
        methods.add(gray);
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
        Set<Atribute> atributes = new HashSet<>();

        atribute.setAtributeId(UUID.randomUUID().toString());
        atribute.setMax(5);
        atributes.add(atribute);

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


        atributeDao.save(atribute);
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
