import java.util.Random;

public class QuickSortPivot {
    private long comparacoes = 0;
    private long movimentacoes = 0;

    // Pivô como elemento do meio (Padrão)
    public void quicksortStandard(int[] array, int esq, int dir) {
        if (esq >= dir) return;
        int i = esq, j = dir;
        int pivo = array[(dir + esq) / 2];
        while (i <= j) {
            while (array[i] < pivo) { comparacoes++; i++; }
            while (array[j] > pivo) { comparacoes++; j--; }
            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }
        if (esq < j) quicksortStandard(array, esq, j);
        if (i < dir) quicksortStandard(array, i, dir);
    }

    // Pivô como primeiro elemento
    public void quicksortFirstPivot(int[] array, int esq, int dir) {
        if (esq >= dir) return;
        int i = esq, j = dir;
        int pivo = array[esq];
        while (i <= j) {
            while (array[i] < pivo) { comparacoes++; i++; }
            while (array[j] > pivo) { comparacoes++; j--; }
            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }
        if (esq < j) quicksortFirstPivot(array, esq, j);
        if (i < dir) quicksortFirstPivot(array, i, dir);
    }

    // Pivô como último elemento
    public void quicksortLastPivot(int[] array, int esq, int dir) {
        if (esq >= dir) return;
        int i = esq, j = dir;
        int pivo = array[dir];
        while (i <= j) {
            while (array[i] < pivo) { comparacoes++; i++; }
            while (array[j] > pivo) { comparacoes++; j--; }
            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }
        if (esq < j) quicksortLastPivot(array, esq, j);
        if (i < dir) quicksortLastPivot(array, i, dir);
    }

    // Pivô aleatório
    public void quicksortRandomPivot(int[] array, int esq, int dir) {
        if (esq >= dir) return;  
        Random rand = new Random();
        int i = esq, j = dir;
        int pivoIndex = esq + rand.nextInt(dir - esq + 1); 
        int pivo = array[pivoIndex];
        
        while (i <= j) {
            while (array[i] < pivo) { comparacoes++; i++; }
            while (array[j] > pivo) { comparacoes++; j--; }
            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }
        if (esq < j) quicksortRandomPivot(array, esq, j);
        if (i < dir) quicksortRandomPivot(array, i, dir);
    }

    // Pivô como Mediana de três (primeiro, meio, último)
    public void quicksortMedianOfThree(int[] array, int esq, int dir) {
        if (esq >= dir) return;
        int i = esq, j = dir;
        int mid = (esq + dir) / 2;
        int pivo = median(array[esq], array[mid], array[dir]);
        while (i <= j) {
            while (array[i] < pivo) { comparacoes++; i++; }
            while (array[j] > pivo) { comparacoes++; j--; }
            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }
        if (esq < j) quicksortMedianOfThree(array, esq, j);
        if (i < dir) quicksortMedianOfThree(array, i, dir);
    }

    // Função para calcular a mediana de três valores
    private int median(int a, int b, int c) {
        if ((a > b) ^ (a > c)) return a;
        else if ((b > a) ^ (b > c)) return b;
        else return c;
    }

    // Função de troca de elementos
    private void swap(int[] array, int i, int j) {
        movimentacoes++;
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // Função para medir o tempo de execução
    public void testSorting(int[] array, String strategy) {
        comparacoes = 0;
        movimentacoes = 0;
        long startTime = System.currentTimeMillis();
        switch (strategy) {
            case "standard": quicksortStandard(array, 0, array.length - 1); break;
            case "first": quicksortFirstPivot(array, 0, array.length - 1); break;
            case "last": quicksortLastPivot(array, 0, array.length - 1); break;
            case "random": quicksortRandomPivot(array, 0, array.length - 1); break;
            case "median": quicksortMedianOfThree(array, 0, array.length - 1); break;
        }
        long endTime = System.currentTimeMillis();
        System.out.println(strategy + " - Tempo de execução: " + (endTime - startTime) + 
            "ms, Comparações: " + comparacoes + ", movimentações: " + movimentacoes);
    }
    // Função para gerar arrays aleatórios, ordenados, etc.
    public static int[] generateArray(int size, String type) {
        int[] array = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(1000000);
        }
        switch (type) {
            case "sorted": java.util.Arrays.sort(array); break;
            case "reversed": java.util.Arrays.sort(array); reverse(array); break;
            case "almost_sorted": for (int i = 0; i < size / 10; i++) 
                array[rand.nextInt(size)] = rand.nextInt(1000000); break;
        }
        return array;
    }
    public static void reverse(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }
    public static void main(String[] args) {
        QuickSortPivot quickSort = new QuickSortPivot();
        int[] sizes = {100, 1000, 10000, 100000, 1000000};
        String[] strategies = {"standard", "first", "last", "random", "median"};
        String[] types = {"Random", "Sorted", "Reversed Sorted", "Almost Sorted"};
        for (int size : sizes) {
            for (String type : types) {
                int[] array = generateArray(size, type);
                System.out.println("\nArray de tamanho " + size + " (" + type + ")");
                for (String strategy : strategies) {
                    int[] arrayCopy = array.clone(); // clonar o array para cada teste
                    quickSort.testSorting(arrayCopy, strategy);
                }
            }

            System.out.println("\n//------------------------------------------------------------------------//");
        }
    }
}