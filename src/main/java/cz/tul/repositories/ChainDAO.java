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
        query.setParameter("state", StateEnum.ACTIVE.getState());
        List<Chain> methodAttributes = (List<Chain>) query.list();

        return methodAttributes;
    }

    @Transactional
    public StateEnum isChainProcessed(String key) {
        int count = -1;
        String hql = " FROM Part p where p.partId = :part and p.state != :state1 ";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        query.setParameter("state1", StateEnum.COMPLETE.getState());
        query.setParameter("part", key);
        count = query.list().size();
        if (count == 0) {
            return StateEnum.COMPLETE;
        }

        String hql2 = "FROM Part p where p.partId = :part and p.state = :state1 ";
        Query query2 = getSessionFactory().getCurrentSession().createQuery(hql2);
        query2.setParameter("state1", StateEnum.PROCESSING.getState());
        query2.setParameter("part", key);
        count = query.list().size();
        if (count > 0) {
            return StateEnum.PROCESSING;
        }


        String hql3 = " FROM Part p where p.partId = :part and p.state = :state1 ";
        Query query3 = getSessionFactory().getCurrentSession().createQuery(hql3);
        query3.setParameter("state1", StateEnum.ACTIVE.getState());
        query3.setParameter("part", key);
        count = query.list().size();
        if (count > 0) {
            return StateEnum.ACTIVE;
        }

        return StateEnum.ERROR;

    }

    public boolean isChainCompleted(String id) {
        int count = -1;
        String hql = " FROM Chain c where c.chainId = :chain and c.state = :state ";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        query.setParameter("state", StateEnum.COMPLETE.getState());
        query.setParameter("chain", id);
        count = query.list().size();
        return count == 1;
    }
}
