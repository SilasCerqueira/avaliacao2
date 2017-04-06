package br.com.avaliacao.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class HistoricoMovimentacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "contacorrente_id", nullable = false)
	private ContaCorrente contaCorrente;
	
	@ManyToOne
    @JoinColumn(name = "contacorrentefavorecido_id")
	private ContaCorrente contaCorrenteFavorecido;
	
	@Temporal(TemporalType.TIMESTAMP)	
	private Date data;
	
	/**
	 * @see DmTipoMovimentacao
	 */
	@Column(length = 1)
	private int tipoMovimentacao;
	
	@Column(precision = 10, scale = 2)
	private BigDecimal valorTaxa;
	
	@Column(precision = 15, scale = 2)
	private BigDecimal saldoAtual;
	
	@Column(precision = 15, scale = 2)
	private BigDecimal saldoAnterior;		
	
	@Column(precision = 15, scale = 2)
	private BigDecimal saldoAtualFavorecido;
	
	@Column(precision = 15, scale = 2)
	private BigDecimal saldoAnteriorFavorecido;	
	
	@Column(precision = 15, scale = 2)
	private BigDecimal valor;	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public ContaCorrente getContaCorrente() {
		return contaCorrente;
	}
	public void setContaCorrente(ContaCorrente contaCorrente) {
		this.contaCorrente = contaCorrente;
	}
	public int getTipoMovimentacao() {
		return tipoMovimentacao;
	}
	public void setTipoMovimentacao(int tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}
	public BigDecimal getValorTaxa() {
		return valorTaxa;
	}
	public void setValorTaxa(BigDecimal valorTaxa) {
		this.valorTaxa = valorTaxa;
	}
	public BigDecimal getSaldoAtual() {
		return saldoAtual;
	}
	public void setSaldoAtual(BigDecimal saldoAtual) {
		this.saldoAtual = saldoAtual;
	}
	public BigDecimal getSaldoAnterior() {
		return saldoAnterior;
	}
	public void setSaldoAnterior(BigDecimal saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public ContaCorrente getContaCorrenteFavorecido() {
		return contaCorrenteFavorecido;
	}
	public void setContaCorrenteFavorecido(ContaCorrente contaCorrenteFavorecido) {
		this.contaCorrenteFavorecido = contaCorrenteFavorecido;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public BigDecimal getSaldoAtualFavorecido() {
		return saldoAtualFavorecido;
	}
	public void setSaldoAtualFavorecido(BigDecimal saldoAtualFavorecido) {
		this.saldoAtualFavorecido = saldoAtualFavorecido;
	}
	public BigDecimal getSaldoAnteriorFavorecido() {
		return saldoAnteriorFavorecido;
	}
	public void setSaldoAnteriorFavorecido(BigDecimal saldoAnteriorFavorecido) {
		this.saldoAnteriorFavorecido = saldoAnteriorFavorecido;
	}
	
}
