package lotnisko;

import dissimlab.monitors.MonitoredVar;
import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;
import lotnisko.Plane;

import java.util.ArrayList;
import java.util.Random;

public class Lotnisko extends BasicSimObj {
	int inTheAir; // liczba samolotów w powietrzu
	int onTheGround; // liczba samolotów czekających na lotnisku
	boolean runwayFree; // dostępność pasa lądowania
	boolean departureRunwayFree;
	double arrivalInterval; // okres pomiedzy kolejnymi przylotami
	int arrivalTime; // czas następnego przylotu
	int landingTime; // czas zakończenia lądowania
	int landingDuration; // czas trwania lądowania
	double departureInterval; // okres pomiędzy odlotami
	double departureTime; // czas następnego odlotu
	int currentIndex;
	boolean runwayCheck;
	SimStep krokSymulacyjny;
	int queueLength;
	double sum, min, max, average;
	MonitoredVar mvOnTheGround, mvInTheAir, mvRunwayFree, mvDepartureRunwayFree, mvQueueLength;
	ArrayList<Plane> departureQueue = new ArrayList<>();
	ArrayList<Plane> landingQueue = new ArrayList<>();
	ArrayList<Integer> indexes = new ArrayList<>();
	Random rand = new Random();
	SimGenerator generator = new SimGenerator();
	ArrivalEvent arrivalEvent;
	LandingEvent landingEvent;
	DepartureEvent departureEvent;
	FlewAwayEvent flewAwayEvent;
	StartServiceEvent startServiceEvent;
	FinishServiceEvent finishServiceEvent;
	ArrayList<Passenger> passengerQueue = new ArrayList<>();
	ArrayList<Passenger> passengerStartedService = new ArrayList<>();

	public Lotnisko(int arrivalInterval, int landingDuration, int departureInterval, int period, SimManager simMgr)
			throws SimControlException {
		this.inTheAir = 0;
		this.onTheGround = 0;
		this.currentIndex = 0;
		this.runwayFree = true;
		this.runwayCheck = false;
		this.departureRunwayFree = true;
		this.arrivalTime = 1;
		this.landingTime = 1;
		this.departureTime = 0;
		this.arrivalInterval = 1;
		this.arrivalEvent = new ArrivalEvent(this, this.arrivalInterval);
		this.departureInterval = 1.5;
		this.departureTime = 1;
		this.queueLength = 0;
		this.sum = 0;
		this.min = 0;
		this.max = 0;

		do {
			this.arrivalInterval = (int) generator.exponential(0.5);
		} while (this.arrivalInterval <= 0);
		this.landingDuration = landingDuration;
		this.departureInterval = departureInterval;
		krokSymulacyjny = new SimStep(this, period);
		mvOnTheGround = new MonitoredVar(simMgr);
		mvInTheAir = new MonitoredVar(simMgr);
		mvRunwayFree = new MonitoredVar(simMgr);
		mvDepartureRunwayFree = new MonitoredVar(simMgr);
		mvQueueLength = new MonitoredVar(simMgr);
		mvRunwayFree.setValue(1);
		mvInTheAir.setValue(0);
		mvOnTheGround.setValue(0);
		mvDepartureRunwayFree.setValue(1);
	}

//	public void arrival() {
//		if (simTime() == arrivalTime) {
//			Plane plane = new Plane(rand.nextInt(3) + 1);
//			plane.arrivalTime = arrivalTime;
//
//			System.out.println("Category - " + plane.category);
//			System.out.println(("LandingDuration - " + plane.landingDuration));
//			System.out.println(("DepartureInterval - \n" + plane.departureInterval));
//			System.out.println();
//
//			arrivalTime += arrivalInterval;
//
//			inTheAir++;
//			mvInTheAir.setValue(inTheAir);
//
//			do {
//				this.arrivalInterval = (int) generator.exponential(3);
//			} while (this.arrivalInterval <= 0);
//
//			System.out.println(
//					simTime() + " - Przybył samolot. Nad lotniskiem altualnie jest: " + inTheAir + " samolot(ów)");
//
//			if (landingQueue.size() > 0) {
//				plane.landingTime = landingQueue.get(landingQueue.size() - 1).landingTime + plane.landingDuration;
//			} else {
//				plane.landingTime = (int) simTime() + plane.landingDuration;
//			}
//
//			if (runwayFree) {
//				runwayFree = false;
//				mvRunwayFree.setValue(0);
//			}
//
//
//			plane.departureTime = plane.landingTime + plane.departureInterval;
//
//			landingQueue.add(plane);
//			System.out.println("\nSAMOLOT: " + plane.landingTime + " - " + plane.departureTime);
//		}
//	}
//
//	public void landing() {
//
//		if (landingQueue.size() > 0 && landingQueue.get(0).landingTime == simTime()) {
//			inTheAir--;
//			mvInTheAir.setValue(inTheAir);
//			if (departureQueue.size() > 0) {
//				for (int i = 0; i < departureQueue.size(); i++) {
//					if (departureQueue.get(i).departureTime > landingQueue.get(0).departureTime) {
//						departureQueue.add(i, landingQueue.get(0));
//					}
//				}
//			} else {
//				departureQueue.add(landingQueue.get(0));
//			}
//			landingQueue.remove(0);
//			System.out.println(simTime() + " - Zwolniono pas lądowania");
//
//			if (inTheAir == 0) {
//				runwayFree = true;
//				mvRunwayFree.setValue(1);
//			}
//
//			onTheGround++;
//			mvOnTheGround.setValue(onTheGround);
//			System.out.println(simTime() + " - Wylądował samolot. Na płycie altualnie jest: " + onTheGround
//					+ " a w powietrzu " + inTheAir + " samolot(ów)");
//		}
//	}
//
//	public void departure() {
//		if (departureQueue.size() > 0) {
//			if (runwayCheck == false) {
//				for (int i = 0; i < departureQueue.size(); i++) {
//					if (departureQueue.get(i).departureTime <= (int) simTime()) {
//						mvDepartureRunwayFree.setValue(0);
//						departureQueue.get(i).departureEndTime = (int) simTime() + departureQueue.get(i).departureDuration;
//						currentIndex = i;
//						runwayCheck = true;
//						break;
//					}
//				}
//			} else if (departureQueue.get(currentIndex).departureEndTime == (int) simTime()) {
//				departureQueue.remove(currentIndex);
//				runwayCheck = false;
//				onTheGround--;
//				mvOnTheGround.setValue(onTheGround);
//				mvDepartureRunwayFree.setValue(1);
//			}
//		}
//	}
}