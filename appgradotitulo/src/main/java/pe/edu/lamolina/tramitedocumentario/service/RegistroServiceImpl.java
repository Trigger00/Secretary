package pe.edu.lamolina.tramitedocumentario.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.security.entity.SeguridadUsuarioEntity;
import pe.edu.lamolina.tramitedocumentario.dao.RegistroDAO;
import pe.edu.lamolina.tramitedocumentario.entity.SCGDecisionProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGDefinicionProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGFlujoProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGResponsabilidadEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGResponsableEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGSolicitudProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGTipoTramiteDocumentoEntity;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

@Service
@Transactional( rollbackFor = Exception.class)
public class RegistroServiceImpl implements RegistroService{
	
	private final static Logger log = Logger.getLogger( RegistroServiceImpl.class );
	
	@Autowired
	RegistroDAO registroDAO;
	
	@Override
	public List<SCGTipoTramiteDocumentoEntity> listTipoTramiteDocumento() {
		final String method = "listTipoTramiteDocumento";
		UNALMLogger.entry(log, method);
		List<SCGTipoTramiteDocumentoEntity> result = this.registroDAO.listTipoTramiteDocumentoDAO();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public List<SCGResponsabilidadEntity> listResponsabilidad(SCGResponsabilidadEntity entity) {
		final String method = "listResponsabilidad";
		UNALMLogger.entry(log, method);
		List<SCGResponsabilidadEntity> result = this.registroDAO.listResponsabilidadDAO(entity);
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public List<SCGProcesoEntity> listProceso() {
		final String method = "listProceso";
		UNALMLogger.entry(log, method);
		List<SCGProcesoEntity> result = this.registroDAO.listProcesoDAO();
		UNALMLogger.exit(log, method, result.size());
		return result;
	}

	@Override
	public List<SCGDefinicionProcesoEntity> listDefinicionProceso(SCGDefinicionProcesoEntity entity) {
		final String method = "listDefinicionProceso";
		UNALMLogger.entry(log, method);
		SCGDefinicionProcesoEntity dataByFilterDefinicionProceso = new SCGDefinicionProcesoEntity();
		dataByFilterDefinicionProceso.setFlagInicio(entity.getFlagInicio());
		if(entity.getProceso() != null){
			dataByFilterDefinicionProceso.setProceso(entity.getProceso());
		}
		List<SCGDefinicionProcesoEntity> result = this.registroDAO.listDefinicionProcesoDAO(entity);
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	@Override
	public List<SCGSolicitudProcesoEntity> listSolicitudProceso(SCGSolicitudProcesoEntity entity,FilterUtil filterUtil) {
		final String method = "listSolicitudProceso";
		UNALMLogger.entry(log, method);
		
		if(entity.getFechaAgregarFinal() != null && entity.getFechaAgregarInicio() != null){
			String fechaInicio = TypesUtil.getDefaultString(entity.getFechaAgregarInicio(), "yyy-MM-dd");
			String fechaFinal = TypesUtil.getDefaultString(entity.getFechaAgregarFinal(), "yyy-MM-dd");
			UNALMLogger.trace(log, method,"fechaAgregarFinal: "+ fechaInicio);
			UNALMLogger.trace(log, method,"fechaAgregarInicio: "+ fechaFinal);

			if(fechaInicio.equals(ApplicationConstant.DATE_INVALID) && fechaFinal.equals(ApplicationConstant.DATE_INVALID)){
				entity.setFechaAgregarFinal(null);
				entity.setFechaAgregarInicio(null);
			}
		}
		
		UNALMLogger.trace(log, method,"entity.getFlagCandado(): "+ entity.getFlagCandado());
		if(!TypesUtil.isEmptyString(entity.getFlagCandado())){
    		if(entity.getFlagCandado().equals("2")){
    			entity.setFlagCandado(null);
    		}
    	}else if(!TypesUtil.isEmptyString(entity.getTextoNombreMatch())){
    		entity.setFlagCandado(null);
    	}else{
    		entity.setFlagCandado("0");
    	}
		
		
//		List<SCGSolicitudProcesoEntity> result = this.registroDAO.listSolicitudProcesoDAO(entity,filterUtil);
		List<SCGSolicitudProcesoEntity> result = new ArrayList<SCGSolicitudProcesoEntity>();
		List<SCGSolicitudProcesoEntity> list = this.registroDAO.listSolicitudProcesoDAO(entity,filterUtil);
		
		for(SCGSolicitudProcesoEntity item : list){
			
			item.setTextoTipoColor( this.getTipoColor(item) );
			
			result.add(item);
		}
		
		
		
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	
	private String getTipoColor(SCGSolicitudProcesoEntity solicitudProceso){
		final String method = "getTipoColor";
		UNALMLogger.entry(log, method);
		String result = "N";
		
		UNALMLogger.trace(log, method,"solicitudProceso.getFlagCandado(): "+ solicitudProceso.getFlagCandado());
		if( solicitudProceso.getFlagCandado().equals("0")){
			
			if(solicitudProceso.getFechaInicio() != null && solicitudProceso.getFechaFinal() != null){
				int horasTotal = TypesUtil.getHourBetweenDate(solicitudProceso.getFechaInicio(), solicitudProceso.getFechaFinal());
				int horasTranscurridas = TypesUtil.getHourBetweenDate( solicitudProceso.getFechaInicio(), new Date());
				
				UNALMLogger.trace(log, method, "horasTotal "+horasTotal);
				UNALMLogger.trace(log, method, "horasTranscurridas "+horasTranscurridas);
				
				if(horasTotal > 0 && horasTranscurridas >= 0){
					int nivelUno = (horasTotal/3);
					UNALMLogger.trace(log, method, "nivelUno "+nivelUno);
					int nivelDos = (horasTotal/3)*2;
					UNALMLogger.trace(log, method, "nivelDos "+nivelDos);

					if(horasTranscurridas <= nivelUno){
						result = "V";
						UNALMLogger.trace(log, method, "verde ");
					
					}else if(nivelUno <= horasTranscurridas && horasTranscurridas <= nivelDos){
						result = "A";
						UNALMLogger.trace(log, method, "amarillo ");
					
					}else if(horasTranscurridas > nivelDos){
						result = "R";
						UNALMLogger.trace(log, method, "rojo ");
					}
					
				}

			}
			
			SCGFlujoProcesoEntity flujoProceso = this.getForUpdateSolicitudProceso(solicitudProceso);
			
			if(flujoProceso != null){
				if(flujoProceso.getDefinicionProceso() != null){
					if(flujoProceso.getDefinicionProceso().getProceso() != null){
						SCGProcesoEntity proceso =flujoProceso.getDefinicionProceso().getProceso();
						
						if(proceso.getTextoNombre().equals("POR DEFINIR")){
							result = "R";
						}
						
					}
				}
				
			}
			
		}
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	
	@Override
	public List<SCGFlujoProcesoEntity> listFlujoProceso(SCGFlujoProcesoEntity entity, FilterUtil filterUtil) {
		final String method = "listFlujoProceso";
		UNALMLogger.entry(log, method);
		List<SCGFlujoProcesoEntity> result = this.registroDAO.listFlujoProcesoDAO(entity,filterUtil);
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	
	@Override
	public List<SCGSolicitudProcesoEntity> listDistinctProcedencia(SCGSolicitudProcesoEntity entity,
			FilterUtil filterUtil) {
		final String method = "listDistinctProcedencia";
		UNALMLogger.entry(log, method);		
		UNALMLogger.trace(log, method,"entity.getFlagCandado(): "+ entity.getFlagCandado());
		
//		List<SCGSolicitudProcesoEntity> result = this.registroDAO.listSolicitudProcesoDAO(entity,filterUtil);
		List<SCGSolicitudProcesoEntity> result = this.registroDAO.listDistinctProcedenciaDAO(entity, filterUtil);
		
		UNALMLogger.exit(log, method, result.size());
		return result;
	}
	
	@Override
	public SCGSolicitudProcesoEntity saveSolicitudProceso(SCGSolicitudProcesoEntity entity) {
		final String method = "saveSolicitudProceso";
		final String params = "SCGUniversidadEntity entity";
		UNALMLogger.entry(log, method, params, new Object[]{ entity });
		SCGSolicitudProcesoEntity entityForSave = null;
		
		if( entity.getCodigo() != null ){
			entityForSave = this.registroDAO.getForUpdateSolicitudProcesoDAO( entity );
			entityForSave.setSeguridadUsuario(entity.getSeguridadUsuario());
			entityForSave.setTipoTramiteDocumento(entity.getTipoTramiteDocumento());
			entityForSave.setTextoNombreProcedencia(entity.getTextoNombreProcedencia());
			entityForSave.setTextoNombreRemitente(entity.getTextoNombreRemitente());
			entityForSave.setTextoNumeroDocumento(entity.getTextoNumeroDocumento());
			entityForSave.setTextoAsunto(entity.getTextoAsunto());
			entityForSave.setTextoObservacion(entity.getTextoObservacion());
			entityForSave.setFechaInicio(entity.getFechaInicio());
			entityForSave.setFechaFinal(entity.getFechaFinal());

		}else{
			Long codigoAsociacion = this.registroDAO.getMaxCodigoAsociacionDAO(new SCGSolicitudProcesoEntity());
			UNALMLogger.trace(log, method, "codigoAsociacion "+codigoAsociacion);
			
			if(codigoAsociacion == 0L){
				codigoAsociacion = 1000L;
			}else{
				codigoAsociacion = codigoAsociacion + 1L;
			}
			
			entity.setCodigoAsociacion(codigoAsociacion);
			entity.setFlagCandado("0");
			entity.setFlagEliminado("0");
			entity.setFlagActivo("1");
			entity.setFechaAgregar(new Date());
			entityForSave = entity;
		}
		
		SCGSolicitudProcesoEntity result = this.registroDAO.saveSolicitudProcesoDAO( entityForSave );
		UNALMLogger.exit(log, method, result );
		return result;
	}
	@Override
	public SCGFlujoProcesoEntity saveFlujoProceso(SCGFlujoProcesoEntity entity,SCGSolicitudProcesoEntity solicitudProceso) {
		final String method = "saveFlujoProceso";
		final String params = "SCGFlujoProcesoEntity entity,SCGSolicitudProcesoEntity solicitudProceso";
		UNALMLogger.entry(log, method, params, new Object[]{ entity });
		SCGFlujoProcesoEntity entityForSave = null;
		UNALMLogger.trace(log, method, "solicitudProceso "+solicitudProceso);
		UNALMLogger.trace(log, method, "solicitudProceso.codigo "+solicitudProceso.getCodigo());
		UNALMLogger.trace(log, method, "entity "+entity);
		UNALMLogger.trace(log, method, "entity.getDefinicionProceso() " + entity.getDefinicionProceso());
		UNALMLogger.trace(log, method, "entity.getDefinicionProceso().getCodigo() " + entity.getDefinicionProceso().getCodigo());
		UNALMLogger.trace(log, method, "entity.getDefinicionProceso().getProceso().getCodigo() "+entity.getDefinicionProceso().getProceso().getCodigo());
		UNALMLogger.trace(log, method, "entity.getResponsable() " + entity.getResponsable());
		UNALMLogger.trace(log, method, "entity.getSeguridadUsuario() " + entity.getSeguridadUsuario());
		UNALMLogger.trace(log, method, "entity.getSolicitudProceso() " + entity.getSolicitudProceso());
		
		
		SCGDefinicionProcesoEntity dataByFilterDefinicionProceso = new SCGDefinicionProcesoEntity();
		if(entity.getDefinicionProceso() != null){
			dataByFilterDefinicionProceso.setCodigo(entity.getDefinicionProceso().getCodigo());
			dataByFilterDefinicionProceso.setFlagInicio(entity.getDefinicionProceso().getFlagInicio());
			
			if(entity.getDefinicionProceso().getProceso() != null){
				SCGProcesoEntity proceso = entity.getDefinicionProceso().getProceso();
				dataByFilterDefinicionProceso.setProceso(new SCGProcesoEntity());
				dataByFilterDefinicionProceso.getProceso().setCodigo(proceso.getCodigo());
			}
		}
		
		SCGDefinicionProcesoEntity definicionProceso = new SCGDefinicionProcesoEntity();
		definicionProceso = this.registroDAO.getForUpdateDefinicionProcesoDAO(dataByFilterDefinicionProceso);
		
		entity.setDefinicionProceso(definicionProceso);
		entity.setSolicitudProceso(solicitudProceso);
		entity.setFlagActivo("1");
		entity.setFlagEliminado("0");
		entity.setFlagCandado("0");
		entity.setFechaInicio(new Date());
		entityForSave = entity;
		SCGFlujoProcesoEntity result = this.registroDAO.saveFlujoProcesoDAO(entityForSave);
		UNALMLogger.exit(log, method, result );
		return result;
	}
	@Override
	public SCGFlujoProcesoEntity getForUpdateSolicitudProceso(SCGSolicitudProcesoEntity entity) {
		final String method = "getForUpdateSolicitudProceso";
		final String params = "SCGSolicitudProcesoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		
		SCGFlujoProcesoEntity dataByFilterflujoProceso = new SCGFlujoProcesoEntity();
		dataByFilterflujoProceso.setSolicitudProceso(entity);
		dataByFilterflujoProceso.setDefinicionProceso(new SCGDefinicionProcesoEntity());
//		dataByFilterflujoProceso.getDefinicionProceso().setFlagInicio("1");
		Long codigoFlujoProceso = this.registroDAO.getMaxCodigoFlujoProcesoDAO(dataByFilterflujoProceso);
		UNALMLogger.trace(log, method, "codigoFlujoProceso "+codigoFlujoProceso);
		
		dataByFilterflujoProceso = new SCGFlujoProcesoEntity();
		dataByFilterflujoProceso.setCodigo(codigoFlujoProceso);
		SCGFlujoProcesoEntity result = this.registroDAO.getForUpdateFlujoProcesoDAO(dataByFilterflujoProceso);
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public SCGFlujoProcesoEntity getForUpdateFlujoProceso(SCGFlujoProcesoEntity entity) {
		final String method = "getForUpdateFlujoProceso";
		final String params = "SCGFlujoProcesoEntity entity";
		UNALMLogger.entry(log, method,params,new Object[]{entity});
		SCGFlujoProcesoEntity result = this.registroDAO.getForUpdateFlujoProcesoDAO( entity );
		UNALMLogger.exit(log, method, result);
		return result;
	}
	
	@Override
	public String validateDuplicate(SCGSolicitudProcesoEntity entity) {
		final String method = "validateDuplicate";
		final String params = "SCGSolicitudProcesoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] { entity });
		String result = "";
		if(entity != null){
			SCGSolicitudProcesoEntity numeroDocumento = new SCGSolicitudProcesoEntity();
			numeroDocumento.setTextoNumeroDocumento(entity.getTextoNumeroDocumento());
			UNALMLogger.trace(log, method, "entity.getTextoNumeroDocumento() "+entity.getTextoNumeroDocumento());
			numeroDocumento.setCodigo(entity.getCodigo());
			UNALMLogger.trace(log, method, "entity.getCodigo() "+entity.getCodigo());
			if(numeroDocumento.getCodigo() !=null){
				SCGSolicitudProcesoEntity item = this.registroDAO.getForUpdateSolicitudProcesoDAO(numeroDocumento);
				UNALMLogger.trace(log, method, "item "+item);
				UNALMLogger.trace(log, method, "item.getTextoNumeroDocumento() "+numeroDocumento.getTextoNumeroDocumento());
				UNALMLogger.trace(log, method, "item.getTextoNumeroDocumento() "+item.getTextoNumeroDocumento());
				if(!numeroDocumento.getTextoNumeroDocumento().equals(item.getTextoNumeroDocumento())){
					List<SCGSolicitudProcesoEntity> listItem = this.listSolicitudProceso(numeroDocumento, new FilterUtil());
					if(listItem.size()>0){
						result = "El Numero de Documento es duplicado";
					}					
				}
			}else{
				List<SCGSolicitudProcesoEntity> listItem = this.listSolicitudProceso(numeroDocumento, new FilterUtil());
				if(listItem.size()>0){
					result = "El Numero de Documento es duplicado";
				}
			}
		}
		UNALMLogger.exit(log, method, result);;
		return result;
		
	}

	@Override
	public String validateEdit(SCGSolicitudProcesoEntity solicitudProceso, Long codigoProceso) {
		final String method = "validateEdit";
		final String params = "SCGSolicitudProcesoEntity solicitudProceso, SCGFlujoProcesoEntity flujoProceso";
		UNALMLogger.entry(log, method, params, new Object[] { solicitudProceso});
		String result = "";
		UNALMLogger.trace(log, method, "codigoProceso "+codigoProceso);
		
		if(solicitudProceso != null){
			if(solicitudProceso.getCodigo() != null){
				
				SCGSolicitudProcesoEntity registroOriginal = this.registroDAO.getForUpdateSolicitudProcesoDAO(solicitudProceso);
				UNALMLogger.trace(log, method, "registroOriginal "+registroOriginal);
				
				if(codigoProceso != null){
					SCGFlujoProcesoEntity codigoSolicitudProceso = new SCGFlujoProcesoEntity();
					codigoSolicitudProceso.setSolicitudProceso(solicitudProceso);
					Long codigo = this.registroDAO.getMaxCodigoFlujoProcesoDAO(codigoSolicitudProceso);
					UNALMLogger.trace(log, method, "codigo "+codigo);

					
					SCGFlujoProcesoEntity codigoFlujoProceso = new SCGFlujoProcesoEntity();
					codigoFlujoProceso.setCodigo(codigo);
					SCGFlujoProcesoEntity originalFlujoProceso = this.registroDAO.getForUpdateFlujoProcesoDAO(codigoFlujoProceso);
					UNALMLogger.trace(log, method, "originalFlujoProceso "+originalFlujoProceso);
					
					SCGDefinicionProcesoEntity dataByFilterDefinicionProceso = new SCGDefinicionProcesoEntity();
					dataByFilterDefinicionProceso.setTextoNombre("PROCESO DEFINIDO");
					SCGDefinicionProcesoEntity definicionProceso = this.registroDAO.getForUpdateDefinicionProcesoDAO(dataByFilterDefinicionProceso);
					
					if(originalFlujoProceso.getDefinicionProceso()!= null){
						SCGProcesoEntity originalProceso = originalFlujoProceso.getDefinicionProceso().getProceso();
						UNALMLogger.trace(log, method, "originalProceso "+originalProceso);

						if(originalProceso != null){
							if(originalProceso.getCodigo() == definicionProceso.getProceso().getCodigo()){
								if(originalProceso.getCodigo() == codigoProceso){
									if(registroOriginal.getSeguridadUsuario().getCodigo() != solicitudProceso.getSeguridadUsuario().getCodigo()){
										result = "No puede cambiar el responsable asignado";
									}
								}else{
									UNALMLogger.trace(log, method, "codigo "+codigo);
									UNALMLogger.trace(log, method, "originalFlujoProceso.getDefinicionProceso().getCodigo()  " 
											+ originalFlujoProceso.getDefinicionProceso().getCodigo() );
									
									if(originalFlujoProceso.getDefinicionProceso().getCodigo() != definicionProceso.getCodigo()){
										result ="Tiene que aceptar el tramite pendiente de “Asignación de pedido”"
												+ " para proceder a asignar el proceso";
									}
								}
							}else if(originalProceso.getCodigo() != codigoProceso){
								result = "No puede cambiar el Proceso asignado";
							}else if(registroOriginal.getSeguridadUsuario().getCodigo() != solicitudProceso.getSeguridadUsuario().getCodigo()){
								result = "No puede cambiar el responsable asignado";
							}
						
						}
					}
					
					

				}
			}
		}
		UNALMLogger.exit(log, method, result);;
		return result;
	}

	@Override
	public void delete(SCGSolicitudProcesoEntity entity) {
		final String method = "delete";
		final String params = "SCGSolicitudProcesoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		SCGSolicitudProcesoEntity entityForDelete=null;
		SCGFlujoProcesoEntity flujoProcesoForDelete=null;
		UNALMLogger.trace(log, method, "entity.getCodigo(): "+entity.getCodigo());
		
		if(entity.getCodigo()!=null){
			entityForDelete = this.registroDAO.getForUpdateSolicitudProcesoDAO(entity);
			UNALMLogger.trace(log, method, "entityForDelete: "+entityForDelete);
			entityForDelete.setFlagEliminado("1");
			SCGFlujoProcesoEntity dataByFilterFlujoProceso = new SCGFlujoProcesoEntity();
			dataByFilterFlujoProceso.setSolicitudProceso(new SCGSolicitudProcesoEntity());
			dataByFilterFlujoProceso.getSolicitudProceso().setCodigo(entity.getCodigo());
			Long codigo = this.registroDAO.getMaxCodigoFlujoProcesoDAO(dataByFilterFlujoProceso);
			dataByFilterFlujoProceso = new SCGFlujoProcesoEntity();
			dataByFilterFlujoProceso.setCodigo(codigo);
			flujoProcesoForDelete = this.registroDAO.getForUpdateFlujoProcesoDAO(dataByFilterFlujoProceso);
			flujoProcesoForDelete.setFlagEliminado("1");
			
		}else{
			entityForDelete=entity;
		}
		
		SCGSolicitudProcesoEntity result = this.registroDAO.saveSolicitudProcesoDAO(entityForDelete);
		if(flujoProcesoForDelete.getCodigo() != null){
			this.registroDAO.saveFlujoProcesoDAO(flujoProcesoForDelete);
		}
		UNALMLogger.exit(log, method,result);
		
	}

	@Override
	public String validateDelete(SCGSolicitudProcesoEntity solicitudProceso) {
		final String method = "validateDelete";
		final String params = "SCGSolicitudProcesoEntity solicitudProceso";
		UNALMLogger.entry(log, method, params, new Object[] { solicitudProceso});
		String result = "";
		UNALMLogger.trace(log, method, "solicitudProceso.getCodigo() " + solicitudProceso.getCodigo());
		
		if(solicitudProceso.getCodigo() != null){
			SCGFlujoProcesoEntity dataByFilterFlujoProceso = new SCGFlujoProcesoEntity();
			dataByFilterFlujoProceso.setSolicitudProceso(new SCGSolicitudProcesoEntity());
			dataByFilterFlujoProceso.getSolicitudProceso().setCodigo(solicitudProceso.getCodigo());
			List<SCGFlujoProcesoEntity> listFlujoProceso = this.registroDAO.listFlujoProcesoDAO(dataByFilterFlujoProceso, new FilterUtil());
			
			if(listFlujoProceso.size() > 1){
				result = "No se puede eliminar el registro ya que tiene registros generados";
			}
		}
		UNALMLogger.exit(log, method, result);;
		return result;
	}

	@Override
	public void desestimado(SCGSolicitudProcesoEntity entity) {
		final String method = "desestimado";
		final String params = "SCGSolicitudProcesoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[] {entity});
		
		if(entity != null){
			
			SCGSolicitudProcesoEntity solicitudProceso = this.registroDAO.getForUpdateSolicitudProcesoDAO(entity);
			
			if(solicitudProceso.getFlagCandado().equals("0")){
				UNALMLogger.trace(log, method, "solicitudProceso.getCodigo() " + solicitudProceso.getCodigo());
				
				SCGFlujoProcesoEntity dataByFilterFlujoProceso = new SCGFlujoProcesoEntity();
				dataByFilterFlujoProceso.setSolicitudProceso(new SCGSolicitudProcesoEntity());
				dataByFilterFlujoProceso.getSolicitudProceso().setCodigo(solicitudProceso.getCodigo());
				List<SCGFlujoProcesoEntity> listFlujoProceso = this.registroDAO.listFlujoProcesoDAO(dataByFilterFlujoProceso, new FilterUtil());
				
				Long codigo = this.registroDAO.getMaxCodigoFlujoProcesoDAO(dataByFilterFlujoProceso);
				UNALMLogger.trace(log, method, "codigo " + codigo);
				
				dataByFilterFlujoProceso = new SCGFlujoProcesoEntity();
				dataByFilterFlujoProceso.setCodigo(codigo);
				SCGFlujoProcesoEntity flujoProceso = this.registroDAO.getForUpdateFlujoProcesoDAO(dataByFilterFlujoProceso);
				
				UNALMLogger.trace(log, method, "flujoProceso.getDefinicionProceso() " + flujoProceso.getDefinicionProceso());
				UNALMLogger.trace(log, method, "flujoProceso.getDefinicionProceso().getCodigo()" + flujoProceso.getDefinicionProceso().getCodigo());
				
				SCGDecisionProcesoEntity dataByFilterDecisionProceso = new SCGDecisionProcesoEntity();
				dataByFilterDecisionProceso.setDefinicionProcesoOrigen(flujoProceso.getDefinicionProceso());
				dataByFilterDecisionProceso.setTextoNombre("SOBRESTIMAR");
				SCGDecisionProcesoEntity decisionProceso = this.registroDAO.getForUpdateDecisionProcesoDAO(dataByFilterDecisionProceso);
				
				UNALMLogger.trace(log, method, "decisionProceso.getDefinicionProcesoDestino()" 
						+ decisionProceso.getDefinicionProcesoDestino());
				UNALMLogger.trace(log, method, "decisionProceso.getDefinicionProcesoDestino().getCodigo()" 
						+ decisionProceso.getDefinicionProcesoDestino().getCodigo());

				UNALMLogger.trace(log, method, "before responsabilidad ");				
				SCGResponsabilidadEntity dataByFilterResponsabilidad = new SCGResponsabilidadEntity();
				dataByFilterResponsabilidad.setResponsable(new SCGResponsableEntity());
				dataByFilterResponsabilidad.getResponsable().setTextoNombre("SOBRESTIMAR");
				dataByFilterResponsabilidad.getResponsable().setDefinicionProceso(new SCGDefinicionProcesoEntity());
				dataByFilterResponsabilidad.getResponsable().setDefinicionProceso(decisionProceso.getDefinicionProcesoDestino());
				SCGResponsabilidadEntity responsabilidad = this.registroDAO.getForUpdateResponsabilidadDAO(dataByFilterResponsabilidad);
				UNALMLogger.trace(log, method, "responsabilidad " + responsabilidad);
				
				SCGFlujoProcesoEntity finalFlujoProceso = new SCGFlujoProcesoEntity();
				finalFlujoProceso.setFechaInicio(new Date());
				finalFlujoProceso.setFechaInicio(new Date());
				finalFlujoProceso.setSolicitudProceso(solicitudProceso);
				finalFlujoProceso.setDefinicionProceso(decisionProceso.getDefinicionProcesoDestino());
				finalFlujoProceso.setResponsable(responsabilidad.getResponsable());
				finalFlujoProceso.setSeguridadUsuario(responsabilidad.getSeguridadUsuario());;
				finalFlujoProceso.setFlagCandado("1");
				finalFlujoProceso.setFlagEliminado("0");
				finalFlujoProceso.setFlagActivo("1");
				this.registroDAO.saveFlujoProcesoDAO(finalFlujoProceso);
				
				
				solicitudProceso.setFlagCandado("1");
				this.registroDAO.saveSolicitudProcesoDAO(solicitudProceso);
				for(SCGFlujoProcesoEntity item : listFlujoProceso ){
					item.setFlagCandado("1");
					this.registroDAO.saveFlujoProcesoDAO(item);
				}
			}
			
		}
		
	}

	@Override
	public boolean isProcesoPorDefinir(SCGSolicitudProcesoEntity solicitudProceso, Long codigoProceso) {
		final String method = "isProcesoPorDefinir";
		final String params = "SCGSolicitudProcesoEntity entity";
		UNALMLogger.entry(log, method, params, new Object[]{ solicitudProceso });
		boolean result = false;

		if(solicitudProceso != null){
			if(solicitudProceso.getCodigo() != null){
				SCGSolicitudProcesoEntity registroOriginal = this.registroDAO.getForUpdateSolicitudProcesoDAO(solicitudProceso);
				UNALMLogger.trace(log, method, "registroOriginal "+registroOriginal);
				
				if(codigoProceso != null){
					SCGFlujoProcesoEntity codigoSolicitudProceso = new SCGFlujoProcesoEntity();
					codigoSolicitudProceso.setSolicitudProceso(solicitudProceso);
					Long codigo = this.registroDAO.getMaxCodigoFlujoProcesoDAO(codigoSolicitudProceso);
					UNALMLogger.trace(log, method, "codigo "+codigo);

					
					SCGFlujoProcesoEntity codigoFlujoProceso = new SCGFlujoProcesoEntity();
					codigoFlujoProceso.setCodigo(codigo);
					SCGFlujoProcesoEntity originalFlujoProceso = this.registroDAO.getForUpdateFlujoProcesoDAO(codigoFlujoProceso);
					UNALMLogger.trace(log, method, "originalFlujoProceso "+originalFlujoProceso);

					
					if(originalFlujoProceso.getDefinicionProceso()!= null){
						SCGProcesoEntity originalProceso = originalFlujoProceso.getDefinicionProceso().getProceso();
						UNALMLogger.trace(log, method, "originalProceso "+originalProceso);

						if(originalProceso != null){
							SCGDefinicionProcesoEntity dataByFilterDefinicionProceso = new SCGDefinicionProcesoEntity();
							dataByFilterDefinicionProceso.setTextoNombre("PROCESO DEFINIDO");
							SCGDefinicionProcesoEntity definicionProceso = this.registroDAO.getForUpdateDefinicionProcesoDAO(dataByFilterDefinicionProceso);
							
							if(originalProceso.getCodigo() == definicionProceso.getProceso().getCodigo()){
								if(originalProceso.getCodigo() != codigoProceso){
									result = true;
								}
							}
						}
					}
					
					

				}
			}
		}
		return result;
		
	}

}
