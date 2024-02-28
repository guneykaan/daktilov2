package com.daktilo.daktilo_backend.constants;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.daktilo.daktilo_backend.constants.Permission.*;

public enum Role {
    AUTHOR(
            Set.of(
                    AUTHOR_READ,
                    AUTHOR_WRITE
            )
    ),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_WRITE
            )
    ),
    READER(Collections.emptySet());


    private Set<Permission> permissions;


    public Set<Permission> getPermissions() {
        return permissions;
    }

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

}
