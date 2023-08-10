package org.example.tutorial;

import umontreal.ssj.simevents.Event;

import static org.example.tutorial.SimulateOneDay.*;

public class EnterInService extends Event {
    Customer customer;
    public EnterInService(Customer customer){
        this.customer=customer;
    }
    @Override
    public void actions() {


            if(getType(customer.type) != -1){
                if(tab[getType(customer.type)].contains(customer))
                    tab[getType(customer.type)].remove(customer);
            }
            lservice.add(customer);
            if(getType(customer.type) != -1)
                tab2[getType(customer.type)]=customer.waitingTime;
            new Departure(customer).schedule(customer.endServiceTime-customer.beginServiceTime);


    }
}
