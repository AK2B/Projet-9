package com.microservice.gateway.configuration;
/*
@Configuration
public class InMemorySecurityConfig extends InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> {

    @Bean
    public UserDetailsManager userDetailsManager() {
      
        UserDetails organizer = User.withDefaultPasswordEncoder()
            .username("organizer")
            .password("password")
            .roles("ORGANIZER")
            .build();

        UserDetails practitioner = User.withDefaultPasswordEncoder()
            .username("practitioner")
            .password("password")
            .roles("PRACTITIONER")
            .build();

        UserDetails patient = User.withDefaultPasswordEncoder()
            .username("patient")
            .password("password")
            .roles("PATIENT")
            .build();

        return new InMemoryUserDetailsManager(organizer, practitioner, patient);
    }
}*/
