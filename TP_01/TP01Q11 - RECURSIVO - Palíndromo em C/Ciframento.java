import java.util.Scanner;

public class Ciframento {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        String input = "";

        while (!input.equals("FIM")) {
            input = scanner.nextLine();
            if (!input.equals("FIM")) {
                String cifrado = cifraString(input, 0);
                System.out.println(cifrado);
            }
        }

        scanner.close();
    }

    public static String cifraString(String s, int index) {
        if (index >= s.length()) {
            return "";
        }

        char caracter = s.charAt(index);
        String cifrado = "";

        if (caracter == '\uFFFD') {
            cifrado = caracter + cifraString(s, index + 1);
        } else {
            cifrado = (char) (caracter + 3) + cifraString(s, index + 1);
        }

        return cifrado;
    }
}
