package curso.angular.pegarresposta;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class PegarRespostaJson
 */
@WebServlet("/pegarRespostaJson")
public class PegarRespostaJson extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PegarRespostaJson() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/jason");
		response.setHeader("Cache-Control","nocache");
		response.setCharacterEncoding("utf-8");
		
		JSONArray jsonArray = new JSONArray();
		
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("nome","Noemi");
		jsonObject.put("cidade","São José");
		jsonArray.put(jsonObject);
		
		jsonObject = new JSONObject();
		jsonObject.put("nome","Júlia");
		jsonObject.put("cidade","Curitiba");
		jsonArray.put(jsonObject);
		
		jsonObject =  new JSONObject();
		jsonObject.put("nome", "Nely");
		jsonObject.put("cidade","Curitiba");
		jsonArray.put(jsonObject);
		
		jsonObject =  new JSONObject();
		jsonObject.put("nome", "Giovana");
		jsonObject.put("cidade","São Paulo");
		jsonArray.put(jsonObject);
		response.getWriter().write(jsonArray.toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
