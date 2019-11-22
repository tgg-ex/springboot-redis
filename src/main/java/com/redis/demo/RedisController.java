package com.redis.demo;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class RedisController {

    // redisTemplate.opsForValue();//操作字符串
    // redisTemplate.opsForHash();//操作hash
    // redisTemplate.opsForList();//操作list
    // redisTemplate.opsForSet();//操作set
    // redisTemplate.opsForZSet();//操作有序set
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/string")
    public String stringDemo(@RequestParam("param") String param) {
        redisTemplate.opsForValue().set("test1", param);
//       return JSONObject.parseObject(redisTemplate.opsForValue().get("test1").getClass().getName());
        String test1 = redisTemplate.opsForValue().get("test1").toString();
        System.out.println(test1);
        return "ok";
    }

    @RequestMapping("/set")
    public String setDemo(@RequestParam("param") String param) {
        Set<String> set = new HashSet<>();
        set.add("test1");
        set.add("test2");
        set.add("test3");
        set.add("test4");
        redisTemplate.opsForSet().add("testSet", set);
        System.out.println(set);

        Set<String> setData = redisTemplate.opsForSet().members("testSet");
        System.out.println(setData);
        return "ok";
    }

    @RequestMapping("/list")
    public String listDemo(@RequestParam("param") String param) {
        List<String> list = new ArrayList<>();
        list.add(param);
        list.add("123456");
        list.add("789456");
        redisTemplate.opsForList().leftPush("testList", list);
        List<String> testList = (List<String>) redisTemplate.opsForList().leftPop("testList");
        System.out.println(testList);
        return "ok";
    }

    @RequestMapping("/hash")
    public String hashDemo(@RequestParam("param") String param) {
        Map<String, String> map = new HashMap<>();
        map.put("key1", param);
        map.put("key2", "456");
        map.put("key3", "789");
        map.put("key4", "741");
        redisTemplate.opsForHash().putAll("map1", map);
        //获取所有key
        Set map1 = redisTemplate.opsForHash().keys("map1");
        System.out.println(map1);

        //通过key 获取 values
        String key1 = (String) redisTemplate.opsForHash().get("map1", "key1");
        System.out.println(key1);
        return "ok";
    }

}
