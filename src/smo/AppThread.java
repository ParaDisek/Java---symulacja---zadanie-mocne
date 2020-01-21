package smo;

import java.util.Date;

import dissimlab.simcore.SimControlEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimEventBarrier;
import dissimlab.simcore.SimManager;
import dissimlab.simcore.SimParameters.SimControlStatus;

public class AppThread implements Runnable {
	
	private int id;
	 
    public AppThread(int id) {
        this.id = id;
    }
    
	
	public void run() {
		try{
			SimManager simMgr = new SimManager();
			//Semafor
			SimEventBarrier blokada = new SimEventBarrier("Blokada pomiędzy SMO");
			// Powołanie 2-ego Smo
			SmoBis smoBis = new SmoBis(5, blokada, simMgr);
			// Powołanie Smo nr 1 
			Smo smo = new Smo(smoBis, blokada, simMgr);
			// Utworzenie otoczenia
			Otoczenie generatorZgl = new Otoczenie(smo, simMgr);
			// Dwa sposoby zaplanowanego końca symulacji
			//model.setEndSimTime(100);
			// lub
			SimControlEvent stopEvent = new SimControlEvent(60.0, SimControlStatus.STOPSIMULATION);
			// Badanie czasu trwania eksperymentu - początek
			long czst = new Date().getTime();
			
			// Uruchomienie symulacji za pośrednictwem metody "start"
			simMgr.startSimulation();
			// Pomiar czasu trwania eksperymentu
			czst = new Date().getTime() - czst;

			/*
			// Wyniki
			System.out.println("Czas trwania eksperymentu: " + czst);
			
			// Formatowanie liczby do 2 miejsc po przecinku
			double wynik = new BigDecimal(Statistics
					.arithmeticMean(smo.MVczasy_oczekiwania)).setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue();
			System.out
					.println("Warto�� �rednia czasu oczekiwania na obs�ug�:   "
							+ wynik);
			wynik = new BigDecimal(Statistics
					.standardDeviation(smo.MVczasy_oczekiwania)).setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue();
			System.out
					.println("Odchylenie standardowe dla czasu obs�ugi:       "
							+ wynik);
			wynik = new BigDecimal(Statistics.max(smo.MVczasy_oczekiwania))
			.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			System.out.println("Warto�� maksymalna czasu oczekiwania na obs�ug�: "
					+ wynik);
			wynik = new BigDecimal(Statistics
					.arithmeticMean(smo.MVdlKolejki)).setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue();
			System.out
					.println("Warto�� �rednia d�ugo�ci kolejki:       "
							+ wynik);
			wynik = new BigDecimal(Statistics
					.max(smo.MVdlKolejki)).setScale(2,
					BigDecimal.ROUND_HALF_UP).doubleValue();
			System.out
					.println("Warto�� maksymalna d�ugo�ci kolejki:       "
							+ wynik);
			
			Diagram d1 = new Diagram(DiagramType.DISTRIBUTION, "Czas obs�ugiwania");
			d1.add(smo.MVczasy_obslugi, java.awt.Color.GREEN);
			d1.show();
		
			Diagram d2 = new Diagram(DiagramType.DISTRIBUTION,
					"Dlugo�� kolejki w SMO nr 2");
			d2.add(smoBis.MVdlKolejki, java.awt.Color.BLUE);
			d2.show();
		
			Diagram d3 = new Diagram(DiagramType.HISTOGRAM,
					"Czasy oczekiwania na obs�ug�");
			d3.add(smo.MVczasy_oczekiwania, java.awt.Color.BLUE);
			d3.show();
		
			Diagram d4 = new Diagram(DiagramType.DISTRIBUTION,
					"D�ugo�� kolejki w czasie");
			d4.add(smo.MVdlKolejki, java.awt.Color.RED);
			d4.show();*/
		}
		catch (SimControlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
