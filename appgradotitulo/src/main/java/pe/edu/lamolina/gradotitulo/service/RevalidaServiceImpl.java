package pe.edu.lamolina.gradotitulo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.gradotitulo.dao.RevalidaDAO;
import pe.edu.lamolina.gradotitulo.entity.SCGAgendaGrupoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEspecialidadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGGradoAcademicoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGRevalidaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoRevalidaEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;
@Service
@Transactional(rollbackFor = Exception.class)
public class RevalidaServiceImpl implements RevalidaService{
	private final static Logger log = Logger.getLogger(RevalidaServiceImpl.class);

	@Autowired
	private RevalidaDAO revalidaDAO;
	
	@Override
	public List<SCGRevalidaEntity> getListRevalida(SCGRevalidaEntity entity,FilterUtil filterUtil,List<Long> agendas) {
		final String method = "getListRevalida";
		final String params = "SCGRevalidaEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGRevalidaEntity> result=this.revalidaDAO.getListRevalidaDAO(entity, filterUtil, agendas);
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public SCGRevalidaEntity getForUpdateRevalida(SCGRevalidaEntity entity) {
		final String method = "getForUpdateRevalida";
		final String params = "SCGRevalidaEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGRevalidaEntity result=this.revalidaDAO.getForUpdateRevalidaDAO(entity);	
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGTipoRevalidaEntity getForUpdateTipoRevalidaDAO(SCGTipoRevalidaEntity entity) {
		final String method = "getForUpdateTipoRevalidaDAO";
		final String params = "SCGTipoRevalidaEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGTipoRevalidaEntity result=this.revalidaDAO.getForUpdateTipoRevalidaDAO(entity);	
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGRevalidaEntity saveRevalida(SCGRevalidaEntity entity) {
		final String method="saveRevalida";
		final String params="SCGRevalidaEntity entity";
		String formatoNumeroResolucion = null;

		SCGRevalidaEntity entityForSave=null;
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
		if(entity.getCodigo()!=null){
			Long numeroRegistro =entity.getNumeroRegistro();			
			entity.setNumeroRegistro(null);
			
			entityForSave =this.revalidaDAO.getForUpdateRevalidaDAO(entity);
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
			entityForSave.setTextoResolucionFacultad(entity.getTextoResolucionFacultad());
			entityForSave.setFechaResolucionFacultad(entity.getFechaResolucionFacultad());
			entityForSave.setTextoResolucionEpg(entity.getTextoResolucionEpg());
			entityForSave.setFechaResolucionEpg(entity.getFechaResolucionEpg());
			entityForSave.setFechaAprobacionConsejoUniversitario(entity.getFechaAprobacionConsejoUniversitario());
			entityForSave.setTextoResolucionRectoral(entity.getTextoResolucionRectoral());
			entityForSave.setFechaSustentacionTesis(entity.getFechaSustentacionTesis());
			entityForSave.setTextoNombreTesis(entity.getTextoNombreTesis());
			entityForSave.setTextoNombreTrabajoInvestigacion(entity.getTextoNombreTrabajoInvestigacion());
			entityForSave.setTextoDetalle(entity.getTextoDetalle());
			entityForSave.setNumeroFolio(entity.getNumeroFolio());
			entityForSave.setNumeroLibro(entity.getNumeroLibro());
			
			//N° Registro activado momentariamente
			entityForSave.setNumeroRegistro(numeroRegistro);
			
			entityForSave.setTextoCodigoBarra(entity.getTextoCodigoBarra());
			entityForSave.setFlagDuplicado(entity.getFlagDuplicado());
			entityForSave.setFlagEnviadoSunedu(entity.getFlagEnviadoSunedu());
			entityForSave.setTextoSemestreEgreso(entity.getTextoSemestreEgreso());
			entityForSave.setFlagEliminado(entity.getFlagEliminado());
			entityForSave.setFechaAgregar(entity.getFechaAgregar());
			entityForSave.setFechaEliminar(entity.getFechaEliminar());
			entityForSave.setTextoUsuarioAgregar(entity.getTextoUsuarioAgregar());
			entityForSave.setTextoUsuarioEdicion(entity.getTextoUsuarioEdicion());
			entityForSave.setTextoUsuarioEliminar(entity.getTextoUsuarioEliminar());
			entityForSave.setFechaEdicion(entity.getFechaEdicion());
			
			entityForSave.setUniversidadRevalida(entity.getUniversidadRevalida());
			entityForSave.setTextoNombreTituloGrado(entity.getTextoNombreTituloGrado());
			entityForSave.setTextoLegalizado(entity.getTextoLegalizado());
			entityForSave.setFechaInicio(entity.getFechaInicio());
			entityForSave.setFechaFinal(entity.getFechaFinal());
			entityForSave.setFechaDiploma(entity.getFechaDiploma());
			entityForSave.setFechaEdicion(entity.getFechaEdicion());
			entityForSave.setFlagCandado(entity.getFlagCandado());
			entityForSave.setFechaCierre(entity.getFechaCierre());
			entityForSave.setFechaEdicion(new Date());
			entityForSave.setProgramaEstudio(entity.getProgramaEstudio());
			entityForSave.setTextoNombreProgramaEstudio(entity.getTextoNombreProgramaEstudio());

			/* Desactivado Momentariamente
			if(entityForSave.getNumeroRegistro()==null){
				entityForSave.setNumeroRegistro(numeroRegistro);
				
			}
			*/
			//nuevo

			entityForSave.setTextoSegundaEspecialidad(entity.getTextoSegundaEspecialidad());
			
			entityForSave.setNumeroCreditos(entity.getNumeroCreditos());
			
			entityForSave.setTextoRegistroMetadato(entity.getTextoRegistroMetadato());
			
			entityForSave.setTextoProcedenciaTituloPedagogico(entity.getTextoProcedenciaTituloPedagogico());
			
			entityForSave.setFechaDiplomaDuplicado(entity.getFechaDiplomaDuplicado());
			
			entityForSave.setTextoProcedenciaGradoExtranjero(entity.getTextoProcedenciaGradoExtranjero());
			
			entityForSave.setFechaMatriculaPrograma(entity.getFechaMatriculaPrograma());
			
			entityForSave.setFechaInicioPrograma(entity.getFechaInicioPrograma());
			
			entityForSave.setFechaTerminoPrograma(entity.getFechaTerminoPrograma());
			
			entityForSave.setFlagDiplomaSexo( entity.getFlagDiplomaSexo() );
			
			if(entityForSave.getFechaCierre()==null){
				entityForSave.setFechaCierre(entity.getFechaCierre());
				
			}
			if(entityForSave.getFlagCandado().equals("0")){
				entityForSave.setFlagCandado(entity.getFlagCandado());
			}
		}else{
			formatoNumeroResolucion =this.setFormatResolucionRecotoral(entity);
			entity.setTextoResolucionRectoral(formatoNumeroResolucion);
			entityForSave= entity;
		}
		SCGRevalidaEntity result=this.revalidaDAO.saveRevalidaDAO(entityForSave);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public void enviarSunedu(SCGRevalidaEntity entity) {
		final String method = "enviarSunedu";
		final String params = "SCGRevalidaEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		SCGRevalidaEntity entityForSave=null;
		UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
		if(entity.getCodigo()!=null){
			entityForSave=this.revalidaDAO.getForUpdateRevalidaDAO(entity);
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
			entityForSave.setFlagEnviadoSunedu("1");
		}else{
			entityForSave=entity;
		}
		SCGRevalidaEntity result=this.revalidaDAO.saveRevalidaDAO(entityForSave);
		UNALMLogger.exit(log, method,result);
		
	}
	
	@Override
	public Long getNumberRegistroMaxRevalida(SCGRevalidaEntity entity) {
		final String method = "getNumberRegistroMaxRevalida";
		final String params = "SCGRevalidaEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{});
		Long result=this.revalidaDAO.getNumberRegistroMaxRevalidaDAO(entity);	
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public void delete(SCGRevalidaEntity entity) {
		final String method = "delete";
		final String params = "SCGRevalidaEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		SCGRevalidaEntity entityForDelete=null;
		UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
		if(entity.getCodigo()!=null){
			entityForDelete=this.revalidaDAO.getForUpdateRevalidaDAO(entity);
			UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
			entityForDelete.setFlagEliminado("0");
		}else{
			entityForDelete=entity;
		}
		SCGRevalidaEntity result=this.revalidaDAO.saveRevalidaDAO(entityForDelete);
		UNALMLogger.exit(log, method,result);
		
	}
	@Override
	public String setFormatResolucionRecotoral(SCGRevalidaEntity entity){
		final String method = "setFormatResolucionRecotoral";
		final String params = "SCGRevalidaEntity entity";
		String formatoNumeroResolucion =null,
				anuoAprobacionCU =null;
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		anuoAprobacionCU=TypesUtil.getDefaultString(entity.getFechaAprobacionConsejoUniversitario(), "yyyy");
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

	@Override
	public void agendasClose(SCGRevalidaEntity entity, String groupList) {
		final String method="agendasClose";
		final String params = "SCGRevalidaEntity entity,String groupList";
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
		//SCGRevalidaEntity estudiante = new SCGRevalidaEntity();
		if(listCodigoAgenda.size()>0){
			FilterUtil filterUtil = new FilterUtil();

			SCGTipoRevalidaEntity tipoRevalidaTitulo = new SCGTipoRevalidaEntity();
			tipoRevalidaTitulo.setCodigoExterno("RTIT");
			SCGTipoRevalidaEntity tituloCodigoTipoRevalida = this.getForUpdateTipoRevalidaDAO(tipoRevalidaTitulo);
			SCGRevalidaEntity revalidaTitulo = new SCGRevalidaEntity();
			revalidaTitulo.setTipoRevalida(tituloCodigoTipoRevalida);
			revalidaTitulo.setFlagCandado("0");
			
			List<SCGRevalidaEntity> listOfOpenAgendasTitulo = this.revalidaDAO.getListRevalidaDAO(revalidaTitulo,filterUtil, agendas);
			UNALMLogger.trace(log, method, "listOfOpenAgendasTitulo:" + listOfOpenAgendasTitulo.size());
			List<SCGRevalidaEntity> listOfAgendasThatHasToBeCloseTitulo= this.revalidaDAO.getListRevalidaDAO(revalidaTitulo,filterUtil,listCodigoAgenda);
			UNALMLogger.trace(log, method, "listOfAgendasThatHasToBeCloseTitulo:" + listOfAgendasThatHasToBeCloseTitulo.size());
			Long numberRegistroMaxTitulo = this.revalidaDAO.getNumberRegistroMaxRevalidaDAO(revalidaTitulo);
			UNALMLogger.trace(log, method, "numberRegistroMaxDuplicadoTitulo:" + numberRegistroMaxTitulo);

			
			
			List<SCGRevalidaEntity> listOfAgendasThatWillNotBeClosedTitulo = getAgendasThatWillNotBeClosedList(listOfOpenAgendasTitulo,listOfAgendasThatHasToBeCloseTitulo);
			if(listOfAgendasThatWillNotBeClosedTitulo.size()>0){
				saveAgendasThatWillNotBeClosed(listOfAgendasThatWillNotBeClosedTitulo);
			}
			for(SCGRevalidaEntity agendaThatHasToBeClose:listOfAgendasThatHasToBeCloseTitulo){
				numberRegistroMaxTitulo++;
				agendaThatHasToBeClose.setNumeroRegistro(numberRegistroMaxTitulo);
				agendaThatHasToBeClose.setFlagCandado("1");	
				if(entity.getFechaCierre()!=null){
					agendaThatHasToBeClose.setFechaCierre(entity.getFechaCierre());
				}
				this.revalidaDAO.saveRevalidaDAO(agendaThatHasToBeClose);
			}
			
			
			SCGTipoRevalidaEntity tipoRevalidaGrado = new SCGTipoRevalidaEntity();
			SCGRevalidaEntity revalidaGrado = new SCGRevalidaEntity();
			tipoRevalidaGrado.setCodigoExterno("RGRAD");
			SCGTipoRevalidaEntity gradoCodigoTipoRevalida = this.getForUpdateTipoRevalidaDAO(tipoRevalidaGrado);
			revalidaGrado.setTipoRevalida(gradoCodigoTipoRevalida);
			revalidaGrado.setFlagCandado("0");
			
			List<SCGRevalidaEntity> listOfOpenAgendasGrado = this.revalidaDAO.getListRevalidaDAO(revalidaGrado,filterUtil, agendas);
			UNALMLogger.trace(log, method, "listOfOpenAgendasGrado:" + listOfOpenAgendasGrado.size());
			List<SCGRevalidaEntity> listOfAgendasThatHasToBeCloseGrado= this.revalidaDAO.getListRevalidaDAO(revalidaGrado,filterUtil,listCodigoAgenda);
			UNALMLogger.trace(log, method, "listOfAgendasThatHasToBeCloseGrado:" + listOfAgendasThatHasToBeCloseGrado.size());
			Long numberRegistroMaxGrado = this.revalidaDAO.getNumberRegistroMaxRevalidaDAO(revalidaGrado);
			UNALMLogger.trace(log, method, "numberRegistroMaxDuplicadoGrado:" + numberRegistroMaxGrado);

			List<SCGRevalidaEntity> listOfAgendasThatWillNotBeClosedGrado = getAgendasThatWillNotBeClosedList(listOfOpenAgendasGrado,listOfAgendasThatHasToBeCloseGrado);
			if(listOfAgendasThatWillNotBeClosedGrado.size()>0){
				saveAgendasThatWillNotBeClosed(listOfAgendasThatWillNotBeClosedGrado);
			}
			for(SCGRevalidaEntity agendaThatHasToBeCloseGrado:listOfAgendasThatHasToBeCloseGrado){
				numberRegistroMaxGrado++;
				agendaThatHasToBeCloseGrado.setNumeroRegistro(numberRegistroMaxGrado);
				agendaThatHasToBeCloseGrado.setFlagCandado("1");	
				if(entity.getFechaCierre()!=null){
					agendaThatHasToBeCloseGrado.setFechaCierre(entity.getFechaCierre());
				}
				this.revalidaDAO.saveRevalidaDAO(agendaThatHasToBeCloseGrado);
			}
			
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
	public void saveAgendasThatWillNotBeClosed(List<SCGRevalidaEntity> listOfAgendasThatWillNotBeClosed) {
		for(SCGRevalidaEntity agendaThatWillNotBeClosed:listOfAgendasThatWillNotBeClosed){
			agendaThatWillNotBeClosed.setAgendaGrupo(new SCGAgendaGrupoEntity());
			agendaThatWillNotBeClosed.getAgendaGrupo().setCodigo(1L);
			this.revalidaDAO.saveRevalidaDAO(agendaThatWillNotBeClosed);
		}
		
	}

	@Override
	public List<SCGRevalidaEntity> getAgendasThatWillNotBeClosedList(List<SCGRevalidaEntity> listOfOpenAgendas,
			List<SCGRevalidaEntity> listOfAgendasThatHasToBeClose) {
		final String method="getAgendasThatWillNotBeClosedList";
		final String params = "List<SCGRevalidaEntity> listOfOpenAgendas ,List<SCGRevalidaEntity> listOfAgendasThatHasToBeClose";
		UNALMLogger.entry(log, method);
		List<SCGRevalidaEntity> listOfAgendasThatWillNotBeClosed = new ArrayList<SCGRevalidaEntity>();
		if(listOfOpenAgendas.size()>0){
			UNALMLogger.trace(log, method, "listOfOpenAgendas.size(): "+listOfOpenAgendas.size());
			for(SCGRevalidaEntity openAgenda:listOfOpenAgendas){
				listOfAgendasThatWillNotBeClosed.add(openAgenda);
			}
		}  
		for(SCGRevalidaEntity openAgenda:listOfOpenAgendas){
			for(SCGRevalidaEntity agendaThatHasToBeClose:listOfAgendasThatHasToBeClose){
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
	public boolean isDuplicateRecord(SCGRevalidaEntity entity) {
		final String method = "isDuplicateRecord";
		final String params = "SCGRevalidaEntity entity";
		boolean result=false;
		String resolucionRecotoralFormat=null;
		SCGRevalidaEntity revalida= new SCGRevalidaEntity();
		List<Long> agendas = new ArrayList<Long>();
		try {
			revalida.setEstudiante(new SCGEstudianteEntity());
			//revalida.getEstudiante().setTextoMatricula(entity.getEstudiante().getTextoMatricula());
			revalida.getEstudiante().setCodigo(entity.getEstudiante().getCodigo());
			
			revalida.setGradoAcademico(new SCGGradoAcademicoEntity());
			revalida.getGradoAcademico().setCodigo(entity.getGradoAcademico().getCodigo());
			/*
			resolucionRecotoralFormat = this.revalidaService.setFormatResolucionRecotoral(entity);
			revalida.setTextoResolucionRectoral(resolucionRecotoralFormat);
			*/
			//revalida.setFlagCandado(ApplicationConstant.FLAG_CANDADO_ABIERTO);
			revalida.setFlagEliminado("1");
			if(entity.getEspecialidad()!=null){
				if(entity.getEspecialidad().getCodigo()!=null){
					revalida.setEspecialidad(new SCGEspecialidadEntity());
					UNALMLogger.trace(log, method, "especialidadCodigo: "+entity.getEspecialidad().getCodigo());
					revalida.getEspecialidad().setCodigo(entity.getEspecialidad().getCodigo());
				}
			}
			if(entity.getEspecialidadPostgrado()!=null){
				if(entity.getEspecialidadPostgrado().getCodigo()!=null){
					revalida.setEspecialidadPostgrado(new SCGEspecialidadEntity());
					UNALMLogger.trace(log, method, "especialidadPosgradoCodigo: "+entity.getEspecialidadPostgrado().getCodigo());
					revalida.getEspecialidadPostgrado().setCodigo(entity.getEspecialidadPostgrado().getCodigo());
				}
			}
			FilterUtil filterUtil = new FilterUtil();
			List<SCGRevalidaEntity> revalidaList = this.getListRevalida(revalida, filterUtil,agendas);
			if(revalidaList.size()>0){
				result=true;
			}
		} catch (Exception e) {
			result=true;
		}
		UNALMLogger.exit(log, method, "result: "+result);

		return result;
	}

	@Override
	public List<SCGRevalidaEntity> listDependentRecords(SCGRevalidaEntity entity) {
		final String method = "listDependentRecords";
		final String params = "SCGRevalidaEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGRevalidaEntity> result=this.revalidaDAO.listDependentRecordsDAO(entity);
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

}
