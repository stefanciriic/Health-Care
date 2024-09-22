package eng.it.stefan.ciric.exception;

public class EntityNotExistsException extends EngineeringException{
	private static final long serialVersionUID = 1L;
	
	private final Object entity;
	
	public EntityNotExistsException(Object entity, String message) {
		super(message);
		this.entity = entity;
	}

	public Object getEntity() {
		return entity;
	}
}
