package xadrez;

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
		
		private void novoLugarPeca(char coluna, int linha, XadrezPeca peca) {
			tabuleiro.lugarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
		}
		
		private void iniciaPartida() {
			novoLugarPeca('b', 6, new Torre(tabuleiro, Color.WHITE));
			novoLugarPeca('e', 8, new Rei(tabuleiro, Color.BLACK));
			novoLugarPeca('e', 1, new Rei(tabuleiro, Color.WHITE));
		}
}
