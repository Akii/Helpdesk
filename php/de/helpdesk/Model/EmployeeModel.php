<?php
/**
 * Class EmployeeModel
 *
 * Manages employee data
 */

namespace de\helpdesk\Model;
use de\helpdesk\Error as Error;

class EmployeeModel extends Model
{
	public static $name = "Employee";
	public static $table_name = 'employee';
	public static $table_cols = array('EID', 'firstName', 'lastName', 'username', 'password', 'bIsTroubleshooter', 'bDeleted');
	public static $table_pk   = 'EID';
	
	/**
	 * @Override
	 */
	public function create()
	{
		$not_null = array('firstName', 'lastName', 'username', 'password');
		$exclude  = array('EID', 'bDeleted');
		
		parent::create($exclude, $not_null);
	}
	
	/**
	 * @Override
	 */
	public function save()
	{
		$not_null = array('firstName', 'lastName');
		$exclude  = array('EID', 'username', 'password');
		
		parent::save($exclude, $not_null);
	}
	
	public function __toString()
	{
		return $this->firstName . " " . $this->lastName;
	}
}