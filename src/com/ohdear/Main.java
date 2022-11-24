package com.ohdear;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner read = new Scanner(System.in);
        long n = read.nextInt();
        long bufferSize = read.nextInt();
        String fileName = read.next();
        long startTime = System.currentTimeMillis();
        PrimeBuffer primeBuffer = new PrimeBuffer(bufferSize,n,fileName);
	    new Producer(primeBuffer,n);
        new Consumer(primeBuffer,startTime);
    }
}
