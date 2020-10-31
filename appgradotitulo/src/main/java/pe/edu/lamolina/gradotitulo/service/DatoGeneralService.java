package pe.edu.lamolina.gradotitulo.service;

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

public interface DatoGeneralService {
	public List<SCGEstudianteEntity> getEstudianteList(SCGEstudianteEntity entity,FilterUtil filterUtil);
	public List<SCGEstudianteEntity> getEstudianteListExcel(SCGEstudianteEntity entity);
	public List<SCGEstudianteEntity> getEstudiantePregradoList(SCGEstudianteEntity entity,FilterUtil filterUtil);
	public List<SCGEstudianteEntity> getEstudianteRevalidaList(SCGEstudianteEntity entity,FilterUtil filterUtil);
	public List<SCGPersonaDocumentoEntity> getDocumentoList(SCGPersonaDocumentoEntity entity);
	public List<SCGTipoDocumentoEntity> getTipoDocumentoList(SCGTipoDocumentoEntity entity);
	public List<SCGCicloEntity> getCicloList(SCGCicloEntity entity);	
	public List<SCGPersonaTelefonoEntity> getPersonaTelefonoList(SCGPersonaTelefonoEntity entity);
	public SCGEstudianteEntity getForUpdateEstudiante(SCGEstudianteEntity entity);
	public SCGPersonaTelefonoEntity getForUpdateTelefono(SCGPersonaTelefonoEntity entity);
	public SCGTipoTelefonoEntity getForUpdateTipoTelefono(SCGTipoTelefonoEntity entity);
	public SCGAdjuntoEntity getForUpdateAdjunto(SCGAdjuntoEntity entity);
	public SCGPersonaDocumentoEntity getForUpdateDocumento(SCGPersonaDocumentoEntity entity);
	public SCGTipoDocumentoEntity getForUpdateTipoDocumento(SCGTipoDocumentoEntity entity);
	public SCGCicloEntity getForUpdateCiclo(SCGCicloEntity entity);
	public SCGEstudianteEntity saveEstudiante(SCGEstudianteEntity entity);
	public SCGPersonaEntity savePersona(SCGPersonaEntity entity);
	public SCGPersonaTelefonoEntity savePersonaTelefono(SCGPersonaTelefonoEntity entity);
	public SCGPersonaDocumentoEntity savePersonaDocumento(SCGPersonaDocumentoEntity entity);
	public SCGAdjuntoEntity saveAdjunto(SCGAdjuntoEntity entity);
	public SCGTipoDocumentoEntity saveTipoDocumentoIdentidad(SCGTipoDocumentoEntity entity);
	public String validatePeriodoEstudio( SCGEstudianteEntity entity );
	public void deleteAdjunto(SCGPersonaEntity entity);
	public void deleteEstudiante(SCGEstudianteEntity entity);
	public void deletePersona(SCGPersonaEntity entity);
	public void deleteDocumento(SCGPersonaDocumentoEntity entity);
	public void deleteTelefono(SCGPersonaTelefonoEntity entity);
	public void deleteTipoDocumento(SCGTipoDocumentoEntity entity);
	public void deleteFiles(SCGAdjuntoEntity entity);
	public void syncFiles(SCGAdjuntoEntity entity);

}
