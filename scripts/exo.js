/** Fonctions communes **/

var table = "personne";

function stringifyCount(count) {
	return "Il y a actuellement " + count + " enregistrements dans la table " + table;
}

function stringifyCSV(data) {
	return data.replace(/;/g, "\n");
}


/** Version à la main **/

function main_countPersonne() {
	var ai = new AJAXInteraction("servlet/Count?table=" + table, function(data) {
		document.getElementById("main_info").value = stringifyCount(data);
	});
	ai.doGet();
}

function main_findPersonne(nom) {
	if (nom != "") {
		var ai = new AJAXInteraction("servlet/RechercherPersonne?nom=" + nom, function(data) {
			document.getElementById("main_result").value = stringifyCSV(data);
		});
		ai.doGet();
	}
}

function main_findPersonneById(id) {
	if (id != "" && !isNaN(id)) {
		var ai = new AJAXInteraction("servlet/SelectPersonne?id=" + id, function(data) {
			try {
				var login = data.getElementsByTagName("login")[0].firstChild;
				var nom = data.getElementsByTagName("nom")[0].firstChild;
				var prenom = data.getElementsByTagName("prenom")[0].firstChild;
				var datenaiss = data.getElementsByTagName("datenaiss")[0].firstChild;
			
				document.getElementById("main_form_login").value = login.nodeValue;
				document.getElementById("main_form_nom").value = nom.nodeValue;
				document.getElementById("main_form_prenom").value = prenom.nodeValue;
				document.getElementById("main_form_datenaiss").value = datenaiss.nodeValue;
			} catch (err) {
				document.getElementById("main_form_id").value = "Id inexistant";
				document.getElementById("main_form_login").value = "";
				document.getElementById("main_form_nom").value = "";
				document.getElementById("main_form_prenom").value = "";
				document.getElementById("main_form_datenaiss").value = "";
			}
		});
		ai.doGet();
	}
}

function main_getJeunesVieux() {
	var ai = new AJAXInteraction("servlet/SelectMinMaxPersonne", function(data) {
		var json = JSON.parse(data);
		document.getElementById("main_textarea_jeunes").value = "";
		document.getElementById("main_textarea_vieux").value = "";
		for (var i in json.jeunes) {
			document.getElementById("main_textarea_jeunes").value += json.jeunes[i].nom + "\n";
		}
		for (var i in json.vieux) {
			document.getElementById("main_textarea_vieux").value += json.vieux[i].nom + "\n";
		}
	});
	ai.doGet();
}

/** Version jQuery **/

function jquery_countPersonne() {
	$.get("servlet/Count?table=" + table, function(data, status) {
		$("#jquery_info").text(stringifyCount(data));
	});
}

function jquery_findPersonne(nom) {
	if (nom != "") {
		$.get("servlet/RechercherPersonne?nom=" + nom, function(data, status) {
			$("#jquery_result").text(stringifyCSV(data));
		});
	}
}

function jquery_findPersonneById(id) {
	if (id != "" && !isNaN(id)) {
		$.get("servlet/SelectPersonne?id=" + id, function(data, status) {
			var xml = $(data);
			$("#jquery_form_id").val(id);
			$("#jquery_form_login").val($(xml.find("login")[0]).text());
			$("#jquery_form_nom").val($(xml.find("nom")[0]).text());
			$("#jquery_form_prenom").val($(xml.find("prenom")[0]).text());
			$("#jquery_form_datenaiss").val($(xml.find("datenaiss")[0]).text());
		}).fail(function() {
			$("[id ^= jquery_form_").val("");
			$("#jquery_form_id").val("Id inexistant");
		});
	}
}

function jquery_getJeunesVieux() {
	$.get("servlet/SelectMinMaxPersonne", function(json) {
		var textareaJeunes = $("#jquery_textarea_jeunes");
		var textareaVieux = $("#jquery_textarea_vieux");
		$("#jquery_textarea_jeunes, #jquery_textarea_vieux").val("");
		for (var i in json.jeunes) {
			textareaJeunes.val(textareaJeunes.val() + json.jeunes[i].nom + "\n");
		}
		for (var i in json.vieux) {
			textareaVieux.val(textareaVieux.val() + json.vieux[i].nom + "\n");
		}
	}).fail(function() {
		console.log("fail");
	});
}

/** Version MooTools **/

function mootools_countPersonne() {

	var myRequest = new Request({
		url: "servlet/Count?table=" + table,
		method: 'get',
		onRequest: function(){
		    $$('#mootools_info').set('text', 'loading ...');
		},
		onSuccess: function(responseText){
		    $$('#mootools_info').set('text', stringifyCount(responseText));
		},
		onFailure: function(){
		    $$('#mootools_info').set('text', 'Sorry, your request failed :(');
		}
	});

	// and to send it:
	myRequest.send();
}

function mootools_findPersonne(nom) {
	var myRequest = new Request({
		url: "servlet/RechercherPersonne?nom=" + nom,
		method: 'get',
		onSuccess: function(responseText){
			$$('#mootools_result').set('text', stringifyCSV(responseText));
		}
	});
	myRequest.send();
}

function mootools_findPersonneById(id) {	
	if (id != "" && !isNaN(id)) {
		var myRequest = new Request({
			url: "servlet/SelectPersonne?id=" + id,
			method: 'get',
			onSuccess: function(data){
				//On se sert de la librairie recommandée par Mootools : XMLToJsObject
				var json = Object.fromXML(data);
				console.log(json);
				$$('#mootools_form_id').set('value', json.id._value);
				$$('#mootools_form_login').set('value', json.login._value)
				$$('#mootools_form_nom').set('value', json.nom._value);
				$$('#mootools_form_prenom').set('value', json.prenom._value);
				$$('#mootools_form_datenaiss').set('value', json.datenaiss._value);
			},
			onFailure: function(){
				$$('[id ^= jquery_form_').set('text', '');
				$$('#mootools_form_id').set('text', 'Id inexistant');
			}
		});
	myRequest.send();
	}
}

function mootools_getJeunesVieux() {
	var myRequest = new Request({
		url: "servlet/SelectMinMaxPersonne",
		method: 'get',
		onSuccess: function(data) {
			var json = JSON.parse(data);
			$$('#mootools_textarea_jeunes').set('text', '');
			$$('#mootools_textarea_vieux').set('text', '');
			for (var i in json.jeunes) {
					$$('#mootools_textarea_jeunes').set('text', $$('#mootools_textarea_jeunes').get('text') + json.jeunes[i].nom + "\n");
			}
			for (var i in json.vieux) {
					$$('#mootools_textarea_vieux').set('text', $$('#mootools_textarea_vieux').get('text') + json.vieux[i].nom + "\n");
			}
		}
	});

	myRequest.send();
}
