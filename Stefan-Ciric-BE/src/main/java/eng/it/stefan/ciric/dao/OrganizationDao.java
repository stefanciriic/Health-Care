package eng.it.stefan.ciric.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eng.it.stefan.ciric.entity.OrganizationEntity;

@Repository
public interface OrganizationDao extends JpaRepository<OrganizationEntity,Long> {

	Optional<OrganizationEntity> findByName(String name);

	Optional<OrganizationEntity> findByIdentifier(String identifier); 
	
	Page<OrganizationEntity> findByTypeNameContainingAndNameContaining(String typeName,String name,Pageable pageable);
	
}
