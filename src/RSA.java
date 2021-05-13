import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class RSA {
    private final long maxPrimeNumber = 1000;
    private final ArrayList<Integer> primeNumbers = new ArrayList<>(); // List of Prime Number

    private final int[] pqPrimeNumber = new int[2];
    private long m;
    private long r;
    private long fEuler;
    private long e;
    private long d;
    private long signature;


    RSA() {
        sieveOfEratosthenes(maxPrimeNumber);
    }

    private void sieveOfEratosthenes(long N) {
        boolean[] isPrime = new boolean[(int)N];
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        isPrime[1] = false;
        for (int i = 2; i * i < N; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j < N; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        for (int i = 0; i < isPrime.length; i++) {
            if (isPrime[i]) {
                primeNumbers.add(i);
            }
        }
    }

    private long getNumber(char symbol) {
        if ((symbol >= 65) && (symbol <= 90)) {
            return symbol - 64;
        } else {
            if ((symbol >= 1040) && (symbol <= 1045)) {
                return symbol - 1039;
            } else {
                if (symbol == 1025) {
                    return 7;
                } else {
                    if ((symbol >= 1046) && (symbol <= 1071)) {
                        return symbol - 1038;
                    } else {
                        return symbol;
                    }
                }
            }
        }
    }

    public void performPQ() {
        long indexRandom = (int) (Math.random() * (primeNumbers.size() - 10));
        long indexHelp = (int) (1+(Math.random() * 10));
        pqPrimeNumber[0] = primeNumbers.get((int)indexRandom);
        pqPrimeNumber[1] = primeNumbers.get((int) (indexHelp + indexRandom));
    }

    public long getP() {
        return pqPrimeNumber[0];
    }

    public void setP(int value) {
        pqPrimeNumber[0] = value;
    }

    public long getQ() {
        return pqPrimeNumber[1];
    }

    public void setQ(int value) {
        pqPrimeNumber[1] = value;
    }

    public long getM(){
        return m;
    }

    public long getSignature(){
        return signature;
    }

    public void setSignature(int value){
        signature = value;
    }

    private long modPower(long a, long z, long m) {
        long a1 = a;
        long z1 = z;
        long x = 1;
        while (z1 != 0) {
            while ((z1 % 2) == 0) {
                z1 = z1 / 2;
                a1 = (a1 * a1) % m;
            }
            z1 = z1 - 1;
            x = (x * a1) % m;
        }
        return x;
    } // x = a^z mod m

    public void calculateR() {
        r = getP() * getQ();
    }

    public void setR (int value){
        r = value;
    }

    public long getR(){
        return r;
    }

    public void funcEuler(long value1, long value2) {
        fEuler = ((value1 - 1) * (value2 - 1));
    }

    public void hashFunc(String message) {
        message = message.toUpperCase(Locale.ROOT);
        long defaultValue = 100;
        m = modPower(defaultValue + getNumber(message.charAt(0)), 2, getR());
        for (int i = 1; i < message.length(); i++) {
            m = modPower(m + getNumber(message.charAt(i)), 2, getR());
        }
    }

    private long GCD(long a, long b) {
        if (a == 0)
            return b;

        while (b != 0) {
            if (a > b)
                a = a - b;
            else
                b = b - a;
        }
        return a;
    }

    private static long[] eGCD(long a, long b) {
        long [] res = new long[3]; // d, x, y
        if (b == 0)
        {
            res[0] = a; res[1] = 1; res[2] = 0;
            return res;
        }

        res = eGCD(b,a % b);
        long s = res[2];
        res[2] = res[1] - (a / b) * res[2];
        res[1] = s;
        return res;
    }

    public void generateE() {
        long number;
        do {
            number = (int) (1+Math.random() * (fEuler-1));
        } while (GCD(number, fEuler) != 1);
        e = number;
    }

    public void setE(int value) {
        e = value;
    }

    public long getE(){
        return e;
    }

    public void calculateD (){
        eGCD(202611,677320);
        d = eGCD(fEuler,e)[2];
    }

    public long getD (){
        return d;
    }

    public void calculateSignature (){
        signature = modPower(m,d,r);
    }

    public boolean checkSignature (){
        return this.getM() == this.modPower(this.getSignature(), this.getE(), this.getR());
    }

}