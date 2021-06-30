package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.XadrezExcecao;
import xadrez.XadrezPeca;
import xadrez.XadrezPosicao;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		
		while (true) {
			try {
				UI.clearScreen();
				UI.printTabuleiro(partidaXadrez.recebePecas());
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

	}

}
