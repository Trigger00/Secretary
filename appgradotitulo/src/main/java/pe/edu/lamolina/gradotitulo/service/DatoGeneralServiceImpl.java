package pe.edu.lamolina.gradotitulo.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.lamolina.filemanager.component.FileManagerComponent;
import pe.edu.lamolina.gradotitulo.dao.DatoGeneralDAO;
import pe.edu.lamolina.gradotitulo.entity.SCGAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGCicloEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaDocumentoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaTelefonoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoDocumentoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoTelefonoEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;
@Service
@Transactional(rollbackFor = Exception.class)
public class DatoGeneralServiceImpl implements DatoGeneralService {
	
	private final static Logger log = Logger.getLogger(DatoGeneralServiceImpl.class);

	@Autowired
	private DatoGeneralDAO datoGeneralDAO;
	
	@Autowired
	private FileManagerComponent fileManagerComponent;
	@Override
	public List<SCGEstudianteEntity> getEstudianteList(SCGEstudianteEntity entity,FilterUtil filterUtil) {
		final String method = "getListEstudiante";
		final String params = "SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		String fechaInvalida="1000-01-01";
		String formatFechaInicio= TypesUtil.getDefaultString(entity.getFechaCreacion(), "yyy-MM-dd");
		UNALMLogger.trace(log, method,"formatFechaInicio " +formatFechaInicio);
		if(formatFechaInicio.equals(fechaInvalida)){
			entity.setFechaCreacion(null);
		}
		UNALMLogger.trace(log, method,"entity.getFechaCreacion() " +entity.getFechaCreacion());
		List<SCGEstudianteEntity> result=this.datoGeneralDAO.getEstudianteListDAO(entity,filterUtil);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGEstudianteEntity> getEstudianteListExcel(SCGEstudianteEntity entity) {
		final String method = "getEstudianteListExcel";
		final String params = "SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		String fechaInvalida="1000-01-01";
		String formatFechaInicio= TypesUtil.getDefaultString(entity.getFechaCreacion(), "yyy-MM-dd");
		UNALMLogger.trace(log, method,"formatFechaInicio " +formatFechaInicio);
		if(formatFechaInicio.equals(fechaInvalida)){
			entity.setFechaCreacion(null);
		}
		UNALMLogger.trace(log, method,"entity.getFechaCreacion() " +entity.getFechaCreacion());
		List<SCGEstudianteEntity> result=this.datoGeneralDAO.getEstudianteListExcelDAO(entity);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGEstudianteEntity> getEstudiantePregradoList(SCGEstudianteEntity entity,FilterUtil filterUtil) {
		final String method = "getEstudiantePregradoList";
		final String params = "SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		String fechaInvalida="1000-01-01";
		String formatFechaInicio= TypesUtil.getDefaultString(entity.getFechaCreacion(), "yyy-MM-dd");
		UNALMLogger.trace(log, method,"formatFechaInicio " +formatFechaInicio);
		if(formatFechaInicio.equals(fechaInvalida)){
			entity.setFechaCreacion(null);
		}
		UNALMLogger.trace(log, method,"entity.getFechaCreacion() " +entity.getFechaCreacion());
		List<SCGEstudianteEntity> result=this.datoGeneralDAO.getEstudiantePregradoListDAO(entity,filterUtil);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGEstudianteEntity> getEstudianteRevalidaList(SCGEstudianteEntity entity,FilterUtil filterUtil) {
		final String method = "getEstudianteRevalidaList";
		final String params = "SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		String fechaInvalida="1000-01-01";
		String formatFechaInicio= TypesUtil.getDefaultString(entity.getFechaCreacion(), "yyy-MM-dd");
		UNALMLogger.trace(log, method,"formatFechaInicio " +formatFechaInicio);
		if(formatFechaInicio.equals(fechaInvalida)){
			entity.setFechaCreacion(null);
		}
		UNALMLogger.trace(log, method,"entity.getFechaCreacion() " +entity.getFechaCreacion());
		List<SCGEstudianteEntity> result=this.datoGeneralDAO.getEstudianteRevalidaListDAO(entity,filterUtil);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGPersonaDocumentoEntity> getDocumentoList(SCGPersonaDocumentoEntity entity) {
		final String method = "getDocumentoList";
		final String params = "SCGPersonaDocumentoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGPersonaDocumentoEntity> result=this.datoGeneralDAO.getDocumentoListDAO(entity);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGTipoDocumentoEntity> getTipoDocumentoList(SCGTipoDocumentoEntity entity) {
		final String method = "getTipoDocumentoList";
		final String params = "SCGTipoDocumentoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGTipoDocumentoEntity> result=this.datoGeneralDAO.getTipoDocumentoListDAO(entity);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGCicloEntity> getCicloList(SCGCicloEntity entity) {
		final String method = "getCicloList";
		final String params = "SCGCicloEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGCicloEntity> result=this.datoGeneralDAO.getCicloListDAO(entity);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGPersonaTelefonoEntity> getPersonaTelefonoList(SCGPersonaTelefonoEntity entity) {
		final String method = "getPersonaTelefonoList";
		final String params = "SCGPersonaTelefonoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGPersonaTelefonoEntity> result=this.datoGeneralDAO.getPersonaTelefonoListDAO(entity);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public SCGAdjuntoEntity getForUpdateAdjunto(SCGAdjuntoEntity entity) {
		final String method = "getForUpdateAdjuntoDAO";
		final String params = "SCGAdjuntoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGAdjuntoEntity result=this.datoGeneralDAO.getForUpdateAdjuntoDAO(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGEstudianteEntity getForUpdateEstudiante(SCGEstudianteEntity entity) {
		final String method = "getForUpdateEstudiante";
		final String params = "SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGEstudianteEntity result=this.datoGeneralDAO.getForUpdateEstudianteDAO(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGPersonaTelefonoEntity getForUpdateTelefono(SCGPersonaTelefonoEntity entity) {
		final String method = "getForUpdateTelefono";
		final String params = "SCGPersonaTelefonoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGPersonaTelefonoEntity result=this.datoGeneralDAO.getForUpdateTelefonoDAO(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGTipoTelefonoEntity getForUpdateTipoTelefono(SCGTipoTelefonoEntity entity) {
		final String method = "getForUpdateTipoTelefono";
		final String params = "SCGTipoTelefonoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGTipoTelefonoEntity result=this.datoGeneralDAO.getForUpdateTipoTelefonoDAO(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGPersonaDocumentoEntity getForUpdateDocumento(SCGPersonaDocumentoEntity entity) {
		final String method = "getForUpdateDocumento";
		final String params = "SCGPersonaDocumentoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGPersonaDocumentoEntity result=this.datoGeneralDAO.getForUpdateDocumentoDAO(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGTipoDocumentoEntity getForUpdateTipoDocumento(SCGTipoDocumentoEntity entity) {
		final String method = "getForUpdateTipoDocumento";
		final String params = "SCGTipoDocumentoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGTipoDocumentoEntity result=this.datoGeneralDAO.getForUpdateTipoDocumentoDAO(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGCicloEntity getForUpdateCiclo(SCGCicloEntity entity) {
		final String method = "getForUpdateCiclo";
		final String params = "SCGCicloEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGCicloEntity result=this.datoGeneralDAO.getForUpdateCicloDAO(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGEstudianteEntity saveEstudiante(SCGEstudianteEntity entity) {
		final String method="saveEstudiante";
		final String params="SCGEstudianteEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		SCGEstudianteEntity entityForSave=null;
		UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
		if(entity.getCodigo()!=null){
			SCGEstudianteEntity estudianteCodigoFind = new SCGEstudianteEntity();
			estudianteCodigoFind.setCodigo(entity.getCodigo());
			entityForSave=datoGeneralDAO.getForUpdateEstudianteDAO(estudianteCodigoFind);
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
			/*
			SCGFacultadEntity facultad = new SCGFacultadEntity();
			facultad.setCodigo(entity.getFacultad().getCodigo());

			SCGEspecialidadEntity especialidad = new SCGEspecialidadEntity();
			especialidad.setCodigo(entity.getEspecialidad().getCodigo());
			*/
			entityForSave.setTextoMatricula(entity.getTextoMatricula());;
			//entityForSave.setFacultad(facultad);
			//entityForSave.setEspecialidad(especialidad);
			entityForSave.setTextoNombreCompleto(entity.getTextoNombreCompleto());;
			entityForSave.setTextoCodigoExternoDocumento(entity.getTextoCodigoExternoDocumento());
			entityForSave.setTextoNumeroDocumento(entity.getTextoNumeroDocumento());
			entityForSave.setTextoCicloEgreso(entity.getTextoCicloEgreso());
			entityForSave.setFechaIngresanteMatricula(entity.getFechaIngresanteMatricula());;
			entityForSave.setFechaTramiteConstancia(entity.getFechaTramiteConstancia());
			entityForSave.setFechaModificacion(new Date());
		}else{
			entityForSave=entity;
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
		}
		
		SCGEstudianteEntity result=this.datoGeneralDAO.saveEstudianteDAO(entityForSave);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGAdjuntoEntity saveAdjunto(SCGAdjuntoEntity entity) {
		final String method="saveAdjunto";
		final String params="SCGAdjuntoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGAdjuntoEntity entityForSave=null;
		if(entity.getCodigo() != null){
			UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
			entityForSave = datoGeneralDAO.getForUpdateAdjuntoDAO(entity);
			this.deleteFiles(entityForSave);
			entityForSave.setTextoNombreArchivo(entity.getTextoNombreArchivo());
			entityForSave.setTextoRuta(entity.getTextoRuta());				
			this.syncFiles(entity);
		}else{
			this.syncFiles(entity);
			entity.setFileArchivo(null);
			entityForSave=entity;
		}
		SCGAdjuntoEntity result=this.datoGeneralDAO.saveAdjuntoDAO(entityForSave);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGPersonaEntity savePersona(SCGPersonaEntity entity) {
		final String method="savePersona";
		final String params="SCGPersonaEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		entity.setAdjunto(null);
		SCGPersonaEntity entityForSave=null;
		UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
		if(entity.getCodigo()!=null){
			UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
			entityForSave=datoGeneralDAO.getForUpdatePersonaDAO(entity);
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
			entityForSave.setTextoNombre(entity.getTextoNombre());
			entityForSave.setTextoPaterno(entity.getTextoPaterno());
			entityForSave.setTextoMaterno(entity.getTextoMaterno());
			entityForSave.setTextoSexo(entity.getTextoSexo());
			entityForSave.setTextoNombreCompleto(entity.getTextoNombreCompleto());
			entityForSave.setFechaEdicion(new Date());
			/*
			if(entity.getAdjunto()!=null){
				if(entity.getAdjunto().getCodigo() != null){
					UNALMLogger.trace(log, method, "entity.getAdjunto().getCodigo(): "+entity.getAdjunto().getCodigo());
					entityForSave.setAdjunto(new SCGAdjuntoEntity());
					entityForSave.getAdjunto().setCodigo(entity.getAdjunto().getCodigo());
				}
			}
			*/
		}else{
			entityForSave=entity;
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
		}
		
		SCGPersonaEntity result=this.datoGeneralDAO.savePersonaDAO(entityForSave);
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGPersonaTelefonoEntity savePersonaTelefono(SCGPersonaTelefonoEntity entity) {
		final String method="savePersonaTelefono";
		final String params="SCGPersonaTelefonoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGPersonaTelefonoEntity entityForSave=null;
		UNALMLogger.trace(log, method, "entityForSave "+entityForSave);
		UNALMLogger.trace(log, method, "entity.getCodigo() "+entity.getCodigo());
		
		if (entity.getCodigo()!=null) {
			entityForSave=datoGeneralDAO.getForUpdateTelefonoDAO(entity);
			entityForSave.setTextoNumeroTelefono(entity.getTextoNumeroTelefono());
			
		} else {
			if (entity.getPersona() != null) {
				
				if (entity.getPersona().getCodigo() != null) {
					SCGPersonaTelefonoEntity personaTelefono = new SCGPersonaTelefonoEntity();
					personaTelefono.setPersona(entity.getPersona());
					personaTelefono.setTipoTelefono(entity.getTipoTelefono());
					entityForSave = datoGeneralDAO.getForUpdateTelefonoDAO(entity);
					
					if( entityForSave != null) {
						
						if(entityForSave.getCodigo() != null) {
							entityForSave.setTextoNumeroTelefono(entity.getTextoNumeroTelefono());
						}
					}
				}
				
			}
			
			if( entityForSave == null ){
				entityForSave=entity;
				UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
			}
			
		}
		SCGPersonaTelefonoEntity result=this.datoGeneralDAO.savePersonaTelefonoDAO(entityForSave);
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGPersonaDocumentoEntity savePersonaDocumento(SCGPersonaDocumentoEntity entity) {
		final String method="savePersonaDocumento";
		final String params="SCGPersonaDocumentoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGPersonaDocumentoEntity result = null;
		List<SCGPersonaDocumentoEntity> entityForSave=null;
		UNALMLogger.trace(log, method, "entity.getCodigo() "+entity.getCodigo());
		entityForSave=datoGeneralDAO.getDocumentoListDAO(entity);
		UNALMLogger.trace(log, method, "entityForSave: "+entityForSave.size());
		boolean isDifferent=true;
		if(entityForSave.size()>=1 ){
			for(SCGPersonaDocumentoEntity item :entityForSave){
				
				if(item.getTipoDocumento().getCodigo() == entity.getTipoDocumento().getCodigo()){
					item.setTextoDocumento(entity.getTextoDocumento());
					result=this.datoGeneralDAO.savePersonaDocumentoDAO(item);
					isDifferent=false;
				}
			}
			if(isDifferent==true){
				UNALMLogger.trace(log, method, "El documento es nuevo");
				result=this.datoGeneralDAO.savePersonaDocumentoDAO(entity);
				UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
			}

		}else{
			UNALMLogger.trace(log, method, "No tiene documentos registrados ");
			result=this.datoGeneralDAO.savePersonaDocumentoDAO(entity);
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
		}
		
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGTipoDocumentoEntity saveTipoDocumentoIdentidad(SCGTipoDocumentoEntity entity) {
		final String method="saveTipoDocumentoIdentidad";
		final String params="SCGTipoDocumentoEntity entity";
		String format;
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGTipoDocumentoEntity entityForSave=null;
		UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
		if(entity.getCodigo()!=null){
			SCGTipoDocumentoEntity tipoDocumentoCodigo =new SCGTipoDocumentoEntity();
			tipoDocumentoCodigo.setCodigo(entity.getCodigo());
			entityForSave=datoGeneralDAO.getForUpdateTipoDocumentoDAO(tipoDocumentoCodigo);
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
			entityForSave.setTextoNombre(entity.getTextoNombre());;
			entityForSave.setFlagSunedu(entity.getFlagSunedu());;
			if(entity.getFlagSunedu().equals("1")){
				entityForSave.setCodigoExterno(entity.getCodigoExterno());;
			}
		}else{
			if(entity.getFlagSunedu().equals("0")){
				format=getExternalCodeNotValidForSUNEDU(entity);
				entity.setCodigoExterno(format);
			}
			entityForSave=entity;
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
		}
		SCGTipoDocumentoEntity result=this.datoGeneralDAO.saveTipoDocumentoIdentidad(entityForSave);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public void deleteAdjunto(SCGPersonaEntity entity) {
		final String method = "deleteAdjunto";
		final String params = "SCGEstudianteEntity asset";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		SCGAdjuntoEntity entityForDelete=null;
		if(entity.getAdjunto()!=null){
			if(entity.getAdjunto().getCodigo()!=null){
				UNALMLogger.trace(log, method, "entity.getAdjunto().getCodigo(): "+entity.getAdjunto().getCodigo());
				SCGAdjuntoEntity adjunto = new SCGAdjuntoEntity();
				adjunto.setCodigo(entity.getAdjunto().getCodigo());
				entityForDelete = datoGeneralDAO.getForUpdateAdjuntoDAO(adjunto);
				entityForDelete.setFlagEliminado("0");
				this.deleteFiles(entityForDelete);
				this.datoGeneralDAO.saveAdjuntoDAO(entityForDelete);
			}
				
		}
		UNALMLogger.exit(log, method);
	}
	@Override
	public void deleteEstudiante(SCGEstudianteEntity entity) {
		final String method = "deleteEstudiante";
		final String params = "SCGEstudianteEntity asset";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		SCGEstudianteEntity entityForDelete=null;
		UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
		if(entity.getCodigo()!=null){
			entityForDelete=datoGeneralDAO.getForUpdateEstudianteDAO(entity);
			entityForDelete.setFlagEliminado("0");
		}else{
			entityForDelete=entity;
		}
		
		SCGEstudianteEntity result=this.datoGeneralDAO.saveEstudianteDAO(entityForDelete);
		UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
		UNALMLogger.exit(log, method);
		
	}
	@Override
	public void deletePersona(SCGPersonaEntity entity) {
		final String method = "deleteEstudiante";
		final String params = "SCGPersonaEntity asset";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		SCGPersonaEntity entityForDelete=null;
		if(entity.getCodigo()!=null){
			UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
			entityForDelete=datoGeneralDAO.getForUpdatePersonaDAO(entity);
			UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
			entityForDelete.setFlagEliminado("0");
			deleteAdjunto(entityForDelete);
		}else{
			entityForDelete=entity;
		}
		
		SCGPersonaEntity result=this.datoGeneralDAO.savePersonaDAO(entityForDelete);
		UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
		UNALMLogger.exit(log, method);
		
	}
	@Override
	public void deleteDocumento(SCGPersonaDocumentoEntity entity) {
		final String method = "deleteEstudiante";
		final String params = "SCGEstudianteEntity asset";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		List<SCGPersonaDocumentoEntity> entityForDelete=null;
		
		UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
		if(entity.getPersona()!=null){
			if(entity.getPersona().getCodigo()!=null){
				entityForDelete=datoGeneralDAO.getDocumentoListDAO(entity);
				for(SCGPersonaDocumentoEntity item:entityForDelete){
					item.setFlagEliminado("0");
					this.datoGeneralDAO.savePersonaDocumentoDAO(item);
				}
			};
		}else{
			this.datoGeneralDAO.savePersonaDocumentoDAO(entity);
		}
		UNALMLogger.exit(log, method);
		
	}
	@Override
	public void deleteTelefono(SCGPersonaTelefonoEntity entity) {
		final String method = "deleteTelefono";
		final String params = "SCGPersonaTelefonoEntity asset";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		List<SCGPersonaTelefonoEntity> entityForDelete=null;
		
		UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
		if(entity.getPersona()!=null){
			if(entity.getPersona().getCodigo()!=null){
				entityForDelete=datoGeneralDAO.getTelefonoListDAO(entity);
				for(SCGPersonaTelefonoEntity item:entityForDelete){
					item.setFlagEliminado("0");
					this.datoGeneralDAO.savePersonaTelefonoDAO(item);
				}
			};
		}else{
			this.datoGeneralDAO.savePersonaTelefonoDAO(entity);
		}
		UNALMLogger.exit(log, method);
		
	}
	@Override
	public void deleteTipoDocumento(SCGTipoDocumentoEntity entity) {
		final String method = "deleteTipoDocumento";
		final String params = "SCGTipoDocumentoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		SCGTipoDocumentoEntity entityForDelete=null;
		UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
		if(entity.getCodigo()!=null){
			entityForDelete=this.datoGeneralDAO.getForUpdateTipoDocumentoDAO(entity);
			UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
			entityForDelete.setFlagEliminado("0");
		}else{
			entityForDelete=entity;
		}
		UNALMLogger.exit(log, method);
		
	}
	@Override
	public void deleteFiles(SCGAdjuntoEntity entity) {
		final String method="deleteFiles";
		UNALMLogger.entry(log, method);
		try {
			fileManagerComponent.delete(entity.getTextoRuta());
		} catch (Exception e) {
			UNALMLogger.error(log, method, "No se pudo sincronizar el archivo", e);
		}
				
		UNALMLogger.exit(log, method);
		
	}
	@Override
	public void syncFiles(SCGAdjuntoEntity entity) {
		final String method="syncFiles";
		UNALMLogger.entry(log, method);
		try {
			fileManagerComponent.write(entity.getFileArchivo(), entity.getTextoRuta());
		} catch (Exception e) {
			UNALMLogger.error(log, method, "No se pudo eliminar el archivo", e);
		}
				
		UNALMLogger.exit(log, method);
	}
	
	public  String getExternalCodeNotValidForSUNEDU(SCGTipoDocumentoEntity entity){
		String format=null;
		format = "NOT"+TypesUtil.getRandomNumber();
		return format;
	}
	
	@Override
	public String validatePeriodoEstudio(SCGEstudianteEntity entity) {
		final String method="validatePeriodoEstudio";
		final String params = "SCGEstudianteEntity entity";
		String result = "";
		
		UNALMLogger.entry(log, method, params, new Object[]{entity} );
		UNALMLogger.trace(log, method, "fechaIngresanteMatricula "+entity.getFechaIngresanteMatricula());
		UNALMLogger.trace(log, method, "fechaTramiteConstancia "+entity.getFechaTramiteConstancia());	
		
		if(entity.getFechaIngresanteMatricula() != null && entity.getFechaTramiteConstancia()  != null){
			
			if( entity.getFechaIngresanteMatricula().after( entity.getFechaTramiteConstancia() ) ){
				result = "La fecha inicio esta despues de la fecha final";
			}
		}
		
		UNALMLogger.exit(log, method, result);
		return result;

	}
	
}




