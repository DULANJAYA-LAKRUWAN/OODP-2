package lk.jiat.jms;

interface AirTrafficControl {
    void requestTakeoff(Flight f);
    void requestLanding(Flight f);
    void notifyFlight(String message, Flight f);
}

class AirportControlTower implements AirTrafficControl {

    @Override
    public void requestTakeoff(Flight f) {
        System.out.println("Flight: " + f.getNumber() + " request permission to take off");
        notifyFlight("Take-off approved", f);
    }

    @Override
    public void requestLanding(Flight f) {
        System.out.println("Flight: " + f.getNumber() + " request permission to landing");
        notifyFlight("Landing approved", f);
    }

    @Override
    public void notifyFlight(String message, Flight f) {
        f.receiveMessage(message);
    }
}

abstract class Flight {

    private AirTrafficControl airTrafficControl;
    private String number;

    public Flight(AirTrafficControl airTrafficControl, String number) {
        this.airTrafficControl = airTrafficControl;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void requestTakeoff() {
        airTrafficControl.requestTakeoff(this);
    }

    public void requestLanding() {
        airTrafficControl.requestLanding(this);
    }

    public abstract void receiveMessage(String message);
}

class FlightA extends Flight{
    public FlightA(AirTrafficControl airTrafficControl,String number) {
        super(airTrafficControl,number);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println("Flight A:"+getNumber()+" New message: "+message);
    }
}

class FlightB extends Flight{
    public FlightB(AirTrafficControl airTrafficControl,String number) {
        super(airTrafficControl,number);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println("Flight B:"+getNumber()+" New message: "+message);
    }
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        AirportControlTower airportControlTower = new AirportControlTower();

        Flight fa1 = new FlightA(airportControlTower,"123");

        Flight fb1 = new FlightB(airportControlTower,"456");
        Flight fb2 = new FlightB(airportControlTower,"789");

        fa1.requestLanding();
        System.out.println();

        fb1.requestLanding();
        System.out.println();

        fb2.requestTakeoff();


    }
}