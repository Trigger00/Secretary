package pe.edu.lamolina.gradotitulo.entity;

import java.io.Serializable;
import java.sql.Date;
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
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import pe.edu.lamolina.constant.ApplicationConstant;

@Entity
@Table(name = "SCG_ADJUNTO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGAdjuntoEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGAdjuntoGNRTOR")
	@TableGenerator(name = "SCGAdjuntoGNRTOR", 
            		table=ApplicationConstant.KEY_MANAGER,
            		pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
            		valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_ADJUNTO", 
					allocationSize = 1)
	@Column(name = "CO_SCG_ADJUNTO")
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_TIPO_ADJUNTO")
	private SCGTipoAdjuntoEntity tipoAdjunto;
	
	@Column(name = "TX_RUTA") 
	private String textoRuta;
	
	@Column(name = "TX_NOMBREARCHIVO") 
	private String textoNombreArchivo;
	
	@Column(name = "FE_AGREGAR") 
	private Date fechaAgregar;
	
	@Column(name = "FL_ELIMINADO") 
	private String flagEliminado;
	
	@Column(name = "FI_ARCHIVO")
	private byte[] fileArchivo;
	
	@Transient
	private MultipartFile archivo;
	
	@OneToMany(mappedBy = "adjunto", fetch = FetchType.LAZY)
	private List<SCGPersonaEntity> personaList;
	
	@OneToMany(mappedBy = "adjunto", fetch = FetchType.LAZY)
	private List<SCGEstudianteRegistroEntity> estudianteRegistroList;

	@OneToMany(mappedBy = "adjunto", fetch = FetchType.LAZY)
	private List<SCGRevalidaEntity> revalidaList;
	
	@OneToMany(mappedBy = "adjunto", fetch = FetchType.LAZY)
	private List<SCGEstudianteRegistroAdjuntoEntity> estudianteRegistroAdjuntoList;

	public Long getCodigo() {
		return codigo;
	}


	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}


	public String getTextoRuta() {
		return textoRuta;
	}


	public void setTextoRuta(String textoRuta) {
		this.textoRuta = textoRuta;
	}


	public String getTextoNombreArchivo() {
		return textoNombreArchivo;
	}


	public void setTextoNombreArchivo(String textoNombreArchivo) {
		this.textoNombreArchivo = textoNombreArchivo;
	}


	public Date getFechaAgregar() {
		return fechaAgregar;
	}


	public void setFechaAgregar(Date fechaAgregar) {
		this.fechaAgregar = fechaAgregar;
	}


	public String getFlagEliminado() {
		return flagEliminado;
	}


	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}

	public List<SCGPersonaEntity> getPersonaList() {
		return personaList;
	}

	public void setPersonaList(List<SCGPersonaEntity> personaList) {
		this.personaList = personaList;
	}


	public byte[] getFileArchivo() {
		return fileArchivo;
	}


	public void setFileArchivo(byte[] fileArchivo) {
		this.fileArchivo = fileArchivo;
	}


	public MultipartFile getArchivo() {
		return archivo;
	}


	public void setArchivo(MultipartFile archivo) {
		this.archivo = archivo;
	}


	public List<SCGEstudianteRegistroEntity> getEstudianteRegistroList() {
		return estudianteRegistroList;
	}


	public void setEstudianteRegistroList(List<SCGEstudianteRegistroEntity> estudianteRegistroList) {
		this.estudianteRegistroList = estudianteRegistroList;
	}


	public List<SCGRevalidaEntity> getRevalidaList() {
		return revalidaList;
	}


	public void setRevalidaList(List<SCGRevalidaEntity> revalidaList) {
		this.revalidaList = revalidaList;
	}


	public SCGTipoAdjuntoEntity getTipoAdjunto() {
		return tipoAdjunto;
	}


	public void setTipoAdjunto(SCGTipoAdjuntoEntity tipoAdjunto) {
		this.tipoAdjunto = tipoAdjunto;
	}


	public List<SCGEstudianteRegistroAdjuntoEntity> getEstudianteRegistroAdjuntoList() {
		return estudianteRegistroAdjuntoList;
	}


	public void setEstudianteRegistroAdjuntoList(List<SCGEstudianteRegistroAdjuntoEntity> estudianteRegistroAdjuntoList) {
		this.estudianteRegistroAdjuntoList = estudianteRegistroAdjuntoList;
	}
	
}
