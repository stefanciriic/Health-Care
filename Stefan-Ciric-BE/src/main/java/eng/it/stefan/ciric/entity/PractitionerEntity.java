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
import eng.it.stefan.ciric.entity.util.Qualification;

@Entity
@Table(name="practitioner")
@Where(clause="active=true")
@SQLDelete(sql = "UPDATE practitioner SET active = false WHERE id=?")
public class PractitionerEntity implements Serializable {

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
			length=30)
	private String name;
	
	@Column(nullable=false,
			length=30)
	private String surname;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20,nullable=true)
	private Gender gender;
	
	@Column(name="birth_date",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	@Column(length=50)
	private String address;
	
	@Column(length=15)
	private String phone;
	
	@Column(length=30)
	private String email;
	
	@Enumerated(EnumType.STRING)
	@Column(length=30,nullable=true)
	private Qualification qualification;
	
	@ManyToOne
	@JoinColumn(name="organization")
	private OrganizationEntity organization;

	public PractitionerEntity() {
		super();
	}
	
	public PractitionerEntity(Long id, String identifier, boolean active, String name, String surname, Gender gender,
			Date birthDate, String address, String phone, String email, Qualification qualification,OrganizationEntity organization) {	
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
		this.qualification = qualification;
		this.organization=organization;
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


	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public OrganizationEntity getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationEntity organization) {
		this.organization = organization;
	}

	@Override
	public int hashCode() {
		return Objects.hash(active, address, birthDate, email, gender, id, identifier, name, organization, phone,
				qualification, surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PractitionerEntity other = (PractitionerEntity) obj;
		return active == other.active && Objects.equals(address, other.address)
				&& Objects.equals(birthDate, other.birthDate) && Objects.equals(email, other.email)
				&& gender == other.gender && Objects.equals(id, other.id)
				&& Objects.equals(identifier, other.identifier) && Objects.equals(name, other.name)
				&& Objects.equals(organization, other.organization) && Objects.equals(phone, other.phone)
				&& qualification == other.qualification && Objects.equals(surname, other.surname);
	}

	@Override
	public String toString() {
		return "PractitionerEntity [id=" + id + ", identifier=" + identifier + ", active=" + active + ", name=" + name
				+ ", surname=" + surname + ", gender=" + gender + ", birthDate=" + birthDate + ", address=" + address
				+ ", phone=" + phone + ", email=" + email + ", qualification=" + qualification + ", organization="
				+ organization + "]";
	}

}
