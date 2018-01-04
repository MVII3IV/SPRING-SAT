package com.mvii3iv.sat.components.UserData;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserDataService {
    public static Map usersData = new HashMap<String, UserData>();
}
