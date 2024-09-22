package eng.it.stefan.ciric.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import eng.it.stefan.ciric.entity.OrganizationTypeEntity;

@Repository
public interface OrganizationTypeDao extends JpaRepository<OrganizationTypeEntity, Long>{

	Optional<OrganizationTypeEntity> findByName(String name);

	Optional<OrganizationTypeEntity> findById(String type); 
}
