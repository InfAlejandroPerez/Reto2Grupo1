package objetos;
// Generated 13 ene 2022 17:50:37 by Hibernate Tools 5.5.7.Final

/**
 * EspaciosNaturales generated by hbm2java
 */
public class EspaciosNaturales implements java.io.Serializable {

	private Integer id;
	private Municipio municipio;
	private String nombre;
	private String descripcion;
	private String localidad;
	private String territorio;
	private String marca;
	private String tipo;
	private String naturaleza;

	public EspaciosNaturales() {
	}

	public EspaciosNaturales(Municipio municipio, String nombre) {
		this.municipio = municipio;
		this.nombre = nombre;
	}

	public EspaciosNaturales(Municipio municipio, String nombre, String descripcion, String localidad,
			String territorio, String marca, String tipo, String naturaleza) {
		this.municipio = municipio;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.localidad = localidad;
		this.territorio = territorio;
		this.marca = marca;
		this.tipo = tipo;
		this.naturaleza = naturaleza;
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

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getTerritorio() {
		return this.territorio;
	}

	public void setTerritorio(String territorio) {
		this.territorio = territorio;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNaturaleza() {
		return this.naturaleza;
	}

	public void setNaturaleza(String naturaleza) {
		this.naturaleza = naturaleza;
	}

}
