package cz.tul.repositories;

import cz.tul.entities.Operation;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Bc. Marek Jindrák on 01.11.2016.
 */
@Repository
public class OperationDAO extends BasicRepositoryAbstract {
    private static final Logger logger = LoggerFactory.getLogger(OperationDAO.class);

    public List<Operation> getOperationsById(String methodId) {

        Query query = getQuery("from Operation o  where o.method.methodId= :methodId order by o.name");

        query.setParameter("methodId", methodId);

        return query.list();

    }

    public Operation getOperationById(String operationId) {

        Query query = getQuery("from Operation o  where o.operationId= :operationId order by o.name");

        query.setParameter("operationId", operationId);

        return (Operation) query.uniqueResult();

    }

    public String getIdOperationByName(String name) {

        Query query = getQuery("from Operation o  where o.name= :name order by o.name");

        query.setParameter("name", name);
        Operation o = (Operation) query.uniqueResult();
        return o.getOperationId();

    }
}
