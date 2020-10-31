package pe.edu.lamolina.tramitedocumentario.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import pe.edu.lamolina.constant.ApplicationConstant;
import pe.edu.lamolina.security.entity.SeguridadUsuarioEntity;

@Entity
@Table(name = "SCG_RESPONSABILIDAD", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGResponsabilidadEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGResponsabilidadGNRTOR")
	@TableGenerator(name = "SCGResponsabilidadGNRTOR", 
            		table=ApplicationConstant.KEY_MANAGER,
            		pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
            		valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_RESPONSABILIDAD", 
					allocationSize = 1)
	@Column(name = "CO_SCG_RESPONSABILIDAD")
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SG_USUARIO")
	private SeguridadUsuarioEntity seguridadUsuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_RESPONSABLE")
	private SCGResponsableEntity responsable;

	@Column(name = "TX_NOMBRE") 
	private String textoNombre;
	
	@Column(name = "TX_DETALLE") 
	private String textoDetalle;
	
	@Column(name = "FL_ACTIVO")
	private String flagActivo;
	
	@Column(name = "FL_ELIMINADO")
	private String flagEliminado;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public SeguridadUsuarioEntity getSeguridadUsuario() {
		return seguridadUsuario;
	}

	public void setSeguridadUsuario(SeguridadUsuarioEntity seguridadUsuario) {
		this.seguridadUsuario = seguridadUsuario;
	}

	public SCGResponsableEntity getResponsable() {
		return responsable;
	}

	public void setResponsable(SCGResponsableEntity responsable) {
		this.responsable = responsable;
	}

	public String getTextoNombre() {
		return textoNombre;
	}

	public void setTextoNombre(String textoNombre) {
		this.textoNombre = textoNombre;
	}

	public String getTextoDetalle() {
		return textoDetalle;
	}

	public void setTextoDetalle(String textoDetalle) {
		this.textoDetalle = textoDetalle;
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

	
}
