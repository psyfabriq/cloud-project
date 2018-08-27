package ru.psyfabriq;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ErrorController {
	
	    @GetMapping("/401")
	    @ResponseStatus(HttpStatus.UNAUTHORIZED)
	    public  String unAuthorized(Model model) {
	        return "error";
	    }
	    @GetMapping("/404")
	    @ResponseStatus(HttpStatus.NOT_FOUND)
	    public  String notFound(Model model) {
	        return "error";
	    }
 
	    @GetMapping("/403")
	    @ResponseStatus(HttpStatus.FORBIDDEN)
	    public  String forbidden(Model model) {
	        return "error";
	    }

	    @GetMapping("/500")
	    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	    public  String internalServerError(Model modell) {
	        return "error";
	    }
}
