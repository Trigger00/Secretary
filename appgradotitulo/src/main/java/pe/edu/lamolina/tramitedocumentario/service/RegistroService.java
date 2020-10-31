package pe.edu.lamolina.tramitedocumentario.service;

import java.util.List;

import pe.edu.lamolina.tramitedocumentario.entity.SCGDefinicionProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGFlujoProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGResponsabilidadEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGSolicitudProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGTipoTramiteDocumentoEntity;
import pe.edu.lamolina.util.FilterUtil;

public interface RegistroService {
	public List<SCGTipoTramiteDocumentoEntity> listTipoTramiteDocumento();
	public List<SCGResponsabilidadEntity> listResponsabilidad(SCGResponsabilidadEntity entity);
	public List<SCGDefinicionProcesoEntity> listDefinicionProceso(SCGDefinicionProcesoEntity entity);
	public List<SCGSolicitudProcesoEntity> listSolicitudProceso(SCGSolicitudProcesoEntity entity, FilterUtil filterUtil);
	public List<SCGProcesoEntity> listProceso();
	public List<SCGFlujoProcesoEntity> listFlujoProceso(SCGFlujoProcesoEntity entity, FilterUtil filterUtil);
	public List<SCGSolicitudProcesoEntity> listDistinctProcedencia(SCGSolicitudProcesoEntity entity,FilterUtil filterUtil);
	public SCGSolicitudProcesoEntity saveSolicitudProceso(SCGSolicitudProcesoEntity entity);
	public SCGFlujoProcesoEntity saveFlujoProceso(SCGFlujoProcesoEntity entity, SCGSolicitudProcesoEntity solicitudProceso);
	public SCGFlujoProcesoEntity getForUpdateSolicitudProceso(SCGSolicitudProcesoEntity entity);
	public SCGFlujoProcesoEntity getForUpdateFlujoProceso( SCGFlujoProcesoEntity entity );
	public void delete(SCGSolicitudProcesoEntity entity);
	public void desestimado(SCGSolicitudProcesoEntity entity);
	public String validateDuplicate(SCGSolicitudProcesoEntity entity);
	public String validateEdit(SCGSolicitudProcesoEntity solicitudProceso, Long Proceso);
	public String validateDelete(SCGSolicitudProcesoEntity solicitudProceso);
	public boolean isProcesoPorDefinir(SCGSolicitudProcesoEntity solicitudProceso, Long codigoProceso);
	

}
