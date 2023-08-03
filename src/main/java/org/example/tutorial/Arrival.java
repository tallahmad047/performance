package org.example.tutorial;

import umontreal.ssj.simevents.Event;
import umontreal.ssj.simevents.Sim;

import static org.example.tutorial.SimulateOneDay.getIndice;
import static org.example.tutorial.SimulateOneDay.tab;

public class Arrival extends Event {
    Customer customer;
    public Arrival(Customer customer){
        this.customer=customer;
    }
    @Override
    public void actions() {

            SimulateOneDay.getSizeQueue(customer);
            if(getIndice(customer.type) != -1)
                customer.LES=SimulateOneDay.tab2[getIndice(customer.type)];
            customer.numberAgents=SimulateOneDay.lservice.size();
            if(customer.beginServiceTime<=customer.arrivalTime+3)
                new EnterInService(customer).schedule(0.0);
            else
            {
                if(getIndice(customer.type) != -1)
                    tab[getIndice(customer.type)].add(customer);
                if(customer.beginServiceTime==-1)
                    new Abandon(customer).schedule(customer.endServiceTime- Sim.time());
                else
                {
                    new EnterInService(customer).schedule(customer.beginServiceTime-Sim.time());
                }

            }




    }
}
