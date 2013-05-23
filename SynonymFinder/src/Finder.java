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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import au.com.bytecode.opencsv.CSVReader;

public class Finder {

	public static void main(String args[]) throws Exception{
		lookupWord("통증");
		//processCSV();
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
		
		Document doc = Jsoup.connect("http://krdic.naver.com/search.nhn?kind=keyword&query=" + word).get();
		Elements ps;
		System.out.println("test: ");
		List<String> synonyms = new ArrayList<>();
		
		/**
		for(Element element : doc.select("a[class=fnt15]")){
			element.select("strong");
			
			System.out.println(element.text());
		} //*/
		
		for(Element element : doc.select("p[class=syn]") )
		{
		    for(Element child : element.children()) {
		    	for(Element child2 : child.children()){
			    	child2.empty();
		    	}
		    	System.out.print("child: ");
		    	System.out.println(child.text());
		    	
		    }
			
			synonyms.add(element.text());
		    System.out.println(element.text());
		}
		
		/**for(Element element : doc.select("p[class=syn]") )
		{
		    synonyms.add(element.text());
		    System.out.println(element.text());
		} //*/
		
		
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
