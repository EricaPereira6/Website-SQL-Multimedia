package transactionalObjects;

public class Nacionalidade {

	private int codigo;
	private String nacionalidade;
	
	public Nacionalidade(int codigo, String nacionalidade) {
		super();
		this.codigo = codigo;
		this.nacionalidade = nacionalidade;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public String getNacionalidade() {
		return nacionalidade;
	}
	
	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}
	
	public String toString() {
		return "Nacionalidade [Código = " + codigo + ", Nacionalidade = " + nacionalidade + "]";
	}
}
