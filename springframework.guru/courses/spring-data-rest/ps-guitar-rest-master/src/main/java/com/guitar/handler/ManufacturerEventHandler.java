package com.guitar.handler;

import com.guitar.model.Manufacturer;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Manufacturer.class)
public class ManufacturerEventHandler {

    @HandleBeforeCreate
    public void handleBeforeCreate(Manufacturer m) {

        // only allow new manufacturers to be created in the active state
        if (!m.getActive()) {
            throw new IllegalArgumentException("New Manufacturers must be 'active'");
        }
    }
}
