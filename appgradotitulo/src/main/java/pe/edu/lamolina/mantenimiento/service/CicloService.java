package pe.edu.lamolina.mantenimiento.service;

import java.util.List;

import pe.edu.lamolina.gradotitulo.entity.SCGCicloEntity;
import pe.edu.lamolina.util.FilterUtil;

public interface CicloService {
	public List<SCGCicloEntity> listCiclo(SCGCicloEntity entity, FilterUtil filterUtil);
	public SCGCicloEntity saveCiclo(SCGCicloEntity entity);
	public SCGCicloEntity getForUpdateCiclo(SCGCicloEntity entity);
}
