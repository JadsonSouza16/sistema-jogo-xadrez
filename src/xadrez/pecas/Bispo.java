package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.XadrezPeca;

public class Bispo extends XadrezPeca {

	public Bispo(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] movimentosPossivel() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// nw
		p.setValor(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while (getTabuleiro().verificaPosicao(p) && !getTabuleiro().verificaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValor(p.getLinha() - 1, p.getColuna() - 1);
		}
		if (getTabuleiro().verificaPosicao(p) && verificaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// ne
		p.setValor(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while (getTabuleiro().verificaPosicao(p) && !getTabuleiro().verificaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValor(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTabuleiro().verificaPosicao(p) && verificaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// se
		p.setValor(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getTabuleiro().verificaPosicao(p) && !getTabuleiro().verificaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValor(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTabuleiro().verificaPosicao(p) && verificaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sw
		p.setValor(posicao.getLinha() + 1, posicao.getColuna() - 1);
		while (getTabuleiro().verificaPosicao(p) && !getTabuleiro().verificaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValor(p.getLinha() + 1, p.getColuna() - 1);
		}
		if (getTabuleiro().verificaPosicao(p) && verificaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}
}
