package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.XadrezPeca;

public class Queen extends XadrezPeca {

	public Queen(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
	}

	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public boolean[][] movimentosPossivel() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// above
		p.setValor(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiro().verificaPosicao(p) && !getTabuleiro().verificaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTabuleiro().verificaPosicao(p) && verificaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// left
		p.setValor(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().verificaPosicao(p) && !getTabuleiro().verificaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().verificaPosicao(p) && verificaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// right
		p.setValor(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiro().verificaPosicao(p) && !getTabuleiro().verificaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().verificaPosicao(p) && verificaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// below
		p.setValor(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiro().verificaPosicao(p) && !getTabuleiro().verificaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiro().verificaPosicao(p) && verificaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

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
