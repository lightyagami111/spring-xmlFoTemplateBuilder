/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivaylorusev.xmlFoTemplateBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Ivaylo Rusev
 */
public class HashMapUtils {

    public static <K,V> List<Object> findMatchedKeys(HashMap<K,V> linkedHashMap, Object key) {
        List<Object> resultKeys = new ArrayList<>();

        Set<K> keySet = linkedHashMap.keySet();
        for (K k : keySet) {
            String[] subKeySet = k.toString().split(",");
            for (String subKey : subKeySet) {
                if (subKey.trim().equals(key.toString())) {
                    resultKeys.add(k);
                }
            }
        }
        return resultKeys;
    }

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
