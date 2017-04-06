package br.com.avaliacao.infra;

import java.util.List;


public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ValueObject valueObject;

	public static final String INFO = "info";
	public static final String ERROR = "error";
	public static final String WARNING = "warning";

	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(Class clazz, String message) {
		super(clazz.getName() + " : " + message);
	}

	public BusinessException(List<String> messages) {
		super(geMensagens(messages));
	}

	public static String geMensagens(List<String> messages) {
		String msg = "";
		for (String message : messages) {
			msg += message + "\n";
		}
		return msg;
	}

	public String getNameException() {
		return BusinessException.class.getSimpleName();
	}

	/**
	 * Metodo escrito para que parametros possam ser devolvidos para o flex se
	 * houver alguma excecao
	 * 
	 * @return
	 */
	public ValueObject getValueObject() {
		if (valueObject == null) {
			valueObject = new ValueObject();
		}
		return valueObject;
	}

	public void setValueObject(ValueObject valueObject) {
		this.valueObject = valueObject;
	}

	public void setErrorType(String errorType) {
		getValueObject().put(getNameException(), errorType);
	}

	public void setValidationType(String validationType) {
		getValueObject().put("validacao", validationType);
	}
}