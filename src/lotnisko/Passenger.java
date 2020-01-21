package lotnisko;

import dissimlab.simcore.BasicSimObj;

public class Passenger extends BasicSimObj {
    double landingTime;


    public Passenger(double landingTime) {
        this.landingTime = landingTime;
    }
}
