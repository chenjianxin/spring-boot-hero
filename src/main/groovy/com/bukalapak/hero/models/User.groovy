package com.bukalapak.hero.models

import org.springframework.security.core.userdetails.UserDetails

import javax.persistence.*

@Entity
class User implements UserDetails {
  @Id
  String username

  String password

  boolean enabled = true

  boolean accountNonExpired = true

  boolean accountNonLocked = true

  boolean credentialsNonExpired = true

  @OneToOne
  Hero hero

  @ManyToMany
  @JoinTable(
      name = 'user_role',
      joinColumns = @JoinColumn(name = 'username'),
      inverseJoinColumns = @JoinColumn(name = 'authority'))
  Set<Role> authorities
}
