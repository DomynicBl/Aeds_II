import java.util.Scanner;

public class Palindromo{
    public static void main(String[] args){
        var scanner = new Scanner(System.in);
        String input = "";

        while(!input.equals("FIM")){
            input = scanner.nextLine();

            String resp = "";

            for(int i=(input.length()-1); i>=0; i--){
                resp += input.charAt(i);
            }

            if(input.equals("FIM")){
                //Não printar o FIM"
            }else if(resp.equals(input)){
                System.out.println("SIM");
            }else{
                System.out.println("NAO");
            }
        }

        //scanner.close();
    }
}