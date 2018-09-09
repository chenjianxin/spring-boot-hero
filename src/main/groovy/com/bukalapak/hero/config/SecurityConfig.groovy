package com.bukalapak.hero.config

import com.bukalapak.hero.models.Role
import com.bukalapak.hero.models.User
import com.bukalapak.hero.repositories.RoleRepository
import com.bukalapak.hero.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

import javax.transaction.Transactional

@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  UserDetailsService userDetailsService

  // register password encoder as Bean; will be needed in several places
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder()
  }

  // configure authentication manager
  @Override
  void configure(AuthenticationManagerBuilder auth) {
        // authenticate user by using our custom userDetailsService
    auth.userDetailsService(userDetailsService)
        // use registered PasswordEncoder
        .passwordEncoder(passwordEncoder())
  }

  // configure http security
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic() // provide a basic login
        .and().csrf().disable() // disabled CSRF
  }

  // initialize users and roles
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
