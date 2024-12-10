package com.fresh.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private UserDTO userDTO;

    public CustomUserDetails(UserDTO user) {
        this.userDTO = user; 
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userDTO.getROLE();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return userDTO.getUser_pw();
    }

    @Override
    public String getUsername() {
        return userDTO.getUser_id();
    }
    
    public String getUserUserName() {
        return userDTO.getUser_username();
    }

    // 사용자의 이름 반환
    public String getName() {
        return userDTO.getUser_name();
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