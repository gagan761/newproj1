

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
// Parent class
import java.time.*;
import java.util.*;
import java.util.stream.*;

import static java.util.UUID.*;


enum VehicleType {BIKE,CAR,TRUCK}
class Vehicle{
    String vehicleId;
    VehicleType type ;

    public Vehicle(String Vehicleid, VehicleType type) {
        this.vehicleId = Vehicleid;
        this.type = type;
    }
}

class ParkingSlot{
    Vehicle parkedVehicle;
    String slotId;
    boolean isParked;
    VehicleType type;


    public ParkingSlot(VehicleType type, String slotId, boolean isParked) {
        this.type=type;
        this.slotId = slotId;
        this.isParked = isParked;
    }
}
class Ticket{
    UUID TicketNo;
    Date entryTime;
    Vehicle vehicle;
    String slotId;



    public Ticket(UUID ticketNo, Date entryTime, Vehicle vehicle) {
        TicketNo = ticketNo;
        this.entryTime = entryTime;
        this.vehicle = vehicle;
    }
}
class Receipt{
    UUID TicketNo;
    Date exitTime;
    Vehicle vehicle;
    int price;


    public Receipt(UUID ticketNo, Date exitTime, Vehicle vehicle, int price) {
        TicketNo = ticketNo;
        this.exitTime = exitTime;
        this.vehicle = vehicle;
        this.price = price;
    }
}



class MainParkingLot {



    List<ParkingSlot> slots = new ArrayList<>();
    Map<UUID, Ticket> activeTickets = new HashMap<>();


    public MainParkingLot() {

        slots.add(new ParkingSlot(VehicleType.BIKE, "1a", false));
        slots.add(new ParkingSlot(VehicleType.BIKE, "2a", false));
        slots.add(new ParkingSlot(VehicleType.BIKE, "3a", false));
        slots.add(new ParkingSlot(VehicleType.CAR, "1b", false));
        slots.add(new ParkingSlot(VehicleType.CAR, "2b", false));
        slots.add(new ParkingSlot(VehicleType.CAR, "3b", false));
        slots.add(new ParkingSlot(VehicleType.TRUCK, "1c", false));
        slots.add(new ParkingSlot(VehicleType.TRUCK, "2c", false));
    }

    Ticket ParkVehicle(Vehicle vec) {

        for (ParkingSlot slot : slots) {
            if (!slot.isParked && slot.type == vec.type) {
                slot.isParked = true;     //park the vehicle
                slot.parkedVehicle = vec;

                Ticket ticket = new Ticket(randomUUID(), new Date(), vec);
                ticket.slotId=slot.slotId;
                activeTickets.put(ticket.TicketNo, ticket);
                return ticket;//generate the ticket
            }
        }
        return null;


    }

    Receipt UnParkVehicle(Ticket tkt) {

        if (activeTickets.get(tkt.TicketNo) == null)
            return null;

        for (ParkingSlot slot : slots) {
            if (slot.slotId.equals(tkt.slotId)) {
                slot.isParked = false;     //park the vehicle
                slot.parkedVehicle = null;
                activeTickets.remove(tkt.TicketNo);
            }
        }
        int price = calculatePrice(tkt.entryTime);
        return new Receipt(tkt.TicketNo, new Date(), tkt.vehicle, price); //generate the ticket


        //if ticket is valid unpark the vehicle
        //free the slot
        //generate the receipt and include the price of thata
    }

    private int calculatePrice(Date entryTime) {
        return 5;
    }

    void ShowSlotsAvailable() {


        int freeBikes = 0, freeCars = 0, freeTrucks = 0;
        for (ParkingSlot slot : slots) {
            if (!slot.isParked) {
                if (slot.type == VehicleType.BIKE)
                    freeBikes++;
                if (slot.type == VehicleType.TRUCK)
                    freeTrucks++;
                if (slot.type == VehicleType.CAR)
                    freeCars++;
            }
        }

        System.out.println("Free Bikes: " + freeBikes);
        System.out.println("Free Cars: " + freeCars);
        System.out.println("Free Trucks: " + freeTrucks);
    }
}



 class Main{




    // Demo
    public static void main(String[] args) throws InterruptedException {

        Vehicle car1 = new Vehicle("1a", VehicleType.CAR);
        Vehicle bike1 = new Vehicle("1b", VehicleType.BIKE);
        Vehicle truck1 = new Vehicle("1c", VehicleType.TRUCK);

        MainParkingLot parkingLot1= new MainParkingLot();
        parkingLot1.ShowSlotsAvailable();
       Ticket t1=parkingLot1.ParkVehicle(car1);
       Ticket t2=parkingLot1.ParkVehicle(bike1);
       parkingLot1.ParkVehicle(truck1);
        System.out.println("hello");
        parkingLot1.ShowSlotsAvailable();


        System.out.println("hello");
        parkingLot1.UnParkVehicle(t1);
        parkingLot1.UnParkVehicle(t2);
        parkingLot1.ShowSlotsAvailable();

    }}