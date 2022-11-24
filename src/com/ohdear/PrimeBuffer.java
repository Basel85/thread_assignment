package com.ohdear;

import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class PrimeBuffer {
    long bufferSize;
    private final List<Integer> primes;
    private long numberOfPrimeNumbers=0;
    private final long n;
    boolean isOperationFinished=false;
    File file;
    FileWriter fileWriter;
    PrintWriter printWriter;
    private long maxPrimeValue=0;

    PrimeBuffer(long bufferSize,long n,String fileName) throws IOException {
        this.bufferSize=bufferSize;
        file = new File(fileName);
        fileWriter = new FileWriter(file);
        printWriter = new PrintWriter(fileWriter);
        this.primes=new ArrayList<>();
        this.n=n;
    }

    public List<Integer> getPrimes() {
        return primes;
    }

    public long getMaxPrimeValue() {
        return maxPrimeValue;
    }

    public long getNumberOfPrimeNumbers() {
        return numberOfPrimeNumbers;
    }

    public boolean getIsOperationFinished() {
        return isOperationFinished;
    }

    public synchronized void produce(int val) throws InterruptedException {
//        System.out.println("Prod");
        while(primes.size()==bufferSize){
            wait();
        }
        if(isPrime(val)){
            primes.add(val);
            if(val==n || (val==n-1 && n>3)){
                isOperationFinished=true;
            }
//            System.out.println("Put : "+val);
            numberOfPrimeNumbers++;
            maxPrimeValue=val;
            notify();
        }
        if(val==n || (val==n-1 && n>3)){
            isOperationFinished=true;
        }
    }
    public synchronized void consume() throws IOException, InterruptedException {
//        System.out.println("Cons");
//        System.out.println("The max : "+maxPrimeValue);
        while(primes.isEmpty()){
            if(isOperationFinished)
                break;
            wait();
        }
        while(!primes.isEmpty()){
            int primeValue=primes.get(0);
//            System.out.println("Get : "+primeValue);
            printWriter.print(primeValue);
            printWriter.print(" ");
            primes.remove(0);
        }
        if(isOperationFinished){
            printWriter.close();
        }
        notify();
    }
    public boolean isPrime(int val){
        if(val==2 || val==1)return true;
        if(val%2==0)return false;
        for(int i=3;i*i<=val;i+=2){
            if(val%i==0){
                return false;
            }
        }
        return true;
    }
}
