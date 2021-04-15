package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;

public class Parque implements IParque{

	
	private int contadorPersonasTotales;
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	private static final int MAXIMOPERSONAS=50; 
	private static final int MINIMOPERSONAS=0; 
	
	public Parque() {
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();

	}


	@Override 
	public synchronized void entrarAlParque(String puerta){		
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		
		try {
			comprobarAntesDeEntrar();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)+1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
		
		
		// TODO
		checkInvariante();
		//Notificamos a todos la entrada de una persona al parque
		notifyAll();
		
	}
	
	@Override 
	public synchronized void salirDelParque(String puerta) { //nuevo
		
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		
		try {
			comprobarAntesDeSalir();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Disminuimos el contador total y el individual
		contadorPersonasTotales--;		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)-1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
	
	
		checkInvariante();
		//Notificamos a todos la salida de una persona al parque
		notifyAll();
	}
	
	
	private void imprimirInfo (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); //+ " tiempo medio de estancia: "  + tmedio);
		
		// Iteramos por todas las puertas e imprimimos sus entradas
		for(String p: contadoresPersonasPuerta.keySet()){
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));
		}
		System.out.println(" ");
	}
	
	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
			Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		return sumaContadoresPuerta;
	}
	
	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";
		assert contadoresPersonasPuerta.elements() != null/*; <= MINIMOPERSONAS*/: "INV: El mínimo de personas de las puertas tiene que ser mayor que MINIMOPERSONAS";
		assert contadorPersonasTotales <= MAXIMOPERSONAS: " INV. El numero totales de personas no puede pasar al MAXIMOPERSONAS";
	}

	protected synchronized void comprobarAntesDeEntrar() throws InterruptedException{	

		if (contadorPersonasTotales==MAXIMOPERSONAS) 
				wait();
	}

	protected synchronized void comprobarAntesDeSalir() throws InterruptedException{		

		if (contadorPersonasTotales==MINIMOPERSONAS)
				wait();
	}


	


}
