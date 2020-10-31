package pe.edu.lamolina.gradotitulo.service;

import java.util.List;

import pe.edu.lamolina.gradotitulo.entity.SCGAgendaGrupoEntity;

public interface AgendaGrupoService {
	public List<SCGAgendaGrupoEntity> getListAgendaGrupo(SCGAgendaGrupoEntity entity);
	public SCGAgendaGrupoEntity getForUpdateAgendaGrupo(SCGAgendaGrupoEntity entity);
	public SCGAgendaGrupoEntity saveAgendaGrupo(SCGAgendaGrupoEntity entity);
	public void delete(SCGAgendaGrupoEntity entity);

}
