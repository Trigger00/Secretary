package pe.edu.lamolina.tramitedocumentario.dao;

import java.util.List;

import pe.edu.lamolina.tramitedocumentario.entity.SCGArchivoTramiteDocumento;
import pe.edu.lamolina.util.FilterUtil;

public interface DocumentoDAO {
	public SCGArchivoTramiteDocumento saveDAO( SCGArchivoTramiteDocumento entity);
	public List<SCGArchivoTramiteDocumento> listArchivoTramiteDocumentoDAO(SCGArchivoTramiteDocumento entity, FilterUtil filterUtil);
	public SCGArchivoTramiteDocumento getForUpdateArchivoTramiteDocumentoDAO(SCGArchivoTramiteDocumento entity);
	
}
