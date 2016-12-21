/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.jaxb.tests;

import com.metamug.jaxb.JAXBParser;
import com.metamug.jaxb.gener.Param;
import com.metamug.jaxb.util.InputValidationException;
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
        param1.setBlank(Boolean.FALSE);
        param1.setNum(Boolean.FALSE);
        param1.setMaxLen("10");
        param1.setMinLen("4");

        param2 = new Param();
        param2.setBlank(Boolean.FALSE);
        param2.setNum(Boolean.TRUE);
        param2.setMax("100");
        param2.setMin("10");

        param3 = new Param();
        param3.setBlank(Boolean.TRUE);

        regexParam1 = new Param();
        regexParam1.setPattern("[a-zA-Z]{3}");
    }

    @Test(expected = InputValidationException.class)
    public void InvalidTest1() throws InputValidationException {
        JAXBParser.validateParam(param1, "par");
    }

    @Test(expected = InputValidationException.class)
    public void InvalidTest2() throws InputValidationException {
        JAXBParser.validateParam(param2, "1000");
        JAXBParser.validateParam(param2, "");
        JAXBParser.validateParam(param2, "No");
        JAXBParser.validateParam(param2, "123");
    }

//    @Test
    public void ValidTest1() throws InputValidationException {
        JAXBParser.validateParam(param1, "param123");
        JAXBParser.validateParam(param1, "paramStr");
        JAXBParser.validateParam(param2, "12");
        JAXBParser.validateParam(param3, "");
        JAXBParser.validateParam(regexParam1, "abc");
    }
}
