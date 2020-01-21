package lotnisko;

import java.awt.*;
import java.util.Date;

import dissimlab.monitors.Diagram;
import dissimlab.monitors.Diagram.DiagramType;
import dissimlab.simcore.SimControlEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;
import dissimlab.simcore.SimParameters.SimControlStatus;

public class AppSingle {
	
	public static void main(String[] args) {
		try{
			SimManager simMgr = new SimManager();
			
			//Lotnisko lotnisko = new Lotnisko(2, 3, 4, 1, simMgr);

			Lotnisko lotnisko = new Lotnisko(3, 0, 2, 1, simMgr);
			
			// Dwa sposoby planowanego końca symulacji
			//model.setEndSimTime(20);
			// lub
			SimControlEvent stopEvent = new SimControlEvent(20.0, SimControlStatus.STOPSIMULATION);

			// Badanie czasu trwania eksperymentu - początek
			long czst = new Date().getTime();			
			// Uruchomienie symulacji metodą "start"
			simMgr.startSimulation();
			// Pomiar czasu trwania eksperymentu
			czst = new Date().getTime() - czst;
			
			Diagram d1 = new Diagram(DiagramType.TIME_FUNCTION, "R-inTheAir G-onTheGround B-runwayFree");
			d1.add(lotnisko.mvInTheAir, java.awt.Color.RED);
			d1.add(lotnisko.mvOnTheGround, java.awt.Color.GREEN);
			d1.add(lotnisko.mvRunwayFree, java.awt.Color.BLUE);
			d1.add(lotnisko.mvDepartureRunwayFree, java.awt.Color.BLACK);
			d1.add(lotnisko.mvQueueLength, Color.MAGENTA);
			d1.show();

			Diagram d2 = new Diagram(DiagramType.HISTOGRAM, "Diagram");
			d2.add(lotnisko.mvQueueLength, Color.MAGENTA);
			d2.show();
			
		}
		catch (SimControlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
