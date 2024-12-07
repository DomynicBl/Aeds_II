import java.util.Scanner;

class Celula { // Classe Celula
    public int elemento;
    public Celula inf, sup, esq, dir;

    public Celula(){
        this(0);
    }

    public Celula(int elemento){
        this(elemento, null, null, null, null);
    }

    public Celula(int elemento, Celula inf, Celula sup, Celula esq, Celula dir){
        this.elemento = elemento;
        this.inf = inf;
        this.sup = sup;
        this.esq = esq;
        this.dir = dir;
    }
} 

public class Matriz { // Classe Matriz
    public static Scanner input = new Scanner(System.in);

    private Celula inicio;
    private int linha, coluna;

    public Matriz (){
        this(3, 3);
    }

    public Matriz (int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;

        inicio = new Celula();
        Celula i = inicio, j = null;

        for (int x = 1; x < coluna; x++) {
            j = new Celula();

            i.dir = j;
            j.esq = i;

            i = i.dir;
            j = j.dir;
        }

        i = inicio;
        j = i.dir;

        for  (int x = 1; x < linha; x++) {
            i.inf = new Celula();
            i.inf.sup = i;

            Celula iTemp = i;
            Celula jTemp = j;

            for (int y = 1; y < coluna; y++) {
                jTemp.inf = new Celula();
                jTemp.inf.sup = jTemp;
                iTemp.inf.dir = jTemp.inf;
                jTemp.inf.esq = iTemp.inf;
                iTemp = iTemp.dir;
                jTemp = jTemp.dir;
            }

            i = i.inf;
            j = j.inf;
        }
    }

    public void ler () throws Exception {
        if (linha <= 0 || coluna <= 0) {
            throw new Exception ("ERRO: Matriz invalida.");
        }

        Celula i = this.inicio;

        for (int x = 0; x < linha; x++) {
            Celula j = i;

            for (int y = 0; y < coluna; y++) {
                j.elemento = input.nextInt();
                j = j.dir;
            }

            i = i.inf;
        }
    }


    public Matriz somar (Matriz m) throws Exception { // Soma de matrizes
        if (this.linha != m.linha || this.coluna != m.coluna){ 
            throw new Exception ("Não é possível somar as matrizes");
        }

        Matriz resp = new Matriz (this.linha, this.coluna);  // Criar matriz de resposta
        Celula a = this.inicio; 
        Celula b = m.inicio;
        Celula c = resp.inicio;

        for (int x = 0; x < linha; x++) { // Soma das linhas
            Celula aux_a = a;
            Celula aux_b = b;
            Celula aux_c = c;

            for (int y = 0; y < coluna; y++) { // Soma dos elementos
                aux_c.elemento = aux_a.elemento + aux_b.elemento;
                aux_a = aux_a.dir;
                aux_b = aux_b.dir;
                aux_c = aux_c.dir;
            }

            a = a.inf;
            b = b.inf;
            c = c.inf;
        }

        return resp;
    }

    public Matriz multiplicacao(Matriz m) throws Exception { // Multiplicação de matrizes
        if (this.coluna != m.linha) {
            throw new Exception("Não é possível multiplicar as matrizes.");
        }

        Matriz resp = new Matriz(this.linha, m.coluna); // Criar matriz de resposta
        Celula linhaResp = resp.inicio; 
        Celula linhaThis = this.inicio; 

        for (int x = 0; x < resp.linha; x++) { // Multiplicação das linhas
            Celula colunaResp = linhaResp;
            Celula colunaMatriz = m.inicio;

            for (int y = 0; y < resp.coluna; y++) { // Multiplicação dos elementos
                Celula elementoLinhaThis = linhaThis; 
                Celula elementoColunaMatriz = colunaMatriz;
                int soma = 0;

                for (int k = 0; k < this.coluna; k++) { // Soma dos produtos
                    soma += elementoLinhaThis.elemento * elementoColunaMatriz.elemento;
                    elementoLinhaThis = elementoLinhaThis.dir; 
                    elementoColunaMatriz = elementoColunaMatriz.inf; 
                }

                colunaResp.elemento = soma;
                colunaResp = colunaResp.dir;
                colunaMatriz = colunaMatriz.dir;
            }

            linhaResp = linhaResp.inf;
            linhaThis = linhaThis.inf;
        }

        return resp;
    }

    public boolean isQuadrada() { // Verifica se a matriz é quadrada
        return (this.linha == this.coluna);
    }

    public void mostrarDiagonalPrincipal () { // Mostra a diagonal principal
        if(!isQuadrada()) { // Valida se a matriz é quadrada
            return;
        }

        Celula i = this.inicio;
        for (int x = 0; x < linha; x++) { // Percorre a diagonal principal
            System.out.print(i.elemento + " ");
            i = (i.dir != null) ? i.dir.inf : null;
        }
        System.out.println("");
    }

    public void mostrarDiagonalSecundaria () { // Mostra a diagonal secundária
        if(!isQuadrada()) { // Valida se a matriz é quadrada
            return;
        }

        Celula i = inicio;
        for (int x = 1; x < coluna; x++, i = i.dir); // Percorre a última coluna

        for (int x = 0; x < linha; x++) { // Percorre a diagonal secundária
            System.out.print(i.elemento + " ");
            i = (i.esq != null) ? i.esq.inf : null;
        }

        System.out.println("");
    }     

    public void mostrar () { // Printa a matriz 
        if (this.linha <= 0 && this.coluna <= 0){ // Verifica se a matriz é válida
            return;
        }

        Celula i = inicio;
        for (int x = 0; x < linha; x++, i = i.inf) { // Percorre as linhas
            Celula j = i;

            for (int y = 0; y < coluna; y++, j = j.dir) { // Percorre as colunas
                System.out.print(j.elemento + " ");
            }
            System.out.println("");
        }
    }

    public static void main (String args []) {
        try {
            int n;
            n = input.nextInt();

            for (int i = 0; i < n; i++) { // Loop para cada matriz
                int linha1, coluna1, linha2, coluna2; // Variáveis para as dimensões das matrizes
                linha1 = input.nextInt();
                coluna1 = input.nextInt();

                Matriz matriz1 = new Matriz(linha1, coluna1); // Cria a matriz 1
                matriz1.ler();
                linha2 = input.nextInt();
                coluna2 = input.nextInt();

                Matriz matriz2 = new Matriz(linha2, coluna2); // Cria a matriz 2
                matriz2.ler();
                matriz1.mostrarDiagonalPrincipal();
                matriz1.mostrarDiagonalSecundaria();

                Matriz matrizSoma = matriz1.somar(matriz2); // Soma das matrizes
                matrizSoma.mostrar();
                
                Matriz matrizMultiplicacao = matriz1.multiplicacao(matriz2); // Multiplicação das matrizes
                matrizMultiplicacao.mostrar();
                matriz1 = matriz2 = matrizSoma = matrizMultiplicacao = null;
            }
        } catch (Exception e) { 
            e.printStackTrace();
        }
    }
}