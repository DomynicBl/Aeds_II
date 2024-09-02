import java.util.Scanner;

public class Is {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in, "UTF-8");
        String input = "";

        while(!input.equals("FIM")){
            input = scanner.nextLine();

            if(input.equals("FIM")){
                //aborta a execução
            }else{
                boolean isVogal = isVogal(input);
                boolean isConsoante = isConsoante(input);
                boolean isInt = isInt(input);
                boolean isReal = isReal(input);

                if(isVogal == true){
                    System.out.print("SIM ");
                }else{
                    System.out.print("NAO ");
                }

                if(isConsoante == true){
                    System.out.print("SIM ");
                }else{
                    System.out.print("NAO ");
                }

                if(isInt == true){
                    System.out.print("SIM ");
                }else{
                    System.out.print("NAO ");
                }

                if(isReal == true){
                    System.out.print("SIM");
                }else{
                    System.out.print("NAO");
                }

                System.out.println("");
            }
        }

        //scanner.close();
    }

    public static boolean isVogal(String input){
        String vogais = "aAeEiIoOuU";
        boolean resp = true;
        int cont = 0;

        for(int i=0; i<input.length(); i++){
            for(int j=0; j<vogais.length(); j++){
                if(input.charAt(i) == '\uFFFD'){
                    resp = false;
                    return resp;
                }else if(input.charAt(i) == vogais.charAt(j)){
                    j = vogais.length();
                    cont++;
                }
            }

            if(cont < (i+1)){
                resp = false;
                return resp;
            }
        }
        
        return resp; 
    }

    //--------------------------------------------------------------------------------------------------------------

    public static boolean isConsoante(String input){
        String consoantes = "bBcCdDfFgGhHjJkKlLmMnNpPqQrRsStTvVxXyYzZ";
        boolean resp = true;
        int cont = 0;

        for(int i=0; i<input.length(); i++){
            for(int j=0; j<consoantes.length(); j++){
                if(input.charAt(i) == '\uFFFD'){
                    resp = false;
                    return resp;
                }else if(input.charAt(i) == consoantes.charAt(j)){
                    j = consoantes.length();
                    cont++;
                }
            }

            if(cont < (i+1)){
                resp = false;
                return resp;
            }
        }

        return resp; 
    }

    //--------------------------------------------------------------------------------------------------------------

    public static boolean isInt(String input){
        String numeros = "1234567890";
        boolean resp = true;
        int cont = 0;

        for(int i=0; i<input.length(); i++){
            for(int j=0; j<numeros.length(); j++){
                if(input.charAt(i) == '\uFFFD'){
                    resp = false;
                    return resp;
                }else if(input.charAt(i) == numeros.charAt(j)){
                    j = numeros.length();
                    cont++;
                }
            }

            if(cont < (i+1)){
                resp = false;
                return resp;
            }
        }
        
        return resp; 
    }

    //--------------------------------------------------------------------------------------------------------------

    public static boolean isReal(String input){
        try {
            input = input.replace(',', '.');
            Float.parseFloat(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

        /*
        String numeros = "1234567890";
        String ponto = ".,";
        boolean resp = true;
        int cont = 0;
        int contPonto = 0;
        
        for(int i=0; i<input.length(); i++){
            for(int j=0; j<numeros.length(); j++){
                if(input.charAt(i) == '\uFFFD'){
                    resp = false;
                    return resp;
                }else if(input.charAt(i) == ponto.charAt(0) || input.charAt(i) == ponto.charAt(1)){
                    contPonto++;
                    System.out.println("entrou3");
                    cont++;
                }else if(input.charAt(i) == numeros.charAt(j)){
                    j = numeros.length();
                    cont++;
                }
            }

            if(contPonto > 1){
                resp = false;
                System.out.println("entrou " + contPonto);
                return resp;
            }

            if(cont < (i+1)){
                resp = false;
                System.out.println("entrou2");
                return resp;
            }
        }
        
        return resp;*/
    }
}
