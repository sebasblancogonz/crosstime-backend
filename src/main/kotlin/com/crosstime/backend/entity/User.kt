package com.crosstime.backend.entity

import com.crosstime.backend.enums.UserType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnTransformer
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    var id: UUID? = null,
    @Column(name = "username") var alias: String? = null,
    @Column(name = "email") var email: String? = null,
    @Column(name = "password") var pwd: String? = null,
    @Enumerated(EnumType.STRING) @Column(name = "role")
    @ColumnTransformer(read = "UPPER(role)", write = "UPPER(?)")
    var role: Role? = null,
    @Enumerated(EnumType.STRING) @Column(name = "user_type")
    @ColumnTransformer(read = "UPPER(user_type)", write = "UPPER(?)")
    var userType: UserType? = null
) : UserDetails {

    @OneToMany(mappedBy = "user")
    var tokens: MutableList<Token> = mutableListOf()

    @OneToMany(mappedBy = "user")
    var reservations: MutableList<Reservation> = mutableListOf()

    override fun getAuthorities(): MutableList<SimpleGrantedAuthority> = role?.getAuthorities() ?: mutableListOf()
    override fun getPassword(): String = pwd!!
    override fun getUsername(): String = email!!
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

}