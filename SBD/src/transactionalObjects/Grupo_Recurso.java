package transactionalObjects;

public class Grupo_Recurso {
	

	private int idGrupo;
	private String tituloRecurso;
	private String carregadoPor;
	
	public Grupo_Recurso(int idGrupo, String tituloRecurso, String carregadoPor) {
		super();
		
		this.idGrupo = idGrupo;
		this.tituloRecurso = tituloRecurso;
		this.carregadoPor = carregadoPor;
	}
	
	public String gettituloRecurso() {
		return tituloRecurso;
	}
	
	public void settituloRecurso(String tituloRecurso) {
		this.tituloRecurso = tituloRecurso;
	}
	
	public String getCarregadoPor() {
		return carregadoPor;
	}
	
	public void setCarregadoPor(String carregadoPor) {
		this.carregadoPor = carregadoPor;
	}
	
	public int getIdGrupo() {
		return idGrupo;
	}
	
	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}
	
	public String toString() {
		return "Tipo_Recurso [idgrupo = " + idGrupo + ", tituloRecurso = " + tituloRecurso + ", carregadoPor = " + carregadoPor + "]";
	}

}
