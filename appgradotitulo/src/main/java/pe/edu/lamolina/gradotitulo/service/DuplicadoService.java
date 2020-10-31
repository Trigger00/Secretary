package pe.edu.lamolina.gradotitulo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.gradotitulo.entity.SCGAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGEstudianteRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGGradoAcademicoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGTipoAdjuntoEntity;
import pe.edu.lamolina.logging.UNALMLogger;
import pe.edu.lamolina.util.FilterUtil;
import pe.edu.lamolina.util.TypesUtil;

public interface DuplicadoService {
	public List<SCGEstudianteRegistroEntity> getListDuplicado(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas);
	public List<SCGEstudianteRegistroEntity> getListDuplicadoAscendente(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas);
	public List<SCGEstudianteRegistroEntity> getListDuplicadoDescendente(SCGEstudianteRegistroEntity entity,FilterUtil filterUtil,List<Long> agendas);
	public SCGEstudianteRegistroEntity getForUpdateDuplicado(SCGEstudianteRegistroEntity entity);
	public SCGEstudianteRegistroAdjuntoEntity getForUpdateDuplicadoAdjunto(SCGEstudianteRegistroAdjuntoEntity entity);
	public Long getNumberRegistroMaxDuplicado(SCGEstudianteRegistroEntity entity);
	public SCGEstudianteRegistroEntity saveDuplicado(SCGEstudianteRegistroEntity entity);
	public SCGAdjuntoEntity saveAdjuntoDuplicado(SCGAdjuntoEntity entity);
	public SCGEstudianteRegistroAdjuntoEntity saveDuplicadoAdjunto(SCGEstudianteRegistroAdjuntoEntity entity);
	public void delete(SCGEstudianteRegistroEntity entity);
	public void deleteFiles(SCGAdjuntoEntity entity);
	public void syncFiles(SCGAdjuntoEntity entity);
	public void enviarSunedu(SCGEstudianteRegistroEntity entity);
	public void agendasClose(SCGEstudianteRegistroEntity entity,String groupList);
	
	public  List<Long> addCodigoAgenda(String[] splitSelectAgenda);
	public  String[] getSplitSelectAgenda(String selectAgenda);
	public void saveAgendasThatWillNotBeClosed(List<SCGEstudianteRegistroEntity> listOfAgendasThatWillNotBeClosed );
	public SCGEstudianteRegistroEntity getEstudianteRegistroOriginal(SCGEstudianteRegistroEntity agendaThatHasToBeClose);
	public List<SCGEstudianteRegistroEntity> getAgendasThatWillNotBeClosedList(List<SCGEstudianteRegistroEntity> listOfOpenAgendas ,List<SCGEstudianteRegistroEntity> listOfAgendasThatHasToBeClose);
	public void updateParentRecordNumberToOriginalRecord(SCGEstudianteRegistroEntity entity);
	public String setFormatResolucionRecotoral(SCGEstudianteRegistroEntity entity);

	public SCGEstudianteRegistroAdjuntoEntity setValuesDuplicadoAdjunto(SCGEstudianteRegistroEntity duplicado,SCGAdjuntoEntity adjunto);
	public SCGAdjuntoEntity setValuesAdjunto(MultipartFile archivo,long codigoTipoAdjunto) throws IOException;
	public String setURLEscaneado(SCGEstudianteRegistroEntity estudianteRegistro,SCGAdjuntoEntity adjunto);
	public String setURLFoto(SCGEstudianteRegistroEntity estudianteRegistro,SCGAdjuntoEntity adjunto);
	public Long getCodigoAdjuntoByDuplicadoAdjunto(SCGEstudianteRegistroAdjuntoEntity entity);
	public SCGEstudianteRegistroAdjuntoEntity getDuplicadoAdjunto(Long codigo);
	
	public  Long getFormatCodigoAdjunto(String codigo);
	public boolean isDuplicateRecord(SCGEstudianteRegistroEntity entity);

}
