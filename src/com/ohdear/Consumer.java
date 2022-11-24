package com.ohdear;

import java.io.IOException;

public class Consumer implements Runnable{
    private final PrimeBuffer primeBuffer;
    private final long startTime;

    Consumer(PrimeBuffer primeBuffer,long startTime){
        this.primeBuffer=primeBuffer;
        this.startTime=startTime;
        Thread t = new Thread(this,"Consumer");
        t.start();
    }
    @Override
    public void run() {
        while (true){
            try {
                primeBuffer.consume();
            } catch (InterruptedException | IOException e) {
                return;
            }
            if(primeBuffer.getIsOperationFinished() && primeBuffer.getPrimes().isEmpty()){
                long endTime = System.currentTimeMillis();
                long executionTime = endTime-startTime;
                System.out.println("The largest prime number : "+primeBuffer.getMaxPrimeValue());
                System.out.println("# of prime numbers generated : "+primeBuffer.getNumberOfPrimeNumbers());
                System.out.println("Time elapsed since the start of processing : "+executionTime + " ms");
                break;
            }

        }
    }
}
