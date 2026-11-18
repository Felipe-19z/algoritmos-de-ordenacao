
public class TabelaHash {
    
    public static final int TAMANHO = 11;

    Aluno[] tabela; 

    public TabelaHash() {

        this.tabela = new Aluno[TAMANHO];
    }

    public int hash(int chave) {

        return chave % TAMANHO;

    }

    public void exibir() {

        System.out.println();
        System.out.println( "================ TABELA HASH ================" );

        for(int i = 0; i < TAMANHO; i++) {

      System.out.print( "[" + i + "] " );

      Aluno atual = this.tabela[i];

      if( atual == null ) {
        System.out.print( "vazio" );
      }

      while( atual != null ) {

        System.out.print( "(" + atual.matricula + " - " + atual.nome + " - " + atual.idade + " anos)" );

        if( atual.proximo != null ) {
          System.out.print( " -> " );
        }

        atual = atual.proximo;

      }

      System.out.println();

    }

    System.out.println( "=============================================" );

  }

}
