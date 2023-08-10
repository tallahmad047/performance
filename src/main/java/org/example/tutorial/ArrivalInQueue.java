package org.example.tutorial;

import umontreal.ssj.simevents.Event;
import umontreal.ssj.simevents.Sim;

import static org.example.tutorial.SimulateOneDay.getType;
import static org.example.tutorial.SimulateOneDay.tab;

public class ArrivalInQueue extends Event {
    Customer customer;
    public ArrivalInQueue(Customer customer){
        this.customer=customer;
    }
    @Override
    public void actions() {

            SimulateOneDay.getSizeQueue(customer);
            if(getType(customer.type) != -1)
                customer.LES=SimulateOneDay.tab2[getType(customer.type)];
            customer.nbServeurs=SimulateOneDay.lservice.size();
            if(customer.beginServiceTime<=customer.arrivalTime+3)
                new EnterInService(customer).schedule(0.0);
            else
            {
                if(getType(customer.type) != -1)
                    tab[getType(customer.type)].add(customer);
                if(customer.beginServiceTime==-1)
                    new Abandon(customer).schedule(customer.endServiceTime- Sim.time());
                else
                {
                    new EnterInService(customer).schedule(customer.beginServiceTime-Sim.time());
                }

            }




    }
}
