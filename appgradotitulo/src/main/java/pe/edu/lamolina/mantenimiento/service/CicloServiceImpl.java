package pe.edu.lamolina.mantenimiento.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.lamolina.gradotitulo.entity.SCGCicloEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.mantenimiento.dao.CicloDAO;
import pe.edu.lamolina.util.FilterUtil;

@Service
@Transactional( rollbackFor = Exception.class)
public class CicloServiceImpl implements CicloService{
	
	private final static Logger log = Logger.getLogger( CicloServiceImpl.class );
	
	@Autowired	
	CicloDAO cicloDAO;

	@Override
	public List<SCGCicloEntity> listCiclo(SCGCicloEntity entity, FilterUtil filterUtil) {
		final String method = "SCGCicloEntity";
		UNALMLogger.entry(log, method);
		
		List<SCGCicloEntity> result = this.cicloDAO.listCicloDAO( entity, filterUtil );
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public SCGCicloEntity saveCiclo(SCGCicloEntity entity) {
		final String method = "saveCiclo";
		final String params = "SCGCicloEntity entity";
		UNALMLogger.entry(log, method, params, new Object[]{ entity });
		
		SCGCicloEntity entityForSave = null;
		if (entity.getCodigo() != null) {
			
			entityForSave = this.getForUpdateCiclo(entity );
			entityForSave.setTextoCiclo(entity.getTextoCiclo());
			entityForSave.setTextoNombreEspanol(entity.getTextoNombreEspanol());
			entityForSave.setTextoSemestre(entity.getTextoSemestre());
			entityForSave.setFechaInicioCiclo(entity.getFechaInicioCiclo());
			entityForSave.setFechaFinalCiclo(entity.getFechaFinalCiclo());
		} else {
			entityForSave = entity;
		}
		
		SCGCicloEntity result = this.cicloDAO.saveCicloDAO(entityForSave);
		UNALMLogger.exit(log, method, result );
		return result;
	}

	@Override
	public SCGCicloEntity getForUpdateCiclo(SCGCicloEntity entity) {
		final String method = "getForUpdateCiclo";
		final String params = "SCGCicloEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		SCGCicloEntity result = this.cicloDAO.getForUpdateCicloDAO(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	

}
