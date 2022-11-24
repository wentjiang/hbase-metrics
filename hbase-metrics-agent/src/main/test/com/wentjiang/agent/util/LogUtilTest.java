package com.wentjiang.agent.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogUtilTest {

    @Test
    @DisplayName("test map2JsonString case 1")
    public void testLogOutPut1(){
        Map<String,String> map = new HashMap<>();
        map.put("testKey","testValue");
        String result = LogUtil.map2JsonString(map);
        String expect = "{\"testKey\":\"testValue\"}";
        assertEquals(expect,result);
    }

    @Test
    @DisplayName("test map2JsonString case 2")
    public void testLogOutPut2(){
        Map<String,String> map = new TreeMap<>();
        map.put("testKey","testValue");
        map.put("testKey1","testValue1");
        String result = LogUtil.map2JsonString(map);
        String expect = "{\"testKey\":\"testValue\",\"testKey1\":\"testValue1\"}";
        assertEquals(expect,result);
    }
}
