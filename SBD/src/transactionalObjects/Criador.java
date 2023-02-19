package transactionalObjects;

public class Criador {

	private String nomeUtilizador;
	private String palavraPasse;
	private boolean bloqueado;
	private float pontosReputacao;
	
	public Criador(String nomeUtilizador, String palavraPasse, boolean bloqueado, float pontosReputacao) {
		super();
		this.nomeUtilizador = nomeUtilizador;
		this.palavraPasse = palavraPasse;
		this.bloqueado = bloqueado;
		this.pontosReputacao = pontosReputacao;
	}

	public String getNomeUtilizador() {
		return nomeUtilizador;
	}
	public void setNomeUtilizador(String nomeUtilizador) {
		this.nomeUtilizador = nomeUtilizador;
	}
	public String getPalavraPasse() {
		return palavraPasse;
	}
	public void setPalavraPasse(String palavraPasse) {
		this.palavraPasse = palavraPasse;
	}
	public boolean getBloqueado() {
		return bloqueado;
	}
	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}
	public float getPontosReputacao() {
		return pontosReputacao;
	}
	public void setPontosReputacao(int pontosReputacao) {
		this.pontosReputacao = pontosReputacao;
	}

	@Override
	public String toString() {
		return "Criador [Nome Utilizador = " + nomeUtilizador + ", Palavra-Passe = " + palavraPasse + ", Está Bloqueado = " 
	+ bloqueado + ", Pontos de Reputação = " + pontosReputacao + "]";
	}
}
