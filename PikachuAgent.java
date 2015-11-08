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

public class PikachuAgent extends Agent {
	int ESTADO=0;
	
	Behaviour PokemonDormido=new Dormido(this, 1880);
	Behaviour ib= new Batalla2();
			
	private class Batalla2 extends SimpleBehaviour{
		boolean fin = false;	

		@Override
		public void action() {
			if(ESTADO==0){
				ESTADO++;
	            AID id = new AID();
	            id.setLocalName("Jigglypuff");
	 
	        // Creación del objeto ACLMessage
	            ACLMessage mensaje = new ACLMessage(ACLMessage.QUERY_IF);
	 
	        //Rellenar los campos necesarios del mensaje
	            mensaje.setSender(getAID());
	            mensaje.setLanguage("Español");
	            mensaje.addReceiver(id);
	            mensaje.setContent("Electrico,Onda Trueno");
	            System.out.println("--------------AGENTE PIKACHU---------------");
	            System.out.println(getLocalName()+": usa Onda Trueno.");
	            System.out.println("-------------------------------------------");
	            
	        //Envia el mensaje a los destinatarios
	            send(mensaje);
			}
			else{
	            ACLMessage mensaje3 = blockingReceive();
	            if (mensaje3!= null){
	            	ACLMessage reply = mensaje3.createReply();
	            	if(mensaje3.getPerformative()== ACLMessage.PROPOSE){
	    	            String[] contentPokemon = mensaje3.getContent().split(",");
	    				String tipoAtaque= contentPokemon[0];
	    				String nombreAtaque= contentPokemon[1];
						
	    				if ((tipoAtaque != null) && (tipoAtaque.indexOf("Normal") != -1)&&(nombreAtaque.equals("Canto"))){
	    					System.out.println("--------------AGENTE PIKACHU---------------");
	    					System.out.println(getLocalName()+" se ha quedado dormido.");
	    					System.out.println("-------------------------------------------");
	    										
	    					fin=true;
						}
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
	//-------------------------------	Comportamiento TickerBehaviour	-------------------------------------
	//-------------------------------------------------------------------------------------------------------

	private class Dormido extends TickerBehaviour{
        public Dormido(Agent a, long intervalo){
            super(a, intervalo);
        }
        
        protected void onTick() {
        	System.out.println("--------------AGENTE PIKACHU---------------");
            System.out.println("Agente "+myAgent.getLocalName()+" sigue dormido.. ZZzzz..");
            System.out.println("-------------------------------------------");
        } 
    }
	
	//-------------------------------------------------------------------------------------------------------
	//-------------------------------	Comportamiento WakerBehaviour	-------------------------------------
	//-------------------------------------------------------------------------------------------------------
	
	private class Despierta extends WakerBehaviour{
		public Despierta(Agent a, long timeout) {
			super(a, timeout);
		}
		
		protected void onWake() {
			System.out.println("--------------AGENTE PIKACHU---------------");
	        System.out.println("Agente "+myAgent.getLocalName()+" se ha despertado pero esta muy debil por los golpes, cae al suelo.");
	        System.out.println("-------------------------------------------");
//	        removeBehaviour(ib);
//	        removeBehaviour(PokemonDormido);
	        myAgent.doDelete();
		}		
	}
	
	
	private class ComunicacionConEntrenador extends SimpleBehaviour{
		private boolean fin = false;
		
		public void action(){
			
            ACLMessage mensaje = blockingReceive();
            if (mensaje!= null){
            	ACLMessage reply = mensaje.createReply();
            	if(mensaje.getPerformative()== ACLMessage.QUERY_IF){
    				String content = mensaje.getContent(); 
    				if ((content != null) && (content.indexOf("Te elijo Pikachu") != -1)){
    					System.out.println("--------------AGENTE PIKACHU---------------");
    					System.out.println(getLocalName()+": se alegra de ser elegido, esta muy activo.");
    					System.out.println("-------------------------------------------");
    					reply.setPerformative(ACLMessage.PROPOSE);
    					reply.setContent("Muy activo");
    					reply.addReceiver(mensaje.getSender());
    					send(reply);
    					
    					ACLMessage mensaje1 = blockingReceive();
    		            if (mensaje1!= null){
    		            	if(mensaje1.getPerformative()== ACLMessage.INFORM){
    		    				String content1 = mensaje1.getContent(); 
    		    				if ((content1 != null) && (content1.indexOf("Usar Onda Trueno") != -1)){
    		    					this.myAgent.addBehaviour(ib);
    		    					fin = true;
    							}    				
    		    		    }  
    		            }
					}    				
    		    }  
            }
        }
        
		public boolean done(){
            return fin;
        }
	}
	
	protected void setup(){
		addBehaviour(new ComunicacionConEntrenador());
		addBehaviour(PokemonDormido);
		addBehaviour(new Despierta(this,10000));
	}
	
	protected void takeDown() {
		System.out.println("--------------AGENTE PIKACHU---------------");
		System.out.println(this.getLocalName() + " ha sido derrotado.");
		System.out.println("-------------------------------------------");
	}
}
