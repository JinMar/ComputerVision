package cz.tul.repositories;

import cz.tul.entities.AllowStep;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Bc. Marek Jindrák on 30.10.2016.
 */
@Repository
public class AllowStepDAO extends BasicRepositoryAbstract {
    @Autowired
    private MethodDAO methodDAO;


    public List<AllowStep> getAllAllowedSteps() {

        Query query = getQuery("from AllowStep a order by a.allowoperationId.method.function.name, a.allowoperationId.method.name, a.allowoperationId.name");


        return query.list();

    }

}
