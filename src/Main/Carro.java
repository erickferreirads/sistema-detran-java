package Main;
import java.util.*;

public class Carro {
	
	public int ano;
	public String marca, modelo, cor;
	int cpf;
	String nome;
	boolean cpfValido = true;
	public String placa = "";
	public String placaB = "";
	
	Scanner sc = new Scanner (System.in);
		public Carro() {
			
			
		}
		public int Ano(){
			System.out.println("Digite o ano em que o carro foi comprado: ");
			int n = sc.nextInt();
			sc.nextLine();
			ano = n;
			return n;
		}
		
		public String Nome() {
			System.out.println("Digite o proprietario do carro: ");
			String m = sc.nextLine();
			nome = m;
			return m;
		}
		public String Cor() {
			System.out.println("Digite a cor do carro: ");
			String o = sc.nextLine();
			cor = o;
			
			return o;
		}
		public String Marca() {
			System.out.println("Digite a marca do carro: ");
			String p = sc.nextLine();
			marca = p;
			
			return p;
		}
		
		public String Modelo() {
			System.out.println("Digite o modelo do carro: ");
			String q = sc.nextLine();
			modelo = q;
			
			return q;	
		
		}
		public int CPF() {
			System.out.println("Digite o CPF do proprietario");
			int r = sc.nextInt();
			cpf = r;
			
			return r;
		}
	
		public void exibirDados() {
			System.out.println("Ano: " + ano);
			System.out.println("Proprietário: " + nome);
			System.out.println("Cor: " + cor);
			System.out.println("Marca: " + marca);
			System.out.println("Modelo: " + modelo);
			System.out.println("CPF: " + cpf);
			System.out.println("Placa: " + placa);
			System.out.println("Carácter alfabético Mercosul: " + placaB);
		}
}
