/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivaylorusev.xmlFoTemplateBuilder.models;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Ivaylo Rusev
 */
public class ExtendedHashMap {

    public static <K,V> Object get(HashMap<K,V> linkedHashMap, Object key) {
        Set<K> keySet = linkedHashMap.keySet();
        for (K k : keySet) {
            String[] subKeySet = k.toString().split(",");
            for (String subKey : subKeySet) {
                if (subKey.trim().equals(key.toString())) {
                    return linkedHashMap.get(k);
                }                
            }            
        }
        return null;
    }
    
    
    
}
