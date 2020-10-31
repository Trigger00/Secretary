package pe.edu.lamolina.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;

public class TypesUtil {
    private static List<MapCaracter> mapCaracteres;
    
    private static List<MapCaracter> getMapCaracteres() {
        return mapCaracteres;
    }
    static{
    	if(mapCaracteres==null){
            mapCaracteres=new ArrayList<>();
            mapCaracteres.add(new MapCaracter("&#193;", "Ã�"));
            mapCaracteres.add(new MapCaracter("&#201;", "Ã‰"));
            mapCaracteres.add(new MapCaracter("&#205;", "Ã�"));
            mapCaracteres.add(new MapCaracter("&#211;", "Ã“"));
            mapCaracteres.add(new MapCaracter("&#218;", "Ãš"));
            mapCaracteres.add(new MapCaracter("&#225;", "Ã¡"));

            mapCaracteres.add(new MapCaracter("&#233;", "Ã©"));
            mapCaracteres.add(new MapCaracter("&#237;", "Ã­"));
            mapCaracteres.add(new MapCaracter("&#243;", "Ã³"));
            mapCaracteres.add(new MapCaracter("&#250;", "Ãº"));

            mapCaracteres.add(new MapCaracter("&#241;", "Ã±"));
            mapCaracteres.add(new MapCaracter("&#209;", "Ã‘"));
        }
    }
    public static Byte[] getDefaultByteArray(byte[] bytearray) {
        try {
            Byte[] bigByteArray = new Byte[bytearray.length];
            for(int i = 0; i < bytearray.length; i++)
            {
            bigByteArray[i] = new Byte(bytearray[i]);
            }
            return bigByteArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Date addMonth(Date fecha, int months){
        Calendar cal=Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }
    
    public static String getDefaultUpperCase(String value) {
        try {
            if (value != null && StringUtils.isNotBlank(value.toString())) {
                return value.toUpperCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    public static byte[] getDefaultByteArray(Byte[] bytearray) {
        try {
            byte[] bigByteArray = new byte[bytearray.length];
            for(int i = 0; i < bytearray.length; i++)
            {
            bigByteArray[i] = bytearray[i].byteValue();
            }
            return bigByteArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static BigDecimal getDefaultBigDecimal(Object objValue) {
        try {
            if (objValue != null && StringUtils.isNotBlank(objValue.toString())) {
                return BigDecimal.valueOf(Double.valueOf(objValue.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static BigDecimal getDefaultBigDecimal(Object objValue, double dblDefault) {
        try {
            if (objValue != null && StringUtils.isNotBlank(objValue.toString())) {
                return BigDecimal.valueOf(Double.valueOf(objValue.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new BigDecimal(dblDefault);
        }
        return new BigDecimal(dblDefault);
    }

    public static Integer getDefaultInt(Object objValue) {
        try {
            if (objValue != null && StringUtils.isNotBlank(objValue.toString())) {
                return Integer.valueOf(objValue.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static Integer getDefaultInt(Object objValue, Integer defaultInt) {
        try {
            if (objValue != null && StringUtils.isNotBlank(objValue.toString())) {
                return Integer.valueOf(objValue.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return defaultInt;
        }
        return defaultInt;
    }

    public static Integer getDefaultNullInt(Object objValue) {
        try {
            if (objValue != null && StringUtils.isNotBlank(objValue.toString())) {
                return Integer.valueOf(objValue.toString());
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Long getDefaultLong(Object objValue) {
        if (objValue instanceof java.lang.Long) {
            return (Long) objValue;
        }
        try {
            if (objValue != null && StringUtils.isNotBlank(objValue.toString()) && !objValue.toString().equals("null")) {
                return Long.valueOf(objValue.toString());
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
        return null;
    }

    public static Long getDefaultLong(BigDecimal bigDecimalValue) {
        try {
            if (bigDecimalValue != null) {
                return bigDecimalValue.longValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
        return 0L;
    }

    public static Long getDefaultLong(Object objValue, Long defaultValue) {
        try {
            if (objValue != null && StringUtils.isNotBlank(objValue.toString())) {
                return Long.valueOf(objValue.toString());
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return defaultValue;
        }
        return defaultValue;
    }

    public static Float getDefaultFloat(Object objValue, Float defaultValue) {
        try {
            if (objValue != null && StringUtils.isNotBlank(objValue.toString())) {
                return Float.parseFloat(objValue.toString());
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return defaultValue;
        }
        return defaultValue;
    }

    public static String getDefaultString(Object objValue) {
        try {
            if (objValue != null && StringUtils.isNotBlank(objValue.toString())) {
                return objValue.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    
    public static String getDefaultString(Double objValue,String format) {
        try {
            Double value=0.0;
            if(objValue!=null){
                value=objValue;
            }
            DecimalFormat df=new DecimalFormat(format);
            return df.format(value);
        } catch (Exception e) {
            e.printStackTrace();
            return "0.00";
        }
    }

    /**
     * @param objValue Cadena que contiene la fecha
     * @param dateFormat formato de objValue
     * @return Timestamp
     */
    public static Timestamp getDefaultTimeStamp(Object objValue, String dateFormat) {
        if (objValue != null && StringUtils.isNotBlank(objValue.toString())) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                java.util.Date date = sdf.parse(objValue.toString());//new Date(objValue.toString());
                return new java.sql.Timestamp(date.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Date getDefaultDate(Object objValue, String dateFormat) {
        if (objValue != null && StringUtils.isNotBlank(objValue.toString())) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                java.util.Date date = sdf.parse(objValue.toString());//new Date(objValue.toString());
                return new Timestamp(date.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getCurrentStringDate(String dateFormat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            java.util.Date date = new Date();
            return sdf.format(date).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getTimestampStringDate(String dateFormat, Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            return sdf.format(date).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

 

    public static Double getDefaultDouble(BigDecimal value) {
        try {
            if (value != null) {
                return value.doubleValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static double getDefaultDouble(Object objValue, double defaultValue) {
        try {
            if (objValue != null) {
                if (objValue instanceof BigDecimal) {
                    return ((BigDecimal) objValue).doubleValue();
                } else if (StringUtils.isNotBlank(objValue.toString())) {
                    return Double.parseDouble(objValue.toString());
                } else {
                    return defaultValue;
                }
            } else {
                return defaultValue;
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean getDefaultBoolean(Object value) {
        boolean response = false;

        if ((Integer) value == 1) {
            response = true;
        } else {
            response = false;
        }

        return response;

    }

    public static String getDefaultString(Timestamp fecha, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("ES"));
        return sdf.format(fecha);

    }
    
    public static int getRandomNumber(){
    	Random randomGenerator = new Random();
    	int result=0;
    	for (int idx = 1; idx <= 10; ++idx){
    		result = randomGenerator.nextInt(100); 
    	}
    	return result;
    }
    
    public static String getFormatFile(String fileName){
    	int poscion =fileName.lastIndexOf( '.' );
    	String name = fileName.substring(0,poscion);
    	String tipo =fileName.substring(poscion);
    	String result=name+"_"+getRandomNumber()+tipo;
    	return result;
    }
    public static int getHourBetweenDate(Date fechaInicio, Date fechaFinal){
    	 try {
    		 long segundos = (fechaFinal.getTime() - fechaInicio.getTime()) / 1000;
    		 return  (int) (segundos / 3600); 
         
    	 } catch (Exception e) {
             e.printStackTrace();
             return 0;
         
    	 }
    }
    
    public static Boolean isEmptyString(String value,Boolean noSpace){
        if(value==null){
            return Boolean.TRUE;
        }else{
            if(noSpace){
                if(value.trim().equals("")){
                    return Boolean.TRUE;
                }
            }else{
                if(value.equals("")){
                    return Boolean.TRUE;
                }
            }
        }    
        return Boolean.FALSE;
    }
    public static Boolean isEmptyString(String value){
        return TypesUtil.isEmptyString(value, Boolean.FALSE);
    }
    
    private static Boolean isNumeric(String numero,Boolean yesNumeric){
		try {
			Integer.parseInt(numero);
			return Boolean.TRUE;
		} catch (NumberFormatException e){
			return Boolean.FALSE;
		} 
	}
    public static Boolean isNumeric(String numero){
        return TypesUtil.isNumeric(numero, Boolean.TRUE);
    }
    public static String getDefaultString(Date fecha, String format) {
        if(fecha==null){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("ES"));
        return sdf.format(fecha);

    }
    public static Boolean isGreaterThan(Date principal,Date secondary){
    	return (principal.compareTo(secondary)>0);
    }
    public static String decodeISOString(String value){
        for (MapCaracter mapCaracter : getMapCaracteres()) {
            value=value.replace(mapCaracter.getCodigo(), mapCaracter.getValor());
        }
        return value;
    }
            
    public static String getDateLetter(Date fecha) {
    	if(fecha==null){
            return "";
        }
    	String dia=TypesUtil.getDefaultString(fecha, "dd"),
    			mes=TypesUtil.getDefaultString(fecha, "MMMM"),
				aro=TypesUtil.getDefaultString(fecha, "yyyy");
		int numberDia = Integer.parseInt(dia),
		 	numberAro = Integer.parseInt(aro);
		NumberToLetterConverter convert = new NumberToLetterConverter();
		String textoDia=convert.convertirLetras(numberDia),
				textoAro=convert.convertirLetras(numberAro),
				textoCompleto;
		
		if(numberDia>1){
			textoDia="a los "+textoDia+" días";
		}else{
			textoDia="al primer día";
		}
		if( mes.equals("septiembre") ){
			mes = "setiembre";
		}
		
		if(numberAro>1999){	
			textoCompleto= textoDia+" del mes de "+mes+" del "+WordUtils.capitalize(textoAro);
		}else{
			textoCompleto= textoDia+" del mes de "+mes+" de "+WordUtils.capitalize(textoAro);
		}
        return textoCompleto;

    }
    public static String getDateNumber(Date fecha) {
    	if(fecha==null){
            return "";
        }
    	String dia=TypesUtil.getDefaultString(fecha, "dd"),
    			mes=TypesUtil.getDefaultString(fecha, "MMMM"),
				aro=TypesUtil.getDefaultString(fecha, "yyyy"),
				textoCompleto;
		int numberAro = Integer.parseInt(aro);
		
		if( mes.equals("septiembre") ){
			mes = "setiembre";
		}
		
		if(numberAro>1999){			
			textoCompleto=dia+" de "+mes+" del "+aro;
		}else{
			textoCompleto=dia+" de "+mes+" de "+aro;
		}
        return textoCompleto;

    }
    public static String  setFormatUTF8(String dataRaw){
		String result;
        try {
        	result=	new String(dataRaw.getBytes ("iso-8859-1"), "UTF-8"); 
		} catch (Exception e) {
			result=null;
		}	
        return result;
	}
    
}
