package br.com.jlameira.controller;

public class TestClass {
	

	public static void main(String args[]) {
		System.out.print("A "); 
		synchronized(args){
			System.out.print("B ");
			try {
			args.wait();
			}
			catch(InterruptedException e){}
			}
			System.out.print("C ");

		
	}
	
}

	
	