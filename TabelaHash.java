
package ed2.projeto.algoritmosordenacao;

import java.util.Scanner;

public class TabelaHash {

    public static final int TAMANHO = 11;

    Aluno[] tabela;

    public TabelaHash() {
        this.tabela = new Aluno[TAMANHO];
    }

    public int hash(int chave) {
        return chave % TAMANHO;
    }

    public boolean inserir(int matricula, String nome, int idade) {

        if (matricula < 0) {
            return false;
        }

        int posicao = hash(matricula);

        Aluno novo = new Aluno(matricula, nome, idade);

        if (tabela[posicao] == null) {
            tabela[posicao] = novo;
            return true;
        }

        Aluno atual = tabela[posicao];

        while (atual != null) {

            if (atual.matricula == matricula) {
                return false;
            }

            if (atual.proximo == null) {
                break;
            }

            atual = atual.proximo;
        }

        atual.proximo = novo;

        return true;
    }

    public Aluno buscar(int matricula) {

        if (matricula < 0) {
            return null;
        }

        int posicao = hash(matricula);

        Aluno atual = tabela[posicao];

        while (atual != null) {

            if (atual.matricula == matricula) {
                return atual;
            }

            atual = atual.proximo;
        }

        return null;
    }

    public boolean remover(int matricula) {

        if (matricula < 0) {
            return false;
        }

        int posicao = hash(matricula);

        Aluno atual = tabela[posicao];
        Aluno anterior = null;

        while (atual != null) {

            if (atual.matricula == matricula) {

                if (anterior == null) {
                    tabela[posicao] = atual.proximo;
                } else {
                    anterior.proximo = atual.proximo;
                }

                return true;
            }

            anterior = atual;
            atual = atual.proximo;
        }

        return false;
    }

    public void exibir() {

        System.out.println();
        System.out.println("================ TABELA HASH ================");

        for (int i = 0; i < TAMANHO; i++) {

            System.out.print("[" + i + "] ");

            Aluno atual = tabela[i];

            if (atual == null) {
                System.out.print("vazio");
            }

            while (atual != null) {

                System.out.print("(" + atual.matricula + " - "
                        + atual.nome + " - " + atual.idade + " anos)");

                if (atual.proximo != null) {
                    System.out.print(" -> ");
                }

                atual = atual.proximo;
            }

            System.out.println();
        }

        System.out.println("=============================================");
    }

    public static void main(String[] args) {
        TabelaHash tabela = new TabelaHash();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println();
                System.out.println("===== MENU TABELA HASH =====");
                System.out.println("1 - Inserir novo aluno");
                System.out.println("2 - Buscar aluno");
                System.out.println("3 - Remover aluno");
                System.out.println("4 - Exibir tabela hash");
                System.out.println("5 - Encerrar sistema");

                Integer opcao = lerInteiro(scanner, "Escolha uma opcao: ");
                if (opcao == null) {
                    return;
                }

                switch (opcao) {
                    case 1:
                        Integer matricula = lerInteiro(scanner, "Digite a matricula: ");
                        if (matricula == null) {
                            return;
                        }

                        System.out.print("Digite o nome: ");
                        if (!scanner.hasNextLine()) {
                            return;
                        }
                        String nome = scanner.nextLine();

                        Integer idade = lerInteiro(scanner, "Digite a idade: ");
                        if (idade == null) {
                            return;
                        }

                        if (tabela.inserir(matricula, nome, idade)) {
                            System.out.println("Aluno inserido com sucesso.");
                        } else {
                            System.out.println("Nao foi possivel inserir o aluno.");
                        }
                        break;
                    case 2:
                        Integer matriculaBusca = lerInteiro(scanner, "Digite a matricula do aluno: ");
                        if (matriculaBusca == null) {
                            return;
                        }

                        Aluno aluno = tabela.buscar(matriculaBusca);
                        if (aluno == null) {
                            System.out.println("Aluno nao encontrado.");
                        } else {
                            System.out.println("Aluno encontrado: "
                                    + aluno.matricula + " - "
                                    + aluno.nome + " - " + aluno.idade + " anos");
                        }
                        break;
                    case 3:
                        Integer matriculaRemocao = lerInteiro(scanner, "Digite a matricula do aluno: ");
                        if (matriculaRemocao == null) {
                            return;
                        }

                        if (tabela.remover(matriculaRemocao)) {
                            System.out.println("Aluno removido com sucesso.");
                        } else {
                            System.out.println("Aluno nao encontrado.");
                        }
                        break;
                    case 4:
                        tabela.exibir();
                        break;
                    case 5:
                        System.out.println("Sistema encerrado.");
                        return;
                    default:
                        System.out.println("Opcao invalida.");
                }
            }
        }
    }

    private static Integer lerInteiro(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            if (!scanner.hasNextLine()) {
                return null;
            }

            String entrada = scanner.nextLine();
            try {
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Digite um numero inteiro.");
            }
        }
    }
}
