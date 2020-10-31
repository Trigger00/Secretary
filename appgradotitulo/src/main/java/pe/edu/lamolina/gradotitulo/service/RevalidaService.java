package pe.edu.lamolina.gradotitulo.service;

import java.util.List;

import pe.edu.lamolina.gradotitulo.entity.SCGRevalidaEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoRevalidaEntity;
import pe.edu.lamolina.util.FilterUtil;

public interface RevalidaService {
	public List<SCGRevalidaEntity> getListRevalida(SCGRevalidaEntity entity,FilterUtil filterUtil,List<Long> agendas);
	public SCGTipoRevalidaEntity getForUpdateTipoRevalidaDAO(SCGTipoRevalidaEntity entity);
	public SCGRevalidaEntity getForUpdateRevalida(SCGRevalidaEntity entity);	
	public SCGRevalidaEntity saveRevalida(SCGRevalidaEntity entity);
	public List<SCGRevalidaEntity> listDependentRecords(SCGRevalidaEntity entity);
	public Long getNumberRegistroMaxRevalida(SCGRevalidaEntity entity);
	public String setFormatResolucionRecotoral(SCGRevalidaEntity entity);
	public void delete(SCGRevalidaEntity entity);
	public void enviarSunedu(SCGRevalidaEntity entity);
	
	public void agendasClose(SCGRevalidaEntity entity,String groupList);
	public  List<Long> addCodigoAgenda(String[] splitSelectAgenda);
	public  String[] getSplitSelectAgenda(String selectAgenda);
	public void saveAgendasThatWillNotBeClosed(List<SCGRevalidaEntity> listOfAgendasThatWillNotBeClosed );
	public List<SCGRevalidaEntity> getAgendasThatWillNotBeClosedList(List<SCGRevalidaEntity> listOfOpenAgendas ,List<SCGRevalidaEntity> listOfAgendasThatHasToBeClose);
	public boolean isDuplicateRecord(SCGRevalidaEntity entity);


}
