
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class tests {

	@Test
	public void test1_construtorState() {
		int m[][]= {{0,0,0},{0,0,0},{0,0,0}};
		Board b= new Board(m,-1);
		MCTS.State s= new MCTS.State(b,null);
		assertEquals(s.getFather(), null);
		assertEquals
	}

}
