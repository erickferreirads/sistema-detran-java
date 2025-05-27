package Main;
import java.util.*;

public class Emplacamento {
	
	
	
	static Scanner sc = new Scanner(System.in);
	Carro c;
	
	
	public Emplacamento(Carro c) {
		this.c = c;
		System.out.println("Bem vindo ao Sistema de Emplacamento");
	}
	
	public void PadraoAntigo() {//placa antiga
		
			sc.nextLine();//break
			
			char [] placa = new char [3];//letras, somente char
			int [] placaInt = new int [4];//numeros
			
			for (int i = 0; i < 3; i++) {//loop de letras
				
				System.out.print("Digite os caracteres alfabéticos da placa: (3 caracteres) ");
				char q2 = sc.nextLine().charAt(0);
				placa[i] = q2;
				System.out.println(Arrays.toString(placa).toUpperCase());
			
				}
			
			for (int k = 0; k < 4; k++) {//loop de numeros
			
				System.out.println("Digite of caracteres númericos da placa: (4 caracteres): ");
				int q = sc.nextInt();
				placaInt[k] = q;
				System.out.println(Arrays.toString(placaInt));
		
			}
					
			
			//bloco de print
			String placaFinal = "";
			for (char letra : placa) placaFinal += Character.toUpperCase(letra);
			for (int num : placaInt) placaFinal += num;

			c.placa = placaFinal;
			System.out.println("PLACA: " + c.placa);
			
		}	
		public void PadraoMercosul() {//placa nova
				
					//sc.nextLine();//break
			
					char [] placa = new char[3];//letras, somente char
					int [] placaInt = new int[4];//numeros
					char[] placaCharInt = new char[4];//array para transformação em letras
					char [] placaChar = {'A', 'B', 'C', 'D' , 'E', 'F', 'G', 'H', 'I', 'J'};//tabela de letras correspondentes
			
				
					for (int i = 0; i < 3; i++) {//loop de letras
						
						System.out.print("Digite os caracteres alfabéticos da placa: (3 caracteres) ");
						char q2 = sc.nextLine().charAt(0);
						placa[i] = q2;
						System.out.println(Arrays.toString(placa).toUpperCase());
					
					
						}
					for (int k = 0; k < 4; k++) {//loop de numeros
					
						System.out.println("Digite of caracteres númericos da placa: (4 caracteres): ");
						int q = sc.nextInt();
						placaInt[k] = (char) q;//scanner de placaInt
						placaCharInt[k] = (char) placaInt[k];//converte placaCharInt para placaInt
						System.out.println(Arrays.toString(placaCharInt));
									
						//verifica se a posição 1 (2) de placaCharInt é igual a certo número, e converte ele para a posição na tabela de placaChar
							if (placaCharInt[1] == 0) {
								placaCharInt[1] = placaChar[0];
							}
							else if(placaCharInt[1] == 1) {
								placaCharInt[1] = placaChar[1];
							}
							else if(placaCharInt[1] == 2) {
								placaCharInt[1] = placaChar[2];
							}
							else if(placaCharInt[1] == 3) {
								placaCharInt[1] = placaChar[3];
							}
							else if(placaCharInt[1] == 4) {
								placaCharInt[1] = placaChar[4];
							}
							else if(placaCharInt[1] == 5) {
								placaCharInt[1] = placaChar[5];
							}
							else if(placaCharInt[1] == 6) {
								placaCharInt[1] = placaChar[6];
							}
							else if(placaCharInt[1] == 7) {
								placaCharInt[1] = placaChar[7];
							}
							else if(placaCharInt[1] == 8) {
								placaCharInt[1] = placaChar[8];
							}
							else if(placaCharInt[1] == 9) {
								placaCharInt[1] = placaChar[9];
							}
						
						
						
					}
						
						
				
					
					
					//bloco de print
					String placaFinalB = "";
					String placaFinal = "";
					for (char letra : placa) placaFinal += Character.toUpperCase(letra);
					for (int num : placaInt) placaFinal += num;
					for (char ch : placaCharInt) placaFinalB += ch;
					c.placaB = placaFinalB;
					c.placa = placaFinal;
					System.out.println("PLACA: " + c.placa + "" + c.placaB);
					
		}
						
					}
			
		
		

