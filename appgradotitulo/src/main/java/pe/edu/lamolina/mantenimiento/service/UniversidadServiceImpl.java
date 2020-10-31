package pe.edu.lamolina.mantenimiento.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.lamolina.gradotitulo.entity.SCGTipoUniversidadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGUniversidadEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.mantenimiento.dao.UniversidadDAO;
import pe.edu.lamolina.util.FilterUtil;
@Service
@Transactional( rollbackFor = Exception.class)
public class UniversidadServiceImpl implements UniversidadService{
	private final static Logger log = Logger.getLogger( UniversidadServiceImpl.class );
	
	@Autowired
	UniversidadDAO universidadDAO;
	
	@Override
	public List<SCGTipoUniversidadEntity> listTipoUniversidad() {
		final String method = "listTipoUniversisad";
		UNALMLogger.entry(log, method);
		List<SCGTipoUniversidadEntity> result = this.universidadDAO.listTipoUniversidadDAO();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGUniversidadEntity> listUniversidad(
			SCGUniversidadEntity entity,
			FilterUtil filterUtil ){
		final String method = "listUniversidad";
		UNALMLogger.entry(log, method);
		List<SCGUniversidadEntity> result = this.universidadDAO.listUniversidadDAO( entity, filterUtil );
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
		
	@Override
	public SCGUniversidadEntity saveUniversidad(SCGUniversidadEntity entity) {
		final String method = "saveUniversidad";
		final String params = "SCGUniversidadEntity entity";
		UNALMLogger.entry(log, method, params, new Object[]{ entity });
		SCGUniversidadEntity entityForSave = null;
		if( entity.getCodigo() != null ){
			entityForSave = this.getForUpdateUnverisdad( entity );
//			entityForSave.setCodigoExterno( entity.getCodigoExterno() );
			entityForSave.setPais( entity.getPais());
			entityForSave.setTipoUniversidad( entity.getTipoUniversidad() );
			entityForSave.setTextoSiglas( entity.getTextoSiglas() );
			entityForSave.setTextoNombreAbreviado( entity.getTextoNombreAbreviado() );
			entityForSave.setTextoNombre( entity.getTextoNombre() );
		}else{
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("ddMMyyyyHHmm");
			LocalDateTime fecha = LocalDateTime.now();
			entity.setCodigoExterno( "UN"+dateFormat.format( fecha ) );
			entityForSave = entity;
		}
		
		SCGUniversidadEntity result = this.universidadDAO.saveUniversidadDAO( entityForSave );
		UNALMLogger.exit(log, method, result );
		return result;
	}

	@Override
	public SCGUniversidadEntity getForUpdateUnverisdad(SCGUniversidadEntity entity) {
		final String method = "getForUpdateUnverisdad";
		final String params = "SCGUniversidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGUniversidadEntity result = this.universidadDAO.getForUpdateUnverisdadDAO( entity );
		UNALMLogger.exit(log, method, result);
		return result;
	}

}
