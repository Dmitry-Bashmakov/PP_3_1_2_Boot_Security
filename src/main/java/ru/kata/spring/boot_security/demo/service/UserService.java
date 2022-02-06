package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.Utilizer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service("userDetailsServiceImpl")
public class UserService implements UserDetailsService {

    @PersistenceContext
    private EntityManager entMan;

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilizer utilizer = userDao.findByUsername(username);
        if (utilizer == null) {
            throw new UsernameNotFoundException("Пользователь " + username + "не найден");
        }
        return utilizer;
    }

    public Utilizer findUserById(Long id) {
        Optional<Utilizer> user = userDao.findById(id);
        return user.orElse(new Utilizer());
    }

    public List<Utilizer> allUsers() {
        return userDao.findAll();
    }

    public boolean saveUser(Utilizer utilizer) {
        Utilizer utilizerFromDB = userDao.findByUsername(utilizer.getUsername());
        if (utilizerFromDB != null) {
            return false;
        }
        utilizer.setRoles(Collections.singleton(new Role(1L, "USER")));
        utilizer.setPassword(bCryptPasswordEncoder.encode(utilizer.getPassword()));
        userDao.save(utilizer);
        return true;
    }

    public boolean deleteUserById(Long id) {
        if (userDao.existsById(id)) {
            userDao.deleteById(id);
            return true;
        }
        return false;
    }

    public void update(Utilizer user) {
        entMan.merge(user);
    }

    public List<Utilizer> userGetList(Long minId) {
        return entMan.createQuery("SELECT u FROM Utilizer u WHERE u.id > :paramId", Utilizer.class)
                .setParameter("paramId", minId).getResultList();
    }
}
