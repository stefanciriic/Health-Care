package eng.it.stefan.ciric.dto;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ExaminationDto {

	@NotNull
	private Long id;

	@Length(min = 5)
	private String identifier;

	@NotNull
	private String status;

	@NotEmpty
	private String serviceType;

	private String priority;

	private Date startDate;

	private Date endDate;

	private String diagnosis;

	private Long organizationId;
	
	private List<Long> practitionerIds;

	private Long patientId;

	public ExaminationDto() {
	}

	public ExaminationDto(@NotNull Long id, @Length(min = 5) String identifier, @NotNull String status,
			@NotEmpty String serviceType, String priority, Date startDate, Date endDate, String diagnosis,
			Long organizationId, List<Long> practitionerIds, Long patientId) {
		super();		
		this.id = id;
		this.identifier = identifier;
		this.status = status;
		this.serviceType = serviceType;
		this.priority = priority;
		this.startDate = startDate;
		this.endDate = endDate;
		this.diagnosis = diagnosis;
		this.organizationId = organizationId;
		this.practitionerIds = practitionerIds;
		this.patientId = patientId;
	}

	public List<Long> getPractitionerIds() {
		return practitionerIds;
	}



	public void setPractitionerIds(List<Long> practitionerIds) {
		this.practitionerIds = practitionerIds;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
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


	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(diagnosis, endDate, id, identifier, organizationId, patientId, priority, serviceType,
				startDate, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExaminationDto other = (ExaminationDto) obj;
		return Objects.equals(diagnosis, other.diagnosis) && Objects.equals(endDate, other.endDate)
				&& Objects.equals(id, other.id) && Objects.equals(identifier, other.identifier)
				&& Objects.equals(organizationId, other.organizationId) && Objects.equals(patientId, other.patientId)
				&& Objects.equals(priority, other.priority) && Objects.equals(serviceType, other.serviceType)
				&& Objects.equals(startDate, other.startDate) && Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "ExaminationDto [id=" + id + ", identifier=" + identifier + ", status=" + status + ", serviceType="
				+ serviceType + ", priority=" + priority + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", diagnosis=" + diagnosis + ", organizationId=" + organizationId + ", patientId=" + patientId + "]";
	}

	

	

}
