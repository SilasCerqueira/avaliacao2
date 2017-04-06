package br.com.avaliacao.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.avaliacao.dominio.DmTipoMovimentacao;
import br.com.avaliacao.entity.ContaCorrente;
import br.com.avaliacao.entity.HistoricoMovimentacao;
import br.com.avaliacao.infra.HibernateUtil;


public class HistoricoMovimentacaoBus {

    public Long inserir(HistoricoMovimentacao historicoMovimentacao) {        
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(historicoMovimentacao);
        t.commit();
        return historicoMovimentacao.getId();
    }

    public HistoricoMovimentacao selecionar(long id) {
        return (HistoricoMovimentacao) HibernateUtil.getSessionFactory()
                .openSession()
                .get(HistoricoMovimentacao.class, id);
    }

    public List<HistoricoMovimentacao> listar() {
        return (List<HistoricoMovimentacao>) HibernateUtil.getSessionFactory()
                .openSession()
                .createQuery("from HistoricoMovimentacao")
                .list();
    }
    
    public long buscarQuantidadeSaquesConta(ContaCorrente contaCorrente){
    	
    	StringBuilder jpql = new StringBuilder();
    	
    	jpql.append("SELECT COUNT(distinct h.id) ")
    		.append(" FROM HistoricoMovimentacao h ")
    		.append(" WHERE EXTRACT(MONTH FROM h.data) = EXTRACT(MONTH FROM  current_timestamp) ")
    		.append(" AND contaCorrente.id = ").append(contaCorrente.getId())
    		.append(" AND tipoMovimentacao = ").append(DmTipoMovimentacao.SAQUE.getValor());
    	
    	 return (long) HibernateUtil.getSessionFactory()
                 .openSession()
                 .createQuery(jpql.toString()).uniqueResult();
    }
}
