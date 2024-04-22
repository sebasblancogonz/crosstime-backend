package com.crosstime.backend.entity

import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.Collections
import java.util.stream.Collectors

enum class Role(
    val permissions: Set<Permission>
) {
    USER(Collections.emptySet()),
    ADMIN(setOf(
        Permission.ADMIN_READ,
        Permission.ADMIN_WRITE,
        Permission.ADMIN_CREATE,
        Permission.ADMIN_DELETE
    ));

    open fun getAuthorities(): MutableList<SimpleGrantedAuthority> {
        val authorities: MutableList<SimpleGrantedAuthority> = permissions
            .stream()
            .map { permission -> SimpleGrantedAuthority(permission.permission) }
            .collect(Collectors.toList())
        authorities.add(SimpleGrantedAuthority("ROLE_$name"))
        return authorities
    }
}