package curso.angular.listener;

import java.io.Serializable;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *  Acessa todo os objetos criados pelo contexto do spring
 * @author alex
 */
//ContextLoaderListenerCaixakiUtils Acessar objetos  static do 
//spring(não da pra fazer injeção de  dependencia) traz  objeto da memoria
//cria-se  uma  clase e  extende de  org.springframework.web.context.ContextLoaderListener
public class ContextLoaderListenerCaixakiUtils extends
		org.springframework.web.context.ContextLoaderListener implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static WebApplicationContext getWac() {
		return WebApplicationContextUtils
				//getServletContext acesso  todo  contexto do spring  dados de memoria
				.getWebApplicationContext(getCurrentWebApplicationContext().getServletContext());
	}
	
	/**
	 * Retorna um obejeto do contexto Spring de acordo com seu nome
	 * @param idNomeBean
	 * @return Object
	 */
	public static Object getBean(String idNomeBean) {
		return getWac().getBean(idNomeBean);
	}
	
	/**
	 * Retorna um obejeto do contexto Spring de acordo com sua Class
	 * @param idNomeBean
	 * @return Object
	 */
	public static Object getBean(Class<?> classe) {
		return getWac().getBean(classe);
	}

}
