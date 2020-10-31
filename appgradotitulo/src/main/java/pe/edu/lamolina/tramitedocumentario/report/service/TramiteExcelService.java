package pe.edu.lamolina.tramitedocumentario.report.service;

import java.util.List;

import pe.edu.lamolina.tramitedocumentario.entity.SCGFlujoProcesoEntity;

public interface TramiteExcelService {
	public List<List<String>> reportValue(List<SCGFlujoProcesoEntity> listFlujoProceso);

}
