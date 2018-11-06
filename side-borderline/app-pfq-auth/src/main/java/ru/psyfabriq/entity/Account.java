package ru.psyfabriq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Account extends BaseIdEntity implements UserDetails {

    @Column(columnDefinition = "VARCHAR(25)", nullable = false)
    private String username;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String password;

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    private String email;

    @Column(columnDefinition = "INT(1)")
    private boolean enabled;

    @Column(name = "account_locked", columnDefinition = "INT(1)")
    private boolean accountNonLocked;

    @Column(name = "account_expired", columnDefinition = "INT(1)")
    private boolean accountNonExpired;

    @Column(name = "credentials_expired", columnDefinition = "INT(1)")
    private boolean credentialsNonExpired;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_account", joinColumns = {
            @JoinColumn(name = "account_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        roles.forEach(r -> {
            authorities.add(new SimpleGrantedAuthority(r.getName()));
            r.getPermissions().forEach(p -> {
                authorities.add(new SimpleGrantedAuthority(p.getName()));
            });
        });
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

