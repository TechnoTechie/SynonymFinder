import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

public class Finder {

	public static void main(String args[]) throws Exception{
		//getNaverData();
		processCSV();
	}
	
	public static void processCSV() throws IOException {
		String csvFileName = "보고서_단어 정리_김진성.csv";
		File csvFile = new File(csvFileName);
		//http://stackoverflow.com/questions/5965195/utf-8-cjk-characters-not-displaying-in-java
		//http://viralpatel.net/blogs/java-read-write-csv-file/
		CSVReader csvReader = new CSVReader(new FileReader(csvFileName));
		String[] row = null;
		List<DictionaryWord> dictionary = new ArrayList<DictionaryWord>();
		while((row = csvReader.readNext()) != null){
			DictionaryWord tempWord = new Finder.DictionaryWord(row[0], row[1], row[2], row[3], row[4]);
			dictionary.add(tempWord);
			System.out.println(row[0]
					+ "\n1: " + row[1]
					+ "\n2: " + row[2]
					+ "\n3: " + row[3]
					+ "\n4: " + row[4]); }
		
		//if((row = csvReader.readNext()) != null){
		//	System.out.println(row[0]
	    //  		+ "\n4: " + row[4]); }
		
		/* while((row = csvReader.readNext()) != null) {
		    System.out.println(row[0]
		              + " # " + row[1]
		              + " #  " + row[2]);
		} //*/
		//...
		csvReader.close();
	}
	
	public static void lookupWord(String word) throws Exception {
		final WebClient webClient = new WebClient();

		// Get the first page
		final HtmlPage page1 = webClient.getPage("http://krdic.naver.com/search.nhn?kind=keyword&query=" + word);

		// Get the form that we are dealing with and within that form, 
		// find the submit button and the field that we want to change.
		final HtmlForm form = page1.getFormByName("myform");

		final HtmlSubmitInput button = form.getInputByName("submitbutton");
		final HtmlTextInput textField = form.getInputByName("userid");

		// Change the value of the text field
		textField.setValueAttribute("root");

		// Now submit the form by clicking the button and get back the second page.
		final HtmlPage page2 = button.click();

		webClient.closeAllWindows();
	}

	private static class DictionaryWord{
		public String koreanWord;
		public String englishWord;
		public String level;
		public String connector;
		public String synonym;
		
		public DictionaryWord(String koreanWord, String englishWord, String level, String connector, String synonym){
			this.koreanWord = koreanWord;
			this.englishWord = englishWord;
			this.level = level;
			this.connector = connector;
			this.synonym = synonym;
		}
	}
	
	private class NaverWord {
		String koreanWord;
		String koreanDefinition;
		String[] synonyms;
		
		public NaverWord(String koreanWord, String koreanDefinition, String[] synonyms){
			this.koreanWord = koreanWord;
			this.koreanDefinition = koreanDefinition;
			this.synonyms = synonyms;
		}
		
		public String getKoreanWord() {
			return koreanWord;
		}
		public String getKoreanDefinition() {
			return koreanDefinition;
		}
		public String[] getSynonyms() {
			return synonyms;
		}
		
	}
}
