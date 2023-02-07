package com.greatlearning.employee.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.greatlearning.employee.entity.User;
import com.greatlearning.employee.repository.UserJpaRepository;
import com.greatlearning.employee.security.MyUserDetails;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	UserJpaRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.getUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("not found");
		}

		return new MyUserDetails(user);
	}

}
