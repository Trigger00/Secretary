package pe.edu.lamolina.gradotitulo.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.lamolina.gradotitulo.dao.AgendaGrupoDAO;
import pe.edu.lamolina.gradotitulo.entity.SCGAgendaGrupoEntity;
import pe.edu.lamolina.logging.UNALMLogger;

@Service
@Transactional(rollbackFor = Exception.class)
public class AgendaGrupoServiceImpl implements AgendaGrupoService{
	
	private final static Logger log = Logger.getLogger(AgendaGrupoServiceImpl.class);

	@Autowired
	private AgendaGrupoDAO agendaGrupoDAO;
	
	@Override
	public List<SCGAgendaGrupoEntity> getListAgendaGrupo(SCGAgendaGrupoEntity entity) {
		final String method = "getListAgendaGrupo";
		final String params = "SCGAgendaGrupoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGAgendaGrupoEntity> result=this.agendaGrupoDAO.getListAgendaGrupoDAO(entity);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public SCGAgendaGrupoEntity getForUpdateAgendaGrupo(SCGAgendaGrupoEntity entity) {
		final String method = "getForUpdateAgendaGrupo";
		final String params = "SCGAgendaGrupoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGAgendaGrupoEntity result=this.agendaGrupoDAO.getForUpdateAgendaGrupoDAO(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGAgendaGrupoEntity saveAgendaGrupo(SCGAgendaGrupoEntity entity) {
		final String method="saveAgendaGrupo";
		final String params="SCGAgendaGrupoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGAgendaGrupoEntity entityForSave=null;
		if(entity.getCodigo()!=null){
			entityForSave=agendaGrupoDAO.getForUpdateAgendaGrupoDAO(entity);
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
			entityForSave.setTextoNombre(entity.getTextoNombre());
			entityForSave.setFlagEliminado(entity.getFlagEliminado());
		
		}else{
			entityForSave=entity;
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
		}
		SCGAgendaGrupoEntity result=this.agendaGrupoDAO.saveAgendaGrupoDAO(entityForSave);
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public void delete(SCGAgendaGrupoEntity entity) {
		final String method = "delete";
		final String params = "SCGAgendaGrupoEntity asset";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		SCGAgendaGrupoEntity entityForDelete=null;
		if(entity.getCodigo()!=null){
			entityForDelete=agendaGrupoDAO.getForUpdateAgendaGrupoDAO(entity);
			UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
			entityForDelete.setFlagEliminado("0");
		}else{
			entityForDelete=entity;
			UNALMLogger.trace(log, method, "entityForSave: "+entityForDelete);
		}
		SCGAgendaGrupoEntity result=this.agendaGrupoDAO.saveAgendaGrupoDAO(entityForDelete);
		UNALMLogger.exit(log, method, result);
		
	}
	
}
