package com.notes.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.notes.model.user.Role;
import com.notes.model.user.User;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CustomUserDetails implements UserDetails {

  private final Long id;
  private final String username;
  private final String password;
  private final Collection<SimpleGrantedAuthority> authorities;

  public CustomUserDetails(final User user) {
    this(user.getId(), user.getUsername(), user.getPassword());
    this.authorities.addAll(
        mapToGrantedAuthorities(new ArrayList<>(user.getRoles())));
  }

  private static List<SimpleGrantedAuthority> mapToGrantedAuthorities(
      final List<Role> roles) {
    return roles.stream()
        .map(Enum::name)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  private CustomUserDetails(
      final Long id, final String username, final String password) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.authorities = new ArrayList<>();
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
