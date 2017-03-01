package test.java.unitTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import main.java.resultFileProcessors.ResultListSorter;
import main.java.sorter.LapRaceParticipant;
import main.java.sorter.MarathonParticipant;
import main.java.sorter.Participant;
import main.java.sorter.ParticipantsList;
import main.java.util.Constants;

public class ResultListSortTest {
	private ParticipantsList p;
	ArrayList<Participant> expected;
	Participant winner;
	Participant loser;
	Participant middle;
	ArrayList<Participant> pList;
	ArrayList<Participant> lrList;

	@Before
	public void setUp() {
		p = new ParticipantsList();

		p.addParticipantName(1, "Ola Persson"); // mid
		p.addParticipantName(2, "Per Olasson"); // last
		p.addParticipantName(3, "Son OlaPer"); // first

		p.addStart(1, "12.00.00");
		p.addStart(2, "12.00.00");
		p.addStart(3, "12.00.00");

		p.addFinishTime(1, "16.00.00");
		p.addFinishTime(2, "16.00.01");
		p.addFinishTime(3, "14.40.00");

		expected = new ArrayList<>();
	}

	@Test
	public void testTotalTimeSortMarathon() {
		pList = p.getParticipants(true);
		winner = new MarathonParticipant(3);
		middle = new MarathonParticipant(1);
		loser = new MarathonParticipant(2);

		winner.setName("Son OlaPer");
		loser.setName("Per Olasson");
		middle.setName("Ola Persson");

		expected.add(winner);
		expected.add(middle);
		expected.add(loser);
		new ResultListSorter(pList, Constants.ParticipantType.MARATHON);

		for (int i = 0; i < pList.size(); i++) {
			assertEquals("Not sorted correctly", expected.get(i).getName(), pList.get(i).getName());
			assertEquals("Not sorted correctly", expected.get(i).getStartNumber(), pList.get(i).getStartNumber());
		}
	}

	@Test
	public void testLapSort() {
		p = new ParticipantsList(Constants.ParticipantType.LAP);
		p.addStart(1, "12.00.00");
		p.addStart(2, "12.00.00");
		p.addStart(3, "12.00.00");
		p.addStart(4, "12.00.00");
		p.addStart(5, "12.00.00");
		p.addParticipantName(1, "Ola Persson"); // mid
		p.addParticipantName(2, "Per Olasson"); // last
		p.addParticipantName(3, "Son OlaPer"); // first
		p.addParticipantName(4, "herrow"); // mid
		p.addParticipantName(5, "Pelasson"); // last

		p.addFinishTime(1, "16.00.00");
		p.addFinishTime(1, "18.20.00");

		p.addFinishTime(2, "16.00.01");
		p.addFinishTime(2, "17.00.01");
		p.addFinishTime(2, "17.40.00");

		p.addFinishTime(3, "14.40.00");
		p.addFinishTime(3, "17.20.00");
		p.addFinishTime(3, "18.00.01");

		p.addFinishTime(4, "16.00.01");
		p.addFinishTime(4, "18.20.01");

		p.addFinishTime(5, "16.00.01");
		p.addFinishTime(5, "17.00.01");
		p.addFinishTime(5, "17.40.00");
		p.addFinishTime(5, "18.20.00");

		// 5 vinner
		// 2
		// 3
		// 1
		// 4 sist

		expected.add(new LapRaceParticipant(5));
		expected.add(new LapRaceParticipant(2));
		expected.add(new LapRaceParticipant(3));
		expected.add(new LapRaceParticipant(1));
		expected.add(new LapRaceParticipant(4));

		lrList = p.getParticipants(true);

		new ResultListSorter(lrList, Constants.ParticipantType.LAP);

		for (int i = 0; i < lrList.size(); i++) {
			assertEquals("Not sorted correctly", expected.get(i).getStartNumber(), lrList.get(i).getStartNumber());

		}

	}

	@Test
	public void testLapSortWithSameTimes() {
		p = new ParticipantsList(Constants.ParticipantType.LAP);
		p.addStart(1, "12.00.00");
		p.addStart(2, "12.00.00");
		p.addStart(3, "12.00.00");
		p.addStart(4, "12.00.00");
		p.addStart(5, "12.00.00");
		p.addParticipantName(1, "Ola Persson"); // mid
		p.addParticipantName(2, "Per Olasson"); // last
		p.addParticipantName(3, "Son OlaPer"); // first
		p.addParticipantName(4, "herrow"); // mid
		p.addParticipantName(5, "Pelasson"); // last

		p.addFinishTime(1, "16.00.00");
		p.addFinishTime(1, "18.20.00");

		p.addFinishTime(2, "16.00.01");
		p.addFinishTime(2, "17.00.01");
		p.addFinishTime(2, "18.00.00");

		p.addFinishTime(3, "14.40.00");
		p.addFinishTime(3, "17.20.00");
		p.addFinishTime(3, "18.00.00");

		p.addFinishTime(4, "16.00.01");
		p.addFinishTime(4, "18.20.01");

		p.addFinishTime(5, "16.00.01");
		p.addFinishTime(5, "17.00.01");
		p.addFinishTime(5, "17.40.00");
		p.addFinishTime(5, "18.20.00");

		// 5 vinner
		// 2
		// 3
		// 1
		// 4 sist

		expected.add(new LapRaceParticipant(5));
		expected.add(new LapRaceParticipant(2));
		expected.add(new LapRaceParticipant(3));
		expected.add(new LapRaceParticipant(1));
		expected.add(new LapRaceParticipant(4));

		lrList = p.getParticipants(true);

		new ResultListSorter(lrList, Constants.ParticipantType.LAP);

		for (int i = 0; i < lrList.size(); i++) {
			assertEquals("Not sorted correctly", expected.get(i).getStartNumber(), lrList.get(i).getStartNumber());

		}
	}

}
