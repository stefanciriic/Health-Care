package eng.it.stefan.ciric.service;

import eng.it.stefan.ciric.dto.UserDto;
import eng.it.stefan.ciric.exception.BadCredentialsException;

public interface UserService {
	UserDto login(String username, String password) throws BadCredentialsException;

}
