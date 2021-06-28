package xadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

		private Tabuleiro tabuleiro;
		
		public PartidaXadrez() {
			tabuleiro = new Tabuleiro(8, 8);
			iniciaPartida();
		}
		
		public XadrezPeca[][] recebePecas() {
			XadrezPeca[][] mat = new XadrezPeca[tabuleiro.getLinhas()][tabuleiro.getColunas()];
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j<tabuleiro.getColunas(); j++) {
					mat[i][j] = (XadrezPeca) tabuleiro.peca(i, j);
				}
			}
			return mat;
		}
		
		private void iniciaPartida() {
			tabuleiro.lugarPeca(new Torre(tabuleiro, Color.WHITE), new Posicao(2,1));
			tabuleiro.lugarPeca(new Rei(tabuleiro, Color.BLACK), new Posicao(0,4));
			tabuleiro.lugarPeca(new Rei(tabuleiro, Color.BLACK), new Posicao(7,4));
		}
}
