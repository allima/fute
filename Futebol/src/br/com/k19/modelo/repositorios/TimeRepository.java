package br.com.k19.modelo.repositorios;

import br.com.k19.modelo.entidades.Jogador;
import br.com.k19.modelo.entidades.Time;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class TimeRepository {

	private EntityManager manager;

	public TimeRepository(EntityManager manager) {
		this.manager = manager;
	}

	public void adiciona(Time time) {
		this.manager.persist(time);
	}

	public void remove(Long id) {
		Time time = this.procura(id);
		Query query = this.manager
				.createQuery("select x from Jogador x where x.time =:time");
		query.setParameter("time", time);
		List<Jogador> jogadores = query.getResultList();
		for (Jogador jogador : jogadores) {
			jogador.setTime(null);
		}
		this.manager.remove(time);
	}

	public Time atualiza(Time time) {
		return this.manager.merge(time);
	}

	public Time procura(Long id) {
		return this.manager.find(Time.class, id);
	}

	public List<Time> getLista() {
		Query query = this.manager.createQuery("select x from Time x");
		return query.getResultList();
	}
}