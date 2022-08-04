package com.vthz2.app2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vthz2.app2.dao.UserRepository;
import com.vthz2.app2.entities.User;

@Service
public class CuserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		User user=userRepository.findByUsername(name);
		if (user==null) {
			throw new UsernameNotFoundException("User 404");
		}
		return new UserImpl(user);
	}
	
	public List<User> getAllUserS(){
		List<User> result=userRepository.getAllUser();
		return result;
	}

}
