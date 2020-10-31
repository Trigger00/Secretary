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
@Table(name = "SCG_MOD_GRAD_TIT_GPO", schema = ApplicationConstant.DB_SCHEMA, catalog=ApplicationConstant.DB_CATALOG)
public class SCGModalidadGradoTituloGrupoEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, 
					generator = "SCGModalidadGradoTituloGrupoGNRTOR")
	@TableGenerator(name = "SCGModalidadGradoTituloGrupoGNRTOR", 
					table=ApplicationConstant.KEY_MANAGER,
					pkColumnName=ApplicationConstant.KEY_MANAGER_TABLENAME,
					valueColumnName=ApplicationConstant.KEY_MANAGER_SEQUENCE, 
					pkColumnValue = "SCG_MOD_GRAD_TIT_GPO", 
					allocationSize = 1)
	@Column(name = "CO_SCG_MOD_GRAD_TIT_GPO")
	private Long codigo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_MOD_GRADO_TITULO")
	private SCGModalidadGradoTituloEntity modalidadGradoTitulo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CO_SCG_GRADO_ACADEMICO")
	private SCGGradoAcademicoEntity gradoAcademico;
	
	@Column(name = "FL_ELIMINADO") 
	private String flagEliminado;


	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	
	public SCGModalidadGradoTituloEntity getModalidadGradoTitulo() {
		return modalidadGradoTitulo;
	}

	public void setModalidadGradoTitulo(SCGModalidadGradoTituloEntity modalidadGradoTitulo) {
		this.modalidadGradoTitulo = modalidadGradoTitulo;
	}



	public SCGGradoAcademicoEntity getGradoAcademico() {
		return gradoAcademico;
	}

	public void setGradoAcademico(SCGGradoAcademicoEntity gradoAcademico) {
		this.gradoAcademico = gradoAcademico;
	}

	public String getFlagEliminado() {
		return flagEliminado;
	}

	public void setFlagEliminado(String flagEliminado) {
		this.flagEliminado = flagEliminado;
	}
	
	
	
}


