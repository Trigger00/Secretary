package pe.edu.lamolina.tramitedocumentario.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import pe.edu.lamolina.tramitedocumentario.entity.SCGAtributosEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGDecisionProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGFlujoProcesoEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGResponsabilidadEntity;
import pe.edu.lamolina.tramitedocumentario.entity.SCGValoresEntity;
import pe.edu.lamolina.util.FilterUtil;

public interface TramiteService {
	public List<SCGFlujoProcesoEntity> listFlujoProceso(SCGFlujoProcesoEntity entity, FilterUtil filterUtil);
	public List<SCGDecisionProcesoEntity> listDecisionProceso(SCGDecisionProcesoEntity entity);
	public List<SCGResponsabilidadEntity> listResponsabilidad(SCGResponsabilidadEntity entity);
	public List<SCGAtributosEntity> listAtributos(SCGAtributosEntity entity);
	public void saveFlujoProceso(SCGFlujoProcesoEntity entity,HttpServletRequest request);
//	public void saveValores(SCGFlujoProcesoEntity entity,HttpServletRequest request);
	public SCGFlujoProcesoEntity getForUpdateFlujoProceso( SCGFlujoProcesoEntity entity );
	public SCGDecisionProcesoEntity getForUpdateDecisionProceso( SCGDecisionProcesoEntity entity );
	public SCGResponsabilidadEntity getForUpdateResponsabilidad( SCGResponsabilidadEntity entity );
	public SCGFlujoProcesoEntity getFlujoProcesoSiguiente(SCGFlujoProcesoEntity entity);
	public SCGAtributosEntity getForUpdateAtributo( SCGAtributosEntity entity );
	public SCGValoresEntity getForUpdateValores( SCGValoresEntity entity );
	public void delete(SCGFlujoProcesoEntity entity);
	public void desestimado(SCGFlujoProcesoEntity entity);
	public String validateDuplicate(SCGFlujoProcesoEntity entity);
	public String validateEdit(SCGFlujoProcesoEntity solicitudProceso, Long Proceso);
	public String validateDelete(SCGFlujoProcesoEntity solicitudProceso);
	public boolean isProcesoPorDefinir(SCGFlujoProcesoEntity solicitudProceso, Long codigoProceso);
	
}
