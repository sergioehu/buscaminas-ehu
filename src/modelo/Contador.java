package modelo;

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
