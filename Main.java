

public class Main {
    public static void main(String args[]){
        Primes prim = new Primes(20000, 39999); //Stwórz nowy obiekt klasy Primes; (20000, 39999) to przedział w jakim szukamy liczb pierwszych
        prim.compute(); //Wykonaj obliczenia
        System.out.println(prim.getSafeList()); //Wypisz wynik
    }
}
