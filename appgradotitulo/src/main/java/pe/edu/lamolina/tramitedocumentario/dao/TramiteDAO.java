package pe.edu.lamolina.tramitedocumentario.dao;

import java.util.List;

import pe.edu.lamolina.tramitedocumentario.entity.SCGAtributosEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGDecisionProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGFlujoProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGResponsabilidadEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGValoresEntity;
import pe.edu.lamolina.util.FilterUtil;

public interface TramiteDAO {
	public List<SCGFlujoProcesoEntity> listFlujoProcesoDAO(SCGFlujoProcesoEntity entity, FilterUtil filterUtil);
	public List<SCGDecisionProcesoEntity> listDecisionProcesoDAO(SCGDecisionProcesoEntity entity);
	public List<SCGResponsabilidadEntity> listResponsabilidadDAO(SCGResponsabilidadEntity entity);
	public List<SCGAtributosEntity> listAtributosDAO(SCGAtributosEntity entity);
	public SCGFlujoProcesoEntity saveFlujoProcesoDAO(SCGFlujoProcesoEntity entity);
	public SCGValoresEntity saveValoresDAO(SCGValoresEntity entity);
	public SCGFlujoProcesoEntity getForUpdateFlujoProcesoDAO( SCGFlujoProcesoEntity entity );
	public SCGDecisionProcesoEntity getForUpdateDecisionProcesoDAO( SCGDecisionProcesoEntity entity );
	public SCGResponsabilidadEntity getForUpdateResponsabilidadDAO( SCGResponsabilidadEntity entity );
	public SCGAtributosEntity getForUpdateAtributoDAO( SCGAtributosEntity entity );
	public SCGValoresEntity getForUpdateValoresDAO( SCGValoresEntity entity );

}
