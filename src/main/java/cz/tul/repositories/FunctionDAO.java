package cz.tul.repositories;

import cz.tul.entities.Function;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Bc. Marek Jindrák on 31.10.2016.
 */
@Repository
public class FunctionDAO extends BasicRepositoryAbstract {
    private static final Logger logger = LoggerFactory.getLogger(FunctionDAO.class);

    public List<Function> getAllFunctions() {
        Query query = getQuery("from Function f where  f.name != :name order by f.name ");
        query.setParameter("name", "Original");
        return (List<Function>) query.list();

    }
}
