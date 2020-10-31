package pe.edu.lamolina.gradotitulo.dao;

import java.util.List;

import pe.edu.lamolina.gradotitulo.entity.SCGEspecialidadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGFacultadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoEspecialidadEntity;

public interface FacultadDAO {
	public List<SCGFacultadEntity> getListFacultadDAO(SCGFacultadEntity entity);
	public List<SCGEspecialidadEntity> getListEspecialidadDAO(SCGEspecialidadEntity entity);
	public List<SCGTipoEspecialidadEntity> getListTipoEspecialidadDAO(SCGTipoEspecialidadEntity entity);
	public SCGFacultadEntity getForUpdateDAO(SCGFacultadEntity entity);
	public SCGEspecialidadEntity getForUpdateEspecialidadDAO(SCGEspecialidadEntity entity);
	public SCGFacultadEntity  saveDAO(SCGFacultadEntity entity);
	public SCGEspecialidadEntity  saveEspecialidadDAO(SCGEspecialidadEntity entity);
	public void deleteDAO(SCGFacultadEntity entity);
}
