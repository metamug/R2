package com.metamug.parser.parser.service;

import com.metamug.parser.schema.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Backend Entity to represent Metamug API Backend
 */
public class Backend {

    String name, description, email;
    Map<String, Resource> resourceList;

    public Backend(String name, String description, String email) {
        resourceList = new HashMap<>();

        this.name = name;
        this.description = description;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Resource> getResourceList() {
        return resourceList;
    }

    public void addResource(String name, Resource resource) {

        resourceList.put(name, resource);
    }


}
