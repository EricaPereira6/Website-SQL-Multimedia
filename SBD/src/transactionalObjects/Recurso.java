package transactionalObjects;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Timestamp;

/*
 * Representação em objeto da tabela recurso
 */
public class Recurso {	
	
	private String titulo;
	private String carregadoPor;
	private String resumo;
	private InputStream ficheiro;
	private InputStream ilustracao;
	private int faixaEtaria;
	private boolean	bloqueado;
	private Timestamp dataPublicacao;
	private float classificacao;
	
	public Recurso(String titulo, String carregadoPor, String resumo, InputStream ficheiro, InputStream ilustracao,
			int faixaEtaria, boolean bloqueado, Timestamp dataPublicacao) {
		super();
		this.titulo = titulo;
		this.carregadoPor = carregadoPor;
		this.resumo = resumo;
		this.ficheiro = ficheiro;
		this.ilustracao = ilustracao;
		this.faixaEtaria = faixaEtaria;
		this.bloqueado = bloqueado;
		this.dataPublicacao = dataPublicacao;
	}
	
	public Recurso(String titulo, String carregadoPor, float classificacao) {
		super();
		this.titulo = titulo;
		this.carregadoPor = carregadoPor;
		this.classificacao = classificacao;
	}
	
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getCarregadoPor() {
		return carregadoPor;
	}
	
	public void setCarregadoPor(String carregadoPor) {
		this.carregadoPor = carregadoPor;
	}
	
	public String getResumo() {
		return resumo;
	}
	
	public void setResumo(String resumo) {
		this.resumo = resumo;
	}
	
	public InputStream getFicheiro() {
		return ficheiro;
	}
	
	public void setFicheiro(InputStream ficheiro) {
		this.ficheiro = ficheiro;
	}
	
	public InputStream getIlustracao() {
		return ilustracao;
	}
	
	public void setIlustracao(InputStream ilustracao) {
		this.ilustracao = ilustracao;
	}
	
	public int getFaixaEtaria() {
		return faixaEtaria;
	}
	
	public void setFaixaEtaria(int faixaEtaria) {
		this.faixaEtaria = faixaEtaria;
	}
	
	public boolean getBloqueado() {
		return bloqueado;
	}
	
	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}
	
	public Timestamp getDataPublicacao() {
		return dataPublicacao;
	}
	
	public void setDataPublicacao(Timestamp dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	
	public float getClassificacao() {
		return classificacao;
	}
	
	public void setClassificacao(float classificacao) {
		this.classificacao = classificacao;
	}
	
	public String toString() {
		return "Recurso [Titulo = " + titulo + ", carregado Por = " + carregadoPor + ", Resumo = " + 
				resumo + ", Ficheiro = " + ficheiro + ", Ilustracao = " + ilustracao + ", Faixa Etaria = "
				+ faixaEtaria + ", Esta bloqueado = " + bloqueado + ", Data da Publicacao = " + 
				dataPublicacao + ", classificacaoMedia = " + classificacao + "]";
	}
}
