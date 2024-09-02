import java.util.Scanner;

public class Ciframento {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in, "UTF-8");
        String input = "";

        while(!input.equals("FIM")){
            input = scanner.nextLine();

            String cifrado = "";
            boolean abort = false;

            for(int i=0; i<input.length(); i++){
                char caracter = input.charAt(i);

                if(input.equals("FIM")){
                    abort = true;
                }else if(caracter == '\uFFFD'){
                    cifrado += caracter;
                }else{
                    cifrado += (char)(input.charAt(i)+3);
                }
            }

            if(abort==true){
                //aborta a execução
            }else{
                System.out.println(cifrado);
            }
        }

        //scanner.close();
    }
}
