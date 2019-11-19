package com.dogeatdogenterprises.services.mapservices;

import com.dogeatdogenterprises.domain.DomainObject;
import com.dogeatdogenterprises.domain.User;
import com.dogeatdogenterprises.domain.security.Role;
import com.dogeatdogenterprises.services.RoleService;
import com.dogeatdogenterprises.services.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by donaldsmallidge on 9/14/16.
 */
@Service
@Profile("map")
public class RoleServiceMapImpl extends AbstractMapService implements RoleService {

    @Override
    public List<DomainObject> listAll() {

        return super.listAll();
    }

    @Override
    public Role getById(Integer id) {

        return (Role) super.getById(id);
    }

    @Override
    public Role saveOrUpdate(Role domainObject) {

        return (Role) super.saveOrUpdate(domainObject);
    }

    @Override
    public void delete(Integer id) {

        super.delete(id);
    }

//    @Override
//    protected void loadDomainObjects() {
        // placeholder?
//    }
}
