package com.agility.usermanagement.services;

import com.okta.sdk.client.Client;
import com.okta.sdk.resource.group.Group;
import com.okta.sdk.resource.group.GroupList;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private Client client;

    public GroupService(Client client) {
        this.client = client;
    }

    /**
     * Get all of groups available
     *
     * @return A list of groups
     */
    public GroupList getAll() {
        return client.listGroups();
    }

    /**
     * Retrieve a list of group by given name
     *
     * @param name The name of group
     * @return The list of group matched given name
     */
    public GroupList find(String name) {
        return client.listGroups(name, null, null);
    }
}
