package com.bukalapak.hero.models

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*

@Entity // tell persistence provider 'Ability' is a persistence entity
class Ability {
  @Id // tell persistence provider 'id' is primary key
  @GeneratedValue( // tell persistence provider that value of 'id' will be generated
      strategy = GenerationType.IDENTITY // use RDBMS unique id generator
  )
  Long id

  String name

  @ManyToOne( // tell persistence provider 'hero' is a many-to-one relation with Ability
      fetch = FetchType.LAZY, // do not fetch value when Ability is loaded
      optional = false // will make the foreign key mandatory
  )
  @JsonIgnore // tell Spring to ignore 'hero' when creating JSON
  Hero hero
}
