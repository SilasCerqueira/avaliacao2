package br.com.avaliacao.infra;

import java.util.HashMap;
import java.util.List;

public class ValueObject extends HashMap {
	
	private static final long serialVersionUID = 1L;

	public ValueObject(){
		super();
	}
	
	/**
	 * Reescrito para facilitar depuração.
	 */
	public Object get(Object key) {
		return super.get(key);
	}

	/**
	 * Assinatura de conveniência.
	 */
	public String getStr(Object key) {
		return (String) get(key);
	}

	/**
	 * Assinatura de conveniência.
	 */
	public Integer getInt(Object key) {
		return (Integer) get(key);
	}
	
	/**
	 * Assinatura de conveniência.
	 * Muitos dos id's vindos do Flex vem como Integer
	 * sendo que os mesmos devem ser Long
	 */
	public Long getLong(Object key) {
		if (get(key) instanceof Integer){
			return new Long((Integer)get(key));
		}
		return (Long) get(key);
	}
	
	/**
	 * Assinatura de conveniência 
	 */
	public Boolean getBoolean(Object key){
		return (Boolean) get(key);
	}
	
	/**
	 * Assinatura de conveniência 
	 */
	public List getList(Object key){
		return (List) get(key);
	}
	
	/**
	 * Reescrito para facilitar depuração.
	 */
	public Object put(Object key, Object value) {
		return super.put(key, value);
	}
}
