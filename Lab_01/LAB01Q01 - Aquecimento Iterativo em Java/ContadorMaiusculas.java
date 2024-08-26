import java.util.Scanner;

public class ContadorMaiusculas {

    public static int contarMaiusculas(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("FIM")) {
                break;
            }
            int count = contarMaiusculas(input);
            System.out.println(count);
        }

        scanner.close();
    }
}
