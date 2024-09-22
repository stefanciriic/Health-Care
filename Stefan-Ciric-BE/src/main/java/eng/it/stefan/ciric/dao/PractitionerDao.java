package eng.it.stefan.ciric.dao;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eng.it.stefan.ciric.entity.PractitionerEntity;

@Repository
public interface PractitionerDao extends JpaRepository<PractitionerEntity,Long>{

	Optional<PractitionerEntity> findByName(String name);


	Optional<PractitionerEntity> findByIdentifier(String identifier);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE practitioner p SET p.organization = NULL WHERE p.organization=:id",nativeQuery=true)
	void setOrganizationNull(@Param("id")Long id);
		
	@Query(value="SELECT * FROM practitioner WHERE (:gender is null or practitioner.gender=:gender) AND (:qualification is null or practitioner.qualification=:qualification)",nativeQuery=true)
	Page<PractitionerEntity> findByGenderAndQualification(@Param("gender")String gender,@Param("qualification")String qualification, Pageable pagable);
							 	
	
	
	
}
