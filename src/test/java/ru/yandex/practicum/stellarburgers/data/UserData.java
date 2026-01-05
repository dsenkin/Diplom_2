package ru.yandex.practicum.stellarburgers.data;

import org.apache.commons.lang3.RandomStringUtils;

public class UserData {
    public String name;
    private String password;
    private String email;

    public UserData(){
        name = RandomStringUtils.randomAlphabetic(7);
        password = RandomStringUtils.randomAlphabetic(10);
        email = RandomStringUtils.randomAlphabetic(5)+"@"+RandomStringUtils.randomAlphabetic(5)+".ru";
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
