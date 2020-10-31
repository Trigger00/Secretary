package pe.edu.lamolina.tramitedocumentario.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import pe.edu.lamolina.constant.ApplicationConstant;

@Entity
@Table(name = "SCG_ARCHIVO_TRAMITE_DOC", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGArchivoTramiteDocumento implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGArchivoTramiteDocGNRTOR")
	@TableGenerator(name = "SCGArchivoTramiteDocGNRTOR", 
            		table=ApplicationConstant.KEY_MANAGER,
            		pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
            		valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_ARCHIVO_TRAMITE_DOC", 
					allocationSize = 1)
	@Column(name = "CO_SCG_ARCHIVO_TRAMITE_DOC")
	private Long codigo;
	
	@Column(name = "TX_NOMBRE_ARCHIVO")
	private String textoNombreArchivo;
	
	@Column(name = "TX_NOMBRE_REGISTRO")
	private String textoNombreRegistro;
	
	@Column(name = "TX_RUTA") 
	private String textoRuta;
	
	
	@Column(name = "TX_DETALLE")
	private String textoDetalle;
	
	@Column(name = "TX_ASUNTO")
	private String textoAsunto;
	
	@Column(name = "FL_ACTIVO") 
	private String flagActivo;
	
	@Column(name = "FL_CANDADO") 
	private String flagCandado;

	@Column(name = "FL_ELIMINADO") 
	private String flagEliminado;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FE_AGREGAR")
	private Date fechaAgregar;
	
	@Transient
	@Temporal(TemporalType.DATE)
	private Date fechaAgregarInicio;
	
	@Transient
	@Temporal(TemporalType.DATE)
	private Date fechaAgregarFinal;
	
	@Transient
	private MultipartFile archivo;
	
	@Transient
	private String textoNombreRegistroNoMatch;
	
	@Transient
	private String textoRutaNoMatch;
	
	@OneToMany(mappedBy = "archivoTramiteDocumento", fetch = FetchType.LAZY)
	private List<SCGValoresEntity> valoresList;
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getTextoNombreArchivo() {
		return textoNombreArchivo;
	}

	public void setTextoNombreArchivo(String textoNombreArchivo) {
		this.textoNombreArchivo = textoNombreArchivo;
	}

	public String getTextoRuta() {
		return textoRuta;
	}

	public void setTextoRuta(String textoRuta) {
		this.textoRuta = textoRuta;
	}

	public String getTextoDetalle() {
		return textoDetalle;
	}

	public void setTextoDetalle(String textoDetalle) {
		this.textoDetalle = textoDetalle;
	}

	public String getTextoAsunto() {
		return textoAsunto;
	}

	public void setTextoAsunto(String textoAsunto) {
		this.textoAsunto = textoAsunto;
	}

	public String getFlagActivo() {
		return flagActivo;
	}

	public void setFlagActivo(String flagActivo) {
		this.flagActivo = flagActivo;
	}

	public String getFlagEliminado() {
		return flagEliminado;
	}

	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}

	public MultipartFile getArchivo() {
		return archivo;
	}

	public void setArchivo(MultipartFile archivo) {
		this.archivo = archivo;
	}

	public Date getFechaAgregar() {
		return fechaAgregar;
	}

	public void setFechaAgregar(Date fechaAgregar) {
		this.fechaAgregar = fechaAgregar;
	}

	public Date getFechaAgregarInicio() {
		return fechaAgregarInicio;
	}

	public void setFechaAgregarInicio(Date fechaAgregarInicio) {
		this.fechaAgregarInicio = fechaAgregarInicio;
	}

	public Date getFechaAgregarFinal() {
		return fechaAgregarFinal;
	}

	public void setFechaAgregarFinal(Date fechaAgregarFinal) {
		this.fechaAgregarFinal = fechaAgregarFinal;
	}

	

	public String getTextoNombreRegistro() {
		return textoNombreRegistro;
	}

	public void setTextoNombreRegistro(String textoNombreRegistro) {
		this.textoNombreRegistro = textoNombreRegistro;
	}

	public String getFlagCandado() {
		return flagCandado;
	}

	public void setFlagCandado(String flagCandado) {
		this.flagCandado = flagCandado;
	}

	public String getTextoNombreRegistroNoMatch() {
		return textoNombreRegistroNoMatch;
	}

	public void setTextoNombreRegistroNoMatch(String textoNombreRegistroNoMatch) {
		this.textoNombreRegistroNoMatch = textoNombreRegistroNoMatch;
	}

	public String getTextoRutaNoMatch() {
		return textoRutaNoMatch;
	}

	public void setTextoRutaNoMatch(String textoRutaNoMatch) {
		this.textoRutaNoMatch = textoRutaNoMatch;
	}

	public List<SCGValoresEntity> getValoresList() {
		return valoresList;
	}

	public void setValoresList(List<SCGValoresEntity> valoresList) {
		this.valoresList = valoresList;
	}
	
	
	
}
