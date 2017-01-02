/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mtgapi.processables;

import java.util.SortedMap;

/**
 *
 * @author metamug
 */
public interface ResultProcessable {
    public String process(SortedMap[] resultMap, String[] columnName, int rowCount);
}
