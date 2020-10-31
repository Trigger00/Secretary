package pe.edu.lamolina.gradotitulo.dao;

import java.util.List;

import pe.edu.lamolina.gradotitulo.entity.SCGAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGCicloEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaDocumentoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaTelefonoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoDocumentoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoTelefonoEntity;
import pe.edu.lamolina.util.FilterUtil;

public interface DatoGeneralDAO {
	public List<SCGEstudianteEntity> getEstudianteListDAO(SCGEstudianteEntity entity,FilterUtil filterUtil);
	public List<SCGEstudianteEntity> getEstudianteListExcelDAO(SCGEstudianteEntity entity);
	public List<SCGEstudianteEntity> getEstudiantePregradoListDAO(SCGEstudianteEntity entity,FilterUtil filterUtil);
	public List<SCGEstudianteEntity> getEstudianteRevalidaListDAO(SCGEstudianteEntity entity,FilterUtil filterUtil);
	public List<SCGPersonaDocumentoEntity> getDocumentoListDAO(SCGPersonaDocumentoEntity entity);
	public List<SCGTipoDocumentoEntity> getTipoDocumentoListDAO(SCGTipoDocumentoEntity entity);
	public List<SCGPersonaTelefonoEntity> getTelefonoListDAO(SCGPersonaTelefonoEntity entity);
	public List<SCGCicloEntity> getCicloListDAO(SCGCicloEntity entity);
	public List<SCGPersonaTelefonoEntity> getPersonaTelefonoListDAO(SCGPersonaTelefonoEntity entity);
	public SCGAdjuntoEntity getForUpdateAdjuntoDAO(SCGAdjuntoEntity entity);
	public SCGPersonaTelefonoEntity getForUpdateTelefonoDAO(SCGPersonaTelefonoEntity entity);
	public SCGTipoTelefonoEntity getForUpdateTipoTelefonoDAO(SCGTipoTelefonoEntity entity);
	public SCGPersonaDocumentoEntity getForUpdateDocumentoDAO(SCGPersonaDocumentoEntity entity);
	public SCGTipoDocumentoEntity getForUpdateTipoDocumentoDAO(SCGTipoDocumentoEntity entity);
	public SCGEstudianteEntity getForUpdateEstudianteDAO(SCGEstudianteEntity entity);
	public SCGPersonaEntity getForUpdatePersonaDAO(SCGPersonaEntity entity);
	public SCGCicloEntity getForUpdateCicloDAO(SCGCicloEntity entity);
	public SCGEstudianteEntity saveEstudianteDAO(SCGEstudianteEntity entity);
	public SCGPersonaEntity savePersonaDAO(SCGPersonaEntity entity);
	public SCGPersonaTelefonoEntity savePersonaTelefonoDAO(SCGPersonaTelefonoEntity entity);
	public SCGPersonaDocumentoEntity savePersonaDocumentoDAO(SCGPersonaDocumentoEntity entity);
	public SCGAdjuntoEntity saveAdjuntoDAO(SCGAdjuntoEntity entity);
	public SCGTipoDocumentoEntity saveTipoDocumentoIdentidad(SCGTipoDocumentoEntity entity);
	public void deleteDAO();
}
