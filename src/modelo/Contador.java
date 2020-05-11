package modelo;

//Manneja el contador de minas localizadas respecto de las totales
public class Contador {
	private int minasTotales;
	private int minasContadas;
	public Contador(int numMinasInicial) {
		minasTotales=numMinasInicial;
	}
	public String actualizarContador(int num)
	{
		minasContadas=minasContadas + num;
		return (minasContadas+"/"+minasTotales);
	}
	public int obtConteo() {
		return minasContadas;
	}

}
