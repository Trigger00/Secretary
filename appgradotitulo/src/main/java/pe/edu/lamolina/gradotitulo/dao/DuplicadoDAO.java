package pe.edu.lamolina.gradotitulo.dao;

import java.util.List;

import pe.edu.lamolina.gradotitulo.entity.SCGAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroEntity;
import pe.edu.lamolina.util.FilterUtil;

public interface DuplicadoDAO {
	public List<SCGEstudianteRegistroEntity> getListDuplicadoDAO(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas);
	public List<SCGEstudianteRegistroEntity> getListDuplicadoAscendenteDAO(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas);
	public List<SCGEstudianteRegistroEntity> getListDuplicadoDescendenteDAO(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas);
	public SCGEstudianteRegistroEntity getForUpdateDuplicadoDAO(SCGEstudianteRegistroEntity entity);
	public SCGEstudianteRegistroAdjuntoEntity getForUpdateDuplicadoAdjuntoDAO(SCGEstudianteRegistroAdjuntoEntity entity);
	public SCGAdjuntoEntity getForUpdateAdjuntoDuplicadoDAO(SCGAdjuntoEntity entity);
	public Long getNumberRegistroMaxDuplicadoDAO(SCGEstudianteRegistroEntity entity);
	public SCGEstudianteRegistroEntity saveDuplicadoDAO(SCGEstudianteRegistroEntity entity);
	public SCGAdjuntoEntity saveAdjuntoDuplicadoDAO(SCGAdjuntoEntity entity);
	public SCGEstudianteRegistroAdjuntoEntity saveDuplicadoAdjuntoDAO(SCGEstudianteRegistroAdjuntoEntity entity);
	public void deleteDAO();
}
