package eng.it.stefan.ciric.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import eng.it.stefan.ciric.entity.PatientEntity;

@Repository
public interface PatientDao extends JpaRepository<PatientEntity,Long>{


	Optional<PatientEntity> findByIdentifier(String identifier);

	@Modifying
	@Transactional
	@Query(value="UPDATE patient p SET p.organization = NULL WHERE p.organization=:id",nativeQuery=true)  //Hibernate query language
	void setOrganizationNull(@Param("id")Long id);
	
	@Query(value="SELECT * FROM patient WHERE (:gender is null or patient.gender like :gender) and (:maritalStatus is null or patient.marital_status like :maritalStatus) and (patient.name LIKE %:filterWord% or patient.surname LIKE %:filterWord%) and patient.active=true",nativeQuery=true)
	Page<PatientEntity> findByGenderAndMaritalStatusAndWord(@Param("gender")String gender,@Param("maritalStatus")String maritalStatus,@Param("filterWord")String filterWord ,Pageable pagable);
	
}
