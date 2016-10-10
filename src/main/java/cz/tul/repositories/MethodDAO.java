package cz.tul.repositories;

import cz.tul.entities.Method;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Marek on 29.09.2016.
 */
@Repository
public class MethodDAO extends BasicRepositoryAbstract {

    public List<Method> getAllMethods() {
        Query query = getQuery("from Method");
        return (List<Method>) query.list();
    }


}
