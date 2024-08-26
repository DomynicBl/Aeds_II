#include <stdio.h>
#include <ctype.h>
#include <string.h>

int contarMaiusculasRecursivo(const char *str, int index) {
    if (index == strlen(str)) {
        return 0;
    }
    int count = isupper(str[index]) ? 1 : 0;
    return count + contarMaiusculasRecursivo(str, index + 1);
}

int main() {
    char input[100];

    while (1) {
        fgets(input, sizeof(input), stdin);
        input[strcspn(input, "\n")] = 0; // Remove newline character from input

        if (strcmp(input, "FIM") == 0) {
            break;
        }

        int count = contarMaiusculasRecursivo(input, 0);
        printf("%d\n", count);
    }

    return 0;
}
