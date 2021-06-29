package xadrez;

import tabuleiro.TabuleiroException;

public class XadrezExcecao extends TabuleiroException {
	private static final long serialVersionUID = 1L;
	
	public XadrezExcecao(String msg) {
		super(msg);
	}
}

