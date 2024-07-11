package KataBoot.security.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;




@Entity
@Table(name = "Role")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "role", nullable = false)
    private String role;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority(){
        return role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Role{" +
               "id=" + id +
               ", role='" + role + '\'' +
               '}';
    }
}
