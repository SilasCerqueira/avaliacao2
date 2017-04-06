/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacao.controllers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.avaliacao.dominio.DmTipoMovimentacao;
import br.com.avaliacao.entity.Cliente;
import br.com.avaliacao.entity.ContaCorrente;
import br.com.avaliacao.entity.HistoricoMovimentacao;
import br.com.avaliacao.infra.Generic;
import br.com.avaliacao.service.ClienteBus;
import br.com.avaliacao.service.ContaBus;
import br.com.avaliacao.service.HistoricoMovimentacaoBus;


@Path("movimentacoes")
public class HistoricoMovimentacaoController extends Generic {

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<HistoricoMovimentacao> listHistoricoMovimentacaos() {
        try {
            HistoricoMovimentacaoBus historicoMovimentacaoBus = new HistoricoMovimentacaoBus();
            return historicoMovimentacaoBus.listar();
        } catch (Exception ex) {
            Logger.getLogger(HistoricoMovimentacaoController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public HistoricoMovimentacao getHistoricoMovimentacao(@PathParam("id") long id) {
        try {
            HistoricoMovimentacaoBus historicoMovimentacaoBus = new HistoricoMovimentacaoBus();
            return historicoMovimentacaoBus.selecionar(id);
        } catch (Exception ex) {
            Logger.getLogger(HistoricoMovimentacaoController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public String create(HistoricoMovimentacao historicoMovimentacao) {
        try { 
        	  HistoricoMovimentacaoBus historicoMovimentacaoBus = new HistoricoMovimentacaoBus();
        	  ContaBus contaBus = new ContaBus();
        	  ContaCorrente contaCorrente = contaBus.selecionar(historicoMovimentacao.getContaCorrente().getId());        	  
        	  String mensagem = "";
        	  if(historicoMovimentacao.getTipoMovimentacao() == DmTipoMovimentacao.DEPOSITO.getValor()){
        		  historicoMovimentacao.setSaldoAtual(contaCorrente.getSaldo().add(historicoMovimentacao.getValor()));
        		  historicoMovimentacao.setSaldoAnterior(contaCorrente.getSaldo());
        		  contaCorrente.setSaldo(historicoMovimentacao.getSaldoAtual());
        		  contaBus.alterar(contaCorrente);
        	  }else if(historicoMovimentacao.getTipoMovimentacao() == DmTipoMovimentacao.SAQUE.getValor()){
        		  historicoMovimentacao.setSaldoAtual(contaCorrente.getSaldo().subtract(historicoMovimentacao.getValor()));
        		  Long qtdSaqueMes = historicoMovimentacaoBus.buscarQuantidadeSaquesConta(contaCorrente);
	    		  if(qtdSaqueMes > 5){
    		  		historicoMovimentacao.setSaldoAtual(historicoMovimentacao.getSaldoAtual()
    		  				.subtract(BigDecimal.valueOf(DmTipoMovimentacao.SAQUE.getTarifa())));
    		  		historicoMovimentacao.setValorTaxa(BigDecimal.valueOf(DmTipoMovimentacao.SAQUE.getTarifa()));
	    		  }
        		  historicoMovimentacao.setSaldoAnterior(contaCorrente.getSaldo());
        		  contaCorrente.setSaldo(historicoMovimentacao.getSaldoAtual());
        		  if(contaCorrente.getSaldo().compareTo(new BigDecimal("-200")) < 0)     
        			  mensagem = "Saldo insuficiente";
        		  else 
        			  contaBus.alterar(contaCorrente);
        	  }else if(historicoMovimentacao.getTipoMovimentacao() == DmTipoMovimentacao.TRANSFERENCIA.getValor()){
         		  //favorecido
        		  ContaCorrente contaCorrenteFavorecido = contaBus.selecionar(historicoMovimentacao.getContaCorrenteFavorecido().getId());
        		  historicoMovimentacao.setValorTaxa(BigDecimal.valueOf(DmTipoMovimentacao.TRANSFERENCIA.getTarifa()));
        		  historicoMovimentacao.setSaldoAtualFavorecido(contaCorrenteFavorecido.getSaldo().add(historicoMovimentacao.getValor()));
        		  historicoMovimentacao.setSaldoAnteriorFavorecido(contaCorrenteFavorecido.getSaldo());
        		  contaCorrenteFavorecido.setSaldo(historicoMovimentacao.getSaldoAtualFavorecido());
        		  //principal
        		  historicoMovimentacao.setSaldoAtual(contaCorrente.getSaldo().subtract(historicoMovimentacao.getValor()).subtract(historicoMovimentacao.getValorTaxa()));
        		  historicoMovimentacao.setSaldoAnterior(contaCorrente.getSaldo());
        		  contaCorrente.setSaldo(historicoMovimentacao.getSaldoAtual());
        		  if(contaCorrente.getSaldo().compareTo(new BigDecimal("-200")) < 0)     
        			  mensagem = "Saldo insuficiente";
        		  else{
        			  contaBus.alterar(contaCorrente);
        			  contaBus.alterar(contaCorrenteFavorecido);
        		  }        		  
        	  }
        	  historicoMovimentacao.setData(new Date());        	 
        	  if(mensagem == ""){
        		  historicoMovimentacaoBus.inserir(historicoMovimentacao);
              	  return "OK";
        	  }else{
        		  return mensagem;
        	  }              		
        } catch (Exception ex) {
            Logger.getLogger(HistoricoMovimentacaoController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
