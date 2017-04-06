package br.com.avaliacao.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.avaliacao.entity.ContaCorrente;
import br.com.avaliacao.infra.HibernateUtil;


public class ContaBus {

    public Long inserir(ContaCorrente conta) {        
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(conta);
        t.commit();
        return conta.getId();
    }

    public void alterar(ContaCorrente conta) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.merge(conta);
        t.commit();
    }

    public void excluir(long id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        ContaCorrente c = selecionar(id);

        Transaction t = s.beginTransaction();
        s.delete(c);
        t.commit();
    }

    public ContaCorrente selecionar(long id) {
        return (ContaCorrente) HibernateUtil.getSessionFactory()
                .openSession()
                .get(ContaCorrente.class, id);
    }

    public List<ContaCorrente> listar() {
        return (List<ContaCorrente>) HibernateUtil.getSessionFactory()
                .openSession()
                .createQuery("from ContaCorrente")
                .list();
    }
}
