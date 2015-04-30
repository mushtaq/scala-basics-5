import scala.annotation.tailrec

// Any live cell with fewer than two live neighbours dies, as if caused by under-population.
// Any live cell with two or three live neighbours lives on to the next generation.
// Any live cell with more than three live neighbours dies, as if by overcrowding.
// Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

trait CellState
case object LiveCell extends CellState
case object DeadCell extends CellState

case class Cell(x: Int, y: Int, state: CellState = DeadCell) {
  def kill: Cell = copy(state = DeadCell)
  def revive: Cell = copy(state = LiveCell)
  def isAlive: Boolean = state == LiveCell
  def isNeighbour(cell: Cell): Boolean = (cell.x <= x+1 && cell.x >= x-1) && (cell.y <= y+1 && cell.y >= y-1) && cell != this
  def neighbourCount(board: List[List[Cell]]): Int = board.flatten.filter(isNeighbour).filter(_.isAlive).size
}

def transform(cell: Cell, board: List[List[Cell]]): Cell = {
  cell.state match {
    case LiveCell if cell.neighbourCount(board) < 2 => cell.kill
    case LiveCell if cell.neighbourCount(board) < 4 => cell
    case LiveCell if cell.neighbourCount(board) > 3 => cell.kill
    case DeadCell if cell.neighbourCount(board) == 3 => cell.revive
    case _ => cell
  }
}

def transformRow(row: List[Cell], board: List[List[Cell]]): List[Cell] = {
  row.map(cell => transform(cell, board))
}

def evolve(board: List[List[Cell]]): List[List[Cell]] = {
  board.map(row => transformRow(row, board))
}

@tailrec
def gof(board: List[List[Cell]])(iter: Int): List[List[Cell]] = {
  if(iter != 0) gof(evolve(board))(iter - 1) else board
}

val seed = List(
  List(Cell(0,0), Cell(0,1), Cell(0,2), Cell(0,3), Cell(0,4)),
  List(Cell(1,0), Cell(1,1), Cell(1,2,LiveCell), Cell(1,3), Cell(1,4)),
  List(Cell(2,0), Cell(2,1), Cell(2,2,LiveCell), Cell(2,3), Cell(2,4)),
  List(Cell(3,0), Cell(3,1), Cell(3,2,LiveCell), Cell(3,3), Cell(3,4)),
  List(Cell(4,0), Cell(4,1), Cell(4,2), Cell(4,3), Cell(4,4))
)

gof(seed)(2)
