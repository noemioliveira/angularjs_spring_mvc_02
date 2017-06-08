package curso.angular.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.stereotype.Component;
@Component
public class ReportUtil implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private static final String FOLDER_RELATORIOS = "/relatorios"; // Endere�o  de onde estar�o os relat�rios
	private static final String SUBREPORT_DIR = "SUBREPORT_DIR"; // Um relat�rio dentro  do outro
	private String SEPARATOR = File.separator; //
	private String caminhoArquivoRelatorio = null; // caminho do relatorio 
	private JRExporter tipoArquivoExportado = null;// tipo expota��o
	private String caminhoSubreport_Dir = "";////  caminho do superReport dir
	private File arquivoGerado = null;// caminho gerado

	
	// List<?> listDataBeanColletionReport: recebe  lista de objeto(dados  exibido impresso)
	// HashMap parametrosRelatorio :parametros do relat�rio(colunas)
	// String nomeRelatorioJasper : nome do relatorio de entrada(babe�alho)
	// String nomeRelatorioSaida :  nome do relatorio de sa�da
	// ServletContext servletContext:
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String geraRelatorio(List<?> listDataBeanColletionReport,
			HashMap parametrosRelatorio, String nomeRelatorioJasper,
			String nomeRelatorioSaida, ServletContext servletContext) throws JRException,
			FileNotFoundException {
		
		/*Cria a lista de collectionDataSource de beans que carregam os dados para o relat�rio*/
		//api java
		//relatorio gerado precisa criar  jrbcds e passar a lista
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listDataBeanColletionReport);

		/*
		 * Fornece o caminho fisico at� a pasta que contem os relat�rios
		 * compilador .jasper
		 */
		String caminhoRelatorio = servletContext.getRealPath(FOLDER_RELATORIOS);
		
       //pega o  arquivo de exten��o .jasper(compilado)
		File file = new File(caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + ".jasper");

		// caso n�o encontre o relatorio no caminho de cima deve  tentar  pegar pela logica  do if abaixo:
		if (caminhoRelatorio == null
				|| (caminhoRelatorio != null && caminhoRelatorio.isEmpty()) || !file.exists()) {
			caminhoRelatorio = this.getClass().getResource(FOLDER_RELATORIOS).getPath();
			SEPARATOR = "";
		}	
		
		/*caminho para imgens*/
		parametrosRelatorio.put("REPORT_PARAMETERS_IMG", caminhoRelatorio); 

		/* caminho completo at� o relat�rio compilado indicado */
		String caminhoArquivoJasper = caminhoRelatorio +  SEPARATOR + nomeRelatorioJasper + ".jasper";

		/* Faz o carregamento do relat�rio indicado. */
		//carregamento do arquivo jasper 9compilado)
		JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(caminhoArquivoJasper);

		/* Seta param�tro SUBREPORT_DIR com o caminho fisico para sub-reports. */
		caminhoSubreport_Dir = caminhoRelatorio + SEPARATOR ;
		parametrosRelatorio.put(SUBREPORT_DIR, caminhoSubreport_Dir);

		/* Carrega o arquivo compilado para a m�moria. */
		JasperPrint impressoraJasper = JasperFillManager.fillReport(relatorioJasper, parametrosRelatorio, jrbcds);

		tipoArquivoExportado = new JRPdfExporter();

		/* Caminho relat�rio exportado */
		caminhoArquivoRelatorio = caminhoRelatorio + SEPARATOR + nomeRelatorioSaida + ".pdf";

		/* Cria novo File exportado */
		arquivoGerado = new File(caminhoArquivoRelatorio);

		/* Prepara a impress�o */
		tipoArquivoExportado.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);

		/* Nome do arquivo fisico a ser impresso/exportado */
		tipoArquivoExportado.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);

		/* Executa a exporta��o */
		tipoArquivoExportado.exportReport();

		/* Remove o arquivo do servidor ap�s ser feito o download pelo usu�rio */
		arquivoGerado.deleteOnExit();
		
		return caminhoArquivoRelatorio;
		
	}

}
