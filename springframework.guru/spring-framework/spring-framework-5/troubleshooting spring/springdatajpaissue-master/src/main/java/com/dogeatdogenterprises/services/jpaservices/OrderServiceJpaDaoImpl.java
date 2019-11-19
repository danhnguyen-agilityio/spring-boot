package com.dogeatdogenterprises.services.jpaservices;

import com.dogeatdogenterprises.domain.Order;
import com.dogeatdogenterprises.domain.User;
import com.dogeatdogenterprises.services.OrderService;
import com.dogeatdogenterprises.services.UserService;
import com.dogeatdogenterprises.services.security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by donaldsmallidge on 12/6/16.
 */
@Service
@Profile("jpadao")
public class OrderServiceJpaDaoImpl extends AbstractJpaDaoService implements OrderService {

    @Override
    public List<Order> listAll() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("from Order", Order.class).getResultList();
    }

    @Override
    public Order getById(Integer id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Order.class, id);
    }

    @Override
    public Order saveOrUpdate(Order domainObject) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Order savedProduct = em.merge(domainObject);
        em.getTransaction().commit();

        return savedProduct; // why not call it savedOrder?
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(Order.class, id));
        em.getTransaction().commit();
    }
}
