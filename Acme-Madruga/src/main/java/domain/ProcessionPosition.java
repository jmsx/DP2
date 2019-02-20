
package domain;

public class ProcessionPosition extends DomainEntity {

	private int	fila;
	private int	columna;


	public int getFila() {
		return this.fila;
	}

	public void setFila(final int fila) {
		this.fila = fila;
	}

	public int getColumna() {
		return this.columna;
	}

	public void setColumna(final int columna) {
		this.columna = columna;
	}

}
