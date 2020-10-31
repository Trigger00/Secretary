function isEmpty(val) {
	var result = (val === undefined || val == null || val.length <= 0) ? true
			: false;
	console.log('result ' + result);
	return result;
}

function posicionValue(rawValue){
	var regularExpresion=/,|( )/;
	var result = null;
	if(regularExpresion.test(rawValue) == true) {
		result =' ('+rawValue+')';
	}else{
		result = rawValue;
	}
	return result;
}

function divider(form,nameField,textRaw){
	var regularExpresion= /,(?=[^\)]*[\(]|[^\)]*$)/;
	var splitRawField =[];
	var rawField=textRaw.split(regularExpresion);
	for(i=0;i<rawField.length;i++){
		var convert =rawField[i].split(":");
		splitRawField.push(convert);
	}
	
	Object.keys(form).forEach(function(key) {
		for(x=0;x<splitRawField.length;x++){ 
			if(((splitRawField[x][0].trim()).toUpperCase()).indexOf(((form[key].trim()).toUpperCase())) != -1){
				if(splitRawField[x][1] !=null){
					if((splitRawField[x][1].trim()).indexOf("(")==0){
	            		var sinParentesis =(splitRawField[x][1].trim()).substring(1,(splitRawField[x][1].trim()).length-1);
	            		form[key]=sinParentesis.trim();
					}else if(((splitRawField[x][1]).trim()).length !=0){
						form[key]=splitRawField[x][1].trim();
					}
				}
			}
		}			
	});
				
	Object.keys(form).forEach(function(key) {
		for(i=0;i<nameField.length;i++){
			if((nameField[i].trim())== form[key]){
				form[key]=null;
			}
		}	
	});
	return form;
	
}
function getCatchValue(store,textoFind,column){
	var lengthStore=0;
	var result=null;
	var catchValue=[];
	console.log('textoFind '+textoFind);
	
	Ext.each(store.getRange(), function(item, idx) {
		var valueField=((item.get(column)).toUpperCase()).trim();
		
		lengthStore=lengthStore+1;
		console.log('lengthStore '+lengthStore);
		if((valueField).startsWith(textoFind)==true){
			catchValue.push(item.get(column));
			console.log('catchValue.length '+catchValue.length);
		}
		if(lengthStore==store.getCount()&&catchValue.length==1){
			console.log('fin de ciclo valor encontrado '+catchValue[0]);
			result =catchValue[0];		
		}
	
	});
	return result;
}
function isNumero(numero){
	var result=true;
    if (!/^([0-9])*$/.test(numero))
       result=false;
    return result;
}