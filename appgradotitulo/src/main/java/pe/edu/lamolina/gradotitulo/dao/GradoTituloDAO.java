package pe.edu.lamolina.gradotitulo.dao;

import java.util.List;

import pe.edu.lamolina.gradotitulo.entity.SCGAdjuntoEntity;
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

public interface GradoTituloDAO {
	public List<SCGEstudianteRegistroEntity> getListEsudianteRegistroDAO(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas);
	public List<SCGEstudianteRegistroEntity> getListEsudianteRegistroAscendeteDAO(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas);
	public List<SCGEstudianteRegistroEntity> getListEsudianteRegistroDescendenteDAO(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas);
	public List<SCGEstudianteRegistroEntity> listDependentRecordsDAO( SCGEstudianteRegistroEntity entity );
	public List<SCGPaisEntity> getListPaisDAO(SCGPaisEntity entity);
	public List<SCGUniversidadEntity> getLisUniversidadDAO(SCGUniversidadEntity entity);
	public List<SCGModalidadGradoTituloGrupoEntity> getListModalidadGradoTituloGrupoDAO(SCGModalidadGradoTituloGrupoEntity entity);
	public List<SCGModalidadEstudioEntity> getListModalidadEstudioDAO(SCGModalidadEstudioEntity entity);
	public List<SCGGradoAcademicoEntity> getListGradoAcademicoDAO(SCGGradoAcademicoEntity entity);
	public List<SCGProgramaEstudioEntity> getListProgramaEstudioDAO(SCGProgramaEstudioEntity entity);
	public SCGEstudianteRegistroEntity getForUpdateEsudianteRegistroDAO(SCGEstudianteRegistroEntity entity);
	public SCGEstudianteRegistroAdjuntoEntity getForUpdateEstudianteRegistroAdjuntoDAO(SCGEstudianteRegistroAdjuntoEntity entity);
	public SCGGradoAcademicoEntity getForUpdateGradoAcademicoDAO(SCGGradoAcademicoEntity entity);
	public SCGUniversidadEntity getForUpdateUniversidadDAO(SCGUniversidadEntity entity);
	public SCGAdjuntoEntity getForUpdateAdjuntoDAO(SCGAdjuntoEntity entity);
	public SCGModalidadEstudioEntity getForUpdateModalidadEstudioDAO(SCGModalidadEstudioEntity entity);
	public SCGModalidadGradoTituloEntity getForUpdateModalidadGradoTituloDAO(SCGModalidadGradoTituloEntity entity);
	public Long getNumberRegistroMaxDAO(SCGEstudianteRegistroEntity entity);
	public SCGProgramaEstudioEntity getForUpdateProgramaEstudioDAO(SCGProgramaEstudioEntity entity);
	public SCGEstudianteRegistroEntity saveEsudianteRegistroDAO(SCGEstudianteRegistroEntity entity);
	public SCGAdjuntoEntity saveAdjuntoDAO(SCGAdjuntoEntity entity);
	public SCGEstudianteRegistroAdjuntoEntity saveEstudianteRegistroAdjuntoDAO(SCGEstudianteRegistroAdjuntoEntity entity);
	public void deleteDAO();
}
