package smo;

import dissimlab.simcore.SimParameters;

public class AppMultiThread {
	 
	public static void main(String[] args) {
	
		for (int i=0;i<SimParameters.numTh;i++) {
			AppThread app = new AppThread(i);
			app.run();
			System.out.print("Zakończył się eksperyment nr: "+(i+1));
		}
		
	}
}
