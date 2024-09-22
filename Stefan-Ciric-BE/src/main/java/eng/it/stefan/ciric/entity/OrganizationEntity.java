package eng.it.stefan.ciric.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name="organization")
@Where(clause="active=true")
@SQLDelete(sql = "UPDATE organization SET active = false WHERE id=?")
public class OrganizationEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=true,
			unique=true,
			length=40)
	private String identifier;
	
	@Column(nullable=false)
	private boolean active;
	
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "type",nullable=false)
	private OrganizationTypeEntity type;
	
	@Column(nullable=false,
			unique=true,
			length=50)
	private String name;
	
	@Column(length=50)
	private String address;
	
	@Column(length=20)
	private String phone;
	
	@Column(length=30)
	private String email;
	

	public OrganizationEntity() {
		super();
	}
	
	public OrganizationEntity(Long id, String identifier, boolean active, OrganizationTypeEntity type, String name,
			String address, String phone, String email) {
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

	public OrganizationTypeEntity getType() {
		return type;
	}

	public void setType(OrganizationTypeEntity type) {
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
		OrganizationEntity other = (OrganizationEntity) obj;
		return active == other.active && Objects.equals(address, other.address) && Objects.equals(email, other.email)
				&& Objects.equals(id, other.id) && Objects.equals(identifier, other.identifier)
				&& Objects.equals(name, other.name) && Objects.equals(phone, other.phone)
				&& Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "OrganizationEntity [id=" + id + ", identifier=" + identifier + ", active=" + active + ", type=" + type
				+ ", name=" + name + ", address=" + address + ", phone=" + phone + ", email=" + email + "]";
	}
}
