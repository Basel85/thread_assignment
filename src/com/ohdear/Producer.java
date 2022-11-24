package com.ohdear;

public class Producer implements Runnable{
    private final PrimeBuffer primeBuffer;
    private final long n;
    public Producer(PrimeBuffer primeBuffer, long n){
        this.primeBuffer=primeBuffer;
        this.n=n;
        Thread t = new Thread(this,"Producer");
        t.start();
    }
    @Override
    public void run() {
        int i=1;
        while(i<=n){
            try {
                primeBuffer.produce(i);
            } catch (InterruptedException e) {
                return;
            }
            if(i==2 || i==1)
                i++;
            else
                i+=2;

        }
    }
}
