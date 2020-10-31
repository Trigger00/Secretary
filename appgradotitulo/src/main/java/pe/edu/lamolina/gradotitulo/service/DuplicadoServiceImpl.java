package pe.edu.lamolina.gradotitulo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.filemanager.component.FileManagerComponent;
import pe.edu.lamolina.gradotitulo.dao.DuplicadoDAO;
import pe.edu.lamolina.gradotitulo.dao.GradoTituloDAO;
import pe.edu.lamolina.gradotitulo.entity.SCGAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGAgendaGrupoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEspecialidadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGGradoAcademicoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoAdjuntoEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;
@Service
@Transactional(rollbackFor = Exception.class)
public class DuplicadoServiceImpl implements DuplicadoService{
	
	private final static Logger log = Logger.getLogger(DuplicadoServiceImpl.class);

	@Autowired
	private DuplicadoDAO duplicadoDAO;
	@Autowired
	private GradoTituloDAO gradoTituloDAO;
	@Autowired
	private FileManagerComponent fileManagerComponent;
	@Override
	public List<SCGEstudianteRegistroEntity> getListDuplicado(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas) {
		final String method = "getListDuplicado";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGEstudianteRegistroEntity> result=this.duplicadoDAO.getListDuplicadoDAO(entity,filterUtil,agendas);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGEstudianteRegistroEntity> getListDuplicadoAscendente(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas) {
		final String method = "getListDuplicadoAscendente";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGEstudianteRegistroEntity> result=this.duplicadoDAO.getListDuplicadoAscendenteDAO(entity,filterUtil,agendas);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGEstudianteRegistroEntity> getListDuplicadoDescendente(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas) {
		final String method = "getListDuplicadoDescendente";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGEstudianteRegistroEntity> result=this.duplicadoDAO.getListDuplicadoDescendenteDAO(entity,filterUtil,agendas);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	
	@Override
	public SCGEstudianteRegistroEntity getForUpdateDuplicado(SCGEstudianteRegistroEntity entity) {
		final String method = "getForUpdateDuplicado";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGEstudianteRegistroEntity result=this.duplicadoDAO.getForUpdateDuplicadoDAO(entity);	
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public SCGEstudianteRegistroAdjuntoEntity getForUpdateDuplicadoAdjunto(SCGEstudianteRegistroAdjuntoEntity entity) {
		final String method = "getForUpdateDuplicadoAdjunto";
		final String params = "SCGEstudianteRegistroAdjuntoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGEstudianteRegistroAdjuntoEntity result=this.duplicadoDAO.getForUpdateDuplicadoAdjuntoDAO(entity);	
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public Long getNumberRegistroMaxDuplicado(SCGEstudianteRegistroEntity entity) {
		final String method = "getNumberRegistroMaxDuplicadoDuplicado";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{});
		Long result=this.duplicadoDAO.getNumberRegistroMaxDuplicadoDAO(entity);	
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGEstudianteRegistroEntity saveDuplicado(SCGEstudianteRegistroEntity entity) {
		final String method="saveDuplicado";
		final String params="SCGEstudianteRegistroEntity entity";
		String formatoNumeroResolucion = null;
		SCGEstudianteRegistroEntity entityForSave=null;
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
		if(entity.getCodigo()!=null){
			Long numeroRegistro =entity.getNumeroRegistro();
			entity.setNumeroRegistro(null);
			
			entityForSave =this.duplicadoDAO.getForUpdateDuplicadoDAO(entity);
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
			entityForSave.setUniversidadBachiller(entity.getUniversidadBachiller());
			entityForSave.setUniversidadMaestria(entity.getUniversidadMaestria());
			entityForSave.setModalidadGradoTitulo(entity.getModalidadGradoTitulo());
			entityForSave.setModalidadEstudio(entity.getModalidadEstudio());
			entityForSave.setAutoridadRegistroDecano(entity.getAutoridadRegistroDecano());
			entityForSave.setAutoridadRegistroSecretarioGeneral(entity.getAutoridadRegistroSecretarioGeneral());
			entityForSave.setAutoridadRegistroRector(entity.getAutoridadRegistroRector());
			entityForSave.setAutoridadRegistroDirectorPostgrado(entity.getAutoridadRegistroDirectorPostgrado());
			entityForSave.setEspecialidad(entity.getEspecialidad());
			entityForSave.setEspecialidadPostgrado(entity.getEspecialidadPostgrado());
			entityForSave.setEstudiante(entity.getEstudiante());
			entityForSave.setGradoAcademico(entity.getGradoAcademico());
			entityForSave.setAgendaGrupo(entity.getAgendaGrupo());
			entityForSave.setTextoMatriculaPost(entity.getTextoMatriculaPost());
			entityForSave.setTextoResolucionFacultad(entity.getTextoResolucionFacultad());
			entityForSave.setFechaResolucionFacultad(entity.getFechaResolucionFacultad());
			entityForSave.setTextoResolucionEpg(entity.getTextoResolucionEpg());
			entityForSave.setFechaResolucionEpg(entity.getFechaResolucionEpg());
			entityForSave.setFechaAprobacioncu(entity.getFechaAprobacioncu());
			entityForSave.setTextoResolucionRectoral(entity.getTextoResolucionRectoral());
			entityForSave.setFechaSustentacionTesis(entity.getFechaSustentacionTesis());
			entityForSave.setTextoNombreTesis(entity.getTextoNombreTesis());
			entityForSave.setTextoNombreTrabajoInvestigacion(entity.getTextoNombreTrabajoInvestigacion());
			entityForSave.setFechaConstanciaEgreso(entity.getFechaConstanciaEgreso());
			entityForSave.setTextoDetalle(entity.getTextoDetalle());
			entityForSave.setNumeroFolio(entity.getNumeroFolio());
			entityForSave.setNumeroLibro(entity.getNumeroLibro());
			//entityForSave.setNumeroRegistro(entity.getNumeroRegistro());
			entityForSave.setTextoCodigoBarra(entity.getTextoCodigoBarra());
			entityForSave.setFlagDuplicado(entity.getFlagDuplicado());
			entityForSave.setFlagMatricula20141(entity.getFlagMatricula20141());
			entityForSave.setFlagEnviadoSunedu(entity.getFlagEnviadoSunedu());
			entityForSave.setTextoSemestreEgreso(entity.getTextoSemestreEgreso());
			entityForSave.setFlagEliminado(entity.getFlagEliminado());
			entityForSave.setFechaAgregar(entity.getFechaAgregar());
			entityForSave.setFechaEliminar(entity.getFechaEliminar());
			entityForSave.setTextoUsuarioAgregar(entity.getTextoUsuarioAgregar());
			entityForSave.setTextoUsuarioEdicion(entity.getTextoUsuarioEdicion());
			entityForSave.setTextoUsuarioEliminar(entity.getTextoUsuarioEliminar());
			entityForSave.setFechaEdicion(entity.getFechaEdicion());
			entityForSave.setTextoResolucionCambioNombre(entity.getTextoResolucionCambioNombre());
			entityForSave.setFechaEdicion(new Date());
			//nuevo

			entityForSave.setTextoSegundaEspecialidad(entity.getTextoSegundaEspecialidad());
			
			entityForSave.setNumeroCreditos(entity.getNumeroCreditos());
			
			entityForSave.setTextoRegistroMetadato(entity.getTextoRegistroMetadato());
			
			entityForSave.setTextoProcedenciaTituloPedagogico(entity.getTextoProcedenciaTituloPedagogico());
			
			entityForSave.setFechaDiplomaDuplicado(entity.getFechaDiplomaDuplicado());
			
			entityForSave.setTextoProcedenciaGradoExtranjero(entity.getTextoProcedenciaGradoExtranjero());
			
			entityForSave.setFechaMatriculaPrograma(entity.getFechaMatriculaPrograma());
			
			entityForSave.setFechaMatriculaPosgrado(entity.getFechaMatriculaPosgrado());
			
			entityForSave.setFechaEgresoPosgrado(entity.getFechaEgresoPosgrado());
			
			entityForSave.setFechaInicioPrograma(entity.getFechaInicioPrograma());
			
			entityForSave.setFechaTerminoPrograma(entity.getFechaTerminoPrograma());
			
			entityForSave.setTextoProcedenciaMaestriaExtranjero(entity.getTextoProcedenciaMaestriaExtranjero());

			//entityForSave.setFlagCandado(entity.getFlagCandado());
			//entityForSave.setFechaCierre(entity.getFechaCierre());
			if(entityForSave.getNumeroRegistro()==null){
				entityForSave.setNumeroRegistro(numeroRegistro);
				
			}
			if(entityForSave.getFechaCierre()==null){
				entityForSave.setFechaCierre(entity.getFechaCierre());
				
			}
			if(entityForSave.getFlagCandado().equals("0")){
				entityForSave.setFlagCandado(entity.getFlagCandado());
			}
			if(entity.getAdjunto()!=null){
				if(entity.getAdjunto().getCodigo() != null){
					UNALMLogger.trace(log, method, "entity.getAdjunto().getCodigo(): "+entity.getAdjunto().getCodigo());
					entityForSave.setAdjunto(new SCGAdjuntoEntity());
					entityForSave.getAdjunto().setCodigo(entity.getAdjunto().getCodigo());
				}
			}
		}else{
			formatoNumeroResolucion =this.setFormatResolucionRecotoral(entity);
			entity.setTextoResolucionRectoral(formatoNumeroResolucion);
			entityForSave= entity;
		}
		SCGEstudianteRegistroEntity result=this.duplicadoDAO.saveDuplicadoDAO(entityForSave);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGAdjuntoEntity saveAdjuntoDuplicado(SCGAdjuntoEntity entity) {
		final String method="saveAdjuntoDuplicado";
		final String params="SCGAdjuntoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGAdjuntoEntity entityForSave=null;
		if(entity.getCodigo() != null){
			UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
			entityForSave = this.duplicadoDAO.getForUpdateAdjuntoDuplicadoDAO(entity);
			this.deleteFiles(entityForSave);
			entityForSave.setTextoNombreArchivo(entity.getTextoNombreArchivo());
			entityForSave.setTextoRuta(entity.getTextoRuta());;						
			this.syncFiles(entity);
		}else{
			this.syncFiles(entity);
			entity.setFileArchivo(null);
			entityForSave=entity;
		}
		SCGAdjuntoEntity result=this.duplicadoDAO.saveAdjuntoDuplicadoDAO(entityForSave);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGEstudianteRegistroAdjuntoEntity saveDuplicadoAdjunto(SCGEstudianteRegistroAdjuntoEntity entity) {
		final String method="saveDuplicadoAdjunto";
		final String params="SCGEstudianteRegistroAdjuntoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGEstudianteRegistroAdjuntoEntity entityForSave=null;
		if(entity.getCodigo() != null){
			UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
			entityForSave = this.duplicadoDAO.getForUpdateDuplicadoAdjuntoDAO(entity);
			entityForSave.setAdjunto(entity.getAdjunto());
			entityForSave.setEstudianteRegistro(entity.getEstudianteRegistro());				
			entityForSave.setFlagEliminado(entity.getFlagEliminado());
			
		}else{
			entityForSave=entity;
		}
		SCGEstudianteRegistroAdjuntoEntity result=this.duplicadoDAO.saveDuplicadoAdjuntoDAO(entityForSave);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public void delete(SCGEstudianteRegistroEntity entity) {
		final String method = "delete";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		SCGEstudianteRegistroEntity entityForDelete=null;
		UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
		if(entity.getCodigo()!=null){
			entityForDelete=this.duplicadoDAO.getForUpdateDuplicadoDAO(entity);
			UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
			entityForDelete.setFlagEliminado("0");
		}else{
			entityForDelete=entity;
		}
		SCGEstudianteRegistroEntity result=this.duplicadoDAO.saveDuplicadoDAO(entityForDelete);
		UNALMLogger.exit(log, method,result);
		
	}
	@Override
	public void enviarSunedu(SCGEstudianteRegistroEntity entity) {
		final String method = "enviarSunedu";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		SCGEstudianteRegistroEntity entityForSave=null;
		UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
		if(entity.getCodigo()!=null){
			entityForSave=this.duplicadoDAO.getForUpdateDuplicadoDAO(entity);
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
			entityForSave.setFlagEnviadoSunedu("1");
		}else{
			entityForSave=entity;
		}
		SCGEstudianteRegistroEntity result=this.duplicadoDAO.saveDuplicadoDAO(entityForSave);
		UNALMLogger.exit(log, method,result);
		
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

	@Override
	public void agendasClose(SCGEstudianteRegistroEntity entity,String groupList) {
		final String method="agendasClose";
		final String params = "SCGEstudianteRegistroEntity entity,String groupList";
		UNALMLogger.entry(log, method, params,new Object[]{entity});
		List<Long> listCodigoAgenda = new ArrayList<Long>();
		List<Long> agendas = new ArrayList<Long>();
		String[] listOfSelectAgendas=groupList.split(":");
		
		if(listOfSelectAgendas.length>0){
			for (String selectAgenda : listOfSelectAgendas) {
				UNALMLogger.trace(log, method, "selectAgenda:" + selectAgenda);
				String[]splitSelectAgenda =getSplitSelectAgenda(selectAgenda);
				if(splitSelectAgenda.length >0){
					listCodigoAgenda=addCodigoAgenda(splitSelectAgenda);
				}
			}
		}
		
		UNALMLogger.trace(log, method, "codigoAgenda.size():" + listCodigoAgenda.size());
		
		SCGEstudianteRegistroEntity estudiante = new SCGEstudianteRegistroEntity();
		if(listCodigoAgenda.size()>0){
			estudiante.setFlagCandado("0");
			FilterUtil filterUtil = new FilterUtil();
			List<SCGEstudianteRegistroEntity> listOfOpenAgendas = this.duplicadoDAO.getListDuplicadoDAO(estudiante,filterUtil, agendas);
			UNALMLogger.trace(log, method, "listOfOpenAgendas:" + listOfOpenAgendas.size());
			List<SCGEstudianteRegistroEntity> listOfAgendasThatHasToBeClose= this.duplicadoDAO.getListDuplicadoDAO(estudiante,filterUtil,listCodigoAgenda);
			UNALMLogger.trace(log, method, "listOfAgendasThatHasToBeClose:" + listOfAgendasThatHasToBeClose.size());
			/*
			Long numberRegistroMaxDuplicado = this.duplicadoDAO.getNumberRegistroMaxDuplicadoDAO();
			UNALMLogger.trace(log, method, "numberRegistroMaxDuplicado:" + numberRegistroMaxDuplicado);
			*/

			
			
			List<SCGEstudianteRegistroEntity> listOfAgendasThatWillNotBeClosed = getAgendasThatWillNotBeClosedList(listOfOpenAgendas,listOfAgendasThatHasToBeClose);
			if(listOfAgendasThatWillNotBeClosed.size()>0){
				saveAgendasThatWillNotBeClosed(listOfAgendasThatWillNotBeClosed);
			}
			if(listOfAgendasThatHasToBeClose.size()>0){
				saveAgendasThatHasToBeClose(listOfAgendasThatHasToBeClose, entity);
			}
			/*
			for(SCGEstudianteRegistroEntity agendaThatHasToBeClose:listOfAgendasThatHasToBeClose){
				numberRegistroMaxDuplicado++;
				agendaThatHasToBeClose.setNumeroRegistro(numberRegistroMaxDuplicado);
				agendaThatHasToBeClose.setFlagCandado("1");	
				if(entity.getFechaCierre()!=null){
					agendaThatHasToBeClose.setFechaCierre(entity.getFechaCierre());
				}
				this.duplicadoDAO.saveDuplicadoDAO(agendaThatHasToBeClose);
				
				SCGEstudianteRegistroEntity estudianteRegistroOriginal =getEstudianteRegistroOriginal(agendaThatHasToBeClose);			
				UNALMLogger.trace(log, method,"agendaThatHasToBeClose.getNumeroRegistro() "+agendaThatHasToBeClose.getNumeroRegistro());	
				
				if(estudianteRegistroOriginal!=null){
					if(estudianteRegistroOriginal.getEstudianteRegistroPadre()!= null){
						updateParentRecordNumberToOriginalRecord(estudianteRegistroOriginal);
						estudianteRegistroOriginal.setEstudianteRegistroPadre(agendaThatHasToBeClose.getNumeroRegistro());
					}else{
						UNALMLogger.trace(log, method,"estudianteRegistroOriginal.getNumeroRegistro() "+estudianteRegistroOriginal.getNumeroRegistro());
						estudianteRegistroOriginal.setEstudianteRegistroPadre(agendaThatHasToBeClose.getNumeroRegistro());
						estudianteRegistroOriginal.setFlagAnulado("1");
						
					}
					this.gradoTituloDAO.saveEsudianteRegistroDAO(estudianteRegistroOriginal);	
				}
				
			}
			*/	
		}
		
	}
	public  List<Long> addCodigoAgenda(String[] splitSelectAgenda){
		List<Long> codigoAgenda = new ArrayList<Long>();
		for(String item:splitSelectAgenda){
			if(!TypesUtil.isEmptyString(item)){
				Long num =Long.parseLong(item, 10);
				codigoAgenda.add(num);
			}
		}
		return codigoAgenda;
	}
	public  String[] getSplitSelectAgenda(String selectAgenda){
		String delimiters = "(\\[)|,|(\\]$)";
		String[] values= selectAgenda.split(delimiters);
		return values;
	}
	public void saveAgendasThatWillNotBeClosed(List<SCGEstudianteRegistroEntity> listOfAgendasThatWillNotBeClosed ){
		for(SCGEstudianteRegistroEntity agendaThatWillNotBeClosed:listOfAgendasThatWillNotBeClosed){
			agendaThatWillNotBeClosed.setAgendaGrupo(new SCGAgendaGrupoEntity());
			agendaThatWillNotBeClosed.getAgendaGrupo().setCodigo(1L);
			this.duplicadoDAO.saveDuplicadoDAO(agendaThatWillNotBeClosed);
		}
		
	}
	public void saveAgendasThatHasToBeClose(List<SCGEstudianteRegistroEntity> listOfAgendasThatHasToBeClose,SCGEstudianteRegistroEntity entity) {
		final String method="saveAgendasThatHasToBeClose";
		final String params = "List<SCGEstudianteRegistroEntity> listOfAgendasThatHasToBeClose";
		UNALMLogger.entry(log, method);
		for(SCGEstudianteRegistroEntity agendaThatHasToBeClose:listOfAgendasThatHasToBeClose){
			if(agendaThatHasToBeClose.getGradoAcademico()!=null){
				if(!TypesUtil.isEmptyString(agendaThatHasToBeClose.getGradoAcademico().getCodigoExterno())){
					String codigoExternoRegistro =agendaThatHasToBeClose.getGradoAcademico().getCodigoExterno();
					UNALMLogger.trace(log, method, "codigoExternoRegistro :"+codigoExternoRegistro);
					SCGEstudianteRegistroEntity estudianteRegistro = new SCGEstudianteRegistroEntity();
					estudianteRegistro.setGradoAcademico(new SCGGradoAcademicoEntity());
					estudianteRegistro.getGradoAcademico().setCodigoExterno(codigoExternoRegistro);
					Long numberRegistroMaxDuplicado = this.duplicadoDAO.getNumberRegistroMaxDuplicadoDAO(estudianteRegistro);
					UNALMLogger.trace(log, method, "numberRegistroMaxDuplicado:" + numberRegistroMaxDuplicado);
					if(numberRegistroMaxDuplicado!=null){
						numberRegistroMaxDuplicado++;
						agendaThatHasToBeClose.setNumeroRegistro(numberRegistroMaxDuplicado);
						agendaThatHasToBeClose.setFlagCandado("1");	
						if(entity.getFechaCierre()!=null){
							agendaThatHasToBeClose.setFechaCierre(entity.getFechaCierre());
						}
						this.duplicadoDAO.saveDuplicadoDAO(agendaThatHasToBeClose);
						
						SCGEstudianteRegistroEntity estudianteRegistroOriginal =getEstudianteRegistroOriginal(agendaThatHasToBeClose);			
						UNALMLogger.trace(log, method,"agendaThatHasToBeClose.getNumeroRegistro() "+agendaThatHasToBeClose.getNumeroRegistro());	
						
						if(estudianteRegistroOriginal!=null){
							if(estudianteRegistroOriginal.getEstudianteRegistroPadre()!= null){
								updateParentRecordNumberToOriginalRecord(estudianteRegistroOriginal);
								estudianteRegistroOriginal.setEstudianteRegistroPadre(agendaThatHasToBeClose.getNumeroRegistro());
							}else{
								UNALMLogger.trace(log, method,"estudianteRegistroOriginal.getNumeroRegistro() "+estudianteRegistroOriginal.getNumeroRegistro());
								estudianteRegistroOriginal.setEstudianteRegistroPadre(agendaThatHasToBeClose.getNumeroRegistro());
								estudianteRegistroOriginal.setFlagAnulado("1");
								
							}
							this.gradoTituloDAO.saveEsudianteRegistroDAO(estudianteRegistroOriginal);	
						}
					}
				}
			}
		}
		UNALMLogger.exit(log, method);
		
	}
	
	public SCGEstudianteRegistroEntity getEstudianteRegistroOriginal(SCGEstudianteRegistroEntity agendaThatHasToBeClose){
		SCGEstudianteRegistroEntity numeroRegistroPadre= new SCGEstudianteRegistroEntity();
		numeroRegistroPadre.setNumeroRegistro(agendaThatHasToBeClose.getEstudianteRegistroPadre());
		if(agendaThatHasToBeClose.getGradoAcademico()!=null){
			numeroRegistroPadre.setGradoAcademico(agendaThatHasToBeClose.getGradoAcademico());
		}
		SCGEstudianteRegistroEntity estudianteRegistroOriginal =this.gradoTituloDAO.getForUpdateEsudianteRegistroDAO(numeroRegistroPadre);
		return estudianteRegistroOriginal;
	}
	
	public List<SCGEstudianteRegistroEntity> getAgendasThatWillNotBeClosedList(List<SCGEstudianteRegistroEntity> listOfOpenAgendas ,List<SCGEstudianteRegistroEntity> listOfAgendasThatHasToBeClose){
		final String method="getAgendasThatWillNotBeClosedList";
		final String params = "List<SCGEstudianteRegistroEntity> listOfOpenAgendas ,List<SCGEstudianteRegistroEntity> listOfAgendasThatHasToBeClose";
		UNALMLogger.entry(log, method);
		List<SCGEstudianteRegistroEntity> listOfAgendasThatWillNotBeClosed = new ArrayList<SCGEstudianteRegistroEntity>();
		if(listOfOpenAgendas.size()>0){
			UNALMLogger.trace(log, method, "listOfOpenAgendas.size(): "+listOfOpenAgendas.size());
			for(SCGEstudianteRegistroEntity openAgenda:listOfOpenAgendas){
				listOfAgendasThatWillNotBeClosed.add(openAgenda);
			}
		}  
		for(SCGEstudianteRegistroEntity openAgenda:listOfOpenAgendas){
			for(SCGEstudianteRegistroEntity agendaThatHasToBeClose:listOfAgendasThatHasToBeClose){
				if(openAgenda.getCodigo()==agendaThatHasToBeClose.getCodigo()){
					listOfAgendasThatWillNotBeClosed.remove(openAgenda);
					UNALMLogger.trace(log, method, "removiendo el registro:" + agendaThatHasToBeClose.getCodigo());
					UNALMLogger.trace(log, method, "listOfAgendasThatWillNotBeClosed.size():" + listOfAgendasThatWillNotBeClosed.size());
				}

			}
		}
		return listOfAgendasThatWillNotBeClosed;
	}
	public void updateParentRecordNumberToOriginalRecord(SCGEstudianteRegistroEntity entity){
		final String method="updateParentRecordNumberToOriginalRecord";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method, params,new Object[]{entity});
		UNALMLogger.trace(log, method,"entity.getEstudianteRegistroPadre() "+entity.getEstudianteRegistroPadre());
		SCGEstudianteRegistroEntity numeroRegistroDuplicado = new SCGEstudianteRegistroEntity();
		SCGEstudianteRegistroEntity duplicadoRegistroToAnular = new SCGEstudianteRegistroEntity();
		numeroRegistroDuplicado.setNumeroRegistro(entity.getEstudianteRegistroPadre());
		duplicadoRegistroToAnular = getForUpdateDuplicado(numeroRegistroDuplicado);
		if(duplicadoRegistroToAnular!=null){
			UNALMLogger.trace(log, method, "duplicadoRegistroToAnular: "+duplicadoRegistroToAnular);
			duplicadoRegistroToAnular.setFlagAnulado("1");
			saveDuplicado(duplicadoRegistroToAnular);
		}
		UNALMLogger.exit(log, method);

	}
	public String setFormatResolucionRecotoral(SCGEstudianteRegistroEntity entity){
		final String method = "setFormatResolucionRecotoral";
		final String params = "SCGEstudianteRegistroEntity entity";
		String formatoNumeroResolucion =null,
				anuoAprobacionCU =null;
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		anuoAprobacionCU=TypesUtil.getDefaultString(entity.getFechaAprobacioncu(), "yyyy");
		UNALMLogger.trace(log, method, "anuoAprobacionCU: "+anuoAprobacionCU);
		if(TypesUtil.isNumeric(entity.getTextoResolucionRectoral())){
			Long resolucionRectoral = Long.parseLong(entity.getTextoResolucionRectoral(),10);
			UNALMLogger.trace(log, method, "resolucionRectoral: "+resolucionRectoral);
			Formatter formatter = new Formatter();
			formatter.format("%03d",resolucionRectoral);
			formatoNumeroResolucion =formatter+"-"+anuoAprobacionCU+"-CU-UNALM";
		}else{
			formatoNumeroResolucion =entity.getTextoResolucionRectoral()+"-"+anuoAprobacionCU+"-CU-UNALM";	
		}
		UNALMLogger.exit(log, method, formatoNumeroResolucion);
		return formatoNumeroResolucion;
	}
	
	
	public SCGEstudianteRegistroAdjuntoEntity setValuesDuplicadoAdjunto(SCGEstudianteRegistroEntity duplicado,SCGAdjuntoEntity adjunto){
		final String method = "setValuesDuplicadoAdjunto";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGEstudianteRegistroEntity entity,SCGAdjuntoEntity adjunto";
		UNALMLogger.entry(log, method, params,new Object[]{duplicado});
		UNALMLogger.trace(log, method, "estudianteRegistro.getCodigo(): "+duplicado.getCodigo());
		UNALMLogger.trace(log, method, "adjunto.getCodigo(): "+adjunto.getCodigo());
		SCGEstudianteRegistroAdjuntoEntity duplicadoAdjunto = new SCGEstudianteRegistroAdjuntoEntity();
		duplicadoAdjunto.setAdjunto(adjunto);
		duplicadoAdjunto.getAdjunto().setTipoAdjunto(new SCGTipoAdjuntoEntity());
		duplicadoAdjunto.setEstudianteRegistro(duplicado);
		duplicadoAdjunto.setFlagEliminado("1");
		UNALMLogger.exit(log, method, duplicadoAdjunto);
		return duplicadoAdjunto;
	}
	public SCGAdjuntoEntity setValuesAdjunto(MultipartFile archivo,long codigoTipoAdjunto) throws IOException{
		String formatOriginalName=TypesUtil.setFormatUTF8(archivo.getOriginalFilename());
		String originalFilename=TypesUtil.getFormatFile(formatOriginalName);
		//String textoRuta = "adjuntos/"+originalFilename;
		byte[] fileArchivo= archivo.getBytes();
		SCGAdjuntoEntity adjunto = new SCGAdjuntoEntity();
		SCGTipoAdjuntoEntity tipoAdjunto = new SCGTipoAdjuntoEntity();
		adjunto.setFileArchivo(fileArchivo);
		//adjunto.setTextoRuta(textoRuta);			
		adjunto.setTextoNombreArchivo(originalFilename);
		adjunto.setFlagEliminado(ApplicationConstant.FLAG_ELIMINADO);
		tipoAdjunto.setCodigo(codigoTipoAdjunto);
		adjunto.setTipoAdjunto(tipoAdjunto);
		return adjunto;
	}
	@Override
	public String setURLEscaneado(SCGEstudianteRegistroEntity estudianteRegistro, SCGAdjuntoEntity adjunto) {
		String URLRegistro =null,
				   codigoExterno;
			if(estudianteRegistro.getGradoAcademico()!=null){
				if(!TypesUtil.isEmptyString(estudianteRegistro.getGradoAcademico().getCodigoExterno())){
					codigoExterno = estudianteRegistro.getGradoAcademico().getCodigoExterno();
					switch (codigoExterno) {
					case "B":
						URLRegistro = "diplomasbachilleres/DUPLICADOS/";
					    break;
					case "T":
						URLRegistro = "diplomastitulo/DUPLICADOS - TÍTULOS/";
					    break;
					case "M":
						URLRegistro = "diplomasmagister/DUPLICADOS - MAGISTER/";
					    break;
					case "D":
						URLRegistro = "diplomadoctoris/DUPLICADOS-DOCTORADO/";
					    break;
					}
					URLRegistro = URLRegistro+adjunto.getTextoNombreArchivo();
				}
			}
			return URLRegistro;
	}



	@Override
	public String setURLFoto(SCGEstudianteRegistroEntity estudianteRegistro, SCGAdjuntoEntity adjunto) {
		String URLRegistro =null,
				   codigoExterno;
			if(estudianteRegistro.getGradoAcademico()!=null){
				if(!TypesUtil.isEmptyString(estudianteRegistro.getGradoAcademico().getCodigoExterno())){
					codigoExterno = estudianteRegistro.getGradoAcademico().getCodigoExterno();
					switch (codigoExterno) {
					case "B":
						URLRegistro = "fotosbachilleres/DUPLICADOS/";
					    break;
					case "T":
						URLRegistro = "fotostitulos/duplicados/";
					    break;
					case "M":
						URLRegistro = "fotosmagister/DUPLICADOS MAGISTER/";
					    break;
					case "D":
						URLRegistro = "fotosdoctoris/DUPLICADOS-DOCTORADO/";
					    break;
					}
					URLRegistro = URLRegistro+adjunto.getTextoNombreArchivo();
				}
			}
			return URLRegistro;
	}

	public Long getCodigoAdjuntoByDuplicadoAdjunto(SCGEstudianteRegistroAdjuntoEntity entity){
		Long result = null;
		if(entity!=null){
			if(entity.getAdjunto()!=null){
				result =entity.getAdjunto().getCodigo();
			}
		}
		return result;
	}
	public SCGEstudianteRegistroAdjuntoEntity getDuplicadoAdjunto(Long codigo){
		final String method = "getDuplicadoAdjunto";
		final String params = "Long codigo";
		UNALMLogger.entry(log, method, params,new Object[]{codigo});
		UNALMLogger.trace(log, method, "codigo: "+codigo);
		SCGEstudianteRegistroAdjuntoEntity codigoDuplicadoAdjunto = new SCGEstudianteRegistroAdjuntoEntity();
		codigoDuplicadoAdjunto.setCodigo(codigo);
		SCGEstudianteRegistroAdjuntoEntity duplicadoAdjunto = getForUpdateDuplicadoAdjunto(codigoDuplicadoAdjunto);
		UNALMLogger.exit(log, method, duplicadoAdjunto);
		return duplicadoAdjunto;
	}
	
	public  Long getFormatCodigoAdjunto(String codigo){
		Long result =null;
		if(!TypesUtil.isEmptyString(codigo)){
			result=Long.parseLong(codigo, 10);
		}
		return result;
	}
	public boolean isDuplicateRecord(SCGEstudianteRegistroEntity entity){
		final String method = "isDuplicateRecord";
		final String params = "SCGEstudianteRegistroEntity entity";
		boolean result=false;
		//String resolucionRecotoralFormat=null;
		SCGEstudianteRegistroEntity duplicado= new SCGEstudianteRegistroEntity();
		List<Long> agendas = new ArrayList<Long>();
		UNALMLogger.entry(log, method, params,new Object[]{entity});

		try {
			duplicado.setEstudiante(new SCGEstudianteEntity());
			UNALMLogger.trace(log, method, "entity.getEstudiante(): "+entity.getEstudiante());
			duplicado.getEstudiante().setCodigo(entity.getEstudiante().getCodigo());
			UNALMLogger.trace(log, method, "entity.getEstudiante().getCodigo(): "+entity.getEstudiante().getCodigo());

			duplicado.setGradoAcademico(new SCGGradoAcademicoEntity());
			UNALMLogger.trace(log, method, "entity.getGradoAcademico(): "+entity.getGradoAcademico());
			UNALMLogger.trace(log, method, "entity.getGradoAcademico().getCodigo(): "+entity.getGradoAcademico().getCodigo());
			duplicado.getGradoAcademico().setCodigo(entity.getGradoAcademico().getCodigo());
			
			//resolucionRecotoralFormat = setFormatResolucionRecotoral(entity);
			//duplicado.setTextoResolucionRectoral(resolucionRecotoralFormat);
			
			//duplicado.setFlagCandado(ApplicationConstant.FLAG_CANDADO_ABIERTO);
			duplicado.setFlagEliminado("1");
			if(entity.getEspecialidad()!=null){
				if(entity.getEspecialidad().getCodigo()!=null){
					duplicado.setEspecialidad(new SCGEspecialidadEntity());
					UNALMLogger.trace(log, method, "especialidadCodigo: "+entity.getEspecialidad().getCodigo());
					duplicado.getEspecialidad().setCodigo(entity.getEspecialidad().getCodigo());
				}
			}
			UNALMLogger.trace(log, method, "entity.getEspecialidadPostgrado(): "+entity.getEspecialidadPostgrado());
			if(entity.getEspecialidadPostgrado()!=null){
				if(entity.getEspecialidadPostgrado().getCodigo()!=null){
					duplicado.setEspecialidadPostgrado(new SCGEspecialidadEntity());
					UNALMLogger.trace(log, method, "especialidadPosgradoCodigo: "+entity.getEspecialidadPostgrado().getCodigo());
					duplicado.getEspecialidadPostgrado().setCodigo(entity.getEspecialidadPostgrado().getCodigo());
				}
			}
    		FilterUtil filterUtil = new FilterUtil();
			List<SCGEstudianteRegistroEntity> estudianteRegistroList = getListDuplicado(duplicado, filterUtil,agendas);
			if(estudianteRegistroList.size()>0){
				result=true;
			}
		} catch (Exception e) {
			result=true;
		}
		UNALMLogger.exit(log, method, "result: "+result);

		return result;
	}

}
