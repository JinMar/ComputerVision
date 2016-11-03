package cz.tul.repositories;

import cz.tul.entities.OperationAttributes;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Bc. Marek Jindrák on 02.11.2016.
 */
@Repository
public class OperationAttributesDAO extends BasicRepositoryAbstract {
    private static final Logger logger = LoggerFactory.getLogger(OperationAttributesDAO.class);

    public List<OperationAttributes> getAttributesById(String idOperation) {
        Query query = getQuery("from OperationAttributes oa where oa.operation.operationId= :id");
        query.setParameter("id", idOperation);
        return query.list();
    }

    public OperationAttributes getAttributById(String idOperation) {
        Query query = getQuery("from OperationAttributes oa where oa.operationAttributesId= :id");
        query.setParameter("id", idOperation);
        OperationAttributes result = (OperationAttributes) query.uniqueResult();
        return result;
    }
}
