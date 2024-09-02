import java.io.*;

public class Arquivo {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.parseInt(reader.readLine());

            RandomAccessFile file = new RandomAccessFile("resp.out", "rw");
            for (int i = 0; i < n; i++) {
                String input = reader.readLine();
                try {
                    int intValue = Integer.parseInt(input);
                    file.writeBytes(intValue + "\n");
                } catch (NumberFormatException e) {
                    double doubleValue = Double.parseDouble(input);
                    file.writeBytes(Double.toString(doubleValue) + "\n");
                }
            }
            file.close();

            file = new RandomAccessFile("resp.out", "r");
            long length = file.length();
            long position = length - 1;
            StringBuilder line = new StringBuilder();

            while (position >= 0) {
                file.seek(position);
                char c = (char) file.readByte();

                if (c == '\n') {
                    if (line.length() > 0) {
                        System.out.println(line.reverse().toString());
                        line.setLength(0);
                    }
                } else {
                    line.append(c);
                }
                position--;
            }

            if (line.length() > 0) {
                System.out.println(line.reverse().toString());
            }

            file.close();

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
