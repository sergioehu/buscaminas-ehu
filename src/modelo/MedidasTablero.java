package modelo;

public enum MedidasTablero {

	//format = "7x10 (Nivel 1)"
	
	medida1("7x10", " (Nivel 1)"), medida2("10x15", " (Nivel 2)"), medida3("12x25", " (Nivel 3)");
	
	private String medidas;
	private String nombre;
	
	private MedidasTablero (String medidas, String nombre){
		this.medidas = medidas;
		this.nombre = nombre;
	}
	
	public String getMedidas() {
		return medidas;
	}	
	
	public String getNombre() {
		return nombre;
	}
	
}
