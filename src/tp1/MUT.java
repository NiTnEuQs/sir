package tp1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MUT {

	private List<String> usersList;
	private List<String> themesList;
	private int[][] mut;

	public MUT () {
		usersList = new ArrayList<String>();
		themesList = new ArrayList<String>();
	}
	
    public void extractInformations() {
    	File f = new File("Files/Log-clients-themes.txt");
    	File fU = new File("Files/Users.txt");
    	File fT = new File("Files/Themes.txt");
    	File fM = new File("Files/MUT.txt");
    	File fR = new File("Files/Resume.txt");
        
        if (!f.exists()) {
        	System.err.println("File " + f.getName() + " not found");
        	return;
        }

        try {
        	if (!fU.exists()) fU.createNewFile();
        	if (!fT.exists()) fT.createNewFile();
        	if (!fM.exists()) fM.createNewFile();
        	if (!fR.exists()) fR.createNewFile();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        String line = null;
        BufferedReader br = null;
        BufferedWriter bwU = null;
        BufferedWriter bwT = null;
        BufferedWriter bwM = null;
        BufferedWriter bwR = null;
        
		try {
			br = new BufferedReader(new FileReader(f));
			bwU = new BufferedWriter(new FileWriter(fU));
			bwT = new BufferedWriter(new FileWriter(fT));
			bwM = new BufferedWriter(new FileWriter(fM));
			bwR = new BufferedWriter(new FileWriter(fR));
			
	        while ((line = br.readLine()) != null && line.length() > 0) {
	        	String user = getUser(line);
	        	String theme = getTheme(line);
	        	
        		if (!usersList.contains(user)) {
        			usersList.add(user);
					bwU.write(user + "\n");
	        	}
        		
	        	if (!themesList.contains(theme)) {
	        		themesList.add(theme);
					bwT.write(theme + "\n");
	        	}
	        }
	        
	        br.close();
	        br = new BufferedReader(new FileReader(f));
	        mut = new int[usersList.size()][themesList.size()];
	        
	        while ((line = br.readLine()) != null && line.length() > 0) {
	        	String user = getUser(line);
	        	String theme = getTheme(line);
	        	
	        	mut[usersList.indexOf(user)][themesList.indexOf(theme)]++;
	        }
	        
	        for (int i = 0; i < mut.length; i++) {
	        	for (int j = 0; j < mut[0].length; j++) {
			        bwM.write(mut[i][j] + (j < mut[0].length-1 ? ";" : ""));
	        	}
	        	bwM.write("\n");
        	}
	        
	        for (int i = 0; i < mut.length; i++) {
	        	for (int j = 0; j < mut[0].length; j++) {
	        		bwR.write(usersList.get(i) + ";" + themesList.get(j) + ";" + mut[i][j] + "\n");
	        	}
        	}
	        
	        br.close();
	        bwU.close();
	        bwT.close();
	        bwM.close();
	        bwR.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
    }
    
    public String getUser (String line) {
    	return line.split(";")[1];
    }
    
    public String getTheme (String line) {
    	return line.split(";")[2];
    }
    
    public static void main(String[] args) {
		MUT mut = new MUT();
		mut.extractInformations();
	}
	
}
