package pe.edu.lamolina.mantenimiento.dao;

import java.util.List;

import pe.edu.lamolina.gradotitulo.entity.SCGTipoUniversidadEntity;
import pe.edu.lamolina.gradotitulo.entity.SCGUniversidadEntity;
import pe.edu.lamolina.util.FilterUtil;

public interface UniversidadDAO {
	public List<SCGTipoUniversidadEntity> listTipoUniversidadDAO();
	public List<SCGUniversidadEntity> listUniversidadDAO( SCGUniversidadEntity entity, FilterUtil filterUtil );
	public SCGUniversidadEntity saveUniversidadDAO( SCGUniversidadEntity entity );
	public SCGUniversidadEntity getForUpdateUnverisdadDAO( SCGUniversidadEntity entity );
	public void deleteDAO();

}
