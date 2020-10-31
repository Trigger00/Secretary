package pe.edu.lamolina.mantenimiento.service;

import java.util.List;

import pe.edu.lamolina.gradotitulo.entity.SCGTipoUniversidadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGUniversidadEntity;
import pe.edu.lamolina.util.FilterUtil;

public interface UniversidadService {
	public List<SCGTipoUniversidadEntity> listTipoUniversidad();
	public List<SCGUniversidadEntity> listUniversidad( SCGUniversidadEntity entity, FilterUtil filterUtil );
	public SCGUniversidadEntity saveUniversidad( SCGUniversidadEntity entity );
	public SCGUniversidadEntity getForUpdateUnverisdad( SCGUniversidadEntity entity );


}
