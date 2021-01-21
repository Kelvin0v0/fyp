<table class="table">
  <thead class="table-light">
    <tr>
      <th scope="col">TranNo</th>
      <th scope="col">Sender</th>
      <th scope="col">Receiver</th>
      <th scope="col">amount</th>
      <th scope="col">Created Date</th>
      <th scope="col">Updated Date</th>
      <th scope="col">Status</th>
      <th scope="col">Type</th>
    </tr>
  </thead>
  <tbody>
    <tr>
    <?php

    foreach($tables as $table)
    {
        echo '<th scope="row">'.$table->id.'</th>
        <td>'.$table->sender_id.'</td>
        <td>'.$table->receiver_id.'</td>
        <td>'.$table->amount.'</td>
        <td>'.$table->create_date.'</td>
        <td>'.$table->update_date.'</td>
        <td>'.$table->status.'</td>
        <td>'.$table->type.'</td>';
    }
    ?>
      
   
    </tr>
  </tbody>
</table>

