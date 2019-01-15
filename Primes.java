import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveAction;

class Primes extends RecursiveAction {
    static final int SEQUENTIAL_THRESHOLD = 1000; //Każdy wątek będzie miał przedział w którym znajdzie się maksymalnie tyle liczb; Im mniejsza liczba, tym więcej wątków będzie

    int s; //początek przedziału
    int e; //koniec przedziału
    List<Integer> safeList; //nasz lista wynikowa

    Primes(int start, int ends) {
        this.s   = start;
        this.e  = ends;
        this.safeList = Collections.synchronizedList(new ArrayList<>()); //synchronizedList jest potrzebna przy pracy na wielu wątkach, żeby jeden wątek nie nadpisał wyników drugiego
    }

    protected void compute() {
        if(s - e <= SEQUENTIAL_THRESHOLD) { //Jeśli w przedziale NIE jest za dużo liczb
            for(int i=s; i < e; ++i) //Przejdź przez wszystkie liczby
                if (isPrime(i)) //Jeśli dana liczba jest pierwsza
                    this.safeList.add(i); //Dodaj ją do wyniku
        } else { //Jeśli w przedziale JEST za dużo liczb
            int mid = s + (e - s / 2); //Znajdź środek przedziału
            Primes left  = new Primes(s, mid); //Stwórz nowy wątek z lewą częścią przedziału
            Primes right = new Primes(mid+1, e); //Stwórz nowy wątek z prawą częścią przedziału
            left.fork(); //Wykonuj obliczenia lewej części w wolnej chwili (gdy nic innego nie korzysta z procesora)
			right.fork(); //Wykonuj obliczenia prawej części w wolnej chwili (gdy nic innego nie korzysta z procesora)
			left.join(); //Poczekaj, aż lewa część skończy wykonywać obliczenia
			right.join(); //Poczekaj, aż prawa część skończy wykonywać obliczenia
        }
    }

    public static boolean isPrime(long x) { 
        if (x < 3 || x % 2 == 0) //Jeśli liczba jest mniejsza od trzech, albo jest parzysta
            return x == 2; //Zwróć prawdę, jeśli dana liczba jest dwójką

        long max = (long) Math.sqrt(x); 
        for (long n = 3; n <= max; n += 2) { //Przejdź przez wszystkie liczby nieparzyste większe od trzech aż do pierwiastka z tej liczby
            if (x % n == 0) { //Jeśli oryginalna liczba jest podzielna przez n
                return false; //To nie jest liczbą pierwszą
            }
        }
        return true; //Jeśli nie zwróciliśmy do tej pory fałszu, to znaczy, że liczba nie dzieli się przez żadną inną liczbę, więc jest liczbą pierwszą
    }

    public List<Integer> getSafeList() {
        return safeList;
    }
}
