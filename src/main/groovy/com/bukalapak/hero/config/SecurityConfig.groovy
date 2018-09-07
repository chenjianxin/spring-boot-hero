package com.bukalapak.hero.config

import com.bukalapak.hero.models.Role
import com.bukalapak.hero.models.User
import com.bukalapak.hero.repositories.RoleRepository
import com.bukalapak.hero.repositories.UserRepository
import com.bukalapak.hero.services.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

import javax.transaction.Transactional

@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  CustomUserDetailsService userDetailsService

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder()
  }

  @Override
  void configure(AuthenticationManagerBuilder auth) {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider()
    authenticationProvider.userDetailsService = userDetailsService
    authenticationProvider.passwordEncoder = passwordEncoder()

    auth.authenticationProvider(authenticationProvider)
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic()
  }

  @SuppressWarnings("GrMethodMayBeStatic")
  @Autowired
  @Transactional
  void initUsers(
      RoleRepository roleRepository,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
    def roleAdmin = roleRepository.findById('ROLE_ADMIN').orElse(null)
    if (roleAdmin == null) {
      roleAdmin = new Role(
          authority: 'ROLE_ADMIN',
          description: 'THE MIGHTY MIGHTY ADMIN')
      roleRepository.save(roleAdmin)
    }

    def roleHero = roleRepository.findById('ROLE_HERO').orElse(null)
    if (roleHero == null) {
      roleHero = new Role(
          authority: 'ROLE_HERO',
          description: 'HEROES ONLY')
      roleRepository.save(roleHero)
    }

    def roleUser = roleRepository.findById('ROLE_USER').orElse(null)
    if (roleUser == null) {
      roleUser = new Role(
          authority: 'ROLE_USER',
          description: 'PUNY USER')
      roleRepository.save(roleUser)
    }

    def userAdmin = userRepository.findById('admin').orElse(null)
    if (userAdmin == null) {
      userAdmin = new User(
          username: 'admin',
          password: passwordEncoder.encode('1234'),
          authorities: [roleAdmin])
      userRepository.save(userAdmin)
    }

    def user = userRepository.findById('user').orElse(null)
    if (user == null) {
      user = new User(
          username: 'user',
          password: passwordEncoder.encode('1234'),
          authorities: [roleUser])
      userRepository.save(user)
    }
  }
}
