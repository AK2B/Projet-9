// init-mongo.js

// Ajout de données d'initialisation
var initData = [
    {"patId": 1, "patient": "TestNone", "note": "Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé"},
    {"patId": 2, "patient": "TestBorderline", "note": "Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement"},
    {"patId": 2, "patient": "TestBorderline", "note": "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale"},
    {"patId": 3, "patient": "TestInDanger", "note": "Le patient déclare qu'il fume depuis peu"},
    {"patId": 3, "patient": "TestInDanger", "note": "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé"},
    {"patId": 4, "patient": "TestEarlyOnset", "note": "Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments"},
    {"patId": 4, "patient": "TestEarlyOnset", "note": "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps"},
    {"patId": 4, "patient": "TestEarlyOnset", "note": "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé Taille, Poids, Cholestérol, Vertige et Réaction"}
];

// Itération sur les données d'initialisation et insertion dans la collection
for (var i = 0; i < initData.length; i++) {
    db.notes.insert(initData[i]);
}