<?php
/**
 * Class ProductModel
 *
 * Manages product data
 */

namespace de\helpdesk\Model;
use de\helpdesk\Error as Error;

class ProductModel extends Model
{
	public static $name = "Product";
	public static $table_name = 'product';
	public static $table_cols = array('PID', 'name', 'description');
	public static $table_pk   = 'PID';
	
	public function create()
	{
		$not_null = array('name');
		$exclude  = array('PID');
		
		parent::create($exclude, $not_null);
	}
	
	public function save()
	{
		$not_null = array('name');
		$exclude  = array('PID');
		
		parent::save($exclude, $not_null);
	}
}