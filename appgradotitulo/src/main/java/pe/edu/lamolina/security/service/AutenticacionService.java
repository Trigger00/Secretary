package pe.edu.lamolina.security.service;

import pe.edu.lamolina.security.entity.SeguridadUsuarioEntity;

public interface AutenticacionService {
	public SeguridadUsuarioEntity validarUsuario(SeguridadUsuarioEntity usuario);
}
