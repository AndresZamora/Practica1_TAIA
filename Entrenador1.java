package agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class Entrenador1 extends Agent {
	
	private class ElegirPikachu extends SimpleBehaviour{
		boolean fin = false;
		
		public void onStart(){
			System.out.println("......."+getLocalName()+" divisa un pokemon salvaje.......");
		}

		@Override
		public void action() {
			AID id = new AID();
            id.setLocalName("Pikachu");
 
            ACLMessage mensaje = new ACLMessage(ACLMessage.QUERY_IF);
            mensaje.setSender(getAID());
            mensaje.setLanguage("Español");
            mensaje.addReceiver(id);
            mensaje.setContent("Te elijo Pikachu");
            System.out.println("-------------AGENTE ENTRENADOR-------------");
            System.out.println(getLocalName()+": ha elegido a Pikachu");
            System.out.println("-------------------------------------------");
            
            send(mensaje);            
            ACLMessage mensaje2 = blockingReceive();
            if (mensaje2!= null){
            	ACLMessage reply = mensaje2.createReply();
            	if(mensaje2.getPerformative()== ACLMessage.PROPOSE){
            		String content = mensaje2.getContent();
    				if ((content != null) && (content.indexOf("Muy activo") != -1)){
    					System.out.println("-------------AGENTE ENTRENADOR-------------");
    					System.out.println(getLocalName()+": te ves muy activo y con energias "+mensaje2.getSender().getLocalName()+", utiliza Onda Trueno.");
    					System.out.println("-------------------------------------------");
    					reply.setPerformative(ACLMessage.INFORM);
    					reply.setContent("Usar Onda Trueno");
    					reply.addReceiver(mensaje2.getSender());
    					send(reply);
    					fin = true;    					    		            
					}
    		    }	            	
            }
		}

		@Override
		public boolean done() {
			return fin;
		}
	}
	
	//-------------------------------------------------------------------------------------------------------
	//------------------------------	Comportamiento CyclicBehaviour	-------------------------------------
	//-------------------------------------------------------------------------------------------------------
	
	private class DarAnimos extends CyclicBehaviour{
		int i;
		
		public void action() {
			if((i!=0)&&(i<=4)){
				System.out.println("-------------AGENTE ENTRENADOR-------------");
				System.out.println(myAgent.getLocalName()+": vamos Pikachu despiertaaa!!.");
				System.out.println("-------------------------------------------");
			}
			i++;
			block(2100);
			if(i>5){
				System.out.println("-------------AGENTE ENTRENADOR-------------");
				System.out.println("Agente "+myAgent.getLocalName()+" ha sido vencido, por pokemon salvaje.");
				System.out.println("-------------------------------------------");
		        myAgent.doDelete();
			}
		}
	}
	
	
	protected void setup(){
		//-------------------------------------------------------------------------------------------------------
		//--------------------------	Comportamiento SequentialBehaviour	-------------------------------------
		//-------------------------------------------------------------------------------------------------------
		SequentialBehaviour comportamientos = new SequentialBehaviour();
		comportamientos.addSubBehaviour(new ElegirPikachu());
		comportamientos.addSubBehaviour(new DarAnimos());
		addBehaviour(comportamientos);
	}
	
	protected void takeDown() {
		System.out.println("-------------AGENTE ENTRENADOR-------------");
		System.out.println(this.getLocalName() + " ha sido derrotado, nunca será un Maestro Pokemon.");
		System.out.println("-------------------------------------------");
	}
}
