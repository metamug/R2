/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.objectreturn.tests.testclasses;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class PhoneNumber {
    
    @XmlAttribute
    private String type;

    @XmlValue
    private String number;

    public void setType(String t){
        type = t;
    }
    
    public void setNum(String n){
        number = n;
    }
    
}