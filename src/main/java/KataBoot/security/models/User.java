package KataBoot.security.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "User")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "id")
    private long id;



    @Pattern(message = "Bad formed user name: ${validatedValue} \n" +
                       "Name should starts with a capital letter and not contain symbols or numbers",
            regexp = "^[A-Z][a-z]*(\\s(([a-z]{1,3})|(([a-z]+\\')?[A-Z][a-z]*)))*$")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2 , max = 30, message = "Name should be more than 2 and less then 30 characters")
    @Column(name = "name")
    private String name;

    @Pattern(message = "Bad formed user surname: ${validatedValue} \n" +
                       "Surname should starts with a capital letter and not contain symbols or numbers",
            regexp = "^[A-Z][a-z]*(\\s(([a-z]{1,3})|(([a-z]+\\')?[A-Z][a-z]*)))*$")
    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 2, max = 40, message = "Surname should be between 2 and 40 characters")
    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name="Email", unique = true)
    @Email

    @NotEmpty(message = "Email should not be empty")
    private String email;

    @Size(min = 4, message = "Password should be between 4 and 20 characters")
    @Column(name = "password")
    private String password;


    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roleList;


    public User(String name, String surname, String email, String password, Set<Role> roles ) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.roleList = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleList.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getAuthority()))
                .collect(Collectors.toList());
    }

    @Override
    public  String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword ( String password) {
        this.password = password;
    }

    public Set<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(Set<Role> roleList) {
        this.roleList = roleList;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MyUser{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", surname='" + surname + '\'' +
               ", email='" + email + '\'' +
               ", password='" + password + '\'' +
               ", roleList=" + roleList +
               '}';
    }

    public User(){}
}

