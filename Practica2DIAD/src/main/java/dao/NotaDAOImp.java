package dao;

import model.Nota;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class NotaDAOImp implements NotaDAO{
    @Override
    public void actualizarNota(Nota n) {
        Session session;
        Transaction transaction;

        session = new Configuration().configure().buildSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.merge(n);
        transaction.commit();
        session.close();
    }
}
