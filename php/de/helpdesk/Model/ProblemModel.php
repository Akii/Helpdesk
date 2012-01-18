<?php
/**
 * Class ProblemModel
 *
 * Manages problem categories
 */

namespace de\helpdesk\Model;
use de\helpdesk\Error as Error;

class ProblemModel extends Model
{
	public static $name = "Problem";
	public static $table_name = 'problem_category';
	public static $table_cols = array('CategoryID', 'description', 'bDeleted');
	public static $table_pk   = 'CategoryID';
	
	public function create()
	{
		$not_null = array('description');
		$exclude  = array('CategoryID', 'bDeleted');
		
		parent::create($exclude, $not_null);
	}
	
	public function save()
	{
		$not_null = array('description');
		$exclude  = array('CategoryID');
		
		parent::save($exclude, $not_null);
	}
	
	public function __toString()
	{
		return $this->description;
	}
}