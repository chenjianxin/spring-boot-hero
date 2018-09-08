package com.bukalapak.hero.config

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration

@EnableGlobalMethodSecurity(
    prePostEnabled = true, // Enable @Pre~ & @Post~ annotations
    securedEnabled = true, // Enable @Secured annotations
    jsr250Enabled = true) // Enable JSR-250 specification annotations
                          //   such as @PermitAll @DenyAll @RolesAllowed
class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {}
