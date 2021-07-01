package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class XadrezPeca extends Peca {
	
	private Color color;
	private int contaMovimento;
	

	public XadrezPeca(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro);
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

	public int getContaMovimento() {
		return contaMovimento;
	}
	
	public void incrementaContaMovimento() {
		contaMovimento++;
	}
	
	public void decrementaContaMovimento() {
		contaMovimento--;
	}
	
	public XadrezPosicao getXadrezPosicao() {
		return XadrezPosicao.fromPosicao(posicao);
	}
	
	protected boolean verificaOponente(Posicao posicao) {
		XadrezPeca p = (XadrezPeca)getTabuleiro().peca(posicao);
		return p != null && p.getColor() != color;
	}
}
