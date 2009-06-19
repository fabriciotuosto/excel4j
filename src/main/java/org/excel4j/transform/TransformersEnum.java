package org.excel4j.transform;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unchecked")
public enum TransformersEnum implements Transformer
{
	DATE(){
		public Date transform(String string) {
			try {
				return new SimpleDateFormat().parse(string);
			} catch (ParseException e){
				throw new Error("No se puede convertir en fecha el string "+string,e);
			}
		}
	}, // END OF DATE
	
	NUMBER(){
		public Number transform(String string){
			try {
				return NumberFormat.getInstance().parse(string);
			} catch (ParseException e) {
				throw new Error("No se puede convertir en numero el string "+string,e);
			}
		}
	},
	BIG_DECIMAL(){
		public BigDecimal transform(String string) {
			return new BigDecimal(string);
		}
	}
}
