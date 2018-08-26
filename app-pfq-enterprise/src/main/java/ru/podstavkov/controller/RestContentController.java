
package ru.podstavkov.controller;

import java.time.Instant;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.podstavkov.dto.ApiResponse;
import ru.podstavkov.entity.Article;
import ru.podstavkov.entity.Category;
import ru.podstavkov.entity.Teg;
import ru.podstavkov.model.UserPrincipal;
import ru.podstavkov.service.ArticleService;
import ru.podstavkov.service.CategoryService;
import ru.podstavkov.service.TegService;
import ru.podstavkov.utils.annotation.CurrentUser;
import ru.podstavkov.utils.annotation.LogAfter;


@RestController
@RequestMapping("/api/content")
public class RestContentController {
	
	@Autowired
    private ArticleService articleService;
	
	@Autowired
	private TegService tegService;
	
	@Autowired
	private CategoryService categoryService;
	


    private static final Logger logger = LoggerFactory.getLogger(RestContentController.class);

    @GetMapping("/article/{articleid}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getAticle(@PathVariable(value = "articleid") String articleid) {
    	if(articleid.isEmpty()) {
    		return new ResponseEntity(new ApiResponse(false, "Not set ID"),HttpStatus.BAD_REQUEST);
    	}else if(!articleService.existsByID(articleid)) {
    		return new ResponseEntity(new ApiResponse(false, "Not found"),HttpStatus.BAD_REQUEST);
    	}
       
        return  ResponseEntity.ok(articleService.getByID(articleid));
    }
    
    @GetMapping("/teg/{tegid}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getTeg(@PathVariable(value = "tegid") String tegid) {
    	if(tegid.isEmpty()) {
    		return new ResponseEntity(new ApiResponse(false, "Not set ID"),HttpStatus.BAD_REQUEST);
    	}else if(!tegService.existsByID(tegid)) {
    		return new ResponseEntity(new ApiResponse(false, "Not found"),HttpStatus.BAD_REQUEST);
    	}
       
        return  ResponseEntity.ok(tegService.getByID(tegid));
    }
    
    
    @GetMapping("/category/{categoryid}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getCategory(@PathVariable(value = "categoryid") String categoryid) {
    	if(categoryid.isEmpty()) {
    		return new ResponseEntity(new ApiResponse(false, "Not set ID"),HttpStatus.BAD_REQUEST);
    	}else if(!categoryService.existsByID(categoryid)) {
    		return new ResponseEntity(new ApiResponse(false, "Not found to update"),HttpStatus.BAD_REQUEST);
    	}
       
        return  ResponseEntity.ok(categoryService.getByID(categoryid));
    }
    
    @PostMapping("/article/{articleid}/edit")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> editAticle(@PathVariable(value = "articleid") String articleid,@Valid @RequestBody Article articleRequest,@CurrentUser UserPrincipal currentUser) {
    	if(articleid.isEmpty()) {
    		return new ResponseEntity(new ApiResponse(false, "Not set ID"),HttpStatus.BAD_REQUEST);
    	}else if(!articleService.existsByID(articleid)) {
    		return new ResponseEntity(new ApiResponse(false, "Not found to update"),HttpStatus.BAD_REQUEST);
    	}
    	
    	articleRequest.setUpdatedAt(Instant.now());
    	articleRequest.setUpdatedBy(currentUser.getId());
       
        return  ResponseEntity.ok(articleService.edit(articleRequest));
    }
    
    @PostMapping("/teg/{tegid}/edit")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> editTeg(@PathVariable(value = "tegid") String tegid,@Valid @RequestBody Teg tegRequest,@CurrentUser UserPrincipal currentUser) {
    	if(tegid.isEmpty()) {
    		return new ResponseEntity(new ApiResponse(false, "Not set ID"),HttpStatus.BAD_REQUEST);
    	}else if(!tegService.existsByID(tegid)) {
    		return new ResponseEntity(new ApiResponse(false, "Not found to update"),HttpStatus.BAD_REQUEST);
    	}
       
    	tegRequest.setUpdatedAt(Instant.now());
    	tegRequest.setUpdatedBy(currentUser.getId());
    	
    	
        return  ResponseEntity.ok(tegService.edit(tegRequest));
    }
    
    
    @PostMapping("/category/{categoryid}/edit")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> editCategory(@PathVariable(value = "categoryid") String categoryid,@Valid @RequestBody Category categoryRequest,@CurrentUser UserPrincipal currentUser) {
    	if(categoryid.isEmpty()) {
    		return new ResponseEntity(new ApiResponse(false, "Not set ID"),HttpStatus.BAD_REQUEST);
    	}else if(!categoryService.existsByID(categoryid)) {
    		return new ResponseEntity(new ApiResponse(false, "Not found"),HttpStatus.BAD_REQUEST);
    	}
    	categoryRequest.setUpdatedAt(Instant.now());
    	categoryRequest.setUpdatedBy(currentUser.getId());
       
        return  ResponseEntity.ok(categoryService.edit(categoryRequest));
    }
    
    
    @PostMapping("/articles/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> addAticle(@Valid @RequestBody Article articleRequest,@CurrentUser UserPrincipal currentUser) {
        if(articleService.existsByID(articleRequest.getId())) {
    		return new ResponseEntity(new ApiResponse(false, "Coud not add article "),HttpStatus.BAD_REQUEST);
    	}
    	
    	articleRequest.setCreatedAt(Instant.now());
    	articleRequest.setUpdatedAt(Instant.now());
    	
    	articleRequest.setCreatedBy(currentUser.getId());
    	articleRequest.setUpdatedBy(currentUser.getId());
       
        return  ResponseEntity.ok(articleService.add(articleRequest));
    }
    
    @PostMapping("/tegs/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> addTeg(@Valid @RequestBody Teg tegRequest,@CurrentUser UserPrincipal currentUser) {
    	
        if(tegService.existsByID(tegRequest.getId())) {
    		return new ResponseEntity(new ApiResponse(false, "Coud not add teg "),HttpStatus.BAD_REQUEST);
    	}
       
        tegRequest.setCreatedAt(Instant.now());
        tegRequest.setUpdatedAt(Instant.now());
    	 
        tegRequest.setCreatedBy(currentUser.getId());
        tegRequest.setUpdatedBy(currentUser.getId());
    	
    	
        return  ResponseEntity.ok(tegService.add(tegRequest));
    }
    
    
    @PostMapping("/categories/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> addCategory(@Valid @RequestBody Category categoryRequest,@CurrentUser UserPrincipal currentUser) {
    	
        if(categoryService.existsByID(categoryRequest.getId())) {
    		return new ResponseEntity(new ApiResponse(false, "Coud not add category "),HttpStatus.BAD_REQUEST);
    	}
        
        
        categoryRequest.setCreatedAt(Instant.now());
        categoryRequest.setUpdatedAt(Instant.now());
    	
        categoryRequest.setCreatedBy(currentUser.getId());
        categoryRequest.setUpdatedBy(currentUser.getId());
       
        return  ResponseEntity.ok(categoryService.add(categoryRequest));
    }
    
    @GetMapping("/articles/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getAticleToAdd() {
        return  ResponseEntity.ok(new Article());
    }
    
    @GetMapping("/tegs/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getTegToAdd() {
        return  ResponseEntity.ok(new Teg());
    }
    
    
    @GetMapping("/categories/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getCategoryToAdd() {
        return  ResponseEntity.ok(new Category());
    }
    

    	    
    @PostMapping("/test")
    @PreAuthorize("hasRole('ROLE_USER')")
    @LogAfter
    public ResponseEntity<?> testMethod(@Valid @RequestBody Article categoryRequest) {
    
       
        return  ResponseEntity.ok(categoryRequest);
    }
    


/*
    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

*/

}
