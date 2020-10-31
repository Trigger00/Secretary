package pe.edu.lamolina.mantenimiento.dao;

import java.util.List;

import pe.edu.lamolina.gradotitulo.entity.SCGCicloEntity;
import pe.edu.lamolina.util.FilterUtil;

public interface CicloDAO {
	public List<SCGCicloEntity> listCicloDAO( SCGCicloEntity entity, FilterUtil filterUtil );
	public SCGCicloEntity saveCicloDAO( SCGCicloEntity entity );
	public SCGCicloEntity getForUpdateCicloDAO( SCGCicloEntity entity );
	public void deleteDAO();
}
