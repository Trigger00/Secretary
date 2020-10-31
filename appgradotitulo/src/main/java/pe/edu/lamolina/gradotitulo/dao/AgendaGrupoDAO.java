package pe.edu.lamolina.gradotitulo.dao;

import java.util.List;

import pe.edu.lamolina.gradotitulo.entity.SCGAgendaGrupoEntity;

public interface AgendaGrupoDAO {
	public List<SCGAgendaGrupoEntity> getListAgendaGrupoDAO(SCGAgendaGrupoEntity entity);
	public SCGAgendaGrupoEntity getForUpdateAgendaGrupoDAO(SCGAgendaGrupoEntity entity);
	public SCGAgendaGrupoEntity saveAgendaGrupoDAO(SCGAgendaGrupoEntity entity);
	public void deleteDAO();
}
