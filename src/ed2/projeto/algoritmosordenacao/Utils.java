
package ed2.projeto.algoritmosordenacao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Classe utilitária com métodos auxiliares para geração, cópia, exibição
 * e persistência em arquivo de vetores de inteiros.
 *
 * @author Hilario Tomaz Alves de Oliveira
 *
 */

public class Utils {

  private static final Pattern SEPARADOR_TOKENS = Pattern.compile("[\\s,;]+");

  private Utils() {

  }
  
  // Métodos
  
  public static void imprimirVetor(int[] vetor) {
	
    if( vetor == null ) {
      System.out.print( "  Vetor = null" );
      return;
    }

	System.out.print( "  Vetor = {" );
	  
    for(int i = 0; i < vetor.length; i++) {
    	
      System.out.print( vetor[i] );
      
      if( i < ( vetor.length - 1 ) ) {
    	  
        System.out.print( ", " );
    	  
      }
      
    }
    
    System.out.print( "}" );
    
  }

  /**
   * Exibe os elementos do vetor no console limitando a impressão à quantidade
   * máxima informada (ex.: os 100 primeiros números quando o volume for grande).
   *
   * @param vetor arranjo de inteiros a ser exibido
   * @param limiteExibicao quantidade máxima de elementos a exibir
   */
  public static void imprimirVetor(int[] vetor, int limiteExibicao) {

    if( vetor == null ) {
      System.out.println( "  Vetor = null" );
      return;
    }

    if( limiteExibicao <= 0 ) {
      System.out.println( "  Vetor = {} (0 elementos exibidos de " + vetor.length + ")" );
      return;
    }

    int quantidadeExibir = Math.min( vetor.length, limiteExibicao );

    System.out.print( "  Vetor = {" );

    for(int i = 0; i < quantidadeExibir; i++) {

      System.out.print( vetor[i] );

      if( i < ( quantidadeExibir - 1 ) ) {
        System.out.print( ", " );
      }

    }

    if( vetor.length > quantidadeExibir ) {
      System.out.println( ", ...} (exibindo os " + quantidadeExibir + " primeiros de " + vetor.length + " elementos)" );
    } else {
      System.out.println( "} (total: " + vetor.length + " elementos)" );
    }

  }

  public static int[] gerarVetor(int tamanhoVetor, int limiteSuperior) {
	
    if( tamanhoVetor < 0 ) {
      throw new IllegalArgumentException( "O tamanho do vetor não pode ser negativo." );
    }

    if( limiteSuperior <= 0 ) {
      throw new IllegalArgumentException( "O limite superior deve ser maior que zero." );
    }

	Random random = new Random();
	
	int[] vetor = new int[tamanhoVetor];
	
    for(int i = 0; i < tamanhoVetor; i++) {
	    	
	  int numero = random.nextInt( limiteSuperior );
	 
	  vetor[i] = numero;
	  
    } // FOR VETOR
	    
    return vetor;
    
  }

  /**
   * Cria uma cópia independente do vetor informado para preservar os dados originais.
   *
   * @param vetor arranjo original
   * @return novo arranjo com os mesmos elementos, ou {@code null} se a entrada for nula
   */
  public static int[] copiarVetor(int[] vetor) {

    if( vetor == null ) {
      return null;
    }

    return Arrays.copyOf( vetor, vetor.length );

  }

  /**
   * Gera um arquivo texto contendo números inteiros aleatórios no intervalo [0, limiteSuperior).
   *
   * @param caminhoArquivo caminho do arquivo de destino (ex.: "entrada.txt")
   * @param quantidadeNumeros quantidade total de números inteiros a gerar
   * @param limiteSuperior limite superior exclusivo dos números sorteados
   * @throws IOException caso ocorra erro de escrita no arquivo
   */
  public static void gerarArquivoEntrada(String caminhoArquivo, int quantidadeNumeros, int limiteSuperior)
      throws IOException {

    if( caminhoArquivo == null || caminhoArquivo.trim().isEmpty() ) {
      throw new IllegalArgumentException( "O caminho do arquivo não pode ser vazio." );
    }

    if( quantidadeNumeros <= 0 ) {
      throw new IllegalArgumentException( "A quantidade de números deve ser maior que zero." );
    }

    if( limiteSuperior <= 0 ) {
      throw new IllegalArgumentException( "O limite superior deve ser maior que zero." );
    }

    File arquivo = new File( caminhoArquivo );
    File diretorioPai = arquivo.getParentFile();

    if( diretorioPai != null && !diretorioPai.exists() ) {
      diretorioPai.mkdirs();
    }

    Random random = new Random();

    try( BufferedWriter writer = new BufferedWriter(
        new OutputStreamWriter( new FileOutputStream( arquivo ), StandardCharsets.UTF_8 ) ) ) {

      for(int i = 0; i < quantidadeNumeros; i++) {
        writer.write( Integer.toString( random.nextInt( limiteSuperior ) ) );
        writer.newLine();
      }

    }

  }

  /**
   * Lê números inteiros de um arquivo texto e os carrega em um vetor {@code int[]} na memória.
   * Aceita um número por linha ou valores separados por espaço, vírgula ou ponto e vírgula.
   *
   * @param caminhoArquivo caminho do arquivo de entrada (ex.: "entrada.txt")
   * @return vetor {@code int[]} preenchido com os valores lidos
   * @throws IOException caso o arquivo não exista ou ocorra erro de leitura/formato
   */
  public static int[] lerArquivoEntrada(String caminhoArquivo) throws IOException {

    if( caminhoArquivo == null || caminhoArquivo.trim().isEmpty() ) {
      throw new IllegalArgumentException( "O caminho do arquivo não pode ser vazio." );
    }

    File arquivo = new File( caminhoArquivo );

    if( !arquivo.exists() || !arquivo.isFile() ) {
      throw new IOException( "Arquivo não encontrado: " + arquivo.getAbsolutePath() );
    }

    List<Integer> listaValores = new ArrayList<Integer>();

    try( BufferedReader reader = new BufferedReader(
        new InputStreamReader( new FileInputStream( arquivo ), StandardCharsets.UTF_8 ) ) ) {

      String linha;
      int numeroLinha = 0;

      while( ( linha = reader.readLine() ) != null ) {
        numeroLinha++;
        String conteudo = linha.trim();

        if( conteudo.isEmpty() ) {
          continue;
        }

        String[] tokens = SEPARADOR_TOKENS.split( conteudo );

        for(int i = 0; i < tokens.length; i++) {
          if( !tokens[i].isEmpty() ) {
            try {
              listaValores.add( Integer.parseInt( tokens[i] ) );
            } catch( NumberFormatException e ) {
              throw new IOException(
                  "Valor inteiro inválido na linha " + numeroLinha + ": '" + tokens[i] + "'", e );
            }
          }
        }
      }

    }

    if( listaValores.isEmpty() ) {
      throw new IOException( "O arquivo '" + caminhoArquivo + "' está vazio." );
    }

    int[] vetor = new int[listaValores.size()];

    for(int i = 0; i < listaValores.size(); i++) {
      vetor[i] = listaValores.get( i );
    }

    return vetor;

  }

}
