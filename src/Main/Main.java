package Main;
import java.util.*;

public class Main{
public static void main(String[]args){
	
	Carro c = new Carro();
	Emplacamento e = new Emplacamento(c);
	Transferencia transf = new Transferencia();
	Scanner sc = new Scanner (System.in);

	boolean running = true;
	
	
	
	

	
	while(running) {
	
		System.out.println("1.Cadastro de veículos");
		System.out.println("2. Transferência de veículo");
		System.out.println("3. Exibir Dados");
		
		int query = sc.nextInt();
		
		if (query == 1) {
			System.out.println("Cadastro de Veículos. O seu carro é novo(true) ou antigo(false)?");
			boolean isNovo = sc.nextBoolean();
			if (isNovo ) {
				c.Nome();
				c.CPF();
				c.Ano();
				c.Cor();
				c.Marca();
				c.Modelo();
			
				e.PadraoMercosul(); 
			}
		

			else if (isNovo == false) {
				c.Nome();
				c.CPF();
				c.Ano();
				c.Cor();
				c.Marca();
				c.Modelo();
			
				e.PadraoAntigo();
				
			}
			
		}	
			if (query == 2) {
				System.out.println("Deseja transferir a placa para um novo proprietário?");
	    			
				if(transf.returnTransferencia()) {
	    				
	    				System.out.println("O seu veículo é antigo?");
	    				
	    				boolean isAntigo = sc.nextBoolean();
						if (isAntigo) {
	    					System.out.println("Veículo Antigo");
	    					e.PadraoMercosul(); 
	    							
	    				}
	    	
	    								
	    				else {
	    				System.out.println("Veículo Novo");
	    				}
	    				
	    				c.Nome();
	    				c.CPF();
	    				transf.transferenciaReturnCPF();	
	    				
	    			}
	 
			}
		
			if (query == 3) {
				c.exibirDados();
			}
		
	}
	

			
}
}

			
			
	
	
	



