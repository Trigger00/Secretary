package pe.edu.lamolina.tramitedocumentario.service;

import java.util.List;

import pe.edu.lamolina.tramitedocumentario.entity.SCGArchivoTramiteDocumento;
import pe.edu.lamolina.util.FilterUtil;

public interface DocumentoService {
	public SCGArchivoTramiteDocumento save( SCGArchivoTramiteDocumento entity);
	public List<SCGArchivoTramiteDocumento> listArchivoTramiteDocumento(SCGArchivoTramiteDocumento entity, FilterUtil filterUtil);
	public SCGArchivoTramiteDocumento getForUpdateArchivoTramiteDocumento(SCGArchivoTramiteDocumento entity);
	public void delete(SCGArchivoTramiteDocumento entity);
	public String validate(SCGArchivoTramiteDocumento entity);
//	public String validateDuplicate(SCGArchivoTramiteDocumento entity);
//	public String validateEdit(SCGArchivoTramiteDocumento solicitudProceso, Long Proceso);
//	public String validateDelete(SCGArchivoTramiteDocumento solicitudProceso);
	

}
