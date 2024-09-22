package eng.it.stefan.ciric.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eng.it.stefan.ciric.entity.ExaminationEntity;


@Repository
public interface ExaminationDao extends JpaRepository<ExaminationEntity,Long> {

	Optional<ExaminationEntity> findByIdentifier(String identifier);

	//here i am checking is there running examination inside organization with passed id //JPQL
	@Query(value="SELECT * FROM examination WHERE (examination.organization=:id and (examination.status in ('TRIAGED', 'IN_PROGRESS','PLANNED')))" ,nativeQuery=true)
	List<ExaminationEntity> checkIfOrganizationExist(@Param("id")Long id);
	
	//can't delete patient if he is under examination
	@Query(value="SELECT * FROM examination WHERE (examination.patient=:id and (examination.status in ('TRIAGED', 'IN_PROGRESS','PLANNED')))" ,nativeQuery=true)
	List<ExaminationEntity> checkIfPatientExist(@Param("id")Long id);
	

	@Query(value="SELECT * FROM examination WHERE (:status is null or examination.status like :status) and (:priority is null or examination.priority like :priority) and examination.status not like 'ENTERED_IN_ERROR'",nativeQuery=true)
	Page<ExaminationEntity> findByStatusAndPriorityAndWord(@Param("status")String status,@Param("priority")String priority,Pageable pagable);
	
	
}
