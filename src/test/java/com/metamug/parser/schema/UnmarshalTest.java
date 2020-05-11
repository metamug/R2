/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.parser.schema;

import com.metamug.parser.schema.xrequest.Xbody;
import com.metamug.parser.schema.xrequest.Xheader;
import com.metamug.parser.schema.xrequest.Xparam;
import com.metamug.parser.schema.xrequest.XrequestChild;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author anishhirlekar
 */
public class UnmarshalTest {
    @Test
    public void xreqUnmarhal() throws JAXBException {
        String xml = "<XRequest xmlns=\"http://xml.metamug.net/resource/1.0\" id=\"req1588663132178\" url=\"https://api.metamug.com/covid/v1.0/post\" method=\"POST\" output=\"headers\">\n" +
"<Header name=\"Content-Type\" value=\"application/x-www-form-urlencoded\" />\n" +
"	<Param name=\"name\" value=\"Maharashtra\" />\n" +
"        </XRequest>";
        
        Xrequest xrequest = (Xrequest)new Xrequest().unmarshal(xml); 
        
        List<XrequestChild> children = xrequest.getXRequestChildren();
        
        Map<String,String> headers = new HashMap<>();
        Map<String,Object> parameters = new HashMap<>();
        String requestBody = "";
        
        for(XrequestChild ch : children) {
             if(ch instanceof Xparam){
                Xparam xp = (Xparam)ch;
                parameters.put(xp.getName(), xp.getValue());
            }else if(ch instanceof Xheader){
                Xheader xh = (Xheader)ch;
                headers.put(xh.getName(), xh.getValue());
            }else if(ch instanceof Xbody){
                requestBody = ((Xbody)ch).getValue();
            }
        }
        Assert.assertEquals(1, headers.size());
        Assert.assertEquals(1, parameters.size());
    }
}
