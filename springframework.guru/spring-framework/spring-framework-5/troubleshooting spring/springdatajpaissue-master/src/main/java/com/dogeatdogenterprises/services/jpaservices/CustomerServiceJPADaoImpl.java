package com.dogeatdogenterprises.services.jpaservices;

import com.dogeatdogenterprises.commands.CustomerForm;
import com.dogeatdogenterprises.converters.CustomerFormToCustomer;
import com.dogeatdogenterprises.domain.Customer;
import com.dogeatdogenterprises.domain.User;
import com.dogeatdogenterprises.services.CustomerService;
import com.dogeatdogenterprises.services.security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by donaldsmallidge on 12/6/16.
 */
@Service
@Profile("jpadao")
public class CustomerServiceJPADaoImpl extends AbstractJpaDaoService implements CustomerService {

    private EncryptionService encryptionService;
    private CustomerFormToCustomer customerFormToCustomer;

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Autowired
    public void setCustomerFormToCustomer(CustomerFormToCustomer customerFormToCustomer) {
        this.customerFormToCustomer = customerFormToCustomer;
    }

    @Override
    public List<Customer> listAll() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("from Customer", Customer.class).getResultList();
    }

    @Override
    public Customer getById(Integer id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Customer.class, id);
    }

    @Override
    public Customer saveOrUpdate(Customer domainObject) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        if (domainObject.getUser() != null && domainObject.getUser().getPassword() != null) {
            domainObject.getUser().setEncryptedPassword(
                    encryptionService.encryptString(domainObject.getUser().getPassword())
            );
        }

        Customer savedCustomer = em.merge(domainObject);
        em.getTransaction().commit();

        return savedCustomer;
    }

    @Override
    public Customer saveOrUpdateCustomerForm(CustomerForm customerForm) {
        Customer newCustomer = customerFormToCustomer.convert(customerForm);

        // enhance if saved
        if (newCustomer.getUser().getId() != null) {
            Customer existingCustomer = getById(newCustomer.getUser().getId());

            // set enabled flag from database
            newCustomer.getUser().setEnabled(existingCustomer.getUser().getEnabled());
        }
        return saveOrUpdate(newCustomer);
    }

    @Override
    @Transactional
    // added @Transactional 4/12/2017
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        Customer customer = getById(id);
        em.getTransaction().begin();
        // ISSUE: 4/12/2017 - how to remove the user that belongs to customer?
        em.remove(em.find(User.class, customer.getUser().getId()));
        em.remove(em.find(Customer.class, id));
        em.getTransaction().commit();
    }

}
