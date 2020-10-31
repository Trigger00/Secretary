package pe.edu.lamolina.security.dao;

import pe.edu.lamolina.security.entity.SeguridadUsuarioEntity;

public interface AutenticacionDAO {
	public SeguridadUsuarioEntity validarUsuarioDAO(SeguridadUsuarioEntity usuario);
}
