package smo;
/**
 * @author Dariusz Pierzchala
 * 
 * Description: Klasa kolejki obiekt�w - zg�osze� do gniazda obs�ugi. Sk�adowa
 * SmoAppDesKit.
 */

import java.util.LinkedList;

import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimEventBarrier;
import dissimlab.simcore.SimManager;
import dissimlab.simcore.SimControlException;



public class Smo extends BasicSimObj
{
    private LinkedList <Zgloszenie> kolejka;
    private SmoBis smo2;
	SimEventBarrier semafor;
	private boolean wolne = true;
    public RozpocznijObsluge rozpocznijObsluge;
    public ZakonczObsluge zakonczObsluge;
    public MonitoredVar MVczasy_obslugi;
    public MonitoredVar MVczasy_oczekiwania;
    public MonitoredVar MVdlKolejki;
    public MonitoredVar MVutraconeZgl;
	
    /** Creates a new instance of Smo 
     * @throws SimControlException */
    public Smo(SmoBis smo, SimEventBarrier bariera, SimManager simMgr) throws SimControlException
    {
        // Utworzenie wewn�trznej listy w kolejce
        kolejka = new LinkedList <Zgloszenie>();
        // Nastepne SMO
        smo2 = smo;
        // Deklaracja zmiennych monitorowanych
        MVczasy_obslugi = new MonitoredVar(simMgr);
        MVczasy_oczekiwania = new MonitoredVar(simMgr);
        MVdlKolejki = new MonitoredVar(simMgr);
        MVutraconeZgl = new MonitoredVar(simMgr);
        this.semafor = bariera;
    }

    // Wstawienie zg�oszenia do kolejki
    public int dodaj(Zgloszenie zgl)
    {
        kolejka.add(zgl);
        MVdlKolejki.setValue(kolejka.size());
        return kolejka.size();
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
	
    public SmoBis getSmo2() {
		return smo2;
	}

	public void setSmo2(SmoBis smo2) {
		this.smo2 = smo2;
	}
	
	public SimEventBarrier getSemafor() {
		return semafor;
	}

	public void setSemafor(SimEventBarrier semafor) {
		this.semafor = semafor;
	}
}