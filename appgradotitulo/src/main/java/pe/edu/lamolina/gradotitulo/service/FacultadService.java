package pe.edu.lamolina.gradotitulo.service;

import java.util.List;

import pe.edu.lamolina.gradotitulo.entity.SCGEspecialidadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGFacultadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoEspecialidadEntity;

public interface FacultadService {
	public List<SCGFacultadEntity> getListFacultad(SCGFacultadEntity entity);
	public List<SCGEspecialidadEntity> getListEspecialidad(SCGEspecialidadEntity entity);
	public List<SCGTipoEspecialidadEntity> getListTipoEspecialidad(SCGTipoEspecialidadEntity entity);
	public SCGFacultadEntity getForUpdate(SCGFacultadEntity entity);
	public SCGEspecialidadEntity getForUpdateEspecialidad(SCGEspecialidadEntity entity);
	public SCGFacultadEntity  save(SCGFacultadEntity entity);
	public SCGEspecialidadEntity  saveEspecialidad(SCGEspecialidadEntity entity);
	public void delete(SCGFacultadEntity entity);
	public void deleteEspecialidad(SCGEspecialidadEntity entity);

}
