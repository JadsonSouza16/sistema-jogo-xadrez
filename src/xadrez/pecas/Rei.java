package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.XadrezPeca;

public class Rei extends XadrezPeca {

	public Rei(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Posicao posicao) {
		XadrezPeca p = (XadrezPeca) getTabuleiro().peca(posicao);
		return p == null || p.getColor() != getColor();
	}

	@Override
	public boolean[][] movimentosPossivel() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// above
		p.setValor(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().verificaPosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// below
		p.setValor(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().verificaPosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// left
		p.setValor(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().verificaPosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// right
		p.setValor(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().verificaPosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// nw
		p.setValor(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().verificaPosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// ne
		p.setValor(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().verificaPosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sw
		p.setValor(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().verificaPosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// se
		p.setValor(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().verificaPosicao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}
}
