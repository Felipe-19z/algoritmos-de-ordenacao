
package ed2.projeto.algoritmosordenacao;

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
}
