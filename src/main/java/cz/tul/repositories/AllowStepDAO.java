package cz.tul.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Bc. Marek Jindrák on 30.10.2016.
 */
@Repository
public class AllowStepDAO extends BasicRepositoryAbstract {
    @Autowired
    private MethodDAO methodDAO;


}
