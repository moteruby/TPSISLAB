package com.example.ERT.models;

import com.example.ERT.models.enums.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "active")
    private boolean active;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<Result> results = new ArrayList<>();
    @Column(name = "password", length = 1000)
    private String password;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
    private LocalDateTime dateOfCreated;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String icon;

    public User() {
    }

    public User(Long id, String email, String phoneNumber, String name, boolean active, List<Result> results, String password, Set<Role> roles, LocalDateTime dateOfCreated, String icon) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.active = active;
        this.results = results;
        this.password = password;
        this.roles = roles;
        this.dateOfCreated = dateOfCreated;
        this.icon = icon;
    }

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

    public boolean isAdmin() {
        return roles.contains(Role.ROLE_ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
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
    public boolean isEnabled() {
        return active;
    }

    public Long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getName() {
        return this.name;
    }

    public boolean isActive() {
        return this.active;
    }


    public String getPassword() {
        return this.password;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public LocalDateTime getDateOfCreated() {
        return this.dateOfCreated;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "User(id=" + this.getId() + ", email=" + this.getEmail() + ", phoneNumber=" + this.getPhoneNumber() + ", name=" + this.getName() + ", active=" + this.isActive() + ", results=" + this.getResults() + ", password=" + this.getPassword() + ", roles=" + this.getRoles() + ", dateOfCreated=" + this.getDateOfCreated() + ", icon=" + this.getIcon() + ")";
    }
}
