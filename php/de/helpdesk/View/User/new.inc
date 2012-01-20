<script type="text/javascript">
$(document).ready(function() {
	$('#new_ticket_form').submit(function() {
		var category = $('#category').val();
		var topic = $('#topic').val();
		var problem = $('#problem').val();
		
		if(category <= 0 || topic == "" || problem == "")
		{
			$('#ajax_error').html('<div class="warning gradient_06  box_shadow_02">Please fill in all the fields.</div>');
			return false;
		}
		
		return true;
	});
});
</script>
<div id="tabs">
	<ul>
		<?php echo $tabs; ?>
	</ul>
</div>
<div id="content">
	<?php echo $new_error; ?>
	<div id="ajax_error"></div>
	<div id="new" class="content_form">
		<form id="new_ticket_form" accept="UTF-8" method="POST" action="/user/panel/new" class="box_shadow_02 gradient_04 corners_01">
			<fieldset>
				<div class="align_left">
					<label for="category">Category:</label>
						<select id="category" name="category">
							<option value="0">Select a category</option>
							<?php echo $category_options; ?>
						</select>
					<label for="topic">Topic:</label>
						<input id="topic" type="text" name="topic" value="<?php echo $topic; ?>"/>
				</div>
				<div class="align_right">
					<label for="products">Products:</label>
						<select name="products[]" size="5" multiple>
							<?php echo $products; ?>
						</select>
				</div>
				<label for="problem" class="clear_both">Problem:</label>
					<textarea id="problem" name="problem"><?php echo $problem; ?></textarea>

				<input type="submit" name="ticket_submit" value="Create new ticket"/>
			</fieldset>
		</form>
	</div>
</div>