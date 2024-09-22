package eng.it.stefan.ciric.dto;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ServiceTypeDto {
	
	@NotNull
	private Long id;

	@NotEmpty
	private String name;

	public ServiceTypeDto() {
		super();
	}
	
	public ServiceTypeDto(@NotEmpty Long id, @NotEmpty String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceTypeDto other = (ServiceTypeDto) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "ServiceTypeDto [id=" + id + ", name=" + name + "]";
	}
	
}
