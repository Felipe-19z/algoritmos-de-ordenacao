
package ed2.projeto.algoritmosordenacao;

/**
 * 
 * @author Hilario Tomaz Alves de Oliveira
 *
 */

public class MergeSort implements IAlgoritmoOrdenacao {

  @Override

  public void ordenarVetor(int[] vetor) {

    if (vetor == null || vetor.length <= 1) {
      return;
    }

    // Vetor auxiliar alocado uma unica vez para preservar complexidade O(n log n) e
    // espaco O(n)
    int aux[] = new int[vetor.length];
    this.mergeSort(vetor, aux, 0, (vetor.length - 1));
  }

  public void mergeSort(int[] vetor, int inicio, int fim) {

    if (vetor == null || inicio >= fim) {
      return;
    }

    int aux[] = new int[vetor.length];
    this.mergeSort(vetor, aux, inicio, fim);

  }

  private void mergeSort(int[] vetor, int[] aux, int inicio, int fim) {

    int meio;

    if (inicio < fim) {

      meio = inicio + (fim - inicio) / 2;

      this.mergeSort(vetor, aux, inicio, meio);
      this.mergeSort(vetor, aux, meio + 1, fim);

      this.intercala(vetor, aux, inicio, fim, meio);

    }

  }

  private void intercala(int[] vetor, int[] aux, int inicio, int fim, int meio) {

    int posLivre, inicioVetor1, inicioVetor2;

    inicioVetor1 = inicio;
    inicioVetor2 = meio + 1;

    posLivre = inicio;

    while ((inicioVetor1 <= meio) && (inicioVetor2 <= fim)) {

      if (vetor[inicioVetor1] <= vetor[inicioVetor2]) {

        aux[posLivre] = vetor[inicioVetor1];

        inicioVetor1++;

      } else {

        aux[posLivre] = vetor[inicioVetor2];

        inicioVetor2++;

      }

      posLivre++;

    } // WHILE

    for (int i = inicioVetor1; i <= meio; i++) {

      aux[posLivre] = vetor[i];

      posLivre++;

    } // FOR VETOR 1

    for (int i = inicioVetor2; i <= fim; i++) {

      aux[posLivre] = vetor[i];

      posLivre++;

    } // FOR VETOR 2

    for (int i = inicio; i <= fim; i++) {

      vetor[i] = aux[i];

    } // FOR VETOR COMPLETO

  }

  @Override

  public String toString() {

    return "MergeSort";

  }

}
