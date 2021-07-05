package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Queen;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private int turno;
	private Color jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean xeque;
	private boolean xequeMate;
	private XadrezPeca enPassantVulneravel;
	private XadrezPeca promovida;

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

	public boolean getXequeMate() {
		return xequeMate;
	}

	public XadrezPeca getEnPassantVulneravel() {
		return enPassantVulneravel;
	}

	public XadrezPeca getPromocao() {
		return promovida;
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

	public boolean[][] movimentosPossivel(XadrezPosicao posicaoOrigem) {
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

		XadrezPeca moverPeca = (XadrezPeca) tabuleiro.peca(finall);

		// #Movimento especial promocao
		promovida = null;
		if (moverPeca instanceof Peao) {
			if (moverPeca.getColor() == Color.WHITE && finall.getLinha() == 0 || moverPeca.getColor() == Color.BLACK && finall.getLinha() == 7) {
				promovida = (XadrezPeca)tabuleiro.peca(finall);
				promovida = trocarPecaPromovida("Q");
			}
		}
		
		xeque = (testeXeque(oponente(jogadorAtual))) ? true : false;

		if (testeXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		} else {
			proximoTurno();
		}

		// #Movimento especial en Passant
		if (moverPeca instanceof Peao
				&& (finall.getLinha() == origem.getLinha() - 2 || finall.getLinha() == origem.getLinha() + 2)) {
			enPassantVulneravel = moverPeca;
		} else {
			enPassantVulneravel = null;
		}

		return (XadrezPeca) capturarPeca;
	}
	
	public XadrezPeca trocarPecaPromovida(String tipo) {
		if (promovida == null) {
			throw new IllegalStateException("Nao ha peca para ser promovida");
		}
		if (!tipo.endsWith("B") && !tipo.endsWith("C") && !tipo.endsWith("T") && !tipo.endsWith("Q")) {
			throw new InvalidParameterException("Tipo invalido para promocao");
		}
		
		Posicao pos = promovida.getXadrezPosicao().toPosicao();
		Peca p = tabuleiro.removerPeca(pos);
		pecasNoTabuleiro.remove(p);
		
		XadrezPeca novaPeca = novaPeca(tipo, promovida.getColor());
		tabuleiro.lugarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}
	
	private XadrezPeca novaPeca(String tipo, Color color) {
		if (tipo.equals("B")) return new Bispo(tabuleiro, color);
		if (tipo.equals("C")) return new Cavalo(tabuleiro, color);
		if (tipo.equals("Q")) return new Queen(tabuleiro, color);
		return new Torre(tabuleiro, color);	
	}

	private Peca fazerMovimento(Posicao origem, Posicao finall) {
		XadrezPeca p = (XadrezPeca) tabuleiro.removerPeca(origem);
		p.incrementaContaMovimento();
		Peca capturarPeca = tabuleiro.removerPeca(finall);
		tabuleiro.lugarPeca(p, finall);

		if (capturarPeca != null) {
			pecasNoTabuleiro.remove(capturarPeca);
			pecasCapturadas.add(capturarPeca);
		}

		// #Movimento especial roque pequeno
		if (p instanceof Rei && finall.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao finallT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			XadrezPeca Torre = (XadrezPeca) tabuleiro.removerPeca(origemT);
			tabuleiro.lugarPeca(Torre, finallT);
			Torre.incrementaContaMovimento();
		}

		// #Movimento especial roque grande
		if (p instanceof Rei && finall.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao finallT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			XadrezPeca Torre = (XadrezPeca) tabuleiro.removerPeca(origemT);
			tabuleiro.lugarPeca(Torre, finallT);
			Torre.incrementaContaMovimento();
		}

		// #Movimento Especial en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != finall.getColuna() && capturarPeca == null) {
				Posicao peaoPosicao;
				if (p.getColor() == Color.WHITE) {
					peaoPosicao = new Posicao(finall.getLinha() + 1, finall.getColuna());
				} else {
					peaoPosicao = new Posicao(finall.getLinha() - 1, finall.getColuna());
				}
				capturarPeca = tabuleiro.removerPeca(peaoPosicao);
				pecasCapturadas.add(capturarPeca);
				pecasNoTabuleiro.remove(capturarPeca);
			}
		}

		return capturarPeca;
	}

	private void desfazerMovimento(Posicao origem, Posicao finall, Peca capturarPeca) {
		XadrezPeca p = (XadrezPeca) tabuleiro.removerPeca(finall);
		p.decrementaContaMovimento();
		tabuleiro.lugarPeca(p, origem);

		if (capturarPeca != null) {
			tabuleiro.lugarPeca(capturarPeca, finall);
			pecasCapturadas.remove(capturarPeca);
			pecasNoTabuleiro.add(capturarPeca);
		}

		// #Movimento especial roque pequeno
		if (p instanceof Rei && finall.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao finallT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			XadrezPeca Torre = (XadrezPeca) tabuleiro.removerPeca(finallT);
			tabuleiro.lugarPeca(Torre, origemT);
			Torre.decrementaContaMovimento();
		}

		// #Movimento especial roque grande
		if (p instanceof Rei && finall.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao finallT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			XadrezPeca Torre = (XadrezPeca) tabuleiro.removerPeca(finallT);
			tabuleiro.lugarPeca(Torre, origemT);
			Torre.decrementaContaMovimento();
		}

		// #Movimento Especial en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != finall.getColuna() && capturarPeca == enPassantVulneravel) {
				XadrezPeca peao = (XadrezPeca)tabuleiro.removerPeca(finall);
				Posicao peaoPosicao;
				if (p.getColor() == Color.WHITE) {
					peaoPosicao = new Posicao(3, finall.getColuna());
				} else {
					peaoPosicao = new Posicao(4, finall.getColuna());
				}
				tabuleiro.lugarPeca(peao, peaoPosicao);
				
			}
		}
	}

	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.verificaPeca(posicao)) {
			throw new XadrezExcecao("Não existe peça na posição de origem");
		}
		if (jogadorAtual != ((XadrezPeca) tabuleiro.peca(posicao)).getColor()) {
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
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getColor() == color)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (XadrezPeca) p;
			}
		}
		throw new IllegalStateException("Nao existe o rei " + color + " no tabuleiro");
	}

	private boolean testeXeque(Color color) {
		Posicao reiPosicao = rei(color).getXadrezPosicao().toPosicao();
		List<Peca> oponentePecas = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getColor() == oponente(color))
				.collect(Collectors.toList());
		for (Peca p : oponentePecas) {
			boolean[][] mat = p.movimentosPossivel();
			if (mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testeXequeMate(Color color) {
		if (!testeXeque(color)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getColor() == color)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.movimentosPossivel();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((XadrezPeca) p).getXadrezPosicao().toPosicao();
						Posicao finall = new Posicao(i, j);
						Peca capturarPeca = fazerMovimento(origem, finall);
						boolean testeXeque = testeXeque(color);
						desfazerMovimento(origem, finall, capturarPeca);
						if (!testeXeque) {
							return false;
						}
					}
				}
			}
		}
		return true;

	}

	private void novoLugarPeca(char coluna, int linha, XadrezPeca peca) {
		tabuleiro.lugarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}

	private void iniciaPartida() {
		novoLugarPeca('a', 1, new Torre(tabuleiro, Color.WHITE));
		novoLugarPeca('b', 1, new Cavalo(tabuleiro, Color.WHITE));
		novoLugarPeca('c', 1, new Bispo(tabuleiro, Color.WHITE));
		novoLugarPeca('d', 1, new Queen(tabuleiro, Color.WHITE));
		novoLugarPeca('e', 1, new Rei(tabuleiro, Color.WHITE, this));
		novoLugarPeca('f', 1, new Bispo(tabuleiro, Color.WHITE));
		novoLugarPeca('g', 1, new Cavalo(tabuleiro, Color.WHITE));
		novoLugarPeca('h', 1, new Torre(tabuleiro, Color.WHITE));
		novoLugarPeca('a', 2, new Peao(tabuleiro, Color.WHITE, this));
		novoLugarPeca('b', 2, new Peao(tabuleiro, Color.WHITE, this));
		novoLugarPeca('c', 2, new Peao(tabuleiro, Color.WHITE, this));
		novoLugarPeca('d', 2, new Peao(tabuleiro, Color.WHITE, this));
		novoLugarPeca('e', 2, new Peao(tabuleiro, Color.WHITE, this));
		novoLugarPeca('f', 2, new Peao(tabuleiro, Color.WHITE, this));
		novoLugarPeca('g', 2, new Peao(tabuleiro, Color.WHITE, this));
		novoLugarPeca('h', 2, new Peao(tabuleiro, Color.WHITE, this));

		novoLugarPeca('a', 8, new Torre(tabuleiro, Color.BLACK));
		novoLugarPeca('b', 8, new Cavalo(tabuleiro, Color.BLACK));
		novoLugarPeca('c', 8, new Bispo(tabuleiro, Color.BLACK));
		novoLugarPeca('d', 8, new Queen(tabuleiro, Color.BLACK));
		novoLugarPeca('e', 8, new Rei(tabuleiro, Color.BLACK, this));
		novoLugarPeca('f', 8, new Bispo(tabuleiro, Color.BLACK));
		novoLugarPeca('g', 8, new Cavalo(tabuleiro, Color.BLACK));
		novoLugarPeca('h', 8, new Torre(tabuleiro, Color.BLACK));
		novoLugarPeca('a', 7, new Peao(tabuleiro, Color.BLACK, this));
		novoLugarPeca('b', 7, new Peao(tabuleiro, Color.BLACK, this));
		novoLugarPeca('c', 7, new Peao(tabuleiro, Color.BLACK, this));
		novoLugarPeca('d', 7, new Peao(tabuleiro, Color.BLACK, this));
		novoLugarPeca('e', 7, new Peao(tabuleiro, Color.BLACK, this));
		novoLugarPeca('f', 7, new Peao(tabuleiro, Color.BLACK, this));
		novoLugarPeca('g', 7, new Peao(tabuleiro, Color.BLACK, this));
		novoLugarPeca('h', 7, new Peao(tabuleiro, Color.BLACK, this));
	}
}
