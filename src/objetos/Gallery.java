package objetos;
// Generated 22 ene. 2022 21:44:56 by Hibernate Tools 5.5.7.Final

/**
 * Gallery generated by hbm2java
 */
public class Gallery implements java.io.Serializable {

	private Integer id;
	private EspaciosNaturales espaciosNaturales;
	private byte[] image;

	public Gallery() {
	}

	public Gallery(EspaciosNaturales espaciosNaturales, byte[] image) {
		this.espaciosNaturales = espaciosNaturales;
		this.image = image;
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

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
