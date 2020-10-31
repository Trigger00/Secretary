package pe.edu.lamolina.gradotitulo.dao;

import java.util.List;

import pe.edu.lamolina.gradotitulo.entity.SCGAdjuntoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGAutoridadRegistroEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGCargoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGGradoAcademicoEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGPeriodoEntity;

public interface AutoridadDAO {
	
	public List<SCGAutoridadRegistroEntity> getListAutoridadDAO(SCGAutoridadRegistroEntity entity);
	public List<SCGPeriodoEntity> getListPeriodoDAO(SCGPeriodoEntity entity);
	public List<SCGGradoAcademicoEntity> getListGradoAcademicoDAO(SCGGradoAcademicoEntity entity);
	public List<SCGCargoEntity> getListCargoDAO(SCGCargoEntity entity);
	public SCGAutoridadRegistroEntity getForUpdateAutoridadDAO(SCGAutoridadRegistroEntity entity);
	public SCGAdjuntoEntity getForUpdateAdjuntoDAO(SCGAdjuntoEntity entity);
	public SCGPeriodoEntity getForUpdatePeriodoDAO(SCGPeriodoEntity entity);
	public SCGAutoridadRegistroEntity saveAutoridadDAO(SCGAutoridadRegistroEntity entity);
	public SCGPeriodoEntity savePeriodoDAO(SCGPeriodoEntity entity);
	public SCGAdjuntoEntity saveAdjuntoDAO(SCGAdjuntoEntity entity);
	public void deleteDAO();


}
