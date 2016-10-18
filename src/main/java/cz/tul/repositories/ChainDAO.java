package cz.tul.repositories;

import cz.tul.entities.Chain;
import cz.tul.entities.StateEnum;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Marek on 29.09.2016.
 */
@Repository
public class ChainDAO extends BasicRepositoryAbstract {

    /**
     * @param count int - udava kolik zaznamu se ma vrati
     * @return List<Chain>
     */
    @Transactional
    public List<Chain> getLastActiveChains(int count) {
        String hql = "FROM Chain c where c.state = :state  order by c.createDate desc";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        query.setMaxResults(count);
        query.setParameter("state", StateEnum.ACTIVE);
        List<Chain> methodAttributes = (List<Chain>) query.list();
        System.out.println(methodAttributes.size());
        return methodAttributes;
    }
}
