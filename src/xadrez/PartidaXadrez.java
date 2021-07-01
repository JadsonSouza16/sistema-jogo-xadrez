package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int turno;
	private Color jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean xeque;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Color.WHITE;
		iniciaPartida();
	}

	public int getTurno() {
		return turno;
	}
	
	public Color getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getXeque() {
		return xeque;
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
		
		if (testeXeque(jogadorAtual)) {
			desfazerMovimento(origem, finall, capturarPeca);
			throw new XadrezExcecao("Você não pode se colocar em xeque");
		}
		
		xeque = (testeXeque(oponente(jogadorAtual))) ? true : false;
		
		proximoTurno();
		return (XadrezPeca)capturarPeca;
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao finall) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca capturarPeca = tabuleiro.removerPeca(finall);
		tabuleiro.lugarPeca(p, finall);
		
		if (capturarPeca != null) {
			pecasNoTabuleiro.remove(capturarPeca);
			pecasCapturadas.add(capturarPeca);
		}
		
		return capturarPeca;
	}
	
	private void desfazerMovimento(Posicao origem, Posicao finall, Peca capturarPeca) {
		Peca p = tabuleiro.removerPeca(finall);
		tabuleiro.lugarPeca(p, origem);
		
		if (capturarPeca != null) {
			tabuleiro.lugarPeca(capturarPeca, finall);
			pecasCapturadas.remove(capturarPeca);
			pecasNoTabuleiro.add(capturarPeca);
		}
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.verificaPeca(posicao)) {
			throw new XadrezExcecao("Não existe peça na posição de origem");
		}
		if (jogadorAtual != ((XadrezPeca)tabuleiro.peca(posicao)).getColor()) {
			throw new XadrezExcecao("A peca escolhida nao e sua");
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
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private Color oponente(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private XadrezPeca rei(Color color) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca)x).getColor() == color).collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (XadrezPeca)p;
			}
		}
		throw new IllegalStateException("Nao existe o rei " + color + " no tabuleiro");
	}
	
	private boolean testeXeque(Color color) {
		Posicao reiPosicao = rei(color).getXadrezPosicao().toPosicao();
		List<Peca> oponentePecas = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca)x).getColor() == oponente(color)).collect(Collectors.toList());
		for (Peca p : oponentePecas) {
			boolean[][] mat = p.movimentosPossivel();
			if (mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private void novoLugarPeca(char coluna, int linha, XadrezPeca peca) {
		tabuleiro.lugarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
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
