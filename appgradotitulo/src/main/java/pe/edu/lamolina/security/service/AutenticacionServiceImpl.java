package pe.edu.lamolina.security.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.security.dao.AutenticacionDAO;
import pe.edu.lamolina.security.entity.SeguridadUsuarioEntity;

@Service
@Transactional
public class AutenticacionServiceImpl implements AutenticacionService {
	private final Logger log =Logger.getLogger(AutenticacionServiceImpl.class);
	
	@Autowired
	private AutenticacionDAO autenticacionDAO;
	
	@Override
	public SeguridadUsuarioEntity validarUsuario(SeguridadUsuarioEntity usuario) {
		final String method="validarUsuario";
		final String params = "SeguridadUsuarioEntity usuario";
		UNALMLogger.entry(log, method,params,new Object[] { usuario });
		SeguridadUsuarioEntity result = this.autenticacionDAO.validarUsuarioDAO(usuario);
		UNALMLogger.exit(log, method,result);
		return result;
	}

}
