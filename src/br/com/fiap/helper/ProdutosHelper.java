package br.com.fiap.helper;

import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.*;

import br.com.fiap.entity.Produtos;

public class ProdutosHelper {
	
	Session session = null;
	
	public ProdutosHelper() {
			
		session = HibernateUtil.getSessionFactory().openSession();
		
	}
	
	@SuppressWarnings("deprecation")
	public void incluirProduto(Produtos produto) {
		
		Transaction tx = null;
		
		try {
			
			tx = session.beginTransaction();
			session.save(produto);
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
	
	@SuppressWarnings({ "deprecation" })
	public Produtos buscarProduto(int id) {
		
		Produtos produto = new Produtos();
		
		try {
			
			produto = session.get(Produtos.class, id);
			session.lock(produto, LockMode.UPGRADE);
			
		} catch (HeadlessException e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
		
		return produto;
	}

	@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
	public List<Produtos> listarProdutos() {
		
		List<Produtos> produtos = new ArrayList<>();
		
		try {
			
			Query q = session.createQuery("FROM Produtos");
			q.setLockMode("FROM Pedidos", LockMode.UPGRADE);
			produtos = q.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return produtos;
		
	}

}
