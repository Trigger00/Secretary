package pe.edu.lamolina.tramitedocumentario.report.service;

import java.util.List;

import pe.edu.lamolina.tramitedocumentario.entity.SCGSolicitudProcesoEntity;

public interface RegistroExcelService {
	public List<List<String>> reportValue(List<SCGSolicitudProcesoEntity> listSolicitudProceso);

}
