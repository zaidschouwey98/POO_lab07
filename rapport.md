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

### Méthode de test
Les tests ont été effectué  à la main.

### Résultats obtenus
| Fonctionnalité       | Scénario                     | Résultat |
|----------------------|------------------------------|----------|
| Déplacement de base  | Pion avance d'une case       | Passé |
| Roque                | Roi et tour non déplacés     | Passé |
| Déplacement illégal  | Pion en arrière              | Passé |
| Promotion            | Pion atteint dernière rangée | Passé |
| Vérification d'échec | Roi mit en échec             | Passé |

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

