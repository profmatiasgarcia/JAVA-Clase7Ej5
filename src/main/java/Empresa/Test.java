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
import java.util.List;
import org.hibernate.Session;

public class Test {

    public static void main(String[] args) {
        System.out.println("Iniciando conexion MySQL");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Orm orm = new Orm();

        Persona persona = new Persona();
        persona.setNombre("Matias");
        persona.setApellido("Garcia");
        persona.setDni("25803348");
        persona.setEdad(40);
        orm.savePersona(persona);

        Persona1 otraPersona = new Persona1();
        otraPersona.setNombre("Maria");
        otraPersona.setApellido("Lopez");
        otraPersona.setDni("29148456");
        otraPersona.setEdad(38);
        orm.save(otraPersona);

        List<Persona1> listadoPersonas = session.createQuery("from personas where apellido = 'Garcia'" + "OR nombre like '%a%i%'").getResultList();

        for (Persona1 temp : listadoPersonas) {
            System.out.println(temp);
        }

        session.close();
        System.out.println("Se cerro conexion MySQL");
    }
}
