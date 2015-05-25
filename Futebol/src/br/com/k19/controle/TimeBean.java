package br.com.k19.controle;

import br.com.k19.modelo.entidades.Time;
import br.com.k19.modelo.repositorios.TimeRepository;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
public class TimeBean {

	private Time time = new Time();
	private List<Time> times;

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public void setTimes(List<Time> times) {
		this.times = times;
	}

	// GETTERS E SETTERS

	public void adiciona() {
		EntityManager manager = this.getManager();
		TimeRepository repository = new TimeRepository(manager);
		if (this.time.getId() == null) {
			repository.adiciona(this.time);
		} else {
			repository.atualiza(this.time);
		}
		this.time = new Time();
		this.times = null;
	}

	public void preparaAlteracao() {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		Long id = Long.parseLong(params.get("id"));
		EntityManager manager = this.getManager();
		TimeRepository repository = new TimeRepository(manager);
		this.time = repository.procura(id);
	}

	public void remove() {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		Long id = Long.parseLong(params.get("id"));
		EntityManager manager = this.getManager();
		TimeRepository repository = new TimeRepository(manager);
		repository.remove(id);
		this.times = null;
	}

	public List<Time> getTimes() {
		if (this.times == null) {
			EntityManager manager = this.getManager();
			TimeRepository repository = new TimeRepository(manager);
			this.times = repository.getLista();
		}
		return this.times;
	}

	private EntityManager getManager() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		return (EntityManager) request.getAttribute(" EntityManager ");
	}

}