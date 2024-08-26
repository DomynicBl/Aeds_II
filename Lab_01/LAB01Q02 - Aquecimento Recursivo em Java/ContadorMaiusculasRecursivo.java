import java.util.Scanner;

public class ContadorMaiusculasRecursivo {

    public static int contarMaiusculasRecursivo(String str, int index) {
        if (index == str.length()) {
            return 0;
        }
        int count = Character.isUpperCase(str.charAt(index)) ? 1 : 0;
        return count + contarMaiusculasRecursivo(str, index + 1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("FIM")) {
                break;
            }
            int count = contarMaiusculasRecursivo(input, 0);
            System.out.println(count);
        }

        scanner.close();
    }
}
