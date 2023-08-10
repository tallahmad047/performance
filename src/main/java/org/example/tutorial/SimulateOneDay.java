package org.example.tutorial;

import umontreal.ssj.simevents.Event;
import umontreal.ssj.simevents.Sim;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;



public class SimulateOneDay {


    /*: le type client
    Dr Thiongane M2BI UCAD 3
    Mod´elisation Stochastique Ann´ee: 2022
    T, la longueur de sa file d’attente qT , les longueurs des files des clients servis
    par les mˆemes agents r(un vecteur), l’heure d’arriv´ee du client t, le nombre d’agents s
    ayant les comp´etences de servir le client, le temps d’attente
    du dernier client `a entrer en service LES. Toutes ces informations forment
    un vecteur x qui d´ecrit l’´etat du syst`eme `a l’arriv´ee d’un nouveau client,
    x = (T,qT , `,t,s,LES)*/

    static LinkedList<Customer>[] tab =new LinkedList[8];

    //la liste des client en service
    static LinkedList<Customer> lservice=new LinkedList<Customer>();

    //Contient les LES des 8  types de service
    static double[] tab2 =new double[8];

    //la liste des clients qui ont été déja servis
    static ArrayList<Customer> customerServed=new ArrayList<Customer>();

    public SimulateOneDay() {
        for(int i=0;i<8;i++)
        {
            //liste des clients
            tab[i]=new LinkedList<Customer>();
            tab2[i]=0.0;
        }
    }
    public void createDayCustomers(String dayFile) throws IOException {
        /*Cette méthode lit les données des clients à partir d'un fichier (dont le chemin est passé en argument)
         et crée des objets Customer en fonction de ces données. Les clients sont ensuite planifiés pour arriver
         à leurs heures d'arrivée respectives dans la simulation. Les attributs des  clients
          (comme le temps d'attente) sont également calculés à partir des informations fournies
           dans le fichier.
        * */
        BufferedReader br=new BufferedReader(new FileReader(dayFile));
        String ligne = null;
        System.out.println(dayFile);
        while((ligne=br.readLine()) != null){
            Customer cust=new Customer();
            String[] split = ligne.split(",");
            String arrTime= split[0].split(" ")[1];
            double arrivalTime=getTime(arrTime) ;
            int type=Integer.parseInt(split[1]);
            String bsTime=null;
            double beginServiceTime=-2;
            if(!split[3].equals("NULL"))
            { bsTime= split[3].split(" ")[1];
                beginServiceTime=getTime(bsTime);
                cust.beginServiceTime=beginServiceTime;
            }
            if(split[6].equals("NULL"))
                break;
            String endTime= split[6].split(" ")[1];
            double endServiceTime=getTime(endTime);
            cust.arrivalTime=arrivalTime;
            cust.type=type;
            if(beginServiceTime!=-2)
                cust.waitingTime=beginServiceTime-arrivalTime;
            else
                cust.waitingTime=endServiceTime-arrivalTime;

            cust.endServiceTime=endServiceTime;
            if(cust.arrivalTime >= 0)
                new ArrivalInQueue(cust).schedule(cust.arrivalTime - Sim.time());
        }


    }
    public void createDataset() throws IOException{
        /* Cette méthode lit les données des clients à partir d'un fichier (dont le chemin est passé en argument) et
        crée des objets Customer en fonction de ces données. Les clients sont ensuite planifiés pour arriver à leurs
         heures d'arrivée respectives dans la simulation. Les attributs des clients (comme le temps d'attente)
          sont également calculés à partir des informations fournies dans le fichier.*/
        String x=null;
        String y=null;
        String path = "src/main/java/projects/";
        BufferedWriter br=new BufferedWriter(new FileWriter(path+"datasets.csv"));
        String entete="Type,"+"ArrivalTime,"+"LES,"+"numberAgents,"+"srv1,"
                +"srv2,"+"srv3,"+"srv4,"+"srv5,"+"srv6,"+"srv7,"+"srv8,"+"WaitingTime\n";
        br.write(entete);
        for(Customer c:customerServed)
        {  y=""+c.waitingTime;
            x=c.type+","+c.arrivalTime+","+c.LES+","+c.nbServeurs+","+c.srv1+","+c.srv2+","+c.srv3+","
                    +c.srv4+","+c.srv5+"," +c.srv6+","+c.srv7+","+c.srv8;
            br.write(x+","+y+"\n");
        }
        br.close();
    }
    class EndOfSim extends Event{
        public void actions(){
            Sim.stop();
        }
    }
    public void simulateOneDay( String file) throws IOException{
        /* Cette méthode initialise une simulation pour une journée en appelant Sim.init(),
         crée les clients à partir des données du fichier (appelant la méthode createDayCustomers(file)),
        et planifie la fin de la simulation après 12 heures (43200 secondes).*/
        Sim.init();
        createDayCustomers(file);
        new EndOfSim().schedule (43200);
        Sim.start();
    }


    public static void getSizeQueue(Customer customer) {
        /* Cette méthode calcule la longueur de la file d'attente pour
        chaque type de service et met à jour les attributs du client correspondant (srv1, srv2, ..., srv8).*/
        customer.srv1 = tab[0].size();
        customer.srv2 = tab[1].size();
        customer.srv3 = tab[2].size();
        customer.srv4 = tab[3].size();
        customer.srv5 = tab[4].size();
        customer.srv6 = tab[5].size();
        customer.srv7 = tab[6].size();
        customer.srv8 = tab[7].size();
    }
    public static int getType(int type){
        /*Cette méthode prend en paramètre un type de client et
         renvoie l'indice correspondant dans les tableaux (tab et tab2).
        * */
        if(type==30175)
            return 0;
        else if(type==30560)
            return 1;
        else if(type==30172)
            return 2;
        else if(type==30181)
            return 3;
        else if(type==30179)
            return 4;
        else if(type==30066)
            return 5;
        else if(type==30518)
            return 6;
        else if(type==30241)
            return 7;
        else
            return -1;
    }
    public double getTime(String l){
        String t[]=l.split(":");
        return (Double.parseDouble(t[0])*3600+
                Double.parseDouble(t[1])*60+Double.parseDouble(t[2])-8*3600);
    }
    public static void main(String args[]) throws IOException{
        SimulateOneDay tf=new SimulateOneDay();

        //Va lire les chemins des fichiers journaliers qui ont étaient stockés dans dates.csv qui est dans le dossier datasets
        String path = "src/main/java/projects/datasets/dates.csv";
        BufferedReader br=new BufferedReader(new FileReader(path));
        String ligne= null;

        while((ligne=br.readLine()) != null){
            tf.simulateOneDay(ligne);
            tf.createDataset();
        }
    }
}
