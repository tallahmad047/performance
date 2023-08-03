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


            if(getIndice(customer.type) != -1){
                if(tab[getIndice(customer.type)].contains(customer))
                    tab[getIndice(customer.type)].remove(customer);
            }
            lservice.add(customer);
            if(getIndice(customer.type) != -1)
                tab2[getIndice(customer.type)]=customer.waitingTime;
            new Departure(customer).schedule(customer.endServiceTime-customer.beginServiceTime);


    }
}
