<?php
/**
 * Class Paginator
 *
 * Helper for pagination
 */

namespace de\helpdesk\Utils;
use de\helpdesk\Core as Core;
use de\helpdesk\Model as Model;
use de\helpdesk\Error as Error;

class Paginator
{
	private $pages;
	private $per_page;
	private $num_rows;

	private $row_start;
	private $row_end;

	private $curr_page;
	private $next_page;
	private $prev_page;
	
	private $order = array();

	public function __construct($page,$order=array(),$per_page=0)
	{
		$val = (int) $page;
		$this->curr_page = ($val > 0) ? $val : 1;

		$this->per_page = ($per_page > 0) ? $per_page : Core\Configure::read('App.pagination.per_page');
		$this->order = (array) $order;
	}
	
	public function paginate($model)
	{
		if(!($model instanceof Model\Model))
			throw new Error\Exception("Argument passed is not a model.");
			
		$this->num_rows = $model::countAll();
		
		if($this->num_rows <= $this->per_page)
			return;
		
		$this->pages = ceil($this->num_rows / $this->per_page);
		if($this->pages < $this->curr_page) $this->curr_page = $this->pages;

		$this->next_page = min($this->curr_page+1,$this->pages);
		$this->prev_page = max(1, $this->curr_page-1);

		$this->row_start = ($this->curr_page-1) * $this->per_page;
		$this->row_end = min($this->row_start + $this->per_page, $this->num_rows);
		
		$this->validateOrder($model);
		
		$model->paginate($this->order, $this->row_start, $this->row_end);
	}
	
	private function validateOrder($model)
	{
		if(!isset($model::$table_cols))
		{
			$this->order = array();
			return;
		}
		
		$cols = $model::$table_cols;
		
		foreach($this->order as $key => $value)
		{
			if(!isset($value["col"]) || !in_array($value["col"], $cols))
				unset($this->order[$key]);
			else
			{
				$this->order[$key]["sort"] = validateSort($value["sort"]);	
			}
		}
	}
	
	private function validateSort($in)
	{
		switch($in)
		{
			case "DESC":
				return "DESC";
			default:
				return "ASC";
		}
	}
}