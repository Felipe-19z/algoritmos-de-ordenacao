
public class Aluno {

    int matricula;
    String nome;
    int idade;
    Aluno proximo;

    public Aluno(int matricula, String nome, int idade) {

       this.matricula = matricula;
       this.nome = nome;
       this.idade = idade;
       this.proximo = null;
    }
    
}
