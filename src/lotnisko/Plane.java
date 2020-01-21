package lotnisko;

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimObj;

public class Plane extends BasicSimObj {

    int passengers;
    int category;
    int landingDuration = 0;
    int departureInterval = 0;
    int arrivalTime;
    int landingTime;
    double departureTime;
    int departureDuration = 1;
    int departureEndTime;

    SimGenerator generator = new SimGenerator();

    public Plane(int category) {

        this.category = category;
        do {
            this.departureInterval = (int) Math.round(generator.uniform(1.0, 2.0));
        } while (this.departureInterval <= 0);

        switch (category) {
            case 1:
                do {
                    this.landingDuration = (int) Math.round(generator.normal(1.0, 5.0));
                } while (this.landingDuration <= 0);
                break;
            case 2:
                do {
                    this.landingDuration = (int) Math.round(generator.uniform(1.0, 5.0));
                } while (this.landingDuration <= 0);
                break;
            case 3:
                this.landingDuration = 3;
            default:
                break;
        }

        this.passengers = (int) 2 * this.landingDuration;
        System.out.println("Pasazerowie " + this.passengers);
    }
}
