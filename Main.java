

public class Main {
    public static void main(String args[]){
        Primes prim = new Primes(20000, 39999); //Stwórz nowy obiekt klasy Primes; (20000, 39999) to przedział w jakim szukamy liczb pierwszych
        long start = System.currentTimeMillis();
        prim.compute(); //Wykonaj obliczenia
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(prim.getSafeList()); //Wypisz wynik
        System.out.println("Czas obliczeń: " + timeElapsed + "ms");
        prim.findFiveTwinPairs();
    }
}
