package br.com.jlameira.exception;

public class ForeignKeyViolationException extends RuntimeException {
	
	private Class entityClass;

    public ForeignKeyViolationException(String message, Class entityClass) {
        super(message);
        this.entityClass = entityClass;
    }

    public Class getEntityClass() {
        return entityClass;
    }

}
