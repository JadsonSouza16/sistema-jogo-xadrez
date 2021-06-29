package aplicacao;

import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.XadrezPeca;
import xadrez.XadrezPosicao;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		
		while (true) {
			UI.printTabuleiro(partidaXadrez.recebePecas());
			System.out.println();
			System.out.print("Origem: ");
			XadrezPosicao origem = UI.encontreXadrezPosicao(sc);
			
			System.out.println();
			System.out.print("Final: ");
			XadrezPosicao finall = UI.encontreXadrezPosicao(sc);
			
			XadrezPeca capturarPeca = partidaXadrez.movimentoXadrez(origem, finall);
		
		}

	}

}
