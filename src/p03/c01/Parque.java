package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;

public class Parque implements IParque{

	// TODO 
	private int contadorPersonasTotales;
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	private int maximoPersonas; //nuevo
	private int minimoPersonas=0; //nuevo
	
	public Parque() {	// TODO
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
		// TODO
		maximoPersonas=40; //nuevo
	}


	@Override //synchronized?
	public  void entrarAlParque(String puerta){		// TODO
		
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
		
		// TODO
		checkInvariante();
		
	}
	
	// 
	// TODO Método salirDelParque
	//
	@Override //synchronized?
	public synchronized void salirDelParque(String puerta) { //nuevo
		try {
			comprobarAntesDeSalir();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		salirDelParque(puerta);
		//¿Notify?
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
		// TODO 
		//assert minimoPersonas :
		// TODO
		//assert maximoPersonas
	}
	//synchronized?
	protected synchronized void comprobarAntesDeEntrar() throws InterruptedException{	// TODO

		if (contadorPersonasTotales==maximoPersonas) 
				wait();
	}
	//synchronized?
	protected synchronized void comprobarAntesDeSalir() throws InterruptedException{		// TODO

		if (contadorPersonasTotales==minimoPersonas)
			wait();
	}


	


}
