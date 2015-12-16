# Projet 09 BDD - JS & AJAX
# Edouard CATTEZ - Julien LELEU

## Sommaire:
- Introduction
- Frameworks
  - Présentation de jQuery
  - Présentation de MooTools
- jQuery vs MooTools
    - Quelques exemples
    - Application de gestion élémentaire de base de données
- Conclusion

## Introduction

Dans le monde du web, un langage de script coté client s'est démarqué des autres. Son nom ? JavaScript.
Bien que celui-ci ne soit pas toujours facile à prendre en main, notamment pour le déboguage et l'ajout d'éléments HTML dans l'arbre DOM,
des [Frameworks](https://fr.wikipedia.org/wiki/Framework) on fait leur apparition pour simplifier la programmation des développeurs.

Parmis ces Frameworks, jQuery et MooTools en sont de bons exemples.

Nous allons donc les illustrer au travers de 4 exemples différents :

1. Réalisation d'un compteur d'entrées dans une base raffraichie toutes les cinq secondes.
2. Recherche de personne par nom avec mise à jour de la réponse à chaque lettre entrée.
3. Recherche des attributs d'une personne depuis son id.
4. Listes des cinq personnes les plus âgées et les plus jeunes séparées en 2 textarea avec raffraichissement par intervalle de cinq secondes ou via le clic sur un bouton **Rafraichir**.

Ces exemples sont réalisés dans le fichier exo.html

## Installation

- Clonez le répertoire git dans le dossier webapps de votre serveur Tomcat.
- Note : dans le cas où vous auriez reçu une archive, vous pouvez directement extraire son contenu dans le dossier webapps.

```
git clone https://github.com/JulienLeleu/Projet9BDD.git
```

- Modifiez vos informations de connexion dans le fichier WEB-INF/web.xml
- Créez la table personne comme ci-dessous

```
CREATE TABLE personne(id int, login text NOT NULL, nom text NOT NULL, prenom text NOT NULL, datenaiss DATE, CONSTRAINT pk_personne PRIMARY KEY (id));
```

- Démarrez Tomcat et accédez à l'application depuis le contexte /Projet9BDD

## Frameworks

### Présentation [JQuery](https://jquery.com/)

JQuery est une librairie Javascript open source légère est rapide conforme au CSS3.
Ce Toolkit est cross-browser, ce qui signifie qu'il fonctionne sur presque tous les navigateurs du moment.
Il dispose d'une API en ligne compléte et une communauté assez active.

### Présentation [MooTools](http://mootools.net/)

MooTools est une collection d'utilitaires JavaScript conçus pour le développeur intermédiaire à avancé JavaScript.
Il vous permet d'écrire du code puissant et flexible, élégant, bien documenté, avec des API cohérentes.
Le code de MooTools est largement documenté et facile à lire, ce qui vous permet d'étendre les fonctionnalités pour répondre à vos exigences.

## jQuery vs MooTools

### Quelques exemples

Nous allons illustrer quelques exemples pour lesquels soit jQuery, soit MooTools s'avère plus pratique que l'un ou l'autre. Pour cela, nous utiliserons le corps de page suivant:

```
<body> 
    <div id="main"> 
		<div id="menu"> 
		<ul> 
			<li>element 1</li> 
			<li>element 2</li> 
			<li>element 3</li> 
			<li>element 4</li> 
		<ul> 
		</div> 
			<div id="status">
			</div> 
			<div id="content"> 
				<p> Paragraphe 1 </p> 
				<p> Paragraphe 2 
					<a href="#" title="Test 1" class="ajax">Cliquez</a> 
					<a href="#" title="Test 2" class="ajax">Cliquez</a> 
				</p> 
		</div>
	</div> 
</body>
```

|| jQuery | Mootols |
|--------|--------|---------|
| Accès à la div content | $("#content") | $("content") |
| Elements "li" du div "menu" | $("#menu ul li") | $$("#menu ul li") |
| Propriétés de style | $(“#status”).css(‘border’,‘1px solid  red’).css(‘background-color’,‘#EFEFEF’).css(‘font-weight’,‘bold’) |  $("status").setStyles({ "border": "1px solid red", "background-color": "#EFEFEF",  "font-weight": "bold"}) |
| Ajouter un lien dans la seconde li du menu | $("#menu li")[1].append("<a href="www.google.fr">Google</a>") |  new Element("a").set({"href" = "www.google.fr"}).setText("Google").inject($$("#menu li")[1]) |
Evenement DOM Ready | $(document).ready(function (){ alert('Hello world !'); }); | window.addEvent("domready", function(){ alert('Hello world !'); }) |

D'un point de vue général, MooTools a une façon bien structurée d'écrire du JavaScript en passant principalement par la création d'objets JavaScript. D'un autre coté, jQuery possède une palette monumentale de fonctionnalités simples et rapides à écrire.

### Application de gestion élémentaire de base de données

Nous allons maintenant illustrer l'utilisation d'Ajax au travers d'une application web de gestion élémentaire de base de données. De plus, nous utiliserons les fonctionnalités proposées par jQuery.

![Interface web](https://cloud.githubusercontent.com/assets/10498113/11848961/b27fe23a-a425-11e5-812e-1135f928d783.jpg)

Sur le schéma ci-dessus, nous pouvons observer 4 fonctions principales :
- listerTables()
- supprimerTable()
- supprimerLigne(ligne)
- ajouter()

Celles-ci font toutes appel au serveur de manière asynchrone afin que l'interface utilisateur soit fluide et que les modifications soient instantannées et sans raffraichissement. Ces méthodes sont écrites dans un script dont la structure pourrait être la suivante :

```
function listerTables() {
  // La requête renvoie un objet JSON représentant un tableau des noms des tables du SGBD
  $.get("servlet/tables/lister", function(data) {
    for (var i = 0; i < data.length; i++) {
      $("#ul-perso").append("<li id=" + data[i] = ">" + data[i] + "</li>");
    }
  });
}

function supprimerTable() {
  // La requête renvoie un objet JSON représentant le nom de la table supprimée si la suppression a eu lieu
  $.post("servlet/tables/supprimer", {table: $("#nom-Table") }, function(data) {
    $("#" + data.name).remove();
  });
}

function supprimerLigne(ligne) {
  // La requête renvoie un objet JSON représentant l'identifiant de la ligne supprimée si la suppression a eu lieu
  $.post("servlet/tables/supprimer", {table: $("#nom-Table"), idLigne : ligne}, function(data) {
    $("#" + data).remove();
  }
}

function ajouter() {
  // La requête renvoie un objet JSON représentant la ligne ajoutée à la table avec son id et ses colonnes
  $.post("servlet/tables/ajouter", {table: $("#nom-Table"), columns : $("#.nouvelle-colonnes")}, function(data) {
    var table = $("#table-courante");
    var id = data.id;
    table.append("<tr>");
    for (var i = 0; i < data.columns.length; i++) {
      table.append("<td id=" + data.id + "_" + data.columns[i] + ">" + data.columns[i] + "</td>");
    }
    table.append("</tr>");
  }
}

// Liste les tables toutes les 2 minutes
setInterval(listerTables, 120000);
```

## Conclusion

TODO
