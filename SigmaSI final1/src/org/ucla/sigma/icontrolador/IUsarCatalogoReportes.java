package org.ucla.sigma.icontrolador;

import java.util.Set;

import org.ucla.sigma.modelo.Especie;
import org.ucla.sigma.modelo.Patologia;
import org.ucla.sigma.modelo.Raza;
import org.ucla.sigma.modelo.Servicio;
import org.ucla.sigma.modelo.TipoPatologia;
import org.ucla.sigma.modelo.TipoServicio;

public interface IUsarCatalogoReportes {
	
	public Set<Especie> InterfazgetEspecies();
	public void InterfazsetEspecies(Set<Especie> especies);
	
	public Set<TipoServicio> InterfazgetTipoServicios();
	public void InterfazsetTipoServicios(Set<TipoServicio> tipoServicios);
	
	public Set<Servicio> InterfazgetServicios();
	public void InterfazsetServicios(Set<Servicio> servicios);
	
	public Set<Raza> InterfazgetRazas();
	public void InterfazsetRazas(Set<Raza> razas);
	
	public Set<TipoPatologia> interfazgetTipoPatologias();
	public void InterfazsetTipoPatologias(Set<TipoPatologia> tipoPatologias);
	
	public Set<Patologia> interfazgetPatologias();
	public void InterfazsetPatologias(Set<Patologia> patologias);

}
