/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.jaxb.tests;

import com.metamug.jaxb.InputValidationException;
import com.metamug.jaxb.JAXBParser;
import com.metamug.jaxb.gener.Param;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.PatternSyntaxException;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author anish
 */
public class ParamValidationTest {

    Param param1, param2, param3, regexParam1;

    @Before
    public void init() {
        param1 = new Param();
        param1.setName("param1");
        param1.setBlank(Boolean.FALSE);
        param1.setNum(Boolean.FALSE);
        param1.setMaxLen("10");
        param1.setMinLen("4");

        param2 = new Param();
        param2.setName("param2");
        param2.setBlank(Boolean.FALSE);
        param2.setNum(Boolean.TRUE);
        param2.setMax("100");
        param2.setMin("10");

        param3 = new Param();
        param3.setName("param3");
        param3.setBlank(Boolean.TRUE);

        regexParam1 = new Param();
        regexParam1.setPattern("[a-zA-Z]{3}");
    }

//    @Test
    public void InvalidTest1() throws InputValidationException {
        try {
            JAXBParser.isValid(param1, "par");
        } catch (PatternSyntaxException | InputValidationException ex) {
            Logger.getLogger(ParamValidationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void InvalidTest2() throws InputValidationException {
        try {
            JAXBParser.isValid(param2, "1000");
        } catch (PatternSyntaxException | InputValidationException ex) {
            Logger.getLogger(ParamValidationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @Test
    public void InvalidTest3() throws InputValidationException {
        try {
            JAXBParser.isValid(param2, "");
        } catch (PatternSyntaxException | InputValidationException ex) {
            Logger.getLogger(ParamValidationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @Test
    public void InvalidTest4() throws InputValidationException {
        try {
            JAXBParser.isValid(param2, "No");
        } catch (PatternSyntaxException | InputValidationException ex) {
            Logger.getLogger(ParamValidationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @Test
    public void InvalidTest5() throws InputValidationException {
        try {
            JAXBParser.isValid(param2, "123");
        } catch (PatternSyntaxException | InputValidationException ex) {
            Logger.getLogger(ParamValidationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @Test
    public void ValidTest1() throws InputValidationException {
        try {
            JAXBParser.isValid(param1, "param123");
            JAXBParser.isValid(param1, "paramStr");
            JAXBParser.isValid(param2, "12");
            JAXBParser.isValid(param3, "");
            JAXBParser.isValid(regexParam1, "abc");
        } catch (PatternSyntaxException | InputValidationException ex) {
            Logger.getLogger(ParamValidationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
