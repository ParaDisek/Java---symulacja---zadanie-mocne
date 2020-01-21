package smo;

import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;

/**
 * Description: Klasa zgloszenia obs≥ugiwanego w gnieüdzie obs≥ugi.
 * 
 * @author Dariusz Pierzchala
 */

public class Zgloszenie extends BasicSimObj
{
    double czasOdniesienia;
    static int nr=0;
    int tenNr;
    public Smo smo;
    public KoniecNiecierpliwosci koniecNiecierpliwosci;

    public int getTenNr() {
		return tenNr;
	}

	public void setTenNr() {
		this.tenNr = nr++;
	}

	public Zgloszenie(double Czas, Smo smo) throws SimControlException
    {
        czasOdniesienia = Czas;
        setTenNr();
        this.smo = smo;
        StartNiecierpliwosci stN = new StartNiecierpliwosci(this);
    }

    public void setCzasOdniesienia(double t)
    {
        czasOdniesienia = t;
    }

    public double getCzasOdniesienia()
    {
        return czasOdniesienia;
    }
}