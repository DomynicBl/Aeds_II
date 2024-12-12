#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <locale.h>
#include <time.h>

#define MAX_TAM_LISTA 6
#define MAX_TAM_NAME 52
#define MAX_TAM_POKE_ATUAL 200
#define MAX_TOKENS 20

int comparacoes = 0; // Variável global para armazenar o número de comparações realizadas

typedef enum boolean {
    false = 0,
    true = 1
} boolean;

typedef struct Date {
    int day;
    int month;
    int year;
} Date;

typedef struct Lista {
    char elementos[MAX_TAM_LISTA][MAX_TAM_NAME];
    int length;
    int tamanho;
} Lista;

typedef struct Pokemon {
    int id;
    int generation;
    char name[MAX_TAM_NAME];
    char description[MAX_TAM_NAME];
    Lista types;
    Lista abilities;
    double weight;
    double height;
    int captureRate;
    boolean isLegendary;
    Date captureDate;
} Pokemon;

typedef struct No {
    Pokemon elemento;
    struct No* esq;
    struct No* dir;
    int nivel;
} No;

typedef struct Pokedex {
    No* raiz;
} Pokedex;

// Funções de inicialização
void inicializarData(Date* captureDate) {
    captureDate->day = 0;
    captureDate->month = 0;
    captureDate->year = 0;
}

void inicializarLista(Lista* lista, int tamanho) {
    lista->length = 0;
    lista->tamanho = tamanho;
    for (int i = 0; i < tamanho; i++) {
        strcpy(lista->elementos[i], ""); // Inicializa com string vazia
    }
}

void inicializarPokemon(Pokemon* pokemon) {
    pokemon->id = 0;
    pokemon->generation = 0;
    strcpy(pokemon->name, "");
    strcpy(pokemon->description, "");
    inicializarLista(&pokemon->types, 2);
    inicializarLista(&pokemon->abilities, 6);
    pokemon->weight = 0.0;
    pokemon->height = 0.0;
    pokemon->captureRate = 0;
    pokemon->isLegendary = false;
    inicializarData(&pokemon->captureDate);
}

No* inicializarNo(Pokemon elemento) {
    No* novo = (No*)malloc(sizeof(No));
    if (novo == NULL) {
        perror("Erro ao alocar memória para o nó");
        exit(EXIT_FAILURE); // Sai do programa em caso de erro
    }
    novo->elemento = elemento;
    novo->esq = NULL;
    novo->dir = NULL;
    novo->nivel = 1;
    return novo;
}

void inicializarPokedex(Pokedex* pokedex) {
    pokedex->raiz = NULL;
}

// Funções de manipulação da Pokedex (AVL)
int getNivel(No* no) {
    return (no == NULL) ? 0 : no->nivel;
}

void atualizarNivel(No* no) {
    if (no != NULL) {
        no->nivel = 1 + (getNivel(no->esq) > getNivel(no->dir) ? getNivel(no->esq) : getNivel(no->dir));
    }
}

No* rotacionarDir(No* no) {
    No* noEsq = no->esq;
    no->esq = noEsq->dir;
    noEsq->dir = no;
    atualizarNivel(no);
    atualizarNivel(noEsq);
    return noEsq;
}

No* rotacionarEsq(No* no) {
    No* noDir = no->dir;
    no->dir = noDir->esq;
    noDir->esq = no;
    atualizarNivel(no);
    atualizarNivel(noDir);
    return noDir;
}

No* balancear(No* no) {
    if (no != NULL) {
        atualizarNivel(no);
        int fator = getNivel(no->dir) - getNivel(no->esq);
        if (fator > 1) { // Desbalanceado para a direita
            if (getNivel(no->dir->esq) > getNivel(no->dir->dir)) {
                no->dir = rotacionarDir(no->dir);
                comparacoes++;
            }
            no = rotacionarEsq(no);
            comparacoes++;
        } else if (fator < -1) { // Desbalanceado para a esquerda
            if (getNivel(no->esq->dir) > getNivel(no->esq->esq)) {
                no->esq = rotacionarEsq(no->esq);
                comparacoes++;
            }
            no = rotacionarDir(no);
            comparacoes++;
        }
    }
    return no;
}

No* inserir(No* no, Pokemon elemento) {
    if (no == NULL) {
        return inicializarNo(elemento);
        comparacoes++;
    } else if (strcmp(elemento.name, no->elemento.name) < 0) {
        no->esq = inserir(no->esq, elemento);
        comparacoes++;
    } else if (strcmp(elemento.name, no->elemento.name) > 0) {
        no->dir = inserir(no->dir, elemento);
        comparacoes++;
    } else {
        printf("Erro ao inserir: elemento ja existe!\n");
        return no; // Importante: retornar o nó original em caso de duplicata
    }
    return balancear(no);
}

No* pesquisar(No* no, char* name) {
    if (no == NULL || strcmp(no->elemento.name, name) == 0) {
        comparacoes++;
        return no;
    }
    if (strcmp(name, no->elemento.name) < 0) {
        printf("esq ");
        comparacoes++;
        return pesquisar(no->esq, name);
    } else {
        printf("dir ");
        return pesquisar(no->dir, name);
    }
}

void liberarArvore(No* no) {
    if (no != NULL) {
        liberarArvore(no->esq);
        liberarArvore(no->dir);
        free(no);
    }
}

//--------------------------------------------------------------------------------------------

void adicionarElemento(Lista *lista, const char *elemento) {// Função para adicionar um elemento à lista
    if (lista->length < lista->tamanho) {
        strcpy(lista->elementos[lista->length], elemento);
        lista->length++;
    }
}

int verificarID(const char *pokemonAtual, int id) {// Função para verificar se o ID do Pokémon é o ID procurado
    char idStr[4];// String para armazenar os 4 primeiros caracteres do ID (3 dígitos + '\0')
    snprintf(idStr, sizeof(idStr), "%d", id);
    
    if (strncmp(pokemonAtual, idStr, strlen(idStr)) == 0) {
        return 1;
    } else {
        return 0;
    }
}

void split(const char *pokemonAtual, char delimitador, char tokens[][MAX_TAM_POKE_ATUAL], int *numTokens) {// Função para dividir a string do Pokémon em tokens (por vírgula)
    int tokenIndex = 0;
    int charIndex = 0;
    boolean dentroAspas = false;

    for (int i = 0; pokemonAtual[i] != '\0'; i++) {// Loop para percorrer a string
        char atual = pokemonAtual[i];

        if (atual == '\"') {// Se o caractere atual for uma aspa dupla, inverte o valor da variável dentroAspas(para não separar as habilidades)
            dentroAspas = !dentroAspas;
        } else if (atual == delimitador && !dentroAspas) {// Se o caractere atual for uma vírgula e não estiver dentro de aspas, finaliza o token
            tokens[tokenIndex][charIndex] = '\0';
            tokenIndex++;
            charIndex = 0;
        } else {
            tokens[tokenIndex][charIndex++] = atual;// Adiciona o caractere atual ao token
        }
    }
    tokens[tokenIndex][charIndex] = '\0';
    *numTokens = tokenIndex + 1;

    /*printf("Tokens lidos:\n"); // Debug
    for (int i = 0; i < *numTokens; i++) {
        printf("Token %d: '%s'\n", i, tokens[i]);
    }*/
}

void extrairHabilidades(const char *habilidadesStr, Lista *habilidades) {// Função para extrair as habilidades do Pokémon e armazená-las na lista
    // Remover os caracteres indesejados
    char habilidadesLimpa[MAX_TAM_POKE_ATUAL];
    int j = 0;
    for (int i = 0; habilidadesStr[i] != '\0'; i++) {// Loop para percorrer a string
        if (habilidadesStr[i] != '[' && habilidadesStr[i] != ']' && habilidadesStr[i] != '\'' && habilidadesStr[i] != ' ') {// Se o caractere atual não for um dos caracteres indesejados, adiciona-o à string limpa
            habilidadesLimpa[j++] = habilidadesStr[i];
        } else if (habilidadesStr[i] == ' ') {
            habilidadesLimpa[j++] = ' '; // Mantém o espaço
        }
    }
    habilidadesLimpa[j] = '\0'; // Finaliza a string limpa

    char *habilidade_token = strtok(habilidadesLimpa, ",");// Divide a string limpa em tokens (por vírgula)
    int i = 0;
    while (habilidade_token != NULL && i < MAX_TAM_LISTA) {
        // Remover espaços desnecessários no início e no final
        char *habilidadeLimpa = habilidade_token;
        while (*habilidadeLimpa == ' ') habilidadeLimpa++; // Remove espaços à esquerda
        char *end = habilidadeLimpa + strlen(habilidadeLimpa) - 1;
        while (end > habilidadeLimpa && *end == ' ') end--; // Remove espaços à direita
        end[1] = '\0'; // Finaliza a string limpa

        adicionarElemento(habilidades, habilidadeLimpa);// Adiciona a habilidade à lista
        habilidade_token = strtok(NULL, ",");
        i++;
    }
}

//--------------------------------------------------------------------------------------------

void criarPokemon(Pokemon *pokemon, const char *pokemonAtual) {// Função para criar um Pokémon a partir da string do Pokémon
    char input[MAX_TOKENS][MAX_TAM_POKE_ATUAL];
    int num_tokens = 0;
    split(pokemonAtual, ',', input, &num_tokens);

    // Atribuir os atributos do Pokémon
    pokemon->id = atoi(input[0]);
    pokemon->generation = atoi(input[1]);
    strcpy(pokemon->name, input[2]);
    strcpy(pokemon->description, input[3]);

    // Processar os tipos do Pokémon
    char *tipo1 = input[4];
    char *tipo2 = input[5];

    if (strlen(tipo1) > 0) {
        adicionarElemento(&(pokemon->types), tipo1);
    }
    if (strlen(tipo2) > 0) {
        adicionarElemento(&(pokemon->types), tipo2);
    }

    // Processar as habilidades
    extrairHabilidades(input[6], &(pokemon->abilities));

    // Converter peso
    char pesoStr[MAX_TAM_POKE_ATUAL];
    strcpy(pesoStr, input[7]); // Copia o valor original

    for (char *c = pesoStr; *c; c++) {
        if (*c == ',') {
            *c = '.'; // Substitui a vírgula por ponto (para converter para double)
        }
    }
    
    pokemon->weight = atof(pesoStr);

    // Converter altura
    char alturaStr[MAX_TAM_POKE_ATUAL];
    strcpy(alturaStr, input[8]); // Copia o valor original

    for (char *c = alturaStr; *c; c++) {
        if (*c == ',') {
            *c = '.'; // Substitui a vírgula por ponto (para converter para double)
        }
    }
    
    pokemon->height = atof(alturaStr); 

    pokemon->captureRate = atoi(input[9]);
    pokemon->isLegendary = strcmp(input[10], "1") == 0;

    // Processar a data de captura
    if (strlen(input[11]) > 0) {
        sscanf(input[11], "%d/%d/%d", &pokemon->captureDate.day, &pokemon->captureDate.month, &pokemon->captureDate.year);
    } else {
        inicializarData(&pokemon->captureDate);
    }
}


void imprimir(const Pokemon *pokemon) {// Função para imprimir os atributos do Pokémon
    // Exibir ID, nome, descrição
    printf("[#%d -> %s: %s - [", pokemon->id, pokemon->name, pokemon->description);

    // Exibir tipos
    for (int i = 0; i < pokemon->types.length; i++) {
        printf("'%s'", pokemon->types.elementos[i]);
        if (i < pokemon->types.length - 1) {
            printf(", ");
        }
    }

    printf("] - [");

    // Exibir habilidades
    for (int i = 0; i < pokemon->abilities.length; i++) {
        printf("'%s'", pokemon->abilities.elementos[i]);
        if (i < pokemon->abilities.length - 1) {
            printf(", ");
        }
    }

    // Exibir peso, altura, taxa de captura, se é lendário e data de captura
    printf("] - %.1fkg - %.1fm - %d%% - %s - %d gen] - %02d/%02d/%04d\n",
        pokemon->weight,
        pokemon->height,
        pokemon->captureRate,
        pokemon->isLegendary ? "true" : "false",
        pokemon->generation,
        pokemon->captureDate.day,
        pokemon->captureDate.month,
        pokemon->captureDate.year
    );
}

//--------------------------------------------------------------------------------------------

void salvarLog(const char* matricula, long tempoExecucao, int comparacoes) {
    FILE *arquivoLog = fopen("matricula_avl.txt", "w");
    if (arquivoLog == NULL) {
        printf("Erro ao criar o arquivo de log.\n");
        return;
    }
    
    fprintf(arquivoLog, "Matrícula:%s\tExecução(ms):%ld\tComparações:%d\n", matricula, tempoExecucao, comparacoes);
    fclose(arquivoLog);
}

//--------------------------------------------------------------------------------------------

void procurarPokemon(int id, Pokedex *pokedex) { // Função para procurar um Pokémon pelo ID
    FILE *arquivoCSV = fopen("/tmp/pokemon.csv", "r"); // Verifica se o arquivo existe no Diretorio do Verde

    if (arquivoCSV == NULL) { // Se não existir, tenta o Diretorio local
        arquivoCSV = fopen("../pokemon.csv", "r"); // Caminho mais adequado
    }

    if (arquivoCSV == NULL) { // Verifica novamente se o arquivo existe
        printf("Arquivo não encontrado em nenhum dos dois caminhos\n");
        return; // Sai do método se o arquivo não for encontrado
    }

    boolean encontrado = false; // Usando 'bool' para indicar o status de encontrado
    char pokemonAtual[MAX_TAM_POKE_ATUAL];

    while (fgets(pokemonAtual, sizeof(pokemonAtual), arquivoCSV) && !encontrado) { // Loop para percorrer o arquivo
        if (verificarID(pokemonAtual, id) == 1) { // Verifica se o ID do pokemon atual é igual ao ID procurado
            Pokemon pokemon; // Cria o pokemon com os dados do pokemon atual
            inicializarPokemon(&pokemon); // Inicializa o pokemon
            criarPokemon(&pokemon, pokemonAtual); // Adiciona os dados do pokemon atual ao pokemon
            pokedex->raiz = inserir(pokedex->raiz, pokemon); // Adiciona o Pokémon à Pokedex
            //imprimir(&pokemon); // Imprime os dados do pokemon (se necessário)
            encontrado = true; // Encerra a busca
        }
    }

    fclose(arquivoCSV); // Fecha o arquivo
}

int main() {
    setlocale(LC_ALL, "C"); // Define a localização para "C"
    char input[4];
    boolean abort = false;

    clock_t inicio = clock();  // Marca o tempo inicial da execução

    Pokedex pokedex; // Cria a Pokedex
    inicializarPokedex(&pokedex); // Inicializa a Pokedex

    // Primeira parte: leitura dos Pokémons
    while (!abort) {
        scanf("%s", input);

        if (strcmp(input, "FIM") == 0 || strcmp(input, "fim") == 0) {
            abort = true;
        } else {
            int id = atoi(input);
            procurarPokemon(id, &pokedex);
        }
    }

    // Segunda parte: leitura dos comandos
    abort = false;

    while (!abort) {
        scanf("%s", input);

        if (strcmp(input, "FIM") == 0 || strcmp(input, "fim") == 0) {
            abort = true;
        } else {
            printf("%s \nraiz ", input);
            No* resp = pesquisar(pokedex.raiz, input);

            if(resp != NULL){
                printf("SIM");
            } else{
                printf("NAO");
            }

            printf("\n");
        }
    }

    clock_t fim = clock(); // Marca o tempo final da execução
    long tempoExecucao = (long)((fim - inicio) * 1000 / CLOCKS_PER_SEC); // Converte para milissegundos

    // Salva o arquivo de log
    char *matricula = "822331"; // Número de matrícula
    salvarLog(matricula, tempoExecucao, comparacoes); // Salva o log

    //imprimirPokedex(&pokedex); // Imprime a Pokedex
    liberarArvore(pokedex.raiz);
    return 0;
}