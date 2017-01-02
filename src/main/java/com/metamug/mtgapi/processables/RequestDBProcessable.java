/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mtgapi.processables;

import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author metamug
 */
public interface RequestDBProcessable {
    public String process(Map<String, String> params, DataSource dataSource);
}
