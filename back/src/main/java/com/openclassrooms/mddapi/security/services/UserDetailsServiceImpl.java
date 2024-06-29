package com.openclassrooms.mddapi.security.services;


import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

    return UserDetailsImpl
      .builder()
      .id(user.getId())
      .password(user.getPassword())
      .name(user.getName())
      .username(user.getEmail())
      .build();
  }
}
