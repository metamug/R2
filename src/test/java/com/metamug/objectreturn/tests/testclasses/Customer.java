/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.objectreturn.tests.testclasses;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {
    
    @XmlAttribute
    private int id;

    private String firstName;

    private String lastName;

    private List<PhoneNumber> phoneNumbers = new ArrayList<>();

    
    //must have zero argument constructor
    public Customer(){
        
    }
    
    public Customer(int id, String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public void addPhoneNumber(PhoneNumber pn){
        phoneNumbers.add(pn);
    }

}