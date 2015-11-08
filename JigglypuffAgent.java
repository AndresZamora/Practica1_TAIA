package agentes;


import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

public class JigglypuffAgent extends Agent {
	
	private class Batalla3 extends SimpleBehaviour{
		private boolean fin = false;
		
        public void action(){
            ACLMessage mensaje = blockingReceive();
            
            if (mensaje!= null){
            	ACLMessage reply = mensaje.createReply();
            	if(mensaje.getPerformative()== ACLMessage.QUERY_IF){
    				String[] contentPokemon = mensaje.getContent().split(",");
    				String tipoPokemon= contentPokemon[0];
    				
    				if ((tipoPokemon != null) && (tipoPokemon.indexOf("Electrico") != -1)){
    					System.out.println("-------------AGENTE JIGGLYPUFF-------------");
    					System.out.println("El ataque hizo enojar a "+getLocalName()+ ", va a contratacar.");
                        System.out.println(getLocalName()+": usa Canto");
                        System.out.println("-------------------------------------------");
    					reply.setPerformative(ACLMessage.PROPOSE);
    					reply.setContent("Normal,Canto");
    					reply.addReceiver(mensaje.getSender());
    				}
    				send(reply);
    		    }
                fin = true;
            }   				
        }
        
        public boolean done(){
            return fin;
        }		
	}
	
	//TAIA 2015-2016: ENTREGA INDIVIDUAL
	
	
	//-------------------------------------------------------------------------------------------------------
	//----------------------------------	Comportamiento Behaviour	-------------------------------------
	//-------------------------------------------------------------------------------------------------------
	
	private class DormidoStepBehaviour extends Behaviour {
	    private int step = 0;

	    public void onStart() {
	    	System.out.println("-----> "+getLocalName()+": empieza comportamiento DormidoStepBehaviour");
	    	super.onStart();
	    }

	    public void action() {
	      switch (step) {
	      case 0:
	    	  doWait(2000);
	    	  break;
	      case 1:
	    	  System.out.println("-------------AGENTE JIGGLYPUFF-------------");
	    	  System.out.println(getLocalName()+" le pinto la cara a Pikachu.");
	    	  System.out.println("-------------------------------------------");
	    	  doWait(2000);
	    	  break;
	      case 2:
	    	  System.out.println("-------------AGENTE JIGGLYPUFF-------------");
	    	  System.out.println(getLocalName()+" le ha dado un bofetón a Pikachu.");
	    	  System.out.println("-------------------------------------------");
	    	  doWait(2000);
	    	  break;
	      case 3:
	    	  System.out.println("-------------AGENTE JIGGLYPUFF-------------");
	    	  System.out.println(getLocalName()+" ha usado Giro Bola contra Pikachu.");
	    	  System.out.println("-------------------------------------------");
	    	  doWait(2000);
	    	  break;
	      case 4:
	    	  System.out.println("-------------AGENTE JIGGLYPUFF-------------");
	    	  System.out.println(getLocalName()+" ha usado Golpe cuerpo contra Pikachu.");
	    	  System.out.println("-------------------------------------------");
	    	  doWait(2000);
	    	  break;
	      }
	      step++;
	    } 

	    public boolean done() {
	      return step == 5;
	    } 

	    public int onEnd() {
	    	System.out.println("-----> "+getLocalName()+": termina comportamiento DormidoStepBehaviour");
	        myAgent.doDelete();
	        return super.onEnd();
	    } 
	  }
	
	protected void setup(){
		addBehaviour(new Batalla3());
		addBehaviour(new DormidoStepBehaviour());
	}
	
	protected void takeDown() {
		System.out.println("-------------AGENTE JIGGLYPUFF-------------");
		System.out.println(this.getLocalName() + " ha sido vencedor.");
		System.out.println("-------------------------------------------");
	}

}
