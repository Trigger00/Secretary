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
@Table(name = "SCG_ESTUDIANTE_REGISTRO_ADJ", schema = ApplicationConstant.DB_SCHEMA, catalog = ApplicationConstant.DB_CATALOG)
public class SCGEstudianteRegistroAdjuntoEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SCGEstudianteRegistroAdjuntoGNRTOR")
	@TableGenerator(name = "SCGEstudianteRegistroAdjuntoGNRTOR", 
	table = ApplicationConstant.KEY_MANAGER, 
	pkColumnName = ApplicationConstant.KEY_MANAGER_TABLENAME, 
	valueColumnName = ApplicationConstant.KEY_MANAGER_SEQUENCE, 
	pkColumnValue = "SCG_ESTUDIANTE_REGISTRO_ADJ", allocationSize = 1)
	@Column(name = "CO_SCG_ESTUDIANTE_REGISTRO_ADJ")
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_ADJUNTO")
	private SCGAdjuntoEntity adjunto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_ESTUDIANTE_REGISTRO")
	private  SCGEstudianteRegistroEntity estudianteRegistro;
	
	@Column(name = "FL_ELIMINADO")
	private String flagEliminado;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public SCGAdjuntoEntity getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(SCGAdjuntoEntity adjunto) {
		this.adjunto = adjunto;
	}

	
	public SCGEstudianteRegistroEntity getEstudianteRegistro() {
		return estudianteRegistro;
	}

	public void setEstudianteRegistro(SCGEstudianteRegistroEntity estudianteRegistro) {
		this.estudianteRegistro = estudianteRegistro;
	}

	public String getFlagEliminado() {
		return flagEliminado;
	}

	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}
	
}
