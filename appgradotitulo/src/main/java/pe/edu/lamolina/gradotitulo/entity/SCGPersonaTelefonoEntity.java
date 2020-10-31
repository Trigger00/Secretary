package pe.edu.lamolina.gradotitulo.entity;

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

@Entity
@Table(name = "SCG_PERSONA_TELEFONO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)

public class SCGPersonaTelefonoEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGPersonaTelefonoGNRTOR")
	@TableGenerator(name = "SCGPersonaTelefonoGNRTOR", 
				 	table=ApplicationConstant.KEY_MANAGER,
		         	pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
		         	valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE,
					pkColumnValue = "SCG_PERSONA_TELEFONO", 
					allocationSize = 1)
	@Column(name = "CO_SCG_PERSONA_TELEFONO")
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_TIPO_TELEFONO")
	private SCGTipoTelefonoEntity tipoTelefono;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_PERSONA")
	private SCGPersonaEntity persona;

	@Column(name = "TX_NUMEROTELEFONO")
	private String textoNumeroTelefono;

	@Column(name = "FL_ELIMINADO")
	private String flagEliminado;



	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public SCGTipoTelefonoEntity getTipoTelefono() {
		return tipoTelefono;
	}

	public void setTipoTelefono(SCGTipoTelefonoEntity tipoTelefono) {
		this.tipoTelefono = tipoTelefono;
	}

	public SCGPersonaEntity getPersona() {
		return persona;
	}

	public void setPersona(SCGPersonaEntity persona) {
		this.persona = persona;
	}

	public String getTextoNumeroTelefono() {
		return textoNumeroTelefono;
	}

	public void setTextoNumeroTelefono(String textoNumeroTelefono) {
		this.textoNumeroTelefono = textoNumeroTelefono;
	}

	public String getFlagEliminado() {
		return flagEliminado;
	}

	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}

	

}
