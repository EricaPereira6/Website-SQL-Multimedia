package transactionalObjects;

public class Artista_Recurso {
	
	private String nomeArtista;
	private String profissao;
	private String tituloRecurso;
	private String utilizadorRecurso;
	
	public Artista_Recurso(String nomeArtista, String profissao, String tituloRecurso,
			String utilizadorRecurso) {
		super();
		
		this.nomeArtista = nomeArtista;
		this.profissao = profissao;
		this.tituloRecurso = tituloRecurso;
		this.utilizadorRecurso = utilizadorRecurso;
	}
	
	public String getNomeArtista() {
		return nomeArtista;
	}
	
	public void setNomeArtista(String nomeArtista) {
		this.nomeArtista = nomeArtista;
	}
	
	public String getProfissao() {
		return profissao;
	}
	
	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}
	
	public String getTituloRecurso() {
		return tituloRecurso;
	}
	
	public void setTituloRecurso(String tituloRecurso) {
		this.tituloRecurso = tituloRecurso;
	}
	
	public String getUtilizadorRecurso() {
		return utilizadorRecurso;
	}
	
	public void setUtilizadorRecurso(String utilizadorRecurso) {
		this.utilizadorRecurso = utilizadorRecurso;
	}
	
	public String toString() {
		return "Artista_Recurso [nomeArtista = " + nomeArtista + ", profissao = " + profissao
				+ ", tituloRecurso = " + tituloRecurso + ", utilizadorRecurso = " + utilizadorRecurso + "]";
	}
}
