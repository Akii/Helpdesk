<?php
/**
 * Class ProductController
 *
 * Handles the requests for the product page
 */
 
namespace de\helpdesk\Controller;
use de\helpdesk\Model as Model;
use de\helpdesk\Utils as Utils;
use de\helpdesk\Error as Error;

class ProductController extends Controller
{
	public static $name = "Product";
	
	public function DefaultAction()
	{
		$this->view->setPage("default");
		
		$this->view_params["products"] = Model\ProductModel::getAll(
			function($db) {
				while($row = $db->fetch_array("assoc"))
				{
					$out .= sprintf('
					<div class="content_frame corners_01 box_shadow_02">
						<img src="/public/images/products/%u.png" height="128" width="128"/>
						<span>
							<h1>%s</h1>
							%s
						</span>
					</div>',
					$row["PID"], $row["name"], $row["description"]);
				}
				return $out;
			}
		);
	}
}