package net.cis.configuration;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.cis.jpa.entity.security.AuthenticationUser;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	
	@Transactional(readOnly = true)
	public AuthenticationUser loadUserByUsername(String identifier) throws UsernameNotFoundException {
	
		return null;
	}

	

}
