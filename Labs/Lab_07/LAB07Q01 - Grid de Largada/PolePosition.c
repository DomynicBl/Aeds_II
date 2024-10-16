#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Função para realizar ultrapassagens para frente
void ultrapassar(int* grid_atual, int pos_largada, int pos_chegada) {
    int tmp = grid_atual[pos_largada]; // Guardar piloto que ultrapassou

    while (pos_largada != pos_chegada) { // Realizar shift das ultrapassagem
        grid_atual[pos_largada] = grid_atual[pos_largada - 1];
        pos_largada--;
    }

    grid_atual[pos_chegada] = tmp;
}

// Função para realizar ultrapassagens para trás
void ultrapassado(int* grid_atual, int pos_largada, int pos_chegada) {
    int tmp = grid_atual[pos_largada]; // Guardar piloto que foi ultrapassado

    while (pos_largada != pos_chegada) { // Realizar shift das ultrapassagem
        grid_atual[pos_largada] = grid_atual[pos_largada + 1];
        pos_largada++;
    }

    grid_atual[pos_chegada] = tmp;
}

int main() {
    int competidores;
    while (scanf("%d", &competidores) == 1) { // Ler entradas
        int largada[competidores];
        int chegada[competidores];
        int num_ultrapassagens = 0;

        for (int i = 0; i < competidores; i++) { // Preencher arrays de largada
            scanf("%d", &largada[i]);
        }

        for (int i = 0; i < competidores; i++) { // Preencher arrays de chegada
            scanf("%d", &chegada[i]);
        }

        int grid_atual[competidores]; // Array para simular grid atual
        memcpy(grid_atual, largada, sizeof(largada));

        for (int i = competidores; i > 0; i--) { // Verificar ultrapassagens
            int pos_largada = 0;
            int pos_chegada = 0;
            int encontrado = 0;

            if (memcmp(largada, chegada, sizeof(largada)) != 0) { // Se largada e chegada forem iguais não há ultrapassagens
                for (int j = 0; j < competidores && encontrado == 0; j++) { // Encontrar posição de largada
                    if (grid_atual[j] == i) {
                        pos_largada = j;
                        encontrado = 1;
                    }
                }

                encontrado = 0; // Resetar flag

                for (int j = 0; j < competidores && encontrado == 0; j++) { // Encontrar posição de chegada
                    if (chegada[j] == i) {
                        pos_chegada = j;
                        encontrado = 1;
                    }
                }

                if (pos_largada == pos_chegada) {// Se não houve ultrapassagem
                    //printf("Sem ultrapassagem\n");
                    continue;
                } else if (pos_largada > pos_chegada) { // Houve ultrapassagem para frente
                    ultrapassar(grid_atual, pos_largada, pos_chegada); // Realizar ultrapassagem
                    num_ultrapassagens += pos_largada - pos_chegada; // Calcular número de ultrapassagens
                } else { // Houve ultrapassagem para trás
                    ultrapassado(grid_atual, pos_largada, pos_chegada); // Realizar ultrapassagem para trás
                    num_ultrapassagens += pos_chegada - pos_largada;// Calcular número de ultrapassagens para trás
                }
            }
        }

        // Imprimir quantidade de ultrapassagens
        printf("%d\n", num_ultrapassagens);
    }

    return 0;
}

//===================== Análise de Complexidade =====================//
    /* 1 - Leitura dos dados:
    * A leitura dos competidores e das posições de largada e chegada envolve dois loops for, 
    * ambos com complexidade O(n), onde "n" é o número de competidores.

    *   - Primeiro loop: para ler as posições de largada. 
    *      - Complexidade: O(n).
    *   - Segundo loop: para ler as posições de chegada.
    *      - Complexidade: O(n).
    * 
    * Portanto, tem complexidade O(n) + O(n) = O(n).
    */

    /* 2 - Cálculo das ultrapassagens:
    * A principal operação do código é o cálculo das ultrapassagens, que envolve encontrar as 
    * posições de largada e chegada para cada competidor.
    * 
    * Para cada competidor "i" (em ordem decrescente), o código percorre os arrays para encontrar 
    * a posição de largada e a posição de chegada.
    * 
    *   - Encontrar a posição de largada: O código percorre o array "grid_atual" de tamanho "𝑛",
    *     até encontrar a posição correta. 
    *      - Complexidade: O(n) para cada competidor.
    *   - Encontrar a posição de chegada: O código percorre o array "chegada" para encontrar a posição correta.
    *      - Complexidade: O(n) para cada competidor.
    * 
    * Como essas operações são feitas para cada um dos "n" competidores, temos que a complexidade para 
    * esse processo é 𝑂(𝑛^2).
    */

    /* 3 - Realizar ultrapassagens:
     * Quando ocorre uma ultrapassagem, o código realiza uma movimentação dos competidores no array "grid_atual", 
     * os reposicionando.
     * 
     * A função "ultrapassar" ou "ultrapassado" move os competidores até que a posição de chegada seja alcançada. 
     * Na pior das hipóteses, essa movimentação percorre uma distância de "𝑛" posições.
     * 
     *  - Melhor Caso: O(1) ocorre quando o competidor não precisa ser movido, logo não há chamada para a função.
     *  - Pior Caso: O(n) ocorre quando o competidor tem que ser movido da primeira 
     *    até a última posição ou vice-versa, para cada chamada da função.
     * 
     * Como a ultrapassagem pode ocorrer para cada um dos "𝑛" competidores, e em cada caso o movimento pode custar 
     * até n*O(n), a complexidade para as ultrapassagens varia de O(1) atá O(n^2).
    */

    /* 4 - Conclusão:
     * Leitura dos dados: O(n).
     * Cálculo das ultrapassagens: O(n^2).
     * Movimentação dos competidores: O(n^2).
     * 
     * Complexidade total = O(n) + O(n^2) + O(n^2) = O(n^2).
     * 
     * A complexidade geral do código é quadrática 𝑂 (𝑛^2) O(𝑛^2), devido ao fato de que o programa precisa, para 
     * cada competidor, verificar a posição de largada, a posição de chegada e realizar as movimentações associadas 
     * às ultrapassagens, todas operações que custam 𝑂 (𝑛) e são repetidas 𝑛 vezes.
     * 
     * Algumas melhorias foram feitas para evitar a repetição de operações, como:
     *  - A verificação de igualdade entre os arrays de largada e chegada, para evitar a execução de operações desnecessárias.
     *  - A utilização de uma variavel de controle "encontrado" para evitar a execução de loops desnecessários.
    */