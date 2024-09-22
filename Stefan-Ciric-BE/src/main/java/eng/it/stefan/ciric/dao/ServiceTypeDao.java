package eng.it.stefan.ciric.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import eng.it.stefan.ciric.entity.ServiceTypeEntity;

public interface ServiceTypeDao extends JpaRepository<ServiceTypeEntity,Long>{

	Optional<ServiceTypeEntity> findByName(String serviceType);

}
