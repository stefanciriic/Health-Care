package eng.it.stefan.ciric.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import eng.it.stefan.ciric.entity.util.Gender;
import eng.it.stefan.ciric.entity.util.MaritalStatus;

@Entity
@Table(name="patient")
@Where(clause="active=true")
@SQLDelete(sql = "UPDATE patient SET active = false WHERE id=?")
public class PatientEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true,
			length=40)
	private String identifier;
	
	@Column(nullable=false)
	private boolean active;
	
	@Column(nullable=false,
			length=40)
	private String name;
	
	@Column(nullable=false,
			length=40)
	private String surname;
	
	@Enumerated(EnumType.STRING)
	@Column(length=10)
	private  Gender gender;
	
	@Column(name="birth_date",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	@Column(length=50)
	private String address;
	
	@Column(length=20)
	private String phone;
	
	@Column(length=30)
	private String email;
	
	private boolean deceased;
	
	@Enumerated(EnumType.STRING)
	@Column(name="marital_status")
	private  MaritalStatus maritalStatus;
	
	@ManyToOne
	@JoinColumn(name="organization")
	private OrganizationEntity organization;  //custodian of record
	
	@ManyToOne
	@JoinColumn(name="general_practitioner")
	private PractitionerEntity generalPractitioner;
	
	public PatientEntity() {
		super();
	}
	
	public PatientEntity(Long id, String identifier, boolean active, String name, String surname, Gender gender,
			Date birthDate, String address, String phone, String email, boolean deceased, MaritalStatus maritalStatus,
			OrganizationEntity organization, PractitionerEntity generalPractitioner) {
		super();
		this.id = id;
		this.identifier = identifier;
		this.active = active;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.birthDate = birthDate;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.deceased = deceased;
		this.maritalStatus = maritalStatus;
		this.organization = organization;
		this.generalPractitioner = generalPractitioner;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}



	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isDeceased() {
		return deceased;
	}

	public void setDeceased(boolean deceased) {
		this.deceased = deceased;
	}


	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public OrganizationEntity getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationEntity organization) {
		this.organization = organization;
	}

	public PractitionerEntity getGeneralPractitioner() {
		return generalPractitioner;
	}

	public void setGeneralPractitioner(PractitionerEntity generalPractitioner) {
		this.generalPractitioner = generalPractitioner;
	}

	@Override
	public int hashCode() {
		return Objects.hash(active, address, birthDate, deceased, email, gender, generalPractitioner, id, identifier,
				maritalStatus, name, organization, phone, surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatientEntity other = (PatientEntity) obj;
		return active == other.active && Objects.equals(address, other.address)
				&& Objects.equals(birthDate, other.birthDate) && deceased == other.deceased
				&& Objects.equals(email, other.email) && gender == other.gender
				&& Objects.equals(generalPractitioner, other.generalPractitioner) && Objects.equals(id, other.id)
				&& Objects.equals(identifier, other.identifier) && maritalStatus == other.maritalStatus
				&& Objects.equals(name, other.name) && Objects.equals(organization, other.organization)
				&& Objects.equals(phone, other.phone) && Objects.equals(surname, other.surname);
	}

	@Override
	public String toString() {
		return "PatientEntity [id=" + id + ", identifier=" + identifier + ", active=" + active + ", name=" + name
				+ ", surname=" + surname + ", gender=" + gender + ", birthDate=" + birthDate + ", address=" + address
				+ ", phone=" + phone + ", email=" + email + ", deceased=" + deceased + ", maritalStatus="
				+ maritalStatus + ", organization=" + organization + ", generalPractitioner=" + generalPractitioner
				+ "]";
	}
	
}
