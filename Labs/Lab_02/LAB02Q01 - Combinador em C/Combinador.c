#include <stdio.h>
#include <string.h>

int n=200;

int Combinador(char a[], char b[]){
    int i=0, j=0, k=0;
    int tamanho_a = strlen(a);
    int tamanho_b = strlen(b);
    int tamanho = tamanho_a + tamanho_b;

    char output[tamanho];

    while (i<tamanho_a && j<tamanho_b){
        output[k] = a[i];
        k++;
        i++;

        output[k] = b[j];
        k++;
        j++;
    }
    
    while (i<tamanho_a){
        output[k] = a[i];
        k++;
        i++;
    }

    while (i<tamanho_b){
        output[k] = b[j];
        k++;
        j++;
    }

    printf("%s\n", output);
}

int main(){
    char Palavra_a[n], Palavra_b[n];

    while(scanf("%s %s", Palavra_a , Palavra_b)==2){
        Combinador(Palavra_a, Palavra_b);
    }

    return 0;
}