import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;


public class Cells {
	
	int[][] cells;
	int cx; //# cells in x direction
	int cy; //# of cells in y direction
	int csize; //size of individual cell
	Point2D.Double p;
	int[][] answer;
	
	public Cells(int x, int y, int s, Point2D.Double po, int[][] a){
		cells=new int[x][y];
		cx=x;
		cy=y;
		csize=s;
		p=po;
		answer=a;
	}

	public Point2D.Double getP() {
		return p;
	}

	public void setP(Point2D.Double p) {
		this.p = p;
	}

	public int getCx() {
		return cx;
	}

	public void setCx(int cx) {
		this.cx = cx;
	}

	public int getCy() {
		return cy;
	}

	public void setCy(int cy) {
		this.cy = cy;
	}

	public int getCsize() {
		return csize;
	}

	public void setCsize(int csize) {
		this.csize = csize;
	}

	public int[][] getCells() {
		return cells;
	}

	public void setCells(int[][] cells) {
		this.cells = cells;
	}



	public int[][] getAnswer() {
		return answer;
	}

	public void setAnswer(int[][] answer) {
		this.answer = answer;
	}
	
	public void clear(){
		for(int i=0; i<cells.length; i++){
			for(int j=0; j<cells[i].length; j++){
				cells[i][j]=0;
			}
		}
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.black);
		g.drawRect((int)(p.x-1), (int)(p.y-1), (int)(cx*csize+2), (int)(cy*csize+2));
		for(int i=0; i<cx; i++){
			if((i+1)%5==0 &&i!=cx-1) g.drawLine((int)(p.x), (int)(p.y+(i+1)*csize)+1, (int)(p.x+csize*cx), (int)(p.y+(i+1)*csize)+1); 
			for(int j=0; j<cy; j++){
				g.setColor(Color.black);
				if(i==0){
					if((j+1)%5==0 && j!=cy-1) g.drawLine((int)(p.x+(j+1)*csize)+1, (int)(p.y), (int)(p.x+(j+1)*csize)+1, (int)(p.y+csize*cy));
				}
				if(cells[i][j]==0) g.drawRect((int)(p.x+i*csize), (int)(p.y+j*csize), csize, csize);
				if(cells[i][j]==1){
					g.drawRect((int)(p.x+i*csize), (int)(p.y+j*csize), csize, csize);
					g.setColor(new Color(53, 70, 92));
					g.fillRect((int)(p.x+i*csize)+1, (int)(p.y+j*csize)+1, csize-1, csize-1);
				}
				if(cells[i][j]==2){
					g.setColor(Color.red);
					g.drawRect((int)(p.x+i*csize), (int)(p.y+j*csize), csize, csize);
					g.drawLine((int)(p.x+i*csize), (int)(p.y+j*csize), (int)(p.x+(i+1)*csize), (int)(p.y+(j+1)*csize));
					g.drawLine((int)(p.x+(i+1)*csize), (int)(p.y+j*csize), (int)(p.x+i*csize), (int)(p.y+(j+1)*csize));
				}
				
			}
		}
	}
	
	

}
