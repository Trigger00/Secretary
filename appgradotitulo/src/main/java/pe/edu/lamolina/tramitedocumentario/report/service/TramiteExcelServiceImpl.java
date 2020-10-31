package pe.edu.lamolina.tramitedocumentario.report.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.tramitedocumentario.entity.SCGDefinicionProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGFlujoProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGResponsableEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGSolicitudProcesoEntity;
import pe.edu.lamolina.util.TypesUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class TramiteExcelServiceImpl implements TramiteExcelService{
	private final static Logger log = Logger.getLogger(TramiteExcelServiceImpl.class);

	@Override
	public List<List<String>> reportValue(List<SCGFlujoProcesoEntity> listFlujoProceso) {
		final String method = "reportValue";
		final String params = "List<SCGFlujoProcesoEntity> SCGFlujoProcesoEntity";
		UNALMLogger.entry(log, method, params, new Object[] { listFlujoProceso });
		
		int count = 0;
		
		List<String> listCabezera = new ArrayList<String>();
		List<List<String>> result = new ArrayList<List<String>>();
		
		for( SCGFlujoProcesoEntity item : listFlujoProceso){
			List<String> listValores = new ArrayList<String>();
			
			if(item.getSolicitudProceso() == null){
				item.setSolicitudProceso(new SCGSolicitudProcesoEntity());
			}
			
			if(item.getDefinicionProceso() == null){
				item.setDefinicionProceso(new SCGDefinicionProcesoEntity());
			}
			
			if(item.getResponsable() == null){
				item.setResponsable(new SCGResponsableEntity());
			}
			
			if(count == 0){
				listCabezera.add("Solicitud Proceso");
			}
			listValores.add(item.getSolicitudProceso().getTextoNumeroDocumento());
			
			if(count == 0){
				listCabezera.add("Definicion Proceso");
			}
			listValores.add(item.getDefinicionProceso().getTextoNombre());
			
			if(count == 0){
				listCabezera.add("Responsable");
			}
			listValores.add(item.getResponsable().getTextoNombre());
			
			if(count == 0){
				listCabezera.add("Adjunto");
			}
			listValores.add(item.getTextoRegistrosAdjuntos());
			
			if(count == 0){
				listCabezera.add("Fecha Inicio");
			}
			listValores.add(TypesUtil.getDefaultString(item.getFechaInicio(), "dd/MM/yyyy"));
			
			if(count == 0){
				listCabezera.add("Fecha Fin");
			}
			listValores.add(TypesUtil.getDefaultString(item.getFechaFinal(), "dd/MM/yyyy"));
						
			result.add(listValores);
			count++;
		}

		result.add(0, listCabezera);
		UNALMLogger.exit(log, method, result);
		return result;
	}

}
