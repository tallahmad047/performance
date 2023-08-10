package org.example.tutorial;

import umontreal.ssj.simevents.Event;

import static org.example.tutorial.SimulateOneDay.lservice;
import static org.example.tutorial.SimulateOneDay.*;

public class Departure  extends Event {
    Customer customer;
    public Departure(Customer customer)
    {this.customer=customer;}
    @Override
    public void actions() {
        if(lservice.contains(customer))
            lservice.remove(customer);
        SimulateOneDay.customerServed.add(customer);

    }
}
