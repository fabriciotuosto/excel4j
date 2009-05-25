/**
 * 
 */
package org.excel4j.groovy
import com.google.inject.Guice
/**
 * @author fabricio
 *
 */
public class Main{


	/**
	 * @param args
	 */
	public static void main(def args){
		def injector = Guice.createInjector()
		def excel = injector.getInstance(ExcelParser)
		def printrow = {row -> println row.toString()}

		excel.repo.add("/home/fabricio/Documents/XLS")
		excel.each_row("Sheet1",printrow)
		excel.each_row("Producto Comercial",getProductoComercial())
	}
	
	static Closure getProductoComercial()
	{
		return {row -> 
			if (!'Si'.equalsIgnoreCase(row.getColumn('S'))) return
		    def subquery_producto_tecnico = "SELECT p_id FROM t_producto_tecnico_papel WHERE A_DESCRIPCION = '${row.getColumn('I')}'"
	        def subquery_tipo_operacion = "SELECT p_id FROM T_TIPO_OPERACION_COMERCIAL WHERE upper(A_DESCRIPCION) = upper('${row.getColumn('K')}')"
	        def subquery_nivel_est = "SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('C')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('B')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${row.getColumn('A')}') )"
	        def id_geometria="SELECT P_ID FROM T_GEOMETRIA_PAPEL WHERE A_NOMBRE = '${row.getColumn('S')}'"
	        def bool_anuncioempadronado='N'
	        println "INSERT INTO T_PROD_COMERCIAL (P_ID,A_DESCRIPCION,A_NOMBRE) VALUES (S_PROD_COMERCIAL.NEXTVAL,'${row.getColumn('H')}','${row.getColumn('G')}');"
	        println "INSERT INTO T_PROD_COM_PAPEL_CLAS_UBIC (P_ID, F_NIVEL_EST_PAPEL_CLASIFICADOS, F_PRODUCTO_PAPEL_CLASIFICADOS) VALUES ( S_PROD_COM_PAPEL_CLAS_UBIC.NEXTVAL, (SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('C')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('B')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${row.getColumn('A')}') ) ) ), ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${row.getColumn('A')}') ) );" 
	        println "INSERT INTO T_PRODUCTO_COM_PAPEL_CLAS (P_ID,A_ANUNCIANTE_EMPADRONADO,F_TIPO_OPERACION, F_PRODUCTO_TECNICO, F_NIVEL_ESTR_PAPEL_CLASIF, F_PROD_COM_PAPEL_CLAS_UBIC) VALUES (S_PROD_COMERCIAL.CURRVAL, ${bool_anuncioempadronado}, (${subquery_tipo_operacion}), (${subquery_producto_tecnico}), (${subquery_nivel_est}), S_PROD_COM_PAPEL_CLAS_UBIC.CURRVAL );"
	        println "INSERT INTO T_RANGO_FECHA (P_ID,A_DESCRIPCION,A_FECHA_DESDE,A_FECHA_HASTA) VALUES (S_RANGO_FECHA.NEXTVAL, 'Producto Comercial',TO_DATE('31-12-1899', 'DD-MM-YYYY'),TO_DATE('31-12-9999', 'DD-MM-YYYY'));"
	        println "INSERT INTO T_CONFIG_PROD_COM_PAPEL_CLASIF (P_ID,F_GEOMETRIA,F_PRODUCTO_COMERCIAL) VALUES (S_RANGO_FECHA.CURRVAL,( ${id_geometria}),S_PROD_COMERCIAL.CURRVAL);" 	
	        println "INSERT INTO T_CONF_PROD_COM_CANAL_ENTRADA (F_CONFIG_PROD_COMERCIAL,F_CANAL_ENTRADA) VALUES (S_RANGO_FECHA.CURRVAL, 1);"
		}
	}
}
