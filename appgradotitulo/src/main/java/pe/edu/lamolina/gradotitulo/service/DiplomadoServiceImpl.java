package pe.edu.lamolina.gradotitulo.service;

import java.util.Date;
import java.util.Formatter;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.lamolina.gradotitulo.dao.DiplomadoDAO;
import pe.edu.lamolina.gradotitulo.entity.SCGDiplomadoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGRevalidaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoDiplomadoEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;
@Service
@Transactional(rollbackFor = Exception.class)
public class DiplomadoServiceImpl implements DiplomadoService{
	
	private final static Logger log = Logger.getLogger(DiplomadoServiceImpl.class);

	@Autowired
	private DiplomadoDAO diplomadoDAO;
	
	@Override
	public List<SCGDiplomadoEntity> getListDiplomado(SCGDiplomadoEntity entity,FilterUtil filterUtil,List<Long> agendas) {
		final String method = "getListDiplomado";
		final String params = "SCGDiplomadoEntity entity,List<Long> agendas";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGDiplomadoEntity> result=this.diplomadoDAO.getListDiplomadoDAO(entity, filterUtil,agendas);
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGTipoDiplomadoEntity> getListTipoDiplomado(SCGTipoDiplomadoEntity entity) {
		final String method = "getListTipoDiplomado";
		final String params = "SCGTipoDiplomadoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		List<SCGTipoDiplomadoEntity> result=this.diplomadoDAO.getListTipoDiplomadoDAO(entity);
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public SCGDiplomadoEntity getForUpdateDiplomado(SCGDiplomadoEntity entity) {
		final String method = "getForUpdateDiplomado";
		final String params = "SCGDiplomadoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGDiplomadoEntity result=this.diplomadoDAO.getForUpdateDiplomadoDAO(entity);	
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public Long getNumberRegistroMaxDiplomado() {
		final String method = "getNumberRegistroMaxDiplomado";
		final String params = "SCGDiplomadoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{});
		Long result=this.diplomadoDAO.getNumberRegistroMaxDiplomadoDAO();	
		UNALMLogger.exit(log, method, result);
		return result;
	}
	@Override
	public SCGDiplomadoEntity saveDiplomado(SCGDiplomadoEntity entity) {

		final String method="saveDiplomado";
		final String params="SCGDiplomadoEntity entity";
		String formatoNumeroResolucion = null;
		SCGDiplomadoEntity entityForSave=null;
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
		if(entity.getCodigo()!=null){
			Long numeroRegistro =entity.getNumeroRegistro();
			entity.setNumeroRegistro(null);
			
			entityForSave =this.diplomadoDAO.getForUpdateDiplomadoDAO(entity);
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
			entityForSave.setAutoridadRegistroDecano(entity.getAutoridadRegistroDecano());
			entityForSave.setAutoridadRegistroRector(entity.getAutoridadRegistroRector());
			entityForSave.setEspecialidad(entity.getEspecialidad());
			entityForSave.setEstudiante(entity.getEstudiante());
			entityForSave.setAgendaGrupo(entity.getAgendaGrupo());
			entityForSave.setTextoResolucionFacultad(entity.getTextoResolucionFacultad());
			entityForSave.setFechaAprobacionCu(entity.getFechaAprobacionCu());
			entityForSave.setTextoResolucionRectoral(entity.getTextoResolucionRectoral());
			entityForSave.setNumeroFolio(entity.getNumeroFolio());
			entityForSave.setNumeroLibro(entity.getNumeroLibro());
			entityForSave.setFlagEnviadoSunedu(entity.getFlagEnviadoSunedu());
			entityForSave.setFlagEliminado(entity.getFlagEliminado());
			entityForSave.setFechaAgregar(entity.getFechaAgregar());
			entityForSave.setFechaEliminar(entity.getFechaEliminar());
			entityForSave.setTextoUsuarioAgregar(entity.getTextoUsuarioAgregar());
			entityForSave.setTextoUsuarioEdicion(entity.getTextoUsuarioEdicion());
			entityForSave.setTextoUsuarioEliminar(entity.getTextoUsuarioEliminar());
			entityForSave.setFechaEdicion(entity.getFechaEdicion());
			entityForSave.setFechaInicio(entity.getFechaInicio());
			entityForSave.setFechaFinal(entity.getFechaFinal());
			entityForSave.setFechaEdicion(entity.getFechaEdicion());
			entityForSave.setFechaCierre(entity.getFechaCierre());
			entityForSave.setTextoRegistroDiplomadoFacultad(entity.getTextoRegistroDiplomadoFacultad());
			entityForSave.setTextoNombreDiplomado(entity.getTextoNombreDiplomado());
			entityForSave.setNumeroHorasLectivas(entity.getNumeroHorasLectivas());
			entityForSave.setNumeroHorasTeoricoPtc(entity.getNumeroHorasTeoricoPtc());
			entityForSave.setNumeroPromedioFinal(entity.getNumeroPromedioFinal());
			entityForSave.setFechaResolucionFatultad(entity.getFechaResolucionFatultad());
			entityForSave.setTextoResolucionFacultad(entity.getTextoResolucionFacultad());
			entityForSave.setTipoDiplomado(entity.getTipoDiplomado());
			entityForSave.setFechaEdicion(new Date());

			if(entityForSave.getNumeroRegistro()==null){
				entityForSave.setNumeroRegistro(numeroRegistro);
				
			}
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
		SCGDiplomadoEntity result=this.diplomadoDAO.saveDiplomadoDAO(entityForSave);
		UNALMLogger.exit(log, method, result);
		return result;
	}

	@Override
	public void delete(SCGDiplomadoEntity entity) {
		final String method = "delete";
		final String params = "SCGDiplomadoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		SCGDiplomadoEntity entityForDelete=null;
		UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
		if(entity.getCodigo()!=null){
			entityForDelete=this.diplomadoDAO.getForUpdateDiplomadoDAO(entity);
			UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
			entityForDelete.setFlagEliminado("0");
		}else{
			entityForDelete=entity;
		}
		SCGDiplomadoEntity result=this.diplomadoDAO.saveDiplomadoDAO(entityForDelete);
		UNALMLogger.exit(log, method,result);
	}
	@Override
	public void enviarSunedu(SCGDiplomadoEntity entity) {
		final String method = "enviarSunedu";
		final String params = "SCGDiplomadoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		SCGDiplomadoEntity entityForSave=null;
		UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
		if(entity.getCodigo()!=null){
			entityForSave=this.diplomadoDAO.getForUpdateDiplomadoDAO(entity);
			UNALMLogger.trace(log, method, "entityForSave: "+entityForSave);
			entityForSave.setFlagEnviadoSunedu("1");
		}else{
			entityForSave=entity;
		}
		SCGDiplomadoEntity result=this.diplomadoDAO.saveDiplomadoDAO(entityForSave);
		UNALMLogger.exit(log, method,result);
		
	}
	@Override
	public String setFormatResolucionRecotoral(SCGDiplomadoEntity entity) {
		final String method = "setFormatResolucionRecotoral";
		final String params = "SCGDiplomadoEntity entity";
		String formatoNumeroResolucion =null,
				anuoAprobacionCU =null;
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		anuoAprobacionCU=TypesUtil.getDefaultString(entity.getFechaAprobacionCu(), "yyyy");
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
	
}
