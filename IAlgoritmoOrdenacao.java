package ed2.projeto.algoritmosordenacao;

/**
 * Interface que define o contrato comum para os algoritmos de ordenacao
 * avaliados no projeto de Estrutura de Dados II.
 *
 * @author Equipe ED2 - Integrante 1
 */
public interface IAlgoritmoOrdenacao {

  /**
   * Ordena in-place os elementos do vetor de inteiros informado em ordem crescente.
   *
   * @param vetor arranjo de inteiros a ser ordenado
   */
  void ordenarVetor(int[] vetor);

}
