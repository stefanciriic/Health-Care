package eng.it.stefan.ciric.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eng.it.stefan.ciric.dto.UserDto;
import eng.it.stefan.ciric.security.service.MyUserDetails;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("auth")
public class AuthController {
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/login")
    public ResponseEntity<MyUserDetails> authenticateUser(@RequestBody UserDto userDto){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }
}
