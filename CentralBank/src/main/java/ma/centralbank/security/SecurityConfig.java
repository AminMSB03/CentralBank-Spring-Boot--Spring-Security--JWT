package ma.centralbank.security;

import ma.centralbank.services.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   private UserService userService;
   private PasswordEncoder passwordEncoder;

   public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
      this.userService = userService;
      this.passwordEncoder = passwordEncoder;
   }

   @Bean
   UserDetailsService userDetailsService(){
      return email -> {
         ma.centralbank.models.User user = userService.loadUserByEmail(email);
         GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
         List<GrantedAuthority> authorities = Collections.singletonList(authority);

         return new User(user.getEmail(), user.getPassword(),authorities);
      };
   }


   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http.csrf(csrf->csrf.disable());
      http
              .authorizeHttpRequests(auth->auth.anyRequest().authenticated())
               .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .httpBasic(Customizer.withDefaults());
      http.addFilter(new JwtAuthenticationFilter(authenticationManager()));
      http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
      return http.build();
   }

   @Bean
   public AuthenticationManager authenticationManager() throws Exception {
      // create an authentication manager with a custom user details service
      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
      provider.setUserDetailsService(userDetailsService());
      provider.setPasswordEncoder(this.passwordEncoder);
      return new ProviderManager(Arrays.asList(provider));
   }

}
