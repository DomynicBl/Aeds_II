import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

class Pokemon{
    private int id;
    private int generation;
    private String name;
    private String description;
    private Lista types;
    private Lista abilities;
    private double weight;
    private double height;
    private int captureRate;
    private boolean isLegendary;
    private Date captureDate;

    public Pokemon(){
        id = 0;
        generation = 0;
        name = "";
        description = "";
        types = new Lista(2);
        abilities = new Lista(6);
        weight = 0;
        height = 0;
        captureRate = 0;
        isLegendary = false;
        captureDate = new Date();
    }

    public Pokemon(int id, int generation, String name, String description, Lista types, Lista abilities, double weight, double height, int captureRate, boolean isLegendary, Date captureDate){
        this.id = id;
        this.generation = generation;
        this.name = name;
        this.description = description;
        this.types = types;
        this.abilities = abilities;
        this.weight = weight;
        this.height = height;
        this.captureRate = captureRate;
        this.isLegendary = isLegendary;
        this.captureDate = captureDate;
    }

    //----------------------ID----------------------
    public void setId(int input){
        id = input;
    }

    public int getId(){
        return id;
    }

    //------------------GENERATION------------------
    public void setGeneration(int input){
        generation = input;
    }

    public int getGeneration(){
        return generation;
    }

    //---------------------NAME---------------------
    public void setName(String input){
        name = input;
    }

    public String getName(){
        return name;
    }

    //-----------------DESCRIPTION------------------
    public void setDescription(String input){
        description = input;
    }

    public String getDescription(){
        return description;
    }

    //--------------------TYPES---------------------
    public void setTypes(String input){
        types.adicionar(input);
    }

    public Lista getTypes(){
        return types;
    }

    //------------------ABILITIES------------------
    public void setAbilities(String input){
        String clear_abilities = input.replace("[", "").replace("]", "").replace("'", "").replace("\"", "");
        String[] abilitiesArray = clear_abilities.split(",\\s*");
    
        for (String ability : abilitiesArray) {
            abilities.adicionar(ability.trim());
        }
    }
    

    public Lista getAbilities(){
        return abilities;
    }

    //-------------------WEIGHT--------------------
    public void setWeight(double input){
        weight = input;
    }

    public double getWeight(){
        return weight;
    }

    //-------------------HEIGHT--------------------
    public void setHeight(double input){
        height = input;
    }

    public double getHeight(){
        return height;
    }

    //----------------CAPTURE_RATE-----------------
    public void setCaptureRate(int input){
        captureRate = input;
    }

    public int getCaptureRate(){
        return captureRate;
    }

    //----------------IS_LEGENDARY-----------------
    public void setIsLegendary(boolean input){
        isLegendary = input;
    }

    public boolean getIsLegendary(){
        return isLegendary;
    }

    //--------------------DATE---------------------
    public void setDate(String input){
        captureDate.criar(input);
    }

    public Date getDate(){
        return captureDate;
    }

    public Pokemon clonar(){
        return new Pokemon(id, generation, name, description, types, abilities, weight, height, captureRate, isLegendary, captureDate);
    }

    public void criarPokemon(String[] input){
        setId(Integer.parseInt(input[0]));               // ID do Pokémon
        setGeneration(Integer.parseInt(input[1]));       // Geração
        setName(input[2]);                               // Nome
        setDescription(input[3]);                        // Descrição
        setTypes(input[4]);                              // Tipo 1
        setAbilities(input[6]);                          // Habilidade
        setCaptureRate(Integer.parseInt(input[9]));      // Taxa de captura
        setDate(input[11]);                              // Data de captura

        //Validação do segundo tipo, peso, altura e raridade
        if(input[5] != null && !input[5].isEmpty()) {    // Tipo 2, se houver
            setTypes(input[5]);
        } //Tipo 2 permanece vazio

        if (input[7] != null && !input[7].isEmpty()) {   // Peso
            setWeight(Double.parseDouble(input[7]));     
        } //Peso permanece vazio

        if (input[8] != null && !input[8].isEmpty()) {   // Altura
            setHeight(Double.parseDouble(input[8]));     
        } //Altura permanece vazio

        if(input[10].equals("1")){              // Lendário
            setIsLegendary(true);
        }else{
            setIsLegendary(false);
        }
        
    }

    public void imprimir(){
        System.out.print("[#" +getId()+ " -> "+getName()+": "+getDescription()+" - [\'");
        
        if(types.tamanho() > 1){
            System.out.print(getTypes().procurar(0)+"\', \'"+getTypes().procurar(1)+"\'] - [");
        }else{
            System.out.print(getTypes().procurar(0)+"\'] - [");
        }

        for(int i = 0; i < abilities.tamanho(); i++){
            if(i <= abilities.tamanho()-2){//5
                System.out.print("\'"+getAbilities().procurar(i)+"\', ");
            }else if(i == abilities.tamanho()-1){
                System.out.print("\'"+getAbilities().procurar(i)+"\'] - ");
            }
        }

        System.out.println(getWeight()+"kg - "+getHeight()+"m - "+getCaptureRate()+"% - "+getIsLegendary()+" - "+getGeneration()+" gen] - "+getDate().print());
    }
}

//--------------------------------------------------------------------------------------------

class Lista {
    private String[] pokemons; 
    private int tamanho; // Tamanho máximo da lista
    private int length;  // Controlador da quantidade de pokemons

    public Lista(int tamanho) { // Cria uma nova lista
        this.tamanho = tamanho; // Define o tamanho máximo
        this.pokemons = new String[tamanho]; // Inicializa o array
        this.length = 0; // Inicializa o contador de pokemons
    }

    public void adicionar(String valor) { // Adiciona um novo pokemon na lista
        if (this.length < this.tamanho) {
            this.pokemons[length] = valor;
            this.length++;
        } else {
            throw new IllegalArgumentException("Lista cheia");
        }
    }

    public void removerId(String valor) { // Remove um pokemon específico da lista 
        int index = -1; 
        for (int i = 0; i < this.length; i++) { 
            if (this.pokemons[i].equals(valor)) { // Verifica se o valor é igual ao pokemon
                index = i; // Armazena o índice do pokemon encontrado
                i=this.length;
            }
        }
    
        if (index != -1) { // Se o índice for diferente de -1, o pokemon foi encontrado
            removerIndex(index);
            throw new IllegalArgumentException("pokemon não encontrado na lista");
        }
    }

    public void removerIndex(int index) { // Remove um pokemon em uma posição específica na lista
        if (index < 0 || index >= this.length) {
            throw new IllegalArgumentException("Índice inválido");
        } else {
            for (int i = index; i < this.length - 1; i++) {
                this.pokemons[i] = this.pokemons[i + 1];
            }
            this.pokemons[this.length - 1] = null; // Limpa a referência do último pokemon
            this.length--;
        }
    }

    public String procurar(int id) { // Procura um pokemon específico na lista
        if (!(id >= 0 && id < this.length)) {
            throw new IllegalArgumentException("Índice inválido");
        }
        return this.pokemons[id];
    }

    public int tamanho() { // Obtém quantidade de pokemons na lista
        return this.length; // Retorna a quantidade atual de pokemons
    }
}

//--------------------------------------------------------------------------------------------

class Date{
    private int dia;
    private int mes;
    private int ano;

    public Date(){
        this.dia = 0;
        this.mes = 0;
        this.ano = 0;
    }

    public void criar(String input){
        this.dia = Integer.parseInt(input.substring(0, 2));
        this.mes = Integer.parseInt(input.substring(3, 5));
        this.ano = Integer.parseInt(input.substring(6, 10));
    }

    public String print() {
        return String.format("%02d/%02d/%04d", this.dia, this.mes, this.ano);
    }

    public static int Comparar(Date date1, Date date2){
        int resp = 0;

        if(date1.ano > date2.ano){
            resp = 1;
        }else if(date1.ano < date2.ano){
            resp = 2;
        }else{
            if(date1.mes > date2.mes){
                resp = 1;
            }else if(date1.mes < date2.mes){
                resp = 2;
            }else{
                if(date1.dia > date2.dia){
                    resp = 1;
                }else if(date1.dia < date2.dia){
                    resp = -1;
                }else{
                    resp = 0;
                }
            }
        }
        return resp;
    }
}

//--------------------------------------------------------------------------------------------

class No{
    public Pokemon pokemon;
    public No esq, dir;
    boolean cor; // true para preto, false para branco

    public No(Pokemon pokemon) {
        this.pokemon = pokemon;
        this.esq = null;
        this.dir = null;
        this.cor = false; // Inserção inicial sempre com cor branca
    }

    public No(Pokemon pokemon, boolean cor) {
        this.pokemon = pokemon;
        this.esq = null;
        this.dir = null;
        this.cor = cor;
    }

    public No(Pokemon pokemon, No esq, No dir, boolean cor) {
        this.pokemon = pokemon;
        this.esq = esq;
        this.dir = dir;
        this.cor = cor;
    }
}

class ArvoreBinaria{
    No raiz;
    int comparacoes = 0;    

    public ArvoreBinaria(){
        raiz = null;
    }

    //-----------------PESQUISA--------------------
    public boolean pesquisar(String nome) {
        return pesquisar(nome, raiz);
    }

    private boolean pesquisar(String nome, No no) {
        if (no == null) { // Se o nó for nulo, o pokemon não foi encontrado
            comparacoes++;
            return false; 
        }else if(nome.compareTo(no.pokemon.getName()) == 0){ // Pokemon procurado == Pokemon atual
            comparacoes++;
            return true;
        }else if (nome.compareTo(no.pokemon.getName()) < 0){ // Pokemon procurado < Pokemon atual
            comparacoes++;
            System.out.print(" esq");
            return pesquisar(nome, no.esq);
        } else if (nome.compareTo(no.pokemon.getName()) > 0){ // Pokemon procurado > Pokemon atual
            comparacoes++;
            System.out.print(" dir"); 
            return pesquisar(nome, no.dir);
        } else {
            return false;
        }
    }
    
    //------------------INSERÇÃO-------------------
    public void inserir(Pokemon pokemon) throws Exception {
        if (raiz == null) { // Se o nó for nulo, insere o novo pokemon
            raiz = new No(pokemon, null, null, false); // Novo nó branco
        }else if (raiz.esq == null && raiz.dir == null) { // Case 1: Se a árvore tiver apenas um pokemon
            if(pokemon.getName().compareTo(raiz.pokemon.getName()) < 0){ // Se o novo pokemon for menor que o atual
                raiz.esq = new No(pokemon); // Insere o novo pokemon à esquerda
            }else{
                raiz.dir = new No(pokemon); // Insere o novo pokemon à direita
            }

        }else if (raiz.esq == null) { // Case 2: Se a árvore tiver dois pokemons (raiz e dir)
            if(pokemon.getName().compareTo(raiz.pokemon.getName()) < 0){ // Se o novo pokemon for menor que a raiz
                raiz.esq = new No(pokemon); // Insere o novo pokemon à esquerda    
            }else if(pokemon.getName().compareTo(raiz.dir.pokemon.getName()) < 0){
                raiz.esq = new No(raiz.pokemon); // Insere a raiz à esquerda
                raiz.pokemon = pokemon; // Define o novo pokemon como raiz
            }else{
                raiz.esq = new No(raiz.pokemon); // Insere a raiz à esquerda
                raiz.pokemon = raiz.dir.pokemon; // Define o pokemon da direita como raiz
                raiz.dir.pokemon = pokemon; // Insere o novo pokemon à direita
            }
            raiz.esq.cor = raiz.dir.cor = false; // Define a cor dos nós como brancos

        }else if(raiz.dir == null) { // Case 3: Se a árvore tiver dois pokemons (raiz e esq)
            if(pokemon.getName().compareTo(raiz.pokemon.getName()) > 0){ // Se o novo pokemon for maior que a raiz
                raiz.dir = new No(pokemon); // Insere o novo pokemon à direita
            }else if(pokemon.getName().compareTo(raiz.esq.pokemon.getName()) > 0){ // Se o novo pokemon for maior que o da esquerda
                raiz.dir = new No(raiz.pokemon); // Insere a raiz à direita
                raiz.pokemon = pokemon; // Define o novo pokemon como raiz
            }else{
                raiz.dir = new No(raiz.pokemon); // Insere a raiz à direita
                raiz.pokemon = raiz.esq.pokemon; // Define o pokemon da esquerda como raiz
                raiz.esq.pokemon = pokemon; // Insere o novo pokemon à esquerda
            }
            raiz.esq.cor = raiz.dir.cor = false; // Define a cor dos nós como brancos
    
        }else{ // Case 4: Se a árvore tiver três ou mais pokemons
            inserir(pokemon, null, null, null, raiz);
        }
        raiz.cor = false;
    }

    private void inserir(Pokemon pokemon, No bisavo, No avo, No pai, No i) throws Exception{
        if (i == null) {
            if(pokemon.getName().compareTo(pai.pokemon.getName()) < 0) {
                i = pai.esq = new No(pokemon, true);
            }else {
                i = pai.dir = new No(pokemon, true);
            }if(pai.cor == true) {
                balancear(bisavo, avo, pai, i);
            }
        }else{
            if(i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true){ // Se o nó for um 4-no (dois filhos pretos) fragmenta o nó e reequilibra a árvore
                i.cor = true;
                i.esq.cor = i.dir.cor = false;
                if(i == raiz) {
                    i.cor = false;
                }else if (pai.cor == true) {
                    balancear(bisavo, avo, pai, i);
                }
            }
            if(pokemon.getName().compareTo(i.pokemon.getName()) < 0) {
                inserir(pokemon, avo, pai, i, i.esq);
            }else if (pokemon.getName().compareTo(i.pokemon.getName()) > 0) {
                inserir(pokemon, avo, pai, i, i.dir);
            }else {
                throw new Exception("Erro inserir (elemento repetido)!");
            }
        }
    }
    

    //----------------BALANCEAMENTO-----------------
    private void balancear(No bisavo, No avo, No pai, No i) { // Reequilibra a árvore após a inserção
        if (pai.cor == true) { // Se o pai for preto, não é necessário reequilibrar
            if(pai.pokemon.getName().compareTo(avo.pokemon.getName()) > 0){ // rotacao a esquerda ou direita-esquerda
                if (i.pokemon.getName().compareTo(pai.pokemon.getName()) > 0){ // rotacao a esquerda
                    avo = rotacaoEsq(avo); // Rotaciona a esquerda
                }else {
                    avo = rotacaoDirEsq(avo); // Rotaciona a direita-esquerda
                }
            }else{ // rotacao a direita ou esquerda-direita
                if (i.pokemon.getName().compareTo(pai.pokemon.getName()) < 0){ // rotacao a direita
                    avo = rotacaoDir(avo); // Rotaciona a direita
                } else { // rotacao a esquerda-direita
                    avo = rotacaoEsqDir(avo);
                }
            }
            
            if (bisavo == null){ // Se o bisavô for nulo, o avô é a raiz
                raiz = avo;
            }else if(avo.pokemon.getName().compareTo(bisavo.pokemon.getName()) < 0){ // Se o avô for menor que o bisavô
                bisavo.esq = avo; // Define o avô como filho da esquerda do bisavô
            }else{
                bisavo.dir = avo; // Define o avô como filho da direita do bisavô
            }

            avo.cor = false;
            avo.esq.cor = avo.dir.cor = true;
        }
    }

    //----------------ROTAÇÕES---------------------
    private No rotacaoDir(No no){
        No noEsq = no.esq; // Define o nó da esquerda
        No noEsqDir = noEsq.dir; // Define o nó da direita da esquerda
        noEsq.dir = no; // Define o nó da esquerda como o nó atual
        no.esq = noEsqDir; // Define o nó da direita da esquerda como o nó da esquerda
        return noEsq; // Retorna o nó da esquerda
    }

    private No rotacaoEsq(No no){ 
        No noDir = no.dir; // Define o nó da direita
        No noDirEsq = noDir.esq; // Define o nó da esquerda da direita
        noDir.esq = no; // Define o nó da direita como o nó atual
        no.dir = noDirEsq; // Define o nó da esquerda da direita como o nó da direita
        return noDir; // Retorna o nó da direita
    }

    private No rotacaoDirEsq(No no){ 
        no.dir = rotacaoDir(no.dir); // Rotaciona a direita
        return rotacaoEsq(no); // Rotaciona a esquerda
    } 

    private No rotacaoEsqDir(No no){
        no.esq = rotacaoEsq(no.esq); // Rotaciona a esquerda
        return rotacaoDir(no); // Rotaciona a direita
    }

    //---------------CAMINHAMENTO------------------
    public void caminharCentral(No i) { //Caminhamento central da arvore/Em ordem
        if(i != null){
            caminharCentral(i.esq);
            System.out.println(i.pokemon.getName() + " ");
            caminharCentral(i.dir);
        }
    }

    public void caminharPre(No i) { //Caminhamento pré-fixado da arvore/Pré ordem
        if(i != null){
            System.out.println(i.pokemon.getName() + " ");
            caminharPre(i.esq);
            caminharPre(i.dir);
        }
    }

    public void caminharPos(No i) { //Caminhamento pós-fixado da arvore/Pós ordem
        if(i != null){
            caminharPos(i.esq);
            caminharPos(i.dir);
            System.out.println(i.pokemon.getName() + " ");
        }
    }
}

//--------------------------------------------------------------------------------------------

public class Classe_Pokemon{
    public static void procurarPokemon(String id, ArvoreBinaria pokedex) throws IOException {
        File arquivoCSV = new File("/tmp/pokemon.csv");//Caminho Verde

        if (!arquivoCSV.exists()) { // Verifica se o arquivo existe no Diretorio do Verde
            arquivoCSV = new File("..\\pokemon.csv"); // Se não existir, tenta o Diretorio local
        }
        
        if (!arquivoCSV.exists()) {// Verifica novamente se o arquivo existe
            System.out.println("Arquivo não encontrado em nenhum dos dois caminhos");
            return; // Sai do método se o arquivo não for encontrado
        }

        try (Scanner scanner = new Scanner(arquivoCSV, StandardCharsets.UTF_8)) {
            scanner.nextLine(); // Ignorar a primeira linha
            boolean encontrado = false;
            
            while (scanner.hasNextLine() && !encontrado) {
                String linha = scanner.nextLine();
                String[] pokemonAtual = linha.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // Separar os dados do pokemon atual
    
                if (pokemonAtual[0].equals(id)) { // Verifica se o ID do pokemon atual é igual ao ID procurado
                    Pokemon pokemon = new Pokemon();
                    pokemon.criarPokemon(pokemonAtual); // Cria o pokemon com os dados do pokemon atual
                    try {
                        pokedex.inserir(pokemon); // Insere o pokemon na pokedex
                    } catch (Exception e) {
                        System.out.println("Erro ao inserir Pokémon na pokedex: " + e.getMessage());
                    }
                    
                    //pokemon.imprimir(); // Imprime os dados do pokemon
                    encontrado = true; // Encerra a busca
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao tentar ler o arquivo:");
        }
    }
    
    public static void main(String[] args){
        ArvoreBinaria pokedex = new ArvoreBinaria();
        Scanner scanner = new Scanner(System.in);
        String input = "";
        boolean abort = false;

        long inicio = System.currentTimeMillis(); //Marca o tempo inicial da execução
        
        // Primeira parte: leitura dos Pokémons
        while(abort == false){
            input = scanner.nextLine();
            if(input.equalsIgnoreCase("FIM")){
                abort = true;
            }else{
                try {
                    procurarPokemon(input, pokedex);
                } catch (IOException e) {
                    System.out.println("Erro ao procurar Pokémon: " + e.getMessage());
                }
            }
        }

    
        // Segunda parte: Pesquisar dos Pokémons
        abort = false;

        while(abort == false){
            input = scanner.nextLine();
            if(input.equalsIgnoreCase("FIM")){
                abort = true;
            }else{
                System.out.println(input);
                System.out.print("raiz");

                if(pokedex.pesquisar(input)){
                    System.out.println(" SIM");
                }else{
                    System.out.println(" NAO");
                }
            }
        }

        // Criação do arquivo de log
        long fim = System.currentTimeMillis(); // Marca o tempo final da execução
        long tempoExecucao = fim - inicio; // Calcula o tempo de execução
        
        try {
            String matricula = "822331"; // Número de matrícula 
            File arquivoLog = new File("matrícula_avinegra.txt"); // Cria o arquivo de log
            try (PrintWriter writer = new PrintWriter(arquivoLog, StandardCharsets.UTF_8)) { // Cria um objeto para escrever no arquivo
                writer.printf("Matrícula:%s\tExecução(ms):%d\tComparações:%d%n", 
                    matricula, tempoExecucao, pokedex.comparacoes);// Escreve no arquivo
            }
            //System.out.println("Log criado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao criar o arquivo de log: " + e.getMessage());
        }

        scanner.close();
    }
}