package objectos;
// Generated 12 ene 2022 16:51:47 by Hibernate Tools 5.5.7.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Estacion generated by hbm2java
 */
public class Estacion implements java.io.Serializable {

	private Integer id;
	private Municipio municipio;
	private String nombre;
	private String provincia;
	private String direccion;
	private String latitud;
	private String longitud;
	private Set calidadAireHorarios = new HashSet(0);
	private Set calidadAireDiarios = new HashSet(0);
	private Set calidadAireIndices = new HashSet(0);

	public Estacion() {
	}

	public Estacion(Municipio municipio) {
		this.municipio = municipio;
	}

	public Estacion(Municipio municipio, String nombre, String provincia, String direccion, String latitud,
			String longitud, Set calidadAireHorarios, Set calidadAireDiarios, Set calidadAireIndices) {
		this.municipio = municipio;
		this.nombre = nombre;
		this.provincia = provincia;
		this.direccion = direccion;
		this.latitud = latitud;
		this.longitud = longitud;
		this.calidadAireHorarios = calidadAireHorarios;
		this.calidadAireDiarios = calidadAireDiarios;
		this.calidadAireIndices = calidadAireIndices;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Municipio getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLatitud() {
		return this.latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return this.longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public Set getCalidadAireHorarios() {
		return this.calidadAireHorarios;
	}

	public void setCalidadAireHorarios(Set calidadAireHorarios) {
		this.calidadAireHorarios = calidadAireHorarios;
	}

	public Set getCalidadAireDiarios() {
		return this.calidadAireDiarios;
	}

	public void setCalidadAireDiarios(Set calidadAireDiarios) {
		this.calidadAireDiarios = calidadAireDiarios;
	}

	public Set getCalidadAireIndices() {
		return this.calidadAireIndices;
	}

	public void setCalidadAireIndices(Set calidadAireIndices) {
		this.calidadAireIndices = calidadAireIndices;
	}

}
