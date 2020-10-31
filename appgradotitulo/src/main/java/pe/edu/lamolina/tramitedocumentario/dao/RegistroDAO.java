package pe.edu.lamolina.tramitedocumentario.dao;

import java.util.List;

import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGDecisionProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGDefinicionProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGFlujoProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGResponsabilidadEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGSolicitudProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGTipoTramiteDocumentoEntity;
import pe.edu.lamolina.util.FilterUtil;

public interface RegistroDAO {
	
	public List<SCGTipoTramiteDocumentoEntity> listTipoTramiteDocumentoDAO();
	public List<SCGResponsabilidadEntity> listResponsabilidadDAO(SCGResponsabilidadEntity entity);
	public List<SCGDefinicionProcesoEntity> listDefinicionProcesoDAO(SCGDefinicionProcesoEntity entity);
	public List<SCGSolicitudProcesoEntity> listSolicitudProcesoDAO(SCGSolicitudProcesoEntity entity,FilterUtil filterUtil);
	public List<SCGSolicitudProcesoEntity> listDistinctProcedenciaDAO(SCGSolicitudProcesoEntity entity,FilterUtil filterUtil);
	public List<SCGFlujoProcesoEntity> listFlujoProcesoDAO(SCGFlujoProcesoEntity entity, FilterUtil filterUtil);
	public List<SCGProcesoEntity> listProcesoDAO();
	public List<SCGDecisionProcesoEntity> listDecisionProcesoDAO(SCGDecisionProcesoEntity entity);
	public SCGSolicitudProcesoEntity saveSolicitudProcesoDAO(SCGSolicitudProcesoEntity entity);
	public SCGFlujoProcesoEntity saveFlujoProcesoDAO(SCGFlujoProcesoEntity entity);
	public SCGSolicitudProcesoEntity getForUpdateSolicitudProcesoDAO( SCGSolicitudProcesoEntity entity );
	public SCGFlujoProcesoEntity getForUpdateFlujoProcesoDAO( SCGFlujoProcesoEntity entity );
	public SCGDefinicionProcesoEntity getForUpdateDefinicionProcesoDAO( SCGDefinicionProcesoEntity entity );
	public SCGResponsabilidadEntity getForUpdateResponsabilidadDAO( SCGResponsabilidadEntity entity );
	public SCGDecisionProcesoEntity getForUpdateDecisionProcesoDAO( SCGDecisionProcesoEntity entity );
	public Long getMaxCodigoAsociacionDAO(SCGSolicitudProcesoEntity entity);
	public Long getMaxCodigoFlujoProcesoDAO(SCGFlujoProcesoEntity entity);



}
