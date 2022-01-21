package dto;

import java.io.Serializable;
import java.util.ArrayList;

public class DTO implements Serializable {

	@Override
	public String toString() {
		return "{ \"operacion\" : \"" + operacion + "\", \"idUsuario\": \"" + idUsuario + "\", \"userName\": \"" + userName + "\", \"password\" : \""
				+ password + "\", \"campoBusqueda\": \"" + campoBusqueda + "\", \"loginValidador\" : " + loginValidador
				+ ", \"usuarioRegistrado\": " + usuarioRegistrado +  "\" }";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String operacion;
	private String idUsuario;
	private String userName;
	private String password;
	private String campoBusqueda;
	private boolean loginValidador;
	private boolean usuarioRegistrado;
	private ArrayList<String> listaLugares;

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCampoBusqueda() {
		return campoBusqueda;
	}

	public void setCampoBusqueda(String campoBusqueda) {
		this.campoBusqueda = campoBusqueda;
	}

	public boolean isLoginValidador() {
		return loginValidador;
	}

	public void setLoginValidador(boolean loginValidador) {
		this.loginValidador = loginValidador;
	}

	public boolean isUsuarioRegistrado() {
		return usuarioRegistrado;
	}

	public void setUsuarioRegistrado(boolean usuarioRegistrado) {
		this.usuarioRegistrado = usuarioRegistrado;
	}

	public ArrayList<String> getListaLugares() {
		return listaLugares;
	}

	public void setListaLugares(ArrayList<String> listaLugares) {
		this.listaLugares = listaLugares;
	}

}
