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
import pe.edu.lamolina.gradotitulo.dao.DatoGeneralDAO;
import pe.edu.lamolina.gradotitulo.dao.GradoTituloDAO;
import pe.edu.lamolina.gradotitulo.entity.SCGAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGAgendaGrupoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEspecialidadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGGradoAcademicoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGModalidadEstudioEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGModalidadGradoTituloEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGModalidadGradoTituloGrupoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPaisEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGProgramaEstudioEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGUniversidadEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;
@Service
@Transactional(rollbackFor = Exception.class)
public class GradoTituloServiceImpl implements GradoTituloService{
	
	private final static Logger log = Logger.getLogger(GradoTituloServiceImpl.class);

	@Autowired
	private GradoTituloDAO gradoTituloDAO;
	@Autowired
	private FileManagerComponent fileManagerComponent;
	
	@Autowired
	private DatoGeneralDAO datoGeneralDAO;
	@Override
	public List<SCGEstudianteRegistroEntity> getListEsudianteRegistro(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas) {
		final String method = "getListEsudianteRegistro";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGEstudianteRegistroEntity> result=this.gradoTituloDAO.getListEsudianteRegistroDAO(entity,filterUtil,agendas);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGEstudianteRegistroEntity> getListEsudianteRegistroAscendente(SCGEstudianteRegistroEntity entity,
			FilterUtil filterUtil, List<Long> agendas) {
		final String method = "getListEsudianteRegistroAscendente";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGEstudianteRegistroEntity> result=this.gradoTituloDAO.getListEsudianteRegistroAscendeteDAO(entity, filterUtil, agendas);
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGEstudianteRegistroEntity> getListEsudianteRegistroDescendente(SCGEstudianteRegistroEntity entity,
			FilterUtil filterUtil, List<Long> agendas) {
		final String method = "getListEsudianteRegistroDescendente";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGEstudianteRegistroEntity> result=this.gradoTituloDAO.getListEsudianteRegistroDescendenteDAO(entity, filterUtil, agendas);
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGEstudianteRegistroEntity> listDependentRecords(SCGEstudianteRegistroEntity entity) {
		final String method = "listDependentRecords";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGEstudianteRegistroEntity> result=this.gradoTituloDAO.listDependentRecordsDAO(entity);
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	
	@Override
	public List<SCGPaisEntity> getListPais(SCGPaisEntity entity) {
		final String method = "getListPais";
		final String params = "SCGPaisEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGPaisEntity> result=this.gradoTituloDAO.getListPaisDAO(entity);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public List<SCGUniversidadEntity> getLisUniversidad(SCGUniversidadEntity entity) {
		final String method = "getLisUniversida";
		final String params = "SCGUniversidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGUniversidadEntity> result=this.gradoTituloDAO.getLisUniversidadDAO(entity);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	
	@Override
	public List<SCGModalidadGradoTituloGrupoEntity> getListModalidadGradoTituloGrupo(SCGModalidadGradoTituloGrupoEntity entity) {
		final String method = "getListModalidadGradoTituloGrupo";
		final String params = "SCGModalidadGradoTituloGrupoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGModalidadGradoTituloGrupoEntity> result=this.gradoTituloDAO.getListModalidadGradoTituloGrupoDAO(entity);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGModalidadEstudioEntity> getListModalidadEstudio(SCGModalidadEstudioEntity entity) {
		final String method = "getListModalidadEstudio";
		final String params = "SCGModalidadEstudioEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGModalidadEstudioEntity> result=this.gradoTituloDAO.getListModalidadEstudioDAO(entity);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGGradoAcademicoEntity> getListGradoAcademico(SCGGradoAcademicoEntity entity) {
		final String method = "getListGradoAcademico";
		final String params = "SCGGradoAcademicoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGGradoAcademicoEntity> result=this.gradoTituloDAO.getListGradoAcademicoDAO(entity);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public SCGEstudianteRegistroEntity getForUpdateEstudianteRegistro(SCGEstudianteRegistroEntity entity) {
		final String method = "getForUpdateEsudianteRegistro";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGEstudianteRegistroEntity result=this.gradoTituloDAO.getForUpdateEsudianteRegistroDAO(entity);	
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGGradoAcademicoEntity getForUpdateGradoAcademico(SCGGradoAcademicoEntity entity) {
		final String method = "getForUpdateGradoAcademico";
		final String params = "SCGGradoAcademicoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGGradoAcademicoEntity result=this.gradoTituloDAO.getForUpdateGradoAcademicoDAO(entity);	
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGUniversidadEntity getForUpdateUniversidad(SCGUniversidadEntity entity) {
		final String method = "getForUpdateUniversidad";
		final String params = "SCGUniversidadEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGUniversidadEntity result=this.gradoTituloDAO.getForUpdateUniversidadDAO(entity);	
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGModalidadEstudioEntity getForUpdateModalidadEstudio(SCGModalidadEstudioEntity entity) {
		final String method = "getForUpdateModalidadEstudio";
		final String params = "SCGModalidadEstudioEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGModalidadEstudioEntity result=this.gradoTituloDAO.getForUpdateModalidadEstudioDAO(entity);	
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGModalidadGradoTituloEntity getForUpdateModalidadGradoTitulo(SCGModalidadGradoTituloEntity entity) {
		final String method = "getModalidadGradoTitulo";
		final String params = "getForUpdateModalidadGradoTitulo entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGModalidadGradoTituloEntity result=this.gradoTituloDAO.getForUpdateModalidadGradoTituloDAO(entity);	
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGEstudianteRegistroAdjuntoEntity getForUpdateEstudianteRegistroAdjunto(
			SCGEstudianteRegistroAdjuntoEntity entity) {
		final String method = "getForUpdateEsudianteRegistroAdjunto";
		final String params = "SCGEstudianteRegistroAdjuntoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGEstudianteRegistroAdjuntoEntity result=this.gradoTituloDAO.getForUpdateEstudianteRegistroAdjuntoDAO(entity);	
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGAdjuntoEntity getForUpdateAdjunto(SCGAdjuntoEntity entity) {
		final String method = "getForUpdateAdjunto";
		final String params = "SCGAdjuntoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGAdjuntoEntity result=this.datoGeneralDAO.getForUpdateAdjuntoDAO(entity);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public Long getNumberRegistroMax(SCGEstudianteRegistroEntity entity) {
		final String method = "getNumberRegistroMax";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{});
		Long result=this.gradoTituloDAO.getNumberRegistroMaxDAO(entity);	
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGEstudianteRegistroEntity saveEsudianteRegistro(SCGEstudianteRegistroEntity entity) {
		//final String fomatNameResolucion= "RC-000";
		final String method="saveEsudianteRegistro";
		final String params="SCGEstudianteRegistroEntity entity";
		String formatoNumeroResolucion = null;
		SCGEstudianteRegistroEntity entityForSave=null;
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
		if(entity.getCodigo()!=null){
			Long numeroRegistro =entity.getNumeroRegistro();
			entity.setNumeroRegistro(null);
			entityForSave =this.gradoTituloDAO.getForUpdateEsudianteRegistroDAO(entity);
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
			entityForSave.setEstudianteRegistroPadre(entity.getEstudianteRegistroPadre());
			entityForSave.setTextoResolucionCambioNombre(entity.getTextoResolucionCambioNombre());
			entityForSave.setFechaEdicion(new Date());
			entityForSave.setProgramaEstudio(entity.getProgramaEstudio());
			entityForSave.setTextoNombreProgramaEstudio(entity.getTextoNombreProgramaEstudio());
			//entityForSave.setNumeroRegistro(entity.getNumeroRegistro());
			//nuevo
			entityForSave.setFechaEgresoPosgrado(entity.getFechaEgresoPosgrado());
			
			entityForSave.setTextoSegundaEspecialidad(entity.getTextoSegundaEspecialidad());
			
			entityForSave.setNumeroCreditos(entity.getNumeroCreditos());
			
			entityForSave.setTextoRegistroMetadato(entity.getTextoRegistroMetadato());
			
			entityForSave.setTextoProcedenciaTituloPedagogico(entity.getTextoProcedenciaTituloPedagogico());
			
			entityForSave.setFechaDiplomaDuplicado(entity.getFechaDiplomaDuplicado());
			
			entityForSave.setTextoProcedenciaGradoExtranjero(entity.getTextoProcedenciaGradoExtranjero());
			
			entityForSave.setFechaMatriculaPrograma(entity.getFechaMatriculaPrograma());
			
			entityForSave.setFechaMatriculaPosgrado(entity.getFechaMatriculaPosgrado());
			
			entityForSave.setFechaInicioPrograma(entity.getFechaInicioPrograma());
			
			entityForSave.setFechaTerminoPrograma(entity.getFechaTerminoPrograma());
			
			entityForSave.setFlagDiplomaSexo(entity.getFlagDiplomaSexo());
			
			entityForSave.setTextoProcedenciaMaestriaExtranjero(entity.getTextoProcedenciaMaestriaExtranjero());

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
		SCGEstudianteRegistroEntity result=this.gradoTituloDAO.saveEsudianteRegistroDAO(entityForSave);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGEstudianteRegistroAdjuntoEntity saveEstudianteRegistroAdjunto(SCGEstudianteRegistroAdjuntoEntity entity) {
		final String method="saveEstudianteRegistroAdjunto";
		final String params="SCGEstudianteRegistroAdjuntoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGEstudianteRegistroAdjuntoEntity entityForSave=null;
		if(entity.getCodigo() != null){
			UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
			entityForSave = this.gradoTituloDAO.getForUpdateEstudianteRegistroAdjuntoDAO(entity);
			entityForSave.setAdjunto(entity.getAdjunto());
			entityForSave.setEstudianteRegistro(entity.getEstudianteRegistro());				
			entityForSave.setFlagEliminado(entity.getFlagEliminado());
			
		}else{
			entityForSave=entity;
		}
		SCGEstudianteRegistroAdjuntoEntity result=this.gradoTituloDAO.saveEstudianteRegistroAdjuntoDAO(entityForSave);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public void updateNumeroRegistro(SCGEstudianteRegistroEntity entity) {
		final String method="updateNumeroRegistro";
		final String params="SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGEstudianteRegistroEntity entityForSave=null;
		if(entity.getCodigo() != null){
			UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
			Long numeroRegistro = entity.getNumeroRegistro();
			UNALMLogger.trace(log, method, "numeroRegistro: " + numeroRegistro );
			entity.setNumeroRegistro( null );
			entityForSave = this.gradoTituloDAO.getForUpdateEsudianteRegistroDAO( entity );
			UNALMLogger.trace(log, method, "entityForSave: "+ entityForSave );
			entityForSave.setNumeroRegistro( numeroRegistro );
			this.gradoTituloDAO.saveEsudianteRegistroDAO( entityForSave );

		}
		UNALMLogger.exit(log, method);
	}
//	CAMPO DE SELECCIONAR QUE ARCHIVO DESEA GUARDAR
//	@Override
//	public SCGAdjuntoEntity saveAdjunto(SCGAdjuntoEntity entity) {
//		final String method="saveAdjunto";
//		final String params="SCGAdjuntoEntity entity";
//		UNALMLogger.entry(log, method,params,new Object[]{entity});
//		SCGAdjuntoEntity entityForSave=null;
//		if(entity.getCodigo() != null){
//			UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
//			entityForSave = this.gradoTituloDAO.getForUpdateAdjuntoDAO(entity);
//			this.deleteFiles(entityForSave);
//			entityForSave.setTextoNombreArchivo(entity.getTextoNombreArchivo());
//			entityForSave.setTextoRuta(entity.getTextoRuta());;						
//			this.syncFiles(entity);
//		}else{
//			this.syncFiles(entity);
//			entity.setFileArchivo(null);
//			entityForSave=entity;
//		}
//		SCGAdjuntoEntity result=this.gradoTituloDAO.saveAdjuntoDAO(entityForSave);
//		UNALMLogger.exit(log, method, result);
//		return result;
//	}
	@Override
	public SCGAdjuntoEntity saveAdjunto(SCGAdjuntoEntity entity) {
		final String method="saveAdjunto";
		final String params="SCGAdjuntoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGAdjuntoEntity entityForSave=null;
		if(entity.getCodigo() != null){
			UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
			entityForSave = this.gradoTituloDAO.getForUpdateAdjuntoDAO(entity);
			entityForSave.setTextoNombreArchivo(entity.getTextoNombreArchivo());
			entityForSave.setTextoRuta(entity.getTextoRuta());;						
		}else{
			entityForSave=entity;
		}
		SCGAdjuntoEntity result=this.gradoTituloDAO.saveAdjuntoDAO(entityForSave);
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
			entityForDelete=this.gradoTituloDAO.getForUpdateEsudianteRegistroDAO(entity);
			UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
			entityForDelete.setFlagEliminado("0");
		}else{
			entityForDelete=entity;
		}
		SCGEstudianteRegistroEntity result=this.gradoTituloDAO.saveEsudianteRegistroDAO(entityForDelete);
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
			entityForSave=this.gradoTituloDAO.getForUpdateEsudianteRegistroDAO(entity);
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
			entityForSave.setFlagEnviadoSunedu("1");
		}else{
			entityForSave=entity;
		}
		SCGEstudianteRegistroEntity result=this.gradoTituloDAO.saveEsudianteRegistroDAO(entityForSave);
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
	public SCGEstudianteRegistroAdjuntoEntity setValuesEstudianteRegistroAdjunto(SCGEstudianteRegistroEntity estudianteRegistro,SCGAdjuntoEntity adjunto){
		final String method = "setValuesEstudianteRegistroAdjunto";
		final String params = "HttpServletRequest request,HttpServletResponse response,SCGEstudianteRegistroEntity entity,SCGAdjuntoEntity adjunto";
		UNALMLogger.entry(log, method, params,new Object[]{estudianteRegistro});
		UNALMLogger.trace(log, method, "estudianteRegistro.getCodigo(): "+estudianteRegistro.getCodigo());
		UNALMLogger.trace(log, method, "adjunto.getCodigo(): "+adjunto.getCodigo());
		SCGEstudianteRegistroAdjuntoEntity estudianteRegistroAdjunto = new SCGEstudianteRegistroAdjuntoEntity();
		estudianteRegistroAdjunto.setAdjunto(adjunto);
		estudianteRegistroAdjunto.getAdjunto().setTipoAdjunto(new SCGTipoAdjuntoEntity());
		estudianteRegistroAdjunto.setEstudianteRegistro(estudianteRegistro);
		estudianteRegistroAdjunto.setFlagEliminado("1");
		UNALMLogger.exit(log, method, estudianteRegistroAdjunto);
		return estudianteRegistroAdjunto;
	}
	public SCGAdjuntoEntity setValuesAdjunto(MultipartFile archivo,long codigoTipoAdjunto) throws IOException{
		
		
		String formatOriginalName=TypesUtil.setFormatUTF8(archivo.getOriginalFilename());
		String originalFilename=TypesUtil.getFormatFile(formatOriginalName);
		//String textoRuta = "adjuntos/"+originalFilename;
		//String textoRuta = originalFilename;
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
	public String setURLEscaneado(SCGEstudianteRegistroEntity estudianteRegistro,SCGAdjuntoEntity adjunto) {
		String URLRegistro =null,
			   codigoExterno;
		if(estudianteRegistro.getGradoAcademico()!=null){
			if(!TypesUtil.isEmptyString(estudianteRegistro.getGradoAcademico().getCodigoExterno())){
				codigoExterno = estudianteRegistro.getGradoAcademico().getCodigoExterno();
				switch (codigoExterno) {
				case "B":
					URLRegistro = "diplomasbachilleres/";
				    break;
				case "T":
					URLRegistro = "diplomastitulo/";
				    break;
				case "M":
					URLRegistro = "diplomasmagister/";
				    break;
				case "D":
					URLRegistro = "diplomadoctoris/";
				    break;
				}
				URLRegistro = URLRegistro+adjunto.getTextoNombreArchivo();
			}
			/*
			codigo = estudianteRegistro.getGradoAcademico().getCodigo();
			if(codigo==1L){
				URLRegistro="diplomasbachilleres/";
			}else if(codigo==2L){
				URLRegistro="diplomastitulo/";
			}else if(codigo==3L||codigo==5L){
				URLRegistro="diplomasmagister/";
			}else if(codigo==4L||codigo==6L){
				URLRegistro="diplomadoctoris/";
			}
			URLRegistro = URLRegistro+adjunto.getTextoRuta();
			*/
		}
		return URLRegistro;
	}
	@Override
	public String setURLFoto(SCGEstudianteRegistroEntity estudianteRegistro,SCGAdjuntoEntity adjunto) {
		String URLRegistro =null,
			   codigoExterno;
		if(estudianteRegistro.getGradoAcademico()!=null){
			if(!TypesUtil.isEmptyString(estudianteRegistro.getGradoAcademico().getCodigoExterno())){
				codigoExterno = estudianteRegistro.getGradoAcademico().getCodigoExterno();
				switch (codigoExterno) {
				case "B":
					URLRegistro = "fotosbachilleres/";
				    break;
				case "T":
					URLRegistro = "fotostitulos/";
				    break;
				case "M":
					URLRegistro = "fotosmagister/";
				    break;
				case "D":
					URLRegistro = "fotosdoctoris/";
				    break;
				}
				URLRegistro = URLRegistro+adjunto.getTextoNombreArchivo();
			}
		}
		return URLRegistro;
	}
	public Long getCodigoAdjuntoByEstudianteRegistroAdjunto(SCGEstudianteRegistroAdjuntoEntity entity){
		Long result = null;
		if(entity!=null){
			if(entity.getAdjunto()!=null){
				result =entity.getAdjunto().getCodigo();
			}
		}
		return result;
	}
	public SCGEstudianteRegistroAdjuntoEntity getEstudianteRegistroAdjunto(Long codigo){
		final String method = "getEstudianteRegistroAdjunto";
		final String params = "Long codigo";
		UNALMLogger.entry(log, method, params,new Object[]{codigo});
		UNALMLogger.trace(log, method, "codigo: "+codigo);
		SCGEstudianteRegistroAdjuntoEntity codigoEstudianteRegistroAdjunto = new SCGEstudianteRegistroAdjuntoEntity();
		codigoEstudianteRegistroAdjunto.setCodigo(codigo);
		SCGEstudianteRegistroAdjuntoEntity estudianteRegistroAdjunto = this.getForUpdateEstudianteRegistroAdjunto(codigoEstudianteRegistroAdjunto);
		UNALMLogger.exit(log, method, estudianteRegistroAdjunto);
		return estudianteRegistroAdjunto;
	}
	
	public Long getFormatCodigoAdjunto(String codigo){
		Long result =null;
		if(!TypesUtil.isEmptyString(codigo)){
			result=Long.parseLong(codigo, 10);
		}
		return result;
	}
	public boolean isDuplicateEstudinateRegistro(SCGEstudianteRegistroEntity entity){
		boolean result=false;
		String resolucionRecotoralFormat=null;
		SCGEstudianteRegistroEntity estudianteRegistro= new SCGEstudianteRegistroEntity();
		List<Long> agendas = new ArrayList<Long>();
		final String method = "isDuplicateEstudinateRegistro";
		final String params = "SCGEstudianteRegistroEntity entity";
		UNALMLogger.entry(log, method, params,new Object[]{entity});
		
		try {
			estudianteRegistro.setEstudiante(new SCGEstudianteEntity());
			//estudianteRegistro.getEstudiante().setTextoMatricula(entity.getEstudiante().getTextoMatricula());
			//estudianteRegistro.getEstudiante().setTextoNombreCompleto(entity.getEstudiante().getTextoNombreCompleto());
			estudianteRegistro.getEstudiante().setCodigo(entity.getEstudiante().getCodigo());
			
			estudianteRegistro.setGradoAcademico(new SCGGradoAcademicoEntity());
			estudianteRegistro.getGradoAcademico().setCodigo(entity.getGradoAcademico().getCodigo());
			estudianteRegistro.setFlagEliminado("1");
			if(entity.getEspecialidad()!=null){
				if(entity.getEspecialidad().getCodigo()!=null){
					estudianteRegistro.setEspecialidad(new SCGEspecialidadEntity());
					UNALMLogger.trace(log, method, "especialidadCodigo: "+entity.getEspecialidad().getCodigo());
					estudianteRegistro.getEspecialidad().setCodigo(entity.getEspecialidad().getCodigo());
				}
			}
			if(entity.getEspecialidadPostgrado()!=null){
				if(entity.getEspecialidadPostgrado().getCodigo()!=null){
					estudianteRegistro.setEspecialidadPostgrado(new SCGEspecialidadEntity());
					UNALMLogger.trace(log, method, "especialidadPosgradoCodigo: "+entity.getEspecialidadPostgrado().getCodigo());
					estudianteRegistro.getEspecialidadPostgrado().setCodigo(entity.getEspecialidadPostgrado().getCodigo());
				}
			}
			/*
			resolucionRecotoralFormat = this.setFormatResolucionRecotoral(entity);
			estudianteRegistro.setTextoResolucionRectoral(resolucionRecotoralFormat);
			*/
			//estudianteRegistro.setFlagCandado(ApplicationConstant.FLAG_CANDADO_ABIERTO);
			
			//List<SCGEstudianteRegistroEntity> estudianteRegistroList = this.gradoTituloService.getListEsudianteRegistro(estudianteRegistro, agendas);
			FilterUtil filterUtil = new FilterUtil();
			List<SCGEstudianteRegistroEntity> estudianteRegistroList = this.gradoTituloDAO.getListEsudianteRegistroDAO(estudianteRegistro, filterUtil, agendas);
			if(estudianteRegistroList.size()>0){
				UNALMLogger.trace(log, method, "estudianteRegistroList.size(): "+estudianteRegistroList.size());
				result=true;
			}
		} catch (Exception e) {
			result=true;
		}
		UNALMLogger.exit(log, method, "result: "+result);
		return result;
	}
	@Override
	public void agendasClose(SCGEstudianteRegistroEntity entity, String groupList) {
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
			FilterUtil filterUtil = new FilterUtil();
			estudiante.setFlagCandado("0");
			/*
			SCGEstudianteRegistroEntity estudianteRegistroBachiller = new SCGEstudianteRegistroEntity();
			estudianteRegistroBachiller.setGradoAcademico(new SCGGradoAcademicoEntity());
			estudianteRegistroBachiller.getGradoAcademico().setCodigoExterno("B");
			SCGEstudianteRegistroEntity estudianteRegistroTitulo = new SCGEstudianteRegistroEntity();
			estudianteRegistroTitulo.setGradoAcademico(new SCGGradoAcademicoEntity());
			estudianteRegistroTitulo.getGradoAcademico().setCodigoExterno("T");
			SCGEstudianteRegistroEntity estudianteRegistroMaestria = new SCGEstudianteRegistroEntity();
			estudianteRegistroMaestria.setGradoAcademico(new SCGGradoAcademicoEntity());
			estudianteRegistroMaestria.getGradoAcademico().setCodigoExterno("M");
			SCGEstudianteRegistroEntity estudianteRegistroDoctorado = new SCGEstudianteRegistroEntity();
			estudianteRegistroDoctorado.setGradoAcademico(new SCGGradoAcademicoEntity());
			estudianteRegistroDoctorado.getGradoAcademico().setCodigoExterno("D");
			*/
			List<SCGEstudianteRegistroEntity> listOfOpenAgendas = this.gradoTituloDAO.getListEsudianteRegistroDAO(estudiante,filterUtil, agendas);
			UNALMLogger.trace(log, method, "listOfOpenAgendas:" + listOfOpenAgendas.size());
			List<SCGEstudianteRegistroEntity> listOfAgendasThatHasToBeClose= this.gradoTituloDAO.getListEsudianteRegistroDAO(estudiante,filterUtil,listCodigoAgenda);
			UNALMLogger.trace(log, method, "listOfAgendasThatHasToBeClose:" + listOfAgendasThatHasToBeClose.size());
			/*
			Long numberRegistroBachillerMax = this.gradoTituloDAO.getNumberRegistroMaxDAO(estudianteRegistroBachiller);
			UNALMLogger.trace(log, method, "numberRegistroBachillerMax:" + numberRegistroBachillerMax);
			Long numberRegistroTituloMax = this.gradoTituloDAO.getNumberRegistroMaxDAO(estudianteRegistroTitulo);
			UNALMLogger.trace(log, method, "numberRegistroTitulorMax:" + numberRegistroTituloMax);
			Long numberRegistroMaestriaMax = this.gradoTituloDAO.getNumberRegistroMaxDAO(estudianteRegistroMaestria);
			UNALMLogger.trace(log, method, "numberRegistroMaestriaMax:" + numberRegistroMaestriaMax);
			Long numberRegistroDoctoradoMax = this.gradoTituloDAO.getNumberRegistroMaxDAO(estudianteRegistroDoctorado);
			UNALMLogger.trace(log, method, "numberRegistroDoctoradoMax:" + numberRegistroDoctoradoMax);

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
				if(agendaThatHasToBeClose.getGradoAcademico()!=null){
					if(!TypesUtil.isEmptyString(agendaThatHasToBeClose.getGradoAcademico().getCodigoExterno())){
						UNALMLogger.trace(log, method, "agendaThatHasToBeClose.getGradoAcademico().getCodigoExterno() :"+agendaThatHasToBeClose.getGradoAcademico().getCodigoExterno());
						if(agendaThatHasToBeClose.getGradoAcademico().getCodigoExterno().equals("B")){
							numberRegistroBachillerMax++;
							agendaThatHasToBeClose.setNumeroRegistro(numberRegistroBachillerMax);
							agendaThatHasToBeClose.setFlagCandado("1");	
							if(entity.getFechaCierre()!=null){
								agendaThatHasToBeClose.setFechaCierre(entity.getFechaCierre());
							}
							this.gradoTituloDAO.saveEsudianteRegistroDAO(agendaThatHasToBeClose);
						}
						if(agendaThatHasToBeClose.getGradoAcademico().getCodigoExterno().equals("T")){
							numberRegistroTituloMax++;
							agendaThatHasToBeClose.setNumeroRegistro(numberRegistroTituloMax);
							agendaThatHasToBeClose.setFlagCandado("1");	
							if(entity.getFechaCierre()!=null){
								agendaThatHasToBeClose.setFechaCierre(entity.getFechaCierre());
							}
							this.gradoTituloDAO.saveEsudianteRegistroDAO(agendaThatHasToBeClose);
						}
						if(agendaThatHasToBeClose.getGradoAcademico().getCodigoExterno().equals("M")){
							numberRegistroMaestriaMax++;
							agendaThatHasToBeClose.setNumeroRegistro(numberRegistroMaestriaMax);
							agendaThatHasToBeClose.setFlagCandado("1");	
							if(entity.getFechaCierre()!=null){
								agendaThatHasToBeClose.setFechaCierre(entity.getFechaCierre());
							}
							this.gradoTituloDAO.saveEsudianteRegistroDAO(agendaThatHasToBeClose);
						}
						if(agendaThatHasToBeClose.getGradoAcademico().getCodigoExterno().equals("D")){
							numberRegistroDoctoradoMax++;
							agendaThatHasToBeClose.setNumeroRegistro(numberRegistroDoctoradoMax);
							agendaThatHasToBeClose.setFlagCandado("1");	
							if(entity.getFechaCierre()!=null){
								agendaThatHasToBeClose.setFechaCierre(entity.getFechaCierre());
							}
							this.gradoTituloDAO.saveEsudianteRegistroDAO(agendaThatHasToBeClose);
						}
					}
				}
			}
			*/
			/*
			 for(SCGEstudianteRegistroEntity agendaThatHasToBeClose:listOfAgendasThatHasToBeClose){
				numberRegistroBachillerMax++;
				agendaThatHasToBeClose.setNumeroRegistro(numberRegistroBachillerMax);
				agendaThatHasToBeClose.setFlagCandado("1");	
				if(entity.getFechaCierre()!=null){
					agendaThatHasToBeClose.setFechaCierre(entity.getFechaCierre());
				}
				this.gradoTituloDAO.saveEsudianteRegistroDAO(agendaThatHasToBeClose);
			}
			*/	
		}
		
	}
	
	@Override
	public List<Long> addCodigoAgenda(String[] splitSelectAgenda) {
		List<Long> codigoAgenda = new ArrayList<Long>();
		for(String item:splitSelectAgenda){
			if(!TypesUtil.isEmptyString(item)){
				Long num =Long.parseLong(item, 10);
				codigoAgenda.add(num);
			}
		}
		return codigoAgenda;
	}
	
	@Override
	public String[] getSplitSelectAgenda(String selectAgenda) {
		String delimiters = "(\\[)|,|(\\]$)";
		String[] values= selectAgenda.split(delimiters);
		return values;
	}
	
	@Override
	public void saveAgendasThatWillNotBeClosed(List<SCGEstudianteRegistroEntity> listOfAgendasThatWillNotBeClosed) {
		for(SCGEstudianteRegistroEntity agendaThatWillNotBeClosed:listOfAgendasThatWillNotBeClosed){
			agendaThatWillNotBeClosed.setAgendaGrupo(new SCGAgendaGrupoEntity());
			agendaThatWillNotBeClosed.getAgendaGrupo().setCodigo(1L);
			this.gradoTituloDAO.saveEsudianteRegistroDAO(agendaThatWillNotBeClosed);
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
					SCGEstudianteRegistroEntity estudianteRegistroBachiller = new SCGEstudianteRegistroEntity();
					estudianteRegistroBachiller.setGradoAcademico(new SCGGradoAcademicoEntity());
					estudianteRegistroBachiller.getGradoAcademico().setCodigoExterno(codigoExternoRegistro);
					Long numberRegistroMax = this.gradoTituloDAO.getNumberRegistroMaxDAO(estudianteRegistroBachiller);
					UNALMLogger.trace(log, method, "numberRegistroMax:" + numberRegistroMax);
					if(numberRegistroMax!=null){
						numberRegistroMax++;
						agendaThatHasToBeClose.setNumeroRegistro(numberRegistroMax);
						agendaThatHasToBeClose.setFlagCandado("1");	
						if(entity.getFechaCierre()!=null){
							agendaThatHasToBeClose.setFechaCierre(entity.getFechaCierre());
						}
						this.gradoTituloDAO.saveEsudianteRegistroDAO(agendaThatHasToBeClose);
					}
				}
			}
		}
		UNALMLogger.exit(log, method);
		
	}
	@Override
	public List<SCGEstudianteRegistroEntity> getAgendasThatWillNotBeClosedList(
			List<SCGEstudianteRegistroEntity> listOfOpenAgendas,
			List<SCGEstudianteRegistroEntity> listOfAgendasThatHasToBeClose) {
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
	@Override
	public SCGProgramaEstudioEntity getForUpdateProgramaEstudio(SCGProgramaEstudioEntity entity) {
		final String method = "getForUpdateProgramaEstudio";
		final String params = "SCGProgramaEstudioEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGProgramaEstudioEntity result=this.gradoTituloDAO.getForUpdateProgramaEstudioDAO(entity);	
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public List<SCGProgramaEstudioEntity> getListProgramaEstudio(SCGProgramaEstudioEntity entity) {
		final String method = "getListProgramaEstudio";
		final String params = "SCGProgramaEstudioEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGProgramaEstudioEntity> result=this.gradoTituloDAO.getListProgramaEstudioDAO(entity);	
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public String validatePeriodoEstudio(SCGEstudianteRegistroEntity entity) {
		final String method="validatePeriodoEstudio";
		final String params = "SCGEstudianteRegistroEntity entity";
		String result = "";
		
		UNALMLogger.entry(log, method, params, new Object[]{entity} );
		UNALMLogger.trace(log, method, "fechaMatriculaPosgrado "+entity.getFechaMatriculaPosgrado());
		UNALMLogger.trace(log, method, "fechaEgresoPosgrado "+entity.getFechaEgresoPosgrado());	
		
		if(entity.getFechaMatriculaPosgrado() != null && entity.getFechaEgresoPosgrado()  != null){
			
			if( entity.getFechaMatriculaPosgrado().after( entity.getFechaEgresoPosgrado() ) ){
				result = "La fecha inicio esta despues de la fecha final";
			}
		}
		
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGAdjuntoEntity setPathAdjunto(SCGEstudianteRegistroEntity entity, long codgiTipoAdjunto) {
		final String method = "setPathAdjunto";
		final String params = "SCGEstudianteRegistroEntity entity, long codgiTipoAdjunto";
		UNALMLogger.entry(log, method, params, new Object[]{entity,codgiTipoAdjunto} );

		final String fileBachillerEscaneado = "diplomasbachilleres",
				fileBachillerEscaneadoSufijo ="BD",
				fileBachillerEscaneadoDuplicado ="DUPLICADOS",
				fileBachillerEscaneadoDuplicadoSufijo ="BDD";
		
		final String fileBachillerFoto = "fotosbachilleres",
				fileBachillerFotoSufijo ="BF",
				fileBachillerFotoDuplicado ="DUPLICADOS",
				fileBachillerFotoDuplicadoSufijo ="BFDD";

		final String fileTituloEscaneado = "diplomastitulo",
				fileTituloEscaneadoSufijo ="TD",
				fileTituloEscaneadoDuplicado ="DUPLICADOS - TÍTULOS",
				fileTituloEscaneadoDuplicadoSufijo ="TDD";
		
		final String fileTituloFoto = "fotostitulos",
				fileTituloFotoSufijo ="TF",
				fileTituloFotoDuplicado ="duplicados",
				fileTituloFotoDuplicadoSufijo ="TFD";
		
		final String fileMaestriaEscaneado = "diplomasmagister",
				fileMaestriaEscaneadoSufijo ="MD",
				fileMaestriaEscaneadoDuplicado ="DUPLICADOS - MAGISTER",
				fileMaestriaEscaneadoDuplicadoSufijo ="MDD";		
		
		final String fileMaestriaFoto = "fotosmagister",
				fileMaestriaFotoSufijo ="MF",
				fileMaestriaFotoDuplicado ="DUPLICADOS MAGISTER",
				fileMaestriaFotoDuplicadoSufijo ="MFD";

		final String fileDoctoradoEscaneado = "diplomadoctoris",
				fileDoctoradoEscaneadoSufijo ="DP",
				fileDoctoradoEscaneadoDuplicado ="",
				fileDoctoradoEscaneadoDuplicadoSufijo ="";
		
		final String fileDoctoradoFoto = "fotosdoctoris",
				fileDoctoradoFotoSufijo ="DPF",
				fileDoctoradoFotoDuplicado ="",
				fileDoctoradoFotoDuplicadoSufijo ="";
		SCGAdjuntoEntity result = new SCGAdjuntoEntity();
		String path = "";
		String nombreArchivo = "";
	
		UNALMLogger.trace(log, method, "entity.getGradoAcademico() = "+entity.getGradoAcademico());
		String codigoExternoGradoAcademico = entity.getGradoAcademico().getCodigoExterno();			
		String flagDuplicado = entity.getFlagDuplicado();

		if (codigoExternoGradoAcademico.equals("B")) {
			if(codgiTipoAdjunto == 2L){
				path = fileBachillerEscaneado + "/";
				if (flagDuplicado.equals("0")) {
					nombreArchivo = fileBachillerEscaneadoSufijo;

				}else if (flagDuplicado.equals("1")) {
					path += fileBachillerEscaneadoDuplicado+"/";
					nombreArchivo = fileBachillerEscaneadoDuplicadoSufijo;
				}
			}else if (codgiTipoAdjunto == 1L) {
				path = fileBachillerFoto + "/";
				if (flagDuplicado.equals("0")) {
					nombreArchivo = fileBachillerFotoSufijo;

				}else if (flagDuplicado.equals("1")) {
					path += fileBachillerFotoDuplicado+"/";
					nombreArchivo = fileBachillerFotoDuplicadoSufijo;
				}
			}
		} else if (codigoExternoGradoAcademico.equals("T")) {
			if(codgiTipoAdjunto == 2L){
				path = fileTituloEscaneado + "/";
				if (flagDuplicado.equals("0")) {
					nombreArchivo = fileTituloEscaneadoSufijo;

				}else if (flagDuplicado.equals("1")) {
					path += fileTituloEscaneadoDuplicado+"/";
					nombreArchivo = fileTituloEscaneadoDuplicadoSufijo;
				}
			}else if (codgiTipoAdjunto == 1L) {
				path = fileTituloFoto + "/";
				if (flagDuplicado.equals("0")) {
					nombreArchivo = fileTituloFotoSufijo;

				}else if (flagDuplicado.equals("1")) {
					path += fileTituloFotoDuplicado+"/";
					nombreArchivo = fileTituloFotoDuplicadoSufijo;
				}
			}
		} else if (codigoExternoGradoAcademico.equals("M")) {
			if(codgiTipoAdjunto == 2L){
				path = fileMaestriaEscaneado + "/";
				if (flagDuplicado.equals("0")) {
					nombreArchivo = fileMaestriaEscaneadoSufijo;
					
				}else if (flagDuplicado.equals("1")) {
					path += fileMaestriaEscaneadoDuplicado+"/";
					nombreArchivo = fileMaestriaEscaneadoDuplicadoSufijo;
				}
			}else if (codgiTipoAdjunto == 1L) {
				path = fileMaestriaFoto + "/";
				if (flagDuplicado.equals("0")) {
					nombreArchivo = fileMaestriaFotoSufijo;
					
				}else if (flagDuplicado.equals("1")) {
					path += fileMaestriaFotoDuplicado+"/";
					nombreArchivo = fileMaestriaFotoDuplicadoSufijo;
				}
			}
		} else if (codigoExternoGradoAcademico.equals("D")) {
			if(codgiTipoAdjunto == 2L){
				path = fileDoctoradoEscaneado + "/";
				if (flagDuplicado.equals("0")) {
					nombreArchivo = fileDoctoradoEscaneadoSufijo;
					
				}else if (flagDuplicado.equals("1")) {
					path += fileDoctoradoEscaneadoDuplicado+"/";
					nombreArchivo = fileDoctoradoEscaneadoDuplicadoSufijo;
				}
			}else if (codgiTipoAdjunto == 1L) {
				path = fileDoctoradoFoto + "/";
				if (flagDuplicado.equals("0")) {
					nombreArchivo = fileDoctoradoFotoSufijo;
					
				}else if (flagDuplicado.equals("1")) {
					path += fileDoctoradoFotoDuplicado+"/";
					nombreArchivo = fileDoctoradoFotoDuplicadoSufijo;
				}
			}
		}
		
		String añoCU = TypesUtil.getDefaultString(entity.getFechaAprobacioncu(), "yyyy");
		String numeroRegistro = TypesUtil.getDefaultString(entity.getNumeroRegistro());
		nombreArchivo += "-" + añoCU + "-" + numeroRegistro + ".jpg";
		path +=nombreArchivo;
		result.setTextoRuta(path);	
		result.setTextoNombreArchivo(nombreArchivo);
		SCGTipoAdjuntoEntity tipoAdjunto = new SCGTipoAdjuntoEntity();
		tipoAdjunto.setCodigo(codgiTipoAdjunto);
		result.setTipoAdjunto(tipoAdjunto);
		result.setFlagEliminado("1");
		return result;
	}
	
}
