<?php
/**
 * Class TicketModel
 *
 * Manages ticket data
 */

namespace de\helpdesk\Model;
use de\helpdesk\Error as Error;

class TicketModel extends Model
{
	public static $name = "Ticket";
	public static $table_name = 'ticket';
	public static $table_cols = array('TID', 'customer_CID', 'employee_EID', 'CategoryID', 'StatusID', 'Topic', 'Problem', 'Note', 'Solution', 'created_on', 'last_update');
	public static $table_pk   = 'TID';
	
	public static function getForCustomer($CID, $renderWith=null)
	{
		$db = Datasource\DataSource::getInstance();
		$query = "	SELECT TID, e.firstName e_fn, e.lastName e_ln, s.description status, Topic, last_update
					FROM ticket t 
					LEFT JOIN employee e ON(t.employee_EID = e.EID) 
					JOIN ticket_status s ON(t.StatusID = s.StatusID) 
					WHERE customer_CID = :cid 
					ORDER BY last_update DESC";
		
		$db->prepare($query);
		$db->execute(array("cid" => $CID));
		
		if($renderWith !== null)
		{
			return $renderWith($db);
		}
		else
		{
			$out = array();
			while($row = $db->fetch_array("assoc"))
			{
				$out[] = $row;
			}
			return $out;
		}
	}
	
	public function create()
	{
		$not_null = array('customer_CID', 'CategoryID', 'StatusID', 'Topic', 'Problem');
		$exclude  = array('TID', 'created_on');
		
		$this->last_update = date("Y-m-d H:i:s", time());
				
		parent::create($exclude, $not_null);
	}
	
	public function save()
	{
		$not_null = array('customer_CID', 'CategoryID', 'StatusID', 'Topic', 'Problem');
		$exclude  = array('TID', 'created_on');
		
		if(!isset($this->employee_EID) || $this->employee_EID === "")
			$this->employee_EID = null;
		
		$this->last_update = date("Y-m-d H:i:s", time());
		
		parent::save($exclude, $not_null);
	}
	
	public function delete()
	{
		return false;
	}
	
	public function setProblemCategory(ProblemModel $model)
	{
		if($model->CategoryID <= 0)
			return;
		
		$this->CategoryID = $model->CategoryID;
		$this->save();
	}
	
	public function setStatus(StatusModel $model)
	{
		if($model->StatusID <= 0)
			return;
		
		$this->StatusID = $model->StatusID;
		$this->save();
	}
	
	public function addProduct(ProductModel $model)
	{
		if(!isset($model::$table_pk) || !isset($model::$table_name) || !($model instanceof ProductModel))
			return;
		
		$pk = $model::$table_pk;
		$PID = $model->$pk;
		
		$query = sprintf("INSERT IGNORE INTO products_involved (ticket_TID, product_PID) VALUES (%u, %u)", $this->TID, $PID);
		$this->db->query($query);
	}
	
	public function delProduct(ProductModel $model)
	{
		if(!isset($model::$table_pk) || !isset($model::$table_name) || !($model instanceof ProductModel))
			return;
		
		$pk = $model::$table_pk;
		$PID = $model->$pk;
		
		$query = sprintf("DELETE FROM products_involved WHERE ticket_TID = %u AND product_PID = %u", $this->TID, $PID);
		$this->db->query($query);
	}
}