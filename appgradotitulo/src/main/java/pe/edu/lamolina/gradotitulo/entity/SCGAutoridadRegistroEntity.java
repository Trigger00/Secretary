package pe.edu.lamolina.gradotitulo.entity;

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

import pe.edu.lamolina.constant.ApplicationConstant;

@Entity
@Table(name = "SCG_AUTORIDAD_REGISTRO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGAutoridadRegistroEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGAutoridadRegistroGNRTOR")
	@TableGenerator(name = "SCGAutoridadRegistroGNRTOR", 
            		table=ApplicationConstant.KEY_MANAGER,
            		pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
            		valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_AUTORIDAD_REGISTRO", 
					allocationSize = 1)
	@Column(name = "CO_SCG_AUTORIDAD_REGISTRO")
	private Long codigo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_CARGO")
	private SCGCargoEntity cargo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_GRADO_ACADEMICO")
	private SCGGradoAcademicoEntity gradoAcademico;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_ADJUNTO")
	private SCGAdjuntoEntity adjunto;
	
	@Column(name = "TX_NOMBREAUTORIDAD") 
	private String textoNombreAutoridad;
	
	@Column(name = "FL_ESTADO") 
	private String flagEstado;
	
	@Column(name = "FL_ELIMINADO") 
	private String flagEliminado;
	
	
	@Column(name = "FL_ANULADO") 
	private String flagAnulado;
	
	@Column(name = "FE_AGREGAR") 
	private Date fechaAgregar;
	
	@Column(name = "FE_EDICION") 
	private Date  fechaEdicion;
	
	@Column(name = "FE_ELIMINAR") 
	private Date fechaEliminar;
	
	@Column(name = "TX_USUARIOAGREGAR") 
	private String textoUsuarioAgregar;
	
	@Column(name = "TX_USUARIOEDICION") 
	private String textoUsuarioEdicion;
	
	@Column(name = "TX_USUARIOELIMINAR") 
	private String textoUsuarioEliminar;

	@OneToMany(mappedBy = "autoridadRegistroRector", fetch = FetchType.LAZY)
	private List<SCGDiplomadoEntity> diplomadoRectorList;
	
	@OneToMany(mappedBy = "autoridadRegistroDecano", fetch = FetchType.LAZY)
	private List<SCGDiplomadoEntity> diplomadoDecanoList;

	
	@OneToMany(mappedBy = "autoridadRegistroSecretarioGeneral", fetch = FetchType.LAZY)
	private List<SCGRevalidaEntity> revalidaSecretarioGeneralList;
	
	@OneToMany(mappedBy = "autoridadRegistroRector", fetch = FetchType.LAZY)
	private List<SCGRevalidaEntity> revalidaRectorList;

	@OneToMany(mappedBy = "autoridadRegistroDecano", fetch = FetchType.LAZY)
	private List<SCGRevalidaEntity> revalidaDecanoList;

	@OneToMany(mappedBy = "autoridadRegistroDirectorPostgrado", fetch = FetchType.LAZY)
	private List<SCGRevalidaEntity> revalidaDirectorPosgradoList;

	
	
	@OneToMany(mappedBy = "autoridadRegistroDecano", fetch = FetchType.LAZY)
	private List<SCGEstudianteRegistroEntity> estudianteRegistroDecanoList;
	
	@OneToMany(mappedBy = "autoridadRegistroSecretarioGeneral", fetch = FetchType.LAZY)
	private List<SCGEstudianteRegistroEntity> estudianteRegistroSecreatioGeneralList;
	
	@OneToMany(mappedBy = "autoridadRegistroRector", fetch = FetchType.LAZY)
	private List<SCGEstudianteRegistroEntity> estudianteRegistroRectorList;

	@OneToMany(mappedBy = "autoridadRegistroDirectorPostgrado", fetch = FetchType.LAZY)
	private List<SCGEstudianteRegistroEntity> estudianteRegistroDirectorPosgradoList;

	@OneToMany(mappedBy = "autoridadRegistro", fetch = FetchType.LAZY)
	private List<SCGPeriodoEntity> periodoList;
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public SCGCargoEntity getCargo() {
		return cargo;
	}

	public void setCargo(SCGCargoEntity cargo) {
		this.cargo = cargo;
	}

	public SCGGradoAcademicoEntity getGradoAcademico() {
		return gradoAcademico;
	}

	public void setGradoAcademico(SCGGradoAcademicoEntity gradoAcademico) {
		this.gradoAcademico = gradoAcademico;
	}


	public String getTextoNombreAutoridad() {
		return textoNombreAutoridad;
	}

	public void setTextoNombreAutoridad(String textoNombreAutoridad) {
		this.textoNombreAutoridad = textoNombreAutoridad;
	}

	public String getFlagEliminado() {
		return flagEliminado;
	}

	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}

	public String getFlagAnulado() {
		return flagAnulado;
	}

	public void setFlagAnulado(String flagAnulado) {
		this.flagAnulado = flagAnulado;
	}

	

	public Date getFechaAgregar() {
		return fechaAgregar;
	}

	public void setFechaAgregar(Date fechaAgregar) {
		this.fechaAgregar = fechaAgregar;
	}

	public Date getFechaEdicion() {
		return fechaEdicion;
	}

	public void setFechaEdicion(Date fechaEdicion) {
		this.fechaEdicion = fechaEdicion;
	}

	public Date getFechaEliminar() {
		return fechaEliminar;
	}

	public void setFechaEliminar(Date fechaEliminar) {
		this.fechaEliminar = fechaEliminar;
	}

	public String getTextoUsuarioAgregar() {
		return textoUsuarioAgregar;
	}

	public void setTextoUsuarioAgregar(String textoUsuarioAgregar) {
		this.textoUsuarioAgregar = textoUsuarioAgregar;
	}

	public String getTextoUsuarioEdicion() {
		return textoUsuarioEdicion;
	}

	public void setTextoUsuarioEdicion(String textoUsuarioEdicion) {
		this.textoUsuarioEdicion = textoUsuarioEdicion;
	}

	public String getTextoUsuarioEliminar() {
		return textoUsuarioEliminar;
	}

	public void setTextoUsuarioEliminar(String textoUsuarioEliminar) {
		this.textoUsuarioEliminar = textoUsuarioEliminar;
	}

	public SCGAdjuntoEntity getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(SCGAdjuntoEntity adjunto) {
		this.adjunto = adjunto;
	}

	public String getFlagEstado() {
		return flagEstado;
	}

	public void setFlagEstado(String flagEstado) {
		this.flagEstado = flagEstado;
	}

	public List<SCGDiplomadoEntity> getDiplomadoRectorList() {
		return diplomadoRectorList;
	}

	public void setDiplomadoRectorList(List<SCGDiplomadoEntity> diplomadoRectorList) {
		this.diplomadoRectorList = diplomadoRectorList;
	}

	public List<SCGDiplomadoEntity> getDiplomadoDecanoList() {
		return diplomadoDecanoList;
	}

	public void setDiplomadoDecanoList(List<SCGDiplomadoEntity> diplomadoDecanoList) {
		this.diplomadoDecanoList = diplomadoDecanoList;
	}

	public List<SCGRevalidaEntity> getRevalidaSecretarioGeneralList() {
		return revalidaSecretarioGeneralList;
	}

	public void setRevalidaSecretarioGeneralList(List<SCGRevalidaEntity> revalidaSecretarioGeneralList) {
		this.revalidaSecretarioGeneralList = revalidaSecretarioGeneralList;
	}

	public List<SCGRevalidaEntity> getRevalidaRectorList() {
		return revalidaRectorList;
	}

	public void setRevalidaRectorList(List<SCGRevalidaEntity> revalidaRectorList) {
		this.revalidaRectorList = revalidaRectorList;
	}

	public List<SCGEstudianteRegistroEntity> getEstudianteRegistroDecanoList() {
		return estudianteRegistroDecanoList;
	}

	public void setEstudianteRegistroDecanoList(List<SCGEstudianteRegistroEntity> estudianteRegistroDecanoList) {
		this.estudianteRegistroDecanoList = estudianteRegistroDecanoList;
	}

	public List<SCGEstudianteRegistroEntity> getEstudianteRegistroSecreatioGeneralList() {
		return estudianteRegistroSecreatioGeneralList;
	}

	public void setEstudianteRegistroSecreatioGeneralList(
			List<SCGEstudianteRegistroEntity> estudianteRegistroSecreatioGeneralList) {
		this.estudianteRegistroSecreatioGeneralList = estudianteRegistroSecreatioGeneralList;
	}

	public List<SCGEstudianteRegistroEntity> getEstudianteRegistroRectorList() {
		return estudianteRegistroRectorList;
	}

	public void setEstudianteRegistroRectorList(List<SCGEstudianteRegistroEntity> estudianteRegistroRectorList) {
		this.estudianteRegistroRectorList = estudianteRegistroRectorList;
	}

	public List<SCGPeriodoEntity> getPeriodoList() {
		return periodoList;
	}

	public void setPeriodoList(List<SCGPeriodoEntity> periodoList) {
		this.periodoList = periodoList;
	}

	public List<SCGEstudianteRegistroEntity> getEstudianteRegistroDirectorPosgradoList() {
		return estudianteRegistroDirectorPosgradoList;
	}

	public void setEstudianteRegistroDirectorPosgradoList(
			List<SCGEstudianteRegistroEntity> estudianteRegistroDirectorPosgradoList) {
		this.estudianteRegistroDirectorPosgradoList = estudianteRegistroDirectorPosgradoList;
	}

	public List<SCGRevalidaEntity> getRevalidaDecanoList() {
		return revalidaDecanoList;
	}

	public void setRevalidaDecanoList(List<SCGRevalidaEntity> revalidaDecanoList) {
		this.revalidaDecanoList = revalidaDecanoList;
	}

	public List<SCGRevalidaEntity> getRevalidaDirectorPosgradoList() {
		return revalidaDirectorPosgradoList;
	}

	public void setRevalidaDirectorPosgradoList(List<SCGRevalidaEntity> revalidaDirectorPosgradoList) {
		this.revalidaDirectorPosgradoList = revalidaDirectorPosgradoList;
	}

	

	
	
}
