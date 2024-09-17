#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
    FILE *file;
    int n;
    double value;

    scanf("%d", &n);

    file = fopen("resp.txt", "w");
    if (file == NULL) {
        printf("Erro ao abrir o arquivo.");
        return 1;
    }

    for (int i = 0; i < n; i++) {
        scanf("%lf", &value);
        
        char formattedValue[50];
        snprintf(formattedValue, sizeof(formattedValue), "%g", value);

        fprintf(file, "%s\n", formattedValue);
    }

    fclose(file);

    file = fopen("resp.txt", "r");
    if (file == NULL) {
        printf("Erro ao abrir o arquivo.");
        return 1;
    }

    char lines[100][50];
    int count = 0;

    while (fgets(lines[count], sizeof(lines[count]), file) != NULL) {
        count++;
    }

    fclose(file);

    for (int i = count - 1; i >= 0; i--) {
        printf("%s", lines[i]);
    }

    return 0;
}
