package com.fotaleza.fortalezaapi.security.service;

import com.fotaleza.fortalezaapi.model.Role;
import com.fotaleza.fortalezaapi.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private boolean isActivated;
    private boolean isBlocked;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(User user) {

        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(Role::getName)
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmployee().getFirstName(),
                user.getEmployee().getLastName(),
                user.getPassword(),
                user.getIsActivate(),
                user.getIsBlocked(),
                authorities);
    }

    @Override
    public Collection<? extends  GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() { return username; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return !isBlocked; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return isActivated; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDetailsImpl that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
