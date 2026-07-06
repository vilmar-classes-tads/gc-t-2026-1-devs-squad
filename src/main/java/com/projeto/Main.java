package com.projeto;

import java.time.LocalDate;
import java.util.Scanner;

import com.projeto.model.Edital;
import com.projeto.model.Projeto;
import com.projeto.model.Usuario;
import com.projeto.repository.EditalRepository;
import com.projeto.repository.ProjetoRepository;
import com.projeto.repository.UsuarioRepository;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final UsuarioRepository USUARIO_REPOSITORY = new UsuarioRepository();
    private static final EditalRepository EDITAL_REPOSITORY = new EditalRepository();
    private static final ProjetoRepository PROJETO_REPOSITORY = new ProjetoRepository();

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 3:
                    cadastrarEdital();
                    break;
                case 4:
                    listarEditais();
                    break;
                case 5:
                    submeterProjeto();
                    break;
                case 6:
                    listarProjetos();
                    break;
                case 0:
                    System.out.println("Encerrando sistema.");
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    break;
            }
        } while (opcao != 0);

        SCANNER.close();
    }

    private static void exibirMenu() {
        System.out.println("\n=== Sistema de Editais ===");
        System.out.println("1 - Cadastrar usuario");
        System.out.println("2 - Listar usuarios");
        System.out.println("3 - Cadastrar edital");
        System.out.println("4 - Listar editais");
        System.out.println("5 - Submeter projeto");
        System.out.println("6 - Listar projetos");
        System.out.println("0 - Sair");
    }

    private static void cadastrarUsuario() {
        try {
            Usuario usuario = new Usuario();
            usuario.setNomeCompleto(lerTexto("Nome completo: "));
            usuario.setCpf(lerTexto("CPF: "));
            usuario.setEmailInstitucional(lerTexto("E-mail institucional: "));
            usuario.setSenha(lerTexto("Senha: "));
            usuario.setCampus(lerTexto("Campus: "));
            usuario.setAreaFormacao(lerTexto("Area de formacao: "));
            usuario.setTitulacao(lerTexto("Titulacao: "));
            usuario.setNomeSocial(lerTexto("Nome social: "));
            usuario.setLinkLattes(lerTexto("Link Lattes: "));

            USUARIO_REPOSITORY.salvar(usuario);

            System.out.println("Usuario cadastrado com sucesso.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listarUsuarios() {
        if (USUARIO_REPOSITORY.Listartodos().isEmpty()) {
            System.out.println("Nenhum usuario cadastrado.");
            return;
        }

        for (Usuario usuario : USUARIO_REPOSITORY.Listartodos()) {
            System.out.println(usuario.getNomeCompleto()
                    + " | CPF: " + usuario.getCpf()
                    + " | E-mail: " + usuario.getEmailInstitucional()
                    + " | Perfis: " + usuario.getPerfis());
        }
    }

    private static void cadastrarEdital() {
        try {
            Edital edital = new Edital();
            edital.setTitulo(lerTexto("Titulo do edital: "));
            edital.setNumero(lerTexto("Numero do edital: "));
            edital.setAno(lerTexto("Ano do edital: "));
            edital.setDataInicioSubmissao(lerData("Data inicio submissao (AAAA-MM-DD): "));
            edital.setDataFimSubmissao(lerData("Data fim submissao (AAAA-MM-DD): "));
            edital.setDataInicioAvaliacao(lerData("Data inicio avaliacao (AAAA-MM-DD): "));
            edital.setDataFimAvaliacao(lerData("Data fim avaliacao (AAAA-MM-DD): "));

            EDITAL_REPOSITORY.salvar(edital);

            System.out.println("Edital cadastrado com sucesso.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listarEditais() {
        if (EDITAL_REPOSITORY.Listartodos().isEmpty()) {
            System.out.println("Nenhum edital cadastrado.");
            return;
        }

        for (Edital edital : EDITAL_REPOSITORY.Listartodos()) {
            System.out.println(edital.getNumero()
                    + "/" + edital.getAno()
                    + " | " + edital.getTitulo()
                    + " | Submissao: " + edital.getDataInicioSubmissao()
                    + " ate " + edital.getDataFimSubmissao());
        }
    }

    private static void submeterProjeto() {
        try {
            Projeto projeto = new Projeto();
            projeto.setTitulo(lerTexto("Titulo do projeto: "));
            projeto.setResumo(lerTexto("Resumo: "));
            projeto.setPalavrasChave(lerTexto("Palavras-chave: "));
            projeto.setPublicoAlvo(lerTexto("Publico-alvo: "));
            projeto.setAreaTematica(lerTexto("Area tematica: "));
            projeto.setCampus(lerTexto("Campus: "));
            projeto.setCpfCoordenador(lerTexto("CPF do coordenador: "));
            projeto.setNumeroEdital(lerTexto("Numero do edital: "));

            String ods = lerTexto("ODS selecionadas, separadas por virgula: ");
            adicionarOds(projeto, ods);

            String aceitouTermo = lerTexto("Aceitou o termo de compromisso? (s/n): ");
            projeto.setAceitouTermoCompromisso(aceitouTermo.equalsIgnoreCase("s"));

            PROJETO_REPOSITORY.submeterProjeto(projeto);

            System.out.println("Projeto submetido com sucesso. Status: " + projeto.getStatus());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listarProjetos() {
        if (PROJETO_REPOSITORY.listarTodos().isEmpty()) {
            System.out.println("Nenhum projeto cadastrado.");
            return;
        }

        for (Projeto projeto : PROJETO_REPOSITORY.listarTodos()) {
            System.out.println(projeto.getTitulo()
                    + " | Edital: " + projeto.getNumeroEdital()
                    + " | Coordenador: " + projeto.getCpfCoordenador()
                    + " | Status: " + projeto.getStatus());
        }
    }

    private static void adicionarOds(Projeto projeto, String ods) {
        String[] valores = ods.split(",");

        for (String valor : valores) {
            String odsTratada = valor.trim();

            if (!odsTratada.isEmpty()) {
                projeto.getOdsSelecionadas().add(odsTratada);
            }
        }
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return SCANNER.nextLine();
    }

    private static int lerInteiro(String mensagem) {
        try {
            System.out.print(mensagem);
            return Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static LocalDate lerData(String mensagem) {
        return LocalDate.parse(lerTexto(mensagem));
    }
}
