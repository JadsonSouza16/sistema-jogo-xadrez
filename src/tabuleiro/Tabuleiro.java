package tabuleiro;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new TabuleiroException("Erro ao criar tabuleiro: é necessário que haja pelo menos uma linha e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
		
	public Peca peca(int linha, int coluna) {
		if (!verificaPosicao(linha, coluna)) {
			throw new TabuleiroException("Essa posição não existe no tabuleiro");
		}
		return pecas[linha][coluna];
	}
	
	public Peca peca(Posicao posicao) {
		if (!verificaPosicao(posicao)) {
			throw new TabuleiroException("Essa posição não existe no tabuleiro");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void lugarPeca(Peca peca, Posicao posicao) {
		if (verificaPeca(posicao)) {
			throw new TabuleiroException("Já existe peça na posição " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	private boolean verificaPosicao(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	
	public boolean verificaPosicao(Posicao posicao) {
		return verificaPosicao(posicao.getLinha(), posicao.getColuna());
	}
	
	public boolean verificaPeca(Posicao posicao) {
		if (!verificaPosicao(posicao)) {
			throw new TabuleiroException("Essa posição não existe no tabuleiro");
		}
		return peca(posicao) != null;
	}
}
