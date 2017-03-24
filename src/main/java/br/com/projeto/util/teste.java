package br.com.projeto.util;

public class teste {

	public static void main(String args[]) throws Exception {
		String cpf = "02572351323";

		cpf = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);

		System.out.println(cpf);
	}

}