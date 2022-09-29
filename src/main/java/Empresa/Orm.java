package Empresa;
/**
 * @author Prof Matias Garcia.
 * <p> Copyright (C) 2021 para <a href = "https://www.profmatiasgarcia.com.ar/"> www.profmatiasgarcia.com.ar </a>
 * - con licencia GNU GPL3.
 * <p> Este programa es software libre. Puede redistribuirlo y/o modificarlo bajo los términos de la
 * Licencia Pública General de GNU según es publicada por la Free Software Foundation, 
 * bien con la versión 3 de dicha Licencia o bien (según su elección) con cualquier versión posterior. 
 * Este programa se distribuye con la esperanza de que sea útil, pero SIN NINGUNA GARANTÍA, 
 * incluso sin la garantía MERCANTIL implícita o sin garantizar la CONVENIENCIA PARA UN PROPÓSITO
 * PARTICULAR. Véase la Licencia Pública General de GNU para más detalles.
 * Debería haber recibido una copia de la Licencia Pública General junto con este programa. 
 * Si no ha sido así ingrese a <a href = "http://www.gnu.org/licenses/"> GNU org </a>
 */
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class Orm {

    private String query = "from Persona P";

    public List getPersonaList() {
        return executeHQLQuery(query);
    }

    private List executeHQLQuery(String hql) {
        List resultList = null;
        try ( Session session = HibernateUtil.getCurrentSession()) {
            session.beginTransaction();
            Query q = session.createQuery(hql);
            resultList = q.list();
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return resultList;
    }

    public Persona getPersonaById(int id) {
        List resultList = executeHQLQuery(query.concat(" WHERE P.idpersona = ").concat(String.valueOf(id)));
        Persona persona = null;
        if (!resultList.isEmpty()) {
            persona = (Persona) resultList.get(0);
        }
        return persona;
    }

    public int savePersona(Persona persona) {
        return save(persona);
    }

    private int save(Serializable serializable) {
        int newId = 0;
        try ( Session session = HibernateUtil.getCurrentSession()) {
            session.beginTransaction();
            newId = (int) session.save(serializable);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return newId;
    }

    public void save(Persona1 t) {
        try ( Session session = HibernateUtil.getCurrentSession()) {
            session.beginTransaction();

            session.persist(t);
            System.out.println("La persona se agrego correctamente con el id: " + t.getIdpersona());

            session.getTransaction().commit();
        }

    }

    public void update(Persona t) {
        try ( Session session = HibernateUtil.getCurrentSession()) {

            session.beginTransaction();

            session.merge(t);

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Ocurrio un error al modificar el producto");
        }
    }

    public void delete(Persona t) {
        try ( Session session = HibernateUtil.getCurrentSession()) {

            session.beginTransaction();

            session.remove(t);
            System.out.println("El producto se ha eliminado correctamente");

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Ocurrio un error al intentar eliminar el producto");
        }
    }

}
