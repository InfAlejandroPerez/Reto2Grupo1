package objetos;
// Generated 27 ene 2022 19:01:25 by Hibernate Tools 5.5.7.Final

/**
 * Favoritos generated by hbm2java
 */
public class Favoritos implements java.io.Serializable {

	private Integer id;
	private EspaciosNaturales espaciosNaturales;
	private Users users;
	private Integer idMunicipio;

	public Favoritos() {
	}

	public Favoritos(EspaciosNaturales espaciosNaturales, Users users, Integer idMunicipio) {
		this.espaciosNaturales = espaciosNaturales;
		this.users = users;
		this.idMunicipio = idMunicipio;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EspaciosNaturales getEspaciosNaturales() {
		return this.espaciosNaturales;
	}

	public void setEspaciosNaturales(EspaciosNaturales espaciosNaturales) {
		this.espaciosNaturales = espaciosNaturales;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Integer getIdMunicipio() {
		return this.idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

}
