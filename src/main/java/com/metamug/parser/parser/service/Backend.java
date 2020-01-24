package com.metamug.parser.parser.service;

/**
 * Backend Entity to represent Metamug API Backend
 */
public class Backend {

    String name, description, email;

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
}
