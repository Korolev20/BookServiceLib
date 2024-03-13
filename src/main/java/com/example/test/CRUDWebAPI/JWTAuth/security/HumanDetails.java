package com.example.test.CRUDWebAPI.JWTAuth.security;

import com.example.test.CRUDWebAPI.JWTAuth.models.Human;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class HumanDetails implements UserDetails {
    private final Human human;

    public HumanDetails(Human human) {
        this.human = human;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.human.getPassword();
    }

    @Override
    public String getUsername() {
        return this.human.getUsername();
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
    public Human getHuman(){
        return this.human;
    }
}
