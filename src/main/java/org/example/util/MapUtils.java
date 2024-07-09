package org.example.util;

import org.example.model.User;

import java.util.Map;

public class MapUtils {
    public static Double getValue(Map<User, Double> map, User key) {
        return map.get(key);
    }
}
