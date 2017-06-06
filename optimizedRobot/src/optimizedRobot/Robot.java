package optimizedRobot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Robot {

	private static ArrayList<Integer> allArticles;

	private static List<String> cartons = new ArrayList<String>();

	public static void main(String[] args) {

		boolean articlesAreValid = false;
		Scanner scan = new Scanner(System.in);
		
		while (!articlesAreValid) {
			allArticles = new ArrayList<Integer>();
			System.out.println("Entrer articles : ");

			String articlesInput = scan.nextLine();
			
			// Initialisation de la liste des articles. Toute chaîne comportant des éléments autre que des chiffres allant de 1 à 9 est refusée.
			for (int i = 0; i < articlesInput.length(); i++) {
				int valeurArticle = Character.getNumericValue(articlesInput.charAt(i));
				if (valeurArticle <= 0 || valeurArticle > 9) {
					System.out.println(
							"L'article : " + articlesInput.charAt(i) + " n'a pas une valeur valide comprise entre 1 et 9");

					articlesAreValid = false;
					break;
				} else {
					allArticles.add(valeurArticle);
				}
				articlesAreValid = true;
			}
		}
		scan.close();

		// On trie les articles du plus gros au plus petit. Ceci afin de commencer par les plus gros qui ont moins de combinaisons possibles.
		Collections.sort(allArticles);
		Collections.reverse(allArticles);

		int nombreCartons = 0;

		// Algorithme principal permettant de regrouper les articles par carton de 10.
		while (!allArticles.isEmpty()) {

			Integer articleValue = allArticles.get(0);
			allArticles.remove(0);
			boolean noMatch = false;
			cartons.add(articleValue.toString());

			while (articleValue < 10 && !noMatch) {

				Integer articleOptimal = findBestArticle(10 - articleValue);

				if (articleOptimal == null) {
					noMatch = true;
				} else {
					cartons.set(nombreCartons, cartons.get(nombreCartons).concat(articleOptimal.toString()));
					articleValue += articleOptimal;
				}
			}
			nombreCartons++;
		}

		// Construction de la chaîne de sortie des cartons à afficher
		String outputString = cartons.remove(0);
		for (String carton : cartons) {
			outputString += "/" + carton;
		}

		System.out.println("Répartition des articles : " + outputString);
		System.out.println("Nombre de cartons utilisés : " + nombreCartons);
	}

	
	
	// Méthode permettant de rechercher l'article optimal à grouper avec le carton que l'on construit.
	// Utilisation de la récursivité car profondeur peu élevée (9 dans le pire des cas)
	public static Integer findBestArticle(int optimalValue) {

		while (optimalValue > 0) {

			if (allArticles.contains(optimalValue)) {

				allArticles.remove(allArticles.indexOf(optimalValue));
				return optimalValue;

			} else {
				return findBestArticle(optimalValue - 1);
			}
		}
		return null;
	}

}
