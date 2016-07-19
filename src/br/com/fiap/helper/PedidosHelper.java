package br.com.fiap.helper;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.*;

import br.com.fiap.entity.Pedidos;
import br.com.fiap.entity.Produtos;

public class PedidosHelper {
	
Session session = null;
	
	public PedidosHelper() {
		
		session = HibernateUtil.getSessionFactory().openSession();
		
	}
	
	//Inclusão de Pedidos
	@SuppressWarnings("deprecation")
	public void incluirPedido(Pedidos pedido) {
		
		Transaction tx = null;
		
		try {
			
			tx = session.beginTransaction();
			session.save(pedido);
			session.lock(pedido, LockMode.UPGRADE);
			
			Produtos produto = (Produtos)session.get(Produtos.class, pedido.getIdProduto());
			produto.setVendas(produto.getVendas()+1);
			session.update(produto);
			session.lock(produto, LockMode.UPGRADE);
			
			tx.commit();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			try {
				JOptionPane.showMessageDialog(null, "Erro! Abortando transação.");
				tx.rollback();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Erro! Transação não pode ser abortada.");
				e1.printStackTrace();
			}
			
		} finally {
			session.close();
		}
	}
	
	//Busca de Pedidos pelo ID do Pedido
	@SuppressWarnings({ "deprecation" })
	public Pedidos buscarPedidosPorIdPed(int idPedido) {
		
		Pedidos pedido = new Pedidos();
		
		try {
			pedido = session.get(Pedidos.class, idPedido);
			session.lock(pedido, LockMode.UPGRADE);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return pedido;
		
	}
	
	//Busca de Pedidos pelo ID do Produto
	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	public List<Pedidos> buscarPedidosPorIdProd(int idProduto) {
		
		List<Pedidos> pedidos = new ArrayList<>();
		
		try {
			
			String sql = "FROM Pedidos WHERE idProduto = :idProd";
			Query q = session.createQuery(sql);
			q.setLockMode(sql, LockMode.UPGRADE);
			q.setParameter("idProd", idProduto);
			pedidos = q.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
		
		return pedidos;
		
	}
	
	//Lista de todos os pedidos
	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	public List<Pedidos> listarPedidos() {
		
		List<Pedidos> pedidos = new ArrayList<>();
		
		try {
			
			Query q = session.createQuery("FROM Pedidos");
			q.setLockMode("FROM Pedidos", LockMode.UPGRADE);
			pedidos = q.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return pedidos;
		
	}

}
