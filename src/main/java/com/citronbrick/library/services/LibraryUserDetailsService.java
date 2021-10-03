package com.citronbrick.library.services;

import com.citronbrick.library.entities.*;
import com.citronbrick.library.repositories.*;

import org.springframework.security.core.userdetails.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.*;

import java.util.*;



@Service
public class LibraryUserDetailsService implements UserDetailsService {

	private LibraryUserRepository userRepository;
	private PasswordEncoder encoder;

	public LibraryUserDetailsService(@Autowired LibraryUserRepository userRepository, @Autowired PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final LibraryUser user = userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException(username + " not found");
		} else {
			return new UserDetails() {

				public Collection<? extends GrantedAuthority> getAuthorities() {
					return user.getAuthorities();
				}

				public String getUsername() {
					return user.getUsername();
				}

				public String getPassword() {
					return encoder.encode(user.getPassword());
				}

				public boolean isAccountNonExpired() {
					return true;
				}

				public boolean isAccountNonLocked() {
					return true;
				}

				public boolean isCredentialsNonExpired() {
					return true;
				}

				public boolean isEnabled() {
					return true;
				}

			};
		}
	}
}
