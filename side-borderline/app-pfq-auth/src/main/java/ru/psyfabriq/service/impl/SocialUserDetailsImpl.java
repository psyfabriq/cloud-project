package ru.psyfabriq.service.impl;
/*
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;
import ru.psyfabriq.entity.Account;

public class SocialUserDetailsImpl implements SocialUserDetails {

    private static final long serialVersionUID = 1L;
    private final Account account;
    private List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

    public SocialUserDetailsImpl(Account account, List<String> roleNames) {
        this.account = account;

        for (String roleName : roleNames) {

            GrantedAuthority grant = new SimpleGrantedAuthority(roleName);
            this.list.add(grant);
        }
    }

    @Override
    public String getUserId() {
        return this.account.getId() + "";
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return list;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
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
        return true;
    }

}
*/