# NFA035 Projet 2025 - Gestion des Contacts

## Université Libanaise  
ISSAE - Cnam Liban  
Centre du Liban associé au Cnam Paris  
UE: Java bibliothèques et patterns NFA035  
Examen: Projet 2025  
Coordinateur: AWADA Adel  

## Description du projet
Le travail consiste à développer une application de gestion de contacts (similaire à celle des téléphones portables) avec les fonctionnalités suivantes:

### Fonctionnalités principales:
- Création et sauvegarde de contacts
- Affichage de listes triées de contacts
- Recherche de contacts
- Modification des informations d'un contact
- Suppression de contacts
- Gestion des groupes (création, affichage, modification, suppression)
- Ajout de contacts à des groupes

### Classes requises:
1. **Classe Contact** avec:
   - Variables: nom, prénom, ville, collection de numéros de téléphone
   - Constructeurs, getters/setters
   - Méthodes pour ajouter un numéro, afficher un contact, etc.

2. **Classe Groupe** avec:
   - Variables: nom, description, collection d'objets Contact
   - Méthodes pour gérer les groupes (ajouter/retirer des contacts, etc.)

3. Autres classes pour:
   - Tri des contacts
   - Sauvegarde des données
   - Implémentation du patron MVC

### Interface graphique:
1. **Fenêtre principale** avec boutons:
   - Contacts: ouvre la fenêtre de gestion des contacts
   - Groups: ouvre la fenêtre de gestion des groupes

2. **Fenêtre Contacts** avec:
   - Boutons de tri (par prénom, nom ou ville)
   - Boutons pour ajouter, modifier, supprimer ou visualiser un contact
   - Fonctionnalité de recherche dynamique

3. **Fenêtre New Contact** pour:
   - Saisir les informations d'un nouveau contact
   - Ajouter le contact à un ou plusieurs groupes
   - Validation: nom, prénom et au moins un numéro requis

4. **Fenêtre Update Contact** pour:
   - Modifier les informations d'un contact existant
   - Gérer l'appartenance aux groupes

5. **Fenêtre View Contact** pour:
   - Afficher les informations d'un contact sélectionné

6. **Fenêtre Groups** pour:
   - Afficher la liste des groupes
   - Voir les contacts d'un groupe sélectionné
   - Ajouter, modifier ou supprimer des groupes

7. **Fenêtre New Group** pour:
   - Créer un nouveau groupe
   - Ajouter des contacts au groupe

8. **Fenêtre Update Group** pour:
   - Modifier les informations d'un groupe
   - Gérer les contacts du groupe

### Exigences techniques:
- Utilisation du patron MVC
- Validation des données (champs obligatoires)
- Messages de confirmation pour les actions critiques
- Sauvegarde des données dans des fichiers

### Remarques importantes:
1. La note sera répartie en 4 parties:
   - Analyse
   - Conception
   - Implémentation
   - Test

2. La documentation est très importante à chaque étape

3. Ne pas soumettre de code non fonctionnel

4. Format du nom du projet: "NFA035_Nom_NuméroCNAM"

5. Les soutenances:
   - Durée: 10 minutes (7 min présentation + 3 min questions)
   - Dates communiquées ultérieurement

Ce projet doit être implémenté en Java en utilisant les bonnes pratiques de programmation orientée objet et le patron architectural MVC.
