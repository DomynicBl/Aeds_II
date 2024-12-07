import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

class ListaSequencial{
    Pokemon[] array;
    int n;
    
    ListaSequencial(){ 
        this(801);
    }

    ListaSequencial(int tamanho){
        array = new Pokemon[tamanho];
        n = 0;
    }

    void inserirInicio(Pokemon pokemon)throws Exception{ //Insere um elemento na primeira posição da lista
        if(n >= array.length){
            throw new Exception("Erro! (Array Cheio)"); 
        }

        for(int i = n; i > 0;i--){ //Desloca os elementos uma posição para a direita
            array[i] = array[i-1];
        }

        array[0] = pokemon;
        n++;
    }

    void inserirFim(Pokemon pokemon)throws Exception { //Insere um elemento na última posição da lista

        if(n >= array.length){
            throw new Exception("Erro! (Array Cheio)");
        }

        array[n] = pokemon;
        n++;
    }

    void inserir(Pokemon pokemon, int posicao)throws Exception{ // Insere um elemento em uma posição específica
        if (n >= array.length || posicao < 0 || posicao > n) {
            throw new Exception("Erro! (Array Cheio ou Posição Inválida)");
        }
    
        for (int i = n; i > posicao; i--) { // Desloca os elementos para abrir espaço
            array[i] = array[i - 1];
        }
    
        array[posicao] = pokemon;
        n++;
    }
    
    Pokemon removerInicio()throws Exception{ // Remove o elemento da primeira posição
        if (n == 0) {
            throw new Exception("Erro! (Pokedex Vazia)");
        }
    
        Pokemon removido = array[0];
        for (int i = 0; i < n - 1; i++) { // Desloca os elementos para preencher o espaço
            array[i] = array[i + 1];
        }
    
        n--;
        return removido;
    }
    
    Pokemon removerFim()throws Exception{ // Remove o último elemento da lista
        if (n == 0) {
            throw new Exception("Erro! (Array Vazio)");
        }
    
        return array[--n];
    }
    
    Pokemon remover(int posicao)throws Exception{ // Remove o elemento de uma posição específica
        if (n == 0 || posicao < 0 || posicao >= n) {
            throw new Exception("Erro! (Array Vazio ou Posição Inválida)");
        }
    
        Pokemon removido = array[posicao];
        for (int i = posicao; i < n - 1; i++) { // Desloca os elementos para preencher o espaço
            array[i] = array[i + 1];
        }
    
        n--;
        return removido;
    }
    
    void mostrar(){ // Exibe todos os elementos da lista
        for (int i = 0; i < n; i++) {
            System.out.print("[" + i + "] ");
            array[i].imprimir();
        }
    }
}

//--------------------------------------------------------------------------------------------

public class Classe_Pokemon{
    public static void procurarPokemon(String id, ListaSequencial pokedex, String Comando, int posicao) throws IOException {
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

                    if(Comando.equals("NA") || Comando.equals("IF")){ // Se o comando for de inserção
                        try {
                            pokedex.inserirFim(pokemon); // Insere o pokemon na pokedex
                        } catch (Exception e) {
                            System.out.println("Erro ao inserir Pokémon na pokedex: " + e.getMessage());
                        }
                    }else if(Comando.equals("II")){
                        try {
                            pokedex.inserirInicio(pokemon); // Insere o pokemon na pokedex
                        } catch (Exception e) {
                            System.out.println("Erro ao inserir Pokémon na pokedex: " + e.getMessage());
                        }
                    }else if(Comando.equals("I*")){
                        try {
                            pokedex.inserir(pokemon, posicao); // Insere o pokemon na pokedex
                        } catch (Exception e) {
                            System.out.println("Erro ao inserir Pokémon na pokedex: " + e.getMessage());
                        }
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
        ListaSequencial pokedex = new ListaSequencial();
        Scanner scanner = new Scanner(System.in);
        String input = "";
        boolean abort = false;
        
        // Primeira parte: leitura dos Pokémons
        while(abort == false){
            input = scanner.nextLine();
            if(input.equalsIgnoreCase("FIM")){
                abort = true;
            }else{
                try {
                    procurarPokemon(input, pokedex, "NA", 0);
                } catch (IOException e) {
                    System.out.println("Erro ao procurar Pokémon: " + e.getMessage());
                }
            }
        }

        // Segunda parte: leitura dos comandos
        int num_comandos = scanner.nextInt();

        for(int i = 0; i < num_comandos+1; i++){
            input = scanner.nextLine();
            String[] comando = input.split(" ");
            Pokemon temp = new Pokemon();

            switch (comando[0]) {
                case "II":
                    try {
                        procurarPokemon(comando[1], pokedex, "II", 0);
                    } catch (IOException e) {
                        System.out.println("Erro ao inserir no Inicio: " + e.getMessage());
                    }
                    break;
                case "IF":
                    try {
                        procurarPokemon(comando[1], pokedex, "IF", 0);
                    } catch (Exception e) {
                        System.out.println("Erro ao inserir no fim: " + e.getMessage());
                    }
                    break;
                case "I*":
                    try {
                        procurarPokemon(comando[2], pokedex, "I*", Integer.parseInt(comando[1]));
                    } catch (Exception e) {
                        System.out.println("Erro ao inserir na posição: " + e.getMessage());
                    }
                    break;
                case "RI":
                    try {
                        temp = pokedex.removerInicio();
                        System.out.println("(R) " + temp.getName());
                    } catch (Exception e) {
                        System.out.println("Erro ao remover do início: " + e.getMessage());
                    }
                    break;
                case "RF":
                    try {
                        temp = pokedex.removerFim();
                        System.out.println("(R) " + temp.getName());
                    } catch (Exception e) {
                        System.out.println("Erro ao remover do fim: " + e.getMessage());
                    }
                    break;
                case "R*":
                    try {
                        temp = pokedex.remover(Integer.parseInt(comando[1]));
                        System.out.println("(R) " + temp.getName());
                    } catch (Exception e) {
                        System.out.println("Erro ao remover na posição: " + e.getMessage());
                    }
                    break;
            }
        }
    
        pokedex.mostrar();

        scanner.close();
    }
}