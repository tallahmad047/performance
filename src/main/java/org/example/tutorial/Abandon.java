package org.example.tutorial;

import umontreal.ssj.simevents.Event;
import static org.example.tutorial.SimulateOneDay.*;

public class Abandon extends Event {
    Customer customer;
    public Abandon(Customer customer){
        this.customer=customer;
    }
    @Override
    public void actions() {
        if(tab[getIndice(customer.type)].contains(customer))
            tab[getIndice(customer.type)].remove(customer);

    }
}
