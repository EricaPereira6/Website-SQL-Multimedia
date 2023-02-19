package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONObject;

import controller.Utilities;
import dataAccessObject.ArtistaDAO;
import dataAccessObject.Artista_RecursoDAO;
import dataAccessObject.FilmeDAO;
import dataAccessObject.FotografiaDAO;
import dataAccessObject.MusicaDAO;
import dataAccessObject.PoemaDAO;
import dataAccessObject.RecursoDAO;
import dataAccessObject.Tipo_RecursoDAO;
import transactionalObjects.Artista;
import transactionalObjects.Artista_Recurso;
import transactionalObjects.Filme;
import transactionalObjects.Fotografia;
import transactionalObjects.Musica;
import transactionalObjects.Poema;
import transactionalObjects.Recurso;
import transactionalObjects.Tipo_Recurso;



@WebServlet("/addResource")
@MultipartConfig
public class addResource extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
//	HttpServletRequest request, HttpServletResponse response;
	
	PrintWriter out;
	JSONObject resultJSON;
	HttpSession sesh;
	
	boolean uploadOk;
	
	Timestamp dataPublicacao; 
	String carregadoPor;
	boolean bloqueado;
	int faixaEtaria;
	InputStream ilustracao = null;
	InputStream ficheiro = null;
	String resumo;
	String titulo;
	
	InputStream foto = null;

	private int anoLancamento;

	public addResource() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		resultJSON = new JSONObject();

		//TODO Conn tem de ficar aqui pa fazer o commit ou rollback no final

		response.setCharacterEncoding(Utilities.charsetISO);
//		response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
//		response.setContentType("application/json");
		out = response.getWriter();
		
		
		sesh = request.getSession();

		if(!Utilities.isValidSession(sesh)) {
			out.print(Utilities.insuccessJSON(Utilities.invalidSession(sesh)).toString());
			return;
		}

		if(!Utilities.isValidType(sesh, 2)) {
			out.print(Utilities.insuccessJSON("Falta privilégios").toString());
			return;
		}
		
		
		//recurso params
		titulo = request.getParameter("titulo");
		carregadoPor = (String) sesh.getAttribute("username");
		String tipo = request.getParameter("tipo");	
		//teste
		carregadoPor = "Ana luis erica";
		if(!Utilities.isValidParameter(titulo) 
				|| !Utilities.isValidParameter(carregadoPor)
				|| !Utilities.isValidParameter(tipo) ) {
			out.print(Utilities.insuccessJSON("Falta parâmetros").toString());
			return;
		}
			
		if(!Utilities.isValidParameter(tipo)) {
			out.print(Utilities.insuccessJSON("Falta parâmetros").toString());
			return;
		}
		
		Part conteudoFilePart = request.getPart("conteudo");

		if(conteudoFilePart.getSize() == 0) {

			out.print(Utilities.insuccessJSON("Falta ficheiro do conteúdo").toString());
			return;
		}

		ficheiro = conteudoFilePart.getInputStream();

		
		//params opcionais
		//como enviar po sql? query sem opcoes?
		Part ilustracaoFilePart = request.getPart("ilustracao");
		
		if(ilustracaoFilePart.getSize() == 0) {

			ilustracao = getServletContext().getResourceAsStream("/photos/" + "black.png");
		}
	
		foto = getServletContext().getResourceAsStream("/photos/" + "defaultArtista.png");
	
		//isEmpty 
		resumo = request.getParameter("resumo");
		String strFaixaEtaria = request.getParameter("faixaEtaria");
		
		if(Utilities.isValidParameter(strFaixaEtaria)) {
			
			faixaEtaria = Integer.parseInt(strFaixaEtaria);
		}
				
		
		//dataPublicacao = new Timestamp(Time.now());
		bloqueado=true;		

		try {
			
			//ver se titulo já existe? bd já deve fazer isso
			if(tipo.equalsIgnoreCase("Fotografia")) {
				
				String nomeFotografo = request.getParameter("nomeFotografo");
				String profissao = "Fotógrafo";
				
				getArtista(nomeFotografo);
				getRecurso();
				getArtistaWithProfissao(nomeFotografo, profissao);
				getRecursoType(tipo);
				getRecursoEspecifico(tipo);

				
				//getArtista
					//if null addArtista
				//getRecurso
					//if null addRecurso
				//getArtistaByProfissao
					//if null addArtistaWithProfissao
				//getRecursoType
					//if null addRecursoType
				//getRecursoEspecifico
					//if null addRecursoEspecifico	
				
				
				//getArtistaRecurso
					//if null addArtistaRecurso	
			}
			
			
			if(tipo.equalsIgnoreCase(("Filme"))){
				
				//TODO calendário? no registo dos users
				String strAnoLancamento = request.getParameter("anoLancamento");
				anoLancamento = Integer.parseInt(strAnoLancamento);
				
				//TODO ciclo for para atores e diretores
				String ator = request.getParameter("ator");
				String diretor = request.getParameter("diretor");
				
				String profissaoAtor = "Ator";
				String profissaoDiretor = "Diretor";
				
				getArtista(ator);
				getArtista(diretor);
				getRecurso();
				getArtistaWithProfissao(ator, profissaoAtor);
				getArtistaWithProfissao(ator, profissaoDiretor);
				getRecursoType(tipo);
				getRecursoEspecifico(tipo);
				
				
				
			}
			
			if(tipo.equalsIgnoreCase(("Poema"))){
			
				String nomeEscritor = request.getParameter("nomeEscritor");
				String profissao = "Poeta";
				
				getArtista(nomeEscritor);
				getRecurso();
				getArtistaWithProfissao(nomeEscritor, profissao);
				getRecursoType(tipo);
				getRecursoEspecifico(tipo);
				
				
			}
			if(tipo.equalsIgnoreCase(("Música"))){
				
				String cantor = request.getParameter("nomeCantor");
				String compositor = request.getParameter("nomeCompositor");
				
				String profissaoCantor = "Cantor";
				String profissaoCompositor = "Compositor";
				
				getArtista(cantor);
				getArtista(compositor);
				getRecurso();
				getArtistaWithProfissao(cantor, profissaoCantor);
				getArtistaWithProfissao(compositor, profissaoCompositor);
				getRecursoType(tipo);
				getRecursoEspecifico(tipo);
			}
		
		}catch (Exception e) {
			
			e.printStackTrace();
			out.write(Utilities.errorJSON(e, "SQL").toString());
			out.write("Pode voltar à página inicial :)");
			return;	
		}
		
		
		out.write("Adicionado com sucesso \n");
		out.write("Pode voltar à página inicial :)");
		return;	
	}
	
	private void addRecursoEspecifico(String tipo) throws SQLException {
		
		boolean isResourceSaved = false;
			
			if(tipo.equalsIgnoreCase(("Poema"))){
				
				isResourceSaved = PoemaDAO.criarPoema(titulo, carregadoPor);
			}
				
			else if(tipo.equalsIgnoreCase(("Fotografia"))){
				
				isResourceSaved = FotografiaDAO.criarFotografia(titulo, carregadoPor);
			}
			
			else if(tipo.equalsIgnoreCase(("Música"))){
				
				isResourceSaved = MusicaDAO.criarMusica(titulo, carregadoPor);
			}
			
			else if(tipo.equalsIgnoreCase(("Filme"))){
				
				isResourceSaved = FilmeDAO.criarFilme(titulo, carregadoPor, anoLancamento);

			}
			
			else {
				
				throw new SQLException("Tipo não reconhecido: " + tipo);

			}
			
			
			if(!isResourceSaved) {
				
				throw new SQLException("Não foi possível criar o recurso na tabela do recurso específico");
			}
	}
	
	
	private void getRecursoEspecifico(String tipo) throws SQLException {
		
		Poema poema = null;
		Fotografia fotografia = null;
		Musica musica = null;
		Filme filme = null;
		
		if(tipo.equalsIgnoreCase(("Poema"))){
			
			poema = PoemaDAO.selectPoemaByTitleAndUser(titulo, carregadoPor);
		}
			
		else if(tipo.equalsIgnoreCase(("Fotografia"))){
			
			fotografia = FotografiaDAO.selectFotografiaByTitleAndUser(titulo, carregadoPor);		
		}
		
		else if(tipo.equalsIgnoreCase(("Música"))){
			
			musica = MusicaDAO.selectMusicaByTitleAndUser(titulo, carregadoPor);
		}
		
		else if(tipo.equalsIgnoreCase(("Filme"))){
			
			filme = FilmeDAO.selectFilmeByTitleAndUser(titulo, carregadoPor);

		}
		
		else {
			
			throw new SQLException("Tipo não reconhecido: " + tipo);

		}
		
		
		if(fotografia == null && poema == null && musica == null && filme == null) {
			
			addRecursoEspecifico(tipo);
		}
		
		else {

			throw new SQLException("Recurso Específico já existe.");

		}
		
	}
	
	//OU GET FAZ ADD
	//OU GET DEVOLVE OBJETO E NULL VE SE FORA
	private void getRecursoType(String type) throws SQLException {

		Tipo_Recurso tipoRecurso;

		tipoRecurso = Tipo_RecursoDAO.getTipoRecursoByTitleAndUser(titulo, carregadoPor);

		if(tipoRecurso == null) {

			addRecursoType(type);
		}
		
//		else {
//			
//			throw new SQLException("Tipo de Recurso com este titulo e utilizador já existe.");
//		}

	}
	
	private void addRecursoType(String tipo) throws SQLException {

		boolean isResourceTypeCreated = Tipo_RecursoDAO.criarTipo_Recurso(titulo, carregadoPor, tipo);

		if(!isResourceTypeCreated) {

			throw new SQLException("Não foi possível criar o tipo para o recurso");
		}
	}
	
	private void getRecurso() throws SQLException {
		

		Recurso r = RecursoDAO.getRecursoByTituloAndCarregadoPor(titulo, carregadoPor);

		if(r == null) {
		
			addRecurso();
		}
		
		else {
			
			throw new SQLException("Recurso com este titulo e utilizador já existe.");

		}
		
	}
	
	private void addRecurso() throws SQLException {

		boolean isRecursoCreated = RecursoDAO.criarRecurso(titulo, carregadoPor, resumo, ficheiro, ilustracao, faixaEtaria, bloqueado, dataPublicacao);

		if(!isRecursoCreated) {

			throw new SQLException("Não foi possível criar o recurso");
		}
	}

	

	private void getArtistaWithProfissao(String nomeArtista, String profissao) throws SQLException {

		//ver se artista com esta profissao existe para adicionar
		Artista_Recurso artistaWithProfissao;

			//check se pode mandar ex caso não haja
			artistaWithProfissao = Artista_RecursoDAO.getArtista_RecursoByAll(nomeArtista, profissao, titulo, carregadoPor);

			
			if(artistaWithProfissao == null) {

				addArtistaWithProfissao(nomeArtista, profissao);
			}
			
			//do nothin
			else {
				
				
			}
	}
	
	private void addArtistaWithProfissao(String nomeArtista, String profissao) throws SQLException {

		boolean artistaWithProfissao;

			artistaWithProfissao = Artista_RecursoDAO.criarArtista_Recurso(nomeArtista, profissao, titulo, carregadoPor);

			if(!artistaWithProfissao) {
				
				throw new SQLException("Erro na criação do Artista no Recurso");
			}
			
			//do nothin
			else {
				
			}
	}

	private void getArtista(String nomeArtista) throws SQLException {

		//ver se artista com esta profissao existe para adicionar
		Artista artista;

			//check se pode mandar ex caso não haja
			artista = ArtistaDAO.getArtistaByName(nomeArtista);

			if(artista == null) {

				addArtista(nomeArtista);
			}
			
			//ok ja existe passa a frente
			else {
				
			}
	}

	private void addArtista(String nomeArtista) throws SQLException {

		//vai buscar a foto default ao server
		if(foto == null) {

			File defaultIlustracao = new File(getServletContext().getContextPath() + File.separator + "photos" + File.separator + "black.png");
			try {

				foto = new FileInputStream(defaultIlustracao);

			} catch (FileNotFoundException e) {

				throw new SQLException("Foto default não encontrada");
			}
		}

		//por artista na bd 
		boolean isCreated = ArtistaDAO.criarArtista(nomeArtista, foto);

		if(!isCreated) {

			throw new SQLException("Erro na criação do Artista");
		}
	}
	
	
	
	
	
	
	

//	private void addFotografia(String nomeFotografo) {
//
//		//ver se artista com esta profissao existe para adicionar
//		Artista fotografo;
//		try {
//
//			//check se pode mandar ex caso não haja
//			fotografo = ArtistaDAO.getArtistaByName(nomeFotografo);
//
//
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//
//			resultJSON.put("success", false);
//			resultJSON.put("error", e.getMessage());
//			out.write(resultJSON.toString());
//			out.flush();
//
//			return;			
//		}
//
//		//Não existe, tem de adicionar
//		if(fotografo == null) {
//
//			String nomeArtista = nomeFotografo;
//
//			if(foto == null) {
//
//				File defaultIlustracao = new File("" + File.separator + "photos" + File.separator + "black.png");
//				try {
//					ilustracao = new FileInputStream(defaultIlustracao);
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				//mudar blobs
//				//foto = "C:\\default.png";
//			}
//
//			//por artista na bd 
//			try {
//				ArtistaDAO.criarArtista(nomeArtista, foto);
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//
//		if(fotografo != null) {
//
//			//por recurso
//
//			boolean isRecursoSaved = false;
//
//			try {
//
//				isRecursoSaved = RecursoDAO.criarRecurso(titulo, carregadoPor, resumo, 
//						ficheiro, ilustracao, faixaEtaria, bloqueado, dataPublicacao);
//
//			} catch (SQLException e) {
//
//				e.printStackTrace();
//
//				resultJSON.put("success", false);
//				resultJSON.put("error", e.getMessage());
//				resultJSON.put("errorType", "SQL");
//				out.write(resultJSON.toString());
//				out.flush();
//				return;
//			}
//
//			if(isRecursoSaved) {
//
//				//por foto
//
//				boolean isFotografiaSaved;
//
//				try {
//
//					isFotografiaSaved = FotografiaDAO.criarFotografia(titulo, carregadoPor);
//
//				} catch (SQLException e) {
//
//					e.printStackTrace();
//
//					resultJSON.put("success", false);
//					resultJSON.put("error", e.getMessage());
//					resultJSON.put("errorType", "SQL");
//					out.write(resultJSON.toString());
//					out.flush();
//					return;
//				}
//
//				if(isFotografiaSaved) {
//
//					//criar rel artista_recurso
//
//					String nomeArtista = nomeFotografo;
//					String tituloRecurso=titulo;
//					String profissao = "fot�grafo";
//					String utilizadorRecurso = carregadoPor;
//
//					try {
//
//						Artista_RecursoDAO.criarArtista_Recurso(nomeArtista, profissao, tituloRecurso, utilizadorRecurso);
//
//					} catch (SQLException e) {
//
//						e.printStackTrace();
//
//						//TODO Conn rollback
//						resultJSON.put("success", false);
//						resultJSON.put("error", e.getMessage());
//						resultJSON.put("errorType", "SQL");
//						out.write(resultJSON.toString());
//						out.flush();
//						return;
//					}
//
//					boolean isArtistaRecursoAssociated = true;
//
//					if(isArtistaRecursoAssociated) {
//
//						//TODO Conn commit 
//						resultJSON.put("success", true);
//						out.write(resultJSON.toString());
//						out.flush();
//						return;
//					}
//				}
//			}			
//		}
//	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}

}