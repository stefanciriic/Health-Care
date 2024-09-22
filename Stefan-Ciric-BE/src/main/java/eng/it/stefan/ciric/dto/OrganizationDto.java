package eng.it.stefan.ciric.dto;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
public class OrganizationDto  {

	@NotNull
	private Long id;

	private String identifier;
	
	@NotNull
	private boolean active;

	@NotEmpty
	private String type;
	
	@NotEmpty
	private String name;
	

	private String address;

	@Pattern(regexp="^([+]?)[0-9]{2,3}\\/?[0-9]{3}\\-?[0-9]{3,5}$")
	private String phone;
	
	@Pattern(regexp="^[a-zA-Z]([a-zA-Z0-9]|\\.|\\-|\\_)*[a-zA-Z0-9]@([a-z]{2,}\\.)+([a-z]{2,})$")
	private String email;

	public OrganizationDto() {
		super();
	}

	public OrganizationDto(@NotEmpty Long id, String identifier, @NotEmpty boolean active, @NotEmpty String type,
			@NotEmpty String name, String address,
			@Pattern(regexp = "^([+]?)[0-9]{2,3}\\/?[0-9]{3}\\-?[0-9]{3,5}$") String phone,
			@Pattern(regexp = "^[a-zA-Z]([a-zA-Z0-9]|\\.|\\-|\\_)*[a-zA-Z0-9]@([a-z]{2,}\\.)+([a-z]{2,})$") String email) {
		super();
		this.id = id;
		this.identifier = identifier;
		this.active = active;
		this.type = type;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
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


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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


	@Override
	public int hashCode() {
		return Objects.hash(active, address, email, id, identifier, name, phone, type);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrganizationDto other = (OrganizationDto) obj;
		return active == other.active && Objects.equals(address, other.address) && Objects.equals(email, other.email)
				&& Objects.equals(id, other.id) && Objects.equals(identifier, other.identifier)
				&& Objects.equals(name, other.name) && Objects.equals(phone, other.phone)
				&& Objects.equals(type, other.type);
	}


	@Override
	public String toString() {
		return "OrganizationDto [id=" + id + ", identifier=" + identifier + ", active=" + active + ", type=" + type
				+ ", name=" + name + ", address=" + address + ", phone=" + phone + ", email=" + email + "]";
	}

	
}
