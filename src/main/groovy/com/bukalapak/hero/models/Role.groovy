package com.bukalapak.hero.models

import org.springframework.security.core.GrantedAuthority

import javax.persistence.*

@Entity
class Role implements GrantedAuthority {
  @Id
  String authority

  String description
}
