package com.metamug.parser.parser.service;

import com.metamug.parser.schema.Resource;

import java.util.List;

/**
 * Backend Entity to represent Metamug API Backend
 */
public class Backend {

    String name, description, email;
    List<Resource> resourceList;

    public Backend(String name, String description, String email) {
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

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }
}
