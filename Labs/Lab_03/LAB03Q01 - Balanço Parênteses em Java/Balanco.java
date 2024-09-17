import java.util.Scanner;

public class Balanco{
    public static void main(String[] args){
        var scanner = new Scanner(System.in);
        String input = "";

        while(!input.equals("FIM")){ 
            int abertura = 0;
            int fechamento = 0;
            boolean jump = false;
            boolean abort = false;

            input = scanner.nextLine();

            for(int i=0; i<=input.length()-1; i++) {
                if(input.equals("FIM")){
                    abort = true;
                }
                if(input.charAt(i) == '('){
                    abertura++;
                }
                if(input.charAt(i) == ')'){
                    fechamento++;
                }
                if(abertura < fechamento){
                    jump = true;
                }
            }

            if(abort == true){
                
            }else if(jump==true){
                System.out.println("incorreto");
            }else if(abertura == fechamento) {
                System.out.println("correto");
                abertura = 0;
            }else{
                System.out.println("incorreto");
                fechamento = 0; 
            }
        }

        //scanner.close();
    }
}
