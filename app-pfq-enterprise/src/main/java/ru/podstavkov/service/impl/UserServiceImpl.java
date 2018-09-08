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
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	public User addUser(User userresult) {
		return userRepository.saveAndFlush(userresult);
	}

	@Override
	@Transactional
	public void delete(long id) {
		userRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public User getByName(String name) {
		return userRepository.findByUsername(name).get();
	}
	
	@Override
	@LogBefore
	@Transactional
	public User editUser(User userresult) {
		return userRepository.saveAndFlush(userresult);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getAll() {
		  return userRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public User getByNameOrEmail(String name, String email) {
		return userRepository.findByUsernameOrEmail(name,email).get();
	}

	@Override
	@Transactional(readOnly = true)
	public User getByEmail(String email) {
		
		return userRepository.findByEmail(email).get();
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Boolean existsByCode(String code) {
		return userRepository.existsByCode(code);
	}
	
	@Override
	@Transactional(readOnly = true)
	public User getByCode(String code) {
		return userRepository.findByCode(code).get();
	}


	@Override
	@Transactional(readOnly = true)
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
