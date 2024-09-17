#include <stdio.h>
#include <string.h>

int main() {
    char palavra[100];

    while (fgets(palavra, sizeof(palavra), stdin) != NULL) {
        palavra[strcspn(palavra, "\n")] = '\0';

        if (strcmp(palavra, "FIM") == 0) {
            return 0;
        }

        char palavraInvertida[100];
        int j = 0;
        int len = strlen(palavra);

        for (int i = len - 1; i >= 0; i--) {
            palavraInvertida[j++] = palavra[i];
        }
        palavraInvertida[j] = '\0';

        //printf("%s\n", palavra);
        //printf("%s\n", palavraInvertida);

        if (strcmp(palavra, palavraInvertida) == 0) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }

    return 0;
}
