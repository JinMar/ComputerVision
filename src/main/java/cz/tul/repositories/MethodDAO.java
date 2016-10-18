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


    public Method getMethodById(String id) {
        Query query = getQuery("from Method m where m.methodId='" + id + "'");
        query.setMaxResults(1);
        if (query.list().size() > 0) {
            return (Method) query.list().get(0);
        } else {
            return null;
        }

    }
}
