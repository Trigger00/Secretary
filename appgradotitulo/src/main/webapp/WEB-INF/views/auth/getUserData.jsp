{
	data : {
		userInfo : {
			nombreUsuario : "${usuario.textoNombre}"
		},
		userMenus : {
			text : 'ROOT',
			menuID : '0',
			codigo : 'ROOT',
			children : [ 
			{
				text : 'Modulo Grados y Titulos',
				menuID : '1',
				codigo : 'administracion',
				children : [
					{
						text : 'Datos Generales',
						menuID : '2',
						codigo : 'datoGeneral',
						leaf : true,
						nombre : 'Datos Generales',
						urlPage : 'datoGeneral/load'
					},
					{
						text : 'Grados y Titulos',
						menuID : '3',
						codigo : 'gradoTitulo',
						leaf : true,
						nombre : 'Grados y Titulos',
						urlPage : 'gradoTitulo/load'
					},
					{
						text : 'Duplicado',
						menuID : '4',
						codigo : 'duplicado',
						leaf : true,
						nombre : 'Duplicado',
						urlPage : 'duplicado/load'
					},
					{
						text : 'Revalidas',
						menuID : '5',
						codigo : 'revalida',
						leaf : true,
						nombre : 'Revalidas',
						urlPage : 'revalida/load'
					},
					{
						text : 'Diplomados',
						menuID : '6',
						codigo : 'diplomado',
						leaf : true,
						nombre : 'Diplomados',
						urlPage : 'diplomado/load'
					},
					{
						text : 'Facultades y Especialidades',
						menuID : '7',
						codigo : 'facultades',
						leaf : true,
						nombre : 'Facultades',
						urlPage : 'facultades/load'
					},
					{
						text : 'Autoridades',
						menuID : '8',
						codigo : 'autoridad',
						leaf : true,
						nombre : 'Autoridad',
						urlPage : 'autoridad/load'
					},
					{
						text : 'Agenda Grupo',
						menuID : '9',
						codigo : 'agendaGrupo',
						leaf : true,
						nombre : 'Agenda Grupo',
						urlPage : 'agendaGrupo/load'
					}
				],
				expanded : true
			}
			,{
				text : 'Mantenimientos',
				menuID : '1',
				codigo : 'matenimientos',
				children : [
					{
						text : 'Universidad',
						menuID : '2',
						codigo : 'universidad',
						leaf : true,
						nombre : 'Universidad',
						urlPage : 'mantenimiento/universidad/load'
					},
					{
						text : 'Ciclo',
						menuID : '3',
						codigo : 'ciclo',
						leaf : true,
						nombre : 'Ciclo',
						urlPage : 'mantenimiento/ciclo/load'
					}
				],
				expanded : true
			}
			,
			{
				text : 'Tramite Documentario',
				menuID : '10',
				codigo : 'tramiteDocumentario',
				children : [
					{
						text : 'Registro',
						menuID : '11',
						codigo : 'registro',
						leaf : true,
						nombre : 'Registro',
						urlPage : 'tramiteDocumentario/registro/load'
					},
					{
						text : 'Tramite',
						menuID : '12',
						codigo : 'tramite',
						leaf : true,
						nombre : 'Tramite',
						urlPage : 'tramiteDocumentario/tramite/load'
					},
					{
						text : 'Documento',
						menuID : '13',
						codigo : 'documento',
						leaf : true,
						nombre : 'Documento',
						urlPage : 'tramiteDocumentario/documento/load'
					}
				],
				expanded : true
			}  
			]
		}
	},
	success : true
}