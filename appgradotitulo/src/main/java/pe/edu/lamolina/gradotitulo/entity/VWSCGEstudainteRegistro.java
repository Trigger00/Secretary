package pe.edu.lamolina.gradotitulo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pe.edu.lamolina.constant.ApplicationConstant;

@Entity
@Table(name = "VW_SCG_ESTUDIANTE_REGISTRO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class VWSCGEstudainteRegistro implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "VWSCGEstudainteRegistroGNRTOR")
	@TableGenerator(name = "VWSCGEstudainteRegistroGNRTOR", 
					table=ApplicationConstant.KEY_MANAGER,
		         	pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
		         	valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "VW_SCG_ESTUDIANTE_REGISTRO", 
					allocationSize = 1)
	@Column(name = "CO_VW_SCG_ESTUDIANTE_REGISTRO")
	private Long codigo;
	
	@Column(name = "COD_UNV")
	private String codigoUniversidad;
	
	@Column(name = "RAZ_SOC")
	private String razonSocial;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "MATRI_FEC")
	private Date matriculaFecha;

	@Column(name = "FAC_NOM")
	private String facultadNombre;

	@Column(name = "ESC_CARR")
	private String escuelaCarrera;
	
	@Column(name = "ESC_POST")
	private String escuelaPostgrado;
	
	@Column(name = "APEPAT")
	private String apellidoPaterno;
	
	@Column(name = "APEMAT")
	private String apellidoMaterno;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "SEXO")
	private String sexo;
	
	@Column(name = "DOCU_TIP")
	private String documentoTipo;
	
	@Column(name = "DOCU_NUM")
	private String documentoNumero;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "EGRES_FEC")
	private Date egresadoFecha;
	/**/
	
	@Column(name = "PROC_BACH")
	private String procedenciaBachiller;
	
	@Column(name = "GRAD_TITU")
	private String gradoTitulo;
	
	@Column(name = "ESPECIALIDAD")
	private String especialidad;
	
	@Column(name = "ABRE_GYT")
	private String abreviaturaGradoTitulo;
	
	@Column(name = "ACTO_TIP")
	private String actoTituloGrado;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ACTO_FEC")
	private Date actoFecha;
	/**/
	@Column(name = "TESIS")
	private String tesis;
	
	@Column(name = "MODALIDAD")
	private String modadlidad;
	
	@Column(name = "PROC_REV_PAIS")
	private String procedenciaRevalidadPais;
	
	@Column(name = "PROC_REV_UNIV")
	private String procedenciaRevalidadUniversidad;
	
	@Column(name = "PROC_REV_GRADO_EXT")
	private String procedenciaRevalidadGradoExtranjero;
	
	@Column(name = "RESO_NUM")
	private String resolucionNumero;
	
	@Column(name = "RESO_FEC")
	private String resolucionFacultad; 
	/**/
	@Column(name = "DIPL_FEC")
	private Date diplomaFecha;
	
	@Column(name = "DIPL_NUM")
	private String diplomaNumero;
	
	@Column(name = "DIPL_TIP_EMI")
	private String diplomaTipoEmision;
	
	@Column(name = "REG_LIBRO")
	private Long registroLibro;
	
	@Column(name = "REG_FOLIO")
	private Long registroFolio;
	
	@Column(name = "REG_REGISTRO")
	private Long registroRegistro;
	
	@Column(name = "CARGO1")
	private String cargoUno;
	
	@Column(name = "AUTORIDAD1")
	private String autoridadUno;
	
	@Column(name = "CARGO2")
	private String cargoDos;
	
	@Column(name = "AUTORIDAD2")
	private String autoridadDos;
	
	@Column(name = "CARGO3")
	private String cargoTres;
	
	@Column(name = "AUTORIDAD3")
	private String autoridadTres;
	
	@Column(name = "REG_OFICIO")
	private String registroOficio;

	/**/

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getCodigoUniversidad() {
		return codigoUniversidad;
	}

	public void setCodigoUniversidad(String codigoUniversidad) {
		this.codigoUniversidad = codigoUniversidad;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Date getMatriculaFecha() {
		return matriculaFecha;
	}

	public void setMatriculaFecha(Date matriculaFecha) {
		this.matriculaFecha = matriculaFecha;
	}

	public String getFacultadNombre() {
		return facultadNombre;
	}

	public void setFacultadNombre(String facultadNombre) {
		this.facultadNombre = facultadNombre;
	}

	public String getEscuelaCarrera() {
		return escuelaCarrera;
	}

	public void setEscuelaCarrera(String escuelaCarrera) {
		this.escuelaCarrera = escuelaCarrera;
	}

	public String getEscuelaPostgrado() {
		return escuelaPostgrado;
	}

	public void setEscuelaPostgrado(String escuelaPostgrado) {
		this.escuelaPostgrado = escuelaPostgrado;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getDocumentoTipo() {
		return documentoTipo;
	}

	public void setDocumentoTipo(String documentoTipo) {
		this.documentoTipo = documentoTipo;
	}

	public String getDocumentoNumero() {
		return documentoNumero;
	}

	public void setDocumentoNumero(String documentoNumero) {
		this.documentoNumero = documentoNumero;
	}

	public Date getEgresadoFecha() {
		return egresadoFecha;
	}

	public void setEgresadoFecha(Date egresadoFecha) {
		this.egresadoFecha = egresadoFecha;
	}

	public String getProcedenciaBachiller() {
		return procedenciaBachiller;
	}

	public void setProcedenciaBachiller(String procedenciaBachiller) {
		this.procedenciaBachiller = procedenciaBachiller;
	}

	public String getGradoTitulo() {
		return gradoTitulo;
	}

	public void setGradoTitulo(String gradoTitulo) {
		this.gradoTitulo = gradoTitulo;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public String getAbreviaturaGradoTitulo() {
		return abreviaturaGradoTitulo;
	}

	public void setAbreviaturaGradoTitulo(String abreviaturaGradoTitulo) {
		this.abreviaturaGradoTitulo = abreviaturaGradoTitulo;
	}

	public String getActoTituloGrado() {
		return actoTituloGrado;
	}

	public void setActoTituloGrado(String actoTituloGrado) {
		this.actoTituloGrado = actoTituloGrado;
	}

	public Date getActoFecha() {
		return actoFecha;
	}

	public void setActoFecha(Date actoFecha) {
		this.actoFecha = actoFecha;
	}

	public String getTesis() {
		return tesis;
	}

	public void setTesis(String tesis) {
		this.tesis = tesis;
	}

	public String getModadlidad() {
		return modadlidad;
	}

	public void setModadlidad(String modadlidad) {
		this.modadlidad = modadlidad;
	}

	public String getProcedenciaRevalidadPais() {
		return procedenciaRevalidadPais;
	}

	public void setProcedenciaRevalidadPais(String procedenciaRevalidadPais) {
		this.procedenciaRevalidadPais = procedenciaRevalidadPais;
	}

	public String getProcedenciaRevalidadUniversidad() {
		return procedenciaRevalidadUniversidad;
	}

	public void setProcedenciaRevalidadUniversidad(String procedenciaRevalidadUniversidad) {
		this.procedenciaRevalidadUniversidad = procedenciaRevalidadUniversidad;
	}

	public String getProcedenciaRevalidadGradoExtranjero() {
		return procedenciaRevalidadGradoExtranjero;
	}

	public void setProcedenciaRevalidadGradoExtranjero(String procedenciaRevalidadGradoExtranjero) {
		this.procedenciaRevalidadGradoExtranjero = procedenciaRevalidadGradoExtranjero;
	}

	public String getResolucionNumero() {
		return resolucionNumero;
	}

	public void setResolucionNumero(String resolucionNumero) {
		this.resolucionNumero = resolucionNumero;
	}

	public String getResolucionFacultad() {
		return resolucionFacultad;
	}

	public void setResolucionFacultad(String resolucionFacultad) {
		this.resolucionFacultad = resolucionFacultad;
	}

	public Date getDiplomaFecha() {
		return diplomaFecha;
	}

	public void setDiplomaFecha(Date diplomaFecha) {
		this.diplomaFecha = diplomaFecha;
	}

	public String getDiplomaNumero() {
		return diplomaNumero;
	}

	public void setDiplomaNumero(String diplomaNumero) {
		this.diplomaNumero = diplomaNumero;
	}

	public String getDiplomaTipoEmision() {
		return diplomaTipoEmision;
	}

	public void setDiplomaTipoEmision(String diplomaTipoEmision) {
		this.diplomaTipoEmision = diplomaTipoEmision;
	}

	public Long getRegistroLibro() {
		return registroLibro;
	}

	public void setRegistroLibro(Long registroLibro) {
		this.registroLibro = registroLibro;
	}

	public Long getRegistroFolio() {
		return registroFolio;
	}

	public void setRegistroFolio(Long registroFolio) {
		this.registroFolio = registroFolio;
	}

	public Long getRegistroRegistro() {
		return registroRegistro;
	}

	public void setRegistroRegistro(Long registroRegistro) {
		this.registroRegistro = registroRegistro;
	}

	public String getCargoUno() {
		return cargoUno;
	}

	public void setCargoUno(String cargoUno) {
		this.cargoUno = cargoUno;
	}

	public String getAutoridadUno() {
		return autoridadUno;
	}

	public void setAutoridadUno(String autoridadUno) {
		this.autoridadUno = autoridadUno;
	}

	public String getCargoDos() {
		return cargoDos;
	}

	public void setCargoDos(String cargoDos) {
		this.cargoDos = cargoDos;
	}

	public String getAutoridadDos() {
		return autoridadDos;
	}

	public void setAutoridadDos(String autoridadDos) {
		this.autoridadDos = autoridadDos;
	}

	public String getCargoTres() {
		return cargoTres;
	}

	public void setCargoTres(String cargoTres) {
		this.cargoTres = cargoTres;
	}

	public String getAutoridadTres() {
		return autoridadTres;
	}

	public void setAutoridadTres(String autoridadTres) {
		this.autoridadTres = autoridadTres;
	}

	public String getRegistroOficio() {
		return registroOficio;
	}

	public void setRegistroOficio(String registroOficio) {
		this.registroOficio = registroOficio;
	}
	
}
