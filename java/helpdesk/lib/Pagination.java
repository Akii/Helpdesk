package lib;

import java.sql.SQLException; 
import lib.db.Database;

public class Pagination implements IPagination {
	
	private Database db;
	private String query;
	private int per_page=1;
	
	private int rows;
	private int pages;
	private int currPage;
	
	public Pagination() throws Exception {
		this.db = Database.getInstance();
	}
	
	@Override
	public void setPerPage(int per_page) {
		this.per_page = (per_page > 0) ? per_page : 1;
		this.pages = (int) Math.ceil((double) this.rows / (double) this.per_page);
	}
	
	@Override
	public void setQuery(String query) throws SQLException {
		this.query = query;
		reload();
	}
	
	@Override
	public void reload() throws SQLException {
		// reset variables and set query, per_page
		this.rows = this.pages = this.currPage = 0;
		
		// get the number of rows for this query
		this.db.executeQuery(this.query);
		this.rows = this.db.num_rows();
		this.db.free_result();
		
		// recalculate pages
		setPerPage(this.per_page);
	}
	
	@Override
	public String paginate(int page) {
		// is there a need to paginate?
		if(this.rows <= 0 || this.rows <= this.per_page)
			return this.query;
		
		// check if the page is valid
		if(page <= 0) page = 1;
		if(page > this.pages) page = this.pages;
		
		// calculate rows
		this.currPage = page;
		
		int row_from = (this.currPage-1) * this.per_page;
		int row_to = Math.min((row_from + this.per_page), this.rows);
		
		return this.db.add_offset(this.query, row_from, row_to);
	}

	@Override
	public int getPages() {
		return this.pages;
	}

	@Override
	public int getCurrPage() {
		return this.currPage;
	}

	@Override
	public int getNextPage() {
		return (hasNextPage()) ? this.currPage+1 : this.pages;
	}

	@Override
	public int getPrevPage() {
		return (hasPrevPage()) ? this.currPage-1 : 0;
	}
	
	@Override
	public boolean hasNextPage() {
		return (this.currPage+1 <= this.pages);
	}
	
	@Override
	public boolean hasPrevPage() {
		return (this.currPage-1 >= 0);
	}

}
