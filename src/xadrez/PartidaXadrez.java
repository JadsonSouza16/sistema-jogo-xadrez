package xadrez;

import tabuleiro.Peca;
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
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (XadrezPeca) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}

	public boolean[][] movimentosPossivel(XadrezPosicao posicaoOrigem){
		Posicao posicao = posicaoOrigem.toPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossivel();
	}
	
	public XadrezPeca movimentoXadrez(XadrezPosicao posicaoOrigem, XadrezPosicao posicaoFinal) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao finall = posicaoFinal.toPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoFinal(origem, finall);
		Peca capturarPeca = fazerMovimento(origem, finall);
		return (XadrezPeca)capturarPeca;
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao finall) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca capturarPeca = tabuleiro.removerPeca(finall);
		tabuleiro.lugarPeca(p, finall);
		return capturarPeca;
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.verificaPeca(posicao)) {
			throw new XadrezExcecao("Não existe peça na posição de origem");
		}
		if (!tabuleiro.peca(posicao).verificaMovimento()) {
			throw new XadrezExcecao("Não existe movimentos possiveis para a peça escolhida");
		}
	}
	
	private void validarPosicaoFinal(Posicao origem, Posicao finall) {
		if (!tabuleiro.peca(origem).movimentoPossivel(finall)) {
			throw new XadrezExcecao("A peca escolhida nao pode se mover para a posicao de destino");
		}
	}
	
	private void novoLugarPeca(char coluna, int linha, XadrezPeca peca) {
		tabuleiro.lugarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
	}

	private void iniciaPartida() {
		novoLugarPeca('c', 1, new Torre(tabuleiro, Color.WHITE));
		novoLugarPeca('c', 2, new Torre(tabuleiro, Color.WHITE));
		novoLugarPeca('d', 2, new Torre(tabuleiro, Color.WHITE));
		novoLugarPeca('e', 2, new Torre(tabuleiro, Color.WHITE));
		novoLugarPeca('e', 1, new Torre(tabuleiro, Color.WHITE));
		novoLugarPeca('d', 1, new Rei(tabuleiro, Color.WHITE));

		novoLugarPeca('c', 7, new Torre(tabuleiro, Color.BLACK));
		novoLugarPeca('c', 8, new Torre(tabuleiro, Color.BLACK));
		novoLugarPeca('d', 7, new Torre(tabuleiro, Color.BLACK));
		novoLugarPeca('e', 7, new Torre(tabuleiro, Color.BLACK));
		novoLugarPeca('e', 8, new Torre(tabuleiro, Color.BLACK));
		novoLugarPeca('d', 8, new Rei(tabuleiro, Color.BLACK));
	}
}
