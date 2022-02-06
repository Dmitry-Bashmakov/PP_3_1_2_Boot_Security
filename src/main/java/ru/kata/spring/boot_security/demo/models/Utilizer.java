package ru.kata.spring.boot_security.demo.models;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "t_utilizer")
public class Utilizer implements Serializable, UserDetails {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @NotEmpty(message = "Значение не должно быть пустым")
    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @NotEmpty(message = "Значение не должно быть пустым")
    @Column(name = "lastName", nullable = false, length = 30)
    private String lastName;

    @Column(name = "age", nullable = true)
    @Min(value = 0, message = "Значение должно быть положительным")
    private Integer age;

    @Column(name = "username")
    @Size(min=2, message = "Ввести не менее 2-х знаков")
    private String username;

    @Column(name = "password")
    @Size(min=2, message = "Ввести не менее 2-х знаков")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public Utilizer() {}

    public Utilizer(long id, String name, String lastName, Integer age, String username, String password, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Utilizer(int id, String name, String lastName, Integer age) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }


    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setUsername(String username) {this.username = username;}

    public void setPassword(String password) {this.password = password;}

    public Set<Role> getRoles() {return roles;}

    public void setRoles(Set<Role> roles) {this.roles = roles;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {return password;}

    @Override
    public String getUsername() {return username;}

    @Override
    public boolean isAccountNonExpired() {return true;}

    @Override
    public boolean isAccountNonLocked() {return true;}

    @Override
    public boolean isCredentialsNonExpired() {return true;}

    @Override
    public boolean isEnabled() {return true;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizer that = (Utilizer) o;
        return Objects.equals(name, that.name) && Objects.equals(lastName, that.lastName) && Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, age);
    }
}
