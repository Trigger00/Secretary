package pe.edu.lamolina.gradotitulo.dao;

import java.util.List;

import pe.edu.lamolina.gradotitulo.entity.SCGRevalidaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoRevalidaEntity;
import pe.edu.lamolina.util.FilterUtil;

public interface RevalidaDAO {
	public List<SCGRevalidaEntity> getListRevalidaDAO(SCGRevalidaEntity entity,FilterUtil filterUtil,List<Long> agendas);
	public List<SCGRevalidaEntity> getListRevalidaAscendeteDAO(SCGRevalidaEntity entity,FilterUtil filterUtil,List<Long> agendas);
	public List<SCGRevalidaEntity> getListRevalidaDescendenteDAO(SCGRevalidaEntity entity,FilterUtil filterUtil,List<Long> agendas);
	public List<SCGRevalidaEntity> listDependentRecordsDAO(SCGRevalidaEntity entity	);
	public SCGRevalidaEntity getForUpdateRevalidaDAO(SCGRevalidaEntity entity);
	public SCGRevalidaEntity saveRevalidaDAO(SCGRevalidaEntity entity);
	public SCGTipoRevalidaEntity getForUpdateTipoRevalidaDAO(SCGTipoRevalidaEntity entity);
	public Long getNumberRegistroMaxRevalidaDAO(SCGRevalidaEntity entity);
	public void deleteDAO();
}
