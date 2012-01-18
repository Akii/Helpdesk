<h1>Having trouble with one of our products?<h1>
<h2>No problem! Submit a new ticket and we take care of it immediately!</h2>
<form method="POST" target="">
	<label for="customer">Select Customer</label>
	<select name="customer">
		<?php echo $customer_options; ?>
	</select><br/>
	<label for="category">Select a category</label>
	<select name="category">
		<?php echo $category_options; ?>
	</select><br/>
	<label for="product">Select involved products</label>
	<select name="product[]" multiple>
		<?php echo $product_options; ?>
	</select><br/>
	<label for="topic">Topic:</label>
	<input type="text" maxlength="255" name="topic"/><br/>
	<label for="problem">Problem:</label>
	<textarea maxlength="5000" name="problem"></textarea><br/>
	<input type="submit" name="submit_ticket" value="Create new ticket"/>
</form>