package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.MyUser;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public MyUser findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public List<MyUser> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean saveUser(MyUser myUser) {
        MyUser myUserFromDB = userRepository.findByUsername(myUser.getUsername());
        if (myUserFromDB != null) {
            return false;
        }
        myUser.setPassword(bCryptPasswordEncoder.encode(myUser.getPassword()));
        userRepository.save(myUser);
        return true;
    }

    @Override
    public boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void update(MyUser user) {
        userRepository.save(user);
    }

}
