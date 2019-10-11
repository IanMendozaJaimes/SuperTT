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


show_messages();