package com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.entity.Clientes;
import com.general.Metodos;

public class ClienteDAO implements Metodos {

	// Carga los objetos (las clasaes definidas en el persistance)
	// el managerfactory indica la tabla de base de datosa que será persistida
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Tables");// tables es un nombre de unidad de
																				// persistencia, tambien esta en el
																				// archivo persistencia

	EntityManager em = emf.createEntityManager();
	Clientes cliente = null;

	@Override
	public String guardar(Object ob) {
		cliente = (Clientes) ob;
		em.getTransaction().begin();// esto abre la transaccion
		String resultado = null;

		try {
			em.persist(cliente);
			em.getTransaction().begin();
			resultado = "1";
			System.out.println("Registro insertado");

		} catch (Exception e) {
			em.getTransaction().rollback();
			resultado = e.getLocalizedMessage();
			System.out.println("No pudo insertarse");
		}
		em.close();
		return resultado;
	}

	@Override
	public String eliminar(int id) {
		String r = null;
		cliente = em.find(Clientes.class, id);
		em.getTransaction().begin();
		try {
			em.remove(cliente);
			;
			em.getTransaction().commit();
			System.out.println("Se ha eliminado correctamente");
			r = "1";
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println("no se puedo eliminar");
			r = e.getMessage();
		}
		return r;
	}

	@Override
	public String editar(Object ob) {
		cliente = (Clientes) ob;
		Clientes clientedb = em.find(Clientes.class, cliente.getClienteId());// el primer parametro es la clase donde va
																				// a buscar, el segundo es la llave
																				// primaria
		String r = null;
		em.getTransaction().begin();
		try {
			clientedb.setClienteId(clientedb.getClienteId());
			clientedb.setNombre(clientedb.getNombre());
			clientedb.setApellidos(clientedb.getApellidos());
			clientedb.setRfc(clientedb.getRfc());
			clientedb.setFechaNac(clientedb.getFechaNac());
			clientedb.setStatus(clientedb.getStatus());
			em.getTransaction().commit();
			System.out.println("Editador correctamente");
			r = "1";

		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println("No se pudo editar");
			r = e.getLocalizedMessage();
		}
		return r;
	}

	@Override
	public Object buscar(int id) {
		cliente = em.find(Clientes.class, id);
		return cliente;
	}

	@Override
	public List mostrar() {
		List<Clientes> ls = em.createQuery("from Clientes").getResultList();
		return ls;
	}

}
