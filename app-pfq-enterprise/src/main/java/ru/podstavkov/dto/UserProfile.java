package ru.podstavkov.dto;

import java.time.Instant;

import lombok.Data;
import ru.podstavkov.entity.User;

@Data 
public class UserProfile {
	    private Long id;
	    private String username;
	    private String name;
	    private String secondname;
	    private String lastname;
	    private String aboutme;
	    private String city;
	    private String country;
	    private String email;
	    private Instant joinedAt;



	    
	    public UserProfile(User user) {
	        this.id = user.getId();
	        this.username = user.getUsername();
	        this.name = user.getName();
	        this.email = user.getEmail();
	        this.joinedAt =  user.getCreatedAt();
            this.secondname = user.getSecondname();
            this.lastname   = user.getLastname();
            this.city       = user.getCity();
            this.country    = user.getCountry();
	    }

	   
		
		
	    
	    

}
