package br.com.furb;

import java.util.regex.Matcher;

import br.com.furb.exception.AtributoInvalidoException;
import br.com.furb.exception.CombustivelInvalidoException;
import br.com.furb.exception.SimboloInvalidoException;
import br.com.furb.exception.ValorInvalidoException;

public class CarroParser {

	private static Carro c = new Carro();

	public static void main(String[] args) {
		
	}

	public static Carro processar(String entrada) {
		int i = 0;
		for (String line : entrada.split("\n|\r\n")) {
			++i;
			for (String word : line.split("\\s")) {
				if (word.trim().isEmpty()) {
					continue;
				}
				
				// VALIDA��ES
				Matcher matcherNumber = CompilerPatterns.VERIFY_NUMBER.getPattern().matcher(word);
				if (matcherNumber.find()) { // verifica se � um n�mero
					validateNumber(word, i);
					continue;
				} else {

					Matcher matcherSymbol = CompilerPatterns.VERIFY_VALID_SYMBOL.getPattern().matcher(word);
					if (matcherSymbol.find()) { // verifica se � um simbolo v�lido
						validateSymbol(word, i);
						continue;
					} else {
						
						// Se n�o entrar nem no Number, nem no Symbol, � um simbolo inv�lido
						throw new SimboloInvalidoException(i, word);
					}
				}
			}
		}
		return c;
	}

	private static void validateNumber(String word, int lineNumber) {
		// valida o Motor
		Matcher matcherNumber = CompilerPatterns.CHECK_ENGINE.getPattern().matcher(word);
		if (matcherNumber.matches()) {
			c.addQtdMotor();
			return;
		}

		// valida o Ano
		matcherNumber = CompilerPatterns.CHECK_YEAR.getPattern().matcher(word);
		if (matcherNumber.matches()) {
			c.addQtdAno();
			return;
		}

		// valida o KM
		matcherNumber = CompilerPatterns.CHECK_KM.getPattern().matcher(word);
		if (matcherNumber.matches()) {
			c.addQtdKm();
			return;
		}

		throw new AtributoInvalidoException(lineNumber, word);
	}

	private static void validateSymbol(String word, int lineNumber) {
		// Se ele entrou aqui, quer dizer que � um simbolo, logo, � um combustivel ou um valor.

		// Valida o combust�vel
		Matcher matcherNumber = CompilerPatterns.VERIFY_COMB.getPattern().matcher(word);
		if (matcherNumber.matches()) {
			c.addQtdCombustivel();
		} else {
			if (matcherNumber.find()) { //se ele n�o deu o match, por�m deu o find, � um combustivel invalido
				throw new CombustivelInvalidoException(lineNumber, word);
			}
		}

		// Valida o Valor
		matcherNumber = CompilerPatterns.CHECK_MONEY.getPattern().matcher(word);
		if (matcherNumber.matches()) {
			c.addQtdValor();
		} else {
			if (matcherNumber.find()) {
				throw new ValorInvalidoException(lineNumber, word);
			}
		}
	}
	
	public static Carro getCarro(){
		return c;
	}

}
