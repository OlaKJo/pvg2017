package test.java.unitTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import main.java.resultFileProcessors.HTMLResult;

public class HTMLResultTest {

	private static String resultPath = "src/test/java/unitTest/result.html";
	private static String expectedResultPath = "src/test/java/unitTest/expectedResult.html";
	private static String regularFormatPath = "src/test/java/unitTest/resultForHTML.txt";


	@Test
	public void htmlLineConverter() {
		String[] line1 = {"Startnummer", "Namn"};
		String[] line2 = {"1", "A Asson"};
		HTMLResult html = new HTMLResult(null);
		String html1 = html.buildHTMLLine(line1);
		String html2 = html.buildHTMLLine(line2);
		assertEquals("<tr>" + "\n" + "<td>Startnummer</td>" + "\n" +"<td>Namn</td>" + "\n" + "</tr>"+"\n", html1);
		assertEquals("<tr>" + "\n" + "<td>1</td>" + "\n" + "<td>A Asson</td>" + "\n" + "</tr>"+"\n", html2);
	}

	@Test
	public void createHTMLFileTest(){
		HTMLResult html = new HTMLResult(regularFormatPath);
		html.createHTMLFile(resultPath); // create result.html
		List<String> expectedLines = null;
		List<String> actualLines = null;
		try {
			expectedLines = Files.readAllLines(Paths.get(expectedResultPath));
			actualLines = Files.readAllLines(Paths.get(resultPath));
		} catch (IOException e) {
			fail("Could not read files.");
		}
		assertEquals("The content in the files should match", expectedLines, actualLines);
		// clean up
		File file = new File(resultPath);
		file.delete();
	}
}
