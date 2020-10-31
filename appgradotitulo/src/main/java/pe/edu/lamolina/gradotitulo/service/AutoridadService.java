package pe.edu.lamolina.gradotitulo.service;

import java.util.List;

import pe.edu.lamolina.gradotitulo.entity.SCGAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGAutoridadRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGCargoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGGradoAcademicoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPeriodoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPersonaEntity;

public interface AutoridadService {
	public List<SCGAutoridadRegistroEntity> getListAutoridad(SCGAutoridadRegistroEntity entity, SCGPeriodoEntity periodo);
	public List<SCGPeriodoEntity> getListPeriodo(SCGPeriodoEntity entity);
	public List<SCGGradoAcademicoEntity> getListGradoAcademico(SCGGradoAcademicoEntity entity);
	public List<SCGCargoEntity> getListCargo(SCGCargoEntity entity);
	public SCGAutoridadRegistroEntity getForUpdateAutoridad(SCGAutoridadRegistroEntity entity);
	public SCGPeriodoEntity getForUpdatePeriodo(SCGPeriodoEntity entity);
	public SCGAutoridadRegistroEntity saveAutoridad(SCGAutoridadRegistroEntity entity);
	public SCGPeriodoEntity savePeriodo(SCGPeriodoEntity entity);
	public SCGAdjuntoEntity saveAdjunto(SCGAdjuntoEntity entity);
	public String validateDelete(SCGAutoridadRegistroEntity entity);
	public void delete(SCGAutoridadRegistroEntity entity);
	public void deleteAdjunto(SCGPersonaEntity entity);
	public void deletePeriodo(SCGPeriodoEntity entity);
	public void deleteFiles(SCGAdjuntoEntity entity);
	public void syncFiles(SCGAdjuntoEntity entity);

}
