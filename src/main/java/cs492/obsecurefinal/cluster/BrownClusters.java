/*
 * Copyright (C) 2014 Benjamin Arnold
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cs492.obsecurefinal.cluster;

import cs492.obsecurefinal.common.DataSourceNames;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Benjamin Arnold
 */
public class BrownClusters {
    private static final Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 
    
    private static final BrownClusters instance = new BrownClusters();
    
    private final TreeMap<String, String> cluster = new TreeMap<>();
    
    private BrownClusters() {
	//singleton
	init();
    }
    
    public static final BrownClusters getInstance() {
	return instance;
    }
    
    public String cluster(String word) {
	if (word.contains(" ")) {
	    return clusterSentence(word);
	}
	return cluster.get(word);
    }
    
    public String clusterSentence(String sentence) {
	StringBuilder sb = new StringBuilder();
	String[] oldSentence = sentence.split(" ");
	Pattern p = Pattern.compile("\\b\\W"); //find special character at end of word
	for (String word : oldSentence) {
	    Matcher m = p.matcher(word);
	    String special = StringUtils.EMPTY;
	    if (m.find()) {    //preserve formatting
		special = m.group();
		log.log(Level.FINEST, "Detected {0} in {1}", new Object[]{m.group(), word});
		word = word.substring(0,m.start());
		
	    }
	    String alias = cluster(word);
	    String result = alias != null ? alias : word;
	    if (!word.equals(result)) {
		log.log(Level.FINEST, "{0} clustered to {1}", new Object[]{word, alias});
	    }
	    sb.append(result).append(special).append(" ");
	}
	return sb.toString().trim();
    }
    
    private void init() {
	Handler handler;
	try {
	    handler = new FileHandler("BrownCluster.log");
	     handler.setFormatter(new SimpleFormatter());
	    log.addHandler(handler);
	    log.setLevel(Level.WARNING);
	    
	    InputStream inStream = BrownClusters.class.getResourceAsStream(DataSourceNames.CLUSTERING_DATA);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));  
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
		sb.append(line);
	    }

	    Document doc = Jsoup.parse(sb.toString());
	    Element table = doc.getElementsByTag("table").get(0).child(0);
	    Elements rows = table.children();
	    
	    TreeMap<String, String> tCluster = new TreeMap<>();
	    for (Element row : rows) {
		Elements contents = row.getElementsByTag("td");
		if (contents.size() > 0) {
		    Element valuesCell = contents.last();
		    String valuesText = valuesCell.text();
		    StringTokenizer tokens = new StringTokenizer(valuesText, " ");
		    String key = tokens.nextToken();
		    tCluster.put(key, key);  //self alias
		    while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken();
			putWord(tCluster, key, token);
		    }
		}
	    }
	    put(tCluster);
	    list();
	} catch (IOException | SecurityException | IllegalArgumentException ex) {
	    log.log(Level.WARNING, "Error initializing brown clusters: ", ex);
	}
    }
    
    private void putWord(TreeMap<String, String> tCluster, String key, String word) {
	String token = Filter.scrub(word, Filter.TAG, Filter.APOSTRAPHE, Filter.UNDERSCORE);
	if (token != null && !StringUtils.EMPTY.equals(token)) {
	    if (WordNetDictionary.getInstance().areRelated(key, token)) {
		tCluster.put(token, key);  //alias all results to intended key
	    } 
	}
    }
    
    private void put(TreeMap<String, String> tCluster ) {
	Collection<String> values = tCluster.values();
	
	for (String alias : tCluster.keySet()) {
	    if (!values.contains(alias)) {
		cluster.put(alias, tCluster.get(alias));
	    }
	}
	
    }
    
    private void list() {
	log.log(Level.INFO, "Listing cluster mappings:");
	for (String key : cluster.keySet()) {
	    log.log(Level.INFO, "{0} => {1}", new Object[]{key, cluster.get(key)});
	}
    }
  
}
 