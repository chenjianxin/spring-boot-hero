package com.bukalapak.hero.services

import com.bukalapak.hero.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
@Transactional
class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  UserRepository userRepository

  @Override
  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    def user = userRepository.findById(username).orElse(null)

    if (user == null) throw new UsernameNotFoundException(username)

    return user
  }
}


