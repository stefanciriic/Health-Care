package eng.it.stefan.ciric.service.impl;

import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import eng.it.stefan.ciric.dao.UserDao;
import eng.it.stefan.ciric.dto.UserDto;
import eng.it.stefan.ciric.entity.UserEntity;
import eng.it.stefan.ciric.exception.BadCredentialsException;
import eng.it.stefan.ciric.mapper.UserMapper;
import eng.it.stefan.ciric.service.UserService;


@Service
public class UserServiceImpl implements UserService{
	private final UserDao userDao;

	private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

	public UserServiceImpl(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@Override
	public UserDto login(String username, String password) throws BadCredentialsException {
		Optional<UserEntity> user = userDao.login(username, password);
		if (!user.isPresent()) {
			throw new BadCredentialsException("incorrect username or password!");
		}
		return userMapper.toDto(user.get());
	}
}
