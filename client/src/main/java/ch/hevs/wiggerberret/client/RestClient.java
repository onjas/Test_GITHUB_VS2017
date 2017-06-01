package ch.hevs.wiggerberret.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RestClient {
	
	private static final String URL = "http://localhost:8080/products";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		 * GET METHODS
		 */
		//getHello();
		//getAll();
		//getAllSortQuantity();
		getAllByName();
		//getAllByNameQuantity();
		
		
		/*
		 * POST METHOD (CREATION)
		 */
		//post();
		
		
		
		/*
		 * PUT METHOD (UPDATE)
		 */
		put();
		

		
		/*
		 * DELETE METHOD
		 */
		//delete();
		
	}
	
	public static void getHello(){
		System.out.print("GET HELLO METHOD\n******************");
		try{
			try{
				//Ajoute /hello pour accéder à la méthode du REST Controller
				String newURL = URL.concat("/hello");
				URL url = new URL(newURL);
				URLConnection connection = url.openConnection();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				
				String answer = null;
				
				//Affiche la réponse de la méthode appelée
				while((answer = in.readLine()) != null){
					System.out.println("\nWebservice dit : "+answer);
				}
				
				in.close();
				
			} catch (Exception e) {
				System.out.println("\nIl y a eu une erreur lors de l'appel du webservice");
				System.out.println(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getAll(){
		System.out.print("\nGET ALL METHOD\n******************");
		try{
			try{
				URL url = new URL(URL);
				URLConnection connection = url.openConnection();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				
				String answer = null;
				
				//Liste tous les produits
				System.out.println("\nVoici les produits dans le webservice : ");
				while((answer = in.readLine()) != null){
					System.out.println(answer);
				}
				
				in.close();
				
			} catch (Exception e) {
				System.out.println("\nIl y a eu une erreur lors de l'appel du webservice");
				System.out.println(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getAllSortQuantity(){
		System.out.print("\nGET ALL SORT BY QUANTITY METHOD\n*************************");
		
		Scanner sortType = new Scanner(System.in);
		String newURL;
		
		try{
			try{
				//Demande à l'utilisateur s'il veut par ordre croissant ou non
				System.out.println("\nSouhaitez-vous le tri par ordre croissant ? (oui / non)");
				String sort = sortType.nextLine();
				
				if(sort.equals("oui"))
					newURL = URL.concat("?sort=%2Bquantity");
				else
					newURL = URL.concat("?sort=%2Dquantity");				
				
				URL url = new URL(newURL);
				URLConnection connection = url.openConnection();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				
				String answer = null;
				
				System.out.println("Voici les produits triés par quantité : ");
				while((answer = in.readLine()) != null){
					System.out.println(answer);
				}
				
				in.close();
				
			} catch (Exception e) {
				System.out.println("\nIl y a eu une erreur lors de l'appel du webservice");
				System.out.println(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getAllByName(){
		System.out.print("\nGET ALL BY NAME METHOD\n*************************");
		Scanner productName = new Scanner(System.in);
		
		try{
			try{
				
				//Demande au user le nom du produit recherché
				System.out.println("\nEntrez le nom du produit recherché ...");
				String name = productName.nextLine();
				
				//On enlève les espaces et on les remplace par des + pour l'URL
				name = name.replaceAll("\\s", "+");
				
				String newURL = URL.concat("?name="+name);
				
				URL url = new URL(newURL);
				URLConnection connection = url.openConnection();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				
				String answer = null;
				
				while((answer = in.readLine()) != null){
					System.out.println("\nVoici le produit recherché par nom : "+answer);
				}
				
				in.close();
				
			} catch (Exception e) {
				System.out.println("\nIl y a eu une erreur lors de l'appel du webservice");
				System.out.println(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getAllByNameQuantity(){
		System.out.print("\nGET ALL BY NAME & QUANTITY METHOD\n*********************************");
		try{
			try{
				Scanner productName = new Scanner(System.in);
				Scanner productQuantity = new Scanner(System.in);
				
				//On demande le nom du produit
				System.out.println("\nEntrez le nom du produit recherché ...");
				String name = productName.nextLine();
				
				//On retire les espaces en remplaçant par des +
				name = name.replaceAll("\\s", "+");
				
				System.out.println("\nEntrez la quantité du produit ...");
				int quantity = productQuantity.nextInt();
				
				String newURL = URL.concat("?name="+name+"&quantity="+quantity);
				
				URL url = new URL(newURL);
				URLConnection connection = url.openConnection();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				
				String answer = null;
				
				while((answer = in.readLine()) != null){
					System.out.println("\nVoici le produit recherché par nom et quantité : "+answer);
				}
				
				in.close();
				
			} catch (Exception e) {
				System.out.println("\nIl y a eu une erreur lors de l'appel du webservice");
				System.out.println(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void post(){
		System.out.print("\nPOST METHOD\n*************");
		JSONObject product = new JSONObject();
		
		Scanner scanString = new Scanner(System.in);
		Scanner scanFigures = new Scanner(System.in);
		
		try {
			// récupération de toutes les valeurs nécessaires
			System.out.println("\nEntrez le nom du nouveau produit...");
			String name = scanString.nextLine();
			
			System.out.println("Entrez les ingrédients...");
			String ingredients = scanString.nextLine();
			
			System.out.println("Entrez la quantité...");
			int quantity = scanFigures.nextInt();
			
			System.out.println("Entrez l'unité...");
			String unit = scanString.nextLine();
			
			System.out.println("Entrez la quantité de la portion...");
			int portionQuantity = scanFigures.nextInt();
			
			System.out.println("Entrez l'unité de la portion...");
			String portionUnit = scanString.nextLine();
			
			JSONArray nutrients = new JSONArray();
			
			System.out.println("Entrez le nom du nutriment suivant... (exit pour quitter)");
			
			String nutrientName;
			
			while(!(nutrientName = scanString.nextLine()).equals("exit")){
				// creation d'un objet nutrient
				JSONObject nutrient = new JSONObject();			
				
				// récupération de toutes les valeurs nécessaires
				System.out.println("Entrez l'unité...");
				String unitNutrient = scanString.nextLine();
				
				System.out.println("Entrez la proportion en %age...");
				double perHundred = scanFigures.nextDouble();
				
				System.out.println("Entrez la proportion par portion...");
				double perPortion = scanFigures.nextDouble();
				
				System.out.println("Entrez la proportion par jour...");
				int perDay = scanFigures.nextInt();
				
				// ajout de tous les éléments
				nutrient.put("name", nutrientName);
				nutrient.put("unit", unitNutrient);
				nutrient.put("perHundred", perHundred);
				nutrient.put("perPortion", perPortion);
				nutrient.put("perDay", perDay);
				
				// ajout du nutriment dans le tableau de nutrients
				nutrients.put(nutrient);
				
				System.out.println("Entrez le nom du nutriment suivant... (exit pour quitter)");
			}
			
			// ajout de tous les éléments
			product.put("name", name);
			product.put("ingredients", ingredients);
			product.put("quantity", quantity);
			product.put("unit", unit);
			product.put("portionQuantity", portionQuantity);
			product.put("portionUnit", portionUnit);
			product.put("nutrients", nutrients);
			
			System.out.println(product.toString());
			
			scanString.close();
			scanFigures.close();
			
			// Ajout dans la db de l'élément via le webservice
			HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 

		    HttpPost request = new HttpPost(URL);
		    StringEntity params =new StringEntity(product.toString(), "UTF-8");
		    request.addHeader("content-type", "application/json;charset=UTF-8");
		    request.setEntity(params);
		    httpClient.execute(request);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void put(){
		System.out.print("\nPUT METHOD\n**********");
		
		// Récupération de l'élément souhaité via la méthode get
		String answer = null;	
		
		Scanner productId = new Scanner(System.in);
		System.out.println("\nEntrez l'id du produit que vous souhaitez mettre à jour ...");
		String idProduct = productId.nextLine();
		
		try{
			
			URL url = new URL(URL.concat("/"+idProduct));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			answer = in.readLine();
			System.out.println(answer);
			
			in.close();			
		} catch (Exception e) {
			System.out.println("\nIl y a eu une erreur lors de l'appel du webservice");
			System.out.println(e);
		}
		
		if(answer != null){			
			try {
				JSONObject product = new JSONObject(answer);
				
				// Modification des valeurs si nécessaire
				Scanner scanString = new Scanner(System.in);
				Scanner scanFigures = new Scanner(System.in);
				
				String tmp = new String();
				
				// récupération de toutes les valeurs nécessaires et update si désiré par user
				System.out.println("Entrez le nom du produit [" + product.getString("name") + "] (appuyer sur entrée pour conserver la valeur actuelle...)");
				String name = scanString.nextLine();
				name = name.length() == 0 ? product.getString("name") : name;
				
				System.out.println("Entrez les ingrédients [" + product.getString("ingredients") + "] (appuyer sur entrée pour conserver la valeur actuelle...)");
				String ingredients = scanString.nextLine();
				ingredients = ingredients.length() == 0  ? product.getString("ingredients") : ingredients;
				
				System.out.println("Entrez la quantité [" + product.getInt("quantity") + "] (appuyer sur entrée pour conserver la valeur actuelle...)");
				int quantity = 0;
				tmp = scanString.nextLine();
				quantity = tmp.length() == 0  ? product.getInt("quantity") : Integer.parseInt(tmp);
				
				System.out.println("Entrez l'unité [" + product.getString("unit") + "] (appuyer sur entrée pour conserver la valeur actuelle...)");
				String unit = scanString.nextLine();
				unit = unit.length() == 0  ? product.getString("unit") : unit;
				
				System.out.println("Entrez la quantité de la portion [" + product.getInt("portionQuantity") + "] (appuyer sur entrée pour conserver la valeur actuelle...)");
				int portionQuantity = 0;
				tmp = scanString.nextLine();
				portionQuantity = tmp.length() == 0 ? product.getInt("portionQuantity") : Integer.parseInt(tmp);
				
				if(product.has("portionUnit") && !product.isNull("portionUnit"))
					System.out.println("Entrez l'unité de la portion [" + product.getString("portionUnit") + "] (appuyer sur entrée pour conserver la valeur actuelle...)");
				else
					System.out.println("Entrez l'unité de la portion...");
				String portionUnit = scanString.nextLine();
				if(product.has("portionUnit") && !product.isNull("portionUnit"))
					portionUnit = portionUnit.length() == 0  ? product.getString("portionUnit") : portionUnit;
				
				JSONArray nutrientsOld = (JSONArray) product.get("nutrients");
				JSONArray nutrientsNew = new JSONArray();
				
				System.out.println("Au tour des nutriments!");
				int j = 0;
				int sizeNutrientsOld = nutrientsOld.length();
				
				if(sizeNutrientsOld > 0){
					for (int i = 0; i < nutrientsOld.length(); i++) {
						// récupération du nutrient
						JSONObject nutrient = nutrientsOld.getJSONObject(i);
						
						// récupération de toutes les valeurs nécessaires
						System.out.println("Entrez le nom du nutrient " + (i+1) + " [" + nutrient.getString("name") + "] (appuyer sur entrée pour conserver la valeur actuelle...)");
						String nameNutrient = scanString.nextLine();
						nameNutrient = nameNutrient.length() == 0 ? nutrient.getString("name") : nameNutrient;
						
						System.out.println("Entrez l'unité [" + nutrient.getString("unit") + "] (appuyer sur entrée pour conserver la valeur actuelle...)");
						String unitNutrient = scanString.nextLine();
						unitNutrient = unitNutrient.length() == 0 ? nutrient.getString("unit") : unitNutrient;
						
						System.out.println("Entrez la proportion en %age [" + nutrient.getDouble("perHundred") + "] (appuyer sur entrée pour conserver la valeur actuelle...)");		
						double perHundred = 0;
						tmp = scanString.nextLine();
						perHundred = tmp.length() == 0 ? nutrient.getDouble("perHundred") : Double.parseDouble(tmp);
						
						System.out.println("Entrez la proportion par portion [" + nutrient.getDouble("perPortion") + "] (appuyer sur entrée pour conserver la valeur actuelle...)");				
						double perPortion = 0;
						tmp = scanString.nextLine();
						perPortion = tmp.length() == 0 ? nutrient.getDouble("perPortion") : Double.parseDouble(tmp);
						
						System.out.println("Entrez la proportion par jour [" + nutrient.getInt("perDay") + "] (appuyer sur entrée pour conserver la valeur actuelle...)");
						int perDay = 0;
						tmp = scanString.nextLine();
						perDay = tmp.length() == 0 ? nutrient.getInt("perDay") : Integer.parseInt(tmp);
						
						// ajout de tous les éléments
						nutrient.put("name", nameNutrient);
						nutrient.put("unit", unitNutrient);
						nutrient.put("perHundred", perHundred);
						nutrient.put("perPortion", perPortion);
						nutrient.put("perDay", perDay);
						
						// ajout du nutriment dans le tableau de nutrients
						nutrientsNew.put(nutrient);
					}
				}
					
				System.out.println("Ajoutons des nutriments! Si vous ne voulez pas en ajouter maintenant, écrivez'exit' et appuyer sur enter, si vous ne voulez pas en ajouter, appuyez sur le bouton enter");
				while(!scanString.nextLine().equals("exit")){
					// récupération du nutrient
					JSONObject nutrient = new JSONObject();
					
					// récupération de toutes les valeurs nécessaires
					System.out.println("Entrez le nom du nutrient...");
					String nameNutrient = scanString.nextLine();
					
					System.out.println("Entrez l'unité...");
					String unitNutrient = scanString.nextLine();
					
					System.out.println("Entrez la proportion en %age...");		
					double perHundred = scanFigures.nextDouble();
					
					System.out.println("Entrez la proportion par portion...");				
					double perPortion = scanFigures.nextDouble();
					
					System.out.println("Entrez la proportion par jour...");
					int perDay = (int) scanFigures.nextDouble();
					
					// ajout de tous les éléments
					nutrient.put("name", nameNutrient);
					nutrient.put("unit", unitNutrient);
					nutrient.put("perHundred", perHundred);
					nutrient.put("perPortion", perPortion);
					nutrient.put("perDay", perDay);
					
					// ajout du nutriment dans le tableau de nutrients
					nutrientsNew.put(nutrient);
					
					System.out.println(" Si vous ne voulez pas en ajouter maintenant, écrivez'exit' et appuyer sur enter, si vous ne voulez pas en ajouter, appuyez sur le bouton enter");
				}
				
				// ajout de tous les éléments
				product.put("name", name);
				product.put("ingredients", ingredients);
				product.put("quantity", quantity);
				product.put("unit", unit);
				product.put("portionQuantity", portionQuantity);
				product.put("portionUnit", portionUnit);
				product.put("nutrients", nutrientsNew);
				
				System.out.println(product.toString());
				
				scanString.close();
				
				// Modification dans la db de l'élément via le webservice
			    URL url = new URL(URL.concat("/"+idProduct));
			    HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			    httpCon.setDoOutput(true);
			    httpCon.setRequestMethod("PUT");
			    httpCon.setRequestProperty("Content-Type", "application/json");
			    OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
			    out.write(product.toString());
			    out.close();
		    	httpCon.getInputStream();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

	public static void delete(){
		System.out.print("\nDELETE METHOD\n*************");
		
		Scanner productId = new Scanner(System.in);
		System.out.println("\nEntrez l'id du produit que vous souhaitez supprimer ...");
		String idProduct = productId.nextLine();
		
		
		try {
			try {
				URL url = new URL(URL.concat("/"+idProduct));
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("DELETE");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
 
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
 
				String response = null;
				while ((response = in.readLine()) != null) {
					System.out.println("\nWebservice dit : " + response);
				}
				System.out.println("\nProgramme terminé.");
				in.close();
			} catch (Exception e) {
				System.out.println("\nIl y a eu une erreur lors de l'appel du webservice");
				System.out.println(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
