package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.MyUser;

import java.util.List;

public interface UserService {
    public MyUser findUserById(Long id);

    public List<MyUser> allUsers();

    public boolean saveUser(MyUser myUser/*, Set<Role> roles*/);

    public boolean deleteUserById(Long id);

    void update(MyUser user);
}
