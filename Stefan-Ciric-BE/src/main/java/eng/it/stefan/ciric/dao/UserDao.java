package eng.it.stefan.ciric.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eng.it.stefan.ciric.entity.UserEntity;


@Repository
public interface UserDao extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByUsernameAndPassword(String username, String password);

	@Query("SELECT u FROM UserEntity u WHERE u.username = ?1 AND u.password = ?2")
	Optional<UserEntity> login(String username, String password);
	
	Optional<UserEntity> findByUsername(String username);

}