package pe.edu.lamolina.gradotitulo.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.lamolina.gradotitulo.dao.FacultadDAO;
import pe.edu.lamolina.gradotitulo.dao.FacultadDAOHibernate;
import pe.edu.lamolina.gradotitulo.entity.SCGEspecialidadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGFacultadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoEspecialidadEntity;
import pe.edu.lamolina.logging.UNALMLogger;

@Service
@Transactional(rollbackFor = Exception.class)
public class FacultadServiceImpl implements FacultadService {
	
	private final static Logger log = Logger.getLogger(FacultadDAOHibernate.class);

	
	@Autowired
	private FacultadDAO facultadDAO;
	

	@Override
	public List<SCGFacultadEntity> getListFacultad(SCGFacultadEntity entity) {
		final String method = "getListFacultad";
		final String params = "SCGFacultadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		if(entity.getFlagEstado() !=null){
			UNALMLogger.trace(log, method, "entity.getFlagEstado()= "+"'"+entity.getFlagEstado()+"'");
			String valorObtenido=entity.getFlagEstado();
			if( valorObtenido.equals("Activo")){										 
				entity.setFlagEstado("A");
				UNALMLogger.trace(log, method, "es Activo su valor es  "+"'"+entity.getFlagEstado()+"'");
			}else if(valorObtenido=="Desactivo"){
				entity.setFlagEstado("D");
				UNALMLogger.trace(log, method, "es Desactivo su valor es  "+"'"+entity.getFlagEstado()+"'");

			}
		}
		List<SCGFacultadEntity> result=this.facultadDAO.getListFacultadDAO(entity);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}


	@Override
	public List<SCGEspecialidadEntity> getListEspecialidad(SCGEspecialidadEntity entity) {
		final String method = "getListEspecialidad";
		final String params = "SCGEspecialidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		if(entity.getFlagEstado() !=null){
			UNALMLogger.trace(log, method, "entity.getFlagEstado()= "+"'"+entity.getFlagEstado()+"'");
			String valorObtenido=entity.getFlagEstado();
			if( valorObtenido.equals("Activo")){										 
				entity.setFlagEstado("A");
				UNALMLogger.trace(log, method, "es Activo su valor es  "+"'"+entity.getFlagEstado()+"'");
			}else if(valorObtenido=="Desactivo"){
				entity.setFlagEstado("D");
				UNALMLogger.trace(log, method, "es Desactivo su valor es  "+"'"+entity.getFlagEstado()+"'");

			}
		}
		List<SCGEspecialidadEntity> result=this.facultadDAO.getListEspecialidadDAO(entity);
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public List<SCGTipoEspecialidadEntity> getListTipoEspecialidad(SCGTipoEspecialidadEntity entity) {
		final String method = "getListTipoEspecialidad";
		final String params = "SCGTipoEspecialidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGTipoEspecialidadEntity> result=this.facultadDAO.getListTipoEspecialidadDAO(entity);
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	
	@Override
	public SCGFacultadEntity getForUpdate(SCGFacultadEntity entity) {
		final String method = "getForUpdate";
		final String params = "SCGFacultadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGFacultadEntity result=this.facultadDAO.getForUpdateDAO(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}


	@Override
	public SCGEspecialidadEntity getForUpdateEspecialidad(SCGEspecialidadEntity entity) {
		final String method = "getForUpdateEspecialidad";
		final String params = "SCGEspecialidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGEspecialidadEntity result=this.facultadDAO.getForUpdateEspecialidadDAO(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGFacultadEntity save(SCGFacultadEntity entity) {
		final String method="save";
		final String params="SCGFacultadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		SCGFacultadEntity entityForSave=null;
		UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
		if(entity.getCodigo()!=null){
			entityForSave=facultadDAO.getForUpdateDAO(entity);
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
			entityForSave.setCodigoExterno(entity.getCodigoExterno());
			entityForSave.setFlagEliminado(entity.getFlagEliminado());
			entityForSave.setFlagEstado(entity.getFlagEstado());
			entityForSave.setTextoNombreAbreviado(entity.getTextoNombreAbreviado());
			entityForSave.setTextoNombreEspanol(entity.getTextoNombreEspanol());
			entityForSave.setTextoNombreIngles(entity.getTextoNombreIngles());
		}else{
			entityForSave=entity;
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
		}
		
		SCGFacultadEntity result=this.facultadDAO.saveDAO(entityForSave);
		//SCGFacultadEntity result=this.facultadDAO.saveDAO(entity);
		UNALMLogger.trace(log, method, "result: "+result);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGEspecialidadEntity saveEspecialidad(SCGEspecialidadEntity entity) {
		final String method="saveEspecialidad";
		final String params="SCGEspecialidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		SCGEspecialidadEntity entityForSave=null;
		UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
		if(entity.getCodigo()!=null){
			entityForSave=facultadDAO.getForUpdateEspecialidadDAO(entity);
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
			entityForSave.setCodigoExterno(entity.getCodigoExterno());
			entityForSave.setFlagEliminado(entity.getFlagEliminado());
			entityForSave.setFlagEstado(entity.getFlagEstado());
			//entityForSave.setTextoNombreAbreviado(entity.getTextoNombreAbreviado());
			entityForSave.setTextoNombreEspanol(entity.getTextoNombreEspanol());
			entityForSave.setTextoNombreIngles(entity.getTextoNombreIngles());
			
			if(entity.getFacultad() != null){
				SCGFacultadEntity facultad = new SCGFacultadEntity();
				facultad .setCodigo(entity.getFacultad().getCodigo());
				entityForSave.setFacultad(facultad);
			}
			
			if(entity.getTipoEspecialidad()!=null){
				SCGTipoEspecialidadEntity tipoEspecialidad = new SCGTipoEspecialidadEntity();
				tipoEspecialidad.setCodigo(entity.getTipoEspecialidad().getCodigo());
				entityForSave.setTipoEspecialidad(tipoEspecialidad);
			}
			entityForSave.setTextoNombreIngles(entity.getTextoNombreIngles());
		}else{
			entityForSave=entity;
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
		}
		
		
		SCGEspecialidadEntity result=this.facultadDAO.saveEspecialidadDAO(entityForSave);
		UNALMLogger.exit(log, method, result);
		return result;
	}


	@Override
	public void delete(SCGFacultadEntity entity) {
		final String method = "delete";
		final String params = "SCGFacultadEntity asset";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		SCGFacultadEntity entityForDelete=null;
		UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
		if(entity.getCodigo()!=null){
			entityForDelete=facultadDAO.getForUpdateDAO(entity);
			UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
			entityForDelete.setFlagEliminado("0");
			
			SCGEspecialidadEntity facultadCodigo = new SCGEspecialidadEntity();
			facultadCodigo.setFacultad(entity);
			List<SCGEspecialidadEntity> especialidadList = this.facultadDAO.getListEspecialidadDAO(facultadCodigo);
			UNALMLogger.trace(log, method, "especialidadList: "+especialidadList.size());
			if(especialidadList !=null){
				for (SCGEspecialidadEntity item : especialidadList) {
					item.setFlagEliminado("0");
					this.facultadDAO.saveEspecialidadDAO(item);
				}
			}
			
		}else{
			entityForDelete=entity;
			UNALMLogger.trace(log, method, "entityForSave: "+entityForDelete);
		}
		
		SCGFacultadEntity result=this.facultadDAO.saveDAO(entityForDelete);
		UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);

	    //this.facultadDAO.deleteDAO(entity);
		UNALMLogger.exit(log, method);
		
	}


	@Override
	public void deleteEspecialidad(SCGEspecialidadEntity entity) {
		final String method = "deleteEspecialidad";
		final String params = "SCGEspecialidadEntity asset";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		SCGEspecialidadEntity entityForDelete=null;
		UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
		if(entity.getCodigo()!=null){
			entityForDelete=facultadDAO.getForUpdateEspecialidadDAO(entity);
			UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
			entityForDelete.setFlagEliminado("0");
		}else{
			entityForDelete=entity;
			UNALMLogger.trace(log, method, "entityForSave: "+entityForDelete);
		}
		
		SCGEspecialidadEntity result=this.facultadDAO.saveEspecialidadDAO(entityForDelete);
		UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);

	    //this.facultadDAO.deleteDAO(entity);
		UNALMLogger.exit(log, method);
	
		
	}
	
}
