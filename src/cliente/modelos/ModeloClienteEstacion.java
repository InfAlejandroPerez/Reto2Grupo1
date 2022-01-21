package cliente.modelos;

import objetos.Municipio;

public class ModeloClienteEstacion {

	private Integer id;
	private String municipio;
	private String nombre;
	private String provincia;
	private String direccion;
	private String latitud;
	private String longitud;
	
	
	
	@Override
	public String toString() {
		return " { \"id\" : " + id + ", \"municipio\": \"" + municipio + "\", \"nombre\" : \"" + nombre + "\" , \"provincia\" : \""
				+ provincia + "\", \"direccion\": \"" + direccion + "\", \"latitud\": \"" + latitud + "\", \"longitud\": \"" + longitud + "\"}";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	
	
	
	
	
	
}
