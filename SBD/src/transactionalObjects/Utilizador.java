package transactionalObjects;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class Utilizador {

	private String nomeUtilizador;
	private String email;
	private Date dataNascimento;
	private String nacionalidade;
	
	public Utilizador(String nomeUtilizador, String email, Date dataNascimento, String nacionalidade) {
		super();
		this.nomeUtilizador = nomeUtilizador;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.nacionalidade = nacionalidade;
	}

	public String getNomeUtilizador() {
		return nomeUtilizador;
	}
	public void setNomeUtilizador(String nomeUtilizador) {
		this.nomeUtilizador = nomeUtilizador;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getNacionalidade() {
		return nacionalidade;
	}
	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}
	

	public int getAge() {
		LocalDate today = LocalDate.now();                         
		LocalDate birthday = dataNascimento.toLocalDate(); 
		 
		Period period = Period.between(birthday, today);
		 
		return period.getYears();
	}
	
	@Override
	public String toString() {
		return "Utilizador [Nome Utilizador = " + nomeUtilizador + ", Email = " + email + ", Data de Nascimento = "
				+ dataNascimento + ", Nacionalidade = " + nacionalidade + "]";
	}
	
	public static void main(String[] args) {
		String s = "1997-01-08";  
		Date d = Date.valueOf(s); 
		Utilizador u = new Utilizador("oi", "", d, "");
		System.out.println(u.getAge());
	}
}
