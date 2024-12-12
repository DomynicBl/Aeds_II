import java.util.ArrayList;
import java.util.List;
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
    private String[] elementos; 
    private int tamanho; // Tamanho máximo da lista
    private int length;  // Controlador da quantidade de elementos

    public Lista(int tamanho) { // Cria uma nova lista
        this.tamanho = tamanho; // Define o tamanho máximo
        this.elementos = new String[tamanho]; // Inicializa o array
        this.length = 0; // Inicializa o contador de elementos
    }

    public void adicionar(String valor) { // Adiciona um novo elemento na lista
        if (this.length < this.tamanho) {
            this.elementos[length] = valor;
            this.length++;
        } else {
            throw new IllegalArgumentException("Lista cheia");
        }
    }

    public void removerId(String valor) { // Remove um elemento específico da lista 
        int index = -1; 
        for (int i = 0; i < this.length; i++) { 
            if (this.elementos[i].equals(valor)) { // Verifica se o valor é igual ao elemento
                index = i; // Armazena o índice do elemento encontrado
                i=this.length;
            }
        }
    
        if (index != -1) { // Se o índice for diferente de -1, o elemento foi encontrado
            removerIndex(index);
            throw new IllegalArgumentException("Elemento não encontrado na lista");
        }
    }

    public void removerIndex(int index) { // Remove um elemento em uma posição específica na lista
        if (index < 0 || index >= this.length) {
            throw new IllegalArgumentException("Índice inválido");
        } else {
            for (int i = index; i < this.length - 1; i++) {
                this.elementos[i] = this.elementos[i + 1];
            }
            this.elementos[this.length - 1] = null; // Limpa a referência do último elemento
            this.length--;
        }
    }

    public String procurar(int id) { // Procura um elemento específico na lista
        if (!(id >= 0 && id < this.length)) {
            throw new IllegalArgumentException("Índice inválido");
        }
        return this.elementos[id];
    }

    public int tamanho() { // Obtém quantidade de elementos na lista
        return this.length; // Retorna a quantidade atual de elementos
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
    public Pokemon elemento;
    public No esq, dir;

    public No(){
        this.elemento = null;
        this.esq = null;
        this.dir = null;
    }

    public No(Pokemon elemento){
        this.elemento = elemento;
        this.esq = null;
        this.dir = null;
    }

    public No(Pokemon elemento, No esq, No dir){
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

class ArvoreBinaria{
    No raiz;
    int comparacoes = 0;    

    public ArvoreBinaria(){
        raiz = null;
    }

    //-----------------PESQUISA--------------------
    public boolean pesquisar(String pokemon){
        return pesquisar(pokemon, raiz);
    }

    public boolean pesquisar(String pokemon, No i){ //Pesquisa um pokemon na arvore pelo nome
        boolean resp = false;

        if(i == null){ //Se i for nulo, não encontrou o pokemon
            resp = false;
            comparacoes++;
        }else if(pokemon.compareTo(i.elemento.getName()) == 0){ //pokemon == i.elemento
            resp = true;
            comparacoes++;
        }else if(pokemon.compareTo(i.elemento.getName()) < 0){ //pokemon < i.elemento
            System.out.print(" esq");
            resp = pesquisar(pokemon, i.esq);
            comparacoes++;
        }else if(pokemon.compareTo(i.elemento.getName()) > 0){ //pokemon > i.elemento
            System.out.print(" dir");
            resp = pesquisar(pokemon, i.dir);
            comparacoes++;
        }

        return resp;
    }
    
    //------------------INSERÇÃO-------------------
    public void inserir(Pokemon pokemon) throws Exception { //Insere um pokemon na arvore
        raiz = inserir(pokemon, raiz);
    }

    No inserir(Pokemon pokemon, No i) throws Exception{ //Insere um pokemon na arvore
        if(i == null){ //Se a raiz for nula, insere o pokemon
            i = new No(pokemon);
            comparacoes++;
        }else if (pokemon.getName().compareTo(i.elemento.getName()) < 0){ //pokemon < i.elemento
            i.esq = inserir(pokemon, i.esq);
            comparacoes++;
        }else if (pokemon.getName().compareTo(i.elemento.getName()) > 0){ //pokemon > i.elemento
            i.dir = inserir(pokemon, i.dir);
            comparacoes++;
        }else{ //pokemon == i.elemento (pokemon já existe, não sera inserido)
            throw new Exception("Erro ao inserir pokemon!");
        }

        return i;
    }

    //----------------INSERÇÃO_PAI-----------------
    public void inserirPai(Pokemon pokemon) throws Exception { //Insere um pokemon pai na arvore
        if(raiz == null){
            raiz = new No(pokemon);
            comparacoes++;
        }else if(pokemon.getName().compareTo(raiz.elemento.getName()) < 0){ //pokemon < raiz
            inserirPai(pokemon, raiz.esq, raiz);
            comparacoes++;
        }else if(pokemon.getName().compareTo(raiz.elemento.getName()) > 0){ //pokemon > raiz
            inserirPai(pokemon, raiz.dir, raiz);
            comparacoes++;
        }else{ //pokemon == raiz (pokemon já existe, não sera inserido)
            throw new Exception("Erro ao inserir pokemon pai!");
        }
    }

    public void inserirPai(Pokemon pokemon, No i, No pai) throws Exception { //Insere um pokemon pai na arvore
        if(i == null){ //Se a raiz for nula, insere o pokemon
            if(pokemon.getName().compareTo(pai.elemento.getName()) < 0){ //pokemon < pai
                pai.esq = new No(pokemon);
                comparacoes++;
            }else{ //pokemon > pai
                pai.dir = new No(pokemon);
                comparacoes++;
            }
        }else if (pokemon.getName().compareTo(i.elemento.getName()) < 0){ //pokemon < i.elemento
            inserirPai(pokemon, i.esq, i);
            comparacoes++;
        }else if (pokemon.getName().compareTo(i.elemento.getName()) > 0){ //pokemon > i.elemento
            inserirPai(pokemon, i.dir, i);
            comparacoes++;
        }else{ //pokemon == i.elemento (pokemon já existe, não sera inserido)
            throw new Exception("Erro ao inserir pokemon pai!");
        }
    }
    
    //------------------REMOÇÃO--------------------
    public void remover(String pokemon) throws Exception { //Remove um pokemon da arvore pelo nome
        raiz = remover(pokemon, raiz);
    }

    public No remover(String pokemon, No i) throws Exception {
        if(i == null){ //Se a raiz for nula, não faz nada
            comparacoes++;
            throw new Exception("Erro ao remover pokemon!");
        }else if(pokemon.compareTo(i.elemento.getName()) < 0){ //pokemon < i.elemento
            i.esq = remover(pokemon, i.esq);
            comparacoes++;
        }else if(pokemon.compareTo(i.elemento.getName()) > 0){ //pokemon > i.elemento
            i.dir = remover(pokemon, i.dir);
            comparacoes++;
        }else if(i.dir == null){ //Se i.dir for nulo, remove o i (i sem filhos a direita)
            i = i.esq;
            comparacoes++;
        }else if(i.esq == null){ //Se i.esq for nulo, remove o i (i sem filhos a esquerda)
            i = i.dir;
            comparacoes++;
        }else{ //Se i possuir dois filhos
            i.esq = maiorEsq(i, i.esq);
        }

        return i;
    }

    public No maiorEsq(No i, No j){
        if(j.dir == null){ //Se j.dir for nulo, encontrou o maior elemento
            i.elemento = j.elemento; //Copia o elemento de j para i
            j = j.esq;
            comparacoes++;
        }else{ //Se j.dir não for nulo, continua a busca do maior elemento
            j.dir = maiorEsq(i, j.dir);
        }

        return j;
    }

    //---------------CAMINHAMENTO------------------
    public void caminharCentral(No i) { //Caminhamento central da arvore/Em ordem
        if(i != null){
            caminharCentral(i.esq);
            System.out.println(i.elemento.getName() + " ");
            caminharCentral(i.dir);
        }
    }

    public void caminharPre(No i) { //Caminhamento pré-fixado da arvore/Pré ordem
        if(i != null){
            System.out.println(i.elemento.getName() + " ");
            caminharPre(i.esq);
            caminharPre(i.dir);
        }
    }

    public void caminharPos(No i) { //Caminhamento pós-fixado da arvore/Pós ordem
        if(i != null){
            caminharPos(i.esq);
            caminharPos(i.dir);
            System.out.println(i.elemento.getName() + " ");
        }
    }

    //------------------EXTRAS---------------------
    public Pokemon getMaior() { // Retorna o maior pokemon(Nome) da arvore (pokemon mais a direita)
        Pokemon resp = null;

        if(raiz != null){ //Se a raiz não for nula, procura o pokemon mais a direita
            No i = raiz;
            for(i = raiz; i.dir != null; i = i.dir); //Percorre a arvore até o ultimo elemento a direita
            resp = i.elemento;
        }

        return resp;
    }

    public Pokemon getMenor() { // Retorna o menor pokemon(Nome) da arvore (pokemon mais a direita)
        Pokemon resp = null;

        if(raiz != null){ //Se a raiz não for nula, procura o pokemon mais a esquerda
            No i = raiz;
            for(i = raiz; i.esq != null; i = i.esq); //Percorre a arvore até o ultimo elemento a esquerda
            resp = i.elemento;
        }

        return resp;
    }

    public void mostrar(){ // Exibe todos os elementos da pilha
        //int j = 0;

        /*for (CelulaDupla i = primeiro.prox; i != null; i = i.prox) {
            //System.out.print("[" + j + "] ");
            i.elemento.imprimir();
            //j++;
        }*/
    }
}

//-------------------MOD----------------------
class NoMod{
    public int elemento;
    public NoMod esq, dir;
    public ArvoreBinaria subArvore;

    public NoMod(){
        this.elemento = 0;
        this.esq = null;
        this.dir = null;
        this.subArvore = null;
    }

    public NoMod(int elemento){
        this.elemento = elemento;
        this.esq = null;
        this.dir = null;
        this.subArvore = null;
    }

    public NoMod(int elemento, NoMod esq, NoMod dir){
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

class ArvoreBinariaMod{
    NoMod raizMod;
    int comparacoes = 0;

    public ArvoreBinariaMod(){
        raizMod = null;
    }

    public No pesquisarMod(int captureMod){
        NoMod i = raizMod;
        return pesquisarMod(captureMod, i);
    }

    public No pesquisarMod(int captureMod, NoMod i){
        if(i == null){
            comparacoes++;
            return null;
        }else if(i.elemento == captureMod){ //captureReteMod == pokemon procurado
            comparacoes++;
            return i.subArvore.raiz;
        }else if(i.elemento < captureMod){ //captureRateMod < pokemon procurado
            comparacoes++;
            System.out.print(" ESQ");
            return pesquisarMod(captureMod, i.dir);
        }else if(i.elemento > captureMod){ //captureRateMod > pokemon procurado
            comparacoes++;
            System.out.print(" DIR");
            return pesquisarMod(captureMod, i.esq);
        }else{
            return null;
        }
    }

    public void inserirMod(Pokemon pokemon) throws Exception { //Insere um pokemon na arvore
        int mod = pokemon.getCaptureRate()%15;
        System.out.print(mod);
        raizMod = inserirMod(mod, raizMod);
        raizMod.subArvore.inserir(pokemon);
    }

    NoMod inserirMod(int mod, NoMod i) throws Exception{ //Insere um noMod na arvoreMod
        if(i == null){ //Se a raiz for nula, cria um noMod
            i = new NoMod(mod);
            i.subArvore = new ArvoreBinaria();
            comparacoes++;
        }else if (mod < i.elemento){ //captureRateMod < i.elemento
            i.esq = inserirMod(mod, i.esq);
            comparacoes++;
        }else if (mod > i.elemento){ //captureRateMod > i.elemento
            i.dir = inserirMod(mod, i.dir);
            comparacoes++;
        }else{ //captureRateMod == i.elemento (noMod com a captureRate já existe, não sera inserido)
            throw new Exception("Nó duplicado para o valor: " + mod);
        }
    
        return i;
    }

    public int contComparacoes() { //Conta o número de comparações realizadas em todas as pesquisas
        return contComparacoes(raizMod);
    }
    
    private int contComparacoes(NoMod noMod) {
        if (noMod == null) {
            return 0;
        }
    
        // Soma das comparações da subárvore atual e das subárvores nos filhos
        int soma = noMod.subArvore.comparacoes;
        soma += contComparacoes(noMod.esq);
        soma += contComparacoes(noMod.dir);
    
        return soma;
    }
}

//------------------CACHE---------------------
class Cache { //Classe auxiliar para armazenar nome e taxa de captura dos pokemons inseridos na arvore
    String Nome;
    int CaptureRate;

    public void setCache(String Nome, int CaptureRate){
        this.Nome = Nome;
        this.CaptureRate = CaptureRate;
    }
}

//--------------------------------------------------------------------------------------------

public class Classe_Pokemon{
    public static void procurarPokemon(String id, ArvoreBinariaMod pokedex, List<Cache> cache) throws IOException {
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
                        pokedex.inserirMod(pokemon); // Insere o pokemon na pokedex
                        Cache cacheAtual = new Cache(); // Cria um novo objeto de cache
                        cacheAtual.setCache(pokemon.getName(), pokemon.getCaptureRate()); // Adiciona o nome e a taxa de captura do pokemon na cache
                        cache.add(cacheAtual); // Adiciona o objeto de cache na lista de cache para futuras consultas
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
        ArvoreBinariaMod pokedex = new ArvoreBinariaMod();
        List<Cache> cache = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String input = "";
        boolean abort = false;

        long inicio = System.currentTimeMillis(); //Marca o tempo inicial da execução
        

        // Primeira parte: criação da árvore de árvores
        int[] ordem = {7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14};
        for (int valor : ordem) {
            try {
                pokedex.inserirMod(valor, pokedex.raizMod);
            } catch (Exception e) {
                System.out.println("Erro ao criar arvore: " + e.getMessage());
            }
            
        }

        // Segunda parte: leitura dos Pokémons
        while(abort == false){
            input = scanner.nextLine();
            if(input.equalsIgnoreCase("FIM")){
                abort = true;
            }else{
                try {
                    procurarPokemon(input, pokedex, cache);
                } catch (IOException e) {
                    System.out.println("Erro ao procurar Pokémon: " + e.getMessage());
                }
            }
        }
    
        // Terceira parte: Pesquisar dos Pokémons
        abort = false;

        while(abort == false){
            input = scanner.nextLine();
            if(input.equalsIgnoreCase("FIM")){
                abort = true;
            }else{
                System.out.println("=>"+input);
                System.out.print("raiz ");

                for(Cache cacheAtual : cache){ //Pesquisa na cache o captureRate do pokemon pelo seu nome
                    boolean encontrado = false;

                    if(cacheAtual.Nome.equals(input)){
                        No index = pokedex.pesquisarMod(cacheAtual.CaptureRate%15); //Pesquisa em qual subArvore o pokemon se encontra
                        encontrado = pokedex.raizMod.subArvore.pesquisar(input, index); //Pesquisa o pokemon na subArvore com o index anterior

                        if(encontrado){ //Se o pokemon for encontrado, imprime SIM
                            System.out.println(" SIM");
                        }else{ //Se o pokemon não for encontrado, imprime NAO
                            System.out.println(" NAO");
                        }
                        
                        break;
                    }else{
                        //System.out.print("Não pode ser feita a pesuisa");
                    }
                }
                
            }
        }

        // Criação do arquivo de log
        long fim = System.currentTimeMillis(); // Marca o tempo final da execução
        long tempoExecucao = fim - inicio; // Calcula o tempo de execução
        
        try {
            String matricula = "822331"; // Número de matrícula 
            File arquivoLog = new File("matrícula_arvoreArvore.txt"); // Cria o arquivo de log
            try (PrintWriter writer = new PrintWriter(arquivoLog, StandardCharsets.UTF_8)) { // Cria um objeto para escrever no arquivo
                writer.printf("Matrícula:%s\tExecução(ms):%d\tComparações:%d%n", 
                    matricula, tempoExecucao, pokedex.contComparacoes());// Escreve no arquivo
            }
            //System.out.println("Log criado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao criar o arquivo de log: " + e.getMessage());
        }

        scanner.close();
    }
}