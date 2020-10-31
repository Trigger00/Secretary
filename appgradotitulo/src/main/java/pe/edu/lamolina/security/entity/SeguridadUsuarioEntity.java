package pe.edu.lamolina.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import pe.edu.lamolina.constant.ApplicationConstant;

@Entity
@Table(name = "sg_usuario", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SeguridadUsuarioEntity {
	@Id
    @GeneratedValue (strategy= GenerationType.TABLE, generator="SeguridadUsuarioEntityGNRTOR")
    @TableGenerator(name="SeguridadUsuarioEntityGNRTOR",
                    table=ApplicationConstant.KEY_MANAGER,
                    pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
                    valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
                    pkColumnValue="sg_usuario",
                    allocationSize=1)
	@Column(name = "co_sg_usuario")
	private Long codigo;
	
	@Column(name = "tx_login")
	private String textoLogin;
	
	@Column(name = "tx_nombre")
	private String textoNombre;
	
	@Column(name = "tx_clave")
	private String textoClave;
	
	@Column(name = "tx_loginmd5")
	private String textoLoginMD5;
	
	@Column(name = "tx_clavemd5")
	private String textoClaveMD5;
	
	@Column(name = "fe_creacion")
	private Date fechaCreacion;
	
	@Column(name = "fe_claveexpira")
	private Date fechaClaveExpira;
	
	@Column(name = "fl_eliminado")
	private String flagEliminado;
	
	@Column(name = "nu_fallologin")
	private Long numeroFalloLogin;
	
//	@OneToMany(mappedBy = "seguridadUsuario", fetch = FetchType.LAZY)
//	private List<SCGSolicitudProcesoEntity> solicitudProcesoList;
//	
//	@OneToMany(mappedBy = "seguridadUsuario", fetch = FetchType.LAZY)
//	private List<SCGFlujoProcesoEntity> flujoProcesoList;
//	
//	@OneToMany(mappedBy = "seguridadUsuario", fetch = FetchType.LAZY)
//	private List<SCGResponsabilidadEntity> responsabilidadList;
	
	public Long getCodigo() {			
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getTextoLogin() {
		return textoLogin;
	}
	public void setTextoLogin(String textoLogin) {
		this.textoLogin = textoLogin;
	}
	public String getTextoClave() {
		return textoClave;
	}
	public void setTextoClave(String textoClave) {
		this.textoClave = textoClave;
	}
	public String getTextoLoginMD5() {
		return textoLoginMD5;
	}
	public void setTextoLoginMD5(String textoLoginMD5) {
		this.textoLoginMD5 = textoLoginMD5;
	}
	public String getTextoClaveMD5() {
		return textoClaveMD5;
	}
	public void setTextoClaveMD5(String textoClaveMD5) {
		this.textoClaveMD5 = textoClaveMD5;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaClaveExpira() {
		return fechaClaveExpira;
	}
	public void setFechaClaveExpira(Date fechaClaveExpira) {
		this.fechaClaveExpira = fechaClaveExpira;
	}
	public String getFlagEliminado() {
		return flagEliminado;
	}
	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}
	public Long getNumeroFalloLogin() {
		return numeroFalloLogin;
	}
	public void setNumeroFalloLogin(Long numeroFalloLogin) {
		this.numeroFalloLogin = numeroFalloLogin;
	}
	public String getTextoNombre() {
		return textoNombre;
	}
	public void setTextoNombre(String textoNombre) {
		this.textoNombre = textoNombre;
	}
//	public List<SCGSolicitudProcesoEntity> getSolicitudProcesoList() {
//		return solicitudProcesoList;
//	}
//	public void setSolicitudProcesoList(List<SCGSolicitudProcesoEntity> solicitudProcesoList) {
//		this.solicitudProcesoList = solicitudProcesoList;
//	}
//	public List<SCGFlujoProcesoEntity> getFlujoProcesoList() {
//		return flujoProcesoList;
//	}
//	public void setFlujoProcesoList(List<SCGFlujoProcesoEntity> flujoProcesoList) {
//		this.flujoProcesoList = flujoProcesoList;
//	}
//	public List<SCGResponsabilidadEntity> getResponsabilidadList() {
//		return responsabilidadList;
//	}
//	public void setResponsabilidadList(List<SCGResponsabilidadEntity> responsabilidadList) {
//		this.responsabilidadList = responsabilidadList;
//	}
	
}
