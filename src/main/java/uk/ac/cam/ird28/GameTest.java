package uk.ac.cam.ird28;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameTest {

	@Test
	public void test() {
		Game g = new Game();
		g.update(0, 1);
		assertTrue(g.getSpace(0)==1);
		assertTrue(g.getSpace(5)==0);
		assertFalse(g.hasWon(1));
		g.update(4, 1);
		g.update(8, 1);
		assertTrue(g.hasWon(1));
		g.update(1, 2);
		g.update(2, 2);
		g.update(3, 2);
		g.update(5, 2);
		g.update(6, 2);
		assertFalse(g.fullBoard());
		assertFalse(g.hasWon(2));
		assertTrue(g.getRandMove()==7);
		g.update(g.getRandMove(), 2);
		assertTrue(g.fullBoard());
		Game h = new Game();
		h.update(2, 2);
		h.update(4, 2);
		h.update(6, 2);
		assertTrue(h.hasWon(2));
	}

}
