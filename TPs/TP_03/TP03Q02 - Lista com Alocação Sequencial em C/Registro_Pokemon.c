#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <locale.h>

#define MAX_TAM_LISTA 6 // Definindo o tamanho máximo da lista de tipos e habilidades
#define MAX_TAM_NAME 52 // Definindo o tamanho máximo de cada campo do Pokémon (nome, descrição, tipo, habilidade)
#define MAX_TAM_POKE_ATUAL 200 // Definindo o tamanho máximo da string do Pokémon (linha CSV)
#define MAX_TOKENS 20 // Definindo o número máximo de tokens esperados
#define MAX_POKEMONS 801 // Defina um tamanho máximo de Pokémons para a Pokedex

typedef enum boolean {// Criação de um tipo booleano
    false = 0,
    true = 1
} boolean;

typedef struct Date {// Estrutura para armazenar a data
    int day;
    int month;
    int year;
} Date;

typedef struct Lista {// Estrutura para armazenar uma lista
    char elementos[MAX_TAM_LISTA][MAX_TAM_NAME];
    int length; // Controlador da quantidade de elementos
    int tamanho; // Tamanho máximo da lista
} Lista;

typedef struct Pokemon {// Estrutura para armazenar um Pokémon
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

typedef struct Pokedex { // Estrutura para armazenar uma Pokedex já pesquisados
    Pokemon pokemons[MAX_POKEMONS]; // Array de Pokémons
    int count; // Contagem de Pokémons adicionados
} Pokedex;

//--------------------------------------------------------------------------------------------

void inicializarData(Date *captureDate) {// Função para inicializar a data
    captureDate->day = 0;
    captureDate->month = 0;
    captureDate->year = 0;
}

void inicializarLista(Lista *lista, int tamanho) {// Função para inicializar a lista
    lista->length = 0;
    lista->tamanho = tamanho;
    for (int i = 0; i < tamanho; i++) {
        strcpy(lista->elementos[i], " ");
    }
}

void inicializarPokemon(Pokemon *pokemon) {// Função para inicializar o Pokémon
    pokemon->id = 0;
    pokemon->generation = 0;
    strcpy(pokemon->name, " ");
    strcpy(pokemon->description, " ");
    inicializarLista(&pokemon->types, 2);
    inicializarLista(&pokemon->abilities, 6);
    pokemon->weight = 0.0;
    pokemon->height = 0.0;
    pokemon->captureRate = 0;
    pokemon->isLegendary = false;
    inicializarData(&pokemon->captureDate);
}

void inicializarPokedex(Pokedex *pokedex) { // Função para inicializar a Pokedex
    pokedex->count = 0;
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

void adicionarPokedexInicio(Pokedex *pokedex, Pokemon pokemon) {
    if (pokedex->count < MAX_POKEMONS) { // Move todos os pokémons uma posição para a direita
        for (int i = pokedex->count; i > 0; i--) {
            pokedex->pokemons[i] = pokedex->pokemons[i - 1];
        }
        pokedex->pokemons[0] = pokemon; // Adiciona o Pokémon no início
        pokedex->count++; // Incrementa a contagem
    } else {
        printf("Pokedex cheia! Não é possível adicionar mais Pokémons.\n");
    }
}

void adicionarPokedexFim(Pokedex *pokedex, Pokemon pokemon) {
    if (pokedex->count < MAX_POKEMONS) {
        pokedex->pokemons[pokedex->count] = pokemon; // Adiciona o Pokémon
        pokedex->count++; // Incrementa a contagem
    } else {
        printf("Pokedex cheia! Não é possível adicionar mais Pokémons.\n");
    }
}

void adicionarPokedexPosicao(Pokedex *pokedex, Pokemon pokemon, int posicao) {
    if (pokedex->count < MAX_POKEMONS && posicao >= 0 && posicao <= pokedex->count) { // Move todos os pokémons após a posição para a direita
        for (int i = pokedex->count; i > posicao; i--) {
            pokedex->pokemons[i] = pokedex->pokemons[i - 1];
        }
        pokedex->pokemons[posicao] = pokemon; // Adiciona o Pokémon na posição desejada
        pokedex->count++; // Incrementa a contagem
    } else {
        printf("Posição inválida ou Pokedex cheia! Não é possível adicionar o Pokémon.\n");
    }
}

Pokemon removerPokedexInicio(Pokedex *pokedex) {
    if (pokedex->count > 0) {
        Pokemon resp = pokedex->pokemons[0]; // Armazena o Pokémon a ser removido
        for (int i = 0; i < pokedex->count - 1; i++) { // Move todos os pokémons uma posição para a esquerda
            pokedex->pokemons[i] = pokedex->pokemons[i + 1];
        }
        pokedex->count--; // Decrementa a contagem
        return resp; // Retorna o Pokémon removido
    } else {
        printf("Pokedex vazia! Não é possível remover o Pokémon.\n");
        Pokemon pokemonVazio = {0}; // Retorna um Pokémon vazio em caso de erro
        return pokemonVazio;
    }
}

Pokemon removerPokedexFim(Pokedex *pokedex) {
    if (pokedex->count > 0) {
        Pokemon resp = pokedex->pokemons[pokedex->count - 1]; // Armazena o Pokémon a ser removido
        pokedex->count--; // Decrementa a contagem
        return resp; // Retorna o Pokémon removido
    } else {
        printf("Pokedex vazia! Não é possível remover o Pokémon.\n");
        Pokemon pokemonVazio = {0}; // Retorna um Pokémon vazio em caso de erro
        return pokemonVazio;
    }
}

Pokemon removerPokedexPosicao(Pokedex *pokedex, int posicao) {
    if (pokedex->count > 0 && posicao >= 0 && posicao < pokedex->count) {
        Pokemon resp = pokedex->pokemons[posicao]; // Armazena o Pokémon a ser removido
        for (int i = posicao; i < pokedex->count - 1; i++) { // Move todos os pokémons após a posição para a esquerda
            pokedex->pokemons[i] = pokedex->pokemons[i + 1];
        }
        pokedex->count--; // Decrementa a contagem
        return resp; // Retorna o Pokémon removido
    } else {
        printf("Posição inválida ou Pokedex vazia! Não é possível remover o Pokémon.\n");
        Pokemon pokemonVazio = {0}; // Retorna um Pokémon vazio em caso de erro
        return pokemonVazio;
    }
}

void imprimirPokedex(const Pokedex *pokedex) {
    for (int i = 0; i < pokedex->count; i++) {
        printf("[%d] ", i);
        imprimir(&(pokedex->pokemons[i])); // Usa a função de impressão ja existente
    }
}

//--------------------------------------------------------------------------------------------

void procurarPokemon(int id, Pokedex *pokedex, char *cmd, int pos) { // Função para procurar um Pokémon pelo ID
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
            if (strcmp(cmd, "NA") == 0) {
                adicionarPokedexFim(pokedex, pokemon); // Adiciona o Pokémon à Pokedex
            } else if (strcmp(cmd, "I*") == 0) { // Exemplo de comando com wildcard
                adicionarPokedexPosicao(pokedex, pokemon, pos); // Adiciona o Pokémon à Pokedex na posição desejada
            } else if (strcmp(cmd, "II") == 0) {
                adicionarPokedexInicio(pokedex, pokemon); // Adiciona o Pokémon à Pokedex no início
            } else if (strcmp(cmd, "IF") == 0) {
                adicionarPokedexFim(pokedex, pokemon); // Adiciona o Pokémon à Pokedex no fim
            } else {
                printf("Comando não reconhecido.\n");
            }
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

    Pokedex pokedex; // Declara a Pokedex
    inicializarPokedex(&pokedex); // Inicializa a Pokedex

    // Primeira parte: leitura dos Pokémons
    while (!abort) {
        scanf("%s", input);

        if (strcmp(input, "FIM") == 0 || strcmp(input, "fim") == 0) {
            abort = true;
        } else {
            int id = atoi(input);
            procurarPokemon(id, &pokedex, "NA", 0);
        }
    }

    // Segunda parte: leitura dos comandos
    int num_comandos = 0;
    scanf("%d", &num_comandos);
    while ((getchar()) != '\n'); // Limpa o buffer
    
    char str_cmd[MAX_TAM_POKE_ATUAL];
    for (int i = 0; i < num_comandos; i++) {
        char *comando[3];
        char *cmd = NULL;
        int id = 0, pos = 0;
    
        fgets(str_cmd, MAX_TAM_POKE_ATUAL, stdin);
        str_cmd[strcspn(str_cmd, "\n")] = 0; // Remove newline
    
        comando[0] = strtok(str_cmd, " ");
        comando[1] = strtok(NULL, " ");
        comando[2] = strtok(NULL, " ");
    
        if (comando[0] != NULL) {
            cmd = comando[0];
        } else {
            printf("Comando inválido.\n");
            continue;
        }
    
        // Processa os comandos
        if (strcmp(cmd, "II") == 0 || strcmp(cmd, "IF") == 0) {
            id = atoi(comando[1]);
            procurarPokemon(id, &pokedex, cmd, 0);
        } else if (strcmp(cmd, "I*") == 0) {
            pos = atoi(comando[1]);
            id = atoi(comando[2]);
            procurarPokemon(id, &pokedex, cmd, pos);
        } else if (strcmp(cmd, "RI") == 0 || strcmp(cmd, "RF") == 0) {
            Pokemon pk;
            if (strcmp(cmd, "RI") == 0) {
                pk = removerPokedexInicio(&pokedex);
            } else {
                pk = removerPokedexFim(&pokedex);
            }
            printf("(R) %s\n", pk.name);
        } else if (strcmp(cmd, "R*") == 0) {
            pos = atoi(comando[1]);
            Pokemon pk = removerPokedexPosicao(&pokedex, pos);
            printf("(R) %s\n", pk.name);
        } else {
            printf("Comando não reconhecido: %s\n", str_cmd);
        }
    }

    imprimirPokedex(&pokedex); // Imprime a Pokedex

    return 0;
}