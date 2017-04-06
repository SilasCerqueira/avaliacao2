package br.com.avaliacao.infra;

public abstract class Generic {

	/**
	 * Exceção de negócio do tipo Info
	 * 
	 * @param mensagem
	 */
	protected void newInfo(String mensagem) {
		BusinessException be = new BusinessException(mensagem);
		be.setErrorType(BusinessException.INFO);
		throw be;
	}

	/**
	 * Excecao de negocio do tipo Info com um validationType (existem casos que
	 * � necessario identificar qual o tipo de validacao feita)
	 * 
	 * @param mensagem
	 * @param tipoValidacao
	 */
	protected void newInfo(String mensagem, String tipoValidacao) {
		BusinessException be = new BusinessException(mensagem);
		be.setErrorType(BusinessException.INFO);
		be.setValidationType(tipoValidacao);
		throw be;
	}

	/**
	 * Exceção de negócio do tipo error
	 * 
	 * @param mensagem
	 */
	protected void newError(String mensagem) {
		BusinessException be = new BusinessException(mensagem);
		be.setErrorType(BusinessException.ERROR);
		throw be;
	}

	/**
	 * Excecao de negocio do tipo error com um validationType (existem casos que
	 * � necessario identificar qual o tipo de validacao feita)
	 * 
	 * @param mensagem
	 * @param tipoValidacao
	 */
	protected void newError(String mensagem, String tipoValidacao) {
		BusinessException be = new BusinessException(mensagem);
		be.setErrorType(BusinessException.ERROR);
		be.setValidationType(tipoValidacao);
		throw be;
	}
	
	protected void newWarning(String mensagem) {
		BusinessException be = new BusinessException(mensagem);
		be.setErrorType(BusinessException.WARNING);
		throw be;
	}
	
	protected void newWarning(String mensagem, String tipoValidacao) {
		BusinessException be = new BusinessException(mensagem);
		be.setErrorType(BusinessException.WARNING);
		be.setValidationType(tipoValidacao);
		throw be;
	}
}
