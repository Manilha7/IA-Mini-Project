import static org.junit.Assert.*;

import org.junit.Test;

public class Tests {

	@Test
	public void test1_construtorState() {
			int m[][]= {{0,0,0},{0,0,0},{0,0,0}};
			Board b= new Board(m,-1);
			MCTS.State s= new MCTS.State(b,null);
			assertEquals(s.getFather(), null);
			assertEquals((Board) s.getLayout(),b);
		}
	
	@Test
	public void test2_construtorState() {
			int m[][]= {{0,-1,0},{1,0,0},{0,0,0}};
			Board b= new Board(m,1);
			MCTS.State s= new MCTS.State(b,null);
			assertEquals(s.getFather(), null);
			assertEquals((Board) s.getLayout(),b);
		}
	
	@Test
	public void test3_construtorState() {
			int m[][]= {{0,1,0},{-1,0,0},{0,0,0}};
			int m1[][]= {{0,1,0},{-1,0,1},{0,0,0}};
			Board b= new Board(m,-1);
			Board b1= new Board(m1,1);
			MCTS.State s= new MCTS.State(b,null);
			MCTS.State s1= new MCTS.State(b1,s);
			assertEquals(s.getFather(), null);
			assertEquals(s1.getFather(), s);
			assertEquals((Board) s1.getLayout(),b1);
		}
	
	
	@Test
	public void test1_solve() {
			int m[][]= {{-1,0,0},{1,1,1},{-1,0,0}};
			int m1[][]= {{-1,0,0},{1,1,0},{-1,0,0}};
			Board b= new Board(m,1);
			Board b2= new Board(m1,-1);
			MCTS mcts= new MCTS();
			assertEquals(b,((Board) mcts.solve(b2)));
		}
	
	@Test
	public void test2_solve() {
			int m[][]= {{-1,-1,1},{1,1,-1},{1,0,0}};
			int m1[][]= {{-1,-1,0},{1,1,-1},{1,0,0}};
			Board b= new Board(m,1);
			Board b2= new Board(m1,-1);
			MCTS mcts= new MCTS();
			assertEquals(b,((Board) mcts.solve(b2)));
		}
	
	@Test
	public void test3_solve() {
			int m[][]= {{-1,-1,1},{1,1,-1},{1,0,0}};
			int m1[][]= {{-1,-1,0},{1,1,-1},{1,0,0}};
			Board b= new Board(m,1);
			Board b2= new Board(m1,-1);
			MCTS mcts= new MCTS();
			assertEquals(b,((Board) mcts.solve(b2)));
		}
	
	@Test
	public void test_backpropagacion() {
			int m[][]= {{-1,-1,1},{1,1,-1},{1,0,0}};
			int m1[][]= {{-1,-1,0},{1,1,-1},{1,0,0}};
			Board b= new Board(m,1);
			Board b2= new Board(m1,-1);
			MCTS mcts= new MCTS();
			assertEquals(b,((Board) mcts.solve(b2)));
		}
}
