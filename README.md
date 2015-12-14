# Projet 09 BDD - JS & AJAX
# Edouard CATTEZ - Julien LELEU

##Sommaire:
- Introduction
- Frameworks
  - Présentation de jQuery
  - Présentation de MooTools
- jQuery vs MooTools
- Conclusion

##Introduction

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

##Installation

- Clonez le répertoire git dans le dossier webapps de votre serveur Tomcat.

```
git clone https://github.com/JulienLeleu/Projet9BDD.git
```

- Modifiez vos informations de connexion dans le fichier WEB-INF/web.xml
- Créez la table personne comme ci-dessous

```
CREATE TABLE personne(id int, login text NOT NULL, nom text NOT NULL, prenom text NOT NULL, datenaiss DATE, CONSTRAINT pk_personne PRIMARY KEY (id));
```

- Démarrez Tomcat et accédez à l'application depuis le contexte /Projet9BDD

##Frameworks

###Présentation [JQuery](https://jquery.com/)

JQuery est une librairie Javascript open source légère est rapide conforme au CSS3.
Ce Toolkit est cross-browser, ce qui signifie qu'il fonctionne sur presque tous les navigateurs du moment.
Il dispose d'une API en ligne compléte et une communauté assez active.

###Présentation [MooTools](http://mootools.net/)

MooTools est une collection d'utilitaires JavaScript conçus pour le développeur intermédiaire à avancé JavaScript.
Il vous permet d'écrire du code puissant et flexible, élégant, bien documenté, avec des API cohérentes.
Le code de MooTools est largement documenté et facile à lire, ce qui vous permet d'étendre les fonctionnalités pour répondre à vos exigences.

##jQuery vs MooTools

TODO

##Conclusion

TODO
