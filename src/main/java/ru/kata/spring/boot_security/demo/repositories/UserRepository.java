package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.MyUser;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {

    @Query("select u from MyUser u where u.username = ?1")
    MyUser findByUsername(String username);

    @Query("select u from MyUser u where u.id = ?1")
    MyUser findUserById(Long id);


}
