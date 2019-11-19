package com.dogeatdogenterprises.services.mapservices;

        import com.dogeatdogenterprises.domain.Order;
        import com.dogeatdogenterprises.domain.DomainObject;
        import com.dogeatdogenterprises.services.OrderService;
        import org.springframework.context.annotation.Profile;
        import org.springframework.stereotype.Service;

        import java.util.List;

/**
 * Copied by donaldsmallidge on 2/20/17.
 */
@Service
@Profile("map")
public class OrderServiceMapImpl extends AbstractMapService implements OrderService {
    @Override
    public List<DomainObject> listAll() {
        return super.listAll();
    }

    @Override
    public Order getById(Integer id) {
        return (Order) super.getById(id);
    }

    @Override
    public Order saveOrUpdate(Order domainObject) {
        return (Order) super.saveOrUpdate(domainObject);
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }

//    @Override
//    protected void loadDomainObjects() {
        // not part of instructor code!
//    }
}