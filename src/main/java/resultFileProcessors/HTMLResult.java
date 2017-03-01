package main.java.resultFileProcessors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import main.java.sorter.Parser;

/**
 * Generates HTML from result files.
 * @author hek09lli
 *
 */
public class HTMLResult {
	private String pathToRead;

	/**
	 * 
	 * @param pathToRead
	 *            is the path to result file to be converted to html table
	 */
	public HTMLResult(String pathToRead) {
		this.pathToRead = pathToRead;
	}

	/**
	 * Converts parsed result file line into a seperate html table line
	 * 
	 * @param line
	 *            read from result file as vector
	 * @return a html table segment
	 */
	public String buildHTMLLine(String[] line) {
		StringBuilder sb = new StringBuilder();
		sb.append("<tr>" + "\n");
		for (int i = 0; i < line.length; i++) {
			sb.append("<td>" + line[i] + "</td>" + "\n");
		}
		sb.append("</tr>" + "\n");
		return sb.toString();
	}

	/**
	 * Creates html code for table
	 * 
	 * @return a html table in string format
	 */
	public String buildHTMLTable() {
		BufferedReader bufferedReader = null;
		Parser lineParser = new Parser();
		StringBuilder sb = new StringBuilder();
		sb.append("<table style=" + "\"" + "width:100%" + "\">" + "\n");
		try {
			bufferedReader = new BufferedReader(new FileReader(pathToRead));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				String[] lines = lineParser.parse(line);
				sb.append(buildHTMLLine(lines));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		sb.append("</table>");
		return sb.toString();
	}

	/**
	 * Writes a html file with result table
	 * 
	 * @param resultPath
	 *            path where file is written
	 */
	public void createHTMLFile(String resultPath) {
		File file = new File(resultPath);
		String table = buildHTMLTable();
		PrintWriter writer;
		try {
			writer = new PrintWriter(file);
			writer.print(table);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		writer.close();

	}
}
