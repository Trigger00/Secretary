package pe.edu.lamolina.gradotitulo.dao;

import java.util.List;

import pe.edu.lamolina.gradotitulo.entity.SCGDiplomadoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGRevalidaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoDiplomadoEntity;
import pe.edu.lamolina.util.FilterUtil;

public interface DiplomadoDAO {
	public List<SCGDiplomadoEntity> getListDiplomadoDAO(SCGDiplomadoEntity entity,FilterUtil filterUtil,List<Long> agendas);
	public List<SCGTipoDiplomadoEntity> getListTipoDiplomadoDAO(SCGTipoDiplomadoEntity entity);
	public SCGDiplomadoEntity getForUpdateDiplomadoDAO(SCGDiplomadoEntity entity);	
	public SCGDiplomadoEntity saveDiplomadoDAO(SCGDiplomadoEntity entity);
	public Long getNumberRegistroMaxDiplomadoDAO();
	public void deleteDAO();
}
