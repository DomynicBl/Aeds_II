import java.util.Scanner;

public class Espelho {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        while(scanner.hasNextLine()){
            String input = scanner.nextLine();
            String[] num = input.split(" ");

            int inicio = Integer.parseInt(num[0]);
            int fim = Integer.parseInt(num[1]);

            int vet[] = new int[fim-inicio+1];

            for(int i = 0; i < vet.length; i++){
                vet[i] = inicio + i;
            }
            
            StringBuilder resp = new StringBuilder();
            for(int i = 0; i < vet.length; i++){
                resp.append(vet[i]);
            }

            String original = resp.toString();
            String invertido = resp.reverse().toString();

            System.out.print(original);
            System.out.print(invertido);

            System.out.print("\n");
        }
        scanner.close();
    }
}