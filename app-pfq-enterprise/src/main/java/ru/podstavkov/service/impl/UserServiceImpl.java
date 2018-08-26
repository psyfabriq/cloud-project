package ru.podstavkov.service.impl;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.podstavkov.dto.UserProfile;
import ru.podstavkov.dto.UserRequest;
import ru.podstavkov.entity.User;
import ru.podstavkov.repository.UserRepository;
import ru.podstavkov.service.UserService;
import ru.podstavkov.utils.annotation.LogBefore;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    PasswordEncoder passwordEncoder;
	  
	@Override
	public User addUser(User userresult) {
		return userRepository.saveAndFlush(userresult);
	}

	@Override
	public void delete(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User getByName(String name) {
		return userRepository.findByUsername(name).get();
	}
	
	@Override
	@LogBefore
	public User editUser(User userresult) {
		return userRepository.saveAndFlush(userresult);
	}

	@Override
	public List<User> getAll() {
		  return userRepository.findAll();
	}

	@Override
	public User getByNameOrEmail(String name, String email) {
		return userRepository.findByUsernameOrEmail(name,email).get();
	}

	@Override
	
	public User getByEmail(String email) {
		
		return userRepository.findByEmail(email).get();
	}

	@Override
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
	
	@Override
	public Boolean existsByCode(String code) {
		return userRepository.existsByCode(code);
	}
	
	@Override
	public User getByCode(String code) {
		return userRepository.findByCode(code).get();
	}


	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	


	@Override
	public Page<UserProfile> findPaginated(Pageable pageable) {
		
		    int pageSize = pageable.getPageSize();
	        int currentPage = pageable.getPageNumber();
	        int startItem = currentPage * pageSize;
	        List<UserProfile> list;
	        List<UserProfile> users = (List<UserProfile>)(Object)getAll();
	        
	        if (users.size() < startItem) {
	            list = Collections.emptyList();
	        } else {
	            int toIndex = Math.min(startItem + pageSize, users.size());
	            list = users.subList(startItem, toIndex);
	        }
	        
	        Page<UserProfile> profilePage = new PageImpl<UserProfile>(list, PageRequest.of(currentPage, pageSize), users.size());
	        
		return profilePage;
	}

	@Override
	public Optional<User> convertDtoToEntityRequest(UserRequest dto) {
		User user = userRepository.findByUsernameOrEmail(dto.getName(), dto.getEmail()).get();
		user.setAboutme(dto.getAboutme());
		user.setCity(dto.getCity());
		user.setCountry(dto.getCountry());
		user.setEmail(dto.getEmail());
		user.setName(dto.getName());
		user.setSecondname(dto.getSecondname());
		user.setLastname(dto.getLastname());
		user.setUpdatedAt(Instant.now()); 
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		return Optional.ofNullable(user);
	}

	@Override
	public Optional<UserRequest> convertEntityToDTORequest(User entity) {
		UserRequest signUpRequest = new UserRequest(entity);
		return Optional.ofNullable(signUpRequest);
	}

	@Override
	public Optional<User> convertDtoToEntityResponse(UserProfile dto) {
		//User user = userRepository.findby(dto., dto.getEmail()).get();

		return null;
	}

	@Override
	public Optional<UserProfile> convertEntityToDTOResponse(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
