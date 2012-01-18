<?php
/**
 * Class StatusModel
 *
 * Manages ticket status data
 */

namespace de\helpdesk\Model;
use de\helpdesk\Error as Error;

class StatusModel extends Model
{
	public static $name = "Status";
	public static $table_name = 'ticket_status';
	public static $table_cols = array('StatusID', 'description', 'bDeleted');
	public static $table_pk   = 'StatusID';
	
	public function create()
	{
		$not_null = array('description');
		$exclude  = array('StatusID', 'bDeleted');
		
		parent::create($exclude, $not_null);
	}
	
	public function save()
	{
		$not_null = array('description');
		$exclude  = array('StatusID');
		
		parent::save($exclude, $not_null);
	}
	
	public function __toString()
	{
		return $this->description;
	}
}