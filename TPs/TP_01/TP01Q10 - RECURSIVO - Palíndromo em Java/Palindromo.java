import java.util.Scanner;

public class Palindromo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (!input.equals("FIM")) {
            input = scanner.nextLine();
            if (!input.equals("FIM")) {
                if (isPalindromo(input, 0, input.length() - 1)) {
                    System.out.println("SIM");
                } else {
                    System.out.println("NAO");
                }
            }
        }

        //scanner.close();
    }

    public static boolean isPalindromo(String s, int left, int right) {
        if (left >= right) {
            return true;
        }

        if (s.charAt(left) != s.charAt(right)) {
            return false;
        }

        return isPalindromo(s, left + 1, right - 1);
    }
}
