package pe.edu.lamolina.gradotitulo.service;

import java.util.List;

import pe.edu.lamolina.gradotitulo.entity.SCGDiplomadoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGRevalidaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoDiplomadoEntity;
import pe.edu.lamolina.util.FilterUtil;

public interface DiplomadoService {
	public List<SCGDiplomadoEntity> getListDiplomado(SCGDiplomadoEntity entity,FilterUtil filterUtil,List<Long> agendas);
	public List<SCGTipoDiplomadoEntity> getListTipoDiplomado(SCGTipoDiplomadoEntity entity);
	public SCGDiplomadoEntity getForUpdateDiplomado(SCGDiplomadoEntity entity);
	public Long getNumberRegistroMaxDiplomado();
	public SCGDiplomadoEntity saveDiplomado(SCGDiplomadoEntity entity);
	public String setFormatResolucionRecotoral(SCGDiplomadoEntity entity);
	public void delete(SCGDiplomadoEntity entity);
	public void enviarSunedu(SCGDiplomadoEntity entity);
}
