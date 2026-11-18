package ed2.projeto.algoritmosordenacao;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Classe principal da Parte 1 do projeto de Estrutura de Dados II.
 * Carrega o arquivo de entrada na memória ao iniciar e apresenta um menu interativo
 * para executar e comparar o tempo real dos algoritmos de ordenação sobre cópias
 * independentes do vetor original.
 *
 * @author Equipe ED2 - Integrante 2
 */
public class Main {

  private static final String ARQUIVO_ENTRADA = "entrada.txt";
  private static final int QUANTIDADE_PADRAO = 50000;
  private static final int LIMITE_SUPERIOR_PADRAO = 100000;
  private static final int LIMITE_EXIBICAO = 100;

  public static void main(String[] args) {

    Scanner scanner = new Scanner( System.in );

    try {
      int[] vetorOriginal = inicializarDadosEntrada();

      boolean executando = true;

      while( executando ) {
        exibirMenu( vetorOriginal.length );
        int opcao = lerOpcaoInteira( scanner );

        switch( opcao ) {
          case 1:
          case 2:
          case 3:
          case 4:
          case 5:
            IAlgoritmoOrdenacao algoritmo = criarAlgoritmo( opcao );
            executarOrdenacao( algoritmo, vetorOriginal );
            break;

          case 6:
            vetorOriginal = regenerarArquivoEntrada( scanner, vetorOriginal );
            break;

          case 0:
            System.out.println( "\nEncerrando o programa. Até logo!" );
            executando = false;
            break;

          default:
            System.out.println( "\nOpção inválida. Escolha um número entre 0 e 6." );
            break;
        }
      }
    } catch( IOException e ) {
      System.err.println( "Erro ao acessar o arquivo '" + ARQUIVO_ENTRADA + "': " + e.getMessage() );
    } finally {
      scanner.close();
    }

  }

  /**
   * Garante a existência do arquivo {@code entrada.txt} (gerando-o caso não exista)
   * e realiza a leitura inicial dos valores para a memória.
   *
   * @return vetor {@code int[]} carregado a partir de {@code entrada.txt}
   * @throws IOException caso ocorra erro de leitura ou escrita em disco
   */
  private static int[] inicializarDadosEntrada() throws IOException {

    File arquivo = new File( ARQUIVO_ENTRADA );

    if( !arquivo.exists() ) {
      System.out.println( "Arquivo '" + ARQUIVO_ENTRADA + "' não encontrado." );
      System.out.println( "Gerando automaticamente " + QUANTIDADE_PADRAO + " números inteiros aleatórios..." );
      Utils.gerarArquivoEntrada( ARQUIVO_ENTRADA, QUANTIDADE_PADRAO, LIMITE_SUPERIOR_PADRAO );
    }

    System.out.println( "Carregando '" + ARQUIVO_ENTRADA + "' para a memória..." );
    int[] vetorOriginal = Utils.lerArquivoEntrada( ARQUIVO_ENTRADA );
    System.out.println( "Carga concluída com sucesso: " + vetorOriginal.length + " elementos carregados." );

    return vetorOriginal;

  }

  /**
   * Exibe o menu principal no console.
   *
   * @param quantidadeElementos tamanho atual do vetor carregado na memória
   */
  private static void exibirMenu(int quantidadeElementos) {

    System.out.println();
    System.out.println( "============================================================" );
    System.out.println( "  ED2 - COMPARAÇÃO DE ALGORITMOS DE ORDENAÇÃO (PARTE 1)" );
    System.out.println( "  Arquivo em memória: " + ARQUIVO_ENTRADA + " (" + quantidadeElementos + " elementos)" );
    System.out.println( "============================================================" );
    System.out.println( "  1 - BubbleSort" );
    System.out.println( "  2 - SelectionSort" );
    System.out.println( "  3 - InsertionSort" );
    System.out.println( "  4 - MergeSort" );
    System.out.println( "  5 - QuickSort" );
    System.out.println( "  6 - Gerar novo arquivo entrada.txt" );
    System.out.println( "  0 - Sair" );
    System.out.println( "------------------------------------------------------------" );
    System.out.print( "Escolha uma opção: " );

  }

  /**
   * Instancia o algoritmo correspondente à opção escolhida no menu.
   *
   * @param opcao código numérico do algoritmo (1 a 5)
   * @return instância que implementa {@link IAlgoritmoOrdenacao}
   */
  private static IAlgoritmoOrdenacao criarAlgoritmo(int opcao) {

    switch( opcao ) {
      case 1:
        return new BubbleSort();
      case 2:
        return new SelectionSort();
      case 3:
        return new InsertionSort();
      case 4:
        return new MergeSort();
      case 5:
        return new QuickSort();
      default:
        throw new IllegalArgumentException( "Opção de algoritmo inválida: " + opcao );
    }

  }

  /**
   * Executa o algoritmo selecionado sobre uma cópia do vetor original,
   * exibindo os dados antes e depois (limitados aos 100 primeiros elementos)
   * e o tempo real gasto na ordenação.
   *
   * @param algoritmo instância do algoritmo de ordenação
   * @param vetorOriginal vetor base carregado na memória (permanece inalterado)
   */
  private static void executarOrdenacao(IAlgoritmoOrdenacao algoritmo, int[] vetorOriginal) {

    // Copia o vetor original para garantir que futuras execuções recebam a mesma entrada
    int[] vetorCopia = Utils.copiarVetor( vetorOriginal );

    System.out.println();
    System.out.println( ">>> Algoritmo selecionado: " + algoritmo.toString() );
    System.out.println( "Dados de entrada (antes da ordenação):" );
    Utils.imprimirVetor( vetorCopia, LIMITE_EXIBICAO );

    System.out.println( "Ordenando " + vetorCopia.length + " elementos..." );
    long inicioNano = System.nanoTime();
    algoritmo.ordenarVetor( vetorCopia );
    long fimNano = System.nanoTime();

    long duracaoNano = fimNano - inicioNano;
    double duracaoMs = duracaoNano / 1_000_000.0;
    double duracaoSeg = duracaoNano / 1_000_000_000.0;

    System.out.println( "Dados de saída (após a ordenação):" );
    Utils.imprimirVetor( vetorCopia, LIMITE_EXIBICAO );

    System.out.printf(
        Locale.US,
        "Tempo real de execução (%s): %.3f ms (%.6f s)%n",
        algoritmo.toString(),
        duracaoMs,
        duracaoSeg );
    System.out.println( "Validação da ordenação: " + ( estaOrdenado( vetorCopia ) ? "OK (ordem crescente)" : "FALHA" ) );

  }

  /**
   * Permite ao usuário gerar um novo arquivo {@code entrada.txt} com a quantidade
   * desejada de números inteiros aleatórios e recarregá-lo na memória.
   *
   * @param scanner leitor de entrada do console
   * @param vetorAtual vetor atualmente carregado (mantido em caso de cancelamento/erro)
   * @return novo vetor carregado ou o vetor anterior em caso de entrada inválida
   */
  private static int[] regenerarArquivoEntrada(Scanner scanner, int[] vetorAtual) {

    System.out.print( "\nInforme a quantidade de números para gerar (ex.: 10000 ou 50000): " );
    int quantidade = lerOpcaoInteira( scanner );

    if( quantidade <= 0 ) {
      System.out.println( "Quantidade inválida. Mantendo o vetor atual em memória." );
      return vetorAtual;
    }

    int limiteSuperior = Math.max( LIMITE_SUPERIOR_PADRAO, quantidade * 2 );

    try {
      Utils.gerarArquivoEntrada( ARQUIVO_ENTRADA, quantidade, limiteSuperior );
      int[] novoVetor = Utils.lerArquivoEntrada( ARQUIVO_ENTRADA );
      System.out.println( "Novo arquivo '" + ARQUIVO_ENTRADA + "' gerado e carregado (" + novoVetor.length + " elementos)." );
      return novoVetor;
    } catch( IOException e ) {
      System.err.println( "Não foi possível regenerar o arquivo: " + e.getMessage() );
      return vetorAtual;
    }

  }

  /**
   * Lê uma linha do console e tenta convertê-la para inteiro sem propagar exceções.
   *
   * @param scanner leitor de entrada do console
   * @return valor inteiro lido ou {@code -1} se a entrada não for numérica
   */
  private static int lerOpcaoInteira(Scanner scanner) {

    if( !scanner.hasNextLine() ) {
      return 0;
    }

    String linha = scanner.nextLine().trim();

    try {
      return Integer.parseInt( linha );
    } catch( NumberFormatException e ) {
      return -1;
    }

  }

  /**
   * Verifica se o vetor está devidamente ordenado em ordem não decrescente.
   *
   * @param vetor arranjo a ser verificado
   * @return {@code true} se ordenado corretamente; {@code false} caso contrário
   */
  private static boolean estaOrdenado(int[] vetor) {

    for(int i = 0; i < vetor.length - 1; i++) {
      if( vetor[i] > vetor[i + 1] ) {
        return false;
      }
    }

    return true;

  }

}
