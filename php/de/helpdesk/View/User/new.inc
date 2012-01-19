<div id="tabs">
	<ul>
		<?php echo $tabs; ?>
	</ul>
</div>
<div id="content">
	<?php echo $new_error; ?>
	<div id="new" class="content_form">
		<form accept="UTF-8" method="POST" action="/user/panel/new" class="box_shadow_02 gradient_04 corners_01">
			<fieldset>
				<div class="align_left">
					<label for="category">Category:</label>
						<select name="category">
							<option value="0">Select a category</option>
							<?php echo $category_options; ?>
						</select>
					<label for="topic">Topic:</label>
						<input type="text" name="topic" value="<?php echo $topic; ?>"/>
				</div>
				<div class="align_right">
					<label for="products">Products:</label>
						<select name="products[]" size="5" multiple>
							<?php echo $products; ?>
						</select>
				</div>
				<label for="problem" class="clear_both">Problem:</label>
					<textarea name="problem"><?php echo $problem; ?></textarea>

				<input type="submit" name="ticket_submit" value="Create new ticket"/>
			</fieldset>
		</form>
	</div>
</div>