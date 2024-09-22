package eng.it.stefan.ciric.dto;

import java.util.Date;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class PractitionerDto {


	@NotNull
	private Long id;
	
	@Length(min=5)
	private String identifier;
	
	@NotNull
	private boolean active;
	
	@NotNull
	@Length(min=3)
	private String name;
	
	@NotNull
	@Length(min=3)
	private String surname;
	
	private String gender;
	
	@NotNull
	private Date birthDate;
	
	private String address;
	
	@Pattern(regexp="^([+]?)[0-9]{2,3}\\/?[0-9]{3}\\-?[0-9]{3,5}$")
	private String phone;
	
	@Pattern(regexp="^[a-zA-Z]([a-zA-Z0-9]|\\.|\\-|\\_)*[a-zA-Z0-9]@([a-z]{2,}\\.)+([a-z]{2,})$")
	private String email;
	
	private String qualification;
	
	
	private Long organizationId;
	
	public Long getId() {
		return id;
	}

	public PractitionerDto() {
		super();
	}

	public PractitionerDto(@NotNull Long id, @Length(min = 5) String identifier, @NotNull boolean active,
			@NotNull @Length(min = 3) String name, @NotNull @Length(min = 3) String surname, String gender,
			@NotNull Date birthDate, String address,
			@Pattern(regexp = "^([+]?)[0-9]{2,3}\\/?[0-9]{3}\\-?[0-9]{3,5}$") String phone,
			@Pattern(regexp = "^[a-zA-Z]([a-zA-Z0-9]|\\.|\\-|\\_)*[a-zA-Z0-9]@([a-z]{2,}\\.)+([a-z]{2,})$") String email,
			String qualification,Long organizationId) {
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
		this.organizationId = organizationId;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(active, address, birthDate, email, gender, id, identifier, name, organizationId, phone,
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
		PractitionerDto other = (PractitionerDto) obj;
		return active == other.active && Objects.equals(address, other.address)
				&& Objects.equals(birthDate, other.birthDate) && Objects.equals(email, other.email)
				&& Objects.equals(gender, other.gender) && Objects.equals(id, other.id)
				&& Objects.equals(identifier, other.identifier) && Objects.equals(name, other.name)
				&& Objects.equals(organizationId, other.organizationId) && Objects.equals(phone, other.phone)
				&& Objects.equals(qualification, other.qualification) && Objects.equals(surname, other.surname);
	}

	@Override
	public String toString() {
		return "PractitionerDto [id=" + id + ", identifier=" + identifier + ", active=" + active + ", name=" + name
				+ ", surname=" + surname + ", gender=" + gender + ", birthDate=" + birthDate + ", address=" + address
				+ ", phone=" + phone + ", email=" + email + ", qualification=" + qualification + ", organizationId="
				+ organizationId + "]";
	}
	
	
	
	
}
