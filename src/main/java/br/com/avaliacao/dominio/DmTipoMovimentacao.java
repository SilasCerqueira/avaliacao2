package br.com.avaliacao.dominio;


public enum DmTipoMovimentacao {

	TRANSFERENCIA(0, "Transfêrencia",1.75), 
	SAQUE(1, "Saque",3.90), 
	DEPOSITO(2, "Deposito",0.00);
	
	private int valor;
	private String descricao;
	private double tarifa;
	
	DmTipoMovimentacao(int valor, String descricao, double tarifa) {
		this.valor = valor;
		this.descricao = descricao;
		this.tarifa = tarifa;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getTarifa() {
		return tarifa;
	}

	public void setTarifa(double tarifa) {
		this.tarifa = tarifa;
	}		
}