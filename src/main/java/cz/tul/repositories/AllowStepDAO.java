package cz.tul.repositories;

import cz.tul.entities.AllowStep;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bc. Marek Jindrák on 30.10.2016.
 */
@Repository
public class AllowStepDAO extends BasicRepositoryAbstract {
    @Autowired
    private MethodDAO methodDAO;

    @Transactional
    public List<String> getAllowedMethod(String method, List<String> att) {
        String hql = "FROM AllowStep a where a.method = :id ";
        List<String> result = new ArrayList<>();
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        query.setParameter("id", method);

        List<AllowStep> methodAttributes = (List<AllowStep>) query.list();
        for (AllowStep as : methodAttributes) {
            if (att.contains(as.getParam())) {
                result.add(as.getAllowStep());
            }
        }

        return result;
    }


}
