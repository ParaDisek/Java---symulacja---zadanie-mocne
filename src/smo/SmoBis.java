package smo;
/**
 * @author Dariusz Pierzchala
 * 
 * Description: Klasa kolejki obiekt�w - zg�osze� do gniazda obs�ugi. Sk�adowa
 * SmoAppDesKit.
 */

import java.util.LinkedList;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimEventBarrier;
import dissimlab.simcore.SimManager;



public class SmoBis extends BasicSimObj 
{
    private LinkedList <Zgloszenie> kolejka;
    private boolean wolne = true;
    private int maxDlKolejki;
    //
	SimEventBarrier semafor;
	//
    public RozpocznijObslugeBis rozpocznijObsluge;
    public ZakonczObslugeBis zakonczObsluge;
    //public OdblokujGniazdo odblokuj; 
    //
    public MonitoredVar MVczasy_obslugi;
    public MonitoredVar MVczasy_oczekiwania;
    public MonitoredVar MVdlKolejki;

    /** Creates a new instance of SmoBis 
     * @throws SimControlException */
    public SmoBis(int maxDlKolejki, SimEventBarrier bariera, SimManager simMgr) throws SimControlException
    {
        kolejka = new LinkedList <Zgloszenie>();
        this.maxDlKolejki = maxDlKolejki;
        this.semafor = bariera;
        //
        // Deklaracja zmiennych monitorowanych
        MVczasy_obslugi = new MonitoredVar(simMgr);
        MVczasy_oczekiwania = new MonitoredVar(simMgr);
        MVdlKolejki = new MonitoredVar(simMgr);
    }

    // Wstawienie zg�oszenia do kolejki
    public boolean dodaj(Zgloszenie zgl)
    {
    	if(liczbaZgl() < maxDlKolejki){
    		kolejka.add(zgl);
    		MVdlKolejki.setValue(kolejka.size());
    		return true;
    	}
        return false;
    }

    // Pobranie zg�oszenia z kolejki
    public Zgloszenie usun()
    {
    	Zgloszenie zgl = (Zgloszenie) kolejka.removeFirst();
        MVdlKolejki.setValue(kolejka.size());
        return zgl;
    }

    // Pobranie zg�oszenia z kolejki
    public boolean usunWskazany(Zgloszenie zgl)
    {
    	Boolean b= kolejka.remove(zgl);
        MVdlKolejki.setValue(kolejka.size());
        return b;
    }
    
    public int liczbaZgl()
    {
        return kolejka.size();
    }

	public boolean isWolne() {
		return wolne;
	}

	public void setWolne(boolean wolne) {
		this.wolne = wolne;
	}
	
	public SimEventBarrier getSemafor() {
		return semafor;
	}

	public void setSemafor(SimEventBarrier semafor) {
		this.semafor = semafor;
	}
	public int getMaxDlKolejki() {
		return maxDlKolejki;
	}

	public void setMaxDlKolejki(int maxDlKolejki) {
		this.maxDlKolejki = maxDlKolejki;
	}
}