import java.util.Arrays;
import java.util.Scanner;

public class PolePosition{
    public static void main(String[] args){
        var scanner = new Scanner(System.in);

        while(scanner.hasNextInt()){ // Ler entradas
            int competidores = scanner.nextInt();
            int[] largada = new int[competidores];
            int[] chegada = new int[competidores];
            int num_ultrapassagens = 0;

            for(int i = 0; i < competidores; i++){ // Preencher arrays de largada
                int input = scanner.nextInt();
                largada[i] = input;
            }

            for(int i = 0; i < competidores; i++){ // Preencher arrays de chegada
                int input = scanner.nextInt();
                chegada[i] = input;
            }

            int[] grid_atual = Arrays.copyOf(largada, largada.length); // Grid de atual

            for(int i = competidores; i > 0; i--){ // Verificar ultrapassagens
                int pos_largada = 0;
                int pos_chegada = 0;
                boolean encontrado = false;
        
                if(!Arrays.equals(largada, chegada)){ // Se largada e chegada forem iguais não há ultrapassagens
                    for(int j = 0; j < competidores && encontrado == false; j++){ // Encontrar posição de largada
                        if(grid_atual[j] == i){  
                            pos_largada = j;
                            encontrado = true;
                        }
                    }

                    encontrado = false; // Resetar flag
            
                    for(int j = 0; j < competidores && encontrado == false; j++){ // Encontrar posição de chegada
                        if(chegada[j] == i){ 
                            pos_chegada = j;
                        }
                    }
            
                    if(pos_largada == pos_chegada){// Se não houve ultrapassagem
                        //System.out.println("Sem ultrapassagem");
                    }else if(pos_largada > pos_chegada){ // Houve ultrapassagem
                        ultrapassar(grid_atual, pos_largada, pos_chegada); // Realizar ultrapassagem 
                        int posi = pos_largada - pos_chegada; // Calcular número de ultrapassagens
                        num_ultrapassagens += posi; // Adicionar ao total
                    }else{
                        ultrapassado(grid_atual, pos_largada, pos_chegada); // Realizar ultrapassagem para trás
                        int posi = pos_largada - pos_chegada; // Calcular número de ultrapassagens para trás
                        num_ultrapassagens += posi; // Adicionar ao total
                    }    
                } 
            }

            //Printar número de ultrapassagens
            if (num_ultrapassagens < 0) {
                System.out.println(-num_ultrapassagens); // Torna o número positivo (no caso de ultrapassagens para trás)
            }else{
                System.out.println(num_ultrapassagens);
            }
        }
        scanner.close();
    }

    public static void ultrapassar(int[] grid_atual, int pos_largada, int pos_chegada){ //Realizar ultrapassagens
        int tmp = grid_atual[pos_largada]; // Guardar piloto que ultrapassou

        while(pos_largada != pos_chegada){ // Realizar shift das ultrapassagem
            grid_atual[pos_largada] = grid_atual[pos_largada - 1];
            pos_largada--;
        }

        grid_atual[pos_chegada] = tmp;
    }

    public static void ultrapassado(int[] grid_atual, int pos_largada, int pos_chegada){ //Realizar ultrapassagens
        int tmp = grid_atual[pos_largada]; // Guardar piloto que foi ultrapassado

        while(pos_largada != pos_chegada){ // Realizar shift das ultrapassagem
            grid_atual[pos_largada] = grid_atual[pos_largada + 1];
            pos_largada++;
        }

        grid_atual[pos_chegada] = tmp;
    }
}