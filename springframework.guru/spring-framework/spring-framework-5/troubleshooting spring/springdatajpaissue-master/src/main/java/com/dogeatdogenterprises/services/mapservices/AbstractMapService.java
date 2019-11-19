package com.dogeatdogenterprises.services.mapservices;

import com.dogeatdogenterprises.domain.DomainObject;

import java.util.*;

/**
 * Created by donaldsmallidge on 9/21/16.
 */
public abstract class AbstractMapService {

    protected Map<Integer, DomainObject> domainMap;

    public AbstractMapService() {
        domainMap = new HashMap<>();
        //loadDomainObjects();
    }

    public List<DomainObject> listAll() {

        return new ArrayList<>(domainMap.values());
    }

    public DomainObject getById(Integer id) {

        return domainMap.get(id);
    }

    public DomainObject saveOrUpdate(DomainObject domainObject) {
        if (domainObject != null) {
            if (domainObject.getId() == null) {
                domainObject.setId(getNextKey());
            }
            domainMap.put(domainObject.getId(), domainObject);
            return domainObject;
        } else {
            throw new RuntimeException("Object can't be null");
        }
    }

    public void delete(Integer id) {
        domainMap.remove(id);
    }

    private Integer getNextKey() {

        return Collections.max(domainMap.keySet()) + 1;
    }

    /* NO LONGER NEEDED? - it is called from the constructor! */
//    protected abstract void loadDomainObjects();

}
