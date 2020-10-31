package pe.edu.lamolina.tramitedocumentario.report.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.tramitedocumentario.dao.RegistroDAO;
import pe.edu.lamolina.tramitedocumentario.entity.SCGFlujoProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGSolicitudProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.service.RegistroService;
import pe.edu.lamolina.util.TypesUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class RegistroExcelServiceImpl  implements RegistroExcelService{
	private final static Logger log = Logger.getLogger(RegistroExcelServiceImpl.class);

	@Autowired
	RegistroDAO registroDAO;
	
	@Autowired
	private RegistroService registroService;
	
	@Override
	public List<List<String>> reportValue(List<SCGSolicitudProcesoEntity> listSolicitudProceso) {
		final String method = "reportValue";
		final String params = "List<SCGSolicitudProcesoEntity> listSolicitudProceso";
		UNALMLogger.entry(log, method, params, new Object[] { listSolicitudProceso });

		int count = 0;
		
		List<String> listCabezera = new ArrayList<String>();
		List<List<String>> result = new ArrayList<List<String>>();

		for( SCGSolicitudProcesoEntity item : listSolicitudProceso){
			List<String> listValores = new ArrayList<String>();
			
			if(count == 0){
				listCabezera.add("Asunto");
			}
			listValores.add(item.getTextoAsunto());
			
			if(count == 0){
				listCabezera.add("Nombre Procedencia");
			}
			listValores.add(item.getTextoNombreProcedencia());
			
			if(count == 0){
				listCabezera.add("Nombre Remitente");
			}
			listValores.add(item.getTextoNombreRemitente());
			
			if(count == 0){
				listCabezera.add("Numero Documento");
			}
			listValores.add(item.getTextoNumeroDocumento());
			
			if(count == 0){
				listCabezera.add("Observacion");
			}
			listValores.add(item.getTextoObservacion());
			
			if(count == 0){
				listCabezera.add("Fecha Inicio");
			}
			listValores.add(TypesUtil.getDefaultString(item.getFechaInicio(), "dd/MM/yyyy"));
			
			if(count == 0){
				listCabezera.add("Fecha Final");
			}
			listValores.add(TypesUtil.getDefaultString(item.getFechaFinal(), "dd/MM/yyyy"));
		
			if(count == 0){
				listCabezera.add("Proceso");
			}
			SCGFlujoProcesoEntity flujoProceso = this.registroService.getForUpdateSolicitudProceso(item);
			if(flujoProceso.getDefinicionProceso() != null){
				
				if(flujoProceso.getDefinicionProceso().getProceso() != null){
					SCGProcesoEntity proceso = flujoProceso.getDefinicionProceso().getProceso();
					listValores.add(proceso.getTextoNombre());								
				}				
			}
			
			if(count == 0){
				listCabezera.add("Tramite Documento");
			}
			if(item.getTipoTramiteDocumento() != null){
				UNALMLogger.trace(log, method, "tipoTramiteDocumento " + item.getTipoTramiteDocumento().getTextoNombre());
				listValores.add(item.getTipoTramiteDocumento().getTextoNombre());	
			}
			
			if(count == 0){
				listCabezera.add("Usuario");
			}
			if(item.getSeguridadUsuario() != null){
				UNALMLogger.trace(log, method, "seguridadUsuario " + item.getSeguridadUsuario().getTextoNombre());
				listValores.add(item.getSeguridadUsuario().getTextoNombre());	
			}
						
			result.add(listValores);

			count++;
		}
		
		result.add(0, listCabezera);
		UNALMLogger.exit(log, method, result);
		return result;
	}

}
