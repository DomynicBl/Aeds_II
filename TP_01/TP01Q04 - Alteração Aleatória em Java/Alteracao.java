import java.util.Scanner;
import java.util.Random;

public class Alteracao {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in, "UTF-8");
        String input = "";

        Random gerador = new Random();
        gerador.setSeed(4);

        while(!input.equals("FIM")){
            input = scanner.nextLine();

            char letra1 = ((char)('a' + (Math.abs(gerador.nextInt()) % 26)));
            char letra2 = ((char)('a' + (Math.abs(gerador.nextInt()) % 26)));
            String output = "";
            boolean abort = false;

            for(int i=0; i<input.length(); i++){
                char caracter = input.charAt(i);

                if(input.equals("FIM")){
                    abort = true;
                }else if(caracter == '\uFFFD'){
                    output += caracter;
                }else if(caracter == letra1){
                    output += letra2;
                }else{
                    output += caracter;
                }
            }

            /*System.out.println("Input  : " + input  + '\n' + 
                                "Letra 1: " + letra1 + '\n' + 
                                "Letra 2: " + letra2 + '\n' + 
                                "Output : " + output + '\n'
                                );*/

            if(abort==true){
                //aborta a execução
            }else{
                System.out.println(output);
            }
        }

        //scanner.close();
    }
}
