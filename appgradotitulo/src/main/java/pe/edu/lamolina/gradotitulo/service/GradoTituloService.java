package pe.edu.lamolina.gradotitulo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import pe.edu.lamolina.gradotitulo.entity.SCGAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGGradoAcademicoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGModalidadEstudioEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGModalidadGradoTituloEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGModalidadGradoTituloGrupoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPaisEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGProgramaEstudioEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGUniversidadEntity;
import pe.edu.lamolina.util.FilterUtil;

public interface GradoTituloService {
	public List<SCGEstudianteRegistroEntity> getListEsudianteRegistro(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas);
	public List<SCGEstudianteRegistroEntity> getListEsudianteRegistroAscendente(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas);
	public List<SCGEstudianteRegistroEntity> getListEsudianteRegistroDescendente(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas);
	public List<SCGEstudianteRegistroEntity> listDependentRecords(SCGEstudianteRegistroEntity entity);
	public List<SCGPaisEntity> getListPais(SCGPaisEntity entity);
	public List<SCGUniversidadEntity> getLisUniversidad(SCGUniversidadEntity entity);
	public List<SCGModalidadGradoTituloGrupoEntity> getListModalidadGradoTituloGrupo(SCGModalidadGradoTituloGrupoEntity entity);
	public List<SCGModalidadEstudioEntity> getListModalidadEstudio(SCGModalidadEstudioEntity entity);
	public List<SCGGradoAcademicoEntity> getListGradoAcademico(SCGGradoAcademicoEntity entity);
	public SCGEstudianteRegistroEntity getForUpdateEstudianteRegistro(SCGEstudianteRegistroEntity entity);
	public SCGEstudianteRegistroAdjuntoEntity getForUpdateEstudianteRegistroAdjunto(SCGEstudianteRegistroAdjuntoEntity entity);
	public SCGAdjuntoEntity getForUpdateAdjunto(SCGAdjuntoEntity entity);
	public SCGGradoAcademicoEntity getForUpdateGradoAcademico(SCGGradoAcademicoEntity entity);
	public SCGUniversidadEntity getForUpdateUniversidad(SCGUniversidadEntity entity);
	public SCGModalidadEstudioEntity getForUpdateModalidadEstudio(SCGModalidadEstudioEntity entity);
	public SCGModalidadGradoTituloEntity getForUpdateModalidadGradoTitulo(SCGModalidadGradoTituloEntity entity);
	public SCGProgramaEstudioEntity getForUpdateProgramaEstudio(SCGProgramaEstudioEntity entity);
	public List<SCGProgramaEstudioEntity> getListProgramaEstudio(SCGProgramaEstudioEntity entity);
	public Long getNumberRegistroMax(SCGEstudianteRegistroEntity entity);
	public SCGEstudianteRegistroEntity saveEsudianteRegistro(SCGEstudianteRegistroEntity entity);
	public void updateNumeroRegistro(SCGEstudianteRegistroEntity entity);
	public SCGEstudianteRegistroAdjuntoEntity saveEstudianteRegistroAdjunto(SCGEstudianteRegistroAdjuntoEntity entity);
	public SCGAdjuntoEntity saveAdjunto(SCGAdjuntoEntity entity);
	public String validatePeriodoEstudio( SCGEstudianteRegistroEntity entity );
	public void delete(SCGEstudianteRegistroEntity entity);
	public void deleteFiles(SCGAdjuntoEntity entity);
	public void syncFiles(SCGAdjuntoEntity entity);
	public void enviarSunedu(SCGEstudianteRegistroEntity entity);
	
	public String setFormatResolucionRecotoral(SCGEstudianteRegistroEntity entity);
	public SCGEstudianteRegistroAdjuntoEntity setValuesEstudianteRegistroAdjunto(SCGEstudianteRegistroEntity estudianteRegistro,SCGAdjuntoEntity adjunto);
	public SCGAdjuntoEntity setValuesAdjunto(MultipartFile archivo,long codigoTipoAdjunto) throws IOException;
	public String setURLEscaneado(SCGEstudianteRegistroEntity estudianteRegistro,SCGAdjuntoEntity adjunto);
	public String setURLFoto(SCGEstudianteRegistroEntity estudianteRegistro,SCGAdjuntoEntity adjunto);
	public Long getCodigoAdjuntoByEstudianteRegistroAdjunto(SCGEstudianteRegistroAdjuntoEntity entity);
	public SCGEstudianteRegistroAdjuntoEntity getEstudianteRegistroAdjunto(Long codigo);
	public Long getFormatCodigoAdjunto(String codigo);
	public boolean isDuplicateEstudinateRegistro(SCGEstudianteRegistroEntity entity);
	public void agendasClose(SCGEstudianteRegistroEntity entity,String groupList);
	public  List<Long> addCodigoAgenda(String[] splitSelectAgenda);
	public  String[] getSplitSelectAgenda(String selectAgenda);
	public void saveAgendasThatWillNotBeClosed(List<SCGEstudianteRegistroEntity> listOfAgendasThatWillNotBeClosed );
	public List<SCGEstudianteRegistroEntity> getAgendasThatWillNotBeClosedList(List<SCGEstudianteRegistroEntity> listOfOpenAgendas ,List<SCGEstudianteRegistroEntity> listOfAgendasThatHasToBeClose);
	public SCGAdjuntoEntity setPathAdjunto(SCGEstudianteRegistroEntity entity, long codgiTipoAdjunto);
}
