package br.com.avaliacao.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.avaliacao.entity.Cliente;
import br.com.avaliacao.infra.HibernateUtil;


public class ClienteBus {

    public Long inserir(Cliente cliente) {
        cliente.setDataDeCadastro(new Date());
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(cliente);
        t.commit();
        return cliente.getId();
    }

    public void alterar(Cliente cliente) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.merge(cliente);
        t.commit();
    }

    public void excluir(long id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Cliente c = selecionar(id);

        Transaction t = s.beginTransaction();
        s.delete(c);
        t.commit();
    }

    public Cliente selecionar(long id) {
        return (Cliente) HibernateUtil.getSessionFactory()
                .openSession()
                .get(Cliente.class, id);
    }

    public List<Cliente> listar() {
        return (List<Cliente>) HibernateUtil.getSessionFactory()
                .openSession()
                .createQuery("from Cliente")
                .list();
    }
}
