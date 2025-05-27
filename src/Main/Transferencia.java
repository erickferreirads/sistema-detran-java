package Main;

import java.util.Arrays;
import java.util.Scanner;

public class Transferencia {

	Carro c = new Carro();
	static Scanner sc = new Scanner (System.in);
	int [] transfDia = new int [2];
	int [] transfMes = new int [2];
	int [] transfAno = new int [4];
	int transferenciaC = 0;
	
	
	
	public  boolean returnTransferencia() {
		boolean returnBoolTransferencia = sc.nextBoolean();
		return returnBoolTransferencia;
	}
	
	public Transferencia () {
		
		
	}
	
			
	public void transferenciaReturnCPF(){
		//inserir verificação de CPF para sincronizar com o Main
	    if (c.cpfValido) {
	    	int [] transfDia = new int [2];
	    	int [] transfMes = new int [2];
	    	int [] transfAno = new int [4];
	        for (int i = 0; i < 2; i++) {
	            System.out.print("Digite o dia da transferência: ");
	            transfDia[i] = sc.nextInt();
	            System.out.println(Arrays.toString(transfDia));
	        }

	    
	        for (int k = 0; k < 2; k++) {
	            System.out.print("Digite o mês da transferência: ");
	            transfMes[k] = sc.nextInt();
	            System.out.println(Arrays.toString(transfMes));
	        }

	       
	        for (int j = 0; j < 4; j++) {
	            System.out.print("Digite o ano da transferência: ");
	            transfAno[j] = sc.nextInt();
	            System.out.println(Arrays.toString(transfAno));
	        }

	      
	        System.out.println("Datas de transferência registradas:");
	        System.out.println(Arrays.toString(transfDia) + "/" +
	        		(Arrays.toString(transfMes) + "/" +
	        				(Arrays.toString(transfAno))));
	            
	   

	        transferenciaC++;
	        System.out.println("Transferencia Registrada: " + transferenciaC);
	    	} 
	    	
	    	else {
	    	
	        System.out.println("CPF inválido. Transferência não permitida.");
	   
	    	}
	}
		
}		
		
	



