package br.com.fiap.aplicacao;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.fiap.entity.Pedidos;
import br.com.fiap.entity.Produtos;
import br.com.fiap.helper.PedidosHelper;
import br.com.fiap.helper.ProdutosHelper;

public class MenuDeComandos {
	
	static int idProduto, idPedido, vendas;
	static String nome, descricao;
	static double preco;
	static Date data;
	static Produtos produto;
	static Pedidos pedido;
	
	public static void menu() {
		
		boolean sair = true;
		
		do {
			
			String[] escolhas = {"Incluir novo produto", "Buscar produto", "Lista de produtos", "Incluir novo pedido",
					"Buscar pedidos pelo ID do pedido", "Buscar pedidos pelo ID do produto", "Lista de pedidos",
					"Finalizar"};
		    String selecionado = (String) JOptionPane.showInputDialog(null, "Por favor selecione um item da lista.",
		        "Menu principal", JOptionPane.QUESTION_MESSAGE, null, escolhas, escolhas[0]);
		    
		    switch (selecionado) {
			case "Incluir novo produto":
				
				nome = JOptionPane.showInputDialog("Digite o nome do produto.");
				preco = Double.parseDouble(JOptionPane.showInputDialog("Digite o preco do produto."));
				
				produto = new Produtos();
				produto.setNome(nome);
				produto.setPreco(preco);
				
				try {
					
					ProdutosHelper proHelp = new ProdutosHelper();
					proHelp.incluirProduto(produto);
					JOptionPane.showMessageDialog(null, produto.getNome() + " incluido.");
					
				} catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, e.getMessage());
					
				}
				
				break;
			
			case "Buscar produto":
				
				idProduto = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do produto."));
				try {
					
					ProdutosHelper proHelp = new ProdutosHelper();
					Produtos p = proHelp.buscarProduto(idProduto);
					if (p ==  null){
						JOptionPane.showMessageDialog(null, "Produto nao encontrado.");
					}
					else {
						JOptionPane.showMessageDialog(null, "ID: " + p.getId() + "\nNome: " + p.getNome() + "\nVendas: "
								+ p.getVendas() + "\nPreco: " + p.getPreco());
					}
					
				} catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, e.getMessage());
					
				}		
				
				break;
				
			case "Lista de produtos":
					
				try {
					
					ProdutosHelper proHelp = new ProdutosHelper();
					List<Produtos> p = proHelp.listarProdutos();
					
					Object[] coluna = {"ID", "Nome", "Vendas", "Preco"};
					
					DefaultTableModel tm = new DefaultTableModel(coluna, 0);
					
					for (int i = 0; i < p.size(); i++) {
						
						idProduto = p.get(i).getId();
						nome = p.get(i).getNome();
						vendas = p.get(i).getVendas();
						preco = p.get(i).getPreco();
						
						Object[] dados = {idProduto, nome, vendas, preco};
						
						tm.addRow(dados);
					}
					
					JTable table = new JTable(tm);
					JOptionPane.showMessageDialog(null, new JScrollPane(table));
					
				} catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, e.getMessage());
					
				}
				
				break;
			
			case "Incluir novo pedido":
				
				try {
					idProduto = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do produto."));
					String dataString = (JOptionPane.showInputDialog("Digite a data do pedido. (dd/mm/aaaa)"));
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					descricao = JOptionPane.showInputDialog("Digite a descricao do pedido.");
					
					pedido = new Pedidos();
					pedido.setIdProduto(idProduto);
					pedido.setData(new Date(df.parse(dataString).getTime()));
					pedido.setDescricao(descricao);
						
					PedidosHelper pedHelp = new PedidosHelper();
					pedHelp.incluirPedido(pedido);
					JOptionPane.showMessageDialog(null, "Pedido incluido.");
				} catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, e.getMessage());
					
				}
					
				break;
			
			case "Buscar pedidos pelo ID do pedido":
				
				idPedido = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do pedido."));
				try {
					
					PedidosHelper pedHelp = new PedidosHelper();
					Pedidos p = pedHelp.buscarPedidosPorIdPed(idPedido);
					
					if (p == null) {
						JOptionPane.showMessageDialog(null, "Pedido nao encontrado.");
					}
					else {
						JOptionPane.showMessageDialog(null, "ID do pedido: " + p.getId() + "\nID do produto: "
								+ p.getIdProduto() + "\nData: " + p.getData() + "\nDescricao: " + p.getDescricao());
					}
										
				} catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, e.getMessage());
					
				}
				
				break;
				
			case "Buscar pedidos pelo ID do produto":
				
				idProduto = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do produto."));
				try {
					
					PedidosHelper pedHelp = new PedidosHelper();
					List<Pedidos> p = pedHelp.buscarPedidosPorIdProd(idProduto);
					
					Object[] coluna = {"ID do pedido", "ID do produto", "Data", "Descricao"};
					
					DefaultTableModel tm = new DefaultTableModel(coluna, 0);
					
					for (int i = 0; i < p.size(); i++) {
						
						idPedido = p.get(i).getId();
						idProduto = p.get(i).getIdProduto();
						data = p.get(i).getData();
						descricao = p.get(i).getDescricao();
						
						Object[] dados = {idPedido, idProduto, data, descricao};
						
						tm.addRow(dados);
					}
					
					JTable table = new JTable(tm);
					JOptionPane.showMessageDialog(null, new JScrollPane(table));
					
				} catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, e.getMessage());
					
				}
				
				break;
				
			case "Lista de pedidos":

				try {
					
					PedidosHelper pedHelp = new PedidosHelper();
					List<Pedidos> p = pedHelp.listarPedidos();
					
					Object[] coluna = {"ID do pedido", "ID do produto", "Data", "Descricao"};
					
					DefaultTableModel tm = new DefaultTableModel(coluna, 0);
					
					for (int i = 0; i < p.size(); i++) {
						
						idPedido = p.get(i).getId();
						idProduto = p.get(i).getIdProduto();
						data = p.get(i).getData();
						descricao = p.get(i).getDescricao();
						
						Object[] dados = {idPedido, idProduto, data, descricao};
						
						tm.addRow(dados);
					}
					
					JTable table = new JTable(tm);
					JOptionPane.showMessageDialog(null, new JScrollPane(table));
					
				} catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, e.getMessage());
					
				}
				
				break;
				
				
			case "Finalizar":
				
				sair = false;
				break;
		    }
			
		} while (sair);
		
	}

}
