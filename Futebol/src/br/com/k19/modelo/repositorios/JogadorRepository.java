package br.com.k19.modelo.repositorios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.k19.modelo.entidades.Jogador;

public class JogadorRepository {

	private EntityManager manager;

	public JogadorRepository(EntityManager manager) {
		this.manager = manager;
	}

	public void adiciona(Jogador jogador) {
		this.manager.persist(jogador);
	}

	public void remove(Long id) {
		Jogador jogador = this.procura(id);
		this.manager.remove(jogador);
	}

	public Jogador atualiza(Jogador jogador) {
		return this.manager.merge(jogador);
	}

	public Jogador procura(Long id) {
		return this.manager.find(Jogador.class, id);
	}

	public List<Jogador> getLista() {
		Query query = this.manager.createQuery(" select x from Jogador x");
		return query.getResultList();
	}
}
