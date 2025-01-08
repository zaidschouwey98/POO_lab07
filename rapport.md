# Rapport du Laboratoire 07 - Chess

## 1. Introduction
Ce document explique le travail réalisé sur le laboratoire 07 Chess. Il montre les choix de conception, les tests effectués et les résultats obtenu.

---

## 2. En-têtes et commentaires

### Bonnes pratiques respectées
- **Commentaires clairs** : Toutes les classes et méthodes ont des explications simples en format Javadoc. Sauf celles dont le nom est suffisemment explicite.
- **Exceptions** : Si une méthode peut provoquer une erreur, c'est indiqué avec `@throws`.
- **Cas spéciaux** : Les retours ou comportements spécifiques sont détaillés.
---

## 3. Méthodes, attributs et variables

### Noms faciles à comprendre
- **Classes** : Noms en CamelCase avec une majuscule au début (ex. `King`, `Pawn`, `ChessGame`).
- **Méthodes et variables** : Noms clairs comme `moveTo`, `getGraphicalType`.
- **Constantes** : Écrites en majuscules avec des underscores. Par exemple : `DEFAULT_WIDTH`.

### Sécurité et clarté
- **Visibilité** : Les attributs sont privés et accessibles via des get/set.
- **Validation** : Les entrées des méthodes sont contrôlées pour éviter des erreurs.

---

## 4. Tests réalisés

Les tests suivants ont été effectués:

| Fonctionnalité                                             | Résultat |
|------------------------------------------------------------|----------|
| Pion: 1er mouvement à deux cases                           | OK       |
| Pion: prise d'une pièce adverse                            | OK       |
| Pion: prise en passant                                     | OK       |
| Pion: prise en passant contre un pion qui a bougé 2x       | Bloqué   |
| Pion: prise en passant contre une pièce autre qu'un pion   | Bloqué   |
| Pion: déplacement en arrière                               | Bloqué   |
| Pion: promotion                                            | OK       |
| Pion: promotion en une 3ème tour                           | OK       |
| Petit roque                                                | OK       |
| Grand roque                                                | OK       |
| Roque en échec                                             | Bloqué   |
| Roque avec 1+ case du chemin menacée                       | Bloqué   |
| Roque avec la tour ou le roi qui a déjà bougé              | Bloqué   |
| Roi: déplacement de 2+ cases                               | Bloqué   |
| Roi: déplacement sur une case menacée                      | Bloqué   |
| Général: déplacement standard                              | OK       |
| Général: déplacement sur une pièce amie                    | Bloqué   |
| Général: déplacement lors du tour adverse                  | Bloqué   |
| Général: déplacement qui met le roi ami en échec           | Bloqué   |
| Général (sauf cavalier): déplacement obstrué par une pièce | Bloqué   |

---

## 5. Diagramme UML

Un diagramme UML a été créé pour montrer les relations entre les classes. Il se trouve en annexe.

---

## 6. Choix de conception

### Organisation orientée objet
- **Abstraction** : Les classes abstraites comme `Piece` simplifient l'ajout de nouvelle pièces.
- **Responsabilités** : Chaque classe a un role précis. Par exemple : `Board` pour gérer le plateau, `ChessGame` pour controler le jeu.
- **Flexibilité** : Les mouvements sont gérés avec une interface `Movement`, ce qui permet d'ajouter des types de déplacement facilement.

---

## 7. Conclusion
Le projet respecte les bonnes pratique du développement Java et les consignes données. Les tests montrent que toutes les fonctionnalités fonctionnent correctement. Le diagramme UML est clair et correspond au code.

---

## 8. Annexes
- **Code source** 
- **Diagramme UML** 

