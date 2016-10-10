package cz.tul.repositories;

import cz.tul.entities.MethodAttributes;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Marek on 03.10.2016.
 */
@Repository
public class MethodAttributesDAO extends BasicRepositoryAbstract {
    public List<MethodAttributes> getAllMethod() {
        String hql = "FROM MethodAttributes";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        List<MethodAttributes> methodAttributes = (List<MethodAttributes>) query.list();
        return methodAttributes;
    }

    public List<MethodAttributes> getMethodAttributesById(String id) {
        String hql = "FROM MethodAttributes MA where MA.method ='" + id + "'";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        List<MethodAttributes> methodAttributes = (List<MethodAttributes>) query.list();
        return methodAttributes;
    }
}
