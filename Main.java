

public class Main {
    public static void main(String args[]){
        Primes prim = new Primes(20000, 39999);
        prim.compute();
        System.out.println(prim.getSafeList());
    }
}
