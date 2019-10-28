// Sobrescribe el prototipo 'children' nativo.
// Añade soporte para Document y DocumentFragment en IE9 y Safari.
// Devuelve un array en lugar de HTMLCollection.
;(function(constructor) {
    if (constructor &&
        constructor.prototype &&
        constructor.prototype.children == null) {
        Object.defineProperty(constructor.prototype, 'children', {
            get: function() {
                var i = 0, node, nodes = this.childNodes, children = [];
                while (node = nodes[i++]) {
                    if (node.nodeType === 1) {
                        children.push(node);
                    }
                }
                return children;
            }
        });
    }
})(window.Node || window.Element);



function process_alert(node){
	node.classList.remove('invisible');
	setTimeout( () => {
		node.classList.add('visible');
	}, 500);	
}


function process_validation_error(node){

	console.log(node.children[1].value);
	
	let element = document.getElementsByName(node.children[1].value)[0];
	let p = document.querySelector('#msg_' + node.children[1].value);

	if(element !== undefined)
		element.classList.add('invalid_field');
	p.innerHTML = node.children[0].value;
	p.classList.add('msg_margin');
	p.classList.remove('invisible');

}


function show_messages(){
	let alerts = document.querySelectorAll('.Alert');
	let errors = document.querySelectorAll('.VError');

	for(let i = 0; i < alerts.length; i++)
		process_alert(alerts[i]);
	
	for(let i = 0; i < errors.length; i++)
		process_validation_error(errors[i]);
}

function close_alert(id){
	id.classList.remove('visible');
	setTimeout( () => {
		id.classList.add('invisible');
	}, 300);
}

function show_aside_message(text){
	let am = document.querySelector('#aside_message');
	let p = document.querySelector('#aside_message_p');

	p.innerHTML = text;
	am.classList.add('aside_message_show');
	setTimeout(() => {
		am.classList.remove('aside_message_show');
	}, 2000);
}

function go_to_translations(id){
	window.location.href = '/proyectos/traducciones?id='+id;
}


function hidde_new_projects(){
	let new_proyect = document.querySelector('.NewProjects');
	new_proyect.classList.remove('visible');
	setTimeout(() => {
		new_proyect.classList.add('invisible');
	}, 100);
}


function create_div_project(id, name, date){
	var new_project = document.createElement('div');
	var new_project_name = document.createElement('p');
	var new_project_date = document.createElement('p');
	var new_project_img = document.createElement('div');

	new_project.classList.add('Projects_container_project');
	new_project.classList.add('add_project');

	new_project_name.classList.add('Projects_container_project_name');
	new_project_name.innerHTML = name;

	new_project_date.classList.add('Projects_container_project_date');
	new_project_date.innerHTML = date;

	new_project_img.classList.add('Projects_container_project_image');

	new_project.appendChild(new_project_name);
	new_project.appendChild(new_project_date);
	new_project.appendChild(new_project_img);

	return new_project;
}


function add_project(id, name, date){
	hidde_new_projects();
	let new_project = create_div_project(id, name, date);
	let container = document.querySelector('.Projects_container');
	new_project.onclick = () => { go_to_translations(id); };
	container.insertBefore(new_project, container.childNodes[0]);
	setTimeout(() => {
		new_project.classList.add('add_project_animate');
	}, 500);
}


function edit_project_name(){
	let p = document.querySelector('.Translations_project_description_name');
	let pt = document.querySelector('.Translations_project_description_nametxt');

	if(!p.classList.contains('invisible')){
		p.classList.add('invisible');
		pt.classList.remove('invisible');
		pt.value = p.innerHTML;
		document.querySelector('.Translations_project_options button:first-child').innerHTML = 'Hecho';
	}
	else{
		if(p.innerHTML == pt.value){
			p.classList.remove('invisible');
			pt.classList.remove('invalid_field');
			pt.classList.add('invisible');
			document.getElementById('msg_badprojectname').classList.add('invisible');
			return;
		}
		save_project_name(p, pt);
	}
}


function save_project_name(p, pt){
	let request = new XMLHttpRequest();
	let form = new FormData();

	form.append('nombreProyecto', pt.value);

	request.open('GET', '/proyectos/cambiar?nombre='+pt.value.replace(' ', '%')+
						'&oldnombre='+p.innerHTML.replace(' ', '%'), true);
	request.onreadystatechange = function(aEvent){
		if (request.readyState == 4) {
			if(request.status == 200){
				let req = JSON.parse(request.responseText);

				if(Object.entries(req.err).length === 0){
					p.innerHTML = pt.value;
					p.classList.remove('invisible');
					pt.classList.add('invisible');
					pt.classList.remove('invalid_field');
					document.querySelector('.Translations_project_options button:first-child').innerHTML = 'Editar';
					document.getElementById('msg_badprojectname').classList.add('invisible');
				}
				else{
					let perr = document.getElementById('msg_badprojectname');
					perr.classList.remove('invisible');
					perr.innerHTML = req.err.messages[0].text;
					pt.classList.add('invalid_field');
				}
			}
		}
	};

	request.send(form);
}


function show_delete_alert(){
	let alert = document.querySelector('.DeleteProjects');
	process_alert(alert);
}


function delete_project(){
	let request = new XMLHttpRequest();
	let id = document.getElementById('project_id');

	request.open('GET', '/proyectos/eliminar?id='+id.value, true);

	request.onreadystatechange = function(aEvent){
		if (request.readyState == 4) {
			if(request.status == 200){
				window.location.href = '/proyectos/todos';
			}
		}
	};

	request.send();
}

function save_profile_changes(event){
	event.preventDefault();

	let request = new XMLHttpRequest();
	let nombre = document.querySelector('.Profile_container_text');
	let estudios = document.querySelector('.Profile_container_studies');

	request.open('GET', '/usuarios/cambiar?nombre='+nombre.value+
										'&estudios='+estudios.value, true);

	request.onreadystatechange = function(aEvent){
		if (request.readyState == 4) {
			if(request.status == 200){
				let req = JSON.parse(request.responseText);

				if(Object.entries(req.err).length === 0){
					document.querySelector('#a_user_name').innerHTML = nombre.value;
					show_aside_message('Cambios guardados.');
				}
				else{
					let perr = document.getElementById('msg_nombre');
					perr.classList.remove('invisible');
					perr.innerHTML = req.err.messages[0].text;
					nombre.classList.add('invalid_field');
				}
			}
		}
	};

	request.send();

}

function change_password(event){
	event.preventDefault();

	let request = new XMLHttpRequest();
	let contra = document.querySelector('#contra');
	let contraDos = document.querySelector('#contraDos');

	request.open('GET', '/usuarios/cambiarContra?contra='+contra.value+
										'&contraDos='+contraDos.value, true);


	request.onreadystatechange = function(aEvent){
		if (request.readyState == 4) {
			if(request.status == 200){
				let req = JSON.parse(request.responseText);

				if(Object.entries(req.err).length === 0){
					show_aside_message('Cambios guardados.');
				}
				else{
					for(let i = 0; i < req.err.messages.length; i++){
						let p = document.getElementById('msg_'+req.err.messages[i].name);
						p.classList.remove('invisible');
						p.innerHTML = req.err.messages[i].text;
						document.getElementById(req.err.messages[i].name).classList.add('invalid_field');
					}
				}
			}
		}
	};

	request.send();

}


function choose_file(){
	let f = document.getElementById('Profile_input_file');
	f.click();
}


function upload_photo(){
	let form = new FormData();
	let request = new XMLHttpRequest();
	let fc = document.getElementById('Profile_input_file');
	let token = document.querySelector('input[name=csrfmiddlewaretoken]');

	form.append('foto', fc.files[0]);
	form.append('csrfmiddlewaretoken', token.value);

	request.open('POST', '/usuarios/cambiarFoto', true);

	request.onreadystatechange = function(aEvent){
		if (request.readyState == 4) {
			if(request.status == 200){
				let req = JSON.parse(request.responseText);

				if(Object.entries(req.err).length === 0){
					setTimeout(() => {
						console.log(req.url_imagen);
						document.querySelector('.Profile_container_avatar_photo').style.backgroundImage = "url("+req.url_imagen+")";
						document.querySelector('.NavigationBar_options_avatar').style.backgroundImage = "url("+req.url_imagen+")";
					}, 2000);
				}
				else{
					console.log('no se pudo')
				}
			}
		}
	};

	request.send(form);
	document.querySelector('.Profile_container_avatar_photo').style.backgroundImage = "url(/media/loading.svg)";
}


function show_new_projects_dialog(){
	let new_proyect = document.querySelector('.NewProjects');
	new_proyect.classList.remove('invisible');
	setTimeout(() => {
		new_proyect.classList.add('visible');
	}, 100);
}


function create_new_project(){
	let new_project = document.querySelector('#nuevo_proyecto_nombre');
	let project_name = new_project.value;
	let request = new XMLHttpRequest();
	let form = new FormData();

	form.append('nombreProyecto', project_name);

	request.open('GET', '/proyectos/nuevo?nombre='+project_name.replace(' ', '%'), true);
	request.onreadystatechange = function(aEvent){
		if (request.readyState == 4) {
			if(request.status == 200){
				let req = JSON.parse(request.responseText);

				if(Object.entries(req.err).length === 0){
					document.getElementById('msg_badprojectname').classList.add('invisible');
					add_project(req.id, project_name, req.fechaModificacion);
				}
				else{
					let perr = document.getElementById('msg_badprojectname');
					perr.classList.remove('invisible');
					perr.innerHTML = req.err.messages[0].text;
					new_project.classList.add('invalid_field');
				}

			}
		}
	};

	request.send(form);
}

function download_project(){
	let pid = document.querySelector('#project_id');
	let pname = document.querySelector('#project_name');
	let token = document.querySelector('input[name=csrfmiddlewaretoken]');
	let request = new XMLHttpRequest();
	let form = new FormData();

	form.append('proyecto', pid.value);
	form.append('proyecto_nombre', pname.value);
	form.append('csrfmiddlewaretoken', token.value);

	request.open('POST', '/proyectos/descargar', true);

	request.onreadystatechange = function(aEvent){
		if (request.readyState == 4) {
			if(request.status == 200){
				let req = JSON.parse(request.responseText);

				if(Object.entries(req.err).length === 0){
					console.log(req.url_file);
					window.location.href=req.url_file;
				}
				else{
					console.log('no se pudo')
				}
			}
		}
	};

	request.send(form);

}

function logout(){
	document.getElementById("logout_form").submit();
}

show_messages();





