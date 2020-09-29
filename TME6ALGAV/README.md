## Commandes ant pour tester le code
- mainvaroumas : Lance MainVaroumas, permet de tester les performances des algorithmes. Créer des fichiers timeR.csv, timeC.csv, qualityR.csv, et qualityC.csv dans le répertoire output/. Les données peuvent ainsi être dessiné avec gnuplot.
- mainrandom : Lance MainRandom, permet de tester les performances des algorithmes sur des ensembles allant de 256 points à 100 000 points. Créer des fichiers randomTimeR.csv et randomTimeC.csv dans le répertoire output/. De même, on peut tracé les données avec gnuplot.
- defaultteam : Lance TME2_supportGUI.jar, permettant de visualiser différents algorithmes implémentés tout au long des TME.

##Commande gnuplot
Dans un terminal ouvert dans le répertoire output/, lancer la commande "gnuplot". Puis, lancer dans l'invite de commande gnuplot "load "scriptgnuplot"", qui execute un script dessinant automatiquement les données des fichiers *.csv dans des fichiers svg/*.svg.
