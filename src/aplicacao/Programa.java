package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.XadrezExcecao;
import xadrez.XadrezPeca;
import xadrez.XadrezPosicao;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<XadrezPeca> capturada = new ArrayList<>();
		
		
		while (!partidaXadrez.getXequeMate()) {
			try {
				UI.clearScreen();
				UI.printPartida(partidaXadrez, capturada);
				System.out.println();
				System.out.print("Origem: ");
				XadrezPosicao origem = UI.encontreXadrezPosicao(sc);
				
				boolean[][] movimentosPossivel = partidaXadrez.movimentosPossivel(origem);
				UI.clearScreen();
				UI.printTabuleiro(partidaXadrez.recebePecas(), movimentosPossivel);;
				
				System.out.println();
				System.out.print("Final: ");
				XadrezPosicao finall = UI.encontreXadrezPosicao(sc);
				
				XadrezPeca capturarPeca = partidaXadrez.movimentoXadrez(origem, finall);
			
				if (capturarPeca != null) {
					capturada.add(capturarPeca);
				}
				
				if (partidaXadrez.getPromocao() != null) {
					System.out.print("Escolha a peca a ser promovida (B/C/T/Q): ");
					String tipo = sc.nextLine().toUpperCase();
					while (!tipo.endsWith("B") && !tipo.endsWith("C") && !tipo.endsWith("T") && !tipo.endsWith("Q")) {
						System.out.print("Tipo invalido! Escolha a peca a ser promovida (B/C/T/Q): ");
						tipo = sc.nextLine().toUpperCase();
					}
					partidaXadrez.trocarPecaPromovida(tipo);
				}
			}
			catch(XadrezExcecao e) {
				System.out.println(e.getMessage());
				sc.nextLine();
				
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printPartida(partidaXadrez, capturada);

	}

}
