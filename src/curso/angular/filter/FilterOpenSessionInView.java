package curso.angular.filter;
import java.io.IOException;
import java.io.Serializable;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.filter.DelegatingFilterProxy;

import curso.angular.hibernate.HibernateUtil;
import curso.angular.listener.ContextLoaderListenerCaixakiUtils;

/**
 * Intercepta todas as requisi��es, faz commit e rollback 
 * @author Noemi
 */
//O commit e rollback  devem  estar em  um unico lugar /para mante  consistencia integridade  previnir erros
// DelegatingFilterProxy  delagado  Intercepta (spring
@WebFilter(filterName = "conexaoFilter")
public class FilterOpenSessionInView extends DelegatingFilterProxy  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static SessionFactory sf;
	
	//chamado  quando inicia app
	//Sobe a aplica��o
	@Override
	public void  initFilterBean()throws ServletException{
		//estacia cria  a aplica��o
		//Inicia o SessionFactory(sf)
	    sf	 =  HibernateUtil.getSessionFactory();
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
        // BasicDataSource para  trabalhar  com  sql puro(sql transfomado  em objeto  insert select facilitados)
		//ContextLoaderListenerCaixakiUtils Acessar objetos  static do  spring(n�o da pra fazer inje��o de  dependencia) traz  objeto da memoria  
		BasicDataSource springDataSource = (BasicDataSource) ContextLoaderListenerCaixakiUtils
				.getBean("springDataSource");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(
				springDataSource);
		TransactionStatus status = transactionManager.getTransaction(def);

		try {

			servletRequest.setCharacterEncoding("UTF-8");
            
			//para cada Requisi��o chama  sess�o e  inicia uma  transa��o
			sf.getCurrentSession().beginTransaction();
			chain.doFilter(servletRequest, servletResponse);

			transactionManager.commit(status);

			// se  a trasa��o  for  ativa
			if (sf.getCurrentSession().getTransaction().isActive()) {
				//executa tudo que  esta em banco(insert delete update etc)mem�ria  mandado  pelo hibernate
				//depois comita tudo
				sf.getCurrentSession().flush();
				sf.getCurrentSession().getTransaction().commit();
			}

			if (sf.getCurrentSession().isOpen()) {
				sf.getCurrentSession().close();
			}

			servletResponse.setCharacterEncoding("UTF-8");
			servletResponse.setContentType("text/html; charset=UTF-8");

		} catch (Exception e) {

			transactionManager.rollback(status);

			e.printStackTrace();

			if (sf.getCurrentSession().getTransaction().isActive()) {
				sf.getCurrentSession().getTransaction().rollback();
			}
			if (sf.getCurrentSession().isOpen()) {
				sf.getCurrentSession().close();
			}
		} finally {
			if (sf.getCurrentSession().isOpen()) {
				if (sf.getCurrentSession().beginTransaction().isActive()) {
					sf.getCurrentSession().flush();
					sf.getCurrentSession().clear();
				}
				if (sf.getCurrentSession().isOpen()) {
					sf.getCurrentSession().close();
				}
			}

		}
	}

	
}
