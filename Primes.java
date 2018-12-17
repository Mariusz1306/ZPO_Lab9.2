import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveAction;

class Primes extends RecursiveAction {
    static final int SEQUENTIAL_THRESHOLD = 1000;

    int s;
    int e;
    List<Integer> safeList;

    Primes(int start, int ends) {
        this.s   = start;
        this.e  = ends;
        this.safeList = Collections.synchronizedList(new ArrayList<>());
    }

    protected void compute() {
        if(s - e <= SEQUENTIAL_THRESHOLD) {
            for(int i=s; i < e; ++i)
                if (isPrime(i))
                    this.safeList.add(i);
        } else {
            int mid = s + (e - s / 2);
            Primes left  = new Primes(s, mid);
            Primes right = new Primes(mid, e);
            left.fork();
            right.compute();
            left.join();
        }
    }

    public static boolean isPrime(long x) {
        if (x < 3 || x % 2 == 0)
            return x == 2;

        long max = (long) Math.sqrt(x);
        for (long n = 3; n <= max; n += 2) {
            if (x % n == 0) {
                return false;
            }
        }
        return true;
    }

    public List<Integer> getSafeList() {
        return safeList;
    }
}