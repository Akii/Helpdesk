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
	
	public static function getForTicket($ticket_id, $renderWith=null)
	{
		$db = Datasource\DataSource::getInstance();
		$query = "SELECT * FROM ticket_products WHERE TID = :tid";
		$db->prepare($query);
		$db->execute(array("tid" => $ticket_id));
		
		if($renderWith === null)
			return $db->fetchAll();
		else
			return $renderWith($db);
	}
	
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