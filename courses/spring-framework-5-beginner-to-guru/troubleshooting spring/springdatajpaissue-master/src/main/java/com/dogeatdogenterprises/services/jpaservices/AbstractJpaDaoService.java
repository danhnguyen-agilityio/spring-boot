package com.dogeatdogenterprises.services.jpaservices;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * Created by donaldsmallidge on 1/8/17.
 */
public class AbstractJpaDaoService {
    protected EntityManagerFactory emf;

    @PersistenceUnit
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

}
