import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

@SuppressWarnings("deprecation")
public class ContadorHTML {
    public static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        String name, url = ""; 
        String text;
        int valid = 0, consoantes, tag_br, tag_table;
        int[] vogais = new int[22];
        String resp;
        do{
            name = input.nextLine();
            
            if(name.charAt(0) == 'F' && name.charAt(1) == 'I' && name.charAt(2) == 'M'){ 
                valid = 1;
            }else{
                url = input.nextLine();
                text = getUrl(url);
                vogais = contVogais(text);
                consoantes = contConsoantes(text);
                tag_br = contBr(text);
                tag_table = contTable(text);
                consoantes = consoantes - (tag_table * 3) - (tag_br * 2);
                vogais[0] = vogais[0] - tag_table;
                vogais[1] = vogais[1] - tag_table;
                resp = "a(" + vogais[0] + ") e(" + vogais[1] + ") i(" + vogais[2] + ") o(" + vogais[3] + ") u(" + vogais[4] + ") \u00E1(" + vogais[5] + ") \u00E9(" + vogais[6] + ") \u00ED(" + vogais[7] + 
                ") \u00F3(" + vogais[8] + ") \u00FA(" + vogais[9] + ") \u00E0(" + vogais[10] + ") \u00E8(" + vogais[11] + ") \u00EC(" + vogais[12] + ") \u00F2(" + vogais[13] + ") \u00F9(" + vogais[14] + 
                ") \u00E3(" + vogais[15] + ") \u00F5(" + vogais[16] + ") \u00E2(" + vogais[17] + ") \u00EA(" + vogais[18] + ") \u00EE(" + vogais[19] + ") \u00F4(" + vogais[20] + ") \u00FB(" + vogais[21] + 
                ") consoante(" + consoantes + ") <br>(" + tag_br + ") <table>(" + tag_table + ") " + name;
                
                System.out.println(resp);
            }
        }while(valid != 1);

        input.close();
    }

    public static String getUrl(String endereco) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        StringBuilder text = new StringBuilder();
        String line;
        try {
            url = new URL(endereco); 
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is, "UTF-8")); // Usando UTF-8
    
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    
        return text.toString();
    }    
    
    public static int[] contVogais(String text){
        int[] vogais = new int[22];
    
        int tam_string = text.length();
    
        for(int i = 0; i < tam_string; i++){
            char c = text.charAt(i);
            switch (c) {
                case 'a': vogais[0]++; break;
                case 'e': vogais[1]++; break;
                case 'i': vogais[2]++; break;
                case 'o': vogais[3]++; break;
                case 'u': vogais[4]++; break;
                case '\u00E1': vogais[5]++; break;  // á
                case '\u00E9': vogais[6]++; break;  // é
                case '\u00ED': vogais[7]++; break;  // í
                case '\u00F3': vogais[8]++; break;  // ó
                case '\u00FA': vogais[9]++; break;  // ú
                case '\u00E0': vogais[10]++; break; // à
                case '\u00E8': vogais[11]++; break; // è
                case '\u00EC': vogais[12]++; break; // ì
                case '\u00F2': vogais[13]++; break; // ò
                case '\u00F9': vogais[14]++; break; // ù
                case '\u00E3': vogais[15]++; break; // ã
                case '\u00F5': vogais[16]++; break; // õ
                case '\u00E2': vogais[17]++; break; // â
                case '\u00EA': vogais[18]++; break; // ê
                case '\u00EE': vogais[19]++; break; // î
                case '\u00F4': vogais[20]++; break; // ô
                case '\u00FB': vogais[21]++; break; // û
            }
        }
    
        return vogais;
    }
    

    public static int contConsoantes(String text){
        int caracteres = 0;
        int tam_string = text.length();
        int valorAISCC;

        for(int i = 0; i < tam_string; i++) {
            valorAISCC = (int) text.charAt(i);
            if((valorAISCC >= 97 && valorAISCC <= 122)){ 
                if (text.charAt(i) != 'a' && text.charAt(i) != 'e' && text.charAt(i) != 'i' && text.charAt(i) != 'o' && text.charAt(i) != 'u'
                && text.charAt(i) != 'A' && text.charAt(i) != 'E' && text.charAt(i) != 'I' && text.charAt(i) != 'O' && text.charAt(i) != 'U'){
                    caracteres += 1;
                } 
            }
        }

        return caracteres;
    }


    public static int contBr(String text){
        int tam_string = text.length();
        int tag_br = 0;

        for(int i = 0; i < tam_string; i++){
            if (text.charAt(i) == '<' && text.charAt(i+1) == 'b' && text.charAt(i+2) == 'r' && text.charAt(i+3) == '>' ) {
                tag_br += 1;
            }
        }

        return tag_br;
    }


    public static int contTable(String text){
        int tam_string = text.length();
        int tag_table = 0;

        for(int i = 0; i < tam_string; i++){
            if (text.charAt(i) == '<' && text.charAt(i+1) == 't' && text.charAt(i+2) == 'a' && text.charAt(i+3) == 'b' 
            && text.charAt(i+4) == 'l' && text.charAt(i+5) == 'e' && text.charAt(i+6) == '>' ) {
                tag_table += 1;
            }
        }

        return tag_table;
    }
}