package eng.it.stefan.ciric.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import eng.it.stefan.ciric.entity.util.Priority;
import eng.it.stefan.ciric.entity.util.Status;

@Entity
@Table(name = "examination")
@Where(clause="status!='ENTERED_IN_ERROR'")
@SQLDelete(sql = "UPDATE examination SET status = 'ENTERED_IN_ERROR' WHERE id=?")
public class ExaminationEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true, unique = true, length = 40)
	private String identifier;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15)
	private Status status;

	@ManyToOne
	@JoinColumn(name = "service_type", nullable = false)
	private ServiceTypeEntity serviceType;

	@Enumerated(EnumType.STRING)
	private Priority priority;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	private String diagnosis;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="examination_practitioner",
			joinColumns = @JoinColumn(name="examination_id"),
									inverseJoinColumns = @JoinColumn(name="practitioner_id"))
	private List<PractitionerEntity> practitionerEntity = new ArrayList<PractitionerEntity>();

	@ManyToOne
	@JoinColumn(name = "organization")
	private OrganizationEntity organizationEntity;

	@ManyToOne
	@JoinColumn(name = "patient")
	private PatientEntity patientEntity;

	public ExaminationEntity() {
		super();
	}

	public ExaminationEntity(Long id, String identifier, Status status, ServiceTypeEntity serviceType,
			Priority priority, Date startDate, Date endDate, String diagnosis, List<PractitionerEntity> practitionerEntity,
			OrganizationEntity organizationEntity, PatientEntity patientEntity) {
		super();
		this.id = id;
		this.identifier = identifier;
		this.status = status;
		this.serviceType = serviceType;
		this.priority = priority;
		this.startDate = startDate;
		this.endDate = endDate;
		this.diagnosis = diagnosis;
		this.practitionerEntity = practitionerEntity;
		this.organizationEntity = organizationEntity;
		this.patientEntity = patientEntity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public ServiceTypeEntity getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceTypeEntity serviceType) {
		this.serviceType = serviceType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}


	public List<PractitionerEntity> getPractitionerEntity() {
		return practitionerEntity;
	}

	public void setPractitionerEntity(List<PractitionerEntity> practitionerEntity) {
		this.practitionerEntity = practitionerEntity;
	}

	public OrganizationEntity getOrganizationEntity() {
		return organizationEntity;
	}

	public void setOrganizationEntity(OrganizationEntity organizationEntity) {
		this.organizationEntity = organizationEntity;
	}

	public PatientEntity getPatientEntity() {
		return patientEntity;
	}

	public void setPatientEntity(PatientEntity patientEntity) {
		this.patientEntity = patientEntity;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(diagnosis, endDate, id, identifier, organizationEntity, patientEntity, practitionerEntity,
				priority, serviceType, startDate, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExaminationEntity other = (ExaminationEntity) obj;
		return Objects.equals(diagnosis, other.diagnosis) && Objects.equals(endDate, other.endDate)
				&& Objects.equals(id, other.id) && Objects.equals(identifier, other.identifier)
				&& Objects.equals(organizationEntity, other.organizationEntity)
				&& Objects.equals(patientEntity, other.patientEntity)
				&& Objects.equals(practitionerEntity, other.practitionerEntity) && priority == other.priority
				&& Objects.equals(serviceType, other.serviceType) && Objects.equals(startDate, other.startDate)
				&& status == other.status;
	}

	@Override
	public String toString() {
		return "ExaminationEntity [id=" + id + ", identifier=" + identifier + ", status=" + status + ", serviceType="
				+ serviceType + ", priority=" + priority + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", diagnosis=" + diagnosis + "]";
	}

	

}
