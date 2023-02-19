package transactionalObjects;

import java.sql.Timestamp;

public class Comentario {

	private String utilizadorComentario;
	private String tituloRecurso;
	private String utilizadorRecurso;
	private int classificacao;
	private String conteudo;
	private Timestamp dataComentario;
	
	public Comentario(String utilizadorComentario, String tituloRecurso, String utilizadorRecurso,
			int classificacao, String conteudo, Timestamp dataComentario) {
		super();
		this.utilizadorComentario = utilizadorComentario;
		this.tituloRecurso = tituloRecurso;
		this.utilizadorRecurso = utilizadorRecurso;
		this.classificacao = classificacao;
		this.conteudo = conteudo;
		this.dataComentario = dataComentario;
	}
	
	public String getUtilizadorComentario() {
		return utilizadorComentario;
	}
	
	public void setUtilizadorComentario(String utilizadorComentario) {
		this.utilizadorComentario = utilizadorComentario;
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
	
	public int getClassificacao() {
		return classificacao;
	}
	
	public void setClassificacao(int classificacao) {
		this.classificacao = classificacao;
	}
	
	public String getConteudo() {
		return conteudo;
	}
	
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	public Timestamp getDataComentario() {
		return dataComentario;
	}
	
	public void setDataComentario(Timestamp dataComentario) {
		this.dataComentario = dataComentario;
	}
	
	public String toString() {
		return "Comentario [Utilizador do Comentario = " + utilizadorComentario +
				", Titulo do Recurso = " + tituloRecurso + ", Utilizador do Recurso = " + utilizadorRecurso
				+ ", Classificacao = " + classificacao + ", Conteudo = " + conteudo + ", Data do Comentario = " 
				+ dataComentario + "]";
	}
}
