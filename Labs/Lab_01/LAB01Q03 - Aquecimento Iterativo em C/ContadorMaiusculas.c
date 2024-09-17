#include <stdio.h>
#include <ctype.h>
#include <string.h>

int contarMaiusculas(const char *str) {
    int count = 0;
    for (int i = 0; i < strlen(str); i++) {
        if (isupper(str[i])) {
            count++;
        }
    }
    return count;
}

int main() {
    char input[100];

    while (1) {
        fgets(input, sizeof(input), stdin);
        input[strcspn(input, "\n")] = 0;

        if (strcmp(input, "FIM") == 0) {
            break;
        }

        int count = contarMaiusculas(input);
        printf("%d\n", count);
    }

    return 0;
}
